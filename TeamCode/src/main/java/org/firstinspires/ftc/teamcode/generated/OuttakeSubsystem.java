package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;



public class OuttakeSubsystem {

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



    }


    // Set shooter velocity
    public void setVelocity(double velocity) {
        outtakeLeft.setVelocity(velocity);
        outtakeCenter.setVelocity(velocity);
        outtakeRight.setVelocity(velocity); // Assumes positive velocity works with reversed direction
    }

    // Set shooter angle (assuming both servos move together; adjust if one needs inversion)
    public void setAngle(double angle) {
        outerHood.setPosition(angle);

    }

    // Stop the shooter
    public void stop() {
        setVelocity(0.0);
    }


    }



