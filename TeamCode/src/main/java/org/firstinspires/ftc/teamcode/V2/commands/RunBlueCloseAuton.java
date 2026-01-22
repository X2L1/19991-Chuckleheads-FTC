package org.firstinspires.ftc.teamcode.V2.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.V2.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;
import org.firstinspires.ftc.teamcode.V2.utils.Paths.BlueCloseTotalPath;
import org.firstinspires.ftc.teamcode.V2.utils.ZoneUtils;

public class RunBlueCloseAuton extends ParallelCommandGroup {

    private final Robot robot;
    private final ZoneUtils zoneUtils;
    private final Follower follower;
    private final Alliance alliance = Alliance.BLUE;

    public RunBlueCloseAuton(Robot robot, Follower follower) {
        this.robot = robot;
        this.follower = follower;
        this.zoneUtils = new ZoneUtils(robot.hMap);

        zoneUtils.initZone();

        BlueCloseTotalPath pathChainBuilder = new BlueCloseTotalPath(follower);
        PathChain fullChain = pathChainBuilder.fullChain;

        addCommands(new FollowPathCommand(robot.drive, follower, fullChain));
        addCommands(new AutoShoot(alliance, robot.hMap));
        addCommands(new PerpetualCommand(new AutoIntakeCommand(robot.intake)));

        addRequirements(robot.drive, robot.intake, robot.turret, robot.outtake, robot.transfer, robot.limelight);
    }

    @Override
    public void execute() {
        zoneUtils.syncZonesWithPositions();
        super.execute();
    }
}