// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autocommands;

import com.ctre.phoenix.time.StopWatch;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.subsystems.*;



public class BalanceRobot extends CommandBase {
  /** Creates a new AutoDrive. */

  private Swerve drivetrain;
  private Timer timer;
  private double initAngle;

  public BalanceRobot(Swerve drivetrain, double initAngle) {
    timer = new Timer();
    this.initAngle = initAngle;
    this.drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(isBalanced() == -1){
      new AutoDrive(0.35 , drivetrain, false, true).execute();
    } else if(isBalanced() == 1){
      new AutoDrive(-0.35, drivetrain, false, true).execute();
    } else {
      SmartDashboard.putNumber("BalanceTimer", timer.get());
      new AutoDrive(0, drivetrain, false, true).execute();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    new AutoDrive(0, drivetrain, false, true).execute();
  }


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if(isBalanced() == 0 && timer.hasElapsed(1)){
      return true;
    } else {
      return false;
    }


  }

  public int isBalanced(){
    if(drivetrain.getPitch() < (initAngle - 3)){
        timer.reset();
        return -1;
    } else if(drivetrain.getPitch() > (initAngle + 3)){
        timer.reset();
        return 1;
    } else {
        return 0;
    }
}

}
