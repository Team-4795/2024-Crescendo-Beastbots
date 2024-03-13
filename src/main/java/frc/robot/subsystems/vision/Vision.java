package frc.robot.subsystems.vision;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.LimelightHelpers;

public class Vision extends SubsystemBase {
    private VisionIOInputsAutoLogged inputs = new VisionIOInputsAutoLogged();
    private String name;

    @AutoLog
    public static class VisionIOInputs {
        public double yawDeg = 0.0;
        public double pitchDeg = 0.0;
        public double area = 0.0;
        public Pose2d limelightMeasurement;
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
        return inputs.limelightMeasurement;
    }

    @Override
    public void periodic() {
        inputs.area = LimelightHelpers.getTA(name);
        inputs.pitchDeg = LimelightHelpers.getTY(name);
        inputs.yawDeg = LimelightHelpers.getTX(name);

        inputs.limelightMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue(name).pose;

        Logger.processInputs("Vision", inputs);
        Logger.recordOutput("Vision/Yaw Degrees", inputs.yawDeg);
    }
}
