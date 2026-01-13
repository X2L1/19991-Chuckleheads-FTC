package org.firstinspires.ftc.teamcode.V2.commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.TransferSubsystem;


public class Transfer extends InstantCommand {

    // The subsystem the command runs on
    private final TransferSubsystem transfer;
    private final IntakeSubsystem intake;
    public Transfer(IntakeSubsystem intake, TransferSubsystem transfer) {
        this.transfer = transfer;
        this.intake = intake;
        addRequirements(intake, transfer);
    }
    public Transfer(TransferSubsystem transfer, IntakeSubsystem intake) {
        this.transfer = transfer;
        this.intake = intake;
        addRequirements(intake, transfer);
    }

    @Override
    public void execute() {
        intake.run();
        transfer.run();
    }


}
