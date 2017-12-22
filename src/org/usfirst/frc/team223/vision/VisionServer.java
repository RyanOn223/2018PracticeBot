package org.usfirst.frc.team223.vision;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class VisionServer extends Thread
{
	private ServerSocket mServer;
	private BufferedInputStream inputStream;
	private BufferedOutputStream outputStream;
	private Socket socket;
	public Stack<Integer> pointBuffer=new Stack<Integer>();
	private int port;

	public VisionServer(int port)
	{
		this.port = port;
	}

	@Override
	public void run()
	{
		super.run();
		inputStream = null;
		outputStream = null;
		socket = null;

		try
		{
			mServer = new ServerSocket(port);
			System.out.println("port opened");
			while (!Thread.currentThread().isInterrupted())
			{
				// Create Sockets and data streams
				socket = mServer.accept();

				System.out.println("new socket");
				inputStream = new BufferedInputStream(socket.getInputStream());
				outputStream = new BufferedOutputStream(socket.getOutputStream());

				// readImages();
				readData();
				System.out.println("closing Socket");
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (outputStream != null)
				{
					outputStream.close();
					outputStream = null;
				}

				if (inputStream != null)
				{
					inputStream.close();
					inputStream = null;
				}

				if (socket != null)
				{
					socket.close();
					socket = null;
				}

			}
			catch (IOException e)
			{

			}

		}

	}

	private void readData() throws IOException
	{
		try
		{
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());
			while (true)
			{
				int ans=inputStream.readInt();
				System.out.println(ans);
				System.out.println(System.currentTimeMillis());
			}
		}
		catch(EOFException u)
		{
			return;
		}
	}

	private void readImages() throws IOException
	{
		byte[] buff = new byte[256];
		int len = 0;
		String msg = null;
		while ((len = inputStream.read(buff)) != -1)
		{
			JsonParser parser = new JsonParser();
			JsonElement element = null;
			msg = new String(buff, 0, len);
			parser = new JsonParser();
			try
			{
				element = parser.parse(msg);
				JsonObject obj = element.getAsJsonObject();

				element = obj.get("length");
				int length = element.getAsInt();
				element = obj.get("height");
				int height = element.getAsInt();
				element = obj.get("width");
				int width = element.getAsInt();
				element = obj.get("type");
				int type = element.getAsInt();

				byte[] data = new byte[length];
				byte[] temp = new byte[2048];

				int i = 0;

				while (i != length)
				{
					len = inputStream.read(temp);

					for (int x = 0; x < len; x++)
					{
						data[i + x] = temp[x];
					}
					i += len;
				}
				System.out.println("recieved");

				Mat mat = new Mat(height, width, type);
				mat.put(0, 0, data);
				Imgcodecs.imwrite("/home/admin/q.png", mat);
			}
			catch (JsonParseException e)
			{
				System.out.println(msg);
				e.printStackTrace();
				InputStream m = socket.getInputStream();
				while (m.available() != 0)
				{
					m.read();
				}
				// System.exit(-1);
			}
			catch (IllegalStateException e)
			{
				System.out.println(msg);
				e.printStackTrace();
				InputStream m = socket.getInputStream();
				while (m.available() != 0)
				{
					m.read();
				}
				// System.exit(-1);
			}
		}
	}
}

