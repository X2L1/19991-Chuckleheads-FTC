package org.firstinspires.ftc.teamcode.V2.commands;

import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.LEFT_TRIGGER;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.A;
import static org.firstinspires.ftc.teamcode.V2.utils.Alliance.*;

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
        robot.drive.mecanumDrive();
        autoShoot.execute();
        if (driverController.getTrigger(LEFT_TRIGGER) > 0.5) {
            robot.intake.run();
        } else {
            robot.intake.stop();
        }
        driverController.getGamepadButton(A).whenPressed(new Transfer(robot.intake, robot.transfer));
    }
}