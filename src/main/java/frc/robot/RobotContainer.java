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

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.OI;
import frc.robot.commands.AutoCommands;
import frc.robot.commands.NamedCommandManager;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.DriveIO;
import frc.robot.subsystems.drive.DriveIOSim;
import frc.robot.subsystems.drive.DriveIOSparkMax;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIOReal;
import frc.robot.subsystems.intake.IntakeIOSim;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.pivot.PivotIOReal;
import frc.robot.subsystems.pivot.PivotIOSim;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterIOReal;
import frc.robot.subsystems.shooter.ShooterIOSim;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Drive drive;

  // Auto
  private final AutoCommands autoCommands;
  private final AutoChooser autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        drive = new Drive(new DriveIOSparkMax());
        Pivot.init(new PivotIOReal());
        Intake.init(new IntakeIOReal());
        Shooter.init(new ShooterIOReal());
        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive = new Drive(new DriveIOSim());
        Pivot.init(new PivotIOSim());
        Intake.init(new IntakeIOSim());
        Shooter.init(new ShooterIOSim());
        break;

      default:
        drive = new Drive(new DriveIO() {});
        Pivot.init(new PivotIOReal());
        break;
    }

    // Register named commands
    NamedCommandManager.register();

    // Set up auto commands.
    autoCommands = new AutoCommands();
    autoChooser = new AutoChooser(autoCommands);

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    drive.setDefaultCommand(Commands.run(
      () -> drive.driveArcade(-OI.driveController.getLeftY(), OI.driveController.getLeftX()), drive
    ));  

    OI.opController.leftBumper().whileTrue(Commands.startEnd(
      () -> Intake.getInstance().setIntakeSpeed(1), 
      () -> Intake.getInstance().setIntakeSpeed(0)
    ));

    OI.opController.rightBumper().whileTrue(Commands.startEnd(
      () -> Shooter.getInstance().setSpeed(1),
      () -> Shooter.getInstance().setSpeed(0)
    ));

    OI.opController.leftTrigger().whileTrue(Commands.startEnd(
      () -> Pivot.getInstance().setSpeed(-0.5), 
      () -> Pivot.getInstance().setSpeed(0)
    ));

    OI.opController.rightTrigger().whileTrue(Commands.startEnd(
      () -> Pivot.getInstance().setSpeed(0.5), 
      () -> Pivot.getInstance().setSpeed(0)
    ));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
