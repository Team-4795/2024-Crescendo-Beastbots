package frc.robot;

import frc.robot.Constants.Setpoint;
import frc.robot.Constants.StateConstants;

public enum State {
    Intake(StateConstants.intake),
    Shoot(StateConstants.shooter);



    Setpoint setpoint;

    State(Setpoint setpoint) {
        this.setpoint = setpoint;
    }
}