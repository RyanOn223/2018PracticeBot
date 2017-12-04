package org.usfirst.frc.team223.robot;

import org.usfirst.frc.team223.robot.drive.DriveControl;
import org.usfirst.frc.team223.robot.drive.DriveTelop;
import org.usfirst.frc.team223.vision.VisionServer;
import org.usfirst.frc.team223.robot.Utils.Latch;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
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

	DriveTelop driveTelop;

	AHRS ahrs;
	
	DriveControl driveControl;

	Shooter shooter;
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

		//drive = new Drive(RobotMap.driveR0, RobotMap.driveR1, RobotMap.driveL0, RobotMap.driveL1);
		c = new Compressor(RobotMap.pcmID);
		c.setClosedLoopControl(true);

		/*
		 * climb = new CANTalon(RobotMap.climb); gearPiston = new
		 * Solenoid(RobotMap.pcmID, RobotMap.gearPiston); jaws = new
		 * Solenoid(RobotMap.pcmID, RobotMap.jaws); shooter = new
		 * Shooter(RobotMap.shooter0, RobotMap.shooter1, RobotMap.shooter2,
		 * RobotMap.blender, RobotMap.intake);
		 */
		//ahrs = new AHRS(SPI.Port.kMXP);
		driveTelop = new DriveTelop();
		
//		gearPiston = new Solenoid(RobotMap.pcmID, RobotMap.gearPiston);
//		jaws = new Solenoid(RobotMap.pcmID, RobotMap.jaws);

		/*visionServer = new VisionServer(50);
		visionServer.start();*/
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit()
	{
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
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
		shiftLatch = new Latch(OI.shiftFast, OI.shiftSlow)
		{

			@Override
			public void go()
			{
				System.out.println("tyfyfyrfyfytftyf");
				driveTelop.setPistons(true);
			}

			@Override
			public void stop()
			{
				System.out.println("ah");
				driveTelop.setPistons(false);
			}

		};
		pidLatch = new Latch(OI.startAngle, OI.stopAngle)
		{

			@Override
			public void go()
			{
				if (!driveControl.isEnabled()) driveControl.startPID(ahrs.getAngle());
			}

			@Override
			public void stop()
			{
				driveControl.stopPID();
			}

		};
		generalInit();
	}

	/**
	 * This function is called periodically during operator control
	 */
	int i = 0;

	@Override
	public void teleopPeriodic()
	{
		/* shootLatch.get(); */

		if (OI.shiftFast.get())
		{
			if (!fast)
			{
				driveTelop.setPistons(true);
				fast = true;
			}
		}
		else if (fast)
		{
			driveTelop.setPistons(false);
			fast = false;
		}

		driveTelop.cheese(OI.driver);

		/*
		 * shooter.intake(OI.intake.get()); shooter.blend(OI.blend.get());
		 * 
		 * climb.set(OI.operator.getRawAxis(OI.climb));
		 * gearPiston.set(OI.gearPiston.get()); jaws.set(OI.jaws.get());
		 * writeToDash();
		 */

		writeToDash();
	}

	public void writeToDash()
	{
	//	SmartDashboard.putNumber("angle", ahrs.getAngle());
		//SmartDashboard.putNumber("RPM", -shooter.talon2.getSpeed());
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
		/*driveControl.stopPID();
		driveControl.setPID(p.getDouble("pk", .05), p.getDouble("ik", .1), p.getDouble("dk", .0));*/
	}
}
