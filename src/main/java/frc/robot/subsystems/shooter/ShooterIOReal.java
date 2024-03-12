package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

public class ShooterIOReal implements ShooterIO {
  private CANSparkMax leftShooterMotor = new CANSparkMax(ShooterConstants.leftCanID, MotorType.kBrushless);
  private CANSparkMax rightShooterMotor = new CANSparkMax(ShooterConstants.rightCanID, MotorType.kBrushless);

  private SparkPIDController controller = leftShooterMotor.getPIDController();

  public ShooterIOReal() {
    rightShooterMotor.follow(leftShooterMotor);
    rightShooterMotor.setInverted(true);
    rightShooterMotor.setSmartCurrentLimit(ShooterConstants.currentLimit);
    leftShooterMotor.setSmartCurrentLimit(ShooterConstants.currentLimit);
    leftShooterMotor.enableVoltageCompensation(12);
    rightShooterMotor.enableVoltageCompensation(12);

    controller.setD(ShooterConstants.kD);
    controller.setI(ShooterConstants.kI);
    controller.setP(ShooterConstants.kP);

    rightShooterMotor.burnFlash();
    leftShooterMotor.burnFlash();
  }

  @Override
  public void setShooterVoltage(double volts) {
    leftShooterMotor.setVoltage(volts);
  }

  @Override
  public void updateInputs(ShooterIOInputs inputs) {
    inputs.voltage = leftShooterMotor.getBusVoltage() * leftShooterMotor.getAppliedOutput();
    inputs.velocityRPM = leftShooterMotor.getEncoder().getVelocity();
    inputs.current = leftShooterMotor.getOutputCurrent();
  }

  @Override
  public void setDesiredState(double speed) {
    controller.setReference(speed, ControlType.kVelocity);
  }
}
