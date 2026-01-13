package org.firstinspires.ftc.teamcode.Hydra;

import com.qualcomm.hardware.dfrobot.HuskyLens;
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
    public DcMotorEx leftFront;
    public DcMotorEx rightFront;
    public DcMotorEx leftRear;
    public DcMotorEx rightRear;

    // Intake motor (using DcMotorEx)
    public DcMotorEx intake;

    public HuskyLens huskyLens;
    // Chamber kick-up panels (3 Swyft Speed servos)
    public Servo leftChamber;
    public Servo centerChamber;
    public Servo rightChamber;

    // Shooter motors (3 goBILDA 5203 motors, right one reversed, using DcMotorEx for velocity control)
    public DcMotorEx outtakeLeft;
    public DcMotorEx outtakeCenter;
    public DcMotorEx outtakeRight;

    // Shooter angle adjustment
    //public Servo angleServoLeft;
    public Servo outerHood;


    // Extension mechanism (2 lead screws with continuous rotation servos)
    public CRServo leftScrew;
    public CRServo rightScrew;

    // Color sensors (3 REV V3 color sensors)
    public ColorSensor leftColorSensor;
    public ColorSensor centerColorSensor;
    public ColorSensor rightColorSensor;

    // RGB lights (goBILDA RGB lights run as servos)
    public Servo leftRGB;
    public Servo centerRGB;
    public Servo rightRGB;

    // Pinpoint (For Odometry)
    public GoBildaPinpointDriver pinpoint;

    public void init(HardwareMap hardwareMap) {
        // Initialize drive motors
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");

        // Set directions for mecanum drive (adjust as needed based on robot configuration)
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);

        // Initialize intake motor
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        huskyLens = hardwareMap.get(HuskyLens.class, "huskyLens");
        // Initialize chamber servos
        leftChamber = hardwareMap.get(Servo.class, "leftChamber");
        centerChamber = hardwareMap.get(Servo.class, "centerChamber");
        rightChamber = hardwareMap.get(Servo.class, "rightChamber");

        // Initialize shooter motors
        outtakeLeft = hardwareMap.get(DcMotorEx.class, "outtakeLeft");
        outtakeCenter = hardwareMap.get(DcMotorEx.class, "outtakeCenter");
        outtakeRight = hardwareMap.get(DcMotorEx.class, "outtakeRight");

        outtakeLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        outtakeCenter.setDirection(DcMotorSimple.Direction.FORWARD);
        outtakeRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize angle servos
        //angleServoLeft = hardwareMap.get(Servo.class, "angleServoLeft");
        outerHood = hardwareMap.get(Servo.class, "outerHood");

        // Initialize extension servos
        leftScrew = hardwareMap.get(CRServo.class, "leftScrew");
        rightScrew = hardwareMap.get(CRServo.class, "rightScrew");

        // Initialize color sensors
        leftColorSensor = hardwareMap.get(ColorSensor.class, "leftColorSensor");
        centerColorSensor = hardwareMap.get(ColorSensor.class, "centerColorSensor");
        rightColorSensor = hardwareMap.get(ColorSensor.class, "rightColorSensor");

        // Initialize RGB lights as servos
        leftRGB = hardwareMap.get(Servo.class, "leftRGB");
        centerRGB = hardwareMap.get(Servo.class, "centerRGB");
        rightRGB = hardwareMap.get(Servo.class, "rightRGB");

        // Initialize GoBILDA Pinpoint Odometry
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        // Configure Pinpoint (adjust values based on your setup)
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD); // Or custom value, e.g., 19.894 ticks per mm
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        pinpoint.setOffsets(-104.6, 225.425, DistanceUnit.MM);

        // Reset position and calibrate IMU (call this when robot is stationary, e.g., at start of opmode)
        pinpoint.resetPosAndIMU();
    }
}
