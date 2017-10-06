package org.usfirst.frc.team223.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem
{
	Talon blender;
	CANTalon intake;
	
	CANTalon talon0;
	CANTalon talon1;
	CANTalon talon2;
	public Shooter(int a,int b, int c,int blender,int intake)
	{
		talon0=new CANTalon(a);
		talon1=new CANTalon(b);
		talon2=new CANTalon(c);
		this.blender=new Talon(blender);
		this.intake=new CANTalon(intake);
	}
	
	
	
	
	public void set(double speed)
	{
		
		
		
		
		
		talon1.set(speed);
		talon2.set(speed);
		
		
		
	
	}
	
	
	int ek=1;
	int dk=1;
	int ik=1;
	
	double prevError=0;
	double i=0;
	public double shootPID(double target,double actual)
	{
		double error=actual-target;
		double d=prevError-error;
		i+=error;
		
		prevError=error;
		
		return(error*ek+d*dk+i*ik);
	}
	
	
	public void blend(boolean blend){blender.set(blend?-1:0);}
	public void intake(boolean b){intake.set(b?1:0);}
	protected void initDefaultCommand(){}


}
