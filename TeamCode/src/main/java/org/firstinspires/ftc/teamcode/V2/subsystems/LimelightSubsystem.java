package org.firstinspires.ftc.teamcode.V2.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.math.Vector;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;


public class LimelightSubsystem extends SubsystemBase {

    private final Limelight3A limelight;

    public LimelightSubsystem(final HardwareMap hMap) {
        limelight = hMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.start();
    }
    public LLResult getResult() {
        return limelight.getLatestResult();
    }
    public Pose3D getPose() {
        return getResult().getBotpose();
    }

    public double getTy() {
        LLResult result = getResult();
        return result.getTy();
    }
    public double getTx() {
        LLResult result = getResult();
        return result.getTx();
    }
    public double getXDegrees(int tagID)
    {
        LLResultTypes.FiducialResult detection = limelight.getLatestResult().getFiducialResults().get(tagID);
        return detection.getTargetXDegrees();
    }
    public boolean hasTarget() {
        LLResult result = getResult();
        return result.isValid();
    }
    public double distanceFromTag(double tagID) {

        List<LLResultTypes.FiducialResult> fiducialResultList = limelight.getLatestResult().getFiducialResults();

        if (fiducialResultList.isEmpty()) return 0;

        LLResultTypes.FiducialResult target = null;
        for (LLResultTypes.FiducialResult tagResult: fiducialResultList) {
            if (tagResult != null && tagResult.getFiducialId() ==  tagID) {
                target = tagResult;
                break;
            }
        }

        if (target != null) {
            double x = (target.getCameraPoseTargetSpace().getPosition().x / DistanceUnit.mPerInch) + 8; // right/left from tag
            double z = (target.getCameraPoseTargetSpace().getPosition().z / DistanceUnit.mPerInch) + 8; // forward/back from tag

            Vector e = new Vector();
            e.setOrthogonalComponents(x, z);
            return e.getMagnitude();
        }

        return 0;
    }
}