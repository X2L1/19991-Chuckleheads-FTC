package org.firstinspires.ftc.teamcode.V2.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.V2.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;
import org.firstinspires.ftc.teamcode.V2.utils.Configurables;

public class AimTurret extends CommandBase {

    private final TurretSubsystem turret;
    private final LimelightSubsystem limelight;
    private final Alliance alliance;
    private static final double KP = Configurables.turretP; // Proportional gain for position adjustment (tune)
    private static final double OFFSET_TOLERANCE = 1.5; // Degrees to consider aligned

    public AimTurret(TurretSubsystem turret, LimelightSubsystem limelight, Alliance alliance) {
        this.turret = turret;
        this.limelight = limelight;
        this.alliance = alliance;

        addRequirements(turret, limelight);
    }

    @Override
    public void execute() {
        if (turret.manualOverride) {
            return; // Skip during presets/manual
        }

        if (limelight.hasTarget()) {
            int tagID = (alliance == Alliance.BLUE) ? 20 : 24; // DECODE Goal tags
            double offset = limelight.getXDegrees(tagID);
            if (Math.abs(offset) > OFFSET_TOLERANCE) {
                double currentPos = turret.getPosition();
                double adjustment = KP * offset; // Proportional to offset
                turret.setPosition(currentPos + adjustment);
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false; // Continuous until canceled
    }

    @Override
    public void end(boolean interrupted) {
        // Hold last position
    }
}