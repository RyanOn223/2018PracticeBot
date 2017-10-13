package org.usfirst.frc.team223.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter// extends PIDSubsystem
{


	Talon blender;
	CANTalon intake;

	CANTalon talon0;
	CANTalon talon1;
	CANTalon talon2;

	public Shooter(int a, int b, int c, int blender, int intake)
	{
		//super("shooter",pk,ik,dk,100);
		talon0 = new CANTalon(a);
		talon1 = new CANTalon(b);
		talon2 = new CANTalon(c);
		this.blender = new Talon(blender);
		this.intake = new CANTalon(intake);
	}

	public void write()
	{
		System.out.println(talon2.getSpeed());
	}
	public void set(double speed)
	{

		double a = shootPID(speed, talon2.getSpeed());

		talon1.set(a);
		talon2.set(a);
		
	}

	private static double pk =.001;
	private static double dk = 0;
	private static double ik = 0;
	
	
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

/*	@Override
	protected double returnPIDInput()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output)
	{
		// TODO Auto-generated method stub
		
	}*/

}
