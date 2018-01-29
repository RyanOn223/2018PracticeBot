package org.usfirst.frc.team223.robot.elevator;

import org.usfirst.frc.team223.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Plate
{
	TalonSRX talon=new TalonSRX(RobotMap.plate);//encoder here
		
	public Plate()
	{
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);		
	}
	
	public void setSpeed(double L)
	{
		talon.set(ControlMode.PercentOutput,L);
	}
	
	public void resetEncoders()
	{
		talon.setSelectedSensorPosition(0, 0, 0);
	}
	
	public double getSpeed()
	{
		return talon.getSelectedSensorVelocity(0);
	}
	
	public double getPosition()
	{
		return talon.getSelectedSensorPosition(0);
	}
}
