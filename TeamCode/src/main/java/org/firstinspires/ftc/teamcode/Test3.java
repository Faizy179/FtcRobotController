package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "Test3")

public class Test3 extends LinearOpMode {
    private aprilTagV3 tagCam;
    /*
    private DcMotor RF, LF, RB, LB;
    private DcMotor shooter, intake;
    private CRServo left_Transfer, right_Transfer;
    */
    private DcMotor RF;
    private DcMotor LF;
    private DcMotor RB;
    private DcMotor LB;
    private int TARGET_TAG_ID= 21;
    private double dp = 1;
    private double sp = .925;
    private double ip = 1;
    private double zp = 0;
   // private CRServo right_Transfer;
    //private CRServo left_Transfer;
    private DcMotor intake;
    private DcMotor shooter;
    private Servo kicker;
    private CRServo RT1;

    private CRServo RT2;


    @Override
    public void runOpMode() throws InterruptedException {

        // ---- MOTOR SETUP ----
        RF = hardwareMap.get(DcMotor.class, "RF");
        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        LB = hardwareMap.get(DcMotor.class, "LB");

        LF.setDirection(DcMotorSimple.Direction.FORWARD);
        LB.setDirection(DcMotorSimple.Direction.FORWARD);
        RF.setDirection(DcMotorSimple.Direction.REVERSE);
        RB.setDirection(DcMotorSimple.Direction.REVERSE);

        shooter = hardwareMap.get(DcMotor.class, "Shooter");
        intake = hardwareMap.get(DcMotor.class, "Intake");
        kicker = hardwareMap.get(Servo.class, "kicker");
        RT1 = hardwareMap.get(CRServo.class, "RT1");
        RT2 = hardwareMap.get(CRServo.class, "RT2");
        tagCam = new aprilTagV3();
        tagCam.init(hardwareMap, telemetry);
        waitForStart();

        while (opModeIsActive()) {
            tagCam.update();

// get the tag you care about (specific ID), or use first detection if you prefer
            AprilTagDetection det = tagCam.getTagBySpecificId(TARGET_TAG_ID);

// If you want to see info on Driver Station:
            if (det != null) tagCam.displayDetectionTelemetry(det);

// Default bearing = 0 if nothing found
            double bearing = 0.0;
            if (det != null && det.ftcPose != null) {
                bearing = det.ftcPose.bearing; // radians (you set AngleUnit.RADIANS)
            }

            double y  = -gamepad1.left_stick_y;   // forward/back (FTC inverted)
            double x  =  gamepad1.left_stick_x;   // strafe (DO NOT invert)
            double rx = -gamepad1.right_stick_x;  // turn

            // Deadzone
            double deadzone = 0.05;
            if (Math.abs(y)  < deadzone) y  = 0;
            if (Math.abs(x)  < deadzone) x  = 0;
            if (Math.abs(rx) < deadzone) rx = 0;

            // Optional strafe correction
            double strafeMultiplier = 1.1;
            x *= strafeMultiplier;

            // Normalization
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            // Mecanum math (STANDARD)
            double frontLeft  = (y + x + rx) / denominator;
            double backLeft   = (y - x + rx) / denominator;
            double frontRight = (y - x - rx) / denominator;
            double backRight  = (y + x - rx) / denominator;

            // Slow mode (optional)
            double speed = gamepad1.right_bumper ? 0.4 : 1.0;

            // Apply power
            LF.setPower(frontLeft  * speed);
            LB.setPower(backLeft   * speed);
            RF.setPower(frontRight * speed);
            RB.setPower(backRight  * speed);


            // ---------------- INTAKE ----------------
            if (gamepad1.left_trigger > 0.3) {
                intake.setDirection(DcMotorSimple.Direction.FORWARD);
                intake.setPower(1);
            }
            if (gamepad1.left_trigger < 0.3 && !gamepad2.b && !gamepad2.y) {
                intake.setPower(0);
            }


            // _______ TURRET ROTATION -------
            if(bearing > 0.3){
                RT1.setPower(1);
                RT2.setPower(1);
            } else if (bearing < -0.3) {
                RT1.setPower(-1);
                RT2.setPower(-1);
            }
            else{
                RT1.setPower(0);
                RT2.setPower(0);
            }

            // ------- SHOOTER -------
            if (gamepad1.right_trigger > 0.3) {
                shooter.setDirection(DcMotorSimple.Direction.FORWARD);
                kicker.setPosition(1);
                //allign rotation

                shooter.setPower(sp);

                if (gamepad1.right_trigger < 0.2 && !gamepad2.b) {
                    shooter.setPower(0);
                    kicker.setPosition(0);
                }
            } else if (gamepad1.dpad_up) {
                sp = Math.min(sp + 0.05, 1.0);   // <-- FIXED (updates sp now)
                shooter.setPower(sp);
                sleep(120);                      // prevent spam increments

            } else if (gamepad1.dpad_down) {
                sp = Math.max(sp - 0.05, 0.0);   // <-- FIXED (updates sp now)
                shooter.setPower(sp);
                sleep(120);

            } else if (gamepad1.right_bumper) {
                shooter.setPower(0);
            }
            //-------KICKER----------

            // ----------- TELEMETRY FOR TUNING -----------
            telemetry.addData("Shooter Power", sp);
            telemetry.update();

        }
    }
}

/*
cross == A
Circle == B
Triangle == X
Square == Y
 */
