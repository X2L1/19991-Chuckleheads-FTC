package org.firstinspires.ftc.teamcode.V2.utils.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class BlueCloseTotalPath {
    public PathChain fullChain;

    public BlueCloseTotalPath(Follower follower) {
        fullChain = follower.pathBuilder()
                // InitialPushback
                .addPath(
                        new BezierLine(
                                new Pose(20.415, 122.583),
                                new Pose(60.033, 83.209)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(143))

                // IntakeMiddle
                .addPath(
                        new BezierCurve(
                                new Pose(60.033, 83.209),
                                new Pose(55.750, 58.359),
                                new Pose(16.359, 59.906)
                        )
                )
                .setTangentHeadingInterpolation()

                // MoveToGate
                .addPath(
                        new BezierCurve(
                                new Pose(16.359, 59.906),
                                new Pose(27.284, 65.782),
                                new Pose(18.654, 69.759)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))

                // OpenGate
                .addPath(
                        new BezierLine(
                                new Pose(18.654, 69.759),
                                new Pose(15.435, 69.722)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))

                // ShootMiddle
                .addPath(
                        new BezierCurve(
                                new Pose(15.435, 69.722),
                                new Pose(40.671, 67.750),
                                new Pose(59.570, 83.129)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(143))

                // IntakeClose
                .addPath(
                        new BezierLine(
                                new Pose(59.570, 83.129),
                                new Pose(14.946, 82.557)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(180))

                // ShootClose
                .addPath(
                        new BezierLine(
                                new Pose(14.946, 82.557),
                                new Pose(59.277, 82.843)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(143))

                // IntakeFar
                .addPath(
                        new BezierCurve(
                                new Pose(59.277, 82.843),
                                new Pose(79.652, 30.661),
                                new Pose(13.099, 35.041)
                        )
                )
                .setTangentHeadingInterpolation()

                // ShootFar
                .addPath(
                        new BezierLine(
                                new Pose(13.099, 35.041),
                                new Pose(59.409, 82.679)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(143))

                // Park
                .addPath(
                        new BezierLine(
                                new Pose(59.409, 82.679),
                                new Pose(18.616, 69.629)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(90))

                .build();
    }
}