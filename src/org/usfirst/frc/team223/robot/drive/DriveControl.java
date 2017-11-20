package org.usfirst.frc.team223.robot.drive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveControl
{
	DriveBase drive;
	private PIDSource src = new PIDSource()
	{
		@Override
		public void setPIDSourceType(PIDSourceType pidSource)
		{
		}

		@Override
		public double pidGet()
		{
			return 0;
		}

		@Override
		public PIDSourceType getPIDSourceType()
		{
			return PIDSourceType.kRate;
		}
	};

	private PIDOutput out = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			mec(output);
		}
	};

	private static double pk = .05;
	private static double ik = .1;
	private static double dk = 0;

	private PIDController controller;

	private AHRS ahrs;

	public DriveControl(AHRS ahrs, DriveBase drive)
	{
		this.drive = drive;
		this.ahrs = ahrs;
		controller = new PIDController(pk, ik, dk, ahrs, out);
	}

	public void rotate(double amount)
	{
		drive.setAllMotors(amount, -amount, amount, -amount);
	}
	
	public void mec(double amount)
	{
		drive.setAllMotors(amount, -.5, -amount, .5);
	}
	public void stopPID()
	{
		controller.disable();
		controller.reset();
		drive.stopMotors();
	}

	public boolean isEnabled()
	{
		return controller.isEnabled();
	}

	public void startPID(double startPoint)
	{
		controller.setSetpoint(startPoint);
		controller.enable();
	}

	public void setTargetAngle(double out)
	{
		controller.setSetpoint(out);
	}

	public void setPID(double p, double i, double d)
	{
		controller.setPID(p, i, d);
	}
}
