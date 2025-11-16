package org.firstinspires.ftc.teamcode.pedroPathing;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.function.Supplier;
import com.arcrobotics.ftclib.controller.PIDFController;
@Configurable
@TeleOp
public class XTeleOp extends OpMode {
    private Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this

    private Supplier<PathChain> pathChain;
    private TelemetryManager telemetryM;
    private boolean slowMode = false;
    private double slowModeMultiplier = 0.5;
    private Pose targetPoint = new Pose(12.573311367380562, 136.17133443163098, 0); // Set your specific target point here (x, y in inches, heading ignored)
    private boolean headingLockEnabled = false;
    private PIDFController headingPID;
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        headingPID = new PIDFController(0.8, 0, 0, 0.01);
        pathChain = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(45, 98))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(45), 0.8))
                .build();
    }

    @Override
    public void start() {
        //The parameter controls whether the Follower should use break mode on the motors (using it is recommended).
        //In order to use float mode, add .useBrakeModeInTeleOp(true); to your Drivetrain Constants in Constant.java (for Mecanum)
        //If you don't pass anything in, it uses the default (false)
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        //Call this once per loop
        follower.update();
        telemetryM.update();
        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double rotate = -gamepad1.right_stick_x;
        if (!headingLockEnabled) {
            //Make the last parameter false for field-centric
            //In case the drivers want to use a "slowMode" you can scale the vectors

            //This is the normal version to use in the TeleOp
            if (!slowMode) follower.setTeleOpDrive(
                    forward,
                    strafe,
                    rotate,
                    true // Robot Centric
            );

                //This is how it looks with slowMode on
            else follower.setTeleOpDrive(
                    forward * slowModeMultiplier,
                    strafe * slowModeMultiplier,
                    rotate * slowModeMultiplier,
                    true // Robot Centric
            );
        }

        //Automated PathFollowing
        if (gamepad1.aWasPressed()) {
            headingLockEnabled = !headingLockEnabled;
        }



        //Slow Mode
        if (gamepad1.right_bumper) {
            slowMode = true;
        }
        else {
            slowMode = false;
        }
        if (headingLockEnabled) {
            Pose currentPose = follower.getPose();
            double dx = targetPoint.getX() - currentPose.getX();
            double dy = targetPoint.getY() - currentPose.getY();
            double desiredHeading = Math.atan2(dy, dx);
            double currentHeading = currentPose.getHeading();
            double headingError = angleWrap(desiredHeading - currentHeading);
            // Unwrap setpoint to handle continuity
            double unwrappedSetPoint = currentHeading + headingError;
            headingPID.setSetPoint(unwrappedSetPoint);
            rotate = headingPID.calculate(currentHeading); // Compute rotational power using PID
        }

        // Set drive powers: field-centric (false for isRobotCentric)
        follower.setTeleOpDrive(forward, strafe, rotate, false);

        // Telemetry for debugging
        telemetry.addData("Current Pose", follower.getPose().toString());
        telemetry.addData("Target Point", targetPoint.toString());
        telemetry.addData("Heading Lock", headingLockEnabled ? "Enabled" : "Disabled");
        telemetry.update();
    }

    // Helper method to wrap angles to [-pi, pi]
    private double angleWrap(double angle) {
        while (angle > Math.PI) {
            angle -= 2 * Math.PI;
        }
        while (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        return angle;
    }
    public double getDistanceToPoint(Pose targetPoint) {
        Pose currentPose = follower.getPose();
        double dx = targetPoint.getX() - currentPose.getX();
        double dy = targetPoint.getY() - currentPose.getY();
        return Math.hypot(dx, dy); // Euclidean distance
    }
    private double getVelocityForDistance(double distance) {
        double[] distances = {19, 38.5, 53.5, 87.3, 109,128.2, 133.5, 143.9}; // Sorted ascending
        double[] velocities = {0.1, 0.2, 0.4, 0.6, 0.8, 1.0};
        if (distances.length == 0 || velocities.length != distances.length) {
            throw new IllegalStateException("Distances and velocities arrays must be non-empty and of equal length.");
        }

        int closestIndex = 0;
        double minDiff = Math.abs(distances[0] - distance);

        for (int i = 1; i < distances.length; i++) {
            double diff = Math.abs(distances[i] - distance);
            if (diff < minDiff) {
                minDiff = diff;
                closestIndex = i;
            }
        }

        return velocities[closestIndex];
    }


    }
