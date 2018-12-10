package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;
import java.util.ArrayList;

import com.mobo.zggkgisandroid.ClassfyInfo.BridgeMarksInfo;
/**
 * 桥梁集合
 * @author Administrator
 *
 */
public class BridgeMarks implements Serializable{
	private String bridge_totle_num;//全省大桥总数
	private ArrayList<BridgeMarksInfo> bridge_next_level;
	public ArrayList<BridgeMarksInfo> getBridge_next_level() {
		return bridge_next_level;
	}
	public void setBridge_next_level(ArrayList<BridgeMarksInfo> bridge_next_level) {
		this.bridge_next_level = bridge_next_level;
	}
	public String getBridge_totle_num() {
		return bridge_totle_num;
	}
	public void setBridge_totle_num(String bridge_totle_num) {
		this.bridge_totle_num = bridge_totle_num;
	}
	
}
