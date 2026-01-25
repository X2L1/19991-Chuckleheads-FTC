package org.firstinspires.ftc.teamcode.V2.utils.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarZoneSix {
    public PathChain IntakeHP;
    public PathChain ShootHP;
    public PathChain Leave;

    public FarZoneSix(Follower follower) {
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

        Leave = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(59.653, 8.134),

                                new Pose(23.638, 9.176)
                        )
                ).setTangentHeadingInterpolation()

                .build();
    }
}
