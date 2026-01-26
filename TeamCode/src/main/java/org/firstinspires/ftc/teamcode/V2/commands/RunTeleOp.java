package org.firstinspires.ftc.teamcode.V2.commands;

import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.A;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.LEFT_TRIGGER;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;

public class RunTeleOp extends ParallelCommandGroup {

    private AutoShoot autoShoot;
    private final Robot robot;
    private GamepadEx driverController;
    private GamepadEx gunnerController;
    private final Alliance alliance;
    private static final double DEADZONE = 0.1;

    public RunTeleOp(Alliance alliance, HardwareMap hMap, GamepadEx driverController, GamepadEx gunnerController) {
        this.alliance = alliance;
        this.robot = new Robot(hMap);
        this.driverController = driverController;
        this.gunnerController = gunnerController;
        autoShoot = new AutoShoot(alliance, hMap);

        addCommands(autoShoot);
    }

    @Override
    public void execute() {
        driverController.readButtons(); // Fix: Update gamepad state each loop
        gunnerController.readButtons(); // Fix: For reliable edge detection

        robot.drive.mecanumDrive();

        double manualPower = gunnerController.getLeftX();
        if (Math.abs(manualPower) > DEADZONE) {
            robot.turret.manualOverride = true;
            robot.turret.setPower(manualPower);
        } else {
            robot.turret.manualOverride = false;
            autoShoot.execute();
        }

        if (driverController.getTrigger(LEFT_TRIGGER) > 0.5) {
            robot.intake.run();
        } else {
            robot.intake.stop();
        }
        driverController.getGamepadButton(A).whenPressed(new Transfer(robot.intake, robot.transfer));

        // Debug telemetry for gamepads
        robot.telemetry.addData("Driver LeftY", driverController.getLeftY());
        robot.telemetry.addData("Gunner LeftX", gunnerController.getLeftX());
        robot.telemetry.addData("Left Trigger", driverController.getTrigger(LEFT_TRIGGER));
        robot.telemetry.addData("A Button", driverController.getButton(A));
        robot.telemetry.update();
    }
}