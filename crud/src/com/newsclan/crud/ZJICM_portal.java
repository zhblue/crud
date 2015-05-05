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
		String ret=plainText;
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
			ret=buf.toString();
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
        params.put("yhdlb.mm",Md5(user_pass));
        params.put("checkCode", "");
        params.put("yhlx", "3");
        params.put("subnum", "0");

        // TODO Auto-generated method stub
        String ret = http("http://portal.zjicm.edu.cn/tysfrz_qt/front/yhdlAction_dl.htm", params);
        System.out.println(ret);
        return ret.contains("url");
	}

   
    public static String http(String url, Map<String, String> params) {

        URL u = null;

        HttpURLConnection con = null;

        StringBuffer sb = new StringBuffer();

        if (params != null) {

            for (Entry<String, String> e : params.entrySet()) {

                sb.append(e.getKey());

                sb.append("=");

                sb.append(e.getValue());

                sb.append("&");

            }

            sb.substring(0, sb.length() - 1);

        }

        System.out.println("send_url:" + url);

        System.out.println("send_data:" + sb.toString());

        // 尝试发送请求

        try {

            u = new URL(url);

            con = (HttpURLConnection) u.openConnection();

            con.setRequestMethod("POST");

            con.setDoOutput(true);

            con.setDoInput(true);

            con.setUseCaches(false);

            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            OutputStreamWriter osw = new OutputStreamWriter(
                    con.getOutputStream(), "UTF-8");

            osw.write(sb.toString());

            osw.flush();

            osw.close();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (con != null) {
            	try{
            		con.disconnect();
            	}finally{}

            }

        }

        // 读取返回内容

        StringBuffer buffer = new StringBuffer();

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(con

            .getInputStream(), "UTF-8"));

            String temp;

            while ((temp = br.readLine()) != null) {

                buffer.append(temp);

                buffer.append("\n");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return buffer.toString();

    }

}