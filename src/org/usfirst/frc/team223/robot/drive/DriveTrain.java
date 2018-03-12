package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.constants.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain
{
	TalonSRX talonL0 = new TalonSRX(RobotMap.driveL0);
	TalonSRX talonL1 = new TalonSRX(RobotMap.driveL1);// encoder here
	TalonSRX talonR0 = new TalonSRX(RobotMap.driveR0);
	
	TalonSRX talonR1 = new TalonSRX(RobotMap.driveR1);// encoder here

	Solenoid solenoid = new Solenoid(RobotMap.pcmID, RobotMap.driveSolenoid);

	public DriveTrain()
	{
		talonL1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talonR1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);		

		talonR0.setInverted(true);
		talonR1.setInverted(true);
	}

	public void setLeft(double L)
	{
		// if(L>0)L=Math.min(L,.6);
		// if(L<0)L=Math.max(L,-.6);
		talonL0.set(ControlMode.PercentOutput, L);
		talonL1.set(ControlMode.PercentOutput, L);
	}

	public void setRight(double R)
	{
		// if(R>0)R=Math.min(R,.6);
		// if(R<0)R=Math.max(R,-.6);
		talonR1.set(ControlMode.PercentOutput, R);
		talonR0.set(ControlMode.PercentOutput, R);
	}

	public void setMotors(double L, double R)
	{
		// System.out.println(R+" "+L);
		setLeft(L);
		setRight(R);
	}

	/**
	 * set speed of all motors to zero
	 */
	public void stopMotors()
	{
		setMotors(0, 0);
	}

	public void setPistons(boolean yes)
	{
		solenoid.set(yes);
	}

	public void resetEncoders()
	{
		talonL1.setSelectedSensorPosition(0, 0, 0);
		talonR1.setSelectedSensorPosition(0, 0, 0);
	}

	public double getLeftSpeed()
	{
		return -talonL1.getSelectedSensorVelocity(0);
	}

	public double getRightSpeed()
	{
		return talonR1.getSelectedSensorVelocity(0);
	}

	public double getLeftPosition()
	{
		return talonL1.getSelectedSensorPosition(0);
	}

	public double getRightPosition()
	{
		return talonR1.getSelectedSensorPosition(0);
	}

	public double getRightCurrent()
	{
		return talonR0.getOutputCurrent()+talonR1.getOutputCurrent();
	}

	public double getLeftCurrent()
	{
		return talonL0.getOutputCurrent()+talonL1.getOutputCurrent();
	}

	public double getRightVoltage()
	{
		return talonR0.getMotorOutputVoltage()+talonR1.getMotorOutputVoltage();
	}

	public double getLeftVoltage()
	{
		return talonL0.getMotorOutputVoltage()+talonL1.getMotorOutputVoltage();
	}
}
