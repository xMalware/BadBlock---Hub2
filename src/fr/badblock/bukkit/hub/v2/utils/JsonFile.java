package fr.badblock.bukkit.hub.v2.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import fr.badblock.bukkit.hub.v2.BadBlockHub;

public class JsonFile
{

	public static String readFile(File file)
	{
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			String sCurrentLine;
			boolean bool = false;
			while ((sCurrentLine = br.readLine()) != null)
			{
				if (bool) stringBuilder.append(System.lineSeparator());
				bool = true;
				stringBuilder.append(sCurrentLine);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
	
	public static void saveStringFile(File file, String data)
	{
		try
		{
			FileWriter fileWriter = new FileWriter(file, false);
			fileWriter.write(data);
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static <T> void saveFile(File file, T object)
	{
		saveStringFile(file, BadBlockHub.getInstance().getNotRestrictiveGson().toJson(object));
	}
	
	public static <T> T getFile(File file, Class<T> clazz)
	{
		return BadBlockHub.getInstance().getNotRestrictiveGson().fromJson(readFile(file), clazz);
	}
	
}
