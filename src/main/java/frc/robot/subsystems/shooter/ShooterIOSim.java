package frc.robot.subsystems.shooter;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ShooterIOSim implements ShooterIO {
    DCMotorSim motor = new DCMotorSim(DCMotor.getNEO(2), 1, 0.001);
    private double appliedVolts = 0.0;

    @Override
    public void setShooterVoltage(double volts) {
        appliedVolts = volts;
        motor.setInputVoltage(appliedVolts);
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.velocity = motor.getAngularVelocityRPM();
        inputs.voltage = appliedVolts;
        inputs.current = motor.getCurrentDrawAmps();
        motor.update(0.02);
    }
}
