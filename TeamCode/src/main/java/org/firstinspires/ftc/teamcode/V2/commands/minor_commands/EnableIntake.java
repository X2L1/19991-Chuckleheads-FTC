package org.firstinspires.ftc.teamcode.V2.commands.minor_commands;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TurretSubsystem;


public class EnableIntake extends InstantCommand {

    // The subsystem the command runs on
    private final IntakeSubsystem intake;
    public EnableIntake(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.run();
    }


}