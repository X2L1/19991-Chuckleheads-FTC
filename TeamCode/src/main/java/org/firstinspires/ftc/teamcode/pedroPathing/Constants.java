package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(16.60148)
            .forwardZeroPowerAcceleration(-54.65545654)
            .lateralZeroPowerAcceleration(-72.3683974165484)
            .translationalPIDFCoefficients(new PIDFCoefficients(.2,.0001,.03,.02))
            .headingPIDFCoefficients(new PIDFCoefficients(1.1,.1,.01,.03))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(.2,.0001,.03,.6,.02))
            .centripetalScaling(.0005);
    public static MecanumConstants mecanumConstants = new MecanumConstants()
            .useBrakeModeInTeleOp(true)
            .maxPower(1)
            .rightFrontMotorName("frontRight")
            .rightRearMotorName("backRight")
            .leftRearMotorName("backLeft")
            .leftFrontMotorName("frontLeft")
            .xVelocity(72.60962106299213)
            .yVelocity(47.6730707)
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE);
    public static PathConstraints pathConstraints = new PathConstraints(
            0.99, 100, 1, 2);
    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-155.453)
            .strafePodX(-69.453)
            .distanceUnit(DistanceUnit.MM)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);
    public static Follower createFollower(HardwareMap hardwareMap) {

        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(mecanumConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }
}
