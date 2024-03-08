package frc.robot.subsystems.vision;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Limelight;

public class Vision extends SubsystemBase {
    private PIDController controller = new PIDController(0, 0, 0);
    private VisionIOInputsAutoLogged inputs = new VisionIOInputsAutoLogged();
    private String name;

    @AutoLog
    public static class VisionIOInputs {
        public double yawDeg = 0.0;
        public double pitchDeg = 0.0;
        public double area = 0.0;
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

    @Override
    public void periodic() {
        inputs.area = Limelight.getTA(name);
        inputs.pitchDeg = Limelight.getTY(name);
        inputs.yawDeg = Limelight.getTX(name);
        Logger.processInputs("Vision", inputs);
    }
}
