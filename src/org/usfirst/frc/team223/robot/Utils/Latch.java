package org.usfirst.frc.team223.robot.Utils;

import org.usfirst.frc.team223.robot.OI;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class Latch
{
	private JoystickButton b1;
	private JoystickButton b2;
	private boolean hold;

	public Latch(JoystickButton on, JoystickButton off)
	{
		b1 = on;
		b2 = off;
		this.hold = false;
	}

	public Latch(JoystickButton on)
	{
		b1 = on;
		this.hold = true;
	}

	public boolean get()
	{
		if (hold) return hold();
		return latch();
	}

	private boolean on = false;

	private boolean hold()
	{
		if (b1.get())
		{
			if (!on)
			{
				on = true;
				go();
				return true;
			}
		}
		else if (on)
		{
			on = false;
			stop();
		}
		return false;
	}

	private boolean b1prev = false;
	private boolean b2prev = false;
	private boolean go = false;

	private boolean latch()
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
