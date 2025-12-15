package org.firstinspires.ftc.teamcode.generated;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;



@Configurable public class OuttakeSubsystem {

    private DcMotorEx outtakeLeft;
    private DcMotorEx outtakeCenter;
    private DcMotorEx outtakeRight;

    private Servo outerHood;
    //private Servo angleServoRight;

    private GoBildaPinpointDriver pinpoint; // Odometry device



    // Constructor
    public OuttakeSubsystem(HydraHardware hardware) {
        this.outtakeLeft = hardware.outtakeLeft;
        this.outtakeCenter = hardware.outtakeCenter;
        this.outtakeRight = hardware.outtakeRight;

        this.outerHood = hardware.outerHood;

        this.pinpoint = hardware.pinpoint;
        outtakeLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        outtakeCenter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        outtakeRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


    }


    // Set shooter velocity
    public void setVelocity(double velocity) {
        outtakeLeft.setVelocity(velocity);
        outtakeCenter.setVelocity(velocity);
        outtakeRight.setVelocity(velocity); // Assumes positive velocity works with reversed direction
    }
    public double blueVelocity(HuskyLensTester huskyLensTester)
    {
        return 6.7 * huskyLensTester.getDistance(1) + 1126;
    }
    public double redVelocity(HuskyLensTester huskyLensTester)
    {
        return 6.7*huskyLensTester.getDistance(2) + 1126;
    }

    // Set shooter angle (assuming both servos move together; adjust if one needs inversion)
    public void setAngle(double angle) {
        outerHood.setPosition(angle);

    }
    // In OuttakeSubsystem.java

    public void setPIDFCoefficients(double p, double i, double d, double f) {
        outtakeLeft.setVelocityPIDFCoefficients(p, i, d, f);
        outtakeCenter.setVelocityPIDFCoefficients(p, i, d, f);
        outtakeRight.setVelocityPIDFCoefficients(p, i, d, f);
    }




    // Stop the shooter
    public void stop() {
        setVelocity(0.0);
    }


    }



