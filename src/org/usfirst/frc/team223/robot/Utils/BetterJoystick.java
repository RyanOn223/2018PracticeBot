package org.usfirst.frc.team223.robot.Utils;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Joystick;

public class BetterJoystick extends Joystick
{
	private double deadbandCoef = 0;
	private Map<Integer, Double> axisOffsets = new HashMap<>();
	
	
	public BetterJoystick(int port)
	{
		super(port);
	}

	@Override
	public double getRawAxis(int axis)
	{
		double raw =super.getRawAxis(axis);
		if(Math.abs(raw) < deadbandCoef)
			raw = 0;
		return raw;
	}
	
	
	
	public double goodGetRawAxis(int axis) {
		double scl = axisOffsets.get(axis) != null ? axisOffsets.get(axis) : 0;
		double val = super.getRawAxis(axis) - scl;
		return Math.abs(val) < deadbandCoef ? 0 : val;
	}
	
	

	public double getDeadbandCoef()
	{
		return deadbandCoef;
	}

	public void setDeadbandCoef(double deadbandCoef)
	{
		this.deadbandCoef = deadbandCoef;
	}

	public Map<Integer, Double> getAxisOffsets()
	{
		return axisOffsets;
	}

	public void setAxisOffsets(Map<Integer, Double> axisOffsets)
	{
		this.axisOffsets = axisOffsets;
	}
}
