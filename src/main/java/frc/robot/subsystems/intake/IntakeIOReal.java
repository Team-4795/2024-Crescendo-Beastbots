package frc.robot.subsystems.intake;

import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class IntakeIOReal implements IntakeIO {
    private CANSpark coolintake = new CANSpark.Motor(Controller.MAX, IntakeConstants.canID).configure();

    @Override
    public void setIntakeSpeed(double Speed) {
        coolintake.set(Speed);
        
    }

    @Override
    public void stopIntake() {
        coolintake.set(0.0);
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.currentAmps = coolintake.getCurrent();
        inputs.position = coolintake.getRelativePosition();
        inputs.velocity = coolintake.getAbsoluteVelocity();
        inputs.voltage = coolintake.getVoltage();
       
    }

    @Override
    public void setIntakeSpeedDefault() {
        coolintake.set(IntakeConstants.defaultIntakeSpeed);
    }
    
    
}
