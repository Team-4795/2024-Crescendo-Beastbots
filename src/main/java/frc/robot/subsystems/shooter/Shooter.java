package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class Shooter extends SubsystemBase {
    private ShooterIO io;
    private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

    private double requestedSpeed = 0.0;
    private static Shooter instance;

    private Shooter(ShooterIO io){
        this.io = io;
        io.updateInputs(inputs);
    }

    public static Shooter getInstance() {
        return instance;
    }

    public static void init(ShooterIO io) {
        if (instance == null) {
            instance = new Shooter(io);
        }
    }

    public void setRequestedSpeed(double requestedSpeed) {
        this.requestedSpeed = requestedSpeed;
    }

    @Override
    public void periodic(){
        // leftShooterMotor.set(requestedSpeed);
    }
}
