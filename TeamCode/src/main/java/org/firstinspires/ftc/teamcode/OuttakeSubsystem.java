package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class OuttakeSubsystem {

    private DcMotorEx shooterMotorLeft;
    private DcMotorEx shooterMotorCenter;
    private DcMotorEx shooterMotorRight;

    private Servo angleServoLeft;
    private Servo angleServoRight;

    private GoBildaPinpoint pinpoint; // Odometry device

    private TreeMap<Double, ShotParams> shotMap = new TreeMap<>();

    // Map from goal tag ID to its position (x, y) in field coordinates (e.g., mm, matching Pinpoint units)
    // These are example positions; adjust based on actual field setup for FTC Decode
    private Map<Integer, double[]> goalPositions = new HashMap<>();

    // Inner class for shot parameters
    private static class ShotParams {
        double velocity; // e.g., in ticks per second or RPM
        double angle;    // servo position (0-1) or degrees, assuming servo position

        ShotParams(double velocity, double angle) {
            this.velocity = velocity;
            this.angle = angle;
        }
    }

    // Constructor
    public OuttakeSubsystem(HydraHardware hardware) {
        this.shooterMotorLeft = hardware.shooterMotorLeft;
        this.shooterMotorCenter = hardware.shooterMotorCenter;
        this.shooterMotorRight = hardware.shooterMotorRight;

        this.angleServoLeft = hardware.angleServoLeft;
        this.angleServoRight = hardware.angleServoRight;

        this.pinpoint = hardware.pinpoint;

        // Initialize example goal positions (tag ID -> {x, y} in mm)
        // Replace with actual coordinates from the game manual or measurements
        // Assuming field origin and units match Pinpoint (mm)
        goalPositions.put(1, new double[]{0.0, 1828.8});  // Example: Goal at (0, 72 inches = 1828.8 mm)
        goalPositions.put(2, new double[]{609.6, 1828.8}); // Example: Another goal (24 inches = 609.6 mm)
        goalPositions.put(3, new double[]{-609.6, 1828.8});
        // Add more as needed
    }

    // Add a manual shot parameter for a specific distance
    public void addShotParam(double distance, double velocity, double angle) {
        shotMap.put(distance, new ShotParams(velocity, angle));
    }

    // Get interpolated shot params for a given distance
    private ShotParams getShotParams(double distance) {
        if (shotMap.isEmpty()) {
            return null;
        }

        Double lower = shotMap.floorKey(distance);
        Double higher = shotMap.ceilingKey(distance);

        if (lower == null) {
            return shotMap.get(higher);
        }
        if (higher == null) {
            return shotMap.get(lower);
        }
        if (lower.equals(higher)) {
            return shotMap.get(lower);
        }

        // Linear interpolation
        double ratio = (distance - lower) / (higher - lower);
        ShotParams lowParams = shotMap.get(lower);
        ShotParams highParams = shotMap.get(higher);

        double interpVelocity = lowParams.velocity + ratio * (highParams.velocity - lowParams.velocity);
        double interpAngle = lowParams.angle + ratio * (highParams.angle - lowParams.angle);

        return new ShotParams(interpVelocity, interpAngle);
    }

    // Set shooter velocity
    public void setVelocity(double velocity) {
        shooterMotorLeft.setVelocity(velocity);
        shooterMotorCenter.setVelocity(velocity);
        shooterMotorRight.setVelocity(velocity); // Assumes positive velocity works with reversed direction
    }

    // Set shooter angle (assuming both servos move together; adjust if one needs inversion)
    public void setAngle(double angle) {
        angleServoLeft.setPosition(angle);
        angleServoRight.setPosition(angle);
    }

    // Stop the shooter
    public void stop() {
        setVelocity(0.0);
    }

    // Get current distance to the goal using odometry (robot position from Pinpoint and known goal position)
    public double getCurrentDistance(int goalTagId) {
        // Update the pinpoint data
        pinpoint.update();

        Pose2D pose = pinpoint.getPosition();
        double robotX = pose.getX(DistanceUnit.MM);
        double robotY = pose.getY(DistanceUnit.MM);
        // Heading if needed: double heading = pose.getHeading(AngleUnit.RADIANS);

        double[] goalPos = goalPositions.get(goalTagId);
        if (goalPos != null) {
            double dx = goalPos[0] - robotX;
            double dy = goalPos[1] - robotY;
            return Math.sqrt(dx * dx + dy * dy);
        }
        return -1.0; // Goal not found
    }

    // Prepare the outtake for shooting at the current distance (using odometry-based position)
    public void prepareForShot(int goalTagId) {
        double distance = getCurrentDistance(goalTagId);
        if (distance > 0) {
            ShotParams params = getShotParams(distance);
            if (params != null) {
                setAngle(params.angle);
                setVelocity(params.velocity);
            }
        }
    }

    // Optional: Prepare for a known distance (if position not used)
    public void prepareForDistance(double distance) {
        ShotParams params = getShotParams(distance);
        if (params != null) {
            setAngle(params.angle);
            setVelocity(params.velocity);
        }
    }
}
