package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class IntakeSubsystem extends SubsystemBase {

    private final DcMotorEx intake;

    public IntakeSubsystem(final HardwareMap hMap) {
        intake = hMap.get(DcMotorEx.class, "intake");
    }


    public void run() {
        intake.setPower(1);
    }

    public void stop() {
        intake.setPower(0);
    }

}