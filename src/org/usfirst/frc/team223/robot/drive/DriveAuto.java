package org.usfirst.frc.team223.robot.drive;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveAuto
{
	private DriveBase drive;
	private AHRS ahrs;
	private BetterController rotateController;
	private BetterController averageController;

	
	private PIDOutput averageOut = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			drive.setMotors(output);
		}
	};
	private PIDOutput rotateOut = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			rotate(output);
		}
	};
	
	private PIDSource averageSrc = new PIDSource()
	{
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
			return encoderGet();
		}
	};
	
	//default pids change in general init
	private double tp = 0.0065;
	private double ti = 0.000001;
	private double td = 0.005;
	
	private double ap = 0.0111;
	private double ai = 0.0001;
	private double ad = 0;

	public DriveAuto(DriveBase drive, AHRS ahrs)
	{
		this.drive = drive;
		this.ahrs = ahrs;
		rotateController = new BetterController(tp, ti, td, ahrs, rotateOut);
		averageController = new BetterController(ap, ai, ad, averageSrc, averageOut);
	}


	public double getPID()
	{
		return rotateController.get();
	}

	public double encoderGet()
	{
		return (drive.getLeftSpeed()+drive.getRightSpeed())/2;
	}
	
	
	/**
	 * starts PID to turn degrees number of degrees
	 * @param degrees
	 */
	public void turn(double degrees)
	{
		rotateController.startPID(degrees);
	}

	public void go(double set)
	{
		averageController.startPID(set);
	}

	public void stop()
	{
		rotateController.disable();
		rotateController.reset();
		averageController.disable();
		averageController.reset();
	}

	protected void rotate(double output)
	{
		drive.setSides(-output, output);
	}

	public void setPID(double p, double i, double d)
	{
		averageController.setPID(p, i, d);
	}
}
