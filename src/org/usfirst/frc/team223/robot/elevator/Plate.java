package org.usfirst.frc.team223.robot.elevator;

import org.usfirst.frc.team223.robot.constants.RobotMap;
import org.usfirst.frc.team223.robot.utils.BetterController;
import org.usfirst.frc.team223.robot.utils.Panic;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Plate
{
	TalonSRX talon = new TalonSRX(RobotMap.plate);// encoder here
	DigitalInput top = new DigitalInput(RobotMap.plateTop);
	DigitalInput bottom = new DigitalInput(RobotMap.plateBottom);
	private BetterController controller;

	private PIDOutput out = new PIDOutput()
	{
		@Override
		public void pidWrite(double output)
		{
			setSpeed(output);
		}
	};
	private PIDSource src = new PIDSource()
	{
		@Override
		public void setPIDSourceType(PIDSourceType pidSource)
		{
		}

		@Override
		public PIDSourceType getPIDSourceType()
		{
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet()
		{
			return getPosition();
		}
	};

	private double p = 0.005;
	private double i = 0.00000;
	private double d = 0.00;

	public Plate()
	{
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		talon.setInverted(true);
		controller = new BetterController(p, i, d, src, out);
	}

	public void setHeight(double e)
	{
		controller.startPID(e);
	}

	private int pos = 0;

	public void setSpeed(double L)
	{
		//System.out.println(L);
		if(Panic.panic)
		{
			talon.set(ControlMode.PercentOutput, L);
			return;
		}
		
		if (L < 0)
		{
			if (!bottom.get())
			{
				pos = -1;//Dissabled
			}
			if (!top.get())
			{
				pos = 0;
			}
			if (pos < 0)
			{
				talon.set(ControlMode.PercentOutput, 0);
				return;
			}
		}
		if (L > 0)
		{
			if (!top.get())
			{
				pos = 1;
			}
			if (!bottom.get())
			{
				pos = 0;
			}
			if (pos > 0)
			{
				talon.set(ControlMode.PercentOutput, 0);
				return;
			}
		}
		talon.set(ControlMode.PercentOutput, L);
	}

	public void resetEncoders()
	{
		talon.setSelectedSensorPosition(0, 0, 0);
	}

	public double getSpeed()
	{
		return talon.getSelectedSensorVelocity(0);
	}

	public double getPosition()
	{
		return talon.getSelectedSensorPosition(0);
	}

	public void stopControllers()
	{
		controller.disable();
		controller.reset();
	}

	public void checkTop()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{
					if (!top.get())
					{
						talon.set(ControlMode.PercentOutput, 0);
						return;
					}

				}
			}
		}.start();
	}

	public double getCurrent()
	{
		return talon.getOutputCurrent();
	}

	public double getVoltage()
	{
		return talon.getMotorOutputVoltage();
	}
}
