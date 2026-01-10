package org.firstinspires.ftc.teamcode.Hydra;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class IntakeSubsystem {

    private DcMotorEx intake;

    // Constructor that takes the hardware class to get the motor reference
    public IntakeSubsystem(HydraHardware hardware) {
        this.intake = hardware.intake;
    }

    // Run the intake at full speed
    public void run() {
        intake.setPower(1.0);
    }

    // Run the intake at a slower speed (e.g., half power)
    public void runSlow() {
        intake.setPower(0.5);
    }

    // Stop the intake
    public void stop() {
        intake.setPower(0.0);
    }

    // Optional: Run at a custom power level (between -1.0 and 1.0)
    public void runAtPower(double power) {
        intake.setPower(power);
    }

    // Optional: Reverse the intake (e.g., to eject artifacts)
    public void reverse() {
        intake.setPower(-1.0);
    }
}
