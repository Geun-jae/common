package kr.utils.security;

import kr.utils.ObjectUtils;
import kr.utils.StringUtils;


public class Security {
	public static String encrypted(String key , String text) {
		String result = "";
		try {
			if (!ObjectUtils.isEmpty(text)) {
				SeedCipher seed = new SeedCipher();
				result = StringUtils.escape(Base64.encodeToString(seed.encrypt(text, key.getBytes(), "UTF-8")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public static String decryption(String key , String text) {
		String result = "";
		try {
			if (!ObjectUtils.isEmpty(text)) {
				SeedCipher seed = new SeedCipher();
				byte[] encryptbytes =  Base64.decode(StringUtils.unescape(text), Base64.DEFAULT);
				result = seed.decryptAsString(encryptbytes, key.getBytes(), "UTF-8");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}