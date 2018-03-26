package org.usfirst.frc.team223.robot;

import org.usfirst.frc.team223.robot.constants.Constants;
import org.usfirst.frc.team223.robot.drive.DriveAuto;
import org.usfirst.frc.team223.robot.elevator.Claw;
import org.usfirst.frc.team223.robot.elevator.Elevator;
import org.usfirst.frc.team223.robot.elevator.Plate;

import com.kauailabs.navx.frc.AHRS;

public class AutoRoutines
{
	static DriveAuto driveAuto;
	static Claw claw;
	static Plate plate;
	static Elevator elevator;
	static AHRS ahrs;

	public static void init(DriveAuto d, Claw c, Plate p, Elevator e, AHRS a)
	{
		driveAuto = d;
		claw = c;
		plate = p;
		elevator = e;
		ahrs = a;
	}

	private static void crossLine(char pos, boolean goLeft, boolean far) throws InterruptedException
	{
		System.out.println("crossing line");

		double finishDistance = far ? Constants.TO_MIDDLE : Constants.TO_SWITCH;

		switch (pos)
		{
		case 'L':
		case 'R':
		{
			if (pos == 'L' == goLeft)
			{
				driveAuto.go(finishDistance, 3000);
			}
			else
			{
				driveAuto.go(Constants.START_CREEP, 1000);
				driveAuto.turn(goLeft ? -90 : 90);
				driveAuto.go(Constants.FAR_ACROSS, 4000);
				driveAuto.turn(0);
				driveAuto.go(finishDistance - Constants.START_CREEP, 2000);
			}
			break;
		}
		case 'M':
		{
			driveAuto.go(Constants.START_CREEP, 1000);

			driveAuto.turn(goLeft ? -90 : 90);
			driveAuto.go(Constants.MIDDLE_ACROSS, 2000);
			driveAuto.turn(0);
			driveAuto.go(finishDistance - Constants.START_CREEP, 2000);
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

		crossLine(pos, left, true);
		driveAuto.turn(left ? 90 : -90);

		plate.setHeight(Constants.SWITCH_HEIGHT);

		driveAuto.go(Constants.FLEVER_DISTANCE, 4000);

		ahrs.reset();// this is because the controller doesn't work well with 180 degrees
		driveAuto.turn(left ? 90 : -90);
		driveAuto.go(Constants.LEVER_CREEP, 2000);
		claw.out();
	}

	public static void leverNear(char pos, boolean invert) throws InterruptedException
	{
		System.out.println("near switch");

		crossLine(pos, invert, false);

		plate.setHeight(Constants.SWITCH_HEIGHT);
		driveAuto.turn(invert ? 90 : -90);

		driveAuto.go(Constants.NLEVER_DISTANCE, 2000);
		claw.out();
	}

	public static void scaleNear(char pos, boolean left, boolean twoCube) throws InterruptedException
	{
		System.out.println("scale near");

		crossLine(pos, left, false);
		plate.setHeight(Constants.SCALE_HEIGHT);

		// elevator.setHeight(Constants.ELEVATOR_HEIGHT); broken again
		System.out.println(elevator.getPistons());
		elevator.setSpeed(1);
		Thread.sleep(1000);
		elevator.setSpeed(.15);// this is so itdoesn't fall down
		driveAuto.turn(left ? Constants.NLSCALE_ROTATE : -Constants.NLSCALE_ROTATE);
		driveAuto.go(Constants.NSCALE_DISTANCE, 2000);

		claw.out();
		Thread.sleep(1000);
		elevator.setSpeed(-1);

		// backup and put plate down
		driveAuto.go(-24 * Constants.DRIVE_CNT_TO_IN, 1000);
		elevator.setSpeed(0);
		plate.stopControllers();
		plate.setSpeed(-1);
		plate.checkBottom();
		if (twoCube) twoCube(left);
		Thread.sleep(2500);

	}

	public static void twoCube(boolean left) throws InterruptedException
	{
		driveAuto.turn(left ? Constants.TURN_AROUND : -Constants.TURN_AROUND);
		claw.setPiston(true);
		claw.in();
		driveAuto.go(Constants.DRIVE_BACK, 2000);
		claw.setPiston(false);
		claw.setSpeed(0);
		Thread.sleep(500);
		plate.setHeight(Constants.SCALE_HEIGHT);

		driveAuto.go(Constants.LEVER_CREEP, 100);
		claw.out();
	}

	public static void scaleFar(char pos, boolean left) throws InterruptedException
	{
		System.out.println("Scale far");

		crossLine(pos, left, true);
		plate.setHeight(Constants.SCALE_HEIGHT);

		driveAuto.go(Constants.FSCALE_DISTANCE, 4000);
		driveAuto.turn(left ? Constants.FSCALE_TURN : -Constants.FSCALE_TURN);
		elevator.setHeight(Constants.ELEVATOR_HEIGHT);
		Thread.sleep(1000);
		driveAuto.go(Constants.SCALE_CREEP, 2000);
		claw.out();
	}

	public static void crossLineThread(char location, boolean left) throws InterruptedException
	{
		System.out.println("Only cross line");
		crossLine(location, left, false);
	}
}
