package com.newsclan.crud;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

public class Tools {
	public static void log(String msg) {
		System.out.println(msg);
	}
	public static String toSelect(String tbname){
		StringBuffer ret=new StringBuffer();
		String nameFD=JspGenerator.getFirstCharFieldName(tbname);
		String sql="select id,"+nameFD+" from `"+tbname+"`";
		List<List> data=DAO.queryList(sql,false);
		ret.append("<select");
		ret.append(" name='");
		ret.append(tbname);
		ret.append("'>");
		
		for(List row:data){
			ret.append("<option value='");
			ret.append(row.get(0));
			ret.append("'>");
			ret.append(row.get(1));
			ret.append("</option>");
		}
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
			boolean dbid=true;
			if (title) {
				ret.append("<thead>");
			} 
			ret.append("<tr>");
			for (Object object : row) {
				if (title) {
					ret.append("<th>");
				} else {
					ret.append("<td");
					if(dbid){
						ret.append(" class='dbid'");
						dbid=false;
					}else{
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
			
			ret.append("</tr>");
			if (title) {
				ret.append("</thead>");
				ret.append("<tbody>");
			} 
			title=false;
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

}
