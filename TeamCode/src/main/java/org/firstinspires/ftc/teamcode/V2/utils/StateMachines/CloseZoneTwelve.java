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

import org.firstinspires.ftc.teamcode.V2.commands.Transfer;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


public class CloseZoneTwelve{
    public Follower follower;
    public Timer pathTimer;
    private Timer actionTimer;
    public Timer opmodeTimer;
    private int pathState;

    public PathChain InitialPushback;
    public PathChain IntakeClose;
    public PathChain GateOpen;
    public PathChain ShootClose;
    public PathChain IntakeMiddle;
    public PathChain ShootMiddle;
    public PathChain IntakeFar;
    public PathChain ShootFar;
    public PathChain Leave;
    HardwareMap hmap;
    public Robot robot = new Robot(hmap, telemetry);
    Transfer transfer = new Transfer(robot.intake, robot.transfer);
    public void buildPaths()
    {

        InitialPushback = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(21.601, 123.058),

                                new Pose(59.321, 84.158)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(180))

                .build();

        IntakeClose = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(59.321, 84.158),

                                new Pose(21.303, 83.883)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        GateOpen = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(21.303, 83.883),

                                new Pose(15.649, 73.871)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))

                .build();

        ShootClose = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.649, 73.871),

                                new Pose(59.036, 84.091)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))

                .build();

        IntakeMiddle = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(59.036, 84.091),
                                new Pose(53.249, 57.877),
                                new Pose(20.114, 59.792)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootMiddle = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.114, 59.792),

                                new Pose(59.232, 84.048)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        IntakeFar = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(59.232, 84.048),
                                new Pose(64.643, 32.478),
                                new Pose(20.338, 35.675)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        ShootFar = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.338, 35.675),

                                new Pose(59.283, 83.738)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Leave = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(59.283, 83.738),

                                new Pose(23.353, 69.829)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))

                .build();

    }
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                robot.intake.run();
                setPathState(1);
                break;
            case 1:
                robot.outtake.runAtVelocity(1800);
                setPathState(2);
                break;
            case 2:
                robot.turret.setPosition(.25);
                setPathState(5);
                break;
            case 5:
                follower.followPath(InitialPushback, true);
                setPathState(6);
                break;
            case 6:
                if(!follower.isBusy()) {

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
                    follower.followPath(IntakeClose,true);
                    setPathState(20);
                }
                break;
            case 20:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(GateOpen,true);
                    setPathState(30);
                }
                break;
            case 30:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(ShootClose,true);
                    setPathState(31);
                }
                break;
            case 31:
                if(!follower.isBusy()) {

                    transfer.execute();

                    setPathState(32);
                }
                break;
            case 32:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(opmodeTimer.getElapsedTimeSeconds() > 14) {
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
                    follower.followPath(IntakeMiddle,true);
                    setPathState(50);
                }
                break;
            case 50:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(ShootMiddle,true);
                    setPathState(51);
                }
                break;
            case 51:
                if(!follower.isBusy()) {

                    transfer.execute();

                    setPathState(52);
                }
                break;
            case 52:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(opmodeTimer.getElapsedTimeSeconds() > 20) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    transfer.end(true);
                    setPathState(60);
                }
                break;
            case 60:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup3Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(IntakeFar, true);
                    setPathState(70);
                }
                break;
            case 70:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(ShootFar,true);
                    setPathState(71);
                }
                break;
            case 71:
                if(!follower.isBusy()) {

                    transfer.execute();

                    setPathState(72);
                }
                break;
            case 72:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(opmodeTimer.getElapsedTimeSeconds() > 30) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    transfer.end(true);
                    setPathState(80);
                }
                break;
            case 80:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
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