package org.usfirst.frc.team223.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
	public static int driveR0 = 1;
	public static int driveR1 = 2;// encoder on this one
	public static int driveL0 = 4;//encoder on this one
	public static int driveL1 = 3;

	public static int intake = 12;
	public static int blender = 0;

	static int climb = 17;

	static int gearPiston = 2;
	static int jaws = 3;

	public static int shooter0 = 18;
	public static int shooter1 = 21;
	public static int shooter2 = 24;

	public static int pcmID = 51;
	public static int leftSolenoid = 1;
	public static int rightSolenoid = 0;
}
