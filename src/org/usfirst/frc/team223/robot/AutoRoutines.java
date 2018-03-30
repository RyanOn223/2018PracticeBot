package org.usfirst.frc.team223.robot;

import org.usfirst.frc.team223.robot.constants.Constants;
import org.usfirst.frc.team223.robot.drive.DriveAuto;
import org.usfirst.frc.team223.robot.elevator.Claw;
import org.usfirst.frc.team223.robot.elevator.Elevator;
import org.usfirst.frc.team223.robot.elevator.Plate;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;

public class AutoRoutines
{
	static DriveAuto driveAuto;
	static Claw claw;
	static Plate plate;
	static Elevator elevator;
	static AHRS ahrs;

	static DigitalInput beam = new DigitalInput(4);

	public static void init(DriveAuto d, Claw c, Plate p, Elevator e, AHRS a)
	{
		driveAuto = d;
		claw = c;
		plate = p;
		elevator = e;
		ahrs = a;
	}

	public static void crossLine(char pos) throws InterruptedException
	{
		System.out.println("only crossing line");

		switch (pos)
		{
		case 'L':
		case 'R':
		{
			driveAuto.go(Constants.TO_SWITCH, 3000);
			break;
		}
		default:
		{
			error();

			break;
		}
		}
	}

	public static void error() throws InterruptedException
	{
		System.out.println("error");
		driveAuto.go(Constants.ERROR_DISTANCE, 2000);
	}

	public static void leverNear(boolean left) throws InterruptedException
	{
		System.out.println("near switch");

		driveAuto.go(Constants.TO_SWITCH, 3000);

		plate.setHeight(Constants.SWITCH_HEIGHT);
		driveAuto.turn(left ? 90 : -90);

		driveAuto.go(Constants.NLEVER_DISTANCE, 2000);
		claw.out();
	}

	public static void scaleNear(boolean left, boolean twoCube,boolean wide) throws InterruptedException
	{
		System.out.println("scale near");
		if(wide)
			scaleWide(left,twoCube);
		else
			nearAngledScale(left,twoCube);


	}
	private static void nearAngledScale(boolean left, boolean twoCube)throws InterruptedException
	{
		driveAuto.go(Constants.TO_SWITCH, 3000);
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
		

		// backup and put plate down
		driveAuto.go(-24 * Constants.DRIVE_CNT_TO_IN, 1000);
		elevator.setSpeed(0);
		plate.stopControllers();
		plate.setSpeed(-1);
		plate.checkBottom();

		// if (twoCube) twoCube(left);
		Thread.sleep(2500);
	}
	private static void scaleWide(boolean left, boolean twoCube) throws InterruptedException
	{
		plate.setHeight(Constants.SCALE_HEIGHT);
		driveAuto.go(Constants.NWIDE_SCALE_DISTANCE, 3000);
		

		// elevator.setHeight(Constants.ELEVATOR_HEIGHT); broken again
		
		elevator.setSpeed(1);
		Thread.sleep(1000);
		elevator.setSpeed(.15);// this is so itdoesn't fall down
		driveAuto.turn(left ?90:-90);
		driveAuto.go(Constants.NSCALE_DISTANCE, 2000);

		claw.out();
		Thread.sleep(1000);
		
		// backup

		driveAuto.go(-24 * Constants.DRIVE_CNT_TO_IN, 1000);
		elevator.setSpeed(0);
		plate.stopControllers();
		plate.setSpeed(-1);
		plate.checkBottom();

		// if (twoCube) twoCube(left);
		Thread.sleep(2500);

	}
	
	public static void leverFar(boolean left) throws InterruptedException
	{
		System.out.println("far Switch");

		driveAuto.go(Constants.TO_MIDDLE, 3000);
		driveAuto.turn(left ? 90 : -90);

		plate.setHeight(Constants.SWITCH_HEIGHT);

		driveAuto.go(Constants.FLEVER_DISTANCE, 4000);

		driveAuto.turn(180);
		driveAuto.go(Constants.LEVER_CREEP, 2000);
		claw.out();
	}

	public static void scaleFar(boolean left) throws InterruptedException
	{
		System.out.println("Scale far");

		driveAuto.go(Constants.TO_MIDDLE, 3000);
		plate.setHeight(Constants.SCALE_HEIGHT);

		driveAuto.go(Constants.FSCALE_DISTANCE, 4000);
		driveAuto.turn(left ? Constants.FSCALE_TURN : -Constants.FSCALE_TURN);
		elevator.setHeight(Constants.ELEVATOR_HEIGHT);
		Thread.sleep(1000);
		driveAuto.go(Constants.SCALE_CREEP, 2000);
		claw.out();
	}

	public static void middle(boolean left, boolean two) throws InterruptedException
	{
		driveAuto.go(Constants.MIDDLE_START_DISTANCE, 1500);
		driveAuto.turn(left ? -90 : 90);
		driveAuto.go(left ? Constants.LEFT_MIDDLE_DISTANCE : Constants.RIGHT_MIDDLE_DISTANCE, 1500);
		driveAuto.turn(0);
		driveAuto.go(Constants.MIDDLE_END_DISTANCE, 1500);
		claw.out();
		if (two) twoSwitch(left);
	}

	private static void twoSwitch(boolean left) throws InterruptedException
	{
		Thread.sleep(500);
		plate.setSpeed(-1);
		plate.checkBottom();
		driveAuto.go(Constants.DRIVE_BACK, 2000);
		driveAuto.turn(left ? 90 : -90);

		claw.setPiston(false);
		claw.in();

		driveAuto.go(Constants.GET_CUBE, 1000);
		claw.setPiston(true);
		if (beam.get())
		{
			plate.setHeight(Constants.SCALE_HEIGHT);
			claw.setSpeed(-.15);

			driveAuto.go(-Constants.GET_CUBE, 1000);
			driveAuto.turn(0);
			driveAuto.go(-Constants.DRIVE_BACK, 2000);
			claw.out();
		}
	}
}