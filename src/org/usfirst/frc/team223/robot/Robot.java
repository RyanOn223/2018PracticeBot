package org.usfirst.frc.team223.robot;

import org.usfirst.frc.team223.robot.constants.Constants;
import org.usfirst.frc.team223.robot.constants.OI;
import org.usfirst.frc.team223.robot.constants.RobotMap;
import org.usfirst.frc.team223.robot.drive.*;
import org.usfirst.frc.team223.robot.elevator.Claw;
import org.usfirst.frc.team223.robot.elevator.Elevator;
import org.usfirst.frc.team223.robot.elevator.Plate;
import org.usfirst.frc.team223.robot.utils.Latch;
import org.usfirst.frc.team223.vision.VisionServer;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
	Preferences p;
	Compressor c;
	AHRS ahrs;

	DriveTrain drive;
	DriveTelop driveTelop;
	DriveAuto driveAuto;
	Elevator elevator;
	Plate plate;
	Claw claw;

	Latch shiftLatch;
	Latch raiseLatch;
	Latch resetLatch;

	VisionServer visionServer;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		p = Preferences.getInstance();
		c = new Compressor(RobotMap.pcmID);

		ahrs = new AHRS(SPI.Port.kMXP);
		drive = new DriveTrain();
		driveTelop = new DriveTelop(drive, ahrs);
		driveAuto = new DriveAuto(drive, ahrs);
		elevator = new Elevator();
		plate = new Plate();
		claw = new Claw();

		AutoRoutines.init(driveAuto, claw, plate, elevator);
		/*
		 * visionServer = new VisionServer(50); visionServer.start();
		 */
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit()
	{
		generalInit();

		/// *gets string from dashboard, puts it in uppercase, takes first
		/// letter
		char location = p.getString("position", "D").toUpperCase().toCharArray()[0];
		int routine = p.getInt("routine", 0);

		boolean invert = p.getBoolean("left", false);

		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		char lever = gameData.charAt(0);
		char scale = gameData.charAt(1);

		// makes sure lever and scale are both L or R
		if (!((lever == 'L' || lever == 'R') && (scale == 'L' || scale == 'R')))
		{
			location = 'F';
		}

		switch (location)
		{
		// left right
		case 'L':
		case 'R':
		{
			if (lever == location && routine != 1)
			{
				AutoRoutines.near(location == 'L');
			}
			else
			{
				if (scale == location)
				{
					AutoRoutines.far(location == 'L');
				}
				else
				{
					AutoRoutines.none(location == 'L');
				}
			}
			break;
		}
		// middle
		case 'M':
			AutoRoutines.middle(lever == 'L');
			break;
		case 'D':
			System.err.println("BAD DATA FROM DASH BOARD!\n\t Moving forward to cross line");

			AutoRoutines.error();
			break;
		case 'F':
			System.err.println("BAD DATA FROM FMS!\n\t Moving forward to cross line");
			AutoRoutines.error();
			break;
		default:
			System.out.println("something bad happened\n\\t Moving forward to cross line");
			AutoRoutines.error();
			break;
		}
		// */

		/// *
		new Thread()
		{
			public void run()
			{
				try
				{ // wait for general init
					Thread.sleep(200);
					driveAuto.turn(90);
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();// */
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
		driveAuto.update();
		writeToDash();
	}

	/**
	 * Called When Disabled
	 */
	@Override
	public void disabledInit()
	{
		driveTelop.stopControllers();
		driveAuto.stopControllers();
		elevator.stopControllers();
		plate.stopControllers();
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit()
	{
		/*
		 * This doesn't work because the offsets constantly change Map<Integer,
		 * Double> driverOffsets = new HashMap<>();
		 * driverOffsets.put(OI.leftXAxis, OI.driver.getRawAxis(OI.leftXAxis));
		 * driverOffsets.put(OI.leftYAxis, OI.driver.getRawAxis(OI.leftYAxis));
		 * driverOffsets.put(OI.rightXAxis,
		 * OI.driver.getRawAxis(OI.rightXAxis));
		 * OI.driver.setAxisOffsets(driverOffsets);
		 */

		generalInit();
		try
		{// wait for encoder reset
			Thread.sleep(200);
		}
		catch (InterruptedException e)
		{
		}
		driveTelop.init();
		// claw.init();
		shiftLatch = new Latch(OI.shiftFast)
		{

			@Override
			public void go()
			{
				drive.setPistons(true);
			}

			@Override
			public void stop()
			{
				drive.setPistons(false);
			}
		};
		raiseLatch = new Latch(OI.clawUp)
		{
			@Override
			public void go()
			{
				claw.setAngle(0);
			}

			@Override
			public void stop()
			{
			}
		};
		resetLatch = new Latch(OI.clawDrop)
		{
			@Override
			public void go()
			{
				claw.setAngle(-45);
			}

			@Override
			public void stop()
			{
				claw.disable();
			}
		};
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		shiftLatch.get();
		if (!raiseLatch.get())
		{
			if (!resetLatch.get())
			{
				claw.setSpeed(OI.operator.getAxis(OI.rightTrigger) - OI.operator.getAxis(OI.leftTrigger));
			}
		}
		driveTelop.cheese(OI.driver);

		elevator.setSpeed(-OI.operator.getAxis(OI.rightYAxis));
		plate.setSpeed(-OI.operator.getRawAxis(OI.leftYAxis));

		claw.intake(OI.intake.get(), OI.outtake.get());
		writeToDash();
	}

	public void writeToDash()
	{
		SmartDashboard.putNumber("ele", elevator.getPosition());
		SmartDashboard.putNumber("right", drive.getRightSpeed());
		SmartDashboard.putNumber("left", drive.getLeftSpeed());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
	}

	/**
	 * Called Whenever robot is initialized
	 */
	public void generalInit()
	{
		ahrs.reset();
		drive.resetEncoders();
		plate.resetEncoders();
		elevator.resetEncoders();
		claw.resetEncoders();
		driveTelop.setPID("r",p.getDouble("pk", .01), p.getDouble("ik", 0.000), p.getDouble("dk", .0020));
		driveTelop.setPID("l",p.getDouble("pk", .01), p.getDouble("ik", 0.000), p.getDouble("dk", .0002));
	}
}
