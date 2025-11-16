package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class HydraHardware {

    // Drive motors (assuming Swyft Drive V2 mecanum with 4 motors, using DcMotorEx for extended features)
    public DcMotorEx frontLeftMotor;
    public DcMotorEx frontRightMotor;
    public DcMotorEx backLeftMotor;
    public DcMotorEx backRightMotor;

    // Intake motor (using DcMotorEx)
    public DcMotorEx intakeMotor;

    // Chamber kick-up panels (3 Swyft Speed servos)
    public Servo chamberServo1;
    public Servo chamberServo2;
    public Servo chamberServo3;

    // Shooter motors (3 goBILDA 5203 motors, right one reversed, using DcMotorEx for velocity control)
    public DcMotorEx shooterMotorLeft;
    public DcMotorEx shooterMotorCenter;
    public DcMotorEx shooterMotorRight;

    // Shooter angle adjustment
    public Servo angleServoLeft;
    public Servo angleServoRight;


    // Extension mechanism (2 lead screws with continuous rotation servos)
    public CRServo extensionServo1;
    public CRServo extensionServo2;

    // Color sensors (3 REV V3 color sensors)
    public ColorSensor colorSensor1;
    public ColorSensor colorSensor2;
    public ColorSensor colorSensor3;

    // RGB lights (goBILDA RGB lights run as servos)
    public Servo rgbLight1;
    public Servo rgbLight2;
    public Servo rgbLight3;

    // Pinpoint (For Odometry)
    public GoBildaPinpointDriver pinpoint;

    public void init(HardwareMap hardwareMap) {
        // Initialize drive motors
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRightMotor");

        // Set directions for mecanum drive (adjust as needed based on robot configuration)
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize intake motor
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Initialize chamber servos
        chamberServo1 = hardwareMap.get(Servo.class, "chamberServo1");
        chamberServo2 = hardwareMap.get(Servo.class, "chamberServo2");
        chamberServo3 = hardwareMap.get(Servo.class, "chamberServo3");

        // Initialize shooter motors
        shooterMotorLeft = hardwareMap.get(DcMotorEx.class, "shooterMotorLeft");
        shooterMotorCenter = hardwareMap.get(DcMotorEx.class, "shooterMotorCenter");
        shooterMotorRight = hardwareMap.get(DcMotorEx.class, "shooterMotorRight");

        shooterMotorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotorCenter.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotorRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize angle servos
        angleServoLeft = hardwareMap.get(Servo.class, "angleServoLeft");
        angleServoRight = hardwareMap.get(Servo.class, "angleServoRight");

        // Initialize extension servos
        extensionServo1 = hardwareMap.get(CRServo.class, "extensionServo1");
        extensionServo2 = hardwareMap.get(CRServo.class, "extensionServo2");

        // Initialize color sensors
        colorSensor1 = hardwareMap.get(ColorSensor.class, "colorSensor1");
        colorSensor2 = hardwareMap.get(ColorSensor.class, "colorSensor2");
        colorSensor3 = hardwareMap.get(ColorSensor.class, "colorSensor3");

        // Initialize RGB lights as servos
        rgbLight1 = hardwareMap.get(Servo.class, "rgbLight1");
        rgbLight2 = hardwareMap.get(Servo.class, "rgbLight2");
        rgbLight3 = hardwareMap.get(Servo.class, "rgbLight3");

        // Initialize GoBILDA Pinpoint Odometry
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        // Configure Pinpoint (adjust values based on your setup)
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD); // Or custom value, e.g., 19.894 ticks per mm
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.setOffsets(-84.0, -168.0, DistanceUnit.MM); // Example offsets in mm; adjust for your robot

        // Reset position and calibrate IMU (call this when robot is stationary, e.g., at start of opmode)
        pinpoint.resetPosAndIMU();
    }
}
