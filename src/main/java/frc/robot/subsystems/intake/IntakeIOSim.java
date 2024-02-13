package frc.robot.subsystems.intake;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class IntakeIOSim implements IntakeIO {
    DCMotorSim motor = new DCMotorSim(DCMotor.getNEO(1), 2, 0.001);

    public IntakeIOSim() {

    }

    @Override
    public void setIntakeVoltage(double voltage) {
        motor.setInputVoltage(voltage);
        
    }

    @Override
    public void stopIntake() {
        motor.setInputVoltage(0);

    }

    @Override
    public void setIntakeVoltageDefault() {
        motor.setInputVoltage(IntakeConstants.defaultIntakeVoltage);
    }

    

}
//setinputvoltage
