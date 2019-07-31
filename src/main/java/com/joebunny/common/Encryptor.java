package com.joebunny.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 不可逆加密器（MD5/SHA）
 */
public final class Encryptor {

	/**
	 * md5加密
	 */
	public final static String md5Encrypt(String inputText) {
		return encrypt(inputText, "MD5");
	}

	/**
	 * sha加密
	 */
	public final static String shaEncrypt(String inputText) {
		return encrypt(inputText, "SHA-1");
	}

    /**
     * 二次加密
     */
    public final static String md5AndSha(String inputText) {
        return shaEncrypt(md5Encrypt(inputText));
    }

	private final static String encrypt(String inputText, String algorithm) {
		if(inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("Input text is null!");
		}
		if(algorithm == null || "".equals(algorithm.trim())) {
			algorithm = "MD5";
		}
		String encryptText = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(inputText.getBytes("UTF8"));
			byte[] bytes = md.digest();
			return hex(bytes);
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	/**
	 * 返回十六进制字符串
	 */
	private final static String hex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<bytes.length; ++i) {
			sb.append(Integer.toHexString((bytes[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
	
}