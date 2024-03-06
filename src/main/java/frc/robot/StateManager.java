package frc.robot;

import org.littletonrobotics.junction.Logger;

import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.shooter.Shooter;

public class StateManager {
    private static StateManager mInstance;
    
    public State state;

    public void setState(State state) {
        this.state = state;
        setSetpoints();
    }

    public void setSetpoints() {
        Shooter.getInstance().setVelocity(this.state.setpoint.shooter());
        Intake.getInstance().setVelocity(this.state.setpoint.intake());
        Pivot.getInstance().setTargetAngle(this.state.setpoint.pivot());
    }

    public static StateManager getInstance() {
        if (mInstance == null) { 
            mInstance = new StateManager();
        }
        
        return mInstance;
    }

    public void periodic() {
        Logger.recordOutput("StateManager/State", state.toString());
    }
}