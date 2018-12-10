package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;
import java.util.List;

public class BridgeImgs implements Serializable{

	private List<BridgeImgsResult> bridge_list;

	public List<BridgeImgsResult> getBridge_list() {
		return bridge_list;
	}

	public void setBridge_list(List<BridgeImgsResult> bridge_list) {
		this.bridge_list = bridge_list;
	}
	
}
