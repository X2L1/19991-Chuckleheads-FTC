package org.firstinspires.ftc.teamcode.V2.utils.Paths;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.paths.PathChain;

import com.pedropathing.geometry.Pose;

public class FarZonePartner {
    public PathChain IntakeHP;
    public PathChain ShootHP;
    public PathChain IntakeFar;
    public PathChain ShootFar;
    public PathChain IntakePartner;
    public PathChain ShootPartner;
    public PathChain Leave;

    public FarZonePartner(Follower follower) {
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

        IntakePartner = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.862, 8.443),
                                new Pose(15.587, 39.816),
                                new Pose(10.135, 11.713)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(270))

                .build();

        ShootPartner = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.135, 11.713),

                                new Pose(60.003, 8.115)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(180))

                .build();
        Leave = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.003, 8.115),

                                new Pose(17.694, 8.893)
                        )
                ).setTangentHeadingInterpolation()

                .build();
    }
}
  