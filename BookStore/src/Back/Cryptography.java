package Back;

import java.math.BigInteger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Cryptography {
	public String hashPassword(String password, String username) {
		try {
			int iterations = 10000;
		    char[] chars = password.toCharArray();
		    byte[] salt = username.getBytes();

		    PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 512);
		    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		    byte[] hash = skf.generateSecret(spec).getEncoded();
		    return toHex(hash);
		}catch(Exception e) {
			System.out.println(e);
		}return "";
	}
	private String toHex(byte[] array)
	{
	    BigInteger bi = new BigInteger(1, array);
	    String hex = bi.toString(16);
	    
	    int paddingLength = (array.length * 2) - hex.length();
	    if(paddingLength > 0)
	    {
	        return String.format("%0"  +paddingLength + "d", 0) + hex;
	    }else{
	        return hex;
	    }
	}
}
