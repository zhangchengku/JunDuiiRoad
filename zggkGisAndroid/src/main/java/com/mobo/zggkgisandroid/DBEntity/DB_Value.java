package com.mobo.zggkgisandroid.DBEntity;

import com.j256.ormlite.field.DatabaseField;

/**
 * 键值实体类
 * 
 * @author wjm
 */
public class DB_Value {
	public DB_Value() {

	}

	// 设置id[见api]
	@DatabaseField
	String dic_code;// 健
	@DatabaseField
	String dic_desc;// 值
	@DatabaseField
	String dic_type;// 分类 键值集合的分组
	@DatabaseField
	String dic_order;// 排序顺序 本地数据库排序转intger

	public String getDic_type() {
		return dic_type;
	}

	public void setDic_type(String dic_type) {
		this.dic_type = dic_type;
	}

	public String getDic_order() {
		return dic_order;
	}

	public void setDic_order(String dic_order) {
		this.dic_order = dic_order;
	}

	public String getDic_code() {
		return dic_code;
	}

	public void setDic_code(String dic_code) {
		this.dic_code = dic_code;
	}

	public String getDic_desc() {
		return dic_desc;
	}

	public void setDic_desc(String dic_desc) {
		this.dic_desc = dic_desc;
	}
}
