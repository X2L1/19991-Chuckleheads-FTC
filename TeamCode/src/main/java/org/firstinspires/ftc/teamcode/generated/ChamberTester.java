package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Chamber Tester", group = "TeleOp")
public class ChamberTester extends LinearOpMode {

    private HydraHardware hardware;
    private double target = .5;


    @Override
    public void runOpMode() {
        hardware = new HydraHardware();
        hardware.init(hardwareMap);



        waitForStart();
        while(opModeIsActive()) {

            hardware.rightChamber.setPosition(target);
            hardware.centerChamber.setPosition(target);
            hardware.leftChamber.setPosition(target);
            if (gamepad1.right_bumper) {
                target += .05;
                sleep(200);
            } else if (gamepad1.left_bumper) {
                target -= .05;
                sleep(200);
            }
            telemetry.addData("Current Position: ", target);
            telemetry.update();
        }
    }
}



