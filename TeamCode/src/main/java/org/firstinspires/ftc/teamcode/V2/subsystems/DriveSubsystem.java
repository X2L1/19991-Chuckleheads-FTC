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

        drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
    }

    public void mecanumDrive(double forward, double strafe, double rotate) {
        drive.driveRobotCentric(forward, strafe, rotate);
    }

    public void stop() {
        drive.stop();
    }
}