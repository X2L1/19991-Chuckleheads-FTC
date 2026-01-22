package org.firstinspires.ftc.teamcode.V2.commands;

import static org.firstinspires.ftc.teamcode.V2.utils.Alliance.*;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.V2.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;

public class AimTurret extends InstantCommand {

    private final TurretSubsystem turret;
    private final LimelightSubsystem limelight;
    private final Alliance alliance;

    public AimTurret(TurretSubsystem turret, LimelightSubsystem limelight, Alliance alliance) {
        this.turret = turret;
        this.limelight = limelight;
        this.alliance = alliance;

        addRequirements(turret, limelight);
    }

    @Override
    public void execute() {
        if (limelight.hasTarget()) {
            double degrees = (alliance == BLUE) ? limelight.getXDegrees(20) : limelight.getXDegrees(24);
            turret.setTargetPower(degrees);
        } else {
            turret.setTargetPower(0.0); // Default to 0 power if no target
        }
    }
}