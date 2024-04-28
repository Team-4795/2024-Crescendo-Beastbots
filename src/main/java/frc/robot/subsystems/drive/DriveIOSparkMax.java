// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.subsystems.drive;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * NOTE: To use the Spark Flex / NEO Vortex, replace all instances of "CANSparkMax" with
 * "CANSparkFlex".
 */
public class DriveIOSparkMax implements DriveIO {
  private static final double GEAR_RATIO = 10.0;
  private static final double lKP = 2.5; // TODO: MUST BE TUNED, consider using REV Hardware Client
  private static final double lKD = 0; // TODO: MUST BE TUNED, consider using REV Hardware Client

  private static final double rKP = 1; // TODO: MUST BE TUNED, consider using REV Hardware Client
  private static final double rKD = 0;
   // TODO: MUST BE TUNED, consider using REV Hardware Client

  private final CANSparkMax leftLeader = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax rightLeader = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax leftFollower = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax rightFollower = new CANSparkMax(4, MotorType.kBrushless);
  private final RelativeEncoder leftEncoder = leftLeader.getEncoder();
  private final RelativeEncoder rightEncoder = rightLeader.getEncoder();
  private final SparkPIDController leftPID = leftLeader.getPIDController();
  private final SparkPIDController rightPID = rightLeader.getPIDController();
  private final DifferentialDrive drive;

  private final AHRS imu = new AHRS(SPI.Port.kMXP);

  public DriveIOSparkMax() {
    leftLeader.restoreFactoryDefaults();
    rightLeader.restoreFactoryDefaults();
    leftFollower.restoreFactoryDefaults();
    rightFollower.restoreFactoryDefaults();
    
    leftLeader.setIdleMode(IdleMode.kCoast);
    rightLeader.setIdleMode(IdleMode.kCoast);
    leftFollower.setIdleMode(IdleMode.kCoast);
    rightFollower.setIdleMode(IdleMode.kCoast);

    leftLeader.setCANTimeout(250);
    rightLeader.setCANTimeout(250);
    leftFollower.setCANTimeout(250);
    rightFollower.setCANTimeout(250);

    leftLeader.setInverted(false);
    rightLeader.setInverted(true);
    leftFollower.follow(leftLeader, false);
    rightFollower.follow(rightLeader, false);

    leftLeader.enableVoltageCompensation(12.0);
    rightLeader.enableVoltageCompensation(12.0);
    leftLeader.setSmartCurrentLimit(60);
    rightLeader.setSmartCurrentLimit(60);

    leftPID.setP(lKP);
    leftPID.setD(lKD);
    rightPID.setP(rKP);
    rightPID.setD(rKD);

    leftLeader.burnFlash();
    rightLeader.burnFlash();
    leftFollower.burnFlash();
    rightFollower.burnFlash();

    drive = new DifferentialDrive(rightLeader::set, leftLeader::set);

  }

  @Override
  public void driveTank(double leftspeed, double rightspeed) {
    drive.tankDrive(leftspeed, rightspeed);
  }

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    inputs.leftPositionRad = Units.rotationsToRadians(leftEncoder.getPosition() / GEAR_RATIO);
    inputs.leftVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(leftEncoder.getVelocity() / GEAR_RATIO);
    inputs.leftAppliedVolts = leftLeader.getAppliedOutput() * leftLeader.getBusVoltage();
    inputs.leftCurrentAmps =
        new double[] {leftLeader.getOutputCurrent(), leftFollower.getOutputCurrent()};

    inputs.rightPositionRad = Units.rotationsToRadians(rightEncoder.getPosition() / GEAR_RATIO);
    inputs.rightVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(rightEncoder.getVelocity() / GEAR_RATIO);
    inputs.rightAppliedVolts = rightLeader.getAppliedOutput() * rightLeader.getBusVoltage();
    inputs.rightCurrentAmps =
        new double[] {rightLeader.getOutputCurrent(), rightFollower.getOutputCurrent()};

    inputs.gyroYaw = Rotation2d.fromDegrees(imu.getYaw());
  }

  @Override
  public void setVoltage(double leftVolts, double rightVolts) {
    leftLeader.setVoltage(leftVolts);
    rightLeader.setVoltage(rightVolts);
  }

  @Override
  public void zeroHeading() {
    imu.reset();
  }

  @Override
  public void setVelocity(
      double leftRadPerSec, double rightRadPerSec, double leftFFVolts, double rightFFVolts) {
    leftPID.setReference(
        Units.radiansPerSecondToRotationsPerMinute(leftRadPerSec * GEAR_RATIO),
        ControlType.kVelocity,
        0,
        leftFFVolts);
    rightPID.setReference(
        Units.radiansPerSecondToRotationsPerMinute(rightRadPerSec * GEAR_RATIO),
        ControlType.kVelocity,
        0,
        rightFFVolts);
  }
}
