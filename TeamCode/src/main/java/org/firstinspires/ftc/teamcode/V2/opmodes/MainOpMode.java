package org.firstinspires.ftc.teamcode.V2.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.skeletonarmy.marrow.prompts.BooleanPrompt;
import com.skeletonarmy.marrow.prompts.OptionPrompt;
import com.skeletonarmy.marrow.prompts.Prompter;
import com.skeletonarmy.marrow.prompts.ValuePrompt;

import org.firstinspires.ftc.teamcode.V2.utils.Alliance;

@TeleOp
public class MainOpMode extends OpMode {


    private Prompter prompter = new Prompter(this);
    private enum StartingLocation
    {
        CLOSE,
        FAR
    }
    @Override
    public void init() {
        prompter.prompt("alliance", new OptionPrompt<>("Select Alliance", Alliance.RED, Alliance.BLUE))
                .prompt("startpos", new OptionPrompt<>("Select Start Position", StartingLocation.CLOSE,StartingLocation.FAR))
                .onComplete(this::onPromptsComplete);
    }

    public void onPromptsComplete() {
        Alliance alliance = prompter.get("alliance");
        StartingLocation startPos = prompter.get("startpos");

        telemetry.addData("Selected Alliance", alliance);
        telemetry.addData("Selected Starting Position", startPos);
        telemetry.update();
    }

    @Override
    public void init_loop() {
        prompter.run();
    }

    @Override
    public void loop() {
        //TODO: Implement auton logic
    }
}