package frc.robot.subsystems.pivot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.robot.Constants;

public class PivotIOSim implements PivotIO {
    private SingleJointedArmSim pivotSim;
    private double appliedVolts;

    public PivotIOSim() {
        pivotSim = new SingleJointedArmSim(
                DCMotor.getNEO(1),
                PivotConstants.gearing,
                PivotConstants.MOI,
                PivotConstants.armLength,
                PivotConstants.minAngle,
                PivotConstants.maxAngle,
                false,
                PivotConstants.initialAngle);
    }

    @Override
    public void setPivotVoltage(double volts) {
        pivotSim.setInput(volts);
    }

    @Override
    public void setSpeed(double speed) {
        pivotSim.setInputVoltage(MathUtil.clamp(12 * speed, -12, 12));
    }

    @Override
    public void updateInputs(PivotIOInputs inputs) {
        pivotSim.update(Constants.DT);
        inputs.angleRev = pivotSim.getAngleRads() / (2 * Math.PI);
        inputs.angleRevPerSec = pivotSim.getVelocityRadPerSec() / (2 * Math.PI);
        inputs.appliedVolts = appliedVolts;
        inputs.currentOutput = pivotSim.getCurrentDrawAmps();
    }
}
