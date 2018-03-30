package org.usfirst.frc.team223.robot.constants;

public class Constants
{
	public static final double ELE_CNT_TO_IN = 981;// 36 in max height
	public static final double PLATE_CNT_TO_IN = 23232;// not calculazsted
	public static final double DRIVE_CNT_TO_IN = 25.1;
	public static final double CLAW_CNT_TO_DEG = 36;

	public static final int SCALE_HEIGHT = 345678;// unknwn
	public static final double SWITCH_HEIGHT = 345 * PLATE_CNT_TO_IN;// unknown

	public static final double ELEVATOR_HEIGHT = 20 * ELE_CNT_TO_IN;

	// Cross Line Distances------------------

	public static final double TO_SWITCH = 149 * DRIVE_CNT_TO_IN;
	public static final double TO_MIDDLE = 80 * DRIVE_CNT_TO_IN + TO_SWITCH;

	public static final double START_CREEP = 40 * DRIVE_CNT_TO_IN;
	public static final double FAR_ACROSS = 236 * DRIVE_CNT_TO_IN;
	public static final double MIDDLE_ACROSS = 110 * DRIVE_CNT_TO_IN;

	// Near AutoRoutine Distances---------

	public static final double NLEVER_DISTANCE = 25 * DRIVE_CNT_TO_IN;

	public static final double NLSCALE_ROTATE = 30;
	public static final double NSCALE_DISTANCE = 116 * DRIVE_CNT_TO_IN;
	public static final double NWIDE_SCALE_DISTANCE = 305 * DRIVE_CNT_TO_IN;
	// -----------------------------------

	// Far AutoRoutine Distances

	public static final double FSCALE_DISTANCE = 174 * DRIVE_CNT_TO_IN;
	public static final double FLEVER_DISTANCE = FSCALE_DISTANCE + 36 * DRIVE_CNT_TO_IN;
	public static final double FSCALE_TURN = -20;

	// ----------------------------------

	// Middle AutoRoutine Distances

	public static final double LEFT_MIDDLE_DISTANCE = 59 * Constants.DRIVE_CNT_TO_IN;
	public static final double RIGHT_MIDDLE_DISTANCE = 54 * Constants.DRIVE_CNT_TO_IN;

	public static final double MIDDLE_START_DISTANCE = 58.5 * DRIVE_CNT_TO_IN;
	public static final double MIDDLE_END_DISTANCE = 48 * DRIVE_CNT_TO_IN;

	// ----------------------------------

	// Two Cube Auto

	public static final double DRIVE_BACK = -7 * DRIVE_CNT_TO_IN;
	public static final double GET_CUBE = 20 * DRIVE_CNT_TO_IN;
	
	// ---------------

	public static final double SCALE_CREEP = 50 * DRIVE_CNT_TO_IN;
	public static final double LEVER_CREEP = 33 * DRIVE_CNT_TO_IN;

	public static final double ERROR_DISTANCE = 120 * DRIVE_CNT_TO_IN;
}
