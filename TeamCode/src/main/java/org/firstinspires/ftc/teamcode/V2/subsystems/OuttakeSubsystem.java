package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.V2.utils.Configurables;


public class OuttakeSubsystem extends SubsystemBase {

    private final DcMotorEx outtakeLeft;
    private final DcMotorEx outtakeRight;

    public OuttakeSubsystem(final HardwareMap hMap) {
        outtakeLeft = hMap.get(DcMotorEx.class, "outtakeLeft");
        outtakeRight = hMap.get(DcMotorEx.class, "outtakeRight");
        outtakeLeft.setVelocityPIDFCoefficients(Configurables.outtakeP, Configurables.outtakeI, Configurables.outtakeD, Configurables.outtakeF);
        outtakeRight.setVelocityPIDFCoefficients(Configurables.outtakeP, Configurables.outtakeI, Configurables.outtakeD, Configurables.outtakeF);
    }


    public void runAtVelocity(int velocity) {
        outtakeLeft.setVelocity(velocity);
        outtakeRight.setVelocity(velocity);
    }

    public void stop() {
        outtakeLeft.setPower(0);
        outtakeRight.setPower(0);
    }

}