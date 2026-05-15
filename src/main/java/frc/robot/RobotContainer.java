// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.JoysticksConstants;
import frc.robot.subsystems.drivetrain.Swerve;
import swervelib.SwerveInputStream;

public class RobotContainer {
    // ====== Subsystems ======
    public final Swerve swerve = new Swerve(new File(Filesystem.getDeployDirectory(), "swerve"));

    // ====== Driver Control ======
    private final CommandXboxController driver = new CommandXboxController(0);

    // ====== Auto Chooser ======
    private final SendableChooser<Command> autoChooser;

    // ====== Drive Streams ======
    SwerveInputStream driveAngularVelocity = SwerveInputStream.of(
            swerve.getSwerveDrive(),
            () -> driver.getLeftY() * -1,
            () -> driver.getLeftX() * -1)
            .withControllerRotationAxis(() -> driver.getRightX() * -1)
            .deadband(JoysticksConstants.deadband)
            .scaleTranslation(0.8)
            .allianceRelativeControl(true)
            .scaleRotation(0.8);

    SwerveInputStream driveAngularVelocityLowMode = SwerveInputStream.of(
            swerve.getSwerveDrive(),
            () -> driver.getLeftY() * -1,
            () -> driver.getLeftX() * -1)
            .withControllerRotationAxis(() -> driver.getRightX() * -1)
            .deadband(JoysticksConstants.deadband)
            .scaleTranslation(0.3)
            .allianceRelativeControl(true)
            .scaleRotation(0.3);

    SwerveInputStream driveAngularVelocityInvert = SwerveInputStream.of(
            swerve.getSwerveDrive(),
            () -> driver.getLeftY() * 1,
            () -> driver.getLeftX() * 1)
            .withControllerRotationAxis(() -> driver.getRightX())
            .deadband(JoysticksConstants.deadband)
            .scaleTranslation(1.0)
            .allianceRelativeControl(true)
            .scaleRotation(0.6);

    // ====== Constructor ======
    public RobotContainer() {
        configureBindings();

        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Chooser", autoChooser);
        // SmartDashboard.putData("Pivot Subsystem", pivot); // <--- mostra no dashboard
        DriverStation.silenceJoystickConnectionWarning(true);
    }

    // ====== Bindings ======
    private void configureBindings() {
    Command driveFieldOrientedVelocity = swerve.driveFieldOriented(driveAngularVelocity);
    
    Command driveSlow = swerve.driveFieldOriented(driveAngularVelocityLowMode);

    swerve.setDefaultCommand(driveFieldOrientedVelocity);

    driver.back().toggleOnTrue(driveSlow);


    }
    // ====== Autonomous ======
    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    // ====== Utility ======
    public void setMotorBrake(boolean brake) {
        swerve.setMotorBrake(brake);
    }

    public void setHeadingCorrection(boolean heading) {
        swerve.getSwerveDrive().setHeadingCorrection(heading);
    }
}