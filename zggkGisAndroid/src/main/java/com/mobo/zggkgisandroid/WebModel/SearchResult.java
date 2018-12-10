package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;
import java.util.ArrayList;

import com.mobo.zggkgisandroid.ClassfyInfo.SearchInfo;

/**
 * 2.1搜索结果类
 * 
 */
public class SearchResult extends BaseResult implements Serializable{

	private ArrayList<SearchInfo> results;// 返回信息

	public ArrayList<SearchInfo> getResults() {
		return results;
	}

	public void setResults(ArrayList<SearchInfo> results) {
		this.results = results;
	}

}
