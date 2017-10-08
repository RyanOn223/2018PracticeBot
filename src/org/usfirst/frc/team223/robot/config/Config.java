package org.usfirst.frc.team223.robot.config;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config
{
	public double shooterSpeed;
	
	private static final String robotDir = System.getProperty("user.home") + "/2018Robot/";
	private final static File config = new File(robotDir + "FRCConfig.txt");
	private Properties prop;
	private FileOutputStream writer;
	private FileInputStream reader;

	
	public Config()
	{
		new File(robotDir).mkdirs();
		Properties pdefault=new Properties();
		
		pdefault.setProperty("Shooter Speed", Double.toString(DefaultConfig.shooterSpeed));
		prop=new Properties(pdefault);
		
		try
		{
			if (!config.exists())
			{
				config.createNewFile();
				writer=new FileOutputStream(config);
				
				prop.setProperty("Shooter Speed", Double.toString(DefaultConfig.shooterSpeed));
				prop.store(writer,null);
				
				writer.close();
			}
			else
			{
				
			}
		}
		catch (FileNotFoundException e){e.printStackTrace();}
		catch (IOException e){e.printStackTrace();}
	}

	public void init()
	{
		try
		{
			reader=new FileInputStream(config);
			prop.load(reader);
		}
		catch (FileNotFoundException e){e.printStackTrace();}
		catch (IOException e){e.printStackTrace();}
		
		try
		{
			shooterSpeed=Double.parseDouble(prop.getProperty("Shooter Speed"));
		}
		catch (java.lang.NumberFormatException g)
		{
			System.out.println("Number Formatt exception: Loading Defaults");
			shooterSpeed=DefaultConfig.shooterSpeed;
		}
	}

}
