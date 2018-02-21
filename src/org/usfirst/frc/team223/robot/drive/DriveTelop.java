package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.constants.OI;
import org.usfirst.frc.team223.robot.utils.BetterController;
import org.usfirst.frc.team223.robot.utils.BetterJoystick;

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
		addController("c",controller);
	}

	public void init()
	{
		controller.startPID(0);
	}
	
	/** Set the controllers based on joystick axis inputs.*/
	public void cheese(BetterJoystick stick)
	{
		drive.setMotors(-(stick.getAxis(OI.leftYAxis) - stick.getAxis(OI.rightXAxis)) ,
				 -(stick.getAxis(OI.leftYAxis) + stick.getAxis(OI.rightXAxis)));
	}
	
	
	private double getSetPrev=0;
	/**
	 * uses rotation pid to drive straight
	 * @param stick joystick
	 */
	public void cheesePID(BetterJoystick stick)
	{
		//Sets new setpoint of pid to current angle plus value of joystick
		controller.setSetpoint(controller.getSetpoint()+5*stick.getAxis(OI.rightXAxis));
		//Then sets motors to value of outputs plus forward and back
		drive.setMotors(-(stick.getAxis(OI.leftYAxis)+left),-(stick.getAxis(OI.leftYAxis)+right));
	}
}
