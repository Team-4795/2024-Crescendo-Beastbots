package frc.robot.util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class SparkMax {
    private CANSparkMax motor;

    public SparkMax(int CANID, MotorType type){
        motor = new CANSparkMax(CANID, type);
        
        motor.setSmartCurrentLimit(40);

        motor.burnFlash();
    }

    public void set(double value){
        motor.set(value);
    }

    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }

    public CANSparkMax getMotor(){
        return motor;
    }

    public void follow(CANSparkMax leader) {
        motor.follow(leader);
    }

    public void setVoltageCompensation(double num) {
        motor.enableVoltageCompensation(num);
    }
    
    public void setOpenLoopRampRate(double num) {
        motor.setOpenLoopRampRate(num);
    }

    public void setCurrentLimit(int num) {
        motor.setSmartCurrentLimit(num);
    }

    public void setClosedLoopRampRate(double num) {
        motor.setClosedLoopRampRate(num);
    }

}
    /*
     * motor.setVoltageCompensation
     * motor.setOpenLoopRampRate
     * motor.setClosedLoopRampRate
     * change current limit
     * 
     */