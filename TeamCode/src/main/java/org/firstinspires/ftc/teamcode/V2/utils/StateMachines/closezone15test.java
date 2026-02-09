package org.firstinspires.ftc.teamcode.V2.utils.StateMachines; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.V2.commands.Transfer;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;


public class closezone15test {
    public Follower follower;
    public Timer pathTimer;
    private Timer actionTimer;
    public Timer opmodeTimer;
    ElapsedTime executeTimer;
    public int pathState;

    public PathChain InitialPushback;
    public PathChain intakeclose;
    public PathChain firstshot;
    public PathChain middleintake;
    public PathChain secondshot;
    public PathChain gatedintake;
    public PathChain thirdshot;
    public PathChain farintake;
    public PathChain fourthshot;

    public PathChain GETOUTOFTHERE;
    HardwareMap hmap;
    Telemetry telemetry;
    public Robot robot;
    Transfer transfer;

    public closezone15test(HardwareMap hmap, Telemetry telemetry) {
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
                                new Pose(25.649, 130.603),

                                new Pose(32.000, 111.329)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(138))

                .build();

        intakeclose = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(32.000, 111.329),
                                new Pose(76.909, 83.511),
                                new Pose(24.317, 84.037)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(138), Math.toRadians(180))

                .build();

        firstshot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(24.317, 84.037),

                                new Pose(32.000, 111.329)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(138))

                .build();

        middleintake = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(32.000, 111.329),
                                new Pose(64.134, 104.595),
                                new Pose(63.362, 51.977),
                                new Pose(24.711, 59.111)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(138), Math.toRadians(180))

                .build();

        secondshot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(24.711, 59.111),

                                new Pose(32.000, 111.329)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(138))

                .build();

        gatedintake = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(32.000, 111.329),
                                new Pose(36.214, 44.435),
                                new Pose(3.234, 59.745)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(138), Math.toRadians(100))

                .build();

        thirdshot = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(3.234, 59.745),
                                new Pose(36.460, 44.565),
                                new Pose(32.000, 111.329)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(100), Math.toRadians(138))

                .build();

        farintake = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(32.000, 111.329),
                                new Pose(75.305, 24.148),
                                new Pose(24.437, 36.191)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(138), Math.toRadians(180))

                .build();

        fourthshot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(24.437, 36.191),

                                new Pose(32.000, 111.329)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(138))

                .build();

         GETOUTOFTHERE = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(32.000, 111.329),

                            new Pose(54.240, 134.342)
                    )
            ).setLinearHeadingInterpolation(Math.toRadians(138), Math.toRadians(10))

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
                robot.outtake.runAtVelocity(0);
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
                    follower.followPath(intakeclose,true);
                    setPathState(20);
                }
                break;
            case 20:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(firstshot,true);
                    setPathState(21);
                }
                break;

            case 21:
                if(!follower.isBusy() && executeTimer.time() > 3) {

                    transfer.execute();

                    setPathState(22);
                }
                break;
            case 22:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(opmodeTimer.getElapsedTimeSeconds() > 6) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    transfer.end(true);
                    setPathState(30);
                }
                break;

            case 30:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(middleintake,true);
                    setPathState(40);
                }
                break;

            case 40:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup2Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(secondshot,true);
                    setPathState(41);
                }
                break;

            case 41:
                if(!follower.isBusy() && executeTimer.time() > 3) {

                    transfer.execute();

                    setPathState(42);
                }
                break;
            case 42:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(opmodeTimer.getElapsedTimeSeconds() > 6) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    transfer.end(true);
                    setPathState(50);
                }
                break;

            case 50:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(gatedintake,true);
                    setPathState(60);
                }
                break;
            case 60:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(thirdshot,true);
                    setPathState(61);
                }
                break;
            case 61:
                if(!follower.isBusy()) {

                    transfer.execute();
                    executeTimer.startTime();
                    setPathState(62);
                }
                break;
            case 62:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(executeTimer.time() > 3) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    transfer.end(true);
                    setPathState(70);
                }
                break;

            case 70:
                /* This case checks th robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    follower.followPath(farintake);
                    setPathState(80);
                }
                break;

            case 80:
                if(!follower.isBusy()) {

                    follower.followPath(fourthshot);
                    setPathState(81);
                }
                break;

            case 81:
                if(!follower.isBusy() && executeTimer.time() > 3) {

                    transfer.execute();

                    setPathState(82);
                }
                break;

            case 82:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(opmodeTimer.getElapsedTimeSeconds() > 6) {
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    transfer.end(true);
                    setPathState(90);
                }
                break;

            case 90:
                if(!follower.isBusy()) {

                    follower.followPath(GETOUTOFTHERE);
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