package com.welink.myapp.util;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类
 * 
 * 非对称加密 -SHA1加密 ,MD5加密
 * 
 * 对称加密 -AES加密
 * 
 * 签名推荐MD5 用户密码推荐SHA 需双向加解密的用AES 调用方式参照main方法
 * @author rambo
 * 
 */

public class EncrypUtil {
	private final static String TAG="EncrypUtil";

//	public static void main(String[] args) {
//
//		String str = "冯建孟";
//
//		// Md5 16位加密
//		log.info(str + "经MD5-16位加密后为:"+ EncrypUtil.Md5Bit16Eccrypt(str));
//
//		// MD5 32位加密
//		log.info(str + "经MD5-32位加密后为:"+ EncrypUtil.Md5Bit32Eccrypt(str));
//
//		// SHA256加密
//		log.info(str + "经sha-256加密后为:" + EncrypUtil.ShaEccrypt(str));
//
//		String aeskey = "newfjm88";
//
//		// AES加密
//		log.info(str + "经AES加密后为:" + EncrypUtil.AesEncryp(str,aeskey));
//
//		// AES解密
//		log.info(str + "经AES解密后为:" + EncrypUtil.AesDecrypt(EncrypUtil.AesEncryp(str,aeskey),aeskey));
//	}

	/**
	 * SHA256加密,散列算法,不可逆
	 * 
	 * @param str
	 * @return
	 */
	public static String ShaEccrypt(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes("UTF-8"));
			String shaString="";
			for (byte b : md.digest())
			{
				shaString+= String.format("%02X", b);
			}
			return shaString.replace("0", "Z");
		} catch (Exception e) {
			Log.e(TAG,"【调用加密失败】:" + e.toString());
			return "";
		}
	}

	/**
	 * 生成32位的md5串
	 * 
	 * @param SourceString
	 * @return
	 */
	public static String Md5Bit32Eccrypt(String SourceString) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.update(SourceString.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG,"【调用加密失败】:"+e.toString());
			return "";
		}
	}

	/**
	 * 生成16位的md5串
	 * 
	 * @param SourceString
	 * @return
	 * @throws Exception
	 */
	public static String Md5Bit16Eccrypt(String SourceString) {
		return Md5Bit32Eccrypt(SourceString).substring(8, 24);
	}
	
	/**
	 * MD5私有生成方法
	 * 
	 * @param b
	 * @return
	 */
	private static String toHexString(byte[] b) {
		final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * AES加密
	 * @param str
	 * @param password
	 * @return
	 */
	public static String AesEncryp(String str, String password) {
		byte[] result = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = str.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			result = cipher.doFinal(byteContent);
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG,"【调用加密失败】:" + e.toString());
		} catch (NoSuchPaddingException e) {
			Log.e(TAG, "【调用加密失败】:" + e.toString());
		} catch (InvalidKeyException e) {
			Log.e(TAG, "【调用加密失败】:" + e.toString());
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "【调用加密失败】:" + e.toString());
		} catch (IllegalBlockSizeException e) {
			Log.e(TAG, "【调用加密失败】:" + e.toString());
		} catch (BadPaddingException e) {
			Log.e(TAG, "【调用加密失败】:" + e.toString());
		}
		return null == result?"":new String(Base64.encode(result, Base64.DEFAULT));
	}

	/**
	 * AES解密
	 * @param str
	 * @param password
	 * @return
	 */
	public static String AesDecrypt(String str, String password) {
		byte[] result = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(Base64.encode(result, Base64.DEFAULT));
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "【调用加密失败】:" + e.toString());
		} catch (NoSuchPaddingException e) {
			Log.e(TAG, "【调用加密失败】:" + e.toString());
		} catch (InvalidKeyException e) {
			Log.e(TAG, "【调用加密失败】:" + e.toString());
		} catch (IllegalBlockSizeException e) {
			Log.e(TAG, "【调用加密失败】:" + e.toString());
		} catch (BadPaddingException e) {
			Log.e(TAG, "【解密失败】:秘钥错误");
		}
		return null == result?"":new String(result);
	}

}
