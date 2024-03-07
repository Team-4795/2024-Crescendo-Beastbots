package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {
    @AutoLog
    public static class ShooterIOInputs {
        public double current = 0.0;
        public double voltage = 0.0;
        public double velocityRPM = 0.0;
    }

    //
    public default void updateInputs(ShooterIOInputs inputs) {
    }

    public default void setDesiredState(double speed) {
    }

    public default void setShooterVoltage(double volts) {
    }
}