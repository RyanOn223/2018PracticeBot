package org.usfirst.frc.team223.robot;

import org.usfirst.frc.team223.robot.drive.DriveAuto;

public class AutoRoutines
{
	static DriveAuto driveAuto;

	public static void init(DriveAuto d)
	{
		driveAuto = d;
	}
	
	/**
	 * @param distance intitial Distance
	 * @param creep How far to move after turn
	 * @param robotLeft Position of robot
	 */
	public static void near(int distance, int creep, boolean robotLeft)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);

					driveAuto.go(distance);
					driveAuto.turn(robotLeft ? 90 : -90);
					driveAuto.go(creep);
					// clamp.drop()
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}
	
	/**
	 * @param distance intitial Distance
	 * @param creep How far to move after turn
	 * @param robotLeft Position of robot
	 */
	public static void far(int distance, int creep, boolean robotLeft)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);

					driveAuto.go(distance);
					driveAuto.turn(robotLeft ? 90 : -90);
					
					//clamp.extend()
					
					driveAuto.go(creep);
					// clamp.drop()
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}
	
	public static void middle(int distance, boolean buttonLeft)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					// wait for general init
					Thread.sleep(200);

					driveAuto.go(distance);
					driveAuto.turn(buttonLeft ? 90 : -90);
					// clamp.drop()
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
					driveAuto.go(distance);
				}
				catch (InterruptedException e)
				{
				}
			}
		}.start();
	}
}
