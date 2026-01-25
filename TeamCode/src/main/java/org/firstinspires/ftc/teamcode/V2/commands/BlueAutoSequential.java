package org.firstinspires.ftc.teamcode.V2.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.V2.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.OuttakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.V2.subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.V2.utils.ZoneUtils;

public class BlueAutoSequential extends SequentialCommandGroup {

    private final Robot robot;
    private final Follower follower;
    private final ZoneUtils zoneUtils;

    public BlueAutoSequential(Robot robot, Follower follower) {
        this.robot = robot;
        this.follower = follower;
        this.zoneUtils = new ZoneUtils(robot.hMap); // Assuming hMap in Robot

        zoneUtils.initZone();

        // Build path segments for cycles (based on CloseBluePaths12Ball structure)
        PathChain initialPushback = buildInitialPushback();
        PathChain intakeMiddle = buildIntakeMiddle();
        PathChain moveToGate = buildMoveToGate();
        PathChain openGate = buildOpenGate();
        PathChain shootMiddle = buildShootMiddle();
        PathChain intakeClose = buildIntakeClose();
        PathChain shootClose = buildShootClose();
        PathChain intakeFar = buildIntakeFar();
        PathChain shootFar = buildShootFar();
        PathChain park = buildPark();

        // Top-level sequential with perpetual intake and shooter spin
        addCommands(
                new ParallelCommandGroup(
                        new PerpetualCommand(new ContinuousIntakeCommand(robot.intake)),
                        new PerpetualCommand(new ContinuousShooterCommand(robot.outtake)),
                        new SequentialCommandGroup(
                                // Cycle 1: Initial to Middle
                                new FollowPathCommand(robot.drive, follower, initialPushback),
                                new FollowPathCommand(robot.drive, follower, intakeMiddle),
                                new FollowPathCommand(robot.drive, follower, moveToGate),
                                new FollowPathCommand(robot.drive, follower, openGate),
                                new WaitUntilCommand(zoneUtils::isInsideLaunchZone),
                                new ShootCommand(robot.transfer, robot.turret), // Shoot at end of cycle

                                // Cycle 2: To Close
                                new FollowPathCommand(robot.drive, follower, shootMiddle), // Reposition if needed
                                new FollowPathCommand(robot.drive, follower, intakeClose),
                                new FollowPathCommand(robot.drive, follower, shootClose),
                                new WaitUntilCommand(zoneUtils::isInsideLaunchZone),
                                new ShootCommand(robot.transfer, robot.turret),

                                // Cycle 3: To Far
                                new FollowPathCommand(robot.drive, follower, intakeFar),
                                new FollowPathCommand(robot.drive, follower, shootFar),
                                new WaitUntilCommand(zoneUtils::isInsideLaunchZone),
                                new ShootCommand(robot.transfer, robot.turret),

                                // Park
                                new FollowPathCommand(robot.drive, follower, park)
                        )
                )
        );

        addRequirements(robot.drive, robot.intake, robot.outtake, robot.transfer, robot.turret);
    }

    // Path building methods (extracted from CloseBlueFullAutoChain)
    private PathChain buildInitialPushback() {
        return follower.pathBuilder()
                .addPath(new BezierLine(new Pose(20.415, 122.583), new Pose(60.033, 83.209)))
                .setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(143))
                .build();
    }

    private PathChain buildIntakeMiddle() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(60.033, 83.209), new Pose(55.750, 58.359), new Pose(16.359, 59.906)))
                .setTangentHeadingInterpolation()
                .build();
    }

    private PathChain buildMoveToGate() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(16.359, 59.906), new Pose(27.284, 65.782), new Pose(18.654, 69.759)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))
                .build();
    }

    private PathChain buildOpenGate() {
        return follower.pathBuilder()
                .addPath(new BezierLine(new Pose(18.654, 69.759), new Pose(15.435, 69.722)))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();
    }

    private PathChain buildShootMiddle() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(15.435, 69.722), new Pose(40.671, 67.750), new Pose(59.570, 83.129)))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(143))
                .build();
    }

    private PathChain buildIntakeClose() {
        return follower.pathBuilder()
                .addPath(new BezierLine(new Pose(59.570, 83.129), new Pose(14.946, 82.557)))
                .setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(180))
                .build();
    }

    private PathChain buildShootClose() {
        return follower.pathBuilder()
                .addPath(new BezierLine(new Pose(14.946, 82.557), new Pose(59.277, 82.843)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(143))
                .build();
    }

    private PathChain buildIntakeFar() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(59.277, 82.843), new Pose(79.652, 30.661), new Pose(13.099, 35.041)))
                .setTangentHeadingInterpolation()
                .build();
    }

    private PathChain buildShootFar() {
        return follower.pathBuilder()
                .addPath(new BezierLine(new Pose(13.099, 35.041), new Pose(59.409, 82.679)))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(143))
                .build();
    }

    private PathChain buildPark() {
        return follower.pathBuilder()
                .addPath(new BezierLine(new Pose(59.409, 82.679), new Pose(18.616, 69.629)))
                .setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(90))
                .build();
    }



    // Helper command for continuous intake
    private class ContinuousIntakeCommand extends CommandBase {
        private final IntakeSubsystem intake;
        private static final double INTAKE_POWER = 1.0;

        public ContinuousIntakeCommand(IntakeSubsystem intake) {
            this.intake = intake;
            addRequirements(intake);
        }

        @Override
        public void execute() {
            intake.run(); // Run entire time
        }

        @Override
        public void end(boolean interrupted) {
            intake.stop();
        }
    }

    // Helper command for continuous shooter spin
    private class ContinuousShooterCommand extends CommandBase {
        private final OuttakeSubsystem outtake;
        private static final double SHOOTER_VELOCITY = 2000.0; // Tune to desired RPM/ticks

        public ContinuousShooterCommand(OuttakeSubsystem outtake) {
            this.outtake = outtake;
            addRequirements(outtake);
        }

        @Override
        public void execute() {
            outtake.runAtVelocity(SHOOTER_VELOCITY); // Spin flywheel entire time
        }

        @Override
        public void end(boolean interrupted) {
            outtake.stop();
        }
    }

    // Helper command for shoot (transfer at end of cycle)
    private class ShootCommand extends CommandBase {
        private final TransferSubsystem transfer;
        private final TurretSubsystem turret;
        private long startTime;

        public ShootCommand(TransferSubsystem transfer, TurretSubsystem turret) {
            this.transfer = transfer;
            this.turret = turret;
            addRequirements(transfer, turret);
        }

        @Override
        public void initialize() {
            startTime = System.currentTimeMillis();
        }

        @Override
        public void execute() {
            transfer.run(); // Trigger transfer (includes transferServo if added)
        }

        @Override
        public boolean isFinished() {
            return System.currentTimeMillis() - startTime > 500; // Short timeout for shoot
        }

        @Override
        public void end(boolean interrupted) {
            transfer.stop();
        }
    }
}