/**
 * 
 */
package com.perso.proj.services.servinterface;

/**
 * @author deghislain
 *
 */
public interface IEncryptionService {
	 //This method encrypt a given string using 2 keys as described in Caesar cipher 
	 String encrypt(String toEncrypt, int key1, int key2);
	 
	//This method decrypt a given string using 2 keys as described in Caesar cipher 
	 String decrypt(String toDecrypt, int key1, int key2);
}
