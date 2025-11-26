package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class MecanumChassis {

    private DcMotorEx leftFront;
    private DcMotorEx rightFront;
    private DcMotorEx leftRear;
    private DcMotorEx rightRear;

    // Constructor to initialize hardware
    public MecanumChassis(HydraHardware hardware) {
        this.leftFront = hardware.leftFront;
        this.rightFront = hardware.rightFront;
        this.leftRear = hardware.leftRear;
        this.rightRear = hardware.rightRear;
    }

    // Expand the lead screws (full speed forward)
    public void setVelocity(double drive, double strafe, double turn) {
        leftFront.setVelocity(drive + turn + strafe);
        rightFront.setVelocity(drive - turn - strafe);
        rightRear.setVelocity(drive - turn + strafe);
        leftRear.setVelocity(drive + turn - strafe);
    }

    // Retract the lead screws (full speed reverse)

}
