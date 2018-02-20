package org.usfirst.frc.team223.robot.constants;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
	public static int driveL1 = 1;
	public static int driveL0 = 2;// encoder on this one
	public static int driveR1 = 6;//encoder on this one
	public static int driveR0 = 7;
	
	public static int elevator0 = 10;// encoder on this one
	public static int elevator1 = 8;
	public static int elevator2 = 9;
	
	public static int plate = 4;//encoder on this one
	
	public static int claw =3;
	public static int intake=5;
	
	public static int pcmID = 51;
	public static int driveSolenoid = 0;
	public static int elevateSolenoid = 2;
	
	
	public static int plateTop=2;
	public static int plateBottom=3;
	public static int elevatorBotom=1;
	public static int elevatorTop=0;
}
