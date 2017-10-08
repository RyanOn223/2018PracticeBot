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
		//talon0.set(speed);
		talon1.set(speed);
		talon2.set(speed);
	}
	public void blend(boolean blend)
	{
		blender.set(blend?-1:0);
	}
	public void intake(boolean b)
	{
		intake.set(b?1:0);
	}
	@Override
	protected void initDefaultCommand()
	{
		// TODO Auto-generated method stub
		
	}


}
