package org.firstinspires.ftc.teamcode.V2.utils.StateMachines; // make sure this aligns with class location
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.V2.commands.Transfer;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class CloseZone12Red{
    public Follower follower;
    public Timer pathTimer;
    private Timer actionTimer;
    public Timer opmodeTimer;
    ElapsedTime executeTimer;
    public int pathState;

    public PathChain InitialPushback;
    public PathChain Gotoclose;
    public PathChain Intakeclose;
    public PathChain shootclose;
    public PathChain gotomiddle;
    public PathChain intakemiddle;
    public PathChain avoidgate;
    public PathChain shootmiddle;
    public PathChain leave;
    HardwareMap hmap;
    Telemetry telemetry;
    public Robot robot;
    Transfer transfer;

    public CloseZone12Red(HardwareMap hmap, Telemetry telemetry) {
        this.hmap = hmap;
        this.telemetry = telemetry;
        robot = new Robot(hmap, telemetry);
        transfer = new Transfer(robot.intake, robot.transfer);
        executeTimer = new ElapsedTime();
    }

    public void buildPaths()
    {

        InitialPushback = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(23.499, 124.956).mirror(),

                                new Pose(32.040, 116.185).mirror()
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(143 -90), Math.toRadians(143-90))

                .build();

        Gotoclose = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(32.040, 116.185).mirror(),

                                new Pose(48.913, 84.697).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143-90), Math.toRadians(180-180))

                .build();

        Intakeclose = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(48.913, 84.697).mirror(),

                                new Pose(15.015, 84.038).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180-180), Math.toRadians(180-180))

                .build();

        shootclose = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.015, 84.038).mirror(),

                                new Pose(32.030, 116.730).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180-180), Math.toRadians(143-90))

                .build();

        gotomiddle = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(32.030, 116.730).mirror(),

                                new Pose(50.392, 59.366).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143-90), Math.toRadians(180-180))

                .build();

        intakemiddle = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(50.392, 59.366).mirror(),

                                new Pose(15.903, 59.759).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180-180), Math.toRadians(180-180))

                .build();

        avoidgate = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.903, 59.759).mirror(),

                                new Pose(17.521, 69.888).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180-180), Math.toRadians(90-180))

                .build();

        shootmiddle = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.521, 69.888).mirror(),

                                new Pose(32.226, 116.298).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90-180 ), Math.toRadians(143-90))

                .build();

        leave = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(32.226, 116.298).mirror(),

                                new Pose(34.038, 75.211).mirror()
                        )
                ).setTangentHeadingInterpolation()

                .build();

    }
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                robot.intake.run();
                robot.transfer.runSlow();

                setPathState(1);
                break;
            case 1:
                robot.outtake.runAtVelocity(-1800);
                setPathState(2);
                break;
            case 2:

                robot.turret.setPosition(.5);
                setPathState(5);
                break;
            case 5:
                follower.followPath(InitialPushback, true);
                setPathState(6);
                executeTimer.startTime();
                break;
            case 6:
                if(!follower.isBusy() && executeTimer.time() > 3) {

                    transfer.execute();

                    setPathState(7);
                }
                break;
            case 7:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(opmodeTimer.getElapsedTimeSeconds() > 6) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    transfer.end(true);
                    setPathState(10);
                }
                break;
            case 10:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(opmodeTimer.getElapsedTimeSeconds() > 6) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(Gotoclose,true);
                    setPathState(20);
                }
                break;
            case 20:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Intakeclose,true);
                    setPathState(30);
                }
                break;
            case 30:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(shootclose,true);
                    setPathState(31);
                }
                break;
            case 31:
                if(!follower.isBusy()) {

                    transfer.execute();

                    executeTimer.startTime();
                    setPathState(32);
                }
                break;
            case 32:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(executeTimer.time() > 3) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    transfer.end(true);
                    setPathState(40);
                }
                break;
            case 40:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup2Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(gotomiddle,true);
                    setPathState(49);
                }
                break;
            case 49:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(intakemiddle,true);
                    setPathState(50);
                }
                break;
            case 50:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(shootmiddle,true);
                    setPathState(51);
                }
                break;
            case 51:
                if(!follower.isBusy()) {

                    transfer.execute();
                    executeTimer.startTime();
                    setPathState(52);
                }
                break;
            case 52:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(executeTimer.time() > 3) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    transfer.end(true);
                    setPathState(80);
                }
                break;

            case 80:
                /* This case checks th robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    follower.followPath(leave);
                    setPathState(-1);
                }
                break;
        }
    }
    /** These change the states of the paths and actions. It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

}