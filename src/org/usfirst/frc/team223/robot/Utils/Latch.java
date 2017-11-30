package org.usfirst.frc.team223.robot.Utils;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class Latch
{
	JoystickButton b1;
	JoystickButton b2;

	public Latch(JoystickButton on, JoystickButton off)
	{
		b1 = on;
		b2 = off;
	}

	private boolean b1prev = false;
	private boolean b2prev = false;
	private boolean go = false;

	public boolean get()
	{
		boolean b1curr = b1.get();
		boolean b2curr = b2.get();

		if (!b1prev && b1curr) // button 1 rising
		{
			go();
			go = true;
		}
		else if (!b2prev && b2curr) // button 2 rising
		{
			stop();
			go = false;
		}
		b1prev = b1curr;
		b2prev = b2curr;
		return go;
	}

	abstract public void go();

	abstract public void stop();

}
