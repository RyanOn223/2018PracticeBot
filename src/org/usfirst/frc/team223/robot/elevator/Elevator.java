package org.usfirst.frc.team223.robot.elevator;

import org.usfirst.frc.team223.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Elevator
{
	TalonSRX talon0=new TalonSRX(RobotMap.elevator0);//encoder here
	TalonSRX talon1=new TalonSRX(RobotMap.elevator1);//slave
	
	Solenoid solenoid = new Solenoid(RobotMap.pcmID, RobotMap.elevateSolenoid);
	
	public Elevator()
	{
		talon0.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);		
		
		//setSlave
		talon1.set(ControlMode.Follower, RobotMap.elevator0);
		//done
	}
	public void setPistons(boolean yes)
	{
		solenoid.set(yes);
	}
	public void setSpeed(double L)
	{
		talon0.set(ControlMode.PercentOutput,L);
	}
	
	public void resetEncoders()
	{
		talon0.setSelectedSensorPosition(0, 0, 0);
	}
	
	public double getSpeed()
	{
		return talon0.getSelectedSensorVelocity(0);
	}
	
	public double getPosition()
	{
		return talon0.getSelectedSensorPosition(0);
	}
}
