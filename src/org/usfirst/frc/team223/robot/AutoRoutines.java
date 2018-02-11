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
	public static void near(int distance, int creep, int height, boolean robotLeft)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					//plate.setHeight(height*Constants.PLATE_CNT_TO_IN);
					plate.setSpeed(1);
					plate.checkTop();
					
					
					driveAuto.go(distance*Constants.DRIVE_CNT_TO_IN);
					driveAuto.turn(robotLeft ? 90 : -90);
					driveAuto.go(creep*Constants.DRIVE_CNT_TO_IN);
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
	public static void far(int distance, int creep, int height, boolean robotLeft)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					
					//plate.setHeight((height-34)*Constants.PLATE_CNT_TO_IN);
					//elevator.setHeight(34*Constants.ELE_CNT_TO_IN);  elevator. gotottop
					
					plate.setSpeed(1);
					
					
					driveAuto.go(distance*Constants.DRIVE_CNT_TO_IN);
					driveAuto.turn(robotLeft ? 90 : -90);
					elevator.setSpeed(1);//TODO get up before creep
					
					elevator.checkTop();Thread.sleep(2000);
					driveAuto.go(creep*Constants.DRIVE_CNT_TO_IN);
					claw.out();
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}
	
	public static void middle(int distance, int creep, int height, boolean buttonLeft)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					
					//80 115
					plate.setSpeed(1);
					//plate.setHeight(height*Constants.PLATE_CNT_TO_IN);
					
					driveAuto.go(80*Constants.DRIVE_CNT_TO_IN);
					
					driveAuto.turn(-90);
					
					driveAuto.go(115*Constants.DRIVE_CNT_TO_IN);
					
					driveAuto.turn(0);
					driveAuto.go(58*Constants.DRIVE_CNT_TO_IN);
					driveAuto.turn(90);
					claw.out();
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}
	public static void none(int distance)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					driveAuto.go(distance*Constants.DRIVE_CNT_TO_IN);
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}
}
