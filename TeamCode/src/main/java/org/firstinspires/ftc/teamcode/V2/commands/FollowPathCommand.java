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
    public void initialize() {
        follower.followPath(pathChain); // Start following only once
    }

    @Override
    public void execute() {
        follower.update();
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy(); // Use correct method from Pedro Pathing
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}