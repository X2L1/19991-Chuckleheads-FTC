package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Stepmaster", group="Linear OpMode")

public class Stepmaster extends LinearOpMode {

// #### Declare Game Timer and Hardware ####
private ElapsedTime runtime = new ElapsedTime();
private DcMotorEx leftDrive = null;
private DcMotorEx rightDrive = null;
private DcMotorEx arm       = null;
private Servo gripperLeft   = null;
private Servo gripperRight  = null;

// #### Step Processing Arrays, Variables abnd Constants ####
private int maxSteps= 12;
private int[]       leftDriveList   = new int[maxSteps]; //list of double positions
private int[]       rightDriveList  = new int[maxSteps]; //list of double positions
private int[]       armList         = new int[maxSteps]; //list of double positions
private double[]    gripperList     = new double[maxSteps]; //list of servo positions
private String[]    stepNameList    = new String[maxSteps]; // List of Step Names
private int[]       delayList       = new int[maxSteps]; // List of delay times
private int         nbrSteps = -1;
private int         currentStep = 0;
private boolean     SINGLESTEP = true; //### flip to false and program will run all the steps as fast as possible.
private final int   ACCEPTABLE_ERROR = 10;
private boolean     firstTimeGuide = true;  // ### Used to de-bounce the Guide Button ###
private boolean     firstTimeInStep = true;
private ElapsedTime timer = new ElapsedTime();
private double targetTime = 0; // Used to set a time in the future that program should pause until

@Override
public void runOpMode() 
{
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    // ### Hardware Maps ####
    leftDrive       = hardwareMap.get(DcMotorEx.class, "leftDrive");
    rightDrive      = hardwareMap.get(DcMotorEx.class, "rightDrive");
    arm             = hardwareMap.get(DcMotorEx.class, "arm");
    gripperLeft     = hardwareMap.get(Servo.class, "gripperLeft");
    gripperRight    = hardwareMap.get(Servo.class, "gripperRight");

    // #### Hardware housekeeping ####
    leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightDrive.setDirection(DcMotor.Direction.REVERSE);
    gripperLeft.setDirection(Servo.Direction.REVERSE);
    arm.setDirection(DcMotor.Direction.REVERSE);
    arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

    // #### Fill the Step Arrays ####
    addStep( "Init Robot",     0,  0, 0.5, 0,0);
    addStep( "Go Forward-One",   1000, 1000, 0.5, 50,0);
    addStep( "TurnRight",   0, 1000, 0.0, 50,0);
    addStep( "Go Forward-Two",   1000, 1000, 0.5, 50,0);
    addStep( "TurnLeft",   1000, 0, 0.0, 50,0);
    addStep( "Go Forward-Three",   1000, 1000, 0.5, 50,0);
    addStep( "Close Gripper",  0,  0, 0.8, 0,2);
    addStep( "Open Gripper",   0,  0, 0.4, 50,0);
    addStep( "Auton Complete", 0,  0, 0.4, 50,0);

    // Wait for the game to start (driver presses START)
    waitForStart();
    runtime.reset();

 //set your steps
 
   // diag 
  // rightDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
   // rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
   // leftDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
 
    targetTime = runtime.seconds() + delayList[0] ;

 // run until the end of the match (driver presses STOP)
 while (opModeIsActive()) 
 {
    // #### if all steps done - stop the program ####
    if(currentStep == nbrSteps)  break;
  
    //### LeftDrive ###
    leftDrive.setTargetPosition(leftDriveList[currentStep]);
    leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
  
    //### rightDrive ###
    rightDrive.setTargetPosition(rightDriveList[currentStep]);
    rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    // ### launch Both drives simaltaneously to reduce lag
    rightDrive.setVelocity(400);
    leftDrive.setVelocity(400);
    
    // #### Note: ArmDrive does not reset position, and never comes of "run to position"
    // ### armDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    arm.setTargetPosition(armList[currentStep]); 
    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    arm.setVelocity(1000);
    
    //#### Gripper ## note: Both servos get set to same setting
    gripperLeft.setPosition ( gripperList[currentStep] );
    gripperRight.setPosition( gripperList[currentStep] );
    
    // #### Delay ###
    


   //#### is processing done? Can we move to the next step?
    boolean stepComplete = true; // Assume Sucess
    if ( Math.abs(leftDrive.getCurrentPosition() - leftDrive.getTargetPosition()) > ACCEPTABLE_ERROR) 
        {   stepComplete=false; 
            telemetry.addData("wait" , "Waiting on leftDrive");
        }

    if (Math.abs(rightDrive.getCurrentPosition() - rightDrive.getTargetPosition()) > ACCEPTABLE_ERROR) 
        {   stepComplete=false; 
            telemetry.addData("wait" , "Waiting on rightDrive");
        }

    if (Math.abs(arm.getCurrentPosition() - arm.getTargetPosition()) > ACCEPTABLE_ERROR)
        {   stepComplete=false; 
            telemetry.addData("wait" , "Waiting on arm");
        }

    if ( runtime.seconds() <= targetTime ) 
        {   stepComplete=false;
            telemetry.addData("wait" , "Waiting on timer");
        }

    if ( !gamepad1.guide) firstTimeGuide = true;
    
    if (stepComplete)
        {
        if (SINGLESTEP)  
               {   telemetry.addData("Single Step Mode On" , "Press guide to advance");
               if ( gamepad1.guide && firstTimeGuide )
                   {
                       firstTimeGuide = false;
                       nextStep();
                   }   
                }        
        else  
            {  nextStep();}
        }
                
    //### Telemetry Section  ####
    telemetry.addData("Status", "Run Time: " + runtime.toString() );
    telemetry.addData("Current step", currentStep + " : " + stepNameList[ currentStep ]   );
    telemetry.addData("leftDrive", leftDrive.getCurrentPosition() + "/" + leftDrive.getTargetPosition());
    telemetry.addData("rightDrive", rightDrive.getCurrentPosition() + "/" + rightDrive.getTargetPosition());
    telemetry.addData("arm ",arm.getCurrentPosition() + "/" + arm.getTargetPosition());
    telemetry.addData("gripper",gripperLeft.getPosition()  );
    telemetry.update();

};// ### end of While Op mode active ###
}; // ### end of run Op Mode

private void addStep( String stepName,  int leftDriveX,  int rightDriveX,  double gripperX,  int armX,  int delayX )
// #### used to add all the information for a given step at the same time ####
{
    nbrSteps                        = nbrSteps +1;
    stepNameList[nbrSteps]          = stepName;
    leftDriveList[nbrSteps]         = leftDriveX;
    rightDriveList[nbrSteps]        = rightDriveX;
    gripperList[nbrSteps]           = gripperX;
    armList[nbrSteps]               = armX;
    delayList[nbrSteps]             = delayX;
}

private void nextStep(){
    currentStep++; //### same as currentStep = currentStep +1; ###
    
    targetTime = runtime.seconds() + delayList[currentStep] ;
    //### Each time the Robot moves to the next step reset the drive motors to zero.
    rightDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    leftDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
}


} // #### End of Class ####
