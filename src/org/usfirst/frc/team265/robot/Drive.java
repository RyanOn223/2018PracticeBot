package org.usfirst.frc.team265.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem
{
	//Speed Controllers controlling the Drive train.
	CANTalon talonFL;
	CANTalon talonFR;
	CANTalon talonBL;
	CANTalon talonBR;
	
	Solenoid solenoidF=new Solenoid(RobotMap.pcmID,RobotMap.frontSolenoid);
	Solenoid solenoidB=new Solenoid(RobotMap.pcmID,RobotMap.backSolenoid);
	//Victor l;
	
	public Drive(int FLeft,int FRight,int BLeft, int BRight)
	{
		talonFL=new CANTalon(FLeft);
		talonFR=new CANTalon(FRight);
		talonBL=new CANTalon(BLeft);
		talonBR=new CANTalon(BRight);
	}

	//Set the controllers based on joystick axis inputs.
	//TODO fix  
	public void cheese(Joystick stick)
	{
		talonFL.set(-(stick.getRawAxis(OI.leftYAxis)+stick.getRawAxis(OI.rightXAxis)) /2);
		talonFR.set( (stick.getRawAxis(OI.leftYAxis)-stick.getRawAxis(OI.rightXAxis)) /2);
		talonBL.set(-(stick.getRawAxis(OI.leftYAxis)+stick.getRawAxis(OI.rightXAxis)) /2);
		talonBR.set( (stick.getRawAxis(OI.leftYAxis)-stick.getRawAxis(OI.rightXAxis)) /2);	
	}
	@Override
	protected void initDefaultCommand()
	{
		// TODO Auto-generated method stub
		
	}
	public void mec(Joystick stick)
	{
		double leftX=stick.getRawAxis(OI.leftXAxis);
		double leftY=stick.getRawAxis(OI.leftYAxis);
		double rightX=stick.getRawAxis(OI.rightXAxis);
		
		talonFL.set( -(leftY+rightX+leftX)/3);
		talonFR.set(  (leftY-rightX-leftX)/3);
		talonBL.set( -(leftY+rightX-leftX)/3);
		talonBR.set(  (leftY-rightX+leftX)/3);
		
	}
}







