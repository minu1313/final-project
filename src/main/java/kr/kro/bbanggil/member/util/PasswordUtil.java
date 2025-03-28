package kr.kro.bbanggil.member.util;

import java.security.SecureRandom;

public class PasswordUtil {
	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
	private static final int PASSWORD_LENGTH = 10;

	public static String generateTempPassword() {
		SecureRandom random = new SecureRandom();
	    StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);

	    for (int i = 0; i < PASSWORD_LENGTH; i++) {
	    	sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
	    }
	        return sb.toString();
	    }
	
}
