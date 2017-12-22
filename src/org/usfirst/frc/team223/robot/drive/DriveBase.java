package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.RobotMap;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

public class DriveBase
{
	CANTalon talonL0=new CANTalon(RobotMap.driveL0);//encoder here
	CANTalon talonL1=new CANTalon(RobotMap.driveL1);//slave
	CANTalon talonR0=new CANTalon(RobotMap.driveR0);//slave
	CANTalon talonR1=new CANTalon(RobotMap.driveR1);//encoder here
	

	Solenoid solenoidL = new Solenoid(RobotMap.pcmID, RobotMap.leftSolenoid);
	Solenoid solenoidR = new Solenoid(RobotMap.pcmID, RobotMap.rightSolenoid);
	
	public void setMotors(double amount)
	{
		talonL0.set(amount);
		talonR1.set(amount);
	}
	public DriveBase()
	{
		//setSlaves
		talonL1.changeControlMode(CANTalon.TalonControlMode.Follower);
		talonL1.set(RobotMap.driveL0);
		talonR0.changeControlMode(CANTalon.TalonControlMode.Follower);
		talonR0.set(RobotMap.driveR1);
		//done
		talonR1.setInverted(true);
	}
	
	public void setLeft(double l)
	{
		talonL0.set(l);
	}
	public void setRight(double r)
	{
		talonR1.set(r);
	}
	
	public void setSides(double L,double R)
	{
		talonL0.set(-L);
		talonR1.set(-R);
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

	public void resetEncoders()
	{
		talonL0.setEncPosition(0);
		talonR1.setEncPosition(0);
	}
	
	public double getLeftPosition()
	{
		return talonL0.getPosition();
	}
	
	public double getRightPosition()
	{
		return talonR1.getPosition();
	}
}
