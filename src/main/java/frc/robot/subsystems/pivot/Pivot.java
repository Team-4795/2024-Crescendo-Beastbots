package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {
    private final PivotIO io;
    private final PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
    private final ProfiledPIDController controller = 
        new ProfiledPIDController(
            PivotConstants.kP,
            PivotConstants.kI,
            PivotConstants.kD,
            new TrapezoidProfile.Constraints(
                PivotConstants.maxV, 
                PivotConstants.maxA
            )
        );

    private double targetAngle = 0.0;
    private static Pivot instance;

    public static Pivot getInstance(){
       return instance;
    }

    public static Pivot init(PivotIO io) {
        if(instance == null){
            instance = new Pivot(io);
        }
        return instance;
    }

    public Pivot(PivotIO io) {
        this.io = io;
        io.updateInputs(inputs);
    }

    public void setTargetAngle(double targetAngle) {
        this.targetAngle = MathUtil.clamp(targetAngle, PivotConstants.LowSetpointLimit, PivotConstants.HighSetpointLimit);
    }

    public double getPosition() { 
        return inputs.angleRev;
    }

    public double getTargetAngle() {
        return targetAngle;
    }

    @Override
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Pivot", inputs);
        io.setPivotVoltage(
            controller.calculate(inputs.angleRev, targetAngle)
        );
    }
}
