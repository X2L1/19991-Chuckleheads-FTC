package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RGBSubsystem {

    private Servo[] rgbLights;
    private ColorSensor[] colorSensors;
    private OdometryUtils odometryUtils;

    private boolean alignmentEnabled = false;
    private double targetX = 0.0; // Target coordinates for alignment
    private double targetY = 0.0;
    private ElapsedTime timer = new ElapsedTime();
    private double flashPeriod = 0.5; // Seconds for flash on/off
    private double rainbowPeriod = 0.2; // Seconds for rainbow color change
    private int rainbowStep = 0;

    // Assumed positions for colors (calibrate during testing)
    private final double POS_OFF = 0.0;
    private final double POS_RED = 0.1;
    private final double POS_ORANGE = 0.2;
    private final double POS_YELLOW = 0.3;
    private final double POS_GREEN = 0.4;
    private final double POS_BLUE = 0.5;
    private final double POS_INDIGO = 0.6;
    private final double POS_VIOLET = 0.7; // Purple
    private final double POS_WHITE = 0.8;

    private final double[] rainbowColors = {POS_RED, POS_ORANGE, POS_YELLOW, POS_GREEN, POS_BLUE, POS_INDIGO, POS_VIOLET};

    // Enum for artifact colors
    private enum ArtifactColor {
        NONE,
        PURPLE,
        GREEN
    }

    public RGBSubsystem(HydraHardware hardware) {
        rgbLights = new Servo[]{hardware.leftRGB, hardware.centerRGB, hardware.rightRGB};
        colorSensors = new ColorSensor[]{hardware.leftColorSensor, hardware.centerColorSensor, hardware.rightColorSensor};
        odometryUtils = new OdometryUtils(hardware.pinpoint);
    }

    // General method to set a light to a specific color
    public void setColor(Servo light, String color) {
        double pos = POS_OFF;
        switch (color.toLowerCase()) {
            case "red":
                pos = POS_RED;
                break;
            case "orange":
                pos = POS_ORANGE;
                break;
            case "yellow":
                pos = POS_YELLOW;
                break;
            case "green":
                pos = POS_GREEN;
                break;
            case "blue":
                pos = POS_BLUE;
                break;
            case "indigo":
                pos = POS_INDIGO;
                break;
            case "violet":
            case "purple":
                pos = POS_VIOLET;
                break;
            case "white":
                pos = POS_WHITE;
                break;
            case "off":
            default:
                pos = POS_OFF;
                break;
        }
        light.setPosition(pos);
    }

    // Specific method example: setColorBlue
    public void setColorBlue(Servo light) {
        setColor(light, "blue");
    }

    // Add similar methods for other colors if needed, e.g., setColorGreen(Servo light)

    // Turn all lights off
    public void allOff() {
        for (Servo light : rgbLights) {
            light.setPosition(POS_OFF);
        }
    }

    // Set each light to the color of the ball in its chamber
    public void setToBallColors() {
        for (int i = 0; i < 3; i++) {
            ArtifactColor color = getArtifactColor(colorSensors[i]);
            String colorStr = (color == ArtifactColor.GREEN) ? "green" :
                    (color == ArtifactColor.PURPLE) ? "purple" : "off";
            setColor(rgbLights[i], colorStr);
        }
    }

    // Private method to detect artifact color (thresholds to be calibrated)
    private ArtifactColor getArtifactColor(ColorSensor sensor) {
        int red = sensor.red();
        int green = sensor.green();
        int blue = sensor.blue();

        if (red < 50 && green < 50 && blue < 50) {
            return ArtifactColor.NONE;
        } else if (green > 900 && green > red && green > blue) {
            return ArtifactColor.GREEN;
        } else if (red > 900 && blue > 1500 && green < 1300) {
            return ArtifactColor.PURPLE;
        } else {
            return ArtifactColor.NONE;
        }
    }

    // Enable alignment aid mode with target coordinates
    public void enableAlignmentAid(double targetX, double targetY) {
        alignmentEnabled = true;
        this.targetX = targetX;
        this.targetY = targetY;
        timer.reset();
        rainbowStep = 0;
    }

    // Disable alignment aid mode
    public void disableAlignmentAid() {
        alignmentEnabled = false;
        allOff();
    }

    // Update method to be called in the opmode loop for flashing and alignment logic
    public void update() {
        if (!alignmentEnabled) {
            return;
        }

        odometryUtils.update();

        String direction = odometryUtils.determineTurnDirection(targetX, targetY);

        if (direction.equals("aligned")) {
            // Aligned: flash all rainbow
            if (timer.seconds() > rainbowPeriod) {
                timer.reset();
                rainbowStep = (rainbowStep + 1) % rainbowColors.length;
            }
            double pos = rainbowColors[rainbowStep];
            for (Servo light : rgbLights) {
                light.setPosition(pos);
            }
        } else {
            // Not aligned: flash the appropriate side green
            int lightIndex = direction.equals("right") ? 2 : 0; // index 0 = left, 2 = right
            double pos = (((int) (timer.seconds() / flashPeriod)) % 2 == 0) ? POS_GREEN : POS_OFF;

            for (int i = 0; i < 3; i++) {
                rgbLights[i].setPosition((i == lightIndex) ? pos : POS_OFF);
            }

            if (timer.seconds() > flashPeriod * 2) { // Reset every two periods to sync
                timer.reset();
            }
        }
    }
}