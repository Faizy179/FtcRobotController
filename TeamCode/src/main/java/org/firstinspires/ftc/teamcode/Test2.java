
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class Test2 extends LinearOpMode {
    outakeTeleOp outakeShoot = new outakeTeleOp();
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        DcMotor outakeDrive = hardwareMap.dcMotor.get("outakeDrive");
        DcMotor intakeDrive = hardwareMap.dcMotor.get("intakeDrive");
        Servo outakeRotation = hardwareMap.servo.get("outakeRotation");
        Servo outakePush = hardwareMap.servo.get("outakePush");
        Servo turretRotationClockwise = hardwareMap.servo.get("rightTurretSurvo");
        Servo turretRotationCounterclockwise = hardwareMap.servo.get("leftTurretSurvo");
        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
            double flywheelRatation = gamepad2.right_trigger;
            boolean turretRotationInputClockwise = gamepad1.dpad_right;
            boolean turretRotationInputCounterclockwise = gamepad1.dpad_left;
            double intakePower = gamepad2.right_trigger;
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            while(turretRotationInputClockwise){
                int i = 0;
                turretRotationClockwise.setPosition(i);
                i++;
            }
            while(turretRotationInputCounterclockwise){
                int i = 0;
                turretRotationCounterclockwise.setPosition(i);
                i++;
            }
            if (gamepad1.x){
                outakeShoot.Shoot();
            }
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            intakeDrive.setPower(intakePower);
            outakeRotation.setPosition(flywheelRatation);
        }
    }
}
