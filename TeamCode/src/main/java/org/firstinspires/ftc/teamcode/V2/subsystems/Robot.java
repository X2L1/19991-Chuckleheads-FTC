package org.firstinspires.ftc.teamcode.V2.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Robot{

    HardwareMap hMap;
   public DriveSubsystem drive;
    public IntakeSubsystem intake;
  public  OuttakeSubsystem outtake;
   public TransferSubsystem transfer;
   public TurretSubsystem turret;
   public LimelightSubsystem limelight;
   public RGBSubsystem rgb;
    public Robot() {
            drive = new DriveSubsystem(hMap);
            intake = new IntakeSubsystem(hMap);
            outtake = new OuttakeSubsystem(hMap);
            transfer = new TransferSubsystem(hMap);
            turret = new TurretSubsystem(hMap);
            limelight = new LimelightSubsystem(hMap);
            rgb = new RGBSubsystem(hMap);
    }

}