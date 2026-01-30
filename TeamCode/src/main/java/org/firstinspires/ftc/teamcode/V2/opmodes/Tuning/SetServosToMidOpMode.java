package org.firstinspires.ftc.teamcode.V2.opmodes.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;

@TeleOp(name = "SetServosToMidOpMode")
public class SetServosToMidOpMode extends LinearOpMode {

    private Robot robot;
    private static final double MID_POSITION = 0.5;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap, telemetry);

        telemetry.addData("Status", "Press START to set all servos to 0.5.");
        telemetry.update();

        waitForStart();

        // Set outtake hood servos
        robot.outtake.setHoodAngle(MID_POSITION);

        // Set turret (position mode)
        robot.turret.setPosition(MID_POSITION);

        // Set transfer blocker if uncommented/enabled
        // robot.transfer.blocker.setPosition(MID_POSITION); // Uncomment if blocker active

        telemetry.addData("Status", "All servos set to 0.5. OpMode complete.");
        telemetry.update();

        while (opModeIsActive()) {
            idle(); // Keep running until stop
        }
    }
}