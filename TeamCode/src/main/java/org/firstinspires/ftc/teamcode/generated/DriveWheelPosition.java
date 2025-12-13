package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "WheelPositionTelemetry", group = "Diagnostic")
public class DriveWheelPosition extends LinearOpMode {

    private HydraHardware hardware;

    @Override
    public void runOpMode() {
        // Initialize hardware
        hardware = new HydraHardware();
        hardware.init(hardwareMap);
        hardware.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Wait for the start button
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // Get encoder positions for each drive wheel
            int flPosition = hardware.leftFront.getCurrentPosition();
            int frPosition = hardware.rightFront.getCurrentPosition();
            int blPosition = hardware.leftRear.getCurrentPosition();
            int brPosition = hardware.rightRear.getCurrentPosition();

            // Display positions in telemetry
            telemetry.addData("Front Left Position", flPosition);
            telemetry.addData("Front Right Position", frPosition);
            telemetry.addData("Back Left Position", blPosition);
            telemetry.addData("Back Right Position", brPosition);
            telemetry.update();
        }
    }
}