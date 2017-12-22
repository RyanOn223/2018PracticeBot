package org.usfirst.frc.team223.robot;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team223.robot.utils.BetterJoystick;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
	static BetterJoystick driver = new BetterJoystick(0);
	static BetterJoystick operator = new BetterJoystick(1);

	public static int leftYAxis = 1;
	public static int leftXAxis = 0;
	public static int rightXAxis = 4;
	//public static int rightYAxis=3;
	static int climb = 3;

	//static JoystickButton shiftSlow = new JoystickButton(driver, 3);
	static JoystickButton shiftFast = new JoystickButton(driver, 6);

	static
	{
		// TODO add offsets
		Map<Integer, Double> driverOffsets = new HashMap<>();
		driverOffsets.put(leftXAxis, 0.);
		driverOffsets.put(leftYAxis, 0.);
		driverOffsets.put(rightXAxis, 0.);
		///driverOffsets.put(rightYAxis, 0);
		driver.setAxisOffsets(driverOffsets);

		driver.setDeadbandCoef(.1);
		operator.setDeadbandCoef(0.1);
	}
}
