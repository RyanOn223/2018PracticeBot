package org.usfirst.frc.team223.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
	public static int driveFR = 11;
	public static int driveFL = 15;
	public static int driveBR = 22;
	public static int driveBL = 23;

	public static int intake = 12;
	public static int blender = 0;

	static int climb = 17;

	static int gearPiston = 2;
	static int jaws = 3;
	

	public static int shooter0 = 18;
	public static int shooter1 = 21;
	public static int shooter2 = 24;

	public static int pcmID = 52;
	public static int frontSolenoid = 0;
	public static int backSolenoid = 1;
}
