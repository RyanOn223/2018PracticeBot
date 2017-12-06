package org.usfirst.frc.team223.robot.drive;

import com.kauailabs.navx.frc.AHRS;

public class DriveAuto
{
	private DriveBase drive;
	private AHRS ahrs;
	private DriveControl controller; 
	
	
	
	public DriveAuto(DriveBase drive, AHRS ahrs)
	{
		this.drive=drive;
		this.ahrs=ahrs;
		controller=new DriveControl(drive,ahrs);
	}
	public void forward(double amount)
	{
		drive.setMotors(amount);
	}
	public double getPID()
	{
		return controller.get();
	}
	
	public void set(double set)
	{
		//controller.
	}
	
}
