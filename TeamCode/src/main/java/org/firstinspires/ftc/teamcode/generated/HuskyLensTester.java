package org.firstinspires.ftc.teamcode.generated;


import static java.lang.Thread.sleep;


import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;




public class HuskyLensTester extends LinearOpMode {
    public final int greensleep = 100;
    public int servogreen = 0;
    public int greenmotiforder = 0;
    private double disttemp = 0;
    private final int READ_PERIOD = 1;
    public final double up = 0.3;
    public double dista = 0;
    private final double MOTOR_POWER_SCALE = 0.02; // Adjust this to control motor movement speed

    private HuskyLens huskyLens;
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    public Servo leftKick, centerKick, rightKick, leftHood, rightHood;
    private String direction = "";

    public HuskyLensTester(HuskyLens huskyLens)
    {
        this.huskyLens = huskyLens;
    }

    @Override
    public void runOpMode() {
        int desiredTagID = -1;  //blue is 1, red is 2
        int MotifID = 0;
        boolean motifyes = false;
        boolean ballsyes = false;
        boolean green1 = false;
        boolean green2 = false;
        boolean green3 = false;
        boolean tagFound = false;

        huskyLens = hardwareMap.get(HuskyLens.class, "huskyLens");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        leftKick = hardwareMap.get(Servo.class, "leftKick");
        centerKick = hardwareMap.get(Servo.class, "centerKick");
        rightKick = hardwareMap.get(Servo.class, "rightKick");
        leftHood = hardwareMap.get(Servo.class, "leftHood");
        rightHood = hardwareMap.get(Servo.class, "rightHood");

        double dist = 0;
        String errorSide = "";

        // Reverse motors if necessary to align with your robot's configuration
        frontLeft.setDirection(DcMotorEx.Direction.FORWARD);
        frontRight.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);



        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);

        /*
         * Immediately expire so that the first time through we'll do the read.
         */
        rateLimit.expire();

        /*
         * Basic check to see if the device is alive and communicating.  This is not
         * technically necessary here as the HuskyLens class does this in its
         * doInitialization() method which is called when the device is pulled out of
         * the hardware map.  However, sometimes it's unclear why a device reports as
         * failing on initialization.  In the case of this device, it's because the
         * call to knock() failed.
         */
        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }

        /*
         * The device uses the concept of an algorithm to determine what types of
         * objects it will look for and/or what mode it is in.  The algorithm may be
         * selected using the scroll wheel on the device, or via software as shown in
         * the call to selectAlgorithm().
         *
         * The SDK itself does not assume that the user wants a particular algorithm on
         * startup, and hence does not set an algorithm.
         *
         * Users, should, in general, explicitly choose the algorithm they want to use
         * within the OpMode by calling selectAlgorithm() and passing it one of the values
         * found in the enumeration HuskyLens.Algorithm.
         */
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);

        telemetry.update();
        waitForStart();

        /*
         * Looking for AprilTags per the call to selectAlgorithm() above.  A handy grid
         * for testing may be found at https://wiki.dfrobot.com/HUSKYLENS_V1.0_SKU_SEN0305_SEN0336#target_20.
         *
         * Note again that the device only recognizes the 36h11 family of tags out of the box.
         */
        while(opModeIsActive()) {



            if(gamepad1.right_bumper)
            {
                desiredTagID = desiredTagID +1;
            }
            if(desiredTagID == -1)
            {
                if(gamepad1.left_bumper)
                {
                    desiredTagID = 0;
                }
            }
            else
            {
                if(gamepad1.left_bumper)
                {
                    desiredTagID = desiredTagID -1;
                }
            }

            if(desiredTagID < 3)
            {
                if(desiredTagID == 1)
                {
                    telemetry.addData("desiredTag:" ,"blue");
                }
                else if(desiredTagID == 2)
                {
                    telemetry.addData("desiredTag:", "red");
                }
                else if(desiredTagID == -1)
                {
                    telemetry.addData("desiredTagID: ", "Any");
                }
            }

            else if (desiredTagID > 2)
            {
                MotifID = desiredTagID - 2;
                if(desiredTagID == 3)
                {
                    telemetry.addData("desired Motif:", "PPG");
                    greenmotiforder = 3;
                }
                if(desiredTagID == 4)
                {
                    telemetry.addData("desired Motif:", "PGP");
                    greenmotiforder = 2;
                }
                if(desiredTagID == 5)
                {
                    telemetry.addData("desired Motif:", "GPP");
                    greenmotiforder = 1;
                }
            }

            boolean kicking = false;
            if(gamepad1.right_trigger>0.1)
            {
                kicking = true;
            }
           /* if(kicking)
{
    switch(servogreen)
    {
        case 1:
                switch(greenmotiforder)
                {
                    case 1:
                            servoKick(1, 2, 3, greensleep, 0);
                    break;
                    case 2:
                            servoKick(2, 1, 3, greensleep, greensleep);
                    break;
                    case 3:
                            servoKick(3, 2, 1, 0, greensleep);
                    break;
                }
                 break;
        case 2:
                switch(greenmotiforder)
                {
                    case 1:
                            servoKick(2, 1, 3, greensleep, 0);
                    break;
                    case 2:
                            servoKick(1, 2, 3, greensleep, greensleep);
                    break;
                    case 3:
                            servoKick(1, 3, 2, 0, greensleep);
                     break;
                }
                break;
        case 3:
                switch(greenmotiforder)
                {
                    case 1:
                            servoKick(3, 2, 1, greensleep, 0);
                    break;
                    case 2:
                            servoKick(2, 3,1, greensleep, greensleep);
                    break;
                    case 3:
                            servoKick(1, 2, 3, 0, greensleep);
                     break;
                }
                 break;


    }
}*/

            rateLimit.reset();


            HuskyLens.Block[] blocks = huskyLens.blocks();
            for (int i = 0; i < blocks.length; i++) {
                telemetry.addData("Block", blocks[i].toString());
            }

            if (blocks.length > 0) {
                int blockX = blocks[0].x;
                int blockY = blocks[0].y;


                int TagID = blocks[0].id;
                int screenCenterX = 320/2; // Screen's X-axis center
                int screenCenterY = 240/2;
                dist = (10.58888888883 * 30) / (2 * blocks[0].width * Math.tan(0.925/2)) * 5.5;
                double dh = Math.sqrt((blockX - screenCenterX)^2 + (blockY - screenCenterY)^2);
                telemetry.addData("Block tostring", blocks[0].toString());
                telemetry.addData("Block count", blocks.length);
                telemetry.addData("Distance ", dist);
                telemetry.addData("Tag ID #",TagID);
                telemetry.addData("dist of width", dh);
                dista = dist;
                // Assuming the screen resolution is 320x240 pixels

                // Get the position of the first block


                // Calculate the error (difference between block position and screen center)
                double tagerrorX = (blockX - screenCenterX);
                double tagerrorY = (blockY - screenCenterY);
                telemetry.addData("Block X", blockX);
                //telemetry.addData("Block Y", blockY);
                telemetry.addData("Error X", tagerrorX);
                //double drive_gain = Math.abs(tagerrorX/160);
                /* if you want to add drive_gain to drive increasing with distance from nearest tag, say, with speed being
                added from >50% max distance,
                double drive = (gamepad1.left_stick_y) * Range.clip((0.5 * Math.signum(tagerrorX) + tagerrorX, -1.0, 1.0) */
                //double turn_gain = Math.abs(tagerrorY/120);
                double drive = (gamepad1.left_stick_y);
                double turn = (gamepad1.right_stick_x);
                double strafe = (gamepad1.left_stick_x);
                double frontLeftPower = Range.clip((drive + turn + strafe), -1.0, 1.0);
                double frontRightPower = Range.clip((drive - turn - strafe), -1.0, 1.0);
                double backLeftPower = Range.clip((drive + turn - strafe), -1.0, 1.0);
                double backRightPower = Range.clip((drive - turn + strafe), -1.0, 1.0);
                double sign = -Math.signum(blockX - screenCenterX) * .1;
                if(Math.abs(blockX - screenCenterX) < 50) sign = 0;
                tagerrorX = Range.clip(tagerrorX, -160, 160);
                tagerrorY = Range.clip(tagerrorY, -120, 120);
                // Apply motor power adjustments here

                double errorScreenCenter = Math.abs(blockX - screenCenterX);

                if(50 < errorScreenCenter && errorScreenCenter < 100 )
                {
                    telemetry.addData("center of screen error: ", errorScreenCenter);
                }
                else if(errorScreenCenter > 50){
                    errorSide = "right";
                }
                else if(blockX < screenCenterX){
                    errorSide = "left";
                }


                if(TagID == desiredTagID)
                {
                    telemetry.addData("AprilTag:", "FOUND");
                    tagFound = true;
                }
                else if(desiredTagID == -1)
                {
                    telemetry.addData("AprilTag:", "Seeking all/any");
                    tagFound = true;
                }
                else if (TagID != desiredTagID)
                {
                    telemetry.addData("Tag not found", "...");
                    tagFound = false;
                }

                if(gamepad1.right_trigger>0.1)
                {

                }

                switch(desiredTagID)
                {
                    case 1:
                        switch(TagID)
                        {
                            case 1:
                                break;


                            default:
                                telemetry.addData("blue not detected,", "go left...");
                                direction = "left";
                                break;

                        }
                        break;
                    case 2:
                        telemetry.addData("red not detected,", "go right...");
                        direction = "right";
                        break;
                    default:
                        switch(TagID)
                        {
                            case 1:
                                telemetry.addData("motif not detected,", "go right");
                                direction = "right";
                                break;
                            case 2:
                                telemetry.addData("motif not detected,", "go left");
                                direction = "left";
                                break;
                            default:
                                break;
                        }

                }

            }





            // Output telemetry for debugging

            //telemetry.addData("error on: "+errorSide, errorScreenCenter);

            //telemetry.addData("Motor Power X", motorPowerX);
            //telemetry.addData("Motor Power Y", motorPowerY);
        } /*else {
                // If no block detected, stop the motors
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);

                telemetry.addData("No block detected", "");
            }*/ telemetry.update();
    }

    public String getDirection()
    {
        return direction;
    }
    public double getDistance(int desiredTagID) {
        HuskyLens.Block[] blocks = huskyLens.blocks();

        for (HuskyLens.Block block : blocks) {
            if (desiredTagID == -1 || block.id == desiredTagID) {
                disttemp = (10.58888888883 * 30) / (2 * block.width * Math.tan(0.925/2)) * 5.5;
            }
        }

        return disttemp;
    }
}
