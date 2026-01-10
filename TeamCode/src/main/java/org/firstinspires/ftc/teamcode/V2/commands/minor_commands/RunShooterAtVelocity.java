package org.firstinspires.ftc.teamcode.V2.commands.minor_commands;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.OuttakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TurretSubsystem;


public class RunShooterAtVelocity extends InstantCommand {

    // The subsystem the command runs on
    private final OuttakeSubsystem shooter;
    private int velocity;
    public RunShooterAtVelocity(OuttakeSubsystem shooter, int velocity) {
        this.shooter = shooter;
        this.velocity = velocity;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.runAtVelocity(velocity);
    }


}
