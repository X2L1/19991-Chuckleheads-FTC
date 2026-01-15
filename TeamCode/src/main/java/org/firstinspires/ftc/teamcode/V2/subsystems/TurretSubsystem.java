package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.teamcode.V2.utils.Configurables;

public class TurretSubsystem extends SubsystemBase {
    private final Servo leftServo;
    private final Servo rightServo;

    // Constants (tune based on testing)
    private double maxPositionClockwise = 1.0; // Max servo position clockwise
    private double maxPositionCounterClockwise = 0.0; // Max servo position counter

    public TurretSubsystem(HardwareMap hardwareMap) {
        leftServo = hardwareMap.get(Servo.class, "leftTurretServo");
        rightServo = hardwareMap.get(Servo.class, "rightTurretServo");
    }
    public void rotate(double power)
    {
        setTargetPositions(power*Configurables.turretP);
    }
    public void setTargetPositions(double desiredAngle) {
        leftServo.setPosition(desiredAngle);
        rightServo.setPosition(desiredAngle);
    }
}