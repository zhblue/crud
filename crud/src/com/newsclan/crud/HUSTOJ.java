package com.newsclan.crud;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HUSTOJ {
	public static void main(String[] args) {
		String hash = "fhGmzU8zjlTLpemmMEsKsvQaA5MxMjM0";
		String pass = "123456";
//		try {
//			String s = new String(Tools.decodeBase64(hash));
//			System.out.println(Tools.toHexString(Tools.decodeBase64(hash)));
//			System.out.println(Tools.encodeBase64(Tools.deBase64(hash)
//					.getBytes()));
//			String salt = s.substring(19);
//			System.out.println(salt);
//			System.out.println(Tools.Md5(pass) + salt);
//			byte[] sha1 = Tools.computeSha1OfString(Tools.Md5(pass) + salt);
//			System.out.println(Tools.encodeBase64(sha1));
//			System.out.println(Tools.encodeBase64(Tools.concat(sha1,
//					salt.getBytes())));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println(checkPassword(hash, pass));
		System.out.println(getSalt(hash));
		System.out.println(getHash(pass, "1234"));
		System.out.println(getHash(pass));
		System.out.println(checkPassword(getHash(pass),pass));
		
	}

	public static boolean checkPassword(String hash, String pass) {
		try {
			String salt = getSalt(hash);
			return hash.equals(getHash(pass,salt));
		} catch (Exception e) {
			return false;
		}
	}

	public static String getSalt(String hash)  {
		String s = null;
		try {
			s = new String(decodeBase64(hash));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String salt = s.substring(s.length()-4);
		return salt;
	}
	public static String getHash(String password){
		String salt=Tools.getRandomSalt().substring(0,4);
		System.out.println(salt);
		return getHash(password,salt); 
	}
	public static String getHash(String pass,String salt){
		byte[] sha1 = computeSha1OfString(Tools.Md5(pass) + salt);
		try {
			return encodeBase64(concat(sha1,
					salt.getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static byte[] concat(byte[] sha1, byte[] salt) {
		// TODO Auto-generated method stub
		byte []ret=new byte[sha1.length+salt.length];
		for(int i=0;i<sha1.length;i++){
			ret[i]=sha1[i];
		}
		for(int i=0;i<salt.length;i++){
			ret[sha1.length+i]=salt[i];
		}
		return ret;
	}

	private static byte[] computeSha1OfByteArray(byte[] arg) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-1");
	        md.update(arg);
	        byte[] res = md.digest();
	        return (res);
	    } catch (NoSuchAlgorithmException ex) {
	        throw new UnsupportedOperationException(ex);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] computeSha1OfString(String arg) {
	    ByteBuffer buf = StandardCharsets.UTF_8.encode(CharBuffer.wrap(arg)); 
		byte[] utf8 = new byte[buf.limit()]; buf.get(utf8); 
		return computeSha1OfByteArray(utf8);
	}

	public static byte[] decodeBase64(String input) throws Exception{  
	    Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
	    Method mainMethod= clazz.getMethod("decode", String.class);  
	    mainMethod.setAccessible(true);  
	     Object retObj=mainMethod.invoke(null, input);  
	     return (byte[])retObj;  
	}

	private static String dump(byte[] bs) {
		// TODO Auto-generated method stub
		 StringBuilder sb=new StringBuilder();
		 for(byte b:bs){
			 sb.append(String.format("%x", b));
		 }
		return sb.toString();
	}

	public static String encodeBase64(byte[] v) throws Exception{  
	    Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
	    Method mainMethod= clazz.getMethod("encode", byte[].class);  
	    mainMethod.setAccessible(true);  
	     Object retObj=mainMethod.invoke(null, new Object[]{v});  
	     return (String)retObj;  
	}

	private static final String HEX_DIGITS = "0123456789abcdef";

	static String toHexString(byte[] v) {
	    StringBuilder sb = new StringBuilder(v.length * 2);
	    for (int i = 0; i < v.length; i++) {
	        int b = v[i] & 0xFF;
	        sb.append(HEX_DIGITS.charAt(b >>> 4)).append(HEX_DIGITS.charAt(b & 0xF));
	    }
	    return sb.toString();
	}
}
