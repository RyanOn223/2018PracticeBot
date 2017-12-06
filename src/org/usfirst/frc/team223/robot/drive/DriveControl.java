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
			return PIDSourceType.kDisplacement;
		}
	};

	private PIDOutput out = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			rotate(output);
		}
	};

	private static double pk = .005;
	private static double ik = .00;
	private static double dk = 0;

	private PIDController controller;

	private AHRS ahrs;

	public DriveControl( DriveBase drive,AHRS ahrs)
	{
		this.drive = drive;
		this.ahrs = ahrs;
		controller = new PIDController(pk, ik, dk, ahrs, out);
	}
	public double get()
	{
		return controller.get();
	}
	public void rotate(double amount)
	{
		
		drive.setSides(amount, -amount);
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
