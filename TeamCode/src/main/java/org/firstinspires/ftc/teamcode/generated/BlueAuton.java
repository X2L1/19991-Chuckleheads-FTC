package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.generated.Utils.sleep;

@Autonomous(name = "BlueAuton", group = "Autonomous")
public class BlueAuton extends LinearOpMode {

    private HydraHardware hardware;
    private OuttakeSubsystem outtakeSubsystem;
    private IndexerSubsystem indexerSubsystem;

    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        // === INITIALIZATION ===
        hardware = new HydraHardware();
        hardware.init(hardwareMap);

        outtakeSubsystem = new OuttakeSubsystem(hardware);
        indexerSubsystem = new IndexerSubsystem(hardware);

        // Reset odometry at the very start (critical!)
        hardware.pinpoint.resetPosAndIMU();

        // Reset and configure drive encoders
        resetDriveEncoders();
        setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Status", "Initialized - Ready");
        telemetry.update();

        waitForStart();
        runtime.reset();

        if (!opModeIsActive()) return;

        // === STEP 1: First Movement ===
        driveToPosition(4, 450, 103, 343, 0.6);
        stopDriveMotors();

        // === STEP 2: Shoot Preload + Any Loaded Balls ===
        shootSequence(1100, 400); // velocity, delay after spin-up (ms)

        // === STEP 3: Second Movement (Park / Next Cycle) ===
        driveToPosition(-202, 1004, 970, -2544, 0.7);
        stopDriveMotors();

        outtakeSubsystem.stop();

        telemetry.addData("Status", "Autonomous Complete");
        telemetry.update();
    }

    // === HELPER METHODS ===

    private void resetDriveEncoders() {
        hardware.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void setDriveMode(DcMotor.RunMode mode) {
        hardware.leftFront.setMode(mode);
        hardware.rightFront.setMode(mode);
        hardware.leftRear.setMode(mode);
        hardware.rightRear.setMode(mode);
    }

    private void driveToPosition(int lf, int rf, int lr, int rr, double power) {
        hardware.leftFront.setTargetPosition(lf);
        hardware.rightFront.setTargetPosition(rf);
        hardware.leftRear.setTargetPosition(lr);
        hardware.rightRear.setTargetPosition(rr);

        setDriveMode(DcMotor.RunMode.RUN_TO_POSITION);

        hardware.leftFront.setPower(power);
        hardware.rightFront.setPower(power);
        hardware.leftRear.setPower(power);
        hardware.rightRear.setPower(power);

        while (opModeIsActive() &&
                (hardware.leftFront.isBusy() || hardware.rightFront.isBusy() ||
                        hardware.leftRear.isBusy() || hardware.rightRear.isBusy())) {
            telemetry.addData("Driving", "LF:%d FR:%d LR:%d RR:%d",
                    hardware.leftFront.getCurrentPosition(),
                    hardware.rightFront.getCurrentPosition(),
                    hardware.leftRear.getCurrentPosition(),
                    hardware.rightRear.getCurrentPosition());
            telemetry.update();
            idle();
        }
    }

    private void stopDriveMotors() {
        hardware.leftFront.setPower(0);
        hardware.rightFront.setPower(0);
        hardware.leftRear.setPower(0);
        hardware.rightRear.setPower(0);
    }

    private void shootSequence(double velocity, long spinUpDelayMs) {
        // Spin up shooter
        outtakeSubsystem.setVelocity(velocity);

        telemetry.addData("Shooter", "Spinning up to %.0f...", velocity);
        telemetry.update();

        sleep(spinUpDelayMs);

        // Wait until actually at speed (±50 tolerance)
        while (opModeIsActive() &&
                Math.abs(hardware.outtakeCenter.getVelocity() - velocity) > 50) {
            telemetry.addData("Shooter Vel", "%.0f", hardware.outtakeCenter.getVelocity());
            telemetry.update();

        }

        // Shoot everything (double-kick for reliability)
        for (int i = 0; i < 3; i++) {
            indexerSubsystem.kickChamber(i);
            sleep(200);
            indexerSubsystem.resetChamber(i);
            sleep(500);
        }

        // Final sweep
        indexerSubsystem.kickAll();
        sleep(200);
        indexerSubsystem.resetAll();
    }
}