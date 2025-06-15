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
  long timePressed;
  Double speedLimiter;
  Double driverSpeedLimit;

  // The robot's subsystems
  private final DriveSubsystem swerveDrive = new DriveSubsystem();

  // The driver's controller
  private final XboxController controller = new XboxController(0);

  // The elevator
  private final ElevatorSubsystem elevator = new ElevatorSubsystem();
  

  @Override
  public void robotInit() {
    elevator.init();
    driverSpeedLimit = 0.3;
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
    timePressed = 0;
  }

  @Override
  public void teleopPeriodic() {
    
    // Back button - Toggles field relative on and off   
    if (controller.getBackButtonPressed()) { fieldRelative = !fieldRelative; }


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
    
    // Get control values from the controller and apply a deadband and limit speed based on elevator height.
    forward = MathUtil.applyDeadband(-controller.getLeftY()*driverSpeedLimit, 0.02) * elevator.elevatorspeedlimiter;
    strafe = MathUtil.applyDeadband(controller.getLeftX()*driverSpeedLimit, 0.02) * elevator.elevatorspeedlimiter;
    rotate = MathUtil.applyDeadband(controller.getRightX()*driverSpeedLimit, 0.02);

    // Send controller values to swerve drive
    swerveDrive.drive(forward, strafe, rotate, fieldRelative);

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }
}