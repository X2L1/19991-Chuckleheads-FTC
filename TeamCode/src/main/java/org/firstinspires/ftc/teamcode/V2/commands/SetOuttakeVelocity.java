package org.firstinspires.ftc.teamcode.V2.commands;

import static org.firstinspires.ftc.teamcode.V2.utils.Alliance.*;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.OuttakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;
import org.firstinspires.ftc.teamcode.V2.utils.OuttakeLUTs;


public class SetOuttakeVelocity extends InstantCommand {

    // The subsystem the command runs on
    private final OuttakeSubsystem outtake;
    private final LimelightSubsystem limelight;
    OuttakeLUTs luts;
    Alliance alliance = BLUE;
    public SetOuttakeVelocity(OuttakeSubsystem outtake, LimelightSubsystem limelight, Alliance alliance) {
        this.outtake = outtake;
        this.limelight = limelight;
        this.alliance = alliance;

        addRequirements(outtake, limelight);
    }


    @Override
    public void execute() {
        if(alliance == BLUE)
        {
            outtake.runAtVelocity(luts.getSpeed(limelight.distanceFromTag(20)));
            outtake.setHoodAngle(luts.getAngle(limelight.distanceFromTag(20)));
        }
        else if(alliance == RED)
        {
            outtake.runAtVelocity(luts.getSpeed(limelight.distanceFromTag(24)));
            outtake.setHoodAngle(luts.getAngle(limelight.distanceFromTag(24)));
        }

    }


}
