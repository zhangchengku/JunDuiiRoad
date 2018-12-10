package com.mobo.zggkgisandroid.ClassfyInfo;

import java.util.ArrayList;

public class RoadThumbImgInfo {
	
		private String road_thumb_data;
		private ArrayList<IndicatorInfo> indicator_info;// 指标信息集合

		public ArrayList<IndicatorInfo> getIndicator_info() {
			return indicator_info;
		}

		public void setIndicator_info(ArrayList<IndicatorInfo> indicator_info) {
			this.indicator_info = indicator_info;
		}

		public String getRoad_thumb_data() {
			return road_thumb_data;
		}

		public void setRoad_thumb_data(String road_thumb_data) {
			this.road_thumb_data = road_thumb_data;
		}

	
}
