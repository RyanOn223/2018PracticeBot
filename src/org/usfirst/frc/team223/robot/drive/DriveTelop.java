package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.OI;
import org.usfirst.frc.team223.robot.utils.BetterJoystick;

public class DriveTelop
{
	private DriveBase drive;
	
	public DriveTelop(DriveBase drive)
	{
		this.drive=drive;
	}

	/** Set the controllers based on joystick axis inputs.*/
	public void cheese(BetterJoystick stick)
	{
		drive.setSides((stick.getAxis(OI.leftYAxis) - stick.getAxis(OI.rightXAxis)*2/3) ,
				 (stick.getAxis(OI.leftYAxis) + stick.getAxis(OI.rightXAxis)*2/3));
	}
}