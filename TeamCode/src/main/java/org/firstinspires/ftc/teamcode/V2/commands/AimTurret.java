package org.firstinspires.ftc.teamcode.V2.commands;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.V2.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TurretSubsystem;


public class AimTurret extends CommandBase {

    // The subsystem the command runs on
    private final TurretSubsystem turret;
    private final LimelightSubsystem limelight;
    public AimTurret(TurretSubsystem turret, LimelightSubsystem limelight) {
        this.turret = turret;
        this.limelight = limelight;
        addRequirements(turret, limelight);
    }

    @Override
    public void execute() {
        if(limelight.hasTarget() == false || gamepad2.left_trigger > 0.1)
        {
            turret.aimAtGoal(gamepad2.right_stick_x, gamepad2.right_stick_x);
        }
        else
        {
            turret.aimAtGoal(limelight.getTx(), 0.3);
        }

    }

    @Override
    public boolean isFinished() {
        return true;
    }

}