package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "BlueAuton", group = "Autonomous")
public class BlueAuton extends LinearOpMode {

    private HydraHardware hardware;
    private OuttakeSubsystem outtakeSubsystem;
    private IndexerSubsystem indexerSubsystem;

    @Override
    public void runOpMode() {
        // Initialize hardware and subsystems
        hardware = new HydraHardware();
        hardware.init(hardwareMap);

        outtakeSubsystem = new OuttakeSubsystem(hardware);
        indexerSubsystem = new IndexerSubsystem(hardware);

        // Reset encoders and set modes for drive motors
        hardware.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // First movement: Set target positions and move
            hardware.leftFront.setTargetPosition(4);
            hardware.rightFront.setTargetPosition(450);
            hardware.leftRear.setTargetPosition(103);
            hardware.rightRear.setTargetPosition(343);
            hardware.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // Set power to move (adjust sign based on directions; assume positive moves to positive targets)
            double movePower = 0.5; // Adjust as needed
            hardware.leftFront.setPower(movePower);
            hardware.rightFront.setPower(movePower);
            hardware.leftRear.setPower(movePower);
            hardware.rightRear.setPower(movePower);

            // Wait until all motors reach targets
            while (opModeIsActive() &&
                    (hardware.leftFront.isBusy() || hardware.rightFront.isBusy() ||
                            hardware.leftRear.isBusy() || hardware.rightRear.isBusy())) {
                telemetry.addData("Status", "Moving to first position");
                telemetry.update();
            }

            // Stop drive motors
            hardware.leftFront.setPower(0);
            hardware.rightFront.setPower(0);
            hardware.leftRear.setPower(0);
            hardware.rightRear.setPower(0);

            // Set outtake velocity to 1100 using subsystem
            double targetVel = 950;
            outtakeSubsystem.setVelocity(targetVel);
            sleep(3000); // Initial wait before checking velocity
            // Wait until velocity reaches target (check center motor as representative)
            while (opModeIsActive() && hardware.outtakeCenter.getVelocity() == targetVel) { // Tolerance of 0
                telemetry.addData("Status", "Ramping up shooter");
                telemetry.addData("Current Velocity", hardware.outtakeCenter.getVelocity());
                telemetry.update();
                sleep(10);
            }

            // Kick left chamber using subsystem (assuming index 0 is left)
            indexerSubsystem.kickChamber(0);
            sleep(500); // Hold time; adjust as needed
            indexerSubsystem.resetChamber(0);
            sleep(500);
            indexerSubsystem.kickChamber(0);
            sleep(500); // Hold time; adjust as needed
            indexerSubsystem.resetChamber(0);
            outtakeSubsystem.setVelocity(1100);
            // Wait 1 second
            sleep(3000);

            // Kick center chamber (index 1)
            indexerSubsystem.kickChamber(1);
            sleep(500);
            indexerSubsystem.resetChamber(1);
            sleep(500);
            indexerSubsystem.kickChamber(1);
            sleep(500); // Hold time; adjust as needed
            indexerSubsystem.resetChamber(1);
            sleep(3000);
            indexerSubsystem.kickChamber(2);
            sleep(500);
            indexerSubsystem.resetChamber(2);
            sleep(500);
            indexerSubsystem.kickChamber(2);
            sleep(500); // Hold time; adjust as needed
            indexerSubsystem.resetChamber(2);
            // Wait 1 second
            sleep(3000);

            // Kick all chambers using subsystem
            indexerSubsystem.kickAll();
            sleep(500);
            indexerSubsystem.resetAll();

            // Wait 0.5 seconds
            sleep(500);

            // Second movement: Set new target positions
            hardware.leftFront.setTargetPosition(-202);
            hardware.rightFront.setTargetPosition(1004);
            hardware.leftRear.setTargetPosition(970);
            hardware.rightRear.setTargetPosition(-2544);
            hardware.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // Set power to move
            hardware.leftFront.setPower(movePower);
            hardware.rightFront.setPower(movePower);
            hardware.leftRear.setPower(movePower);
            hardware.rightRear.setPower(movePower);

            // Wait until all motors reach targets
            while (opModeIsActive() &&
                    (hardware.leftFront.isBusy() || hardware.rightFront.isBusy() ||
                            hardware.leftRear.isBusy() || hardware.rightRear.isBusy())) {
                telemetry.addData("Status", "Moving to second position");
                telemetry.update();
            }

            // Stop drive motors
            hardware.leftFront.setPower(0);
            hardware.rightFront.setPower(0);
            hardware.leftRear.setPower(0);
            hardware.rightRear.setPower(0);

            // Stop outtake using subsystem
            outtakeSubsystem.stop();

            telemetry.addData("Status", "Autonomous Complete");
            telemetry.update();
        }
    }
}