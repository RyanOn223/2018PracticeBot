package org.usfirst.frc.team223.robot.utils;

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
		stop();
	}

	public boolean get()
	{
		if (hold) return hold();
		return latch();
	}

	private boolean on = false;

	/**
	 * Runs go when pressed and stop when released
	 * 
	 * @returns on/off
	 */
	private boolean hold()
	{
		if (b1.get())
		{
			if (!on)
			{
				on = true;
				//System.out.println("hi");
				go();
				return true;
			}
		}
		else if (on)
		{
			//System.out.println("by");
			on = false;
			stop();
		}
		return on;
	}

	private boolean b1prev = false;
	private boolean b2prev = false;

	/**
	 * Runs go when on button is pressed and stop when the other is pressed
	 * 
	 * @returns on/off
	 */
	private boolean latch()
	{
		boolean b1curr = b1.get();
		boolean b2curr = b2.get();

		if (!b1prev && b1curr)
		{
			go();
			on = true;
		}
		else if (!b2prev && b2curr)
		{
			stop();
			on = false;
		}
		b1prev = b1curr;
		b2prev = b2curr;
		return on;
	}

	abstract public void go();

	abstract public void stop();
}
