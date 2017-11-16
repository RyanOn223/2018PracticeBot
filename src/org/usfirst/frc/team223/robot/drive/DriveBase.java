package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.RobotMap;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

public class DriveBase
{
	CANTalon talonFL=new CANTalon(RobotMap.driveFL);
	CANTalon talonFR=new CANTalon(RobotMap.driveFR);
	CANTalon talonBL=new CANTalon(RobotMap.driveBL);
	CANTalon talonBR=new CANTalon(RobotMap.driveBR);

	public Solenoid solenoidF = new Solenoid(RobotMap.pcmID, RobotMap.frontSolenoid);
	public Solenoid solenoidB = new Solenoid(RobotMap.pcmID, RobotMap.backSolenoid);

	public DriveBase()
	{

	}
	
	public void setMotors(double amount)
	{
		talonFL.set(-amount);
		talonFR.set(amount);
		talonBL.set(-amount);
		talonBR.set(amount);
	}
	
	/**
	 *  set speed of all motors to zero
	 */
	public void stopMotors()
	{
		setMotors(0);
	}
}
