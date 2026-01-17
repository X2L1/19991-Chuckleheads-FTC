package org.firstinspires.ftc.teamcode.V2.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.V2.subsystems.DriveSubsystem;

public class FollowPathCommand extends CommandBase {
    private final DriveSubsystem drive;
    private final Follower follower;
    private final PathChain pathChain;

    public FollowPathCommand(DriveSubsystem drive, Follower follower, PathChain pathChain) {
        this.drive = drive;
        this.follower = follower;
        this.pathChain = pathChain;
        addRequirements(drive);
    }


    @Override
    public void execute() {
        follower.followPath(pathChain);
        follower.update();
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy();
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop(); // Stop the robot when the command ends
    }
}