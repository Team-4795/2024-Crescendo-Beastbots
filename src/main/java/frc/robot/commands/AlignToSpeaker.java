package frc.robot.commands;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OI;
import frc.robot.subsystems.drive.Drive;

public class AlignToSpeaker extends Command {
    private double angle;
    private double x;
    private double y;
    private PIDController controller = new PIDController(1, 0, 0);
    private double output;
    public AlignToSpeaker() {
    }

    @Override
    public void execute() {
        double realAngle = Drive.getInstance().getPose().getRotation().getRadians();
        x = Drive.getInstance().getPose().getX() - 0.5;
        y = Drive.getInstance().getPose().getY() - 5.54;
        angle = Math.atan2(y, x) - (y > 0 ? Math.PI : -Math.PI);
        output = controller.calculate(Drive.getInstance().getPose().getRotation().getRadians(), angle);
        Drive.getInstance().driveArcade(
            OI.driveController.getRightY(), 
            -output
        );
        Logger.recordOutput("Align/controlleroutput", output);
        Logger.recordOutput("Align/target", angle);
    }

    @Override
    public boolean isFinished() {
        if(MathUtil.isNear(angle, Drive.getInstance().getPose().getRotation().getRadians(), 0.1)){
            Drive.getInstance().stop();
            return true;
        }
        else {
            return false;
        }
    }
}
