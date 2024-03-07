package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.AutoLog;

public interface PivotIO {
    @AutoLog
    public static class PivotIOInputs {
        public double angleRad = 0.0;
        public double angleRadPerSec = 0.0;
        public double appliedVolts = 0.0;
        public double currentOutput = 0.0;
    }

    public default void updateInputs(PivotIOInputs inputs) {
    }

    public default void setPivotVoltage(double volts) {
    }

    public default void setSpeed(double speed) {
    }
}
