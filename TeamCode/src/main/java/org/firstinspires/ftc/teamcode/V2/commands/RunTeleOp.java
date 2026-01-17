package org.firstinspires.ftc.teamcode.V2.commands;

import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.*;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.*;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.teamcode.V2.utils.ZoneUtils.*;
import static org.firstinspires.ftc.teamcode.V2.utils.Alliance.*;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.V2.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;
import org.firstinspires.ftc.teamcode.V2.utils.ZoneUtils;


public class RunTeleOp extends ParallelCommandGroup {

    // The subsystem the command runs on

    private AutoShoot autoShoot;
    private final Robot robot = new Robot();
    private GamepadEx driverController = new GamepadEx(gamepad1);
    private GamepadEx gunnerController = new GamepadEx(gamepad2);
    Alliance alliance = BLUE;
    public RunTeleOp(Alliance alliance) {

        this.alliance = alliance;
        autoShoot = new AutoShoot(alliance);
    }


    @Override
    public void execute() {
        robot.drive.mecanumDrive();
        autoShoot.execute();
        if(driverController.getTrigger(LEFT_TRIGGER) > .5)
        {
            robot.intake.run();
        }
        else
        {
            robot.intake.stop();
        }
        driverController.getGamepadButton(A).whenPressed(new Transfer(robot.intake, robot.transfer));
    }


}