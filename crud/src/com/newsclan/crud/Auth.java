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
	public static boolean canUploadFile(int user_id) {
		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, "", "upload");
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
