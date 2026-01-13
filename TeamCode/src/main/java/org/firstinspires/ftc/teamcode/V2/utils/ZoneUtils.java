package org.firstinspires.ftc.teamcode.V2.utils;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class ZoneUtils {
    private final PolygonZone closeLaunchZone = new PolygonZone(new Point(144, 144), new Point(72, 72), new Point(0, 144));
    private final PolygonZone farLaunchZone = new PolygonZone(new Point(48, 0), new Point(72, 24), new Point(96, 0));
    private final PolygonZone robotZone = new PolygonZone(17, 17);
    private Follower follower;
    public void initZone()
    {
        follower = Constants.createFollower(hardwareMap);
        follower.startTeleopDrive(true);
        follower.setStartingPose(new Pose(72, 72));
    }
    public void syncZonesWithPositions()
    {
        robotZone.setPosition(follower.getPose().getX(), follower.getPose().getY());
        robotZone.setRotation(follower.getPose().getHeading());
    }
    public boolean isInsideLaunchZone()
    {
        return robotZone.isInside(closeLaunchZone) || robotZone.isInside(farLaunchZone);
    }
}
