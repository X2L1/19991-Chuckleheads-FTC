package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class IndexerSubsystem {

    private Servo[] chamberServos;
    private ColorSensor[] colorSensors;
    private Servo[] rgbLights;

    // Enum for detected artifact colors
    private enum ArtifactColor {
        NONE,
        PURPLE,
        GREEN
    }

    // Constructor to initialize hardware
    public IndexerSubsystem(HydraHardware hardware) {
        chamberServos = new Servo[]{
                hardware.leftChamber,
                hardware.centerChamber,
                hardware.rightChamber
        };

        colorSensors = new ColorSensor[]{
                hardware.leftColorSensor,
                hardware.centerColorSensor,
                hardware.rightColorSensor
        };


    }

    // Detect the color of the artifact in a chamber using the color sensor
    // Thresholds should be adjusted during testing based on actual sensor readings
    private ArtifactColor getArtifactColor(ColorSensor sensor) {
        int red = sensor.red();
        int green = sensor.green();
        int blue = sensor.blue();

        // Example thresholds (calibrate these!)
        if (red < 50 && green < 50 && blue < 50) {
            return ArtifactColor.NONE; // Empty or no detection
        } else if (green > 100 && green > red && green > blue) {
            return ArtifactColor.GREEN;
        } else if (red > 900 && blue > 1500 && green < 1300) {
            return ArtifactColor.PURPLE;
        } else {
            return ArtifactColor.NONE; // Unknown or not matching
        }
    }







    // Kick a specific chamber by setting servo to kick position
    // Assume 1.0 is the kick/up position; adjust as needed
    public void kickChamber(int chamberIndex) {
        if (chamberIndex >= 0 && chamberIndex < 3) {
            chamberServos[chamberIndex].setPosition(.52);

        }
    }

    // Reset a specific chamber servo to rest position
    // Assume 0.0 is the down/rest position; adjust as needed
    // Call this after a delay or when kick is complete
    public void resetChamber(int chamberIndex) {
        if (chamberIndex >= 0 && chamberIndex < 3) {
            chamberServos[chamberIndex].setPosition(.8);
        }
    }

    // Kick all chambers regardless of color
    public void kickAll() {
        for (int i = 0; i < 3; i++) {
            kickChamber(i);
        }
    }

    // Kick only chambers with purple artifacts
    public void kickPurple() {
        for (int i = 0; i < 3; i++) {
            if (getArtifactColor(colorSensors[i]) == ArtifactColor.PURPLE) {
                kickChamber(i);
                break;
            }
        }
    }

    // Kick only chambers with green artifacts
    public void kickGreen() {
        for (int i = 0; i < 3; i++) {
            if (getArtifactColor(colorSensors[i]) == ArtifactColor.GREEN) {
                kickChamber(i);
                break;
            }
        }
    }

    // Reset all chamber servos to rest position
    public void resetAll() {
        for (int i = 0; i < 3; i++) {
            resetChamber(i);
        }
    }
}
