package org.firstinspires.ftc.teamcode.V2.opmodes.Tuning;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;

@TeleOp(name = "TurretKpTuningOpMode")
public class TurretKpTuningOpMode extends LinearOpMode {

    private Robot robot;
    private GamepadEx gunner;

    private double kP = 0.012;
    private static final double KP_STEP_COARSE = 0.002;
    private static final double KP_STEP_FINE = 0.0005;
    private static final int TAG_ID = 20;
    private static final double MANUAL_DEADZONE = 0.12;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap, telemetry);
        gunner = new GamepadEx(gamepad2); // Fix: Explicit init from gamepad2

        telemetry.addLine("=== Turret kP Tuning ===");
        telemetry.addLine("D-Pad Up/Down → Coarse kP (±0.002)");
        telemetry.addLine("D-Pad Left/Right → Fine kP (±0.0005)");
        telemetry.addLine("Gunner Left Stick X → Manual override");
        telemetry.addLine("Aim Limelight at Goal (tag " + TAG_ID + ")");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            gunner.readButtons(); // Fix: Update state each loop

            if (gamepad1.dpad_up) kP += KP_STEP_COARSE;
            if (gamepad1.dpad_down) kP -= KP_STEP_COARSE;
            if (gamepad1.dpad_right) kP += KP_STEP_FINE;
            if (gamepad1.dpad_left) kP -= KP_STEP_FINE;

            kP = Math.max(0.0, Math.min(0.12, kP));

            double turretPower = 0.0;
            boolean manualActive = false;

            double manualInput = gunner.getLeftX();
            if (Math.abs(manualInput) > MANUAL_DEADZONE) {
                manualActive = true;
                turretPower = manualInput;
            } else if (robot.limelight.hasTarget()) {
                double offset = robot.limelight.getXDegrees(TAG_ID);
                turretPower = kP * offset;
                if (Math.abs(offset) < 1.5) turretPower = 0.0;
            }

            robot.turret.setPower(turretPower);
            robot.turret.manualOverride = manualActive;

            telemetry.addData("kP", String.format("%.5f", kP));
            if (robot.limelight.hasTarget()) {
                double tx = robot.limelight.getXDegrees(TAG_ID);
                telemetry.addData("Offset (tx)", String.format("%.2f°", tx));
                telemetry.addData("Power", String.format("%.3f", turretPower));
            } else {
                telemetry.addData("Offset (tx)", "No Target");
                telemetry.addData("Power", "0.000");
            }
            telemetry.addData("Manual Override", manualActive ? "ACTIVE (LeftStickX)" : "Auto");
            telemetry.addData("Distance", String.format("%.1f in", robot.limelight.distanceFromTag(TAG_ID)));
            telemetry.update();

            sleep(25);
        }

        robot.turret.stop();
    }
}