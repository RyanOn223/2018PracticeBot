package org.usfirst.frc.team223.vision;

import org.spectrum3847.RIOdroid.RIOadb;
import org.spectrum3847.RIOdroid.RIOdroid;
public class VisionServer
{
	public VisionServer()
	{
		RIOdroid.init();
		RIOadb.forward(3800, 8080);
		RIOadb.screencap("~/");
	}
}
