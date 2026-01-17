package org.firstinspires.ftc.teamcode.V2.commands;
import static org.firstinspires.ftc.teamcode.V2.utils.ZoneUtils.*;
import static org.firstinspires.ftc.teamcode.V2.utils.Alliance.*;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.V2.subsystems.Robot;
import org.firstinspires.ftc.teamcode.V2.utils.Alliance;
import org.firstinspires.ftc.teamcode.V2.utils.ZoneUtils;


public class AutoShoot extends ParallelCommandGroup {

    // The subsystem the command runs on

    private AimTurret aimTurret;
    private Transfer transfer;
    private SetOuttakeVelocity setOuttakeVelocity;
    private final Robot robot = new Robot();
    private ZoneUtils zoneUtils = new ZoneUtils();

    Alliance alliance = BLUE;
    public AutoShoot(Alliance alliance) {

        this.alliance = alliance;
        aimTurret = new AimTurret(robot.turret, robot.limelight, alliance);
        transfer = new Transfer(robot.intake, robot.transfer);
        setOuttakeVelocity = new SetOuttakeVelocity(robot.outtake, robot.limelight, alliance);
        zoneUtils = new ZoneUtils();
    }


    @Override
    public void execute() {
        aimTurret.execute();
        setOuttakeVelocity.execute();
            if(zoneUtils.isInsideLaunchZone() && robot.limelight.hasTarget())
            {
                transfer.execute();
            }
    }


}