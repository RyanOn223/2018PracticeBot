package org.usfirst.frc.team223.robot.drive;

import org.usfirst.frc.team223.robot.constants.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain
{
	TalonSRX talonL0 = new TalonSRX(RobotMap.driveL0);// encoder here
	TalonSRX talonL1 = new TalonSRX(RobotMap.driveL1);// slave
	TalonSRX talonR0 = new TalonSRX(RobotMap.driveR0);// slave
	TalonSRX talonR1 = new TalonSRX(RobotMap.driveR1);// encoder here

	Solenoid solenoidL = new Solenoid(RobotMap.pcmID, RobotMap.leftSolenoid);
	Solenoid solenoidR = new Solenoid(RobotMap.pcmID, RobotMap.rightSolenoid);

	public DriveTrain()
	{
		talonL1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talonR0.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

		// setSlaves
		talonL1.set(ControlMode.Follower, RobotMap.driveL0);
		talonR0.set(ControlMode.Follower, RobotMap.driveR1);
		// done

		talonR0.setInverted(true);
		talonR1.setInverted(true);
	}

	public void setLeft(double L)
	{
		// if(L>0)L=Math.min(L,.6);
		// if(L<0)L=Math.max(L,-.6);
		talonL0.set(ControlMode.PercentOutput, L);
	}

	public void setRight(double R)
	{
		// if(R>0)R=Math.min(R,.6);
		// if(R<0)R=Math.max(R,-.6);
		talonR1.set(ControlMode.PercentOutput, R);
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
		solenoidL.set(yes);
		solenoidR.set(yes);
	}

	public void resetEncoders()
	{
		talonL0.setSelectedSensorPosition(0, 0, 0);
		talonR1.setSelectedSensorPosition(0, 0, 0);
	}

	public double getLeftSpeed()
	{
		return -talonL0.getSelectedSensorVelocity(0);
	}

	public double getRightSpeed()
	{
		return talonR1.getSelectedSensorVelocity(0);
	}

	public double getLeftPosition()
	{
		return -talonL0.getSelectedSensorPosition(0);
	}

	public double getRightPosition()
	{
		return talonR1.getSelectedSensorPosition(0);
	}
}
