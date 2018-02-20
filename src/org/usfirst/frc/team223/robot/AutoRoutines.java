package org.usfirst.frc.team223.robot;

import org.usfirst.frc.team223.robot.constants.Constants;
import org.usfirst.frc.team223.robot.drive.DriveAuto;
import org.usfirst.frc.team223.robot.elevator.Claw;
import org.usfirst.frc.team223.robot.elevator.Elevator;
import org.usfirst.frc.team223.robot.elevator.Plate;

public class AutoRoutines
{
	static DriveAuto driveAuto;
	static Claw claw;
	static Plate plate;
	static Elevator elevator;

	public static void init(DriveAuto d, Claw c, Plate p, Elevator e)
	{
		driveAuto = d;
		claw = c;
		plate = p;
		elevator = e;
	}

	// Done Don't touch
	private static void crossLine(char pos, boolean goLeft) throws InterruptedException
	{
		System.out.println("crossing line");
		switch (pos)
		{
		case 'L':
		case 'R':
		{
			if (pos == 'L' == goLeft)
			{
				driveAuto.go(Constants.TO_MIDDLE, 4000);
				driveAuto.turn(goLeft ? 90 : -90);
			}
			else
			{
				driveAuto.go(Constants.START_CREEP, 2000);
				driveAuto.turn(goLeft ? -90 : 90);
				driveAuto.go(Constants.FAR_ACROSS, 2000);
				driveAuto.turn(0);
				driveAuto.go(Constants.TO_MIDDLE - Constants.START_CREEP, 2000);
				driveAuto.turn(goLeft ? 90 : -90);
			}
			break;
		}
		case 'M':
		{
			driveAuto.go(Constants.START_CREEP, 2000);

			driveAuto.turn(goLeft ? -90 : 90);
			driveAuto.go(Constants.MIDDLE_ACROSS, 2000);
			driveAuto.turn(0);
			driveAuto.go(Constants.TO_MIDDLE - Constants.START_CREEP, 2000);
			driveAuto.turn(goLeft ? 90 : -90);
			break;
		}
		}
	}

	public static void error()
	{
		System.out.println("error");
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					driveAuto.go(Constants.ERROR_DISTANCE, 2000);
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}

	public static void leverFar(char pos, boolean invert)
	{
		System.out.println("far Switch");
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					crossLine(pos, invert);
					plate.setHeight(Constants.SCALE_HEIGHT);

					driveAuto.go(Constants.FLEVER_DISTANCE, 2000);
					driveAuto.turn(180);
					driveAuto.go(Constants.LEVER_CREEP, 2000);
					claw.out();
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}

	public static void leverNear(char pos, boolean invert)
	{
		System.out.println("near switch");
		new Thread()
		{
			public void run()
			{
				try
				{
					Thread.sleep(200);
					crossLine(pos, invert);

					plate.setHeight(Constants.SCALE_HEIGHT);

					driveAuto.go(Constants.NLEVER_DISTANCE, 2000);
					driveAuto.turn(180);
					driveAuto.go(Constants.LEVER_CREEP, 2000);

					claw.out();
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}

	public static void scaleNear(char pos, boolean invert)
	{
		System.out.println("scale near");
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					crossLine(pos, invert);

					plate.setHeight(Constants.SCALE_HEIGHT);

					driveAuto.go(Constants.NSCALE_DISTANCE, 2000);
					driveAuto.turn(180);
					driveAuto.go(Constants.SCALE_CREEP, 2000);
					claw.out();
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}

	public static void scaleFar(char pos, boolean invert)
	{
		System.out.println("Scale far");
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					crossLine(pos, invert);
					plate.setHeight(Constants.SCALE_HEIGHT);

					driveAuto.go(Constants.FSCALE_DISTANCE, 2000);
					driveAuto.turn(180);
					driveAuto.go(Constants.SCALE_CREEP, 2000);
					claw.out();
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}

	public static void crossLineThread(char location, boolean invert)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					crossLine(location, invert);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
}
