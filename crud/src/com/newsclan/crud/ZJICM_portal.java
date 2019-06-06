package com.newsclan.crud;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

public class ZJICM_portal {

	/**
	 * @param args
	 */
	

	private static String Md5(String plainText) {
		String ret = plainText;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			ret = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return ret;
	}

	public static boolean login(String user_id, String user_pass) {
		Map<String, String> params;

		params = new HashMap<String, String>();
		params.put("yhdlb.yhm", user_id);
		params.put("yhdlb.mm", Md5(user_pass));
		params.put("checkCode", "");
		params.put("yhlx", "3");
		params.put("subnum", "0");

		// TODO Auto-generated method stub
		String ret = Tools.http("http://portal.zjicm.edu.cn/tysfrz_qt/front/yhdlAction_dl.htm", params);
		System.out.println(ret);
		return ret.contains("url");
	}

}