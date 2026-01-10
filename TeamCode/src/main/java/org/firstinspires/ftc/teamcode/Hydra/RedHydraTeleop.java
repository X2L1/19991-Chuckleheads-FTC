package org.firstinspires.ftc.teamcode.Hydra;
import static org.firstinspires.ftc.teamcode.Hydra.Utils.*;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "RedHydraTeleOp", group = "TeleOp")
public class RedHydraTeleop extends OpMode {

    private HydraHardware hardware;
    private IntakeSubsystem intakeSubsystem;
    private OuttakeSubsystem outtakeSubsystem;
    private IndexerSubsystem indexerSubsystem;
    private HuskyLensTester huskyLens;
    private enum RGBMode {
        INDEX,
        AIM_ASSIST,
        SHOOTER_STATUS // New mode for shooter velocity feedback
    }
    private enum ShooterMode
    {
        FAR_ZONE,
        CLOSE_ZONE
    }
    private RGBMode currentRGBMode = RGBMode.SHOOTER_STATUS; // Set new mode as default
    private RGBSubsystem rgbSubsystem;
    private ShooterMode shooterMode = ShooterMode.CLOSE_ZONE;
    private double targetVelocity;
    private boolean velocityReached = false; // Flag to check if target velocity is reached

    @Override
    public void init() {
        hardware = new HydraHardware();
        hardware.init(hardwareMap);

        intakeSubsystem = new IntakeSubsystem(hardware);
        outtakeSubsystem = new OuttakeSubsystem(hardware);
        indexerSubsystem = new IndexerSubsystem(hardware);
        rgbSubsystem = new RGBSubsystem(hardware);
        huskyLens = new HuskyLensTester(hardware.huskyLens);
        // Reset odometry position and IMU at start
        hardware.pinpoint.resetPosAndIMU();
        //outtakeSubsystem.setPIDFCoefficients(PIDFCoefficients.getOuttakeP(), PIDFCoefficients.getOuttakeI(), PIDFCoefficients.getOuttakeD(), PIDFCoefficients.getOuttakeF());
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Update odometry
        hardware.pinpoint.update();

        // Drive controls (robot-centric mecanum)
        //Strafe and turn inverted due to motor orientation
        double forward = -gamepad1.left_stick_y; // Negative for inversion if needed
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;





        // Mecanum drive powers
        double frontLeftPower = forward + strafe + turn;
        double frontRightPower = forward - strafe - turn;
        double backLeftPower = forward - strafe + turn;
        double backRightPower = forward + strafe - turn;

        // Normalize powers if exceeding 1.0
        double maxPower = Math.max(1.0, Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)))));
        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }

        // Set motor powers
        hardware.leftFront.setPower(frontLeftPower);
        hardware.rightFront.setPower(frontRightPower);
        hardware.leftRear.setPower(backLeftPower);
        hardware.rightRear.setPower(backRightPower);

        // Gamepad2 controls
        // Intake
        if (gamepad2.left_trigger > .1) {
            //intakeSubsystem.run();
            //targetVelocity = 300;
            //HUMAN PLAYER FEEDING MODE
            targetVelocity = -300;
            outtakeSubsystem.setVelocity(targetVelocity);

        } else {
            intakeSubsystem.stop();

        }

        if(gamepad2.right_trigger == 0 && gamepad2.left_trigger == 0)
        {
            targetVelocity = 0; //Stop outtake
        }


        // Indexer kicks
        if (gamepad2.dpad_down) {
            indexerSubsystem.kickAll();
            sleep();
            indexerSubsystem.resetAll();
        } else if (gamepad2.dpad_left) {
            indexerSubsystem.kickChamber(0); // Left chamber
            sleep();
            indexerSubsystem.resetAll();
        } else if (gamepad2.dpad_right) {
            indexerSubsystem.kickChamber(2); // Right chamber
            sleep();
            indexerSubsystem.resetAll();
        }
        else if (gamepad2.dpad_up) {
            indexerSubsystem.kickChamber(1); // Center chamber
            sleep();
            indexerSubsystem.resetAll();
        }
        outtakeSubsystem.setVelocity(targetVelocity);
        sleep(100);

        // Extension
        if (gamepad2.left_bumper) {
            hardware.leftScrew.setPower(1.0); // Extend
            hardware.rightScrew.setPower(1.0);
        } else if (gamepad2.right_bumper) {
            hardware.leftScrew.setPower(-1.0); // Retract
            hardware.rightScrew.setPower(-1.0);
        } else {
            hardware.leftScrew.setPower(0.0);
            hardware.rightScrew.setPower(0.0);
        }
        if(currentRGBMode == RGBMode.INDEX){
            // Set RGB lights to index mode
            rgbSubsystem.setToBallColors();
        }
        else if(currentRGBMode == RGBMode.AIM_ASSIST){
            // Set RGB lights to aim assist mode
            rgbSubsystem.enableAlignmentAid(-1524, -1524); // Example target coordinates; adjust to actual goal position
            rgbSubsystem.update();
        }
        if(gamepad2.psWasPressed())
        {
            intakeSubsystem.reverse();
            sleep(1000);
            intakeSubsystem.stop();
        }
        else if(currentRGBMode == RGBMode.SHOOTER_STATUS) {
            // Check if target velocity is reached (with tolerance)
            double currentVel = hardware.outtakeLeft.getVelocity(); // Use center as reference
            velocityReached = Math.abs(currentVel - (targetVelocity)) < 50; // 50 unit tolerance

            if (velocityReached) {
                // Flash rainbow when velocity reached
                rgbSubsystem.setColor(hardware.leftRGB, "indigo");
                rgbSubsystem.setColor(hardware.centerRGB, "indigo");
                rgbSubsystem.setColor(hardware.rightRGB, "indigo");

                rgbSubsystem.update(); // This will run the rainbow flashing
            } else {
                // Set solid color based on shooter mode / velocity
                String color = (shooterMode == ShooterMode.CLOSE_ZONE) ? "red" : "green";
                rgbSubsystem.setColor(hardware.leftRGB, color);
                rgbSubsystem.setColor(hardware.centerRGB, color);
                rgbSubsystem.setColor(hardware.rightRGB, color);
            }
        }
        if(gamepad2.a){
            indexerSubsystem.kickPurple();
            sleep();
            indexerSubsystem.resetAll();
        }
        else if(gamepad2.b){
            indexerSubsystem.kickGreen();
            sleep();
            indexerSubsystem.resetAll();
        }

        if(shooterMode == ShooterMode.FAR_ZONE){
            if(gamepad2.right_trigger > .2)
            {
                targetVelocity = outtakeSubsystem.redVelocity(huskyLens); // Far zone velocity
            }

            if(gamepad2.y){
                shooterMode = ShooterMode.CLOSE_ZONE;
            }
        }
        else if(shooterMode == ShooterMode.CLOSE_ZONE){
            if(gamepad2.right_trigger > .2)
            {
                targetVelocity = outtakeSubsystem.redVelocity(huskyLens); // Close zone velocity
            }
            if(gamepad2.y){
                shooterMode = ShooterMode.FAR_ZONE;
            }
        }

        // Telemetry
        //telemetry.addData("Heading Lock", headingLockEnabled ? "Enabled" : "Disabled");
        telemetry.addData("Current Heading", hardware.pinpoint.getPosition().getHeading(AngleUnit.DEGREES));
        telemetry.addData("Outtake Velocity", hardware.outtakeCenter.getVelocity());
        telemetry.addData("Intended Velocity on Code", targetVelocity);
        telemetry.addData("Distance from tag: ", huskyLens.getDistance(2));
        telemetry.update();
    }

    @Override
    public void stop() {
        // Stop all subsystems
        intakeSubsystem.stop();
        outtakeSubsystem.stop();
        indexerSubsystem.resetAll();
        hardware.leftScrew.setPower(0.0);
        hardware.rightScrew.setPower(0.0);
    }


}