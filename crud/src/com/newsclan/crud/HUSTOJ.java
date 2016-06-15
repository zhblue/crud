package com.newsclan.crud;

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
		
	}

	public static boolean checkPassword(String hash, String pass) {
		try {
			String salt = getSalt(hash);
			byte[] sha1 = Tools.computeSha1OfString(Tools.Md5(pass) + salt);
			return hash.equals(getHash(pass,salt));
		} catch (Exception e) {
			return false;
		}
	}

	public static String getSalt(String hash)  {
		String s = null;
		try {
			s = new String(Tools.decodeBase64(hash));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String salt = s.substring(s.length()-4);
		return salt;
	}
	public static String getHash(String pass,String salt){
		byte[] sha1 = Tools.computeSha1OfString(Tools.Md5(pass) + salt);
		try {
			return Tools.encodeBase64(Tools.concat(sha1,
					salt.getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
