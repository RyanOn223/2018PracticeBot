package org.usfirst.frc.team223.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
	//OI=Op Interface for those of you who are noobs
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
	static Joystick driver = new Joystick(0);
	static Joystick operator = new Joystick(1);
	
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
	
	
	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
