package org.usfirst.frc.team223.robot;

import org.usfirst.frc.team223.vision.VisionServer;
import org.usfirst.frc.team223.robot.Utils.Latch;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
	boolean mec = false;
	Drive drive;
	Shooter shooter;
	Compressor c;

	CANTalon climb;
	Solenoid gearPiston;
	Solenoid jaws;

	Latch shootLatch;
	Latch shiftLatch;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		p = Preferences.getInstance();

		drive = new Drive(RobotMap.driveFL, RobotMap.driveFR, RobotMap.driveBL, RobotMap.driveBR);
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
		generalInit();
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
		shiftLatch = new Latch(OI.shootOn, OI.shootOff) {

			@Override
			public void go()
			{
				drive.solenoidB.set(true);
				drive.solenoidF.set(true);
				mec = true;
			}

			@Override
			public void stop()
			{
				drive.solenoidF.set(false);
				drive.solenoidB.set(false);
				mec = false;
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

		if(mec)
			drive.mec(OI.driver);
		else
			drive.cheese(OI.driver);

		shooter.intake(OI.intake.get());
		shooter.blend(OI.blend.get());

		climb.set(OI.operator.getRawAxis(OI.climb));
		gearPiston.set(OI.gearPiston.get());
		jaws.set(OI.jaws.get());
		writeToDash();
	}

	public void writeToDash()
	{
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
