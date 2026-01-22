package org.firstinspires.ftc.teamcode.V2.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.utils.ZoneUtils;

// Helper command for auto intake (always on outside zones)
class AutoIntakeCommand extends CommandBase {
    private final IntakeSubsystem intake;
    HardwareMap hMap;
    private final ZoneUtils zoneUtils = new ZoneUtils(hMap);
    private static final double INTAKE_POWER = 1.0; // Tune as needed

    public AutoIntakeCommand(IntakeSubsystem intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void execute() {
        if (!zoneUtils.isInsideLaunchZone()) {
            intake.run(); // Run vector wheels for intake
        } else {
            intake.stop(); // Stop intake in launch zones to avoid interference
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}
