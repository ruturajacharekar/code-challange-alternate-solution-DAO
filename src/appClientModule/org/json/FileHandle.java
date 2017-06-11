package org.json;

import java.io.InputStream;
// Since we are loading resource from classpath
public class FileHandle {
	public static InputStream inputStreamFromFile(String path)
	{
		try
		{
			InputStream inputStream = FileHandle.class.getResourceAsStream(path);
			return inputStream;
		}
		catch(Exception er)	
		{
			er.printStackTrace();
		}
		return null;
	}
}
