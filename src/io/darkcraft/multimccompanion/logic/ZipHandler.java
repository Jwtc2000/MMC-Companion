package io.darkcraft.multimccompanion.logic;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipHandler
{
	public static void unzip(File zipFile, File destination)
	{
		if(!zipFile.exists())
			System.err.println(zipFile + " does not seem to exist");
		try
		{
			ZipFile zip = new ZipFile(zipFile);
			Enumeration<? extends ZipEntry> zipEntries = zip.entries();
			while(zipEntries.hasMoreElements())
			{
				ZipEntry entry = zipEntries.nextElement();
				String name = entry.getName();
				File file = new File(destination,name);
				File dir = file.getParentFile();
				dir.mkdirs();
				if(entry.isDirectory())
					file.mkdir();
				else
				{
					if(file.exists())
						file.delete();
					byte[] buffer = new byte[4096];
					int len;
					InputStream isr = zip.getInputStream(entry);
					FileOutputStream fwriter = new FileOutputStream(file);
					while((len = isr.read(buffer)) > 0)
						fwriter.write(buffer, 0, len);
					fwriter.close();
					isr.close();
				}
			}
			zip.close();
		}
		catch(IOException ioe)
		{
			System.err.println("Error handling " + zipFile);
			ioe.printStackTrace();
		}
	}
}
