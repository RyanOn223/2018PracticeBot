package org.usfirst.frc.team223.robot.constants;

public class Constants
{
	public static final double ELE_CNT_TO_IN = 256;// 36 in max height
	public static final double PLATE_CNT_TO_IN = 23232;// not calculazsted
	public static final double DRIVE_CNT_TO_IN = 24.69;
	public static final double CLAW_CNT_TO_DEG = 36;

	public static final int SCALE_HEIGHT = 345678;// unknwn
	public static final int SWITCH_HEIGHT = 345;// unknown

	public static final int ELEVATOR_HEIGHT = 8000;

	// Cross Line Distances------------------

	public static final double TO_SWITCH = 149;
	public static final double TO_MIDDLE = (63 + TO_SWITCH) * DRIVE_CNT_TO_IN;

	public static final double START_CREEP = 40 * DRIVE_CNT_TO_IN;
	public static final double FAR_ACROSS = 236 * DRIVE_CNT_TO_IN;// unknown
	public static final double MIDDLE_ACROSS = 117 * DRIVE_CNT_TO_IN;// unknown

	// Near AutoRoutine Distances---------

	public static final double NLEVER_DISTANCE = 25 * DRIVE_CNT_TO_IN;

	public static final double NLSCALE_ROTATE = 15;
	public static final double NSCALE_DISTANCE = 116 * DRIVE_CNT_TO_IN;

	// -----------------------------------

	// Far AutoRoutine Distances

	public static final double FSCALE_DISTANCE = 174 * DRIVE_CNT_TO_IN;
	public static final double FLEVER_DISTANCE = (FSCALE_DISTANCE + 46) * DRIVE_CNT_TO_IN;
	public static final double FSCALE_TURN=-20;
	
	// ----------------------------------

	public static final double SCALE_CREEP = 50 * DRIVE_CNT_TO_IN;
	public static final double LEVER_CREEP = 33 * DRIVE_CNT_TO_IN;

	public static final double ERROR_DISTANCE = 120 * DRIVE_CNT_TO_IN;
}
