package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {
    @AutoLog
    public static class ShooterIOInputs {

        public double voltage = 0.0;
        public double velocity = 0.0;

    }
// 
    public default void updateInputs(ShooterIOInputs inputs) {}

    public default void setShooterVoltage(double volts) {}
}