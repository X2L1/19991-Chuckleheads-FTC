package org.firstinspires.ftc.teamcode.Hydra;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "OuttakeVelocityTest", group = "Test")
public class OuttakeTesting extends LinearOpMode {

    private HydraHardware hardware;
    private OuttakeSubsystem outtakeSubsystem;
    private IndexerSubsystem indexerSubsystem;
    private OdometryUtils odometryUtils;
    private HuskyLensTester huskyLens;

    private double targetVelocity = 0.0; // Starting velocity (ticks per second)
    private double velocityStep = 10.0;
    private double velocityStep2 = 5.0;// Amount to adjust velocity per button press

    private boolean lastDpadLeft = false;
    private boolean lastDpadRight = false;
    private boolean lastDpadUp = false;

    private boolean centerKicked = false;
    private ElapsedTime kickTimer = new ElapsedTime();
    private double kickDuration = 0.5; // Seconds to hold kick position before resetting

    @Override
    public void runOpMode() {
        // Initialize hardware and subsystems
        hardware = new HydraHardware();
        hardware.init(hardwareMap);
        huskyLens = new HuskyLensTester(hardware.huskyLens);
        outtakeSubsystem = new OuttakeSubsystem(hardware);
        indexerSubsystem = new IndexerSubsystem(hardware);
        odometryUtils = new OdometryUtils(hardware.pinpoint);

        // Set shooter motors to velocity control mode
        hardware.outtakeLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.outtakeCenter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.outtakeRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Optional: Set a default shooter angle (adjust as needed)
        outtakeSubsystem.setAngle(0.5); // Assuming 0.5 is a mid-position; calibrate

        telemetry.addData("Status", "Initialized - Use dpad left/right to adjust velocity, dpad up to kick center");
        telemetry.update();
        //outtakeSubsystem.setPIDFCoefficients(PIDFCoefficients.getOuttakeP(), PIDFCoefficients.getOuttakeI(), PIDFCoefficients.getOuttakeD(), PIDFCoefficients.getOuttakeF());
        waitForStart();

        while (opModeIsActive()) {
            odometryUtils.update(); // Update odometry for position telemetry

            // Adjust target velocity with dpad left/right (debounced)
            boolean currentDpadLeft = gamepad1.dpad_left;
            if (currentDpadLeft && !lastDpadLeft) {
                targetVelocity -= velocityStep;
                if (targetVelocity < 0) targetVelocity = 0; // Prevent negative velocity
            }
            lastDpadLeft = currentDpadLeft;

            boolean currentDpadRight = gamepad1.dpad_right;
            if (currentDpadRight && !lastDpadRight) {
                targetVelocity += velocityStep;
                if(gamepad1.right_trigger > 0.1)
                {
                    targetVelocity += velocityStep2;
                    sleep(100);
                }
            }
            lastDpadRight = currentDpadRight;

            // Set the shooter to the target velocity
            outtakeSubsystem.setVelocity(targetVelocity);

            // Kick the center chamber (index 1) with dpad up (debounced)
            boolean currentDpadUp = gamepad1.dpad_up;
            if (currentDpadUp && !lastDpadUp && !centerKicked) {
                indexerSubsystem.kickChamber(1); // Kick center chamber
                centerKicked = true;
                kickTimer.reset();
            }
            lastDpadUp = currentDpadUp;

            // Reset the center chamber servo after kick duration
            if (centerKicked && kickTimer.seconds() > kickDuration) {
                indexerSubsystem.resetChamber(1);
                centerKicked = false;
            }

            // Telemetry
            telemetry.addData("Target Velocity", "%.0f ticks/s", targetVelocity);
            telemetry.addData("Actual Velocity (Center)", "%.0f ticks/s", hardware.outtakeCenter.getVelocity());
            telemetry.addData("Robot Pose", odometryUtils.getPose().toString());
            telemetry.addData("Distance from goal", "%.2f mm", odometryUtils.getDistanceFromPoint(3352.8, 1524)); // Example goal position
            telemetry.addData("Distance from tag: %2f", huskyLens.getDistance(1));
            telemetry.update();
        }

        // Stop shooter on opmode end
        outtakeSubsystem.stop();
        indexerSubsystem.resetAll();
    }
}