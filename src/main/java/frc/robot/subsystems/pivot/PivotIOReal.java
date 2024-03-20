package frc.robot.subsystems.pivot;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.util.Units;

import com.revrobotics.CANSparkMax;

public class PivotIOReal implements PivotIO {
   private CANSparkMax pivotMotor = new CANSparkMax(PivotConstants.CANID, MotorType.kBrushless);

   public PivotIOReal() {
      pivotMotor.setSmartCurrentLimit(PivotConstants.currentLimit);
      pivotMotor.burnFlash();
   } 

   @Override
   public void setSpeed(double speed) {
      pivotMotor.set(speed);
   }

   @Override
   public void setPivotVoltage(double voltage) {
      pivotMotor.setVoltage(voltage);
   }

   @Override
   public void updateInputs(PivotIOInputs inputs) {
      inputs.appliedVolts = pivotMotor.getBusVoltage() * pivotMotor.getAppliedOutput();
      inputs.velocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(pivotMotor.getEncoder().getVelocity());
      inputs.currentOutput = pivotMotor.getOutputCurrent();
      inputs.angleRad = Units.rotationsToRadians(pivotMotor.getEncoder().getPosition());
   }
}
