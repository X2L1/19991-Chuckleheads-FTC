package org.firstinspires.ftc.teamcode.V2.opmodes; // make sure this aligns with class location
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.V2.utils.StateMachines.CloseZone12Red;
import org.firstinspires.ftc.teamcode.V2.utils.StateMachines.CloseZoneTwelve;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Close12Red", group = "OpModes")
public class Close12AutoRed extends OpMode {
    CloseZone12Red machine;
    @Override
    public void init() {
        machine = new CloseZone12Red(hardwareMap, telemetry);
        machine.pathTimer = new Timer();
        machine.opmodeTimer = new Timer();
        machine.opmodeTimer.resetTimer();
        machine.follower = Constants.createFollower(hardwareMap);
        machine.buildPaths();
        Pose startPose = new Pose(23.499, 124.956, Math.toRadians(143)).mirror();
        machine.follower.setStartingPose(startPose);
    }
    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}
    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        machine.opmodeTimer.resetTimer();
        machine.setPathState(0);
    }
    @Override
    public void loop() {
        machine.autonomousPathUpdate();
        machine.follower.update();

        telemetry.addData("state", machine.pathState);
    }
    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {}
}