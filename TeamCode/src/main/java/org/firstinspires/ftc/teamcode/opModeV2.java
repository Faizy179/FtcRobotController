package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous
public class opModeV2 extends OpMode {
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

    outakeTeleOp outakeShoot = new outakeTeleOp();
    aprilTagV3 aprilTag = new aprilTagV3();
    @Override
    public void init() {
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        aprilTag.init(hardwareMap, telemetry);

    }




    public void moveToBalls(){

    }
    public void intakeBalls(){

    }
    public void moveToPosition(){

    }

    public void autoDrive(){
        moveToBalls();
        intakeBalls();
        moveToPosition();
        outakeShoot.Shoot();
    }
    public void moveForward(){
        frontRightMotor.setPower(1);
        frontLeftMotor.setPower(1);
        backRightMotor.setPower(1);
        backLeftMotor.setPower(1);
    }
    public void loop(){
        //update the vision portal

        aprilTag.update();
        AprilTagDetection id20 = aprilTag.getTagBySpecificId(20);
        aprilTag.displayDetectionTelemetry(id20);

        moveForward();

    }
}
