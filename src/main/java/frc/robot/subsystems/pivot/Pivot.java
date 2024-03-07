package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {
    private final PivotIO io;
    private final PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
    private final ProfiledPIDController controller = new ProfiledPIDController(
            PivotConstants.kP,
            PivotConstants.kI,
            PivotConstants.kD,
            new TrapezoidProfile.Constraints(
                    PivotConstants.maxV,
                    PivotConstants.maxA));
    private double velocity = 0.0;
    private static Pivot instance;

    private Pivot(PivotIO io) {
        this.io = io;
        io.updateInputs(inputs);
    }

    public static Pivot init(PivotIO io) {
        if (instance == null) {
            instance = new Pivot(io);
        }
        return instance;
    }

    public static Pivot getInstance() {
        return instance;
    }

    public void setVelocity(double v) {
        this.velocity = v;
    }

    public double getPosition() {
        return inputs.angleRad;
    }

    public double getVelocity() {
        return inputs.angleRadPerSec;
    }

    public double getVoltage() {
        return inputs.appliedVolts;
    }

    public double getCurrent() {
        return inputs.currentOutput;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Pivot", inputs);
        // io.setSpeed(
        // controller.calculate(inputs.angleRev, targetAngle)
        // );
        io.setPivotVoltage(MathUtil.clamp(velocity * 12, -12, 12));
    }
}
