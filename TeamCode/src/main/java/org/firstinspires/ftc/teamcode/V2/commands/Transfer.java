package org.firstinspires.ftc.teamcode.V2.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TransferSubsystem;

public class Transfer extends CommandBase {

    private final TransferSubsystem transfer;
    private final IntakeSubsystem intake;
    private long startTime;

    public Transfer(IntakeSubsystem intake, TransferSubsystem transfer) {
        this.transfer = transfer;
        this.intake = intake;
        addRequirements(intake, transfer);
    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        intake.run();
        transfer.run();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
        transfer.stop();
    }
}