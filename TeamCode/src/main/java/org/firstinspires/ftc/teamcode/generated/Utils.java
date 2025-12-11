package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Utils {
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            //do nothing
        }
    }
    public static void sleep()
    {
        sleep(200);
    }

    /**
     * Logs outtake PIDF tuning data to telemetry for graphing in FTC Dashboard.
     * Call this in your loop to plot target vs actual velocity over time.
     * @param telemetry The telemetry object from your opmode.
     * @param targetVelocity The desired velocity.
     * @param outtakeMotor A reference DcMotorEx (e.g., center) for actual velocity.
     */
    public static void logOuttakePIDFGraph(Telemetry telemetry, double targetVelocity, DcMotorEx outtakeMotor) {
        telemetry.addData("Target Velocity", targetVelocity);
        telemetry.addData("Actual Velocity", outtakeMotor.getVelocity());
        // Optional: Log error for additional graph line
        telemetry.addData("Velocity Error", targetVelocity - outtakeMotor.getVelocity());
    }
}