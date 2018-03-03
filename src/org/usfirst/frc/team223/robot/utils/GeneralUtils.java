package org.usfirst.frc.team223.robot.utils;

public class GeneralUtils
{
	/**
	 * puts the input into the range -target to target
	 */
	public static double bounds(double input,double target)
	{
		target=Math.abs(target);
		
		if(input<0)
		{
			input=Math.max(input, -target);
		}
		else if(input>0)
		{
			input=Math.min(input, target);
		}
		return input;
	}
}
