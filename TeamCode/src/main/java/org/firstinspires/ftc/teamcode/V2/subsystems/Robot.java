package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    public HardwareMap hMap;
    public DriveSubsystem drive;
    public IntakeSubsystem intake;
    public OuttakeSubsystem outtake;
    public TransferSubsystem transfer;
    public TurretSubsystem turret;
    public LimelightSubsystem limelight;
    public RGBSubsystem rgb;

    public Robot(HardwareMap hMap) {
        this.hMap = hMap;
        drive = new DriveSubsystem(hMap);
        intake = new IntakeSubsystem(hMap);
        outtake = new OuttakeSubsystem(hMap);
        transfer = new TransferSubsystem(hMap);
        turret = new TurretSubsystem(hMap);
        limelight = new LimelightSubsystem(hMap);
        rgb = new RGBSubsystem(hMap);
    }
}