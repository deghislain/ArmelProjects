/**
 * 
 */
package com.perso.proj.localcomp;

/**
 * @author deghislain
 *
 */
public class Encrypter {
	private String alphabet;
    private String shiftedAlphabet;
   // private int mainKey;
    public Encrypter() { }
    public Encrypter(int key) {
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!*@#$%&?-";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
       // mainKey = key;
    }
        
        
        public String encrypt(String input) {
            StringBuilder sb = new StringBuilder(input);

            for (int i = 0; i < sb.length(); i++) {
                int index = -1;
                char myChar = ' ';
                char c = sb.charAt(i);
                if (Character.isUpperCase(c))
                {
                    index = alphabet.indexOf(c);
                    if (index != -1)
                    {
                        myChar = shiftedAlphabet.charAt(index);
                        sb.setCharAt(i, myChar);
                    }
                }
                else
                {
                    index = alphabet.indexOf(Character.toUpperCase(c));
                    if (index != -1)
                    {
                        myChar = shiftedAlphabet.charAt(index);
                        myChar = Character.toLowerCase(myChar);
                        sb.setCharAt(i, myChar);
                    }
                    
                }

                
            }

            return sb.toString();
        }

    
    
    
}
