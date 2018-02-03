package org.usfirst.frc.team223.robot.elevator;

import org.usfirst.frc.team223.robot.constants.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Claw
{
	TalonSRX intake=new TalonSRX(RobotMap.intake);
	TalonSRX drop=new TalonSRX(RobotMap.claw);
	
	public Claw()
	{
		intake.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		drop.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);		
	}
	
	public void intake()
	{
		intake.set(ControlMode.PercentOutput,1);
	}
	
	public void drop(){}
	public void up(){}
}
