package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class PivotIOReal implements PivotIO {
   private CANSpark pivotMotor = new CANSpark.Motor(Controller.MAX, PivotConstants.CANID).configure();

   @Override
   public void setSpeed(double speed) {
      pivotMotor.set(speed);
   }

   @Override
   public void updateInputs(PivotIOInputs inputs) {
      inputs.appliedVolts = pivotMotor.getVoltage();
      inputs.angleRevPerSec = pivotMotor.getAbsoluteVelocity();
      inputs.currentOutput = pivotMotor.getCurrent();
      inputs.angleRev = pivotMotor.getRelativePosition();
   }
}
