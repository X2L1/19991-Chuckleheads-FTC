package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class OdometryUtils {

    private GoBildaPinpointDriver pinpoint;

    // Constructor to initialize with the Pinpoint device from hardware
    public OdometryUtils(GoBildaPinpointDriver pinpoint) {
        this.pinpoint = pinpoint;
    }

    // Update the odometry data (should be called before querying positions)
    public void update() {
        pinpoint.update();
    }

    // Get the current X position in mm
    public double getXPosition() {
        return pinpoint.getPosition().getX(DistanceUnit.MM);
    }

    // Get the current Y position in mm
    public double getYPosition() {
        return pinpoint.getPosition().getY(DistanceUnit.MM);
    }

    // Get the full current pose (Pose2D with x, y in mm, heading in radians)
    public Pose2D getPose() {
        return pinpoint.getPosition();
    }

    // Calculate the Euclidean distance from current position to a given point (x, y in mm)
    public double getDistanceFromPoint(double targetX, double targetY) {
        double currentX = getXPosition();
        double currentY = getYPosition();
        double dx = targetX - currentX;
        double dy = targetY - currentY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Determine the direction to turn to face a certain point (targetX, targetY in mm)
    // Returns "left" if need to turn left (counter-clockwise), "right" for right (clockwise), "aligned" if already facing within tolerance
    // Tolerance in degrees, default 5 degrees
    public String determineTurnDirection(double targetX, double targetY, double toleranceDegrees) {
        double currentX = getXPosition();
        double currentY = getYPosition();
        double currentHeading = pinpoint.getPosition().getHeading(AngleUnit.RADIANS);

        // Calculate target heading using atan2 (angle from current position to target)
        double dx = targetX - currentX;
        double dy = targetY - currentY;
        double targetHeading = Math.atan2(dy, dx);

        // Normalize headings to [-pi, pi]
        double error = AngleUnit.normalizeRadians(targetHeading - currentHeading);

        double toleranceRadians = Math.toRadians(toleranceDegrees);

        if (Math.abs(error) < toleranceRadians) {
            return "aligned";
        } else if (error > 0) {
            return "left"; // Positive error: turn left (counter-clockwise)
        } else {
            return "right"; // Negative error: turn right (clockwise)
        }
    }

    // Overload for determineTurnDirection with default tolerance of 5 degrees
    public String determineTurnDirection(double targetX, double targetY) {
        return determineTurnDirection(targetX, targetY, 5.0);
    }
}