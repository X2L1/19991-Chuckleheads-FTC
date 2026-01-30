package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.V2.utils.Configurables;


public class OuttakeSubsystem extends SubsystemBase {

    private final DcMotorEx outtakeLeft;
    private final DcMotorEx outtakeRight;
    private final Servo leftHood;
    private final Servo rightHood;

    public OuttakeSubsystem(final HardwareMap hMap) {
        outtakeLeft = hMap.get(DcMotorEx.class, "outtakeLeft");
        outtakeRight = hMap.get(DcMotorEx.class, "outtakeRight");
        rightHood = hMap.get(Servo.class, "rightHood");
        leftHood = hMap.get(Servo.class, "leftHood");
        leftHood.setDirection(Servo.Direction.REVERSE);
        outtakeLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        outtakeRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }


    public void runAtVelocity(double velocity) {
        //feedforward = targetRPM / maxRPM;
        //P controller = kP(target RPM - current RPM);
        //kP is tuned
        // feedforward + P controller
        //Increase max if too low, decrease if too high
        //Start P at 0.0000000001. Remove zeroes until something happens. Tune from there.
        runAtPower((velocity/5800) + (Configurables.outtakeP * (velocity - outtakeLeft.getVelocity())));
    }
    public void runAtPower(double power)
    {
        outtakeLeft.setPower(power);
        outtakeRight.setPower(power);
    }
    public void setHoodAngle(double angle)
    {
        rightHood.setPosition(angle);
        leftHood.setPosition(angle);
    }


    public void stop() {
        outtakeLeft.setPower(0);
        outtakeRight.setPower(0);
    }

}