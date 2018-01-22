package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.utils.BetterController;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveAuto
{
	private DriveBase drive;
	// private AHRS ahrs;
	private BetterController rotateController;
	private BetterController leftController;
	private BetterController rightController;

	private PIDOutput leftOut = new PIDOutput() {
		@Override
		public void pidWrite(double output)
		{
			drive.setLeft(output);
		}
	};
	private PIDOutput rightOut = new PIDOutput() {
		@Override
		public void pidWrite(double output)
		{
			drive.setRight(output);
		}
	};
	private PIDOutput rotateOut = new PIDOutput() {
		@Override
		public void pidWrite(double output)
		{
			rotate(output);
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
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet()
		{
			return drive.getLeftPosition();
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
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet()
		{
			return drive.getRightPosition();
		}
	};

	// default pids change in general init
	private double rp = 0.0085;
	private double ri = 0.00000;
	private double rd = 0.00;

	private double ap = 0.001;
	private double ai = 0.000;
	private double ad = 0.003;

	public DriveAuto(DriveBase drive, AHRS ahrs)
	{
		this.drive = drive;
		// this.ahrs = ahrs;
		rotateController = new BetterController(rp, ri, rd, ahrs, rotateOut);
		leftController = new BetterController(ap, ai, ad, leftSrc, leftOut);
		rightController = new BetterController(ap, ai, ad, rightSrc, rightOut);
	}

	public double getPID()
	{
		return rotateController.get();
	}

	/**
	 * starts PID to turn degrees number of degrees
	 * 
	 * @param degrees
	 * @throws InterruptedException
	 */
	public void turn(double degrees) throws InterruptedException
	{
		rotateController.startPID(degrees);
		Thread.sleep(2000);
		this.stop();
	}

	public void go(double set) throws InterruptedException
	{
		leftController.startPID(set + drive.getLeftPosition());
		rightController.startPID(set + drive.getRightPosition());

		Thread.sleep(2000);
		this.stop();
	}

	public void stop()
	{
		rotateController.disable();
		rotateController.reset();
		leftController.disable();
		leftController.reset();
		rightController.disable();
		rightController.reset();
	}

	protected void rotate(double output)
	{
		drive.setMotors(-output, output);

	}

	public void setPID(double p, double i, double d)
	{
		rotateController.reset();
		rotateController.setPID(p, i, d);
	}
}
