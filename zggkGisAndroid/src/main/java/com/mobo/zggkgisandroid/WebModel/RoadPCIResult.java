package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;

import com.mobo.zggkgisandroid.ClassfyInfo.PCIInfo;

/**
 * 公路PCI类
 * 
 */
public class RoadPCIResult extends BaseResult implements Serializable {

	private PCIInfo results;

	public PCIInfo getResults() {
		return results;
	}

	public void setResults(PCIInfo results) {
		this.results = results;
	}

}
