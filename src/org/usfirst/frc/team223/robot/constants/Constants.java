package org.usfirst.frc.team223.robot.constants;

public class Constants
{
	public static final double ELE_CNT_TO_IN = 256;// 36 in max height
	public static final double PLATE_CNT_TO_IN = 23232;// not calculazsted
	public static final double DRIVE_CNT_TO_IN = 24.69;
	public static final double CLAW_CNT_TO_DEG = 36;

	public static final int SCALE_HEIGHT = 345678;// unknwn
	public static final int SWITCH_HEIGHT = 345678;// unknown

	// Near AutoRoutine Distances---------

	public static final double NEAR_DISTANCE = 137 * DRIVE_CNT_TO_IN;
	public static final double NEAR_CREEP = 36 * DRIVE_CNT_TO_IN;
	public static final double NEAR_PLATE_HEIGHT = 2345 * PLATE_CNT_TO_IN;// unknown do not use

	// -----------------------------------

	// Far AutoRoutine Distances---------

	public static final double FAR_DISTANCE = 303 * DRIVE_CNT_TO_IN;
	public static final double FAR_CREEP = 36 * DRIVE_CNT_TO_IN;
	public static final double FAR_PLATE_HEIGHT = 2345 * PLATE_CNT_TO_IN;// unknown do not use

	// -----------------------------------

	// Middle AutoRoutine Distances---------

	public static final double MIDDLE_DISTANCE = 80 * DRIVE_CNT_TO_IN;
	public static final double MIDDLE_LEFT = 115 * DRIVE_CNT_TO_IN;
	public static final double MIDDLE_CREEP = 58 * DRIVE_CNT_TO_IN;
	public static final double MIDDLE_PLATE_HEIGHT = 2345 * PLATE_CNT_TO_IN;// unknown do not use

	// -----------------------------------

	public static final double ERROR_DISTANCE = 160 * DRIVE_CNT_TO_IN;

}
