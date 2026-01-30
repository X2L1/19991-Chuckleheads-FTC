package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.TelemetryMessage;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Robot {

    public DriveSubsystem drive;
    public IntakeSubsystem intake;
    public OuttakeSubsystem outtake;
    public TransferSubsystem transfer;
    public TurretSubsystem turret;
    public LimelightSubsystem limelight;
    public RGBSubsystem rgb;
    public Telemetry telemetry;


    public Robot(HardwareMap hMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        drive = new DriveSubsystem(hMap);
        intake = new IntakeSubsystem(hMap);
        outtake = new OuttakeSubsystem(hMap);
        transfer = new TransferSubsystem(hMap);
        turret = new TurretSubsystem(hMap);
        limelight = new LimelightSubsystem(hMap);
        rgb = new RGBSubsystem(hMap);
    }
}