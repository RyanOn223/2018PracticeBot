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
		talonFL.set(-(stick.getRawAxis(OI.leftYAxis) + stick.getRawAxis(OI.rightXAxis)) / 2);
		talonFR.set((stick.getRawAxis(OI.leftYAxis) - stick.getRawAxis(OI.rightXAxis)) / 2);
		talonBL.set(-(stick.getRawAxis(OI.leftYAxis) + stick.getRawAxis(OI.rightXAxis)) / 2);
		talonBR.set((stick.getRawAxis(OI.leftYAxis) - stick.getRawAxis(OI.rightXAxis)) / 2);
	}

	public void mec(Joystick stick)
	{
		double leftX = stick.getRawAxis(OI.leftXAxis);
		double leftY = stick.getRawAxis(OI.leftYAxis);
		double rightX = stick.getRawAxis(OI.rightXAxis);

		talonFL.set(-(leftY + rightX + leftX) / 3);
		talonFR.set( (leftY - rightX - leftX) / 3);
		talonBL.set(-(leftY + rightX - leftX) / 3);
		talonBR.set( (leftY - rightX + leftX) / 3);
	}
}