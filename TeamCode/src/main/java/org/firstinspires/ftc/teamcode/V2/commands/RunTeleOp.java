package org.firstinspires.ftc.teamcode.V2.commands;

import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.A;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.B;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.DPAD_LEFT;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.DPAD_RIGHT;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.DPAD_UP;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.X;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.LEFT_TRIGGER;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.RIGHT_TRIGGER;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.V2.subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;

public class RunTeleOp extends ParallelCommandGroup {

    private AimTurret aimTurret;
    private Transfer transfer;
    private final Robot robot;
    private GamepadEx driverController;
    private GamepadEx gunnerController;
    private final Alliance alliance;
    private static final double DEADZONE = 0.1;


    public RunTeleOp(Alliance alliance, HardwareMap hMap, Telemetry telemetry, GamepadEx driverController, GamepadEx gunnerController) {
        this.alliance = alliance;
        this.robot = new Robot(hMap, telemetry);
        this.driverController = driverController;
        this.gunnerController = gunnerController;
        aimTurret = new AimTurret(robot.turret, robot.limelight, alliance);

        transfer = new Transfer(robot.intake, robot.transfer);

        //addCommands(autoShoot);
    }

    @Override
    public void execute() {
        driverController.readButtons(); // Fix: Update gamepad state each loop
        gunnerController.readButtons(); // Fix: For reliable edge detection

        double forward = driverController.getLeftY();
        double strafe = driverController.getLeftX();
        double rotate = driverController.getRightX();
        robot.drive.mecanumDrive(forward, strafe, rotate);

        if (gunnerController.getTrigger(RIGHT_TRIGGER) > 0.5) {
            robot.turret.manualOverride = false;
            aimTurret.execute(); // Auto-align with Limelight
        } else {
            robot.turret.manualOverride = true;
            // Preset positions on gunner gamepad2
            if (gunnerController.getButton(DPAD_UP)) {
                robot.turret.setPosition(0.5);
            } else if (gunnerController.getButton(DPAD_RIGHT)) {
                robot.turret.setPosition(0.75);
            } else if (gunnerController.getButton(DPAD_LEFT)) {
                robot.turret.setPosition(0.25);
            } else if (gunnerController.getButton(X)) {
                robot.turret.setPosition(0.0);
            } else if (gunnerController.getButton(B)) {
                robot.turret.setPosition(1.0);
            }
        }

        if (driverController.getTrigger(LEFT_TRIGGER) > 0.5) {
            robot.intake.run();
        } else {
            robot.intake.stop();
        }
        if(driverController.getButton(A))
        {
            transfer.execute();
        }
        else {
            transfer.end(true);
        }
        if(driverController.getTrigger(RIGHT_TRIGGER)>.2)
        {
            robot.outtake.runAtVelocity(-driverController.getTrigger(RIGHT_TRIGGER)*1800);
        }
        else{
            robot.outtake.stop();
        }



        // Debug telemetry for gamepads
        robot.telemetry.addData("Driver LeftY", driverController.getLeftY());
        robot.telemetry.addData("Driver Right Trigger", driverController.getTrigger(RIGHT_TRIGGER));
        robot.telemetry.addData("Gunner LeftX", gunnerController.getLeftX());
        robot.telemetry.addData("Left Trigger", driverController.getTrigger(LEFT_TRIGGER));
        robot.telemetry.addData("A Button", driverController.getButton(A));
        robot.telemetry.addData("Turret Pos", robot.turret.getPosition());
        robot.telemetry.update();
    }
}