package org.firstinspires.ftc.teamcode.V2.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.skeletonarmy.marrow.prompts.OptionPrompt;
import com.skeletonarmy.marrow.prompts.Prompter;

import org.firstinspires.ftc.teamcode.V2.commands.RunTeleOp;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;

@TeleOp(name = "DecodeMasterOpMode")
public class MainOpMode extends LinearOpMode {

    private Prompter prompter = new Prompter(this);
    private Robot robot;
    private enum StartingLocation {
        CLOSE, FAR
    }

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);

        prompter.prompt("alliance", new OptionPrompt<>("Select Alliance", Alliance.RED, Alliance.BLUE))
                .prompt("startpos", new OptionPrompt<>("Select Start Position", StartingLocation.CLOSE, StartingLocation.FAR))
                .onComplete(this::onPromptsComplete);

        while (opModeInInit()) {
            prompter.run();
        }

        waitForStart();

        while (opModeIsActive()) {
            // Run teleop or auto logic based on prompts
            telemetry.update();
        }
    }

    public void onPromptsComplete() {
        Alliance alliance = prompter.get("alliance");
        StartingLocation startPos = prompter.get("startpos");

        // Schedule teleop with both gamepads
        new RunTeleOp(alliance, hardwareMap, new GamepadEx(gamepad1), new GamepadEx(gamepad2)).schedule();

        telemetry.addData("Selected Alliance", alliance);
        telemetry.addData("Selected Starting Position", startPos);
        telemetry.update();
    }
}