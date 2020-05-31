/**
 * 
 */
package com.perso.proj.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perso.proj.services.servimpl.EncryptionService;

/**
 * @author deghislain
 *
 */
public class TestEncryptionService {
	EncryptionService service;
	String toEncrypt;
	String encryptedStr;
	String decryptedStr;
	int k1;
	int k2;
	
	@BeforeEach
	public  void setUp() {
		service = new EncryptionService();
		toEncrypt = "Hello";
		encryptedStr = "";
		k1 = 12;
		k2 = 15;
	}
	
	@Test
	public void testEncrypt() {
		encryptedStr = service.encrypt(toEncrypt, k1, k2);
		
		System.out.println("encrypted string " + encryptedStr);
		
		assertNotNull(encryptedStr);
		
		assertNotEquals(encryptedStr, toEncrypt);
	}
	
	@Test
	public void testDecrypt() {
		encryptedStr = service.encrypt(toEncrypt, k1, k2);
		decryptedStr = service.decrypt(encryptedStr, k1, k2);
		
		System.out.println("decryptedStr string " + decryptedStr);
		
		assertNotNull(decryptedStr);
		
		assertNotEquals(encryptedStr, decryptedStr);
		
		assertEquals(toEncrypt, decryptedStr);
	}
}
