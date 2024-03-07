package frc.robot.subsystems.pivot;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

public class PivotIOReal implements PivotIO {
   private CANSparkMax pivotMotor = new CANSparkMax(PivotConstants.CANID, MotorType.kBrushless);

   public PivotIOReal() {
      pivotMotor.setSmartCurrentLimit(PivotConstants.currentLimit);
      pivotMotor.getEncoder().setVelocityConversionFactor(1 / (2 * Math.PI * 60));
      pivotMotor.getEncoder().setPositionConversionFactor(1 / 2 * Math.PI);
      pivotMotor.burnFlash();
   } 

   @Override
   public void setSpeed(double speed) {
      pivotMotor.set(speed);
   }

   @Override
   public void updateInputs(PivotIOInputs inputs) {
      inputs.appliedVolts = pivotMotor.getBusVoltage();
      inputs.angleRadPerSec = pivotMotor.getEncoder().getVelocity();
      inputs.currentOutput = pivotMotor.getOutputCurrent();
      inputs.angleRad = pivotMotor.getEncoder().getPosition();
   }
}
