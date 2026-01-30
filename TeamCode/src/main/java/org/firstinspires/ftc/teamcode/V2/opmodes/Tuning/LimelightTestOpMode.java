package org.firstinspires.ftc.teamcode.V2.opmodes.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;

@TeleOp(name = "LimelightTestOpMode")
public class LimelightTestOpMode extends LinearOpMode {

    private Robot robot;
    private static final int TEST_TAG_ID = 20; // Blue Goal; change for red (24)

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap, telemetry);

        telemetry.addData("Status", "Limelight Test Ready. Point at Goal AprilTag.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            boolean hasTarget = robot.limelight.hasTarget();
            telemetry.addData("Has Target", hasTarget);

            if (hasTarget) {
                telemetry.addData("TX (X Degrees)", robot.limelight.getTx());
                telemetry.addData("TY (Y Degrees)", robot.limelight.getTy());
                telemetry.addData("X Degrees to Tag", robot.limelight.getXDegrees(TEST_TAG_ID));
                double distance = robot.limelight.distanceFromTag(TEST_TAG_ID);
                telemetry.addData("Distance to Tag", distance);
                if (distance == 0.0) {
                    telemetry.addData("Debug", "3D Pose invalid; using TY fallback. Check calibration/multiple tags.");
                }

                Pose3D pose = robot.limelight.getPose();
                if (pose != null) {
                    telemetry.addData("Bot Pose Heading", pose.getOrientation().getYaw());
                    telemetry.addData("Raw Pose X (m)", pose.getPosition().x);
                    telemetry.addData("Raw Pose Z (m)", pose.getPosition().z);
                } else {
                    telemetry.addData("Bot Pose", "Not Available - Enable MegaTag or calibrate");
                }
            } else {
                telemetry.addData("TX/TY/X Degrees/Distance/Pose", "No Target Detected");
            }

            telemetry.update();
            sleep(50); // 20Hz refresh
        }
    }
}