package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class IntakeSubsystem {

    private DcMotorEx intakeMotor;

    // Constructor that takes the hardware class to get the motor reference
    public IntakeSubsystem(HydraHardware hardware) {
        this.intakeMotor = hardware.intakeMotor;
    }

    // Run the intake at full speed
    public void run() {
        intakeMotor.setPower(1.0);
    }

    // Run the intake at a slower speed (e.g., half power)
    public void runSlow() {
        intakeMotor.setPower(0.5);
    }

    // Stop the intake
    public void stop() {
        intakeMotor.setPower(0.0);
    }

    // Optional: Run at a custom power level (between -1.0 and 1.0)
    public void runAtPower(double power) {
        intakeMotor.setPower(power);
    }

    // Optional: Reverse the intake (e.g., to eject artifacts)
    public void reverse() {
        intakeMotor.setPower(-1.0);
    }
}
