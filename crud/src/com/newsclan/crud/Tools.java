package com.newsclan.crud;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Properties;
import java.util.Map.Entry;
import java.util.*;
import java.io.*;
import java.util.zip.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Tools {
	public static void log(String msg) {
		System.out.println(msg);
	}
     public static void ZipOneFile(String target, String source) {
		FileOutputStream outputStream = null;
		ZipOutputStream zipOutputStream = null;
		FileInputStream input = null;
		try {
			outputStream = new FileOutputStream(target);
			zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
			input = new FileInputStream(source);

			zipOutputStream.putNextEntry(new ZipEntry(source));

			int readLen = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
				zipOutputStream.write(buffer, 0, readLen);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (zipOutputStream != null)
				zipOutputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (input != null)
				input.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void ZipFiles(String target, String sources[]) {

		FileOutputStream outputStream = null;
		ZipOutputStream zipOutputStream = null;
		FileInputStream input = null;
		try {
			outputStream = new FileOutputStream(target);
			zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));

			for (String source : sources) {

				try {
					input = new FileInputStream(source);
					zipOutputStream.putNextEntry(new ZipEntry(source));
					int readLen = 0;
					byte[] buffer = new byte[1 << 20];
					while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
						zipOutputStream.write(buffer, 0, readLen);
					}
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			zipOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (zipOutputStream != null)
				zipOutputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (input != null)
				input.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String http_taobao(String url, String referer) {
		// TODO Auto-generated method stub
		if (referer == null)
			referer = url;
		URL u = null;

		HttpURLConnection con = null;

		StringBuffer sb = new StringBuffer();

		// System.out.println("send_url:" + url);
		//
		// System.out.println("send_data:" + sb.toString());

		// 尝试发送请求

		try {

			u = new URL(url);

			con = (HttpURLConnection) u.openConnection();

			con.setRequestMethod("GET");

			con.setDoOutput(true);

			// con.setDoInput(true);

			con.setUseCaches(false);

			// con.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			// con.setRequestProperty("Authority", "item.taobao.com");
			// con.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
			// con.setRequestProperty("Accept-encoding","gzip, deflate, br");
			// con.setRequestProperty("User-agent","Mozilla/5.0 (Windows NT
			// 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)
			// Chrome/74.0.3729.169 Safari/537.36");

			con.setRequestProperty("authority", "item.taobao.com");
			con.setRequestProperty("cache-control", "max-age=0");
			con.setRequestProperty("upgrade-insecure-requests", "0");
			con.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
			con.setRequestProperty("dnt", "1");
			con.setRequestProperty("referer", referer);
			con.setRequestProperty("accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
			con.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");

			// OutputStreamWriter osw = new
			// OutputStreamWriter(con.getOutputStream(), "UTF-8");
			//
			// osw.write(sb.toString());
			//
			// osw.flush();
			//
			// osw.close();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (con != null) {
				try {
					con.disconnect();
				} finally {
				}

			}

		}

		// 读取返回内容

		StringBuffer buffer = new StringBuffer();

		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(con

					.getInputStream(), "GBK"));

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

			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");

			osw.write(sb.toString());

			osw.flush();

			osw.close();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (con != null) {
				try {
					con.disconnect();
				} finally {
				}

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

	public static void mail(String who, String title, String content) {
		Properties properties = Config.prop;
		// 得到回话对象
		Session session = Session.getInstance(properties);
		// 获取邮件对象
		Message message = new MimeMessage(session);
		// 设置发件人邮箱地址
		try {
			message.setFrom(new InternetAddress(Config.get("mail.account")));
			message.setRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(who) });
			// message.setRecipient(Message.RecipientType.TO, new
			// InternetAddress("xxx@qq.com"));//一个收件人
			// 设置邮件标题
			message.setSubject(title);
			// 设置邮件内容
			message.setText(content);
			// 得到邮差对象
			try (Transport transport = session.getTransport();) {
				// 连接自己的邮箱账户
				transport.connect(Config.get("mail.account"), Config.get("mail.password"));// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
				// 发送邮件
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String update(HttpServletRequest request) {
		Long id = Long.parseLong(request.getParameter("id"));
		String tbname = request.getParameter("tbname").replace("`", "");
		String fdname = request.getParameter("fdname").replace("`", "");
		String value = request.getParameter("value");
		String sql = "update `" + tbname + "` set `" + fdname + "`=? where " + DAO.getPrimaryKeyFieldName(tbname)
				+ "=?";
		Integer user_id = (Integer) request.getSession().getAttribute("user_id");
		if (Auth.canUpdateTable(user_id, tbname) || Auth.canUpdateField(user_id, tbname, fdname)) {
			DAO.update(sql, value, id);
		}
		return "reload();";
	}

	public static String cleanChars(String in) {
		String out = in.replace(" ", "");
		out = out.replace("#", "");
		out = out.replace("\n", "");
		out = out.replace("\r", "");
		out = out.replace("\\", "");
		out = out.replace("/", "");
		return out;
	}

	public static void importXLS(String path) {
		System.out.println(path);
		try {
			// 打开文件
			Workbook book = Workbook.getWorkbook(new File(path));
			// 取得第一个sheet
			Sheet sheet = book.getSheet(0);
			String tbname = sheet.getName().trim();
			tbname = cleanChars(tbname);

			int rows = sheet.getRows();

			if (!DAO.hasTable(tbname)) {
				System.out.println("creating table " + tbname);
				Cell[] cell = sheet.getRow(0);
				addTable(sheet);
			}
			// 取得行数
			StringBuilder sql = new StringBuilder("insert into `");
			StringBuilder values = new StringBuilder("(");
			sql.append(tbname);
			sql.append("`(");
			String pk = DAO.getPrimaryKeyFieldName(tbname);

			Field[] fds = DAO.getFieldsOfTable(tbname);
			for (Field field : fds) {
				if (field.name.equals(pk))
					continue;
				sql.append("`");
				sql.append(field.name);
				sql.append("`");
				sql.append(",");
				values.append("?,");
			}
			sql.deleteCharAt(sql.length() - 1);
			values.deleteCharAt(values.length() - 1);
			sql.append(") values ");
			values.append(")");
			// System.out.println(rows);
			StringBuilder sb = new StringBuilder();
			String insert = sql.toString() + values.toString();
			System.out.println(insert);
			for (int i = 1; i < rows; i++) {
				Cell[] cell = sheet.getRow(i);

				String[] data = new String[fds.length - 1];
				for (int j = 0; j < data.length; j++) {
					if (cell.length > j) {
						data[j] = (cell[j].getContents());
					} else {
						if (DAO.isFieldNumber(fds[j + 1])) {
							data[j] = "0";
						} else if (DAO.isFieldDate(fds[j + 1])) {
							data[j] = null;
						} else {
							data[j] = "";
						}
					}

				}
				DAO.executeUpdate(insert, data);
			}
			// 关闭文件
			book.close();

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean login(String username, String password) {
		boolean ret = false;
		Connection conn = DB.getConnection();
		String sql = "select password from " + Config.sysPrefix + "user where name=? ";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String dbhash = rs.getString("password");
				String salt = dbhash.substring(32);
				String thishash = getHash(password, salt);
				ret = thishash.equals(dbhash);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB.close(rs);
		DB.close(pstmt);
		DB.close(conn);
		return ret;
	}

	public static boolean login_zfc(String username, String password) {
		boolean ret = false;
		Connection conn = DB.getConnection();
		String sql = "select cardid from " + Config.sysPrefix + "user where name=? ";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (password.length() == 6 && rs.getString(1).endsWith(password)) {
					ret = true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB.close(rs);
		DB.close(pstmt);
		DB.close(conn);
		return ret;
	}

	public static int getUserId(HttpSession session) {
		Integer user_id = (Integer) session.getAttribute("user_id");
		if (user_id == null) {
			if (Config.loginCheck) {
				user_id = 0; // guest
			} else {
				user_id = 1;// admin
				session.setAttribute("user_id", 1);
				session.setAttribute("user_name", "admin");
			}
		}
		return user_id;
	}

	public static String getHash(String origin, String salt) {
		return Md5(origin + salt) + salt;
	}

	public static String getRandomSalt() {
		return Md5(new Date().toString()).substring(0, 8);
	}

	public static String Md5(String plainText) {
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

	public static int getRequestInt(HttpServletRequest req, String paraName) {
		int ret = 0;
		try {
			ret = Integer.parseInt(req.getParameter(paraName));
		} catch (Exception e) {
		}
		return ret;

	}

	private static void addTable(Sheet sheet) {
		// TODO Auto-generated method stub
		String tbname = cleanChars(sheet.getName());
		Cell[] cell = sheet.getRow(0);
		// String fd_names[] = new String[cell.length];
		String fd_types[] = new String[cell.length];
		String fd_titles[] = new String[cell.length];
		for (int i = 0; i < cell.length; i++) {
			fd_titles[i] = cleanChars(cell[i].getContents().trim());
			fd_types[i] = guessType(sheet, i);
			// fd_names[i]=tbname+"_fd_"+i;
		}
		addTable(tbname, tbname, fd_titles, fd_types, fd_titles);
	}

	private static String guessType(Sheet sheet, int i) {
		// TODO Auto-generated method stub
		int rows = sheet.getRows();
		int max_length = 16;
		int precision = 0;
		boolean canBeDecimal = true;
		boolean canBeInt = true;
		boolean canBeDate = true;
		for (int j = 1; j < rows; j++) {
			Cell cell[] = sheet.getRow(j);
			try {
				String data = cell[i].getContents().trim();
				if (max_length < data.length()) {
					max_length = data.length();
				}
				if (canBeInt) {
					if (!isInt(data)) {
						canBeInt = false;
					}
				}
				if (canBeDate) {
					if (!isDate(data)) {
						canBeDate = false;
					}
				}

				if (canBeDecimal) {
					if (precision < getPrecision(data))
						precision = getPrecision(data);
					if (!isDecimal(data, precision)) {
						canBeDecimal = false;
					}
				}
			} catch (Exception e) {
				continue;
			}

		}
		if (canBeDate) {
			return String.format("date");
		}
		if (canBeInt) {
			return String.format("bigint(%d)", max_length > 20 ? max_length + 2 : 20);
		}
		if (canBeDecimal) {
			return String.format("decimal(20,%d)", precision);
		}
		if (max_length < 255) {
			return String.format("varchar(%d)", max_length);
		}
		return "text";
	}

	private static boolean isDate(String data) {
		// TODO Auto-generated method stub
		String split[];
		if(data.contains("/")){
			split=data.split("/");
			if(split.length == 3){
				if(split[0].length()==4 || split[0].length()==2){
					if(split[1].length()==2 || split[1].length()==1){
						return split[2].length()==2 || split[2].length()==1;
					}
				}
			}
		}else if(data.contains("-")){
			split=data.split("-");
			if(split.length == 3){
				if(split[0].length()==4 || split[0].length()==2){
					if(split[1].length()==2 || split[1].length()==1){
						return split[2].length()==2 || split[2].length()==1;
					}
				}
			}
		}else{
			return isInt(data) && data.length()== 8;
		}
		return false;
	}

	private static int getPrecision(String data) {
		// TODO Auto-generated method stub
		int point = data.indexOf('.');
		if (point >= 0)
			return data.length() - point - 1;

		return 0;
	}

	private static boolean isDecimal(String data, int precision) {
		// TODO Auto-generated method stub
		try {
			double d = Double.parseDouble(data);
			if (data.startsWith("00"))
				return false;
			if (Double.parseDouble(String.format("%." + precision + "f", d)) == d)
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	private static boolean isInt(String data) {
		// TODO Auto-generated method stub
		try {
			if (String.valueOf(Long.parseLong(data)).equals(data))
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static void addTable(String tb_name, String tb_title, String[] fd_names, String[] fd_types,
			String[] fd_titles) {
		StringBuffer sql = new StringBuffer("create table " + tb_name + "(");
		sql.append("id bigint(20) unsigned NOT NULL auto_increment");

		String dict = "INSERT INTO `" + Config.sysPrefix + "datadic` (`field`,`name`) VALUES (?,?)";

		DAO.executeUpdate(dict, new String[] { tb_name, tb_title });

		for (int i = 0; i < fd_names.length; i++) {
			if ("id".equalsIgnoreCase(fd_names[i]))
				continue;
			fd_names[i] = filteSQL(fd_names[i]);
			fd_titles[i] = filteSQL(fd_titles[i]);
			String[] values = { fd_names[i], fd_titles[i] };
			if (!"".equals(fd_titles[i])) {
				DAO.executeUpdate(dict, values);
			}
			sql.append(",`" + fd_names[i] + "` " + fd_types[i] + "");

		}
		sql.append(",PRIMARY KEY  (`id`)) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;");
		DAO.executeUpdate(sql.toString(), new String[] {});

	}

	private static String filteSQL(String sql) {
		// TODO Auto-generated method stub
		if (sql == null)
			return null;
		return sql.replace("'", "\\'");
	}

	public static String toSelect(String tbname, String value, String... keys) {
		StringBuilder ret = new StringBuilder();
		StringBuilder options = new StringBuilder();
		String nameFD = "`" + DAO.getFirstCharFieldName(tbname) + "`";
		String input_name = filteSQL(keys[2]);
		String transview = DAO.transview(input_name);
		if (transview != null)
			nameFD = transview;
		String pk = DAO.getPrimaryKeyFieldName(tbname);
		String sql = "select `" + pk + "`," + (nameFD) + " from `" + filteSQL(tbname) + "`";
		boolean noDefault = true;
		if (keys.length >= 2) {
			if (Config.debug)
				System.out.println(keys[0]);
			Field[] fds = DAO.getFieldsOfTable(tbname);
			for (Field fd : fds) {
				if (fd.name.equals(keys[0])) {
					sql += " where " + filteSQL(keys[0]) + "=" + filteSQL(keys[1]);
					break;
				}
				if (Config.debug)
					System.out.println(fd.name);
			}
		}
		sql += " order by `" + pk + "` desc";

		List<List> data = DAO.queryList(sql, false);
		ret.append("<select");
		ret.append(" name='");
		if (null == input_name) {
			input_name = tbname + "_id";
		}
		if ("pcdata_id".equals(input_name)) {
			input_name = "批次uid";
		}
		System.err.println(sql);
		ret.append(input_name);
		ret.append("' onChange='filteNext(this);' onLoad='filteNext(this);'>\n");

		for (List row : data) {
			options.append("<option value='");
			options.append(row.get(0));
			options.append("'");
			if (String.valueOf(row.get(0)).equals(value)) {
				options.append(" selected");
				noDefault = false;
			}
			options.append(">");
			options.append(row.get(1));
			options.append("</option>\n");
		}
		if (noDefault) {
			ret.append("<option value='-1'>请选择</option>\n");
		}
		ret.append(options.toString());
		ret.append("</select>");
		return ret.toString();

	}

	public static String toHTML(String text) {
		return StringEscapeUtils.escapeHtml4(text);
	}

	public static String toTable(List<List> list, String... css) {
		StringBuffer ret = new StringBuffer();
		ret.append("<table id='dataForm' class='");
		for (String css1 : css) {
			ret.append(css1);
		}
		ret.append("'>");
		boolean title = true;

		for (List row : list) {
			boolean dbid = true;
			if (title) {
				ret.append("<thead>");
			}
			ret.append("<tr id='" + String.valueOf(row.get(0)) + "'>");
			for (Object object : row) {
				if (title) {
					ret.append("<th>");
				} else {
					ret.append("<td");
					if (dbid) {
						ret.append(" class='dbid'");
						dbid = false;
					} else {
						ret.append(" class='dbdata'");
					}
					ret.append(">");
				}
				ret.append(String.valueOf(object));
				if (title) {
					ret.append("</th>");
				} else {
					ret.append("</td>");
				}

			}

			ret.append("</tr>\n");
			if (title) {
				ret.append("</thead>\n");
				ret.append("<tbody>\n");
			}
			title = false;
		}
		ret.append("</tbody></table>");
		return ret.toString();
	}

	public static String shortString(String s, int len) {
		if (s != null) {
			if (s.length() > len && len > 0) {
				s = s.substring(0, len);
				if (len > 6) {
					s = s.substring(0, len - 3) + "...";
				}
			}
		} else {
			s = "";
		}
		return s;
	}

	public static String tomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, +1);
		// cal.set(cal.DATE, cal.getActualMaximum(cal.DATE));
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	public static String lastMonthFirstDay() {

		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONTH, -1);
		cal.set(cal.DATE, 1);
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	public static String lastMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONTH, -1);
		cal.set(cal.DATE, cal.getActualMaximum(cal.DATE));
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	public static String today() {
		Calendar cal = Calendar.getInstance();
		// cal.set(cal.DATE, cal.getActualMaximum(cal.DATE));
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	public static void debug(String msg) {
		// TODO Auto-generated method stub
		if (Config.debug)
			System.out.println(msg);
	}

	public static String http(String url) {
		// TODO Auto-generated method stub
		URL u = null;

		HttpURLConnection con = null;

		// 尝试发送请求

		try {

			u = new URL(url);

			con = (HttpURLConnection) u.openConnection();

			con.setRequestMethod("GET");

			con.setDoOutput(true);

			con.setDoInput(true);

			con.setUseCaches(false);

			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (con != null) {
				try {
					con.disconnect();
				} finally {
				}

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

	public static void main(String[] args) {
		String url = "https://item.taobao.com/item.htm?spm=a1z10.5-c-s.w4002-21768955287.13.7fdc6374rE4jwf&id=596049223621";
		Map data = getTaobao(url);
		System.out.println(data.toString());
	}

	public static Map getTaobao(String url) {
		Map ret = new HashMap();
		String html = http_taobao(url, null);
		Document doc = Jsoup.parse(html);

		String image = "http:" + (doc.getElementsByAttributeValue("id", "J_ImgBooth").attr("src"));
		String title = (doc.getElementsByAttributeValue("class", "tb-main-title").text());
		String price = (doc.getElementsByAttributeValue("class", "tb-rmb-num").text());
		String attributes = (doc.getElementsByAttributeValue("id", "attributes").html());
		int start_descUrl = html.indexOf("descUrl");
		start_descUrl = html.indexOf("//", start_descUrl);
		int end_descUrl = html.indexOf(":", start_descUrl) - 2;
		String descUrl = "https:" + html.substring(start_descUrl, end_descUrl);
		// System.out.println(descUrl);
		String desc = http_taobao(descUrl, url);
		desc = desc.substring(10, desc.length() - 2);
		String description = (desc);
		ret.put("image", image);
		ret.put("title", title);
		ret.put("price", new BigDecimal(price));
		ret.put("attributes", attributes);
		ret.put("description", description);

		return ret;
	}
}
