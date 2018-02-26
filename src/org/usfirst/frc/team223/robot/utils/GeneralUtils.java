package org.usfirst.frc.team223.robot.utils;

public class GeneralUtils
{
	/**
	 * puts the input into the range 180 to -180
	 */
	public static double degreeBounds(double d)
	{
		int num = ((int) d) / 360;
		d = d - num * 360;
		if (d < -180) d += 360;
		else if (d > 180) d -= 360;
		return d;
	}
}
