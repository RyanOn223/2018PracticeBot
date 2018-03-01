package org.usfirst.frc.team223.robot.constants;

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
	public static BetterJoystick driver = new BetterJoystick(0);
	public static BetterJoystick operator = new BetterJoystick(1);

	public static int leftYAxis = 1;
	public static int leftXAxis = 0;
	public static int rightXAxis = 4;
	public static int rightYAxis = 5;

	public static int rightTrigger = 3;
	public static int leftTrigger = 2;

	static JoystickButton bottom = new JoystickButton(operator, 3);
	static JoystickButton top = new JoystickButton(operator, 4);
	
	//for disabling all "smart" features    remember to change value
	public static JoystickButton panic = new JoystickButton(operator, 1000);
	public static JoystickButton calm = new JoystickButton(operator, 1000);
	
	public static JoystickButton clawDrop = new JoystickButton(operator, 1);
	public static JoystickButton clawUp = new JoystickButton(operator, 4);

	public static JoystickButton elevatorLock = new JoystickButton(operator, 3);
	public static JoystickButton elevatorUnlock = new JoystickButton(operator, 2);

	public static JoystickButton intake = new JoystickButton(operator, 5);
	public static JoystickButton outtake = new JoystickButton(operator, 6);

	public static JoystickButton shiftFast = new JoystickButton(driver, 6);

	static
	{
		Map<Integer, Double> driverOffsets = new HashMap<>();
		driverOffsets.put(leftXAxis, 0.);
		driverOffsets.put(leftYAxis, 0.);
		driverOffsets.put(rightXAxis, 0.);
		driverOffsets.put(rightYAxis, 0.);

		driver.setAxisOffsets(driverOffsets);
		operator.setAxisOffsets(driverOffsets);

		driver.setDeadbandCoef(0.1);
		operator.setDeadbandCoef(0.1);
	}
}
