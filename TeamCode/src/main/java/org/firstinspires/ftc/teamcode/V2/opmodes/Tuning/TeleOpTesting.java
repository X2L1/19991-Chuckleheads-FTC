package org.firstinspires.ftc.teamcode.V2.opmodes.Tuning;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.V2.commands.RunTeleOp;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;

@TeleOp(name = "TeleOpTesting")
public class TeleOpTesting extends LinearOpMode {

    private Robot robot;
    private RunTeleOp runTeleOp;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);

        // Assuming default alliance as BLUE; adjust if needed or integrate Prompter
        runTeleOp = new RunTeleOp(Alliance.BLUE, hardwareMap, new GamepadEx(gamepad1), new GamepadEx(gamepad2));

        telemetry.addData("Status", "Ready for TeleOp testing");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            runTeleOp.execute();
            telemetry.update();
        }

        runTeleOp.end(false); // Clean up on end
    }
}