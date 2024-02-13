package frc.robot.subsystems.intake;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class IntakeIOSim implements IntakeIO {
    DCMotorSim motor = new DCMotorSim(DCMotor.getNEO(1), 2, 0.001);

    
}
//setinputvoltage
