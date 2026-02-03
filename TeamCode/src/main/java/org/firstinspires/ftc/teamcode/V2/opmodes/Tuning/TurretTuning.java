package org.firstinspires.ftc.teamcode.V2.opmodes.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;

@TeleOp(name = "TurretTuning", group = "Tuners")
public class TurretTuning extends LinearOpMode {

    private Robot robot;
    private double position = 0.5;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap, telemetry); // Pass HardwareMap

        telemetry.addData("Status", "Ready for tuning. Use gamepad1 dpad: up/down for position");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) position += .01;
            if (gamepad1.dpad_down) position -= .01;

            robot.turret.setPosition(position);

            telemetry.addData("Position", position);
            telemetry.update();

            sleep(20);
        }
    }
}