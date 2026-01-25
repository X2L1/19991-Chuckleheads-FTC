package org.firstinspires.ftc.teamcode.V2.utils.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarZoneNine {
    public PathChain IntakeHP;
    public PathChain ShootHP;
    public PathChain IntakeFar;
    public PathChain ShootFar;
    public PathChain Leave;

    public FarZoneNine(Follower follower) {
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
}
