package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.V2.utils.Configurables;


public class TransferSubsystem extends SubsystemBase {

    private final DcMotorEx transfer;
    private final Servo blocker;

    public TransferSubsystem(final HardwareMap hMap) {
        transfer = hMap.get(DcMotorEx.class, "transfer");
        blocker = hMap.get(Servo.class, "blocker");
    }


    public void run() {
        blocker.setPosition(0);
        transfer.setPower(1);
    }

    public void stop() {
        transfer.setPower(0);
        blocker.setPosition(1);
    }

}