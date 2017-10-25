package org.usfirst.frc.team223.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;

public class Shooter
{
	Talon blender;
	CANTalon intake;

	CANTalon talon0;
	CANTalon talon1;
	CANTalon talon2;
	
	private static double pk =.0001;
	private static double ik = .000001;
	private static double dk = .0007;
	
	private PIDSource src = new PIDSource()
	{
		@Override public void setPIDSourceType(PIDSourceType pidSource) {}
		@Override public double pidGet() { return getShooterSpeed(); }
		@Override public PIDSourceType getPIDSourceType() { return PIDSourceType.kRate; }
	};
	
	private PIDOutput out = new PIDOutput()
	{
		@Override public void pidWrite(double output) { setShooterOutput(output); }
	};
	
	private PIDController controller;
	
	public Shooter(int a, int b, int c, int blender, int intake)
	{
		//talon0 = new CANTalon(a);
		talon1 = new CANTalon(b);
		talon2 = new CANTalon(c);
		this.blender = new Talon(blender);
		this.intake = new CANTalon(intake);
		
		// setup the pid controller
		controller = new PIDController(pk, ik, dk, src, out);
		
	}

	/**
	 * sends the raw output to the motors
	 */
	public void setShooterOutput(double out)
	{
		// talon0.set(out);
		talon1.set(out);
		talon2.set(out);
	}

	public void setSpeed(double rpm)
	{
		controller.setSetpoint(rpm);
		controller.enable();
	}
	
	public void stopPID()
	{
		controller.disable();
		setShooterOutput(0);
	}
	
	/**
	 * @return the current speed of the shooter
	 */
	public double getShooterSpeed()
	{
		return talon2.getSpeed();
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
}
