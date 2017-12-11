package org.usfirst.frc.team223.robot;

import org.usfirst.frc.team223.robot.drive.*;
import org.usfirst.frc.team223.vision.VisionServer;
import org.usfirst.frc.team223.robot.Utils.Latch;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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

	AHRS ahrs;

	DriveBase drive;
	DriveTelop driveTelop;
	DriveAuto driveAuto;

	Compressor c;
	CANTalon climb;

	Latch shootLatch;
	Latch shiftLatch;
	Latch pidLatch;
	boolean fast = false;
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
		c.setClosedLoopControl(true);

		ahrs = new AHRS(SPI.Port.kMXP);
		drive = new DriveBase();
		driveTelop = new DriveTelop(drive);
		driveAuto = new DriveAuto(drive, ahrs);
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
		new Thread()
		{
			@Override
			public void run()
			{
				driveAuto.start(90);
			}
		}.start();
		generalInit();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
		writeToDash();
	}

	/**
	 * Called When Dissabled (no shit)
	 */
	@Override
	public void disabledInit()
	{
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit()
	{
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
		pidLatch = new Latch(OI.startAngle, OI.stopAngle)
		{

			@Override
			public void go()
			{
				// if (!driveControl.isEnabled())
				// driveControl.startPID(ahrs.getAngle());
			}

			@Override
			public void stop()
			{
				// driveControl.stopPID();
			}

		};
		generalInit();
	}

	/**
	 * This function is called periodically during operator control
	 */

	@Override
	public void teleopPeriodic()
	{
		shiftLatch.get();
		driveTelop.cheese(OI.driver);
		writeToDash();
	}

	public void writeToDash()
	{
		SmartDashboard.putNumber("RPM", ahrs.getAngle());
		SmartDashboard.putNumber("angle", ahrs.getAngle());
		SmartDashboard.putNumber("pidget", driveAuto.getPID());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
		LiveWindow.run();
	}

	/**
	 * Called Whenever robot is initialized
	 */
	public void generalInit()
	{
		driveAuto.stop();
		driveAuto.setPID(p.getDouble("pk", .05), p.getDouble("ik", .1), p.getDouble("dk", .0));

	}
}
