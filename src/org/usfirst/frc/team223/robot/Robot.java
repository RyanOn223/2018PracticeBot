package org.usfirst.frc.team223.robot;

import org.usfirst.frc.team223.robot.constants.Constants;
import org.usfirst.frc.team223.robot.constants.OI;
import org.usfirst.frc.team223.robot.constants.RobotMap;
import org.usfirst.frc.team223.robot.drive.*;
import org.usfirst.frc.team223.robot.elevator.Claw;
import org.usfirst.frc.team223.robot.elevator.Elevator;
import org.usfirst.frc.team223.robot.elevator.Plate;
import org.usfirst.frc.team223.robot.utils.Latch;
import org.usfirst.frc.team223.robot.utils.Panic;
import org.usfirst.frc.team223.vision.VisionServer;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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

	private Latch shiftLatch;
	private Latch elevatorLatch;
	private Latch raiseLatch;
	private Latch resetLatch;
	private Latch panicLatch;
	private Latch clampLatch;

	VisionServer visionServer;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		CameraServer.getInstance().addAxisCamera("10.2.23.63");
		// CameraServer.getInstance().startAutomaticCapture();

		p = Preferences.getInstance();
		c = new Compressor(RobotMap.pcmID);

		ahrs = new AHRS(SPI.Port.kMXP);
		drive = new DriveTrain();
		driveTelop = new DriveTelop(drive, ahrs);
		driveAuto = new DriveAuto(drive, ahrs);
		elevator = new Elevator();
		plate = new Plate();
		claw = new Claw();

		AutoRoutines.init(driveAuto, claw, plate, elevator, ahrs);
		/*
		 * visionServer = new VisionServer(50); visionServer.start();
		 */
	}

	/**
	 * This function is run once each time the robot enters autonomous mode. I
	 * apologize for this spaghetti
	 */
	@Override
	public void autonomousInit()
	{
		generalInit();
		/// *
		new Thread()
		{
			public void run()
			{
				try
				{
					// claw starts now and works based on time because encoder is nonexistant
					claw.setSpeed(-1);

					// wait for general init and fms
					Thread.sleep(1000);

					claw.setSpeed(0);

					// gets string from dashboard, puts it in uppercase
					// takes first letter
					char location = p.getString("position", "D").toUpperCase().toCharArray()[0];
					int routine = p.getInt("routine", 3);
					
					driveAuto.setFast(p.getBoolean("fast", false));
					// for testing only
					boolean forceFar = p.getBoolean("far", false);
					boolean switchPrior = p.getBoolean("switchPrio", true);
					boolean two = p.getBoolean("two", false);
					boolean ignoreSwitch = (routine & 1) == 1;
					boolean ignoreScale = (routine & 2) == 2;
					boolean wideScale=p.getBoolean("wide", false);

					String gameData = DriverStation.getInstance().getGameSpecificMessage();

					char lever = 'Q';
					char scale = 'Q';

					// put game into lever and scale location
					if (gameData != null)
					{
						try
						{
							lever = gameData.charAt(0);
							scale = gameData.charAt(1);
						}
						catch (IndexOutOfBoundsException e)
						{
							location = 'F';
						}
					}
					else
					{
						gameData = "no game data";
					}

					// makes sure lever and scale are both L or R
					if (!((lever == 'L' || lever == 'R') && (scale == 'L' || scale == 'R')))
					{
						location = 'F';
					}

					System.out.println(location + " " + gameData + " " + routine);

					switch (location)
					{

					case 'L':
					case 'R':
					{
						// if switch is on the left and so is robot after
						if (switchPrior)
						{
							if (!forceFar && !ignoreSwitch && lever == location)
							{
								AutoRoutines.leverNear(location == 'L');
							}
							else if (!forceFar && !ignoreScale && scale == location)
							{
								AutoRoutines.scaleNear(location == 'L', ('L' == location && 'L' == lever) && two,wideScale);
							}
							else if (!ignoreSwitch)
							{
								//AutoRoutines.crossLine(location);

								AutoRoutines.leverFar(location == 'L');
							}
							else if (!ignoreScale)
							{
								//AutoRoutines.crossLine(location);
								 AutoRoutines.scaleFar(location=='L');
							}
							else
							{
								AutoRoutines.crossLine(location);
							}
						}
						else
						{
							if (!forceFar && !ignoreScale && scale == location)
							{
								AutoRoutines.scaleNear(location == 'L', ('L' == location && 'L' == lever) && two,wideScale);
							}
							else if (!forceFar && !ignoreSwitch && lever == location)
							{
								AutoRoutines.leverNear(location == 'L');
							}
							else if (!ignoreScale)
							{
								//AutoRoutines.crossLine(location);
								AutoRoutines.scaleFar(location=='L');
							}
							else if (!ignoreSwitch)
							{
								//AutoRoutines.crossLine(location);

								AutoRoutines.leverFar(location == 'L');
							}
							else
							{
								AutoRoutines.crossLine(location);
							}
						}
						break;
					}
					case 'M':
					{
						AutoRoutines.middle(lever == 'L', two);
						break;
					}
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
				}
				catch (InterruptedException e1)
				{
				}
			}
		}.start();
		// */

		/*
		 * for testing new Thread() { public void run() { try { // wait for general init
		 * Thread.sleep(200); driveAuto.turn(180); //
		 * System.out.println(Constants.DRIVE_CNT_TO_IN*12*6); //
		 * elevator.setHeight(Constants.ELEVATOR_HEIGHT); //
		 * driveAuto.go(Constants.DRIVE_CNT_TO_IN*12*6, 5000);
		 * System.out.println("done"); } catch (InterruptedException e) { } } }.start();
		 * //
		 */
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
	 * This function is called once each time the robot enters tele-operated mode
	 */
	@Override
	public void teleopInit()
	{
		generalInit();
		/*
		 * try {// wait for encoder reset Thread.sleep(200); } catch
		 * (InterruptedException e) { }
		 * 
		 * // driveTelop.init(); // claw.init();
		 */

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
		elevatorLatch = new Latch(OI.elevatorLock)
		{
			@Override
			public void go()
			{
				elevator.setPistons(true);
			}

			@Override
			public void stop()
			{
				elevator.setPistons(false);
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
				claw.setAngle(-90);
			}

			@Override
			public void stop()
			{
				claw.stopControllers();
				;
			}
		};
		panicLatch = new Latch(OI.panic, OI.calm)
		{
			@Override
			public void go()
			{
				Panic.panic = true;
				claw.setPiston(true);
				elevator.setPistons(false);
			}

			@Override
			public void stop()
			{
				Panic.panic = false;
			}
		};
		clampLatch = new Latch(OI.clawClamp, OI.clawUnclamp)
		{
			@Override
			public void go()
			{
				claw.setPiston(true);
			}

			@Override
			public void stop()
			{
				claw.setPiston(false);
			}
		};
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		panicLatch.get();
		// shiftLatch.get();
		// only use manual if not in panic mode
		if (Panic.panic) clampLatch.get();
		elevatorLatch.get();
		claw.setSpeed(OI.operator.getAxis(OI.rightTrigger) - OI.operator.getAxis(OI.leftTrigger));

		// System.out.println(ahrs.getYaw()+" "+ahrs.getPitch()+" "+ahrs.getRoll());
		driveTelop.cheese(OI.driver);

		elevator.setSpeed(-OI.operator.getAxis(OI.rightYAxis));
		plate.setSpeed(-OI.operator.getAxis(OI.leftYAxis));

		claw.intake(OI.intake.get(), OI.outtake.get());
		writeToDash();
	}

	public void writeToDash()
	{
		/*
		 * SmartDashboard.putNumber("ele A.", elevator.getCurrent());
		 * SmartDashboard.putNumber("claw A.", claw.getCurrent());
		 * SmartDashboard.putNumber("plate A.", plate.getCurrent());
		 */
		SmartDashboard.putNumber("right A.", drive.getRightPosition());
		SmartDashboard.putNumber("left A.", drive.getLeftPosition());

		SmartDashboard.putNumber("ele V.", elevator.getPosition());
		/*
		 * SmartDashboard.putNumber("claw V.", claw.getVoltage());
		 * SmartDashboard.putNumber("plate V.", plate.getVoltage());
		 * SmartDashboard.putNumber("right V.", drive.getRightVoltage());
		 * SmartDashboard.putNumber("left V.", drive.getLeftVoltage());
		 */

		SmartDashboard.putNumber("ele", elevator.getPosition());
		SmartDashboard.putBoolean("Panic", !Panic.panic);
	}

	/**
	 * Called Whenever robot is initialized
	 */
	public void generalInit()
	{
		Panic.panic = false;
		ahrs.reset();
		drive.resetEncoders();
		plate.resetEncoders();
		plate.stopControllers();
		elevator.stopControllers();
		elevator.resetEncoders();
		elevator.setPistons(false);
		claw.resetEncoders();
		claw.stopControllers();

		// driveAuto.setPID("r", p.getDouble("p", .01), p.getDouble("i", 0.000),
		// p.getDouble("d", .0002));
		// elevator.setPID(p.getDouble("p", .001), p.getDouble("i", 0.000),
		// p.getDouble("d", .003));
		driveAuto.setPID("rotate", p.getDouble("p", .001), p.getDouble("i", 0.000), p.getDouble("d", .003));

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
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
		claw.stopControllers();
	}
}
