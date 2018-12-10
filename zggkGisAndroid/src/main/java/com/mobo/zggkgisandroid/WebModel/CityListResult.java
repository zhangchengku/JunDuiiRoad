package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;
import java.util.ArrayList;

import com.j256.ormlite.field.DatabaseField;

/**
 * 5.3.城市列表
 * 
 * @author WuXiaoHao
 *
 */
public class CityListResult extends BaseResult implements Serializable {
	ArrayList<CityList> results;

	public ArrayList<CityList> getResults() {
		return results;
	}

	public void setResults(ArrayList<CityList> results) {
		this.results = results;
	}

}
