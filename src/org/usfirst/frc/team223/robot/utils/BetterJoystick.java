package org.usfirst.frc.team223.robot.utils;

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

	public double getAxis(int axis)
	{
		double scl = axisOffsets.get(axis);
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
