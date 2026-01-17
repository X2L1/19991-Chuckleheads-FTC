package org.firstinspires.ftc.teamcode.V2.utils;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class CloseBluePaths12Ball {
    public PathChain InitialPushback;
    public PathChain IntakeMiddle;
    public PathChain MoveToGate;
    public PathChain OpenGate;
    public PathChain ShootMiddle;
    public PathChain IntakeClose;
    public PathChain ShootClose;
    public PathChain IntakeFar;
    public PathChain ShootFar;
    public PathChain Park;

    public CloseBluePaths12Ball(Follower follower) {
        InitialPushback = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.415, 122.583),

                                new Pose(60.033, 83.209)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(143))

                .build();

        IntakeMiddle = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(60.033, 83.209),
                                new Pose(55.750, 58.359),
                                new Pose(16.359, 59.906)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        MoveToGate = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(16.359, 59.906),
                                new Pose(27.284, 65.782),
                                new Pose(18.654, 69.759)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))

                .build();

        OpenGate = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(18.654, 69.759),

                                new Pose(15.435, 69.722)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))

                .build();

        ShootMiddle = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(15.435, 69.722),
                                new Pose(40.671, 67.750),
                                new Pose(59.570, 83.129)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(143))

                .build();

        IntakeClose = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(59.570, 83.129),

                                new Pose(14.946, 82.557)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(180))

                .build();

        ShootClose = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(14.946, 82.557),

                                new Pose(59.277, 82.843)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(143))

                .build();

        IntakeFar = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(59.277, 82.843),
                                new Pose(79.652, 30.661),
                                new Pose(13.099, 35.041)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootFar = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(13.099, 35.041),

                                new Pose(59.409, 82.679)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(143))

                .build();

        Park = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(59.409, 82.679),

                                new Pose(18.616, 69.629)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(90))

                .build();
    }
}
