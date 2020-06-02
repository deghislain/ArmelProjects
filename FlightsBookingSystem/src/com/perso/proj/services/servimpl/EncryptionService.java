/**
 * 
 */
package com.perso.proj.services.servimpl;

import com.perso.proj.localcomp.Encrypter;
import com.perso.proj.services.servinterface.IEncryptionService;

/**
 * @author deghislain
 *
 */
public class EncryptionService implements IEncryptionService{

	@Override
	public String encrypt(String toEncrypt, int key1, int key2) {
		return doubleKeysEncryption(toEncrypt, key1, key2);
	}

	@Override
	public String decrypt(String toDecrypt, int key1, int key2) {
		
		 //use the encryption method to decrypt the input 
        return doubleKeysEncryption(toDecrypt, 45 - key1, 45 - key2);
	}
	
	 //encrypt the given input using the 2 provided keys
    private String doubleKeysEncryption(String input, int key1, int key2)
    {
    	 StringBuilder sb = null;
    	if(input != null && !input.isEmpty()) {
    		String rightString = halfOfStrin(input, 0);
            String leftString = halfOfStrin(input, 1);
            Encrypter rEncr = new Encrypter(key1);
            Encrypter lEncr = new Encrypter(key2);
            sb = new StringBuilder(input);
            String encrRStr = rEncr.encrypt(rightString);
            String encrLStr = lEncr.encrypt(leftString);

            for (int i = 0, j = 0; j < sb.length(); j += 2) {
                if (i < encrRStr.length() && sb.charAt(j) != ' ') {
                    sb.setCharAt(j,encrRStr.charAt(i++));
                }
            }
            for (int i = 0, j = 1; j < sb.length(); j += 2)
            {
                if (i < encrLStr.length() && sb.charAt(j) != ' ')
                {
                    sb.setCharAt(j,encrLStr.charAt(i++));
                }
            }
    	}else {
    		sb = new StringBuilder("Input cannot be null or empty string");
    	}
        


        return sb.toString();
    }
    
    private String halfOfStrin(String input, int start)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < input.length(); i += 2)
        {
            if (input.charAt(i) != ' ')
            {
                sb.append(input.charAt(i));
            }
        }
        return sb.toString();
    }

}
