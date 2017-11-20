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

	Solenoid solenoidF = new Solenoid(RobotMap.pcmID, RobotMap.frontSolenoid);
	Solenoid solenoidB = new Solenoid(RobotMap.pcmID, RobotMap.backSolenoid);

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
	public void setAllMotors(double FL,double FR,double BL,double BR)
	{
		talonFL.set(-FL);
		talonFR.set(FR);
		talonBL.set(-BL);
		talonBR.set(BR);
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
		solenoidB.set(yes);
		solenoidF.set(yes);
	}
	public void free()
	{
		solenoidF.free();
		solenoidB.free();
	}
}
