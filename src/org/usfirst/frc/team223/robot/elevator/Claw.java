package org.usfirst.frc.team223.robot.elevator;

import org.usfirst.frc.team223.robot.constants.RobotMap;
import org.usfirst.frc.team223.robot.utils.BetterController;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Claw
{
	TalonSRX intake=new TalonSRX(RobotMap.intake);
	TalonSRX drop=new TalonSRX(RobotMap.claw);
	
	private BetterController controller;
	
	private PIDOutput out = new PIDOutput() {
		@Override
		public void pidWrite(double output)
		{
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
	
	public Claw()
	{
		intake.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		drop.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		controller= new BetterController(p,i,d,src,out);
	}
	
	public void init()
	{
		controller.startPID(90);
	}
	
	public void setSpeed(double L)
	{
		drop.set(ControlMode.PercentOutput,L);
	}
	
	public void resetEncoders()
	{
		drop.setSelectedSensorPosition(0, 0, 0);
	}
	
	public double getPosition()
	{
		return -drop.getSelectedSensorPosition(0);
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

	public void in()
	{
		intake.set(ControlMode.PercentOutput,1);
	}
	public void out()
	{
		intake.set(ControlMode.PercentOutput,-1);
	}
	
	public void intake(boolean forward,boolean reverse)
	{
		if(forward==reverse)
		{
			intake.set(ControlMode.PercentOutput,0);
			return;
		}
		
		intake.set(ControlMode.PercentOutput,forward?-1:1);
	}
	
	public void setAngle(int deg){controller.startPID(deg);}
	public void addAngle(int j)
	{
		controller.addSetpoint(j);;
		
	}
	public void stopIntake()
	{
		intake.set(ControlMode.PercentOutput,0);		
	}

	
}
