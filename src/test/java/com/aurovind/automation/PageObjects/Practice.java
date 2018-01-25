package com.aurovind.automation.PageObjects;

import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.sql.Connection;

import com.aurovind.automation.BaseTest;
import com.aurovind.automation.config.*;

public class Practice {
	

	public static void main(String[] args) throws NoSuchAlgorithmException, Exception {
		
		SecretKey secKey  = getSecretEncryptionKey();
		String passwordBytes = "SSSS";
		//CONFIG = new Properties();
		//String b = CONFIG.getProperty("password_DB");
		//System.out.println(b);
		//For the DB password encryption
		byte[] dbPasswordText = encryptText(passwordBytes, secKey);
		String decryptedText = decryptText(dbPasswordText, secKey);
		
		System.out.println("AES Key (Hex Form):"+bytesToHex(secKey.getEncoded()));
		System.out.println("Encrypted Text (Hex Form):"+bytesToHex(dbPasswordText));
		System.out.println("Descrypted Text:"+decryptedText);
		

	}
	
	
	/**
     * Convert a binary byte array into readable hex form
     * @author Aurovind
     * @param encoded
     * @return
     */


private static String bytesToHex(byte[] encoded) {
	return DatatypeConverter.printHexBinary(encoded);

}

private static String decryptText(byte[] dbPasswordText, SecretKey secKey) throws NoSuchAlgorithmException, NoSuchPaddingException, Exception
{
	Cipher typeAESCipher = Cipher.getInstance("AES");
	typeAESCipher.init(Cipher.DECRYPT_MODE, secKey);
	byte[] bytePlainText = typeAESCipher.doFinal(dbPasswordText);
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


private static byte[] encryptText(String passwordBytes, SecretKey secKey) throws NoSuchAlgorithmException, NoSuchPaddingException, Exception
{
	Cipher typeAESCipher = Cipher.getInstance("AES");
	typeAESCipher.init(Cipher.ENCRYPT_MODE, secKey);
	byte[] CipherText = typeAESCipher.doFinal(passwordBytes.getBytes());
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
