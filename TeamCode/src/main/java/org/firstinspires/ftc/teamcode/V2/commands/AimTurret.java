package org.firstinspires.ftc.teamcode.V2.commands;

import static org.firstinspires.ftc.teamcode.V2.utils.Alliance.*;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.OuttakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;
import org.firstinspires.ftc.teamcode.V2.utils.OuttakeLUTs;


public class AimTurret extends InstantCommand {

    // The subsystem the command runs on
    private final TurretSubsystem turret;
    private final LimelightSubsystem limelight;
    Alliance alliance = BLUE;
    public AimTurret(TurretSubsystem turret, LimelightSubsystem limelight, Alliance alliance) {
        this.turret = turret;
        this.limelight = limelight;
        this.alliance = alliance;

        addRequirements(turret, limelight);
    }


    @Override
    public void execute() {
        if(alliance == BLUE)
        {
            turret.rotate(limelight.getXDegrees(20));
        }
        else if(alliance == RED)
        {
            turret.rotate(limelight.getXDegrees(24));
        }

    }


}