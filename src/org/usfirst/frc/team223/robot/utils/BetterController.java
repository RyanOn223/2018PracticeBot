package org.usfirst.frc.team223.robot.utils;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class BetterController extends PIDController
{
	public BetterController(double p, double i, double d, PIDSource source, PIDOutput out)
	{
		super(p, i, d, source, out);
	}

	public void startPID(double startPoint)
	{
		this.setSetpoint(startPoint);
		if (!this.isEnabled()) this.enable();
	}

	public void addSetpoint(double i)
	{
		this.setSetpoint(i + this.getSetpoint());
	}
}
