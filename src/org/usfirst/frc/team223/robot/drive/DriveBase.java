package org.usfirst.frc.team223.robot.drive;

import com.kauailabs.navx.frc.AHRS;

public class DriveBase
{
	protected DriveTrain drive;
	protected AHRS ahrs;
	public DriveBase(DriveTrain drive, AHRS ahrs)
	{
		this.drive=drive;
		this.ahrs=ahrs;
	}
	protected void rotate(double output)
	{
		drive.setMotors(-output, output);
	}
}
