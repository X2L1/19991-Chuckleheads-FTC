package org.firstinspires.ftc.teamcode.Hydra;
import static org.firstinspires.ftc.teamcode.Hydra.Utils.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;
import com.bylazar.telemetry.PanelsTelemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.Pose;


@Autonomous(name = "Pedro Pathing Autonomous Blue", group = "Autonomous")
@Configurable // Panels
public class PedroAutonomous extends OpMode {
    private TelemetryManager panelsTelemetry; // Panels Telemetry instance
    public Follower follower; // Pedro Pathing follower instance
    private int pathState; // Current autonomous path state (state machine)
    private Paths paths; // Paths defined in the Paths class
    private HydraHardware hydraHardware;
    private MecanumChassis mecanumChassis;
    private IndexerSubsystem indexerSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private OuttakeSubsystem outtakeSubsystem;

    private final int outtakevel = 1800;


    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(72, 8, Math.toRadians(90)));

        paths = new Paths(follower); // Build paths


         hydraHardware = new HydraHardware();
            hydraHardware.init(hardwareMap);
            mecanumChassis = new MecanumChassis(hydraHardware);
            indexerSubsystem = new IndexerSubsystem(hydraHardware);
            intakeSubsystem = new IntakeSubsystem(hydraHardware);
            outtakeSubsystem = new OuttakeSubsystem(hydraHardware);

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    @Override
    public void loop() {
        follower.update(); // Update Pedro Pathing
        pathState = autonomousPathUpdate(); // Update autonomous state machine
        statePathUpdate();

        // Log values to Panels and Driver Station
        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
    }

public void statePathUpdate() {
    switch(pathState) {
        case 1:
            follower.followPath(paths.startToShoot, true);
            pathState = 1;
            intakeSubsystem.run();
            outtakeSubsystem.setVelocity(outtakevel);
            break;
        case 1:
            if (!follower.isBusy()) {
                indexerSubsystem.kickAll();
                sleep(2700);
                pathState = 2;
            }
            break;
        case 2:
            if (!follower.isBusy()) {
                follower.followPath([paths.shootToIntake1], true);
                pathState = 3;
                indexerSubsystem.resetAll();
            }
        case 3:
            if (!follower.isBusy()) {
                follower.followPath(paths.intake1ToShoot, true);
                pathState = 4;

            }

            break;
        case 4:
            if (!follower.isBusy()) {
                indexerSubsystem.kickAll();
                sleep(200);
                pathState = 5;
            }
            break;
        case 5:
            if (!follower.isBusy()) {
                follower.followPath(paths.shootToIntake2, true);
                pathState = 6;
                indexerSubsystem.resetAll();

            }
            break;
        case 6:
            if (!follower.isBusy()) {
                follower.followPath(paths.intake2ToShoot, true);
                pathState = 7;

            }
            break;
        case 7:
            if (!follower.isBusy()) {
                indexerSubsystem.kickAll();
                sleep(200);
                pathState = 8;
            }
            break;
        case 8:
            if (!follower.isBusy()) {
                follower.followPath(paths.shootToIntake3, true);
                pathState = 9;
                indexerSubsystem.resetAll();
            }
            break;
        case 9:
            if (!follower.isBusy()) {
                follower.followPath(paths.intake3ToShoot, true);
                pathState = 10;
            }
            break;
        case 10:
            if (!follower.isBusy()) {
                indexerSubsystem.kickAll();
                sleep(200);
                pathState = 11;
            }
            break;
        case 11:
            if (!follower.isBusy()) {
                follower.followPath(paths.shootToPark, true);
                pathState = 12;
                indexerSubsystem.resetAll();
            }
            break;
        case 12:
            if (!follower.isBusy()) {
                intakeSubsystem.stop();
            }
            break;
    }
}
    public static class Paths {
        public PathChain startToShoot;
        public PathChain shootToIntake1;
        public PathChain intake1ToShoot;
        public PathChain shootToIntake2;
        public PathChain intake2ToShoot;
        public PathChain shootToIntake3;
        public PathChain intake3ToShoot;
        public PathChain shootToPark;


        public Paths(Follower follower) {
            startToShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(20.652, 122.820),

                                    new Pose(66.201, 78.465)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(48))

                    .build();

            shootToIntake1 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(66.201, 78.465),
                                    new Pose(56.282, 5.410),
                                    new Pose(18.030, 19.928)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(90))

                    .build();

            intake1ToShoot = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(18.030, 19.928),
                                    new Pose(43.381, 68.002),
                                    new Pose(66.188, 78.049)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(140))

                    .build();

            shootToIntake2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(66.188, 78.049),
                                    new Pose(38.993, 11.506),
                                    new Pose(22.069, 56.395)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(90))

                    .build();

            intake2ToShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(22.069, 56.395),

                                    new Pose(66.236, 78.433)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(140))

                    .build();

            shootToIntake3 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(66.236, 78.433),
                                    new Pose(12.315, 59.404),
                                    new Pose(27.056, 81.825)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(90))

                    .build();

            intake3ToShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(27.056, 81.825),

                                    new Pose(66.241, 78.563)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(140))

                    .build();

            shootToPark = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(66.241, 78.563),

                                    new Pose(73.811, 93.539)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();
        }
    }


    public int autonomousPathUpdate() {
        // Event markers will automatically trigger at their positions
        // Make sure to register NamedCommands in your RobotContainer
        return pathState;
    }



}

