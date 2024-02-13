package frc.robot.subsystems.shooter;

import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class ShooterIOReal implements ShooterIO {
    
    private CANSpark leftShooterMotor = new CANSpark.Motor(Controller.MAX, ShooterConstants.leftCanID).configure();
    private CANSpark rightShooterMotor = new CANSpark.Motor(Controller.MAX, ShooterConstants.rightCanID).inverted(true).follows(leftShooterMotor).configure();

    private SparkPIDController controller = leftShooterMotor.getMotor().getPIDController();

    public ShooterIOReal() { 
    }

    @Override
    public void setShooterVoltage(double volts) {
      leftShooterMotor.setVoltage(volts);
    }
    @Override
    public void updateInputs(ShooterIOInputs inputs) {
       inputs.voltage = leftShooterMotor.getVoltage();
       inputs.velocity = leftShooterMotor.getRelativeVelocity();
    }
    
    @Override
    public void setDesiredState(double speed) {
      controller.setReference(speed, ControlType.kVelocity);
    }
}
