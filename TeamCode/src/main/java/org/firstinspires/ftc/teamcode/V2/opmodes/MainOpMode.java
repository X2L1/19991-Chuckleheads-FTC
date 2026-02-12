package org.firstinspires.ftc.teamcode.V2.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.skeletonarmy.marrow.prompts.OptionPrompt;
import com.skeletonarmy.marrow.prompts.Prompter;

import org.firstinspires.ftc.teamcode.V2.commands.RunTeleOpBlue;
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
        robot = new Robot(hardwareMap, telemetry);

        prompter.prompt("alliance", new OptionPrompt<>("Select Alliance", Alliance.RED, Alliance.BLUE))
                .prompt("startpos", new OptionPrompt<>("Select Start Position", StartingLocation.CLOSE, StartingLocation.FAR))
                .onComplete(this::onPromptsComplete);

        while (opModeInInit()) {
            prompter.run();
        }

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();
        }
    }

    public void onPromptsComplete() {
        Alliance alliance = prompter.get("alliance");
        StartingLocation startPos = prompter.get("startpos");

        GamepadEx driver = new GamepadEx(gamepad1); // Fix: Explicit init
        GamepadEx gunner = new GamepadEx(gamepad2);

        new RunTeleOpBlue(alliance, hardwareMap, telemetry, driver, gunner).schedule();

        telemetry.addData("Selected Alliance", alliance);
        telemetry.addData("Selected Starting Position", startPos);
        telemetry.update();
    }
}