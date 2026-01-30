package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class LimelightSubsystem extends SubsystemBase {

    private final Limelight3A limelight;

    // Trig fallback constants (tune these!)
    private static final double TAG_HEIGHT_INCHES = 30.0; // DECODE Goal tag center height above floor (estimate; measure!)
    private static final double CAMERA_HEIGHT_INCHES = 12.0; // Your camera height above floor
    private static final double CAMERA_PITCH_DEGREES = 15.0; // Camera upward tilt angle (0 if level)

    public LimelightSubsystem(final HardwareMap hMap) {
        limelight = hMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    public LLResult getLatestResult() {
        return limelight.getLatestResult();
    }

    public Pose3D getPose() {
        return getLatestResult().getBotpose();
    }

    public double getTy() {
        LLResult result = getLatestResult();
        return result.getTy();
    }

    public double getTx() {
        LLResult result = getLatestResult();
        return result.getTx();
    }

    public double getXDegrees(int tagID) {
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> detections = result.getFiducialResults();
            for (LLResultTypes.FiducialResult detection : detections) {
                if (detection.getFiducialId() == tagID) {
                    return detection.getTargetXDegrees();
                }
            }
        }
        return 0.0;
    }

    public boolean hasTarget() {
        LLResult result = getLatestResult();
        return result != null && result.isValid();
    }

    public double distanceFromTag(int tagID) { // Changed to int for consistency
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> detections = result.getFiducialResults();
            for (LLResultTypes.FiducialResult detection : detections) {
                if (detection.getFiducialId() == tagID) {
                    Pose3D pose = detection.getCameraPoseTargetSpace();
                    if (pose != null) {
                        double xMeters = pose.getPosition().x;
                        double zMeters = pose.getPosition().z;
                        if (xMeters != 0 || zMeters != 0) { // Check non-zero
                            double xInches = xMeters * 39.3701 + 8; // Meters to inches + offset
                            double zInches = zMeters * 39.3701 + 8;
                            return Math.sqrt(xInches * xInches + zInches * zInches); // Magnitude
                        }
                    }
                    // Fallback to ty trig if 3D pose invalid
                    double ty = detection.getTargetYDegrees();
                    double angleToTag = CAMERA_PITCH_DEGREES + ty;
                    if (angleToTag != 0) { // Avoid div zero
                        return (TAG_HEIGHT_INCHES - CAMERA_HEIGHT_INCHES) / Math.tan(Math.toRadians(angleToTag));
                    }
                }
            }
        }
        return 0.0; // No detection
    }
}