package org.firstinspires.ftc.teamcode.V2.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TurretSubsystem extends SubsystemBase {
    private final Servo leftServo;
    private final Servo rightServo;
    public boolean manualOverride = false; // For teleop presets vs. auto

    public TurretSubsystem(HardwareMap hardwareMap) {
        leftServo = hardwareMap.get(Servo.class, "leftTurret");
        rightServo = hardwareMap.get(Servo.class, "rightTurret");
    }

    public void setPosition(double position) {
        position = Math.max(0.0, Math.min(1.0, position)); // Clamp to valid servo range
        leftServo.setPosition(position);
        rightServo.setPosition(position);
    }

    public double getPosition() {
        return leftServo.getPosition(); // Average or left for simplicity
    }

    public void stop() {
        // No-op for position servos; already hold position
    }
}