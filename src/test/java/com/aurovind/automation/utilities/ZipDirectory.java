package com.aurovind.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.testng.Reporter;

import com.aurovind.automation.BaseTest;

public class ZipDirectory extends BaseTest {

	public static void ZipReport(String directoryPath, String outPutDirectory) throws IOException {
		
		File directoryToZip = new File(directoryPath);
		List<File> fileList = new ArrayList<File>();
		ReportLog("[ZipDirectory]---Getting references to all files in: " + directoryToZip.getCanonicalPath());
		getAllFiles(directoryToZip, fileList);
		ReportLog("[ZipDirectory]---Creating zip file");
		writeZipFile(directoryToZip, fileList, outPutDirectory);
		ReportLog("[ZipDirectory]---Done");
		
	}



public static void writeZipFile(File directoryToZip, List<File> fileList, String outPutDirectory) throws IOException 
{
		try 
		{
			FileOutputStream fos = new FileOutputStream(outPutDirectory+directoryToZip.getName() + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			
			for(File file : fileList) 
			{
				if(!file.isDirectory()) 
				{
					addToZip(directoryToZip, file, zos);
				}
			}
			
			zos.close();
			fos.close();
		}
		catch(FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		catch(IOException e) 
		{
			e.printStackTrace();
		}
		
	}



public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException, IOException
{
	FileInputStream fis = new FileInputStream(file);
	
	//zipEntry's path should be relative to the directory being zipped, so rest of the path should be chopped off
	
	String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1, 
			file.getCanonicalPath().length());
	
	ZipEntry zipEntry = new ZipEntry(zipFilePath);
	zos.putNextEntry(zipEntry);
	
	byte[] bytes = new byte[1024];
	int length;
	while((length = fis.read(bytes)) >= 0) {
		zos.write(bytes, 0, length);
	}
	
	zos.closeEntry();
	fis.close();
}



public static void getAllFiles(File dir, List<File> fileList) 
{
		File[] files = dir.listFiles();
		for(File file : files) 
		{
			fileList.add(file);
			if(file.isDirectory()) 
			{
				getAllFiles(file, fileList);
			}
		}
		
	}


public static void ReportLog(String sMessage)
{
	Date sDate = new Date();
	SimpleDateFormat dStartDate = new SimpleDateFormat("HH:mm:ss");
	String time = dStartDate.format(sDate);
	Reporter.log("["+time+"]" + " " + sMessage + "\n" , true);
	
}
}