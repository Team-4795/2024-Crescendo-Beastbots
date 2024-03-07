package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    // IO is input output
    // Autolog, auto logs.
    private ShooterIO io;
    private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

    private double velocity = 0.0;
    private static Shooter instance;

    private Shooter(ShooterIO io) {
        this.io = io;
        io.updateInputs(inputs);
    }

    public static Shooter getInstance() {
        return instance;
    }

    public static Shooter init(ShooterIO io) {
        if (instance == null) {
            instance = new Shooter(io);
        }
        return instance;
    }

    public void setVelocity(double v) {
        this.velocity = v;
    }

    public double getVelocity() {
        return inputs.velocityRPM;
    }
    
    public double getCurrent() {
        return inputs.current;
    }

    public double getVoltage() {
        return inputs.voltage;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Shooter", inputs);
        // Clamp clamps the voltage from 12,-12.
        io.setShooterVoltage(MathUtil.clamp(velocity * 12, -12, 12));
    }
}
