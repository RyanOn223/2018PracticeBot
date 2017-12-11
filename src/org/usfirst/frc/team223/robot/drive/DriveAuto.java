package org.usfirst.frc.team223.robot.drive;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDOutput;

public class DriveAuto
{
	private DriveBase drive;
	private AHRS ahrs;
	private BetterController controller;

	private PIDOutput out = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			rotate(output);
		}
	};
	private double p = 0.001;
	private double i = 0;
	private double d = 0;

	public DriveAuto(DriveBase drive, AHRS ahrs)
	{
		this.drive = drive;
		this.ahrs = ahrs;
		controller = new BetterController(p, i, d, ahrs, out);
	}

	public void forward(double amount)
	{
		drive.setMotors(amount);
	}

	public double getPID()
	{
		return controller.get();
	}

	public void start(double point)
	{
		controller.startPID(point);
	}

	public void set(double set)
	{
		controller.setSetpoint(set);
	}

	public void stop()
	{
		controller.disable();
		controller.reset();
	}

	protected void rotate(double output)
	{
		drive.setSides(output, -output);
	}

	public void setPID(double p, double i, double d)
	{
		controller.setPID(p, i, d);
	}
}
