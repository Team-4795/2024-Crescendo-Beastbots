package frc.robot.subsystems.shooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ShooterIOSim implements ShooterIO {
    DCMotorSim motor = new DCMotorSim(DCMotor.getNEO(2), 1, 0.001);
    private double appliedVolts = 0.0;

    @Override
    public void setShooterVoltage(double volts) {
        appliedVolts = MathUtil.clamp(12 * volts, -12, 12);
        motor.setInputVoltage(appliedVolts);
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        motor.update(0.02);
        
    }
}
