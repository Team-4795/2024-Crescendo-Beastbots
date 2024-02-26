package frc.robot.subsystems.intake;

import com.revrobotics.SparkPIDController;

import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class IntakeIOReal implements IntakeIO {
    private CANSpark coolintake = new CANSpark.Motor(Controller.MAX, IntakeConstants.canID).configure();
    private SparkPIDController controller = coolintake.getMotor().getPIDController();

    public IntakeIOReal() {
        controller.setP(IntakeConstants.kP);
        controller.setI(IntakeConstants.kI);
        controller.setD(IntakeConstants.kD);

    }

    @Override
    public void setIntakeSpeed(double speed) {
        coolintake.set(speed);
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
