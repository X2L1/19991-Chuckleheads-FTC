package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class RGBSubsystem extends SubsystemBase {

    private final Servo intakeRGB;
    private final Servo turretRGB;

    public RGBSubsystem(final HardwareMap hMap) {
        intakeRGB = hMap.get(Servo.class, "intakeRGB");
        turretRGB = hMap.get(Servo.class, "outtakeRGB");
    }
    private enum colors{
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        BLUE,
        INDIGO,
        VIOLET,
        WHITE
    }

    public void setToColor(Servo light, colors color) {
        switch (color) {
            case RED:
                light.setPosition(0.277);
                break;
            case ORANGE:
                light.setPosition(0.333);
                break;
            case YELLOW:
                light.setPosition(0.388);
                break;
            case GREEN:
                light.setPosition(0.480);
                break;
            case BLUE:
                light.setPosition(0.600);
                break;
            case INDIGO:
                light.setPosition(0.666);
                break;
            case VIOLET:
                light.setPosition(0.722);
                break;
            case WHITE:
                light.setPosition(1.0);
                break;
        }

    }

    public void stop() {
        intakeRGB.setPosition(0);
        turretRGB.setPosition(0);
    }

}