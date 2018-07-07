package com.newsclan.crud;

public class Auth {
	public static boolean isAdmin(int user_id) {
		if( user_id == 1) return true;
		if(Config.get("login.check").equalsIgnoreCase("false")) return true;
		return checkPrivilegeForRightOfTable(user_id,"","admin");
	}
	public static boolean checkPrivilegeForRightOfTable(int user_id, String tbname,
			String right) {
		
		
		if(Config.get("login.check").equalsIgnoreCase("false")) return true;
		
		if("t_student_class".equals(tbname)&&"insert".equals(right)) {
			Long selected=(Long)DAO.queryObject("select count(1) from t_student_class where input_user=?", user_id);
			if (selected>=Long.parseLong(Config.get("student.class.max"))) return false;
		}
		
		
		if(Config.get("privilege.auto")!=null&&
				Config.get("privilege.auto").contains(String.format("[%s]%s", tbname,right))) return true;
		
		String sql="select `right` from "+Config.sysPrefix+"privilege where `user_id`=? and `right`=?";
		
		String ret=DAO.queryString(sql, String.valueOf(user_id),String.format("[%s]%s", tbname,right));
		
		return ret!=null;
	}
	public static boolean canInsertTable(int user_id, String tbname) {

		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, tbname, "insert");
	}

	public static boolean canReadTable(int user_id, String tbname) {
		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, tbname, "read");
	}

	

	public static boolean canUpdateTable(int user_id, String tbname) {
		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, tbname, "update");
	}
	public static boolean canUpdateTableById(int user_id, String tbname,int id) {
		
		if( canUpdateTable(user_id, tbname) )return true;
		
		if("t_student_class".equals(tbname)) {
			Long selected=(Long)DAO.queryObject("select count(1) from t_student_class where id=? and input_user=?",id, user_id);
			if (selected ==1) return true;
		}
		
		return false;
	}
	public static boolean canUploadFile(int user_id) {
		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, "", "upload");
	}
	
	public static boolean canDeleteTableById(int user_id, String tbname,int id) {
		if (canDeleteTable(user_id, tbname)) return true;
		
		if("t_student_class".equals(tbname)) {
			Long selected=(Long)DAO.queryObject("select count(1) from t_student_class where id=? and input_user=?",id, user_id);
			if (selected ==1) return true;
		}
		
		return false;
	}
	public static boolean canDeleteTable(int user_id, String tbname) {
		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, tbname, "delete");
	}

	public static boolean canInsertField(int user_id, String tbname,
			String fdname) {
		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, tbname+"."+fdname, "insert");
	}

	public static boolean canReadField(int user_id, String tbname, String fdname) {
		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, tbname+"."+fdname, "read");
	}

	public static boolean canUpdateField(int user_id, String tbname,
			String fdname) {
		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, tbname+"."+fdname, "update");
	}

	public static boolean canDeleteField(int user_id, String tbname,
			String fdname) {
		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, tbname+"."+fdname, "delete");
	}

}
