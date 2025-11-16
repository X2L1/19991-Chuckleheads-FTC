package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.hardware.CRServo;

public class ExtensionSubsystem {

    private CRServo extensionServo1;
    private CRServo extensionServo2;

    // Constructor to initialize hardware
    public ExtensionSubsystem(HydraHardware hardware) {
        this.extensionServo1 = hardware.extensionServo1;
        this.extensionServo2 = hardware.extensionServo2;
    }

    // Expand the lead screws (full speed forward)
    public void expand() {
        extensionServo1.setPower(1.0);
        extensionServo2.setPower(1.0);
    }

    // Retract the lead screws (full speed reverse)
    public void retract() {
        extensionServo1.setPower(-1.0);
        extensionServo2.setPower(-1.0);
    }

    // Stop the lead screws
    public void stop() {
        extensionServo1.setPower(0.0);
        extensionServo2.setPower(0.0);
    }

    // Optional: Run at custom power (positive for expand, negative for retract)
    public void setPower(double power) {
        extensionServo1.setPower(power);
        extensionServo2.setPower(power);
    }
}
