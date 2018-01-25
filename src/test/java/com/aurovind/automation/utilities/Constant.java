package com.aurovind.automation.utilities;

public class Constant 
{
	public static final String rootFolder = System.getProperty("user.dir");
	
	public static final int start = 0;
	public static final int timeout = 60;
	public static final int alertTimeout = 5;
	
	public static final String outPutZipDirectoryPath = "./target/";
	public static final String MainframeAFE = rootFolder+"\\MainframeAFE";
	public static final String statusFileAFE = rootFolder+"\\MainframeAFE\\MFLog\\ExecutionStatus.txt";
	public static final String AEReportPath = "./target/AFEReports";
	
	
	public static final String AE_Output = System.getProperty("user.dir")+"/MainframeAFE/AFE_Output.xlsx";
	
	//public static final String BiWeeklyOPDumpPath = rootFolder+"\\MainframeAFE\\BiWeekly_Output.txt";

	public static final String BiWeeklyOPDumpPath = rootFolder+"\\MainframeAFE\\BiWeekly_Output.txt";;
	
	public static final String Downloads = rootFolder+"\\Downloads";
}
