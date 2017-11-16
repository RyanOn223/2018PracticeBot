package org.usfirst.frc.team223.robot;

import org.usfirst.frc.team223.robot.drive.DriveControl;
import org.usfirst.frc.team223.robot.drive.DriveTelop;
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

	AHRS ahrs;
	boolean mec = false;
	DriveTelop driveTelop;
	DriveControl driveControl;
	Shooter shooter;
	Compressor c;
	CANTalon climb;
	Solenoid gearPiston;
	Solenoid jaws;

	Latch shootLatch;
	Latch shiftLatch;
	Latch pidLatch;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		p = Preferences.getInstance();

		ahrs = new AHRS(SPI.Port.kMXP);
		c = new Compressor(52);
		c.setClosedLoopControl(true);
		
		climb = new CANTalon(RobotMap.climb);
		gearPiston = new Solenoid(RobotMap.pcmID, RobotMap.gearPiston);
		jaws = new Solenoid(RobotMap.pcmID, RobotMap.jaws);
		shooter = new Shooter(RobotMap.shooter0, RobotMap.shooter1, RobotMap.shooter2, RobotMap.blender, RobotMap.intake);
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
	 * This function is called once each time the robot enters tele-operated mode
	 */
	@Override
	public void teleopInit()
	{
		driveTelop = new DriveTelop();
		driveControl = new DriveControl(ahrs, driveTelop);

		shootLatch = new Latch(OI.shootOn, OI.shootOff) {

			@Override
			public void go()
			{
				shooter.setSpeed(-500);
			}

			@Override
			public void stop()
			{
				shooter.stopPID();
			}

		};
		shiftLatch = new Latch(OI.shiftMec, OI.shiftCheese) {

			@Override
			public void go()
			{
				driveTelop.solenoidB.set(true);
				driveTelop.solenoidF.set(true);
				mec = true;
			}

			@Override
			public void stop()
			{
				driveTelop.solenoidF.set(false);
				driveTelop.solenoidB.set(false);
				mec = false;
			}

		};
		pidLatch = new Latch(OI.startAngle, OI.stopAngle) {

			@Override
			public void go()
			{
				if (!driveControl.isEnabled())
					driveControl.startPID(90);
			}

			@Override
			public void stop()
			{
				shooter.stopPID();
				driveControl.stopPID();
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
		shootLatch.get();
		shiftLatch.get();
		if (!pidLatch.get())
			if (mec)
				driveTelop.mec(OI.driver);
			else
				driveTelop.cheese(OI.driver);
		

		shooter.intake(OI.intake.get());
		shooter.blend(OI.blend.get());

		climb.set(OI.operator.getRawAxis(OI.climb));
		gearPiston.set(OI.gearPiston.get());
		jaws.set(OI.jaws.get());
		writeToDash();
	}

	public void writeToDash()
	{
		SmartDashboard.putNumber("angle", ahrs.getAngle());
		SmartDashboard.putNumber("RPM", -shooter.talon2.getSpeed());
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
		shooter.getController().reset();
		shooter.stopPID();
		shooter.getController().setPID(p.getDouble("pk", .0001), p.getDouble("ik", .0000001), p.getDouble("dk", .0007));
		System.out.println(shooter.getController().getP() + " " + shooter.getController().getI() + " "
				+ shooter.getController().getD());
	}
}
