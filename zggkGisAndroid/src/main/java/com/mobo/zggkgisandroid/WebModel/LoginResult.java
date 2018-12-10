package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

/**
 * 登录结果类
 * 
 */

public class LoginResult extends BaseResult {

	private ArrayList<LoginMess> results;// 返回信息

	public ArrayList<LoginMess> getResults() {
		return results;
	}

	public void setResults(ArrayList<LoginMess> results) {
		this.results = results;
	}

	/**
	 * 登录信息
	 * 
	 */
	public class LoginMess {
		private String user_id;// 用户id
		private String user_name;// 用户名称
		private String token;// 令牌
		private String privilege;// 权限
		private String belong_province_code;// 所属省份编码
		private String belong_province_name;// 所属省份名称

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getUser_name() {
			return user_name;
		}

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getPrivilege() {
			return privilege;
		}

		public void setPrivilege(String privilege) {
			this.privilege = privilege;
		}

		public String getBelong_province_code() {
			return belong_province_code;
		}

		public void setBelong_province_code(String belong_province_code) {
			this.belong_province_code = belong_province_code;
		}

		public String getBelong_province_name() {
			return belong_province_name;
		}

		public void setBelong_province_name(String belong_province_name) {
			this.belong_province_name = belong_province_name;
		}
	}

}
