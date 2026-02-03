package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSubsystem extends SubsystemBase {

    private MotorEx frontLeft, frontRight, backLeft, backRight;
    private MecanumDrive drive;

    public DriveSubsystem(final HardwareMap hMap) {
        frontLeft = new MotorEx(hMap, "frontLeft");
        frontRight = new MotorEx(hMap, "frontRight");
        backLeft = new MotorEx(hMap, "backLeft");
        backRight = new MotorEx(hMap, "backRight");

    }

    public void mecanumDrive(double forward, double strafe, double rotate) {
        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1);
        double frontLeftPower = (forward + strafe + rotate) / denominator;
        double backLeftPower = (forward - strafe + rotate) / denominator;
        double frontRightPower = (forward - strafe - rotate) / denominator;
        double backRightPower = (forward + strafe - rotate) / denominator;

        frontLeft.set(-frontLeftPower);
        backLeft.set(backLeftPower);
        frontRight.set(frontRightPower);
        backRight.set(-backRightPower);
    }

    public void stop() {
        frontLeft.stopMotor();
        frontRight.stopMotor();
        backLeft.stopMotor();
        backRight.stopMotor();
    }
}