package frc.robot;

public enum State {
    Intake(
       0,
       0,
       0
    );

    double pivotAngle;
    double intakeSpeed;
    double shooterSpeed;

    State(double pivotAngle, double intakeSpeed, double shooterSpeed) {
        this.pivotAngle = pivotAngle;
        this.intakeSpeed = intakeSpeed;
        this.shooterSpeed = shooterSpeed;
    }
}