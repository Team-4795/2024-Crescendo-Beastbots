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

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public final class Constants {
  public static final Mode currentMode = Mode.REAL;
  public static final int controllerCount = 1;
  public static final double DT = 0.02;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  public static double slowModeVoltageLimit = 4;

  public record Setpoint(double intake, double shooter, double pivot) {
  }

  public static class StateConstants {
    public static final Setpoint intake = new Setpoint(0, 0, 1);
    public static final Setpoint shooter = new Setpoint(0, 0, 0.5);
  }

  public static class OI {
    public static final CommandXboxController driveController = new CommandXboxController(0);
    public static final CommandXboxController opController = new CommandXboxController(1);
  }
}
