package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private double speed = 0.0;
    private IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    private static Intake instance;

    public Intake(IntakeIO io) {
        this.io = io;
    }

    public static Intake getInstance() {
        return instance;
    }

    public static void init(IntakeIO io) {
        if (instance == null) {
            instance = new Intake(io);
        }
    }

    public void setIntakeSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);
        io.setIntakeSpeed(speed);
    }
}
