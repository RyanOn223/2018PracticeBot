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
	public static void init(DriveAuto d,Claw c, Plate p,Elevator e)
	{
		driveAuto = d;
		claw=c;
		plate=p;
		elevator=e;
	}
	
	/**
	 * @param distance initial Distance
	 * @param creep How far to move after turn
	 * @param robotLeft Position of robot
	 */
	public static void near(boolean robotLeft)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);

					plate.setHeight(Constants.SCALE_HEIGHT);					
					
					driveAuto.go(Constants.NEAR_DISTANCE,2000);
					driveAuto.turn(robotLeft ? 90 : -90);
					driveAuto.go(Constants.NEAR_CREEP,2000);
					claw.out();
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}
	
	/**
	 * @param distance initial Distance
	 * @param creep How far to move after turn
	 * @param height How high to lift plate.  Must be larger than 34 in.
	 * @param robotLeft Position of robot
	 */
	public static void far(boolean robotLeft)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					
					plate.setHeight(Constants.SCALE_HEIGHT);
					
					driveAuto.go(Constants.FAR_DISTANCE,4000);
					driveAuto.turn(robotLeft ? 90 : -90);
					
					elevator.setSpeed(1);
					elevator.checkTop();//wait for elevator at top
					
					driveAuto.go(Constants.FAR_CREEP,2000);
					claw.out();
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}
	
	public static void middle(boolean buttonLeft)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					
					plate.setHeight(Constants.SWITCH_HEIGHT);
					
					driveAuto.go(80*Constants.DRIVE_CNT_TO_IN,2000);
					driveAuto.turn(buttonLeft?-90:90);
					driveAuto.go(115*Constants.DRIVE_CNT_TO_IN,2000);
					driveAuto.turn(0);
					driveAuto.go(58*Constants.DRIVE_CNT_TO_IN,2000);
					driveAuto.turn(buttonLeft?90:-90);
					claw.out();
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}
	public static void error()
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					driveAuto.go(Constants.ERROR_DISTANCE,2000);
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}

	public static void none(boolean left)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					driveAuto.go(Constants.TO_MIDDLE,2000);
					driveAuto.turn(left?90:-90);
					driveAuto.go(Constants.ACROSS_DISTANCE, 2000);
					driveAuto.turn(180);
					driveAuto.go(Constants.NONE_CREEP, 1000);
					claw.out();
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
		
	}
}
