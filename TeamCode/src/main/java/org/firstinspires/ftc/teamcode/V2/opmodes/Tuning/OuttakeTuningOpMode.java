package org.firstinspires.ftc.teamcode.V2.opmodes.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;

@TeleOp(name = "OuttakeTuningOpMode")
public class OuttakeTuningOpMode extends LinearOpMode {

    private Robot robot;
    private double velocity = 0.0;
    private double angle = 0.5;
    private double tagID = 20.0;
    private static final double VELOCITY_STEP = 100.0;
    private static final double ANGLE_STEP = 0.01;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap, telemetry); // Pass HardwareMap

        telemetry.addData("Status", "Ready for tuning. Use gamepad1 dpad: up/down for velocity, left/right for angle.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) velocity += VELOCITY_STEP;
            if (gamepad1.dpad_down) velocity -= VELOCITY_STEP;
            if (gamepad1.dpad_right) angle += ANGLE_STEP;
            if (gamepad1.dpad_left) angle -= ANGLE_STEP;

            velocity = Math.max(0.0, velocity);
            angle = Math.max(0.0, Math.min(1.0, angle));

            robot.outtake.setHoodAngle(angle);
            robot.outtake.runAtVelocity(velocity);

            telemetry.addData("Distance from AprilTag", robot.limelight.distanceFromTag(tagID));
            telemetry.addData("Hood Angle", angle);
            telemetry.addData("Outtake Velocity", velocity);
            telemetry.update();

            sleep(20);
        }

        robot.outtake.stop();
    }
}