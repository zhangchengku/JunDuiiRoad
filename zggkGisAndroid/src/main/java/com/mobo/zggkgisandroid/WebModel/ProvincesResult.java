package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

import com.mobo.zggkgisandroid.DBEntity.Province;

/**
 * 全国省份类
 * 
 */

public class ProvincesResult extends BaseResult {

	private ArrayList<Province> results;

	public ArrayList<Province> getResults() {
		return results;
	}

	public void setResults(ArrayList<Province> results) {
		this.results = results;
	}

}
