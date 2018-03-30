package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.utils.BetterController;
import org.usfirst.frc.team223.robot.utils.GeneralUtils;
import org.usfirst.frc.team223.robot.utils.Panic;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveAuto extends DriveBase
{
	// private AHRS ahrs;
	private boolean fast = false;

	private BetterController rotateController;
	private BetterController leftController;
	private BetterController rightController;

	private double leftDrive = 0;
	private double rightDrive = 0;
	private double leftRotate = 0;
	private double rightRotate = 0;

	private PIDOutput leftOut = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			if (!fast)
			{
				if (output > 0) output = Math.min(output, .6);
				if (output < 0) output = Math.max(output, -.6);
			}
			leftDrive = output;
		}
	};
	private PIDOutput rightOut = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			if (!fast)
			{
				if (output > 0) output = Math.min(output, .6);
				if (output < 0) output = Math.max(output, -.6);
			}
			rightDrive = output;
		}
	};
	private PIDOutput rotateOut = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			// rotate(output);
			leftRotate = output;
			rightRotate = -output;
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
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet()
		{
			return drive.getLeftPosition();
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
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet()
		{
			return drive.getRightPosition();
		}
	};

	private PIDSource rotateSrc = new PIDSource()
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
			return ahrs.getAngle();
		}
	};

	// default pids change in general init
	private double rp = 0.0175;
	private double ri = 0.00000;
	private double rd = 0.004;

	private double ap = 0.0015;
	private double ai = 0.000;
	private double ad = 0.003;

	public DriveAuto(DriveTrain drive, AHRS ahrs)
	{
		super(drive, ahrs);
		rotateController = new BetterController(rp, ri, rd, rotateSrc, rotateOut);
		leftController = new BetterController(ap, ai, ad, leftSrc, leftOut);
		rightController = new BetterController(ap, ai, ad, rightSrc, rightOut);
		addController("rotate", rotateController);
		addController("left", leftController);
		addController("right", rightController);
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
		Thread.sleep(2500);
		this.stopControllers();
	}

	public void go(double set, int millisec) throws InterruptedException
	{
		double leftSetPoint = set + drive.getLeftPosition();
		double rightSetPoint = set + drive.getRightPosition();

		leftController.startPID(leftSetPoint);
		rightController.startPID(rightSetPoint);
		rotateController.startPID(ahrs.getAngle());

		if (fast) while (drive.getLeftPosition() < leftSetPoint)
		{
			Thread.sleep(20);
		}
		else
		{
			Thread.sleep(millisec);
		}

		Panic.panic = true;
		System.out.println("AAAAAAAAAAAAAAAAAAAAaaa");
		this.stopControllers();
	}

	public void update()
	{
		// System.out.println(leftDrive+" "+rightDrive);
		double left = leftRotate + leftDrive;
		double right = rightRotate + rightDrive;

		drive.setMotors(left, right);
	}

	public void setFast(boolean fast)
	{
		this.fast = fast;
	}

}
