package org.firstinspires.ftc.teamcode.pedroPathing;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;

public class Paths {

    public PathChain DRIVETOHUMANPLAYER;
    public PathChain INTAKEHUMANPLAYER;
    public PathChain DRIVETOLAUNCH1;
    public PathChain DRIVETOFARSPIKE;
    public PathChain INTAKEFARSPIKE;
    public PathChain DRIVETOLAUNCH2;
    public PathChain DRIVETOMIDDLESPIKE;
    public PathChain INTAKEMIDDLESPIKE;
    public PathChain DRIVETOLAUNCH3;
    public PathChain DRIVEANDINTAKECLOSESPIKE;
    public PathChain DRIVETOLAUNCH4;

    public Paths(Follower follower) {
        DRIVETOHUMANPLAYER = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(63.341, 9.489),
                                new Pose(55.038, 26.333),
                                new Pose(26.570, 9.252)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();

        INTAKEHUMANPLAYER = follower
                .pathBuilder()
                .addPath(new BezierLine(new Pose(26.570, 9.252), new Pose(9.964, 9.015)))
                .setTangentHeadingInterpolation()
                .build();

        DRIVETOLAUNCH1 = follower
                .pathBuilder()
                .addPath(new BezierLine(new Pose(9.964, 9.015), new Pose(64.053, 15.183)))
                .setConstantHeadingInterpolation(Math.toRadians(110))
                .build();

        DRIVETOFARSPIKE = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(64.053, 15.183),
                                new Pose(23.486, 8.778),
                                new Pose(23.723, 21.825)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        INTAKEFARSPIKE = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(23.723, 21.825), new Pose(23.960, 31.077))
                )
                .setTangentHeadingInterpolation()
                .build();

        DRIVETOLAUNCH2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(23.960, 31.077), new Pose(64.290, 15.183))
                )
                .setConstantHeadingInterpolation(Math.toRadians(110))
                .build();

        DRIVETOMIDDLESPIKE = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(64.290, 15.183),
                                new Pose(21.825, 7.591),
                                new Pose(23.960, 47.446)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        INTAKEMIDDLESPIKE = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(23.960, 47.446), new Pose(24.198, 54.563))
                )
                .setTangentHeadingInterpolation()
                .build();

        DRIVETOLAUNCH3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(24.198, 54.563), new Pose(64.290, 15.657))
                )
                .setConstantHeadingInterpolation(Math.toRadians(110))
                .build();

        DRIVEANDINTAKECLOSESPIKE = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(64.290, 15.657),
                                new Pose(21.351, 24.672),
                                new Pose(24.198, 77.812)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        DRIVETOLAUNCH4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(24.198, 77.812), new Pose(63.578, 79.710))
                )
                .setConstantHeadingInterpolation(Math.toRadians(135))
                .build();
    }
}