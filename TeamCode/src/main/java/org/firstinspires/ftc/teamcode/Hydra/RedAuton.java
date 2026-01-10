package org.firstinspires.ftc.teamcode.Hydra;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Hydra.Utils.sleep;

@Autonomous(name = "RedAuton", group = "Autonomous")
public class RedAuton extends LinearOpMode {

    private HydraHardware hardware;
    private OuttakeSubsystem outtakeSubsystem;
    private IndexerSubsystem indexerSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private final ElapsedTime runtime = new ElapsedTime();
    private HuskyLensTester huskyLens;

    private enum AutoState {
        INIT,
        DRIVE_TO_BASKET_1,
        SHOOT_PRELOAD,
        DRIVE_TO_BASKET_2,
        SHOOT_SAMPLE_1,
        DRIVE_TO_SAMPLE_2,
        INTAKE_SAMPLE_2,
        DRIVE_TO_BASKET_3,
        SHOOT_1,
        DRIVE_TO_SAMPLE_3,
        INTAKE_SAMPLE_3,
        DRIVE_TO_BASKET_4,
        SHOOT_3,
        INTAKE_ON,
        DRIVE_TO_SPIKE_1,
        COMPLETE
    }

    private AutoState currentState = AutoState.INIT;
    private ElapsedTime stateTimer = new ElapsedTime();

    @Override
    public void runOpMode() {
        // === INITIALIZATION ===
        hardware = new HydraHardware();
        hardware.init(hardwareMap);
        huskyLens = new HuskyLensTester(hardware.huskyLens);
        outtakeSubsystem = new OuttakeSubsystem(hardware);
        indexerSubsystem = new IndexerSubsystem(hardware);
        intakeSubsystem = new IntakeSubsystem(hardware);
        hardware.pinpoint.resetPosAndIMU();
        resetDriveEncoders();

        telemetry.addData("Status", "Initialized - Ready");
        telemetry.update();

        waitForStart();
        runtime.reset();
        stateTimer.reset();

        if (!opModeIsActive()) return;

        // === STATE MACHINE LOOP ===
        while (opModeIsActive()) {
            switch (currentState) {
                case INIT:
                    currentState = AutoState.DRIVE_TO_BASKET_1;
                    stateTimer.reset();
                    break;

                case DRIVE_TO_BASKET_1:
                    driveToPosition(-787, -682, -351, -1059, 0.8);
                    currentState = AutoState.SHOOT_PRELOAD;
                    stateTimer.reset();
                    break;

                case SHOOT_PRELOAD:
                    shootSequence(1440, 100);
                    outtakeSubsystem.stop();
                    currentState = AutoState.INTAKE_ON;
                    stateTimer.reset();
                    break;

                case INTAKE_ON:
                    driveToPosition(-1087, -382, -51, -1359, 0.8);
                    currentState = AutoState.DRIVE_TO_SPIKE_1;
                    stateTimer.reset();
                    break;

                case DRIVE_TO_SPIKE_1:
                    driveToPosition(1000, 1000, -1000, -1000, 0.7);
                    stopDriveMotors();
                    driveToPosition(1000, 1000, -1000, -1000, 0.7);
                    stopDriveMotors();
                    currentState = AutoState.DRIVE_TO_BASKET_2;
                    stateTimer.reset();
                    break;

                case DRIVE_TO_BASKET_2:
                    driveToPosition(4, 450, 103, 343, 0.8);
                    stopDriveMotors();
                    currentState = AutoState.SHOOT_SAMPLE_1;
                    stateTimer.reset();
                    break;

                case SHOOT_SAMPLE_1:
                    shootSequence(1470, 100);
                    outtakeSubsystem.stop();
                    currentState = AutoState.DRIVE_TO_SAMPLE_2;
                    stateTimer.reset();
                    break;

                case DRIVE_TO_SAMPLE_2:
                    driveToPosition(1000, 1000, -1000, -1000, 0.7);
                    sleep(1500);
                    driveToPosition(1000, 1000, -1000, -1000, 0.7);
                    driveToPosition(1000, 1000, -1000, -1000, 0.7);
                    stopDriveMotors();
                    currentState = AutoState.INTAKE_SAMPLE_2;
                    stateTimer.reset();
                    break;

                case INTAKE_SAMPLE_2:
                    intakeSubsystem.run();
                    outtakeSubsystem.setVelocity(300);
                    if (stateTimer.milliseconds() > 1500) {
                        intakeSubsystem.stop();
                        driveToPosition(1000, 1000, -1000, -1000, 0.7);
                        currentState = AutoState.DRIVE_TO_BASKET_3;
                        stateTimer.reset();
                    }
                    break;

                case DRIVE_TO_BASKET_3:
                    driveToPosition(4, 450, 103, 343, 0.8);
                    stopDriveMotors();
                    currentState = AutoState.SHOOT_1;
                    stateTimer.reset();
                    break;

                case SHOOT_1:
                    shootSequence(outtakeSubsystem.blueVelocity(huskyLens), 100);
                    outtakeSubsystem.stop();
                    currentState = AutoState.DRIVE_TO_SAMPLE_3;
                    stateTimer.reset();
                    break;

                case DRIVE_TO_SAMPLE_3:
                    driveToPosition(1000, 1000, -1000, -1000, 0.7);
                    driveToPosition(1000, 1000, -1000, -1000, 0.7);
                    stopDriveMotors();
                    currentState = AutoState.INTAKE_SAMPLE_3;
                    stateTimer.reset();
                    break;

                case INTAKE_SAMPLE_3:
                    intakeSubsystem.run();
                    outtakeSubsystem.setVelocity(300);
                    if (stateTimer.milliseconds() > 1500) {
                        intakeSubsystem.stop();
                        currentState = AutoState.DRIVE_TO_BASKET_4;
                        stateTimer.reset();
                    }
                    break;

                case DRIVE_TO_BASKET_4:
                    driveToPosition(4, 450, 103, 343, 0.8);
                    stopDriveMotors();
                    currentState = AutoState.SHOOT_3;
                    stateTimer.reset();
                    break;

                case SHOOT_3:
                    shootSequence(outtakeSubsystem.blueVelocity(huskyLens), 100);
                    outtakeSubsystem.stop();
                    currentState = AutoState.COMPLETE;
                    stateTimer.reset();
                    break;

                case COMPLETE:
                    telemetry.addData("Status", "Autonomous Complete");
                    telemetry.update();
                    break;
            }

            telemetry.addData("Current State", currentState);
            telemetry.addData("State Time", "%.1f sec", stateTimer.seconds());
            telemetry.update();
        }
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

        // Wait until actually at speed (±20 tolerance)
        while (opModeIsActive() &&
                Math.abs(hardware.outtakeLeft.getVelocity() - velocity) > 20) {
            telemetry.addData("Shooter Vel", "%.0f", hardware.outtakeCenter.getVelocity());
            telemetry.update();
            idle();
        }

        // Shoot everything (quad-kick for reliability)
        for (int i = 0; i < 4; i++) {
            indexerSubsystem.kickAll();
            sleep(100); // Wait for kick to complete
            indexerSubsystem.resetAll();
            sleep(100);
        }


    }
}
