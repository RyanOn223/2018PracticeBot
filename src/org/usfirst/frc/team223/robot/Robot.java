package org.usfirst.frc.team223.robot;

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
	boolean mec=false;
	Drive drive;
	Shooter shooter;
	Compressor c;
	
	CANTalon climb;
	Solenoid gearPiston;
	Solenoid jaws;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		p=Preferences.getInstance();
		
		drive=new Drive(RobotMap.driveFL,RobotMap.driveFR,RobotMap.driveBL,RobotMap.driveBR);
		c= new Compressor(52);
		c.setClosedLoopControl(true);
		climb=new CANTalon(RobotMap.climb);
		gearPiston=new Solenoid(RobotMap.pcmID,RobotMap.gearPiston);
		jaws=new Solenoid(RobotMap.pcmID,RobotMap.jaws);
		shooter=new Shooter(RobotMap.shooter0,RobotMap.shooter1,RobotMap.shooter2,RobotMap.blender,RobotMap.intake);
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit(){generalInit();}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic(){}
	/**
	 * Called When Dissabled (no shit)
	 */
	@Override public void disabledInit(){}
	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit()
	{
		shooter.setSpeed(p.getInt("shootSpeed", 0));
	//	generalInit();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		shootLatch(OI.shootOn,OI.shootOff);
		shiftLatch(OI.shiftMec,OI.shiftCheese);
		
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
		SmartDashboard.putNumber("RPM",shooter.talon2.getSpeed());
		SmartDashboard.putNumber("Voltage 1", shooter.talon1.getOutputVoltage());
		SmartDashboard.putNumber("Voltage 2", shooter.talon2.getOutputVoltage());
	}
	
	
	
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
		LiveWindow.run();
	}
	
	
	private boolean b1prev = false;
	private boolean b2prev = false;
	
	public void shiftLatch(JoystickButton b1, JoystickButton b2)
	{
		boolean b1curr = b1.get();
		boolean b2curr = b2.get();
		
		if(!b1prev && b1curr) // button 1 rising
		{
			drive.solenoidB.set(true);
			drive.solenoidF.set(true);
			mec=true;
		}
		
		else if(!b2prev && b2curr) // button 2 rising
		{
			drive.solenoidF.set(false);
			drive.solenoidB.set(false);
			mec=false;
		}
		b1prev = b1curr;
		b2prev = b2curr;
	}
	private boolean bs1prev = false;
	private boolean bs2prev = false;
	public void shootLatch(JoystickButton b1, JoystickButton b2)
	{
		boolean b1curr = b1.get();
		boolean b2curr = b2.get();
		
		if(!bs1prev && b1curr) // button 1 rising
		{
			shooter.set(-10);
		}
		
		else if(!bs2prev && b2curr) // button 2 rising
		{
			shooter.set(0);
		}
		bs1prev = b1curr;
		bs2prev = b2curr;
	}
	
	/**
	 * Called Whenever robot is initialized
	 */
	public void generalInit()
	{
	}
}









