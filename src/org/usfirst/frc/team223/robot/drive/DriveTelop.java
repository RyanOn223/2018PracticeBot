package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.OI;
import org.usfirst.frc.team223.robot.utils.BetterController;
import org.usfirst.frc.team223.robot.utils.BetterJoystick;
import org.usfirst.frc.team223.robot.utils.GeneralUtils;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveTelop extends DriveBase
{	
	private BetterController controller;

	private PIDOutput rightOut = new PIDOutput() {
		@Override
		public void pidWrite(double output)
		{
			System.out.println((int)controller.getSetpoint()+" "+(int)ahrs.getAngle()+" "+output);
			left=-output;
			right=output;
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
			return ahrs.getAngle();
		}
	};
	private double left=0;
	private double right=0;
	private double p = 0.0085;
	private double i = 0.00000;
	private double d = 0.00;

	public DriveTelop(DriveTrain drive,AHRS ahrs)
	{
		super(drive,ahrs);
		controller=new BetterController(p,i,d,src,rightOut);
	}

	public void init()
	{
		controller.startPID(0);
	}
	
	/** Set the controllers based on joystick axis inputs.*/
	public void cheese(BetterJoystick stick)
	{
		drive.setMotors((stick.getAxis(OI.leftYAxis) - stick.getAxis(OI.rightXAxis)*2/3) ,
				 (stick.getAxis(OI.leftYAxis) + stick.getAxis(OI.rightXAxis)*2/3));
	}
	
	public void cheesePID(BetterJoystick stick)
	{
		controller.setSetpoint(ahrs.getAngle()+18*stick.getAxis(OI.rightXAxis));		
		drive.setMotors(stick.getAxis(OI.leftYAxis)+left,stick.getAxis(OI.leftYAxis)+right);
	}
	
	public void stop()
	{
		controller.disable();
		controller.reset();
	}
	
	public double getSet()
	{
		return controller.getSetpoint();
	}
	
	public void setPID(double p, double i, double d)
	{
	
		controller.setPID(p, i, d);
		controller.reset();
		
	}
}