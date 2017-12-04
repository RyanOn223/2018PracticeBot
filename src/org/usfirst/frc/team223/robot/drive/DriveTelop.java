package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.OI;
import edu.wpi.first.wpilibj.Joystick;

public class DriveTelop extends DriveBase
{
	public DriveTelop()
	{
	}

	// Set the controllers based on joystick axis inputs.
	public void cheese(Joystick stick)
	{
		setSides((stick.getRawAxis(OI.leftYAxis) - stick.getRawAxis(OI.rightXAxis)) / 2,
				 (stick.getRawAxis(OI.leftYAxis) + stick.getRawAxis(OI.rightXAxis)) / 2);
	}
}