package org.firstinspires.ftc.teamcode.V2.utils.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class CloseZoneTwelve {
    public PathChain InitialPushback;
    public PathChain IntakeClose;
    public PathChain GateOpen;
    public PathChain ShootClose;
    public PathChain IntakeMiddle;
    public PathChain ShootMiddle;
    public PathChain IntakeFar;
    public PathChain ShootFar;
    public PathChain Leave;

    public CloseZoneTwelve(Follower follower) {
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
}
  