package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import java.lang.annotation.Target;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TaskMaster", group="Linear OpMode")

public class TaskMaster extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    enum Tasks {
        GO_FORWARD_W_ENCODER,
        TURN_RIGHT_90_W_ENCODER,
    }

    private int maxSteps= 12;
    private String[]    stepNameList    = new String[maxSteps]; // List of Step Names
   
    private int[]       leftDriveList   = new int[maxSteps]; //list of double positions
    private int[]       rightDriveList  = new int[maxSteps]; //list of double positions
    private int[]       armList         = new int[maxSteps]; //list of double positions
    private double[]    gripperList     = new double[maxSteps]; //list of servo positions
   private int[]       delayList       = new int[maxSteps]; // List of delay times
    private int         nbrSteps = -1;
    private int         currentStep = 0;
    private boolean     SINGLESTEP = true; //### flip to false and program will run all the steps as fast as possible.
    
    private final int   ACCEPTABLE_ERROR = 10;
    private boolean     firstTime = true;  // ### Used to de-bounce the Guide Button ###
    private ElapsedTime timer = new ElapsedTime();
    private double targetTime = 0; // Used to set a time in the future that program should pause until
    private boolean     firstTimeInStep = true;
    
    private int         targetDistance=0; // used for encoder based motor runs



    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) 
            {
                
                // Run Tasks 
                telemetry.addData("Status", "Run Time: " + runtime.toString());
                
                
                
                if (firstTimeInStep) 
                    { 
                     // ### Starts both motors 
                    
                     leftDrive   .setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); 
                     rightDrive  .setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
                     rightDrive  .setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); 
                     rightDrive  .setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); 
                     firstTimeInStep = false;      // if distance is negative, set velocity negative
                     rightDrive.setPower(.5);
                     rightDrive.setPower(.5);
                     targetDistance = 6000;
                    }
                 else
                    {
                        if ( targetDistance > 0) // going forward
                            {
                                if ( rightDrive.getCurrentPosition() > targetDistance )
                                    {
                                        rightDrive.setPower(0);
                                        leftDrive.setPower(0);
                                        currentStep = currentStep +1;
                                        firstTimeInStep = true;
                                    }
                                else // foreard, Adjust velocity 
                                    {
                                        if  (rightDrive.getCurrentPosition() > leftDrive.getCurrentPosition() +10 )
                                            {
                                                rightDrive.setPower( rightDrive.getPower()-.01);
                                            }
                                        else
                                            {
                                                 rightDrive.setPower( rightDrive.getPower()+.01);
                                            }
                                    }
                                    
                            }
                        else // going backwards
                          {
                                if ( rightDrive.getCurrentPosition() < targetDistance )
                                    {
                                        rightDrive.setPower(0);
                                        leftDrive.setPower(0);
                                        currentStep = currentStep +1;
                                        firstTimeInStep = true;
                                    }
                                else // foreard, Adjust velocity 
                                    {
                                        if ( rightDrive.getCurrentPosition() > lettDrive.getCurrentPosition() +10 )
                                            {
                                                rightDrive.setPower(rightDrive.getPower()+.01);
                                            }
                                        else
                                            {
                                                 rightDrive.setPower(rightDrive.getPower()-.01);
                                            }
                                    }
                                    
                            }
                    }
                    
                telemetry.addData("Drive Straight",  rightDrive.getCurrentPosition() + " / " + targetDistance); 
                     
                
                
                
                leftDrive.setPower(leftPower);
                rightDrive.setPower(rightPower);

                // Show the elapsed game time and wheel power.
                
                telemetry.update();
            }
    }
}
