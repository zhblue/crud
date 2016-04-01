package com.newsclan.crud;

public class Field {

	public String label;
	public int type;
	public String name;
	public String table;
	public String transview; //transview 可能的变型，#占位原列名,如:from_unixtime(#/1000) 把JavaDate毫秒数转换成日期类型
	public String ftable;//外键不规则命名时，可以指定外键所在的表
	
}
