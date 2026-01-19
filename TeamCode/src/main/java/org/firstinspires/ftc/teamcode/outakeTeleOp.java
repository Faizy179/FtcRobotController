package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class outakeTeleOp {
    DcMotor outakeDrive = hardwareMap.dcMotor.get("outakeDrive");
    Servo outakeRotation = hardwareMap.servo.get("outakeRotation");
    Servo outakePush = hardwareMap.servo.get("outakePush");
    public void Shoot(){
        for (int i =1; i < 4; i++)
            outakeDrive.setPower(1);
        outakePush.setPosition(90);
        outakePush.setPosition(0);
    }
    public void Adjust (){
        return;
    }
}

