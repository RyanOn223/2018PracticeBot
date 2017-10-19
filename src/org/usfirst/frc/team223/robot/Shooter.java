package org.usfirst.frc.team223.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Shooter
{
	Talon blender;
	CANTalon intake;

	CANTalon talon0;
	CANTalon talon1;
	CANTalon talon2;

	private int shootSpeed=0;
	
	
	
	public Shooter(int a, int b, int c, int blender, int intake)
	{
		//talon0 = new CANTalon(a);
		talon1 = new CANTalon(b);
		talon2 = new CANTalon(c);
		this.blender = new Talon(blender);
		this.intake = new CANTalon(intake);
	}

	public void set(double speed)
	{
		
		if(speed==0)
		{
			
			talon1.set(0);
			talon2.set(0);
			return;
		}
		double a = shootPID(speed, talon2.getSpeed());
		talon2.set(speed);
		//talon1.set(-.1);
	}

	private static double pk =.1;
	private static double ik = .1;
	private static double dk = .1;
	private static double fk=.001;
	double prevError = 0;
	double i = 0;

	public double shootPID(double target, double actual)
	{
		double error = target-actual;
		double d = prevError - error;
		i += error;
		prevError = error;
		double output=pk * error + ik * i + dk * (error - prevError) + fk * target;
		return output;
		//return (error * pk + d * dk + i * ik);
	}

	public void blend(boolean blend)
	{
		blender.set(blend ? -1 : 0);
	}

	public void intake(boolean b)
	{
		intake.set(b ? 1 : 0);
	}

	protected void initDefaultCommand(){}

	public void setSpeed(int speed)
	{
		this.shootSpeed =speed;
		
	}

/*	@Override
	protected double returnPIDInput()
	{
		return talon2.getSpeed();
	}

	@Override
	protected void usePIDOutput(double output)
	{
		talon1.pidWrite(output);
		talon2.pidWrite(output);
		
	}*/
}
