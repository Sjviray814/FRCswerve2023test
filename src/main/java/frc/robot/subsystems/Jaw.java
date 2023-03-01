package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.math.Conversions;
import frc.robot.Constants;

public class Jaw extends SubsystemBase {

  private CANSparkMax jawMotor;
  private SparkMaxAbsoluteEncoder jawEncoder;
  private SparkMaxPIDController jawPIDController;

  public Jaw() {
    jawMotor = new CANSparkMax(Constants.Snake.jawMotorID,  MotorType.kBrushless);
    
    jawEncoder = jawMotor.getAbsoluteEncoder(Type.kDutyCycle);

    initPID();
    resetMotors();


    setEncoderCoversions();
  }

  public void initPID(){
    jawPIDController.setP(0.05);
    jawPIDController.setI(0.0);
    jawPIDController.setD(0.0);
    jawPIDController.setFF(0.0);
  }

  public void setEncoderCoversions(){
    jawEncoder.setPositionConversionFactor((1/Constants.Snake.jawGearRatio) * 360.0); // We do 1 over the gear ratio because 1 rotation of the motor is < 1 rotation of the module
  }

  public void resetMotors(){
    jawMotor.restoreFactoryDefaults();
    jawMotor.setIdleMode(Constants.Snake.jawNeutralMode); 
  }

  public void setNeckAngle(double angle){
    jawPIDController.setReference(angle, ControlType.kPosition);
  }
  
  public void resetjawEncoder() {
    jawEncoder.setZeroOffset(jawEncoder.getPosition());
  }

  public double getJawAngle(){
    return jawEncoder.getPosition();
  }
  
  public void jawOpen(){
    jawMotor.set(.9); 
  }
  
  public void jawClose(){
    jawMotor.set(-.9); 
  }

  public void jawOff(){
    jawMotor.set(0);
  }

  @Override
  public void periodic() {
    
    SmartDashboard.putNumber("Neck Angle", getJawAngle());

  }
}
