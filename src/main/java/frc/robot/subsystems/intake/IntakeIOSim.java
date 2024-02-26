package frc.robot.subsystems.intake;

import edu.wpi.first.math.MathUtil;
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
    public void setIntakeSpeed(double speed){
        motor.setInputVoltage(MathUtil.clamp(12 * speed, -12, 12));
    }

    @Override
    public void stopIntake() {
        motor.setInputVoltage(0);
    }

    @Override
    public void setIntakeVoltageDefault() {
        motor.setInputVoltage(IntakeConstants.defaultIntakeVoltage);
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        motor.update(0.02);
        inputs.currentAmps = motor.getCurrentDrawAmps();
        inputs.position = motor.getAngularPositionRad();
        inputs.velocity = motor.getAngularVelocityRadPerSec();
        inputs.voltage = voltage;
    }
}
//setinputvoltage
