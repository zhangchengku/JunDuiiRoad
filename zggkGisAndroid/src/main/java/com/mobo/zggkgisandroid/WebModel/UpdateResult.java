package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

/***
 * 升级接口实体类
 * 
 * @author xushiwei
 * 
 */

public class UpdateResult extends BaseResult

{
	public ArrayList<Version> getResults() {
		return results;
	}

	public void setResults(ArrayList<Version> results) {
		this.results = results;
	}

	private ArrayList<Version> results;

	public class Version {

		private String up_version;// 最新版本号
		private String up_ismupdate;// 是否强制升级
		private String up_downurl;// 下载路径
		private String up_content;// 升级内容

		public String getUp_version() {
			return up_version;
		}

		public void setUp_version(String up_version) {
			this.up_version = up_version;
		}

		public String getUp_ismupdate() {
			return up_ismupdate;
		}

		public void setUp_ismupdate(String up_ismupdate) {
			this.up_ismupdate = up_ismupdate;
		}

		public String getUp_downurl() {
			return up_downurl;
		}

		public void setUp_downurl(String up_downurl) {
			this.up_downurl = up_downurl;
		}

		public String getUp_content() {
			return up_content;
		}

		public void setUp_content(String up_content) {
			this.up_content = up_content;
		}

	}
}
