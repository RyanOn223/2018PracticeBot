package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.OI;
import org.usfirst.frc.team223.robot.utils.BetterController;
import org.usfirst.frc.team223.robot.utils.BetterJoystick;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveTelop
{
	private DriveTrain drive;
	
	private BetterController rightController;
	private BetterController leftController;
	
	private PIDOutput leftOut = new PIDOutput() {
		@Override
		public void pidWrite(double output)
		{
			//rotate(output);
		}
	};

	private PIDOutput rightOut = new PIDOutput() {
		@Override
		public void pidWrite(double output)
		{
			//rotate(output);
		}
	};
	
	private PIDSource leftSrc = new PIDSource() {
		@Override
		public void setPIDSourceType(PIDSourceType pidSource)
		{
		}

		@Override
		public PIDSourceType getPIDSourceType()
		{
			return PIDSourceType.kRate;
		}

		@Override
		public double pidGet()
		{
			return drive.getLeftSpeed();
		}
	};
	
	

	private PIDSource rightSrc = new PIDSource() {
		@Override
		public void setPIDSourceType(PIDSourceType pidSource)
		{
		}

		@Override
		public PIDSourceType getPIDSourceType()
		{
			return PIDSourceType.kRate;
		}

		@Override
		public double pidGet()
		{
			return drive.getRightSpeed();
		}
	};
	
	
	public DriveTelop(DriveTrain drive)
	{
		this.drive=drive;
	}

	/** Set the controllers based on joystick axis inputs.*/
	public void cheese(BetterJoystick stick)
	{
		drive.setMotors((stick.getAxis(OI.leftYAxis) - stick.getAxis(OI.rightXAxis)*2/3) ,
				 (stick.getAxis(OI.leftYAxis) + stick.getAxis(OI.rightXAxis)*2/3));
	}
	
	public void stop()
	{
		leftController.disable();
		leftController.reset();
		rightController.disable();
		rightController.reset();
	}
	
	public void setPID(double p, double i, double d)
	{
		leftController.reset();
		rightController.setPID(p, i, d);
		rightController.reset();
		leftController.setPID(p, i, d);
	}
}