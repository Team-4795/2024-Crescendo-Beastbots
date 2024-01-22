package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.CANSpark;
import frc.robot.util.CANSpark.Controller;

public class Shooter extends SubsystemBase {
    private CANSpark leftShooterMotor = new CANSpark.Motor(Controller.MAX, ShooterConstants.leftCanID).configure();
    private CANSpark rightShooterMotor = new CANSpark.Motor(Controller.MAX, ShooterConstants.rightCanID).inverted(true).follows(leftShooterMotor).configure();
    private double requestedSpeed = 0.0;

    public Shooter(){

    }

    public void setRequestedSpeed(double requestedSpeed) {
        this.requestedSpeed = requestedSpeed;
    }

    @Override
    public void periodic(){
        leftShooterMotor.set(requestedSpeed);
    }
}
