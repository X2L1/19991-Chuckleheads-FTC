package org.firstinspires.ftc.teamcode.V2.utils.StateMachines;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.V2.commands.Transfer;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;

public class FarZoneNine {
    public PathChain IntakeHP;
    public PathChain ShootHP;
    public PathChain IntakeFar;
    public PathChain ShootFar;
    public PathChain Leave;
    public Follower follower;
    public Timer pathTimer;
    private Timer actionTimer;
    public Timer opmodeTimer;
    private int pathState;
    HardwareMap hMap;
    Robot robot = new Robot(hMap, telemetry);
    Transfer transfer = new Transfer(robot.intake, robot.transfer);
        public void buildPaths() {
            IntakeHP = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(59.558, 8.474),
                                    new Pose(-0.500, 26.575),
                                    new Pose(9.740, 10.616)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(270))

                    .build();

            ShootHP = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(9.740, 10.616),

                                    new Pose(59.653, 8.134)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(180))

                    .build();

            IntakeFar = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(59.653, 8.134),
                                    new Pose(39.855, 38.669),
                                    new Pose(22.689, 35.984)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(180))

                    .build();

            ShootFar = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(22.689, 35.984),

                                    new Pose(58.862, 8.443)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(180))

                    .build();

            Leave = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(58.862, 8.443),

                                    new Pose(25.318, 10.527)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();
        }
        public void autonomousPathUpdate() {
            switch(pathState)
            {
                case 0:
                    robot.turret.setPosition(0);
                    setPathState(1);
                    break;
                case 1:
                    robot.outtake.runAtVelocity(1800);
                    setPathState(2);
                case 2:
                    if (!follower.isBusy()) {
                        transfer.execute();
                        setPathState(3);
                    }
                    break;

                case 3:
                    if (opmodeTimer.getElapsedTimeSeconds() > 3) {
                        transfer.end(true);
                        setPathState(10);
                    }
                    break;
                case 10:
                    follower.followPath(IntakeHP);
                    setPathState(20);
                    break;
                case 20:
                    if (!follower.isBusy()) {
                        follower.followPath(ShootHP);
                        setPathState(21);
                    }
                    break;
                case 21:
                    if (!follower.isBusy()) {
                        transfer.execute();
                        setPathState(22);
                    }
                    break;

                case 22:
                    if (opmodeTimer.getElapsedTimeSeconds() > 12) {
                        transfer.end(true);
                        setPathState(30);
                    }
                    break;
                case 30:
                    if (!follower.isBusy()) {
                        follower.followPath(IntakeFar);
                        setPathState(40);
                    }
                    break;
                case 40:
                    if (!follower.isBusy()) {
                        follower.followPath(ShootFar);
                        setPathState(41);
                    }
                    break;
                case 41:
                    if (!follower.isBusy()) {
                        transfer.execute();
                        setPathState(42);
                    }
                    break;

                case 42:
                    if (opmodeTimer.getElapsedTimeSeconds() > 20) {
                        transfer.end(true);
                        setPathState(50);
                    }
                    break;
                case 50:
                    if (!follower.isBusy()) {
                        follower.followPath(Leave);
                        setPathState(222);
                    }
                    break;
                case 222:
                    // End of state machine
                    break;
            }
        }
    public void setPathState(int state) {
        pathState = state;
        pathTimer.resetTimer();
    }
    }

