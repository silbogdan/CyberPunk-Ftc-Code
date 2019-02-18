/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.robotcontroller.external.samples;

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="CyberPunk TeleOp", group="Linear Opmode")
@Disabled
public class CyberPunk_TeleOp extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor stangaFata = null;
    private DcMotor stangaSpate = null;
    private DcMotor dreaptaFata = null;
    private DcMotor dreaptaSpate = null;
    private DcMotor BratStanga = null;
    private DcMotor BratDreapta = null;
    private DcMotor extindereBrat = null;
    private DcMotor miscareCutie = null;

    private CRServo servoCutie = null;
    private Servo servoSenzor = null;
    private Servo servoMarker = null;
    private Servo servoCarlig = null;
    private Servo servoLock = null;
    private Servo servoBob = null;

    @Override
    public static double btd(boolean b) {
      /* This function it used for converting boolean to double */
        if (b) {
            return 1;
        }
        return 0;
    }

    public void strafing(){

      //Setting the power of the motors for strafing left if dpad_left is pushed
     while (gamepad1.dpad_left) {
            double value = btd(gamepad1.dpad_left);
            stangaFata.setPower(value);
            stangaSpate.setPower(-value);
            dreaptaFata.setPower(value);
            dreaptaSpate.setPower(-value);
        }

    //Setting the power of the motors for strafing right if dpad_right is pushed
      while (gamepad1.dpad_right) {
            double value = btd(gamepad1.dpad_right);
            stangaFata.setPower(-value);
            stangaSpate.setPower(value);
            dreaptaFata.setPower(-value);
            dreaptaSpate.setPower(value);
        }


    public double cm_converter(double motor_ticks, double gear_ratio_1, double gear_ratio_2, double wheel_diameter, double cm){

      /* This function is designed to calculate necessary number of ticks for moving the robot a certain number of centimeters
         Parametres meanings:
         motor_ticks = number of ticks of the motor (not very much to explain here)

         gear_ratio_1 and gear_ratio_2 represents gear ratio between the motor and the wheel took as 2 individual variables
         For example if the ratio is 2:1, gear_ratio_1 will be equal to 2 and gear_ratio_2 will be equal to 1
         If the wheel is directly attached to the motor, gear_ratio_1 and gear_ratio_2 will be equal to 1

         wheel_diameter represnts the diameter of the wheel

         cm represents the number of centimeters the number of centimeters we want the robot to move

         This function can be used to return number of ticks necessary for moving the robot a certain number of inches
         You just need to pass the desired number of inches in the cm  parameter and to encapsulate the last modification of partial_result_3 in comments  */





      // calculating the number of ticks necessary for one complete rotation of the wheel with the actual gear configuration
      double partial_result_1 = (motor_ticks * gear_ratio_1) / gear_ratio_2;

      // calculating the distance traveled by robot afer one complete rotation of the wheel
      double partial_result_2 = wheel_diameter * Math.PI;

      // calculating the necessary number of ticks to move  the robot 1 inch
      double partial_result_3 = partial_result_1 / partial_result_2;

      // calculating the necessary number of ticks to move 1 centimeters knowing that 1 inch = 2.54 centimeters
      // you can encapsulate this part in comments and the function will return the number of inches necessar
      partial_result_3 = partial_result_3 / 2.54;

      // knowing the necessary number of ticks to move 1 centimeters, finally we can calculate the necessary number of ticks to move the robot cm centimeters
      int result = partial_result_3 * cm;

      return result;
    }

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        stangaFata = hardwareMap.get(DcMotor.class, "stangaFata");
        stangaSpate = hardwareMap.get(DcMotor.class, "stangaSpate");
        dreaptaFata = hardwareMap.get(DcMotor.class, "dreaptaFata" );
        dreaptaSpate = hardwareMap.get(DcMotor.class, "dreaptaSpate");
        bratStanga = hardwareMap.get(DcMotor.class, "ridicareBratStanga");
        bratDreapta = hardwareMap.get(DcMotor.class, "ridicareBratDreapta");
        extindereBrat = hardwareMap.get(DcMotor.class, "extindereBrat");
        miscareCutie = hardwareMap.get(DcMotor.class, "miscareCutie");

        servoCutie = hardwareMap.get(CRServo.class, "servoCutie");
        servoSenzor = hardwareMap.get(Servo.class, "servoSenzor");
        servoMarker = hardwareMap.get(Servo.class, "servoMarker");
        servoCarlig = hardwareMap.get(Servo.class, "servoCarlig");
        servoLock = hardwareMap.get(Servo.class, "servoLock");
        servoBob = hardwareMap.get(Servo.class, "servoBob");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        stangaFata = setDirection(DcMotor.Direcion.FORWARD);
        stangaSpate = setDirection(DcMotor.Direction.FORWARD);
        dreaptaFata = setDirecion(DcMotor.Direction.FORWARD);
        dreaptaSpate = setDirection(DcMotor.Direction.FORWARD);

        bratStanga = setDirection(DcMotor.Direction.FORWARD);
        bratDreapta = setDirection(DcMotor.Direction.FORWARD);
        extindereBrat = setDirection(DcMotor.Direction.FORWARD);
        miscareCutie = setDirection(DcMotor.Diretion.FORWARD);



        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //declaring variables
            double rotire=gamepad1.right_stick_x;
            double fata_spate = gamepad1.right_stick_y;

            // Calling starfing function if dpad_left or dpad_right are pushed
            if(gamepad1.dpad_left || gamepad1.dpad_right)strafing();

            // Send calculated power to wheels
             stangaFata.setPower(fata_spate - rotire);
            stangaSpate.setPower(fata_spate - rotire);
            dreaptaFata.setPower(-fata_spate - rotire);
            dreaptaSpate.setPower(-fata_spate - rotire);

            stangaFata.setPower(0);
            stangaSpate.setPower(0);
            dreaptaFata.setPower(0);
            dreaptaSpate.setPower(0);



            telemetry.update();
        }
    }
}
