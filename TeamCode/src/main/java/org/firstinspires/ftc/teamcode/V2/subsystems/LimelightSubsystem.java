package org.firstinspires.ftc.teamcode.V2.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;


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
    public double getDistanceToTarget() {
        LLResult result = getResult();
            double ty = result.getTy();
            // Calculate distance using the formula
            double h1 = 24; // height of limelight in inches
            double h2 = 104; // height of target in inches
            double a1 = 20; // mounting angle of limelight in degrees
            double a2 = ty; // vertical offset angle to target in degrees

            double distance = (h2 - h1) / Math.tan(Math.toRadians(a1 + a2));
            return distance;

    }
    public double getTy() {
        LLResult result = getResult();
        return result.getTy();
    }
    public double getTx() {
        LLResult result = getResult();
        return result.getTx();
    }
    public boolean hasTarget() {
        LLResult result = getResult();
        return result.isValid();
    }
}