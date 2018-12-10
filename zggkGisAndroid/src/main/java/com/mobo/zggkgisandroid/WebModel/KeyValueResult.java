package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

import com.mobo.zggkgisandroid.DBEntity.DB_Value;

/**
 * 键值列表类
 * 
 */
public class KeyValueResult extends BaseResult {

	private ArrayList<Res> results;// 返回信息

	public ArrayList<Res> getResults() {
		return results;
	}

	public void setResults(ArrayList<Res> results) {
		this.results = results;
	}

	/**
	 * 返回信息类
	 * 
	 */
	public class Res {
		private String res_md5;// 最新md5值
		private Value res_value;// 键值集合

		public String getRes_md5() {
			return res_md5;
		}

		public void setRes_md5(String res_md5) {
			this.res_md5 = res_md5;
		}

		public Value getRes_value() {
			return res_value;
		}

		public void setRes_value(Value res_value) {
			this.res_value = res_value;
		}

		/**
		 * 键值集合类
		 * 
		 */
		public class Value {
			private ArrayList<DB_Value> res_value;// 键值集合

			public ArrayList<DB_Value> getRes_value() {
				return res_value;
			}

			public void setRes_value(ArrayList<DB_Value> res_value) {
				this.res_value = res_value;
			}

		}
	}

}
