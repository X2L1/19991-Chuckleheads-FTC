package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TurretSubsystem extends SubsystemBase {
    private final CRServo leftServo;
    private final CRServo rightServo;
    public boolean manualOverride = false; // Flag for teleop manual control

    public TurretSubsystem(HardwareMap hardwareMap) {
        leftServo = hardwareMap.get(CRServo.class, "leftTurret");
        rightServo = hardwareMap.get(CRServo.class, "rightTurret");
    }

    public void setPower(double power) {
        leftServo.setPower(power);
        rightServo.setPower(power); // Adjust for gearing direction
    }

    public void stop() {
        setPower(0.0);
    }
}