package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

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
		talonL0.setFeedbackDevice(FeedbackDevice.QuadEncoder);

		//setSlaves
		talonL1.changeControlMode(CANTalon.TalonControlMode.Follower);
		talonL1.set(RobotMap.driveL0);
		talonR0.changeControlMode(CANTalon.TalonControlMode.Follower);
		talonR0.set(RobotMap.driveR1);
		//done
		talonR1.setInverted(true);
	}
	public void setStuff(double i)
	{
		talonL0.setPosition(0);
		//talonR1.setPosition(0);

		talonL0.set(i);
		//talonR1.set(i);
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
	public void free()
	{
		solenoidR.free();
		solenoidL.free();
	}
	public double getSpeed()
	{
//		return talonL0.getPosition();//+talonR1.getSpeed())/2;
		return talonL0.getEncPosition();
	}
	public double getLeftSpeed()
	{
		return talonL0.getSpeed();
	}
	public double getRightSpeed()
	{
		return talonR1.getSpeed();
	}
}
