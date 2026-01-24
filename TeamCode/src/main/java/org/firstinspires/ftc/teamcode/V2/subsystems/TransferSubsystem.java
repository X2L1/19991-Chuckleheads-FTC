package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TransferSubsystem extends SubsystemBase {

    private final DcMotorEx transfer;
    //private final Servo blocker;
    private final CRServo transferServo; // New intermediate continuous rotation servo

    public TransferSubsystem(final HardwareMap hMap) {
        transfer = hMap.get(DcMotorEx.class, "transfer");
        //blocker = hMap.get(Servo.class, "blocker");
        transferServo = hMap.get(CRServo.class, "transferServo"); // Assumes mapped in hardware config
    }

    public void run() {
       // blocker.setPosition(0); // Open blocker
        transferServo.setPower(1.0); // Spin intermediate servo at full power to prevent jams
        transfer.setPower(1);
    }

    public void stop() {
        transfer.setPower(0);
        transferServo.setPower(0.0); // Stop intermediate servo
       // blocker.setPosition(1); // Close blocker
    }
}