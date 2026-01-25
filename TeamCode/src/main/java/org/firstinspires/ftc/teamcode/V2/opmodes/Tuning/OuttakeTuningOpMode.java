package org.firstinspires.ftc.teamcode.V2.opmodes.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;

@TeleOp(name = "OuttakeTuningOpMode")
public class OuttakeTuningOpMode extends LinearOpMode {

    private Robot robot;
    private double velocity = 0.0; // Starting velocity (tune as needed)
    private double angle = 0.5; // Starting hood angle (0-1 range)
    private double tagID = 20.0; // AprilTag ID for distance (e.g., blue Goal; adjust for red if needed)
    private static final double VELOCITY_STEP = 100.0; // Increment for velocity adjustments
    private static final double ANGLE_STEP = 0.01; // Increment for hood angle adjustments

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);

        telemetry.addData("Status", "Ready for tuning. Use gamepad1 dpad: up/down for velocity, left/right for angle.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Handle gamepad inputs (debounced with sleep to prevent rapid changes)
            if (gamepad1.dpad_up) {
                velocity += VELOCITY_STEP;
            }
            if (gamepad1.dpad_down) {
                velocity -= VELOCITY_STEP;
            }
            if (gamepad1.dpad_right) {
                angle += ANGLE_STEP;
            }
            if (gamepad1.dpad_left) {
                angle -= ANGLE_STEP;
            }

            // Clamp values to safe ranges
            velocity = Math.max(0.0, velocity); // Prevent negative velocity
            angle = Math.max(0.0, Math.min(1.0, angle)); // Servo position range

            // Apply settings to outtake
            robot.outtake.setHoodAngle(angle);
            robot.outtake.runAtVelocity(velocity);

            // Display telemetry
            telemetry.addData("Distance from AprilTag", robot.limelight.distanceFromTag(tagID));
            telemetry.addData("Hood Angle", angle);
            telemetry.addData("Outtake Velocity", velocity);
            telemetry.update();

            sleep(20); // Short delay for debounce and loop stability
        }

        // Stop outtake on end
        robot.outtake.stop();
    }
}