package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.OI;
import edu.wpi.first.wpilibj.Joystick;

public class DriveTelop
{
	private DriveBase drive;
	
	public DriveTelop(DriveBase drive)
	{
		this.drive=drive;
	}

	// Set the controllers based on joystick axis inputs.
	public void cheese(Joystick stick)
	{
		drive.setSides((stick.getRawAxis(OI.leftYAxis) - stick.getRawAxis(OI.rightXAxis)) ,
				 (stick.getRawAxis(OI.leftYAxis) + stick.getRawAxis(OI.rightXAxis)));
	}
}