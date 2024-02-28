package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.SparkPIDController;

import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class ShooterIOReal implements ShooterIO {
  private CANSpark leftShooterMotor = new CANSpark.Motor(Controller.MAX, ShooterConstants.leftCanID).configure();
  private CANSpark rightShooterMotor = new CANSpark.Motor(Controller.MAX, ShooterConstants.rightCanID).inverted(true)
      .follows(leftShooterMotor).configure();

  private SparkPIDController controller = leftShooterMotor.getMotor().getPIDController();

  public ShooterIOReal() {
    controller.setD(ShooterConstants.kD);
    controller.setI(ShooterConstants.kI);
    controller.setP(ShooterConstants.kP);
    rightShooterMotor.getMotor().burnFlash();
    leftShooterMotor.getMotor().burnFlash();
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