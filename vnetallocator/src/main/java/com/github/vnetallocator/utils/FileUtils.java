package com.github.vnetallocator.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils
{
    public static String getFileContent(File file)
    {
	String fileContent = null;
	
	try
	{
	    fileContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), StandardCharsets.UTF_8);
	}
	catch (IOException e)
	{
	    // TODO Log de erro - erro ao abrir o arquivo da topologia da rede
	}
	
	return fileContent;
    }
}
