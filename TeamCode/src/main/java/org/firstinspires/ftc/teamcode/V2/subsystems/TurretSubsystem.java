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
    private final CRServo leftServo;
    private final CRServo rightServo;


    public TurretSubsystem(HardwareMap hardwareMap) {
        leftServo = hardwareMap.get(CRServo.class, "leftTurret");
        rightServo = hardwareMap.get(CRServo.class, "rightTurret");
    }
    public void rotate(double power)
    {
        setTargetPower(power);
    }
    public void setTargetPower(double power) {
        leftServo.setPower(power);
        rightServo.setPower(power);
    }
}