package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Imu {

    public IMU imu;

    Telemetry telemetry;

    public Imu(HardwareMap hardwareMap, Telemetry telemetry) {
        this.imu = hardwareMap.get(IMU.class, "imuE");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        this.telemetry = telemetry;
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public void reset() {
        imu.resetYaw();
    }
}
