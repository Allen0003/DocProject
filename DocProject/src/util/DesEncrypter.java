package util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.IllegalBlockSizeException;

public class DesEncrypter {
	public static String encrypt(String strInput, String strKey) {
		try {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			int fillCount = 0;
			if (strInput.getBytes("UTF8").length % 8 != 0) {
				fillCount = 8 - (strInput.getBytes("UTF8").length % 8);
			}
			byte[] input = fillToken(strInput, ' ', fillCount, 'r').getBytes("UTF8");
			byte[] keyBytes = strKey.getBytes("UTF8");

			SecretKeySpec key = new SecretKeySpec(keyBytes, "TripleDES");
			Cipher cipher = Cipher.getInstance("TripleDES/ECB/NoPadding");

			// encryption pass
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
			int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
			ctLength += cipher.doFinal(cipherText, ctLength);
			return new sun.misc.BASE64Encoder().encode(cipherText);
		} catch (Exception e) {

		}
		return null;
	}

	public static String decrypt(String strData, String strKey) throws Exception {
		try {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			byte[] keyBytes = strKey.getBytes("UTF8");
			SecretKeySpec key = new SecretKeySpec(keyBytes, "TripleDES");
			Cipher cipher = Cipher.getInstance("TripleDES/ECB/NoPadding");

			// encryption pass
			cipher.init(Cipher.DECRYPT_MODE, key);

			// Decode base64 to get bytes
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(strData);

			// Decrypt
			byte[] cipherText = cipher.doFinal(dec);

			// Decode using utf-8

			return new String(cipherText, "UTF8");
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (java.io.IOException e) {
		}
		return null;
	}

	/**
	 * strInput:块﹃ strToken:饼恶干じ num:恶干じ计 position:恶干よ'r'干ㄤ緇オ干 return
	 * 恶干Ч﹃
	 */
	public static String fillToken(String strInput, char token, int num, char position) { // 干じ
		String strOutput = strInput;
		if (strInput != null && num >= 0) {
			for (int i = 0; i < num; i++) {
				if (position == 'r') {
					strOutput += token;
				} else {
					strOutput = token + strOutput;
				}
			}
		}
		return strOutput;
	}

	/**
	 * strInput:块﹃ token:饼恶干じ len:恶干じ return 恶干Ч﹃
	 */
	public String fillToken(String strInput, char token, int len) { // 干じ
		String strOutput = strInput;
		if (strInput != null && strInput.trim().length() < len) {
			int fillCount = (len - strInput.trim().length());
			for (int i = 0; i < fillCount; i++) {
				strOutput = token + strOutput;
			}
		}
		return strOutput;
	}

}
