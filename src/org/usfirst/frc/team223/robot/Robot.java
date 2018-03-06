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

		AutoRoutines.init(driveAuto, claw, plate, elevator);
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

		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init and fms
					Thread.sleep(1000);

					/// *gets string from dashboard, puts it in uppercase, takes
					/// first
					/// letter
					char location = p.getString("position", "D").toUpperCase().toCharArray()[0];
					int routine = p.getInt("routine", 3);
					boolean forceFar=p.getBoolean("far", false);
					// invert true means go left false means go right
					boolean left = p.getBoolean("left", false);

					boolean ignoreSwitch = (routine & 1) == 1;
					boolean ignoreScale = (routine & 2) == 2;

					String gameData = DriverStation.getInstance().getGameSpecificMessage();

					char lever = 'Q';
					char scale = 'Q';

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
						gameData="shit";
					}
					// makes sure lever and scale are both L or R
					if (!((lever == 'L' || lever == 'R') && (scale == 'L' || scale == 'R')))
					{
						location = 'F';
					}
					System.out.println(location+" "+gameData+" "+routine);
					switch (location)
					{

					case 'L':
					case 'R':
					case 'M':
					{
						// if switch is on the left and so is robot after
						if (!forceFar)
						{
							if (!ignoreSwitch && 'L' == lever == left)
							{
								AutoRoutines.leverNear(location, left);
							}
							else if (!ignoreScale && 'L' == scale == left)
							{
								AutoRoutines.scaleNear(location, left);
							}
						}
						else if (!ignoreSwitch)
						{
							//AutoRoutines.crossLineThread(location, left);

							AutoRoutines.leverFar(location, left);
						}
						else if (!ignoreScale)
						{
							//AutoRoutines.crossLineThread(location, left);
							AutoRoutines.scaleFar(location, left);
						}
						else
						{
							AutoRoutines.crossLineThread(location, left);
						}
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
		 * new Thread() { public void run() { try { // wait for general init
		 * Thread.sleep(200); driveAuto.turn(90); } catch (InterruptedException e) { } }
		 * }.start();//
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
	 * This function is called once each time the robot enters tele-operated mode
	 */
	@Override
	public void teleopInit()
	{
		/*
		 * This doesn't work because the offsets constantly change Map<Integer, Double>
		 * driverOffsets = new HashMap<>(); driverOffsets.put(OI.leftXAxis,
		 * OI.driver.getRawAxis(OI.leftXAxis)); driverOffsets.put(OI.leftYAxis,
		 * OI.driver.getRawAxis(OI.leftYAxis)); driverOffsets.put(OI.rightXAxis,
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
				claw.disable();
			}
		};
		panicLatch = new Latch(OI.panic, OI.calm)
		{
			@Override
			public void go()
			{
				Panic.panic = true;
				claw.setPiston(false);
				elevator.setPistons(false);
			}

			@Override
			public void stop()
			{
				Panic.panic = false;
			}
		};
		clampLatch = new Latch(OI.clawClamp)
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

		shiftLatch.get();
		
		if (Panic.panic)
		{
			clampLatch.get();
			
		}
		elevatorLatch.get();
		
		
		if (!raiseLatch.get())
		{
			if (!resetLatch.get())
			{
				claw.setSpeed(OI.operator.getAxis(OI.rightTrigger) - OI.operator.getAxis(OI.leftTrigger));
			}
		}

		driveTelop.cheese(OI.driver);

		elevator.setSpeed(-OI.operator.getAxis(OI.rightYAxis));
		plate.setSpeed(-OI.operator.getAxis(OI.leftYAxis));

		claw.intake(OI.intake.get(), OI.outtake.get());
		writeToDash();
	}

	public void writeToDash()
	{
		/*SmartDashboard.putNumber("ele A.", elevator.getCurrent());
		SmartDashboard.putNumber("claw A.", claw.getCurrent());
		SmartDashboard.putNumber("plate A.", plate.getCurrent());
		SmartDashboard.putNumber("right A.", drive.getRightCurrent());
		SmartDashboard.putNumber("left A.", drive.getLeftCurrent());
		/*
		SmartDashboard.putNumber("ele V.", elevator.getVoltage());
		SmartDashboard.putNumber("claw V.", claw.getVoltage());
		SmartDashboard.putNumber("plate V.", plate.getVoltage());
		SmartDashboard.putNumber("right V.", drive.getRightVoltage());
		SmartDashboard.putNumber("left V.", drive.getLeftVoltage());*/
		
		
		SmartDashboard.putNumber("angle", ahrs.getAngle());
		SmartDashboard.putBoolean("Panic",! Panic.panic);
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
		plate.stopControllers();
		elevator.resetEncoders();
		elevator.setPistons(false);
		claw.resetEncoders();
		claw.disable();
		driveTelop.setPID("r", p.getDouble("p", .01), p.getDouble("i", 0.000), p.getDouble("d", .0002));
		driveTelop.setPID("l", p.getDouble("p", .01), p.getDouble("i", 0.000), p.getDouble("d", .0002));
	}
}
