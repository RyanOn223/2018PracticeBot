package org.usfirst.frc.team223.robot.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Config
{
	public static String robotDir = System.getProperty("user.home") + "/2018Robot/";
	public static File config = new File(robotDir + "FRCConfig.txt");
	
	static PrintWriter writer;
	static BufferedReader reader;
	public static double shooterSpeed;
	
	String[] fileText = new String[1];
	
	
	public Config()
	{
		new File(robotDir).mkdirs();
		try
		{
			if (!config.exists())
			{
				config.createNewFile();
				writer=new PrintWriter(config);
				
				writer.write("Shooter Speed= "+ DefaultConfig.shooterSpeed);
				
				writer.close();
			}
			reader =new BufferedReader(new FileReader(config));
			int i=0;
			while(reader.ready())
			{
				fileText[i]=reader.readLine();
				i++;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void init()
	{

	}

}
