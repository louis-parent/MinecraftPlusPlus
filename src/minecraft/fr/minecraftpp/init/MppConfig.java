package fr.minecraftpp.init;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MppConfig
{
	private static MppConfig currentConfig;

	private long seed;

	private MppConfig(File config)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(config));
			this.seed = Long.parseLong(reader.readLine().substring(5));
			reader.close();

		}
		catch (NumberFormatException | IOException e)
		{
			e.printStackTrace();
		}
	}

	public static MppConfig init()
	{
		File config = new File("MppConfig.mpp");

		try
		{
			if (config.createNewFile())
			{
				writeDefaultText(config);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		MppConfig.currentConfig = new MppConfig(config);
		return MppConfig.currentConfig;
	}

	private static void writeDefaultText(File empty)
	{
		Random rand = new Random();

		String str = "seed=" + rand.nextLong();

		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(empty));
			writer.write(str);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public long getSeed()
	{
		return this.seed;
	}

	public static MppConfig getCurrentConfig()
	{
		return MppConfig.currentConfig;
	}
}
