package frc.robot.subsystems.vision;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drive.Drive;
import frc.robot.util.LimelightHelpers;
import frc.robot.util.LimelightHelpers.PoseEstimate;

public class Vision extends SubsystemBase {
    private VisionIOInputsAutoLogged inputs = new VisionIOInputsAutoLogged();
    private String name;

    @AutoLog
    public static class VisionIOInputs {
        public double yawDeg = 0.0;
        public double pitchDeg = 0.0;
        public double area = 0.0;
        public LimelightHelpers.PoseEstimate limelightMeasurement;
    }

    public Vision(String name) {
        this.name = name;
    }

    public double getPitch() {
        return inputs.pitchDeg;
    }

    public double getYaw() {
        return inputs.yawDeg;
    }

    public double getArea() {
        return inputs.area;
    }

    public Pose2d getVisionPose(){
        return inputs.limelightMeasurement.pose;
    }
    public double getVisionTimestamp(){
        return inputs.limelightMeasurement.timestampSeconds;
    }

    @Override
    public void periodic() {
        inputs.area = LimelightHelpers.getTA(name);
        inputs.pitchDeg = LimelightHelpers.getTY(name);
        inputs.yawDeg = LimelightHelpers.getTX(name);

        inputs.limelightMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue(name);

        Logger.processInputs("Vision", inputs);
        Logger.recordOutput("Vision/Yaw Degrees", inputs.yawDeg);
    }
}
