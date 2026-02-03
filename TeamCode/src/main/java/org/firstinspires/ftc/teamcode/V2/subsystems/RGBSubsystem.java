package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class RGBSubsystem extends SubsystemBase {

    public final Servo rgb;

    public RGBSubsystem(final HardwareMap hMap) {
        rgb = hMap.get(Servo.class, "RGB");
    }
    public enum colors{
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        BLUE,
        INDIGO,
        VIOLET,
        WHITE
    }

    public void setToColor(colors color) {
        switch (color) {
            case RED:
                rgb.setPosition(0.277);
                break;
            case ORANGE:
                rgb.setPosition(0.333);
                break;
            case YELLOW:
                rgb.setPosition(0.388);
                break;
            case GREEN:
                rgb.setPosition(0.480);
                break;
            case BLUE:
                rgb.setPosition(0.600);
                break;
            case INDIGO:
                rgb.setPosition(0.666);
                break;
            case VIOLET:
                rgb.setPosition(0.722);
                break;
            case WHITE:
                rgb.setPosition(1.0);
                break;
        }

    }

    public void stop() {
        rgb.setPosition(0);
    }

}