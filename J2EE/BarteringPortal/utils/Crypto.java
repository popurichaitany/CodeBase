package utils;

import org.apache.commons.codec.binary.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Java program to encode and decode String in Java using Base64 encoding algorithm
 * @author http://javarevisited.blogspot.com
 */
public class Crypto{

    public static void main(String args[])  {
       try{
    	
    	String orig = "bartersystem";
    	 
      
        System.out.println("Original String: " + orig );
        
        String encoded = encode(orig);
        System.out.println("Base64 Encoded String : " + encoded);
        String decoded = decode(encoded);
        System.out.println("Base64 Encoded String : " + decoded);
        //decoding byte array into base64
       }catch(Exception e){
    	   e.printStackTrace();
       }
    }
    
    public static String encode(String inp){
    	String result = null;
    	byte[] keyBytes = new byte[]{13, 0, 3, 9, 4, 6, 7, 1, 8, 15, 10, 11, 12, 5, 14, 2};
        
    	try{
    	SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, key);
        byte[] inputBytes = inp.getBytes();
        
        //encoding  byte array into base 64
        byte[] encoded = Base64.encodeBase64(inputBytes);     
      
        result=new String(encoded);
    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public static String decode(String inp){
    	String result = null;
    	byte[] keyBytes = new byte[]{13, 0, 3, 9, 4, 6, 7, 1, 8, 15, 10, 11, 12, 5, 14, 2};
        
    	try{
    	SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, key);
        byte[] inputBytes = inp.getBytes();
        
        //encoding  byte array into base 64
        byte[] decoded = Base64.decodeBase64(inputBytes);      
       
        result=new String(decoded);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return result;
    	
    }
}
