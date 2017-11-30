package org.usfirst.frc.team223.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive
{
	//Speed Controllers controlling the Drive train.
	CANTalon talonL0;
	CANTalon talonL1;
	CANTalon talonR0;
	CANTalon talonR1;
	
	Solenoid solenoidR=new Solenoid(RobotMap.pcmID,RobotMap.rightSolenoid);
	Solenoid solenoidL=new Solenoid(RobotMap.pcmID,RobotMap.leftSolenoid);
	//Victor l;
	
	public Drive(int r0,int r1,int l0, int l1)
	{
		talonL0=new CANTalon(l0);
		talonR0=new CANTalon(r0);
		talonL1=new CANTalon(l1);
		talonR1=new CANTalon(r1);
	}

	//Set the controllers based on joystick axis inputs. 
	public void cheese(Joystick stick)
	{
		talonL0.set(-(stick.getRawAxis(OI.leftYAxis)-stick.getRawAxis(OI.rightXAxis)));
		talonL1.set(-(stick.getRawAxis(OI.leftYAxis)-stick.getRawAxis(OI.rightXAxis)));

		talonR0.set( (stick.getRawAxis(OI.leftYAxis)+stick.getRawAxis(OI.rightXAxis)));
		talonR1.set( (stick.getRawAxis(OI.leftYAxis)+stick.getRawAxis(OI.rightXAxis)));	
	}
}