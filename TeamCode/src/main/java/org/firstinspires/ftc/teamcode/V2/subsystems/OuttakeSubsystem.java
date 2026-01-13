package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.V2.utils.Configurables;


public class OuttakeSubsystem extends SubsystemBase {

    private final DcMotorEx outtakeLeft;
    private final DcMotorEx outtakeRight;
    private final Servo hood;

    public OuttakeSubsystem(final HardwareMap hMap) {
        outtakeLeft = hMap.get(DcMotorEx.class, "outtakeLeft");
        outtakeRight = hMap.get(DcMotorEx.class, "outtakeRight");
        hood = hMap.get(Servo.class, "hood");
        outtakeLeft.setVelocityPIDFCoefficients(Configurables.outtakeP, Configurables.outtakeI, Configurables.outtakeD, Configurables.outtakeF);
        outtakeRight.setVelocityPIDFCoefficients(Configurables.outtakeP, Configurables.outtakeI, Configurables.outtakeD, Configurables.outtakeF);
    }


    public void runAtVelocity(double velocity) {
        outtakeLeft.setVelocity(velocity);
        outtakeRight.setVelocity(velocity);
    }
    public void setHoodAngle(double angle)
    {
        hood.setPosition(angle);
    }


    public void stop() {
        outtakeLeft.setPower(0);
        outtakeRight.setPower(0);
    }

}