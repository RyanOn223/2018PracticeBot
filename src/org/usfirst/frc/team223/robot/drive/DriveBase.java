package org.usfirst.frc.team223.robot.drive;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team223.robot.utils.BetterController;

import com.kauailabs.navx.frc.AHRS;

public class DriveBase
{
	protected DriveTrain drive;
	protected AHRS ahrs;
	
	private Map<String, BetterController> controllers=new HashMap<String, BetterController>();
	
	public DriveBase(DriveTrain drive, AHRS ahrs)
	{
		this.drive=drive;
		this.ahrs=ahrs;
	}
	protected void rotate(double output)
	{
		drive.setMotors(-output, output);
	}
	
	public void addController(String key, BetterController value)
	{
		controllers.put(key, value);
	}
	
	public void stopControllers()
	{
		for(BetterController c:controllers.values())
		{
			c.disable();
			c.reset();
		}
	}
	
	public double getSet(String key)
	{
		return controllers.get(key).getSetpoint();
	}
	
	public void setPID(String key,double p, double i, double d)
	{
		BetterController c=controllers.get(key);
		c.reset();
		c.setPID(p, i, d);
	}
}
