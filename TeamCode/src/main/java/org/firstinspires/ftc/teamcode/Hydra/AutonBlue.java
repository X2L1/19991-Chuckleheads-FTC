package org.firstinspires.ftc.teamcode.Hydra;
import static org.firstinspires.ftc.teamcode.Hydra.Utils.sleep;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.Pose;


@Autonomous(name = "Pedro Pathing Autonomous Blue", group = "Autonomous")
@Configurable // Panels
public class AutonBlue extends OpMode {
    private TelemetryManager panelsTelemetry; // Panels Telemetry instance
    public Follower follower; // Pedro Pathing follower instance
    public OuttakeSubsystem outtakeSubsystem;

    public IntakeSubsystem intakeSubsystem;
    public IndexerSubsystem indexerSubsystem;
    public HydraHardware hydraHardware;
    private Timer pathTimer, opModeTimer;// Current autonomous path state (state machine)

    public enum PathState {
        START_TO_SHOOT,

        SHOOT_0,
        SHOOT_TO_INTAKE,
        INTAKE_TO_SHOOT,

        SHOOT_1,
        SHOOT_TO_INTAKE2,

        INTAKE2_TO_SHOOT,

        SHOOT_2,

        SHOOT_TO_INTAKE3,

        INTAKE3_TO_SHOOT,

        SHOOT_3,

        SHOOT_T0_PARK,

        PARK,
    }

    PathState pathState;

    private final Pose startPose = new Pose(20.652388797364086, 122.82042833607909, Math.toRadians(144));
    private final Pose shootPose = new Pose(66.20098846787481, 78.46457990115319, Math.toRadians(145));

    private final Pose intake1 = new Pose(15.182866556836903, 14.708401976935736, Math.toRadians(90));
    private final Pose intake2 = new Pose(22.069192751235576, 56.39538714991763,
            Math.toRadians(90));
    private final Pose intake3 = new Pose(27.056013179571668, 81.82537067545306, Math.toRadians(90));
    private final Pose park = new Pose(73.81054365733115, 93.53871499176277, Math.toRadians(90));

    public final int outtakevel = 1800;

    private PathChain startToShoot1;
    private PathChain shootToIntake1;

    private PathChain intake1ToShoot;
    private PathChain shootToIntake2;
    private PathChain intake2ToShoot;
    private PathChain shootToIntake3;
    private PathChain intake3ToShoot;
    private PathChain shootToPark;
    private PathChain parkstop;


    public void buildPaths() {
        startToShoot1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                startPose, shootPose
                        )
                )
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
        shootToIntake1 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                shootPose, new Pose(55.333, 11.578), intake1
                        )
                )
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())

                .build();
        intake1ToShoot = follower.pathBuilder().addPath(
                        new BezierCurve(
                                intake1, new Pose(43.381, 68.002), shootPose
                        )
                )
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())

                .build();
        shootToIntake2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                shootPose, new Pose(38.756, 16.013), intake2
                        )
                )
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())

                .build();
        intake2ToShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                intake2, shootPose)
                )
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())

                .build();
        shootToIntake3 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                shootPose, new Pose(12.315, 59.404), intake3)
                )
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())

                .build();
        intake3ToShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                intake3, shootPose)
                )
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())

                .build();
        shootToPark = follower.pathBuilder().addPath(
                        new BezierLine(
                                shootPose, park)
                )
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())

                .build();
        parkstop = follower.pathBuilder().addPath(
                        new BezierLine(
                                park, park)
                )
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())

                .build();
    }
    public void statePathUpdate() {
        switch(pathState) {
            case START_TO_SHOOT:
                follower.followPath(startToShoot1, true);
                setPathState(PathState.SHOOT_0);
                intakeSubsystem.run();
                outtakeSubsystem.setVelocity(outtakevel);
                break;
            case SHOOT_0:
                if (!follower.isBusy()) {
                    indexerSubsystem.kickAll();
                    sleep(200);
                }
                break;
            case SHOOT_TO_INTAKE:
                if (!follower.isBusy()) {
                    follower.followPath(shootToIntake1, true);
                    setPathState(PathState.INTAKE_TO_SHOOT);
                    indexerSubsystem.resetAll();
                }
            case INTAKE_TO_SHOOT:
                if (!follower.isBusy()) {
                    follower.followPath(intake1ToShoot, true);
                    setPathState(PathState.SHOOT_1);

                }

                break;
            case SHOOT_1:
                if (!follower.isBusy()) {
                    indexerSubsystem.kickAll();
                    sleep(200);
                    setPathState(PathState.SHOOT_TO_INTAKE2);
                }
                break;
            case SHOOT_TO_INTAKE2:
                if (!follower.isBusy()) {
                    follower.followPath(shootToIntake2, true);
                    setPathState(PathState.INTAKE2_TO_SHOOT);
                    indexerSubsystem.resetAll();

                }
                break;
            case INTAKE2_TO_SHOOT:
                if (!follower.isBusy()) {
                    follower.followPath(intake2ToShoot, true);
                    setPathState(PathState.SHOOT_2);

                }
                break;
            case SHOOT_2:
                if (!follower.isBusy()) {
                    indexerSubsystem.kickAll();
                    sleep(200);
                    setPathState(PathState.SHOOT_TO_INTAKE3);
                }
                break;
            case SHOOT_TO_INTAKE3:
                if (!follower.isBusy()) {
                    follower.followPath(shootToIntake3, true);
                    setPathState(PathState.INTAKE3_TO_SHOOT);
                    indexerSubsystem.resetAll();
                }
                break;
            case INTAKE3_TO_SHOOT:
                if (!follower.isBusy()) {
                    follower.followPath(intake3ToShoot, true);
                    setPathState(PathState.SHOOT_3);
                }
                break;
            case SHOOT_3:
                if (!follower.isBusy()) {
                    indexerSubsystem.kickAll();
                    sleep(200);
                    setPathState(PathState.SHOOT_T0_PARK);
                }
                break;
            case SHOOT_T0_PARK:
                if (!follower.isBusy()) {
                    follower.followPath(shootToPark, true);
                    setPathState(PathState.PARK);
                    indexerSubsystem.resetAll();
                }
                break;
            case PARK:
                if (!follower.isBusy()) {
                    intakeSubsystem.stop();
                }
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }
    @Override
    public void init() {
        pathState = PathState.START_TO_SHOOT; // Initialize path state
        pathTimer = new Timer();
        opModeTimer = new Timer();
        hydraHardware = new HydraHardware();
hydraHardware.init(hardwareMap);
        follower = Constants.createFollower(hardwareMap); // Create Pedro Pathing follower

        buildPaths(); // Build paths
        follower.setPose(startPose); // Set starting pose
    }

    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathState); // Start first path
    }
    @Override
    public void loop() {
        follower.update(); // Update Pedro Pathing
        statePathUpdate();
        // Log values to Panels and Driver Station
        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
    }





}
