package org.usfirst.frc.team223.robot.elevator;

import org.usfirst.frc.team223.robot.constants.RobotMap;
import org.usfirst.frc.team223.robot.utils.BetterController;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;

public class Elevator
{
	TalonSRX talon0=new TalonSRX(RobotMap.elevator0);//encoder here
	TalonSRX talon1=new TalonSRX(RobotMap.elevator1);//slave
	
	Solenoid solenoid = new Solenoid(RobotMap.pcmID, RobotMap.elevateSolenoid);
	
	private BetterController controller;

	private PIDOutput out = new PIDOutput() {
		@Override
		public void pidWrite(double output)
		{
			//System.out.println((int)controller.getSetpoint()+" "+(int)ahrs.getAngle()+" "+output);
			setSpeed(output);
		}
	};
	private PIDSource src = new PIDSource() {
		@Override
		public void setPIDSourceType(PIDSourceType pidSource)
		{
		}

		@Override
		public PIDSourceType getPIDSourceType()
		{
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet()
		{
			return getPosition();
		}
	};
	
	private double p = 0.005;
	private double i = 0.00000;
	private double d = 0.00;
	
	
	public Elevator()
	{
		talon0.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);		
		
		//setSlave
		talon1.set(ControlMode.Follower, RobotMap.elevator0);
		//done
		
		controller= new BetterController(p,i,d,src,out);
	}
	
	public void setHeight(double height)
	{
		controller.startPID(height);
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

	public void stopControllers()
	{
		controller.disable();
		controller.reset();		
	}
	public void setPID(double p,double i,double d)
	{
		controller.setPID(p, i, d);
	}

	public void init()
	{
		controller.startPID(0);		
	}
}
