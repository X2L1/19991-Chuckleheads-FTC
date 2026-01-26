package org.firstinspires.ftc.teamcode.V2.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSubsystem extends SubsystemBase {

    private MotorEx frontLeft, frontRight, backLeft, backRight;
    private MecanumDrive drive;
    private GamepadEx driverOp;

    public DriveSubsystem(final HardwareMap hMap) {
        frontLeft = new MotorEx(hMap, "frontLeft");
        frontRight = new MotorEx(hMap, "frontRight");
        backLeft = new MotorEx(hMap, "backLeft");
        backRight = new MotorEx(hMap, "backRight");

        drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
        driverOp = new GamepadEx(gamepad1); // Fix: Proper init
    }

    public void mecanumDrive() {
        driverOp.readButtons(); // Fix: Update each call

        double forward = -driverOp.getLeftY();
        double strafe = driverOp.getLeftX();
        double rotate = driverOp.getRightX();

        drive.driveRobotCentric(forward, strafe, rotate);
    }

    public void stop() {
        drive.stop();
    }
}