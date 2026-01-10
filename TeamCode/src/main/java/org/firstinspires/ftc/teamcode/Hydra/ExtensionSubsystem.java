package org.firstinspires.ftc.teamcode.Hydra;

import com.qualcomm.robotcore.hardware.CRServo;

public class ExtensionSubsystem {

    private CRServo leftScrew;
    private CRServo rightScrew;

    // Constructor to initialize hardware
    public ExtensionSubsystem(HydraHardware hardware) {
        this.leftScrew = hardware.leftScrew;
        this.rightScrew = hardware.rightScrew;
    }

    // Expand the lead screws (full speed forward)
    public void expand() {
        leftScrew.setPower(1.0);
        rightScrew.setPower(1.0);
    }

    // Retract the lead screws (full speed reverse)
    public void retract() {
        leftScrew.setPower(-1.0);
        rightScrew.setPower(-1.0);
    }

    // Stop the lead screws
    public void stop() {
        leftScrew.setPower(0.0);
        rightScrew.setPower(0.0);
    }

    // Optional: Run at custom power (positive for expand, negative for retract)
    public void setPower(double power) {
        leftScrew.setPower(power);
        rightScrew.setPower(power);
    }
}
