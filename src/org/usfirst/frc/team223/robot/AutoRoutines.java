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
					plate.setHeight(height*Constants.PLATE_CNT_TO_IN);
					
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
	 * @param height How high to lift plate
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
					
					plate.setHeight(height*Constants.PLATE_CNT_TO_IN/2);
					elevator.setHeight(height*Constants.ELE_CNT_TO_IN/2);
					
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
	
	public static void middle(int distance, int height, boolean buttonLeft)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);
					
					plate.setHeight(height*Constants.PLATE_CNT_TO_IN);
					driveAuto.turn(buttonLeft ? -45 : 45);
					driveAuto.go(distance);
					
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
