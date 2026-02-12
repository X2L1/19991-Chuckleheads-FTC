package org.firstinspires.ftc.teamcode.V2.commands;

import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.A;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.B;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.DPAD_LEFT;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.DPAD_RIGHT;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.DPAD_UP;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.LEFT_BUMPER;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.X;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.LEFT_TRIGGER;
import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Trigger.RIGHT_TRIGGER;

import static org.firstinspires.ftc.teamcode.V2.subsystems.RGBSubsystem.colors.*;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.V2.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.RGBSubsystem;
import org.firstinspires.ftc.teamcode.V2.subsystems.RGBSubsystem;
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

    public PathChain ToPark;

    public Timer pathTimer;

    public int pathState;


    public RunTeleOp(Alliance alliance, HardwareMap hMap, Telemetry telemetry, GamepadEx driverController, GamepadEx gunnerController) {
        this.alliance = alliance;
        this.robot = new Robot(hMap, telemetry);
        this.driverController = driverController;
        this.gunnerController = gunnerController;
        aimTurret = new AimTurret(robot.turret, robot.limelight, alliance);

        transfer = new Transfer(robot.intake, robot.transfer);

        //addCommands(autoShoot);
    }

    public void ToPark() {
        // 1. Get the current pose from the follower
        Pose currentPose = follower.getPose();

        // 2. Build a PathChain from currentPose to ParkPose
        PathChain parkPath = follower.pathBuilder().addPath(
                new BezierLine(
                        currentPose,

                        new Pose (105.45230769230771, 33.23076923076923)
                    )
                ).setLinearHeadingInterpolation(currentPose.getHeading(), currentPose.getHeading())

                .build();

        // 3. Execute the path
        follower.followPath(parkPath);
    }

    @Override
    public void execute() {
        driverController.readButtons(); // Fix: Update gamepad state each loop
        gunnerController.readButtons(); // Fix: For reliable edge detection

        double forward = driverController.getLeftY();
        double strafe = driverController.getLeftX();
        double rotate = driverController.getRightX();
        robot.drive.mecanumDrive(forward, strafe, rotate);


            robot.turret.manualOverride = true;
            // Preset positions on gunner gamepad2
            if (gunnerController.getButton(DPAD_UP)) {
                robot.turret.setPosition(0.5);
            } else if (gunnerController.getButton(DPAD_RIGHT)) {
                robot.turret.setPosition(0.65);
            } else if (gunnerController.getButton(DPAD_LEFT)) {
                robot.turret.setPosition(0.35);
            } else if (gunnerController.getButton(X)) {
                robot.turret.setPosition(.12);
            } else if (gunnerController.getButton(B)) {
                robot.turret.setPosition(.88);
            }

        if (gunnerController.getTrigger(LEFT_TRIGGER) > 0.5) {
            robot.intake.run();
            robot.transfer.runSlow();
        } else {
            robot.intake.stop();
            robot.transfer.stop();
        }
        if(gunnerController.getButton(A))
        {
            transfer.execute();
        }
        else {
            transfer.end(true);
        }
        if(gunnerController.getTrigger(RIGHT_TRIGGER)>.2)
        {
            robot.outtake.runAtVelocity(-gunnerController.getTrigger(RIGHT_TRIGGER)*1800);
        }
        else if(gunnerController.getButton(LEFT_BUMPER))
        {
            robot.outtake.runAtPower(1);
        }
        else{
            robot.outtake.stop();
        }
        robot.rgb.setToColor(GREEN);

        if(gunnerController.getButton(B))
        {
            ToPark();
        }



        // Debug telemetry for gamepads
        robot.telemetry.addData("Driver LeftY", driverController.getLeftY());
        robot.telemetry.addData("Driver Right Trigger", driverController.getTrigger(RIGHT_TRIGGER));
        robot.telemetry.addData("Gunner LeftX", gunnerController.getLeftX());
        robot.telemetry.addData("Left Trigger", driverController.getTrigger(LEFT_TRIGGER));
        robot.telemetry.addData("A Button", driverController.getButton(A));
        robot.telemetry.addData("Turret Pos", robot.turret.getPosition());
        robot.telemetry.addData("Outtake Vel", robot.outtake.outtakeLeft.getVelocity());
        robot.telemetry.update();
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
}