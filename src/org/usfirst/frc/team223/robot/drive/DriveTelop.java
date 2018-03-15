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
	private BetterController rightController;
	private BetterController leftController;

	private PIDOutput rightOut = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			right = output;
		}
	};

	private PIDOutput leftOut = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			left = output;
		}
	};

	private PIDSource rightSrc = new PIDSource()
	{
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

	private PIDSource leftSrc = new PIDSource()
	{
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

	private double left = 0;
	private double right = 0;

	private double p = 0.01;
	private double i = 0.00000;
	private double d = 0.0002;

	public DriveTelop(DriveTrain drive, AHRS ahrs)
	{
		super(drive, ahrs);
		rightController = new BetterController(p, i, d, rightSrc, rightOut);
		leftController = new BetterController(p, i, d, leftSrc, leftOut);
		addController("l", leftController);
		addController("r", rightController);
	}

	public void init()
	{
		leftController.startPID(0);
		rightController.startPID(0);

	}

	/** Set the controllers based on joystick axis inputs. */
	public void cheese(BetterJoystick stick)
	{
		/// *
		drive.setMotors(-(stick.getAxis(OI.leftYAxis) - stick.getAxis(OI.rightXAxis)),
				-(stick.getAxis(OI.leftYAxis) + stick.getAxis(OI.rightXAxis)));// */

		/*
		 * for testing drive.setMotors(-(stick.getAxis(OI.leftYAxis)),
		 * -stick.getAxis(OI.rightXAxis));
		 */
	}

	/**
	 * uses rotation pid to drive straight not tested
	 * 
	 * @param stick
	 *            joystick
	 */
	public void cheesePID(BetterJoystick stick)
	{
		leftController.setSetpoint(-300 * (stick.getAxis(OI.leftYAxis) - stick.getAxis(OI.rightXAxis)));
		rightController.setSetpoint(-300 * (stick.getAxis(OI.leftYAxis) + stick.getAxis(OI.rightXAxis)));

		drive.setMotors(left, right);
	}
}
