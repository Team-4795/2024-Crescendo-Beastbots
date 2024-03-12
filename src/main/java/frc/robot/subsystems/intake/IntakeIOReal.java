package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

public class IntakeIOReal implements IntakeIO {
    private CANSparkMax motor = new CANSparkMax(IntakeConstants.canID, MotorType.kBrushless);

    public IntakeIOReal() {
        motor.setSmartCurrentLimit(IntakeConstants.currentLimit);
        motor.burnFlash();
    }

    @Override
    public void setIntakeVoltage(double voltage) {
        motor.set(voltage);
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.currentAmps = motor.getOutputCurrent();
        inputs.velocityRPM = motor.getEncoder().getVelocity();
        inputs.voltage = motor.getBusVoltage() * motor.getAppliedOutput();
    }

}
