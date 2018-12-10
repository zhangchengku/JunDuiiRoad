package com.mobo.zggkgisandroid.WebModel;

import java.util.List;

public class MapStaticsModel extends BaseResult {

	private List<ResultsBean> results;

	public List<ResultsBean> getResults() {
		return results;
	}

	public void setResults(List<ResultsBean> results) {
		this.results = results;
	}

	public static class ResultsBean {
		private String typelist;
		/**
		 * code : S7211 guid : S7211350000 mail : 2.403 name : S7211 漳永高速 type :
		 * 省道
		 */

		private List<DatalistBean> datalist;

		public String getTypelist() {
			return typelist;
		}

		public void setTypelist(String typelist) {
			this.typelist = typelist;
		}

		public List<DatalistBean> getDatalist() {
			return datalist;
		}

		public void setDatalist(List<DatalistBean> datalist) {
			this.datalist = datalist;
		}

		public static class DatalistBean {
			private String code;
			private String guid;
			private String mail;
			private String name;
			private String type;

			public String getCode() {
				return code;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public String getGuid() {
				return guid;
			}

			public void setGuid(String guid) {
				this.guid = guid;
			}

			public String getMail() {
				return mail;
			}

			public void setMail(String mail) {
				this.mail = mail;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}
		}
	}
}
