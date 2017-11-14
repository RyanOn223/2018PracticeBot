package org.usfirst.frc.team223.robot.drive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveAuto extends DriveBase
{
	private PIDSource src = new PIDSource()
	{
		@Override public void setPIDSourceType(PIDSourceType pidSource) {}
		@Override public double pidGet() { return 0;}// getShooterSpeed(); }
		@Override public PIDSourceType getPIDSourceType() { return PIDSourceType.kRate; }
	};
	
	private PIDOutput out = new PIDOutput()
	{
		@Override public void pidWrite(double output) { rotate(output); }
	};
	
	private static double pk =.005;
	private static double ik = 0;
	private static double dk = 0;
	
	private PIDController controller;

	private AHRS ahrs;
	
	public DriveAuto(AHRS ahrs)
	{
		this.ahrs=ahrs;
		controller = new PIDController(pk, ik, dk, ahrs, out);
	}
	
	public void rotate(double amount)
	{
		talonFL.set(amount);
		talonFR.set(amount);
		talonBL.set(amount);
		talonBR.set(amount);
	}
	public void stopPID()
	{
		controller.disable();
		stopMotors();
	}
	
	public void setTargetAngle(double out)
	{
		controller.setSetpoint(out);
	}
}