package org.firstinspires.ftc.teamcode.V2.commands;

import static org.firstinspires.ftc.teamcode.V2.utils.Alliance.*;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;
import org.firstinspires.ftc.teamcode.V2.utils.ZoneUtils;

public class AutoShoot extends ParallelCommandGroup {

    private AimTurret aimTurret;
    private Transfer transfer;
    private SetOuttakeVelocity setOuttakeVelocity;
    private final Robot robot;
    private final ZoneUtils zoneUtils;
    private final Alliance alliance;

    public AutoShoot(Alliance alliance, HardwareMap hMap) {
        this.alliance = alliance;
        this.robot = new Robot(hMap);
        this.zoneUtils = new ZoneUtils(hMap);
        zoneUtils.initZone(); // Initialize zones

        aimTurret = new AimTurret(robot.turret, robot.limelight, alliance);
        transfer = new Transfer(robot.intake, robot.transfer);
        setOuttakeVelocity = new SetOuttakeVelocity(robot.outtake, robot.limelight, alliance);

        addCommands(aimTurret, setOuttakeVelocity, transfer);
        addRequirements(robot.turret, robot.limelight, robot.outtake, robot.transfer, robot.intake);
    }

    @Override
    public void execute() {
        zoneUtils.syncZonesWithPositions(); // Sync every tick
        aimTurret.execute();
        setOuttakeVelocity.execute();
        if (zoneUtils.isInsideLaunchZone() && robot.limelight.hasTarget() && !transfer.isScheduled()) {
            transfer.schedule(); // Schedule conditionally to avoid repeats
        }
    }
}