package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

/**
 * 搜索自动提示类
 * 
 */

public class SearchNoticesResult extends BaseResult {

	private ArrayList<SearchNotice> results;

	public ArrayList<SearchNotice> getResults() {
		return results;
	}

	public void setResults(ArrayList<SearchNotice> results) {
		this.results = results;
	}

	public class SearchNotice {
		private String search_str_notice;

		public String getSearch_str_notice() {
			return search_str_notice;
		}

		public void setSearch_str_notice(String search_str_notice) {
			this.search_str_notice = search_str_notice;
		}

	}

}
