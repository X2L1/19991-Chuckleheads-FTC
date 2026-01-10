package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.V2.utils.Configurables;


public class TurretSubsystem extends SubsystemBase {

    private final CRServo turretLeft;
    private final CRServo turretRight;

    public TurretSubsystem(final HardwareMap hMap) {
        turretLeft = hMap.get(CRServo.class, "turretLeft");
        turretRight = hMap.get(CRServo.class, "turretRight");
    }


    public void aimAtGoal(double position, double power) {
        if (position > 0) {
            turretLeft.setPower(power);
            turretRight.setPower(power);
        } else if (position < 0) {
            turretLeft.setPower(-power);
            turretRight.setPower(-power);
        } else {
            stop();
        }
    }

    public void stop() {
        turretLeft.setPower(0);
        turretRight.setPower(0);
    }
}

