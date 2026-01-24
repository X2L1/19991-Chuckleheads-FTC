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
    double KP = Configurables.turretP; // Tune for proportional control (simple speed based on offset)

    public AimTurret(TurretSubsystem turret, LimelightSubsystem limelight, Alliance alliance) {
        this.turret = turret;
        this.limelight = limelight;
        this.alliance = alliance;

        addRequirements(turret, limelight);
    }

    @Override
    public void execute() {
        double KP = Configurables.turretP; // Tune for proportional control (simple speed based on offset)
        if (turret.manualOverride) {
            return; // Skip auto-aim during manual override
        }

        if (limelight.hasTarget()) {
            int tagID = (alliance == Alliance.BLUE) ? 20 : 24; // Adjust IDs for DECODE Goals
            double offset = limelight.getXDegrees(tagID);
            double power = KP * offset; // Simple proportional: rotate faster for larger offset
            turret.setPower(power);
        } else {
            turret.setPower(0.0); // No target, stop
        }
    }

    @Override
    public boolean isFinished() {
        return false; // Continuous aiming until canceled
    }

    @Override
    public void end(boolean interrupted) {
        turret.stop();
    }
}