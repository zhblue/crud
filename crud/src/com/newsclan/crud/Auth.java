package com.newsclan.crud;

public class Auth {
	public static boolean isAdmin(int user_id) {
		if( user_id == 1) return true;
		return checkPrivilegeForRightOfTable(user_id,"","admin");
	}
	public static boolean checkPrivilegeForRightOfTable(int user_id, String tbname,
			String right) {
		if(user_id>1000&&"student,news".contains(tbname)&&"insert,update".contains(right)) return true;
		String sql="select `right` from privilege where `user_id`=? and `right`=?";
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

	public static boolean canDeleteTable(int user_id, String tbname) {
		if (isAdmin(user_id))
			return true;
		return checkPrivilegeForRightOfTable(user_id, tbname, "delete");
	}

	public static boolean canInsertField(int user_id, String tbname,
			String fdname) {
		if (isAdmin(user_id))
			return true;
		return true;
	}

	public static boolean canReadField(int user_id, String tbname, String fdname) {
		if (isAdmin(user_id))
			return true;
		return true;
	}

	public static boolean canUpdateField(int user_id, String tbname,
			String fdname) {
		if (isAdmin(user_id))
			return true;
		return true;
	}

	public static boolean canDeleteField(int user_id, String tbname,
			String fdname) {
		if (isAdmin(user_id))
			return true;
		return true;
	}

}
