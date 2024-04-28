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

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.pathfinding.Pathfinding;
import com.pathplanner.lib.util.PathPlannerLogging;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.Mode;
import frc.robot.subsystems.vision.Vision;
import frc.robot.util.LocalADStarAK;

public class Drive extends SubsystemBase {
  public static final double WHEEL_RADIUS = Units.inchesToMeters(3.0);
  public static final double TRACK_WIDTH = Units.inchesToMeters(30.0);

  // TODO: NON-SIM FEEDFORWARD GAINS MUST BE TUNED
  // Consider using SysId routines defined in RobotContainer
  private static final double KS = Constants.currentMode == Mode.SIM ? 0.0 : 0;
  private static final double KV = Constants.currentMode == Mode.SIM ? 0.227 : 2.56;

  private final DriveIO io;
  private final DriveIOInputsAutoLogged inputs = new DriveIOInputsAutoLogged();
  private final DifferentialDriveKinematics kinematics =
      new DifferentialDriveKinematics(TRACK_WIDTH);
  private final DifferentialDrivePoseEstimator m_PoseEstimator =
      new DifferentialDrivePoseEstimator(kinematics, new Rotation2d(), 0.0, 0.0, new Pose2d());
  private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(KS, KV);

  private final Vision Vision = new Vision("Limelight");
  private double voltageLimit = 3;
  private static Drive instance;


  public static Drive init(DriveIO io) {
        if (instance == null) {
            instance = new Drive(io);
        }
        return instance;
    }

    public static Drive getInstance() {
      return instance;
    }


  /** Creates a new Drive. */
  public Drive(DriveIO io) {
    this.io = io;
  

    AutoBuilder.configureRamsete( 
        this::getPose,
        this::setPose,
        () ->
            kinematics.toChassisSpeeds(
                new DifferentialDriveWheelSpeeds(
                    getLeftVelocityMetersPerSec(), getRightVelocityMetersPerSec())),
        (speeds) -> {
          var wheelSpeeds = kinematics.toWheelSpeeds(speeds);
          driveVelocity(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
        },
        new ReplanningConfig(),
        () ->
            DriverStation.getAlliance().isPresent()
                && DriverStation.getAlliance().get() == Alliance.Red,
        this);
    Pathfinding.setPathfinder(new LocalADStarAK());
    PathPlannerLogging.setLogActivePathCallback(
        (activePath) -> {
          Logger.recordOutput(
              "Odometry/Trajectory", activePath.toArray(new Pose2d[activePath.size()]));
        });
    PathPlannerLogging.setLogTargetPoseCallback(
        (targetPose) -> {
          Logger.recordOutput("Odometry/TrajectorySetpoint", targetPose);
        });
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Drive", inputs);
    Logger.recordOutput("Odometry/Robot", getPose());

    // Update odometry
    m_PoseEstimator.update(inputs.gyroYaw, getLeftPositionMeters(), getRightPositionMeters());

    // Update vision
    // m_PoseEstimator.addVisionMeasurement(Vision.getVisionPose(), 0);
  }

  /** Run open loop at the specified voltage. */
  public void driveVolts(double leftVolts, double rightVolts) {
    io.setVoltage(leftVolts, rightVolts);
  }

  public void driveClosedLoop() {
    io.setVelocity(WHEEL_RADIUS, TRACK_WIDTH, KV, KS);
  }

  /** Run closed loop at the specified voltage. */
  public void driveVelocity(double leftMetersPerSec, double rightMetersPerSec) {
    Logger.recordOutput("Drive/LeftVelocitySetpointMetersPerSec", leftMetersPerSec);
    Logger.recordOutput("Drive/RightVelocitySetpointMetersPerSec", rightMetersPerSec);
    
    double leftRadPerSec = leftMetersPerSec / WHEEL_RADIUS;
    double rightRadPerSec = rightMetersPerSec / WHEEL_RADIUS;
    io.setVelocity(
        leftRadPerSec,
        rightRadPerSec,
        feedforward.calculate(leftRadPerSec),
        feedforward.calculate(rightRadPerSec));
  }

  /** Run open loop based on stick positions. */
  public void driveArcade(double xSpeed, double zRotation) {
    var speeds = DifferentialDrive.arcadeDriveIK(xSpeed, zRotation, true);
    io.setVoltage(speeds.left * voltageLimit, speeds.right * voltageLimit);
  }

  public void setVoltageLimit(double limit) {
    this.voltageLimit = limit;
  }

  /** Stops the drive. */
  public void stop() {
    io.setVoltage(0.0, 0.0);
  }

  public void driveTank(double leftspeed, double rightspeed){
    io.driveTank(leftspeed, rightspeed);
  }

  /** Returns the current odometry pose in meters. */
  public Pose2d getPose() {
    return m_PoseEstimator.getEstimatedPosition();
  }

  /** Resets the current odometry pose. */
  public void setPose(Pose2d pose) {
    m_PoseEstimator.resetPosition(inputs.gyroYaw, getLeftPositionMeters(), getRightPositionMeters(), pose);
  }

  /** Returns the position of the left wheels in meters. */
  @AutoLogOutput
  public double getLeftPositionMeters() {
    return inputs.leftPositionRad * WHEEL_RADIUS;
  }

  /** Returns the position of the right wheels in meters. */
  @AutoLogOutput
  public double getRightPositionMeters() {
    return inputs.rightPositionRad * WHEEL_RADIUS;
  }

  /** Returns the velocity of the left wheels in meters/second. */
  @AutoLogOutput
  public double getLeftVelocityMetersPerSec() {
    return inputs.leftVelocityRadPerSec * WHEEL_RADIUS;
  }

  @AutoLogOutput 
  public double getVelocityDifferenceMetersPerSec() {
    return (inputs.leftVelocityRadPerSec * WHEEL_RADIUS) - (inputs.rightVelocityRadPerSec * WHEEL_RADIUS);
  }

  /** Returns the velocity of the right wheels in meters/second. */
  @AutoLogOutput
  public double getRightVelocityMetersPerSec() {
    return inputs.rightVelocityRadPerSec * WHEEL_RADIUS;
  }

  /** Returns the average velocity in radians/second. */
  public double getCharacterizationVelocity() {
    return (inputs.leftVelocityRadPerSec + inputs.rightVelocityRadPerSec) / 2.0;
  }

  public void zeroHeading() {
    io.zeroHeading();
  }
}
