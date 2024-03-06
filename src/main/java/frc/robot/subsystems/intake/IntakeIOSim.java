package frc.robot.subsystems.intake;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class IntakeIOSim implements IntakeIO {
    private DCMotorSim motor = new DCMotorSim(DCMotor.getNEO(1), 2, 0.001);
    private double voltage = 0.0;

    public IntakeIOSim() {

    }

    @Override
    public void setIntakeVoltage(double voltage) {
        this.voltage = voltage;
        motor.setInputVoltage(voltage);
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        motor.update(0.02);
        inputs.currentAmps = motor.getCurrentDrawAmps();
        inputs.velocity = motor.getAngularVelocityRPM();
        inputs.voltage = voltage;
    }
}
// setinputvoltage
