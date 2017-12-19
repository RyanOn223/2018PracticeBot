package org.usfirst.frc.team223.robot;

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
	
	public static int leftYAxis=1;
	public static int leftXAxis=0;
	public static int rightXAxis=4;
	
	static int climb =3;
	
	static JoystickButton shiftSlow =new JoystickButton(driver, 3);
	static JoystickButton shiftFast =new JoystickButton(driver, 6);
	static JoystickButton startAngle =new JoystickButton(driver, 1);
	static JoystickButton stopAngle =new JoystickButton(driver, 4);
	
	static JoystickButton shootOn =new JoystickButton(operator,4);
	static JoystickButton shootOff =new JoystickButton(operator,1);
	static JoystickButton gearPiston =new JoystickButton(operator,3);
	static JoystickButton jaws =new JoystickButton(operator,2);
	static JoystickButton blend =new JoystickButton(operator,6);
	static JoystickButton intake =new JoystickButton(operator,5);
	
	/*static {
		Map<Integer, Double> driverOffsets = new HashMap<>();
		driverOffsets.put(leftXAxis, 0.1);
		driver.setAxisOffsets(driverOffsets);
		
		driver.setDeadbandCoef(.1);
		operator.setDeadbandCoef(0.1);
	}*/
}
