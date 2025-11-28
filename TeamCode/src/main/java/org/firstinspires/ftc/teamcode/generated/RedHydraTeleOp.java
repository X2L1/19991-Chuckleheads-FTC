package org.firstinspires.ftc.teamcode.generated;

import static org.firstinspires.ftc.teamcode.generated.Utils.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "RedHydraTeleOp", group = "TeleOp")
public class RedHydraTeleOp extends OpMode {

    private HydraHardware hardware;
    private IntakeSubsystem intakeSubsystem;
    private OuttakeSubsystem outtakeSubsystem;
    private IndexerSubsystem indexerSubsystem;
    private enum RGBMode {
        INDEX,
        AIM_ASSIST
    }
    private RGBMode currentRGBMode = RGBMode.INDEX;
    private RGBSubsystem rgbSubsystem;

    @Override
    public void init() {
        hardware = new HydraHardware();
        hardware.init(hardwareMap);

        intakeSubsystem = new IntakeSubsystem(hardware);
        outtakeSubsystem = new OuttakeSubsystem(hardware);
        indexerSubsystem = new IndexerSubsystem(hardware);
        rgbSubsystem = new RGBSubsystem(hardware);
        // Reset odometry position and IMU at start
        hardware.pinpoint.resetPosAndIMU();

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
        double strafe = gamepad1.right_stick_x;
        double turn = gamepad1.left_stick_x;

        

        

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
            intakeSubsystem.run();
        } else {
            intakeSubsystem.stop();
        }

            outtakeSubsystem.setVelocity(gamepad2.right_trigger*600);


       

        // Indexer kicks
        if (gamepad2.dpad_down) {
            indexerSubsystem.kickAll();
            sleep();
            indexerSubsystem.resetAll();
        } else if (gamepad2.dpad_left) {
            indexerSubsystem.kickPurple();
            sleep();
            indexerSubsystem.resetAll();
        } else if (gamepad2.dpad_right) {
            indexerSubsystem.kickGreen();
            sleep();
            indexerSubsystem.resetAll();
        }

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
            rgbSubsystem.enableAlignmentAid(-1524, 1524); // Example target coordinates
        }
        if(gamepad1.a){
            currentRGBMode = RGBMode.INDEX;
            rgbSubsystem.disableAlignmentAid();
        }
        else if(gamepad2.b){
            currentRGBMode = RGBMode.AIM_ASSIST;
        }

        // Telemetry
        //telemetry.addData("Heading Lock", headingLockEnabled ? "Enabled" : "Disabled");
        telemetry.addData("Current Heading", hardware.pinpoint.getPosition().getHeading(AngleUnit.DEGREES));
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
