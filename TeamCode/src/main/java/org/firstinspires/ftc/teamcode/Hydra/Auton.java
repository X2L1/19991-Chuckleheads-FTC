package org.firstinspires.ftc.teamcode.Hydra;
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


@Autonomous(name = "Pedro Pathing Autonomous", group = "Autonomous")
@Configurable // Panels
public class Auton extends OpMode {
    private TelemetryManager panelsTelemetry; // Panels Telemetry instance
    public Follower follower; // Pedro Pathing follower instance
    private int pathState; // Current autonomous path state (state machine)
    private Paths paths; // Paths defined in the Paths class

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(72, 8, Math.toRadians(90)));

        paths = new Paths(follower); // Build paths

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    @Override
    public void loop() {
        follower.update(); // Update Pedro Pathing
        pathState = autonomousPathUpdate(); // Update autonomous state machine

        // Log values to Panels and Driver Station
        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
    }


    public static class Paths {
        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;
        public PathChain Path5;
        public PathChain Path6;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(20.652, 122.820),

                                    new Pose(66.201, 78.465)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(145))

                    .build();

            Path2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(66.201, 78.465),
                                    new Pose(55.333, 11.578),
                                    new Pose(15.183, 14.708)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(90))

                    .build();

            Path3 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(15.183, 14.708),
                                    new Pose(43.381, 68.002),
                                    new Pose(66.188, 78.049)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(140))

                    .build();

            Path4 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(66.188, 78.049),
                                    new Pose(38.756, 16.013),
                                    new Pose(22.069, 56.395)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(90))

                    .build();

            Path5 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(22.069, 56.395),

                                    new Pose(66.236, 78.433)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(140))

                    .build();

            Path6 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(66.236, 78.433),
                                    new Pose(12.315, 59.404),
                                    new Pose(27.056, 81.825)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(90))

                    .build();
        }
    }


    public int autonomousPathUpdate() {
        // Event markers will automatically trigger at their positions
        // Make sure to register NamedCommands in your RobotContainer
        return pathState;
    }


}
