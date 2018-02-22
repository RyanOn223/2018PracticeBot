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

	private static void crossLine(char pos, boolean goLeft,boolean far) throws InterruptedException
	{
		System.out.println("crossing line");
		
		double finishDistance=far?Constants.TO_MIDDLE:Constants.TO_SWITCH;
		
		switch (pos)
		{
		case 'L':
		case 'R':
		{
			if (pos == 'L' == goLeft)
			{
				driveAuto.go(finishDistance, 4000);
			}
			else
			{
				driveAuto.go(Constants.START_CREEP, 4000);
				driveAuto.turn(goLeft ? -90 : 90);
				driveAuto.go(Constants.FAR_ACROSS, 4000);
				driveAuto.turn(0);
				driveAuto.go(finishDistance - Constants.START_CREEP, 4000);
			}
			break;
		}
		case 'M':
		{
			driveAuto.go(Constants.START_CREEP, 4000);

			driveAuto.turn(goLeft ? -90 : 90);
			driveAuto.go(Constants.MIDDLE_ACROSS, 4000);
			driveAuto.turn(0);
			driveAuto.go(finishDistance - Constants.START_CREEP, 4000);
			break;
		}
		}
	}

	public static void error() throws InterruptedException
	{
		System.out.println("error");

		driveAuto.go(Constants.ERROR_DISTANCE, 2000);
	}

	public static void leverFar(char pos, boolean left) throws InterruptedException
	{
		System.out.println("far Switch");

		crossLine(pos, left,true);
		driveAuto.turn(left ? 90 : -90);

		plate.setHeight(Constants.SCALE_HEIGHT);

		driveAuto.go(Constants.FLEVER_DISTANCE, 2000);
		driveAuto.turn(180);
		driveAuto.go(Constants.LEVER_CREEP, 2000);
		claw.out();
	}

	public static void leverNear(char pos, boolean invert) throws InterruptedException
	{
		System.out.println("near switch");
		
		crossLine(pos, invert,false);
		
		plate.setHeight(Constants.SCALE_HEIGHT);
		driveAuto.turn(invert ? 90 : -90);

		driveAuto.go(Constants.NLEVER_DISTANCE, 2000);
		claw.out();
	}

	public static void scaleNear(char pos, boolean invert) throws InterruptedException
	{
		System.out.println("scale near");

		crossLine(pos, invert,false);
		plate.setHeight(Constants.SCALE_HEIGHT);
		elevator.setHeight(Constants.ELEVATOR_HEIGHT);
		Thread.sleep(1000);
		
		driveAuto.turn(invert ? Constants.NLSCALE_ROTATE : -Constants.NLSCALE_ROTATE);
		driveAuto.go(Constants.NSCALE_DISTANCE, 4000);
		
		claw.out();
	}

	public static void scaleFar(char pos, boolean left) throws InterruptedException
	{
		System.out.println("Scale far");

		crossLine(pos, left,true);
		plate.setHeight(Constants.SCALE_HEIGHT);

		driveAuto.go(Constants.FSCALE_DISTANCE, 2000);
		driveAuto.turn(left?-70:70);
		elevator.setHeight(Constants.ELEVATOR_HEIGHT);
		Thread.sleep(1000);
		driveAuto.go(Constants.SCALE_CREEP, 2000);
		claw.out();
	}

	public static void crossLineThread(char location, boolean invert) throws InterruptedException
	{
		crossLine(location, invert,true);
	}
}
