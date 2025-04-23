// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.*;




public class Robot extends TimedRobot {

  // Drive command variables
  Double strafe;
  Double forward;
  Double rotate;
  Boolean fieldRelative;

  // The robot's subsystems
  private final DriveSubsystem swerveDrive = new DriveSubsystem();

  // The driver's controller
  private final XboxController controller = new XboxController(0);

  // The elevator
  private final ElevatorSubsystem elevator = new ElevatorSubsystem();
  
  // The muppet
  private final MuppetSubsystem monkey = new MuppetSubsystem();


  @Override
  public void robotInit() {
    elevator.init();
  }

  @Override
  public void robotPeriodic() {
    elevator.robotPeriodic();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // Initially using field relative
    fieldRelative = false;    
    swerveDrive.reset();
  }

  @Override
  public void teleopPeriodic() {
    
    // Back button - Toggles field relative on and off   
    if (controller.getBackButtonPressed()) { fieldRelative = !fieldRelative; }


    // Bumpers raise or lower the elevator
    if (controller.getRightBumperButton()) {
      monkey.elevatorstick(0.6);
      elevator.raise();
    }
    else if (controller.getLeftBumperButton()) {
      monkey.elevatorstick(0.4);
      elevator.lower();      
    }
    else {
      monkey.elevatorstick(0.5);
      elevator.stop();
    }
    
    // Get control values from the controller and apply a deadband and limit speed based on elevator height.
    forward = MathUtil.applyDeadband(-controller.getLeftY()*.2, 0.02) * elevator.elevatorspeedlimiter;
    strafe = MathUtil.applyDeadband(controller.getLeftX()*.2, 0.02) * elevator.elevatorspeedlimiter;
    rotate = MathUtil.applyDeadband(controller.getRightX()*.2, 0.02);

    monkey.drivestick(forward);

    // Send controller values to swerve drive
    swerveDrive.drive(forward, strafe, rotate, fieldRelative);

  }

  @Override
  public void testInit() {
    elevator.init();
  }

  @Override
  public void testPeriodic() {
    elevator.testPeriodic();
    // Bumpers raise or lower the elevator
    if (controller.getRightBumperButton()) {
      elevator.raise();
    }
    else if (controller.getLeftBumperButton()) {
      elevator.lower();      
    }
    else {
      elevator.stop();
    }
  }
}