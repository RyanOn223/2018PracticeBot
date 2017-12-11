package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.RobotMap;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

public class DriveBase
{
	CANTalon talonL0=new CANTalon(RobotMap.driveL0);
	CANTalon talonL1=new CANTalon(RobotMap.driveL1);
	CANTalon talonR0=new CANTalon(RobotMap.driveR0);
	CANTalon talonR1=new CANTalon(RobotMap.driveR1);
	

	Solenoid solenoidL = new Solenoid(RobotMap.pcmID, RobotMap.leftSolenoid);
	Solenoid solenoidR = new Solenoid(RobotMap.pcmID, RobotMap.rightSolenoid);

	public DriveBase()
	{

	}
	
	public void setMotors(double amount)
	{
		talonL0.set(-amount);
		talonL1.set(-amount);
		talonR0.set(amount);
		talonR1.set(amount);
	}
	public void setSides(double L,double R)
	{
		talonL0.set(-L);	
		talonL1.set(-L);
		talonR0.set(R);
		talonR1.set(R);
	}
	/**
	 *  set speed of all motors to zero
	 */
	public void stopMotors()
	{
		setMotors(0);
	}
	public void setPistons(boolean yes)
	{
		solenoidL.set(yes);
		solenoidR.set(yes);
	}
	public void free()
	{
		solenoidR.free();
		solenoidL.free();
	}
}
