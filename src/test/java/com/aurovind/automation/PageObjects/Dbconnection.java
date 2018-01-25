package com.aurovind.automation.PageObjects;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import com.aurovind.automation.BaseTest;

public class Dbconnection extends BaseTest {
	
	//Connection details from CONFIG.properties
	public static String driver = CONFIG.getProperty("driver_DB");
	public static String url = CONFIG.getProperty("url_DB");
	
	//Variable details
	public static Connection con = null;
	
	public static void dbConnection() throws NoSuchAlgorithmException, Exception {
		
		String user = null;
		String password = null;
		try 
		{
			user = CONFIG.getProperty("username_DB");
			password = CONFIG.getProperty("password_DB");
			
		SecretKey secKey  = getSecretEncryptionKey();
		
		//For the DB Username encryption
		byte[] dbUsernameText = encryptText(CONFIG.getProperty("password_DB"), secKey);
		//For the DB Username decryption
		String decryptedUsername = decryptText(dbUsernameText, secKey);
		
		//For the DB password encryption
		byte[] dbPasswordText = encryptText(CONFIG.getProperty("password_DB"), secKey);
		//For the DB password decryption
		String decryptedText = decryptText(dbPasswordText, secKey);
		
		//System.out.println("AES Key (Hex Form):"+bytesToHex(secKey.getEncoded()));
		//System.out.println("Encrypted Text (Hex Form):"+bytesToHex(dbPasswordText));
		//System.out.println("Descrypted Text:"+decryptedText);
		}
		catch(Exception e)
		{
			ReportLog("[Dbconnection] Exception while decrypting password");
		}
		
		try
		{
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			ReportLog("[Dbconnection] Connected to Database - DSNS");
		}
		
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
			ReportLog("[Dbconnection] Problem in Connecting to Database - DSNS");
		}
		
		
	}
	
	
	

	/**
     * Close the DB connection
     * @author Aurovind
     * @return
     */
	
	
	public static void dbConnectionClose()
	{
		if(con != null) 
		{
			try 
			{
				con.close();
				ReportLog("[Dbconnection] Closed Database Connection");
			}
			catch(SQLException e)
			{
				ReportLog("[Dbconnection] Unable to Database Connection");
			}
		}
	}
	
	/**
     * Query to fetch the SSN 
     * @author Aurovind
     * @return
     */
	
	
	
	
	
	
	
	
	
	
	
	
		/**
	     * Convert a binary byte array into readable hex form
	     * @author Aurovind
	     * @param encoded
	     * @return
	     */
	
	
	private static String bytesToHex(byte[] encoded) {
		return DatatypeConverter.printHexBinary(encoded);

	}

	private static String decryptText(byte[] dbText, SecretKey secKey) throws NoSuchAlgorithmException, NoSuchPaddingException, Exception
	{
		Cipher typeAESCipher = Cipher.getInstance("AES");
		typeAESCipher.init(Cipher.DECRYPT_MODE, secKey);
		byte[] bytePlainText = typeAESCipher.doFinal(dbText);
		return new String(bytePlainText);
	}
	
		/**
	     * Encrypts plainText in AES using the secret key
	     * @author Aurovind
	     * @param dbPasswordText
	     * @param secKey
	     * @return
		 * @throws NoSuchPaddingException 
		 * @throws NoSuchAlgorithmException 
	     * @throws Exception
	     */

	
	private static byte[] encryptText(String dbText, SecretKey secKey) throws NoSuchAlgorithmException, NoSuchPaddingException, Exception
	{
		Cipher typeAESCipher = Cipher.getInstance("AES");
		typeAESCipher.init(Cipher.ENCRYPT_MODE, secKey);
		byte[] CipherText = typeAESCipher.doFinal(dbText.getBytes());
		return CipherText;
	}
	
		/**
	     * gets the AES encryption key. In your actual programs, this should be safely stored.
	     * @author Aurovind
	     * @return
	     * @throws Exception
	     */
	
	private static SecretKey getSecretEncryptionKey() throws NoSuchAlgorithmException, Exception
	{
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(128);
		SecretKey secKey = generator.generateKey();
		return secKey;
	}

}
