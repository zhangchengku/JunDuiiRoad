package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;
import java.util.ArrayList;

public class MilListResult extends BaseResult implements Serializable

{
	private station results;

	public station getResults() {
		return results;
	}

	public void setResults(station results) {
		this.results = results;
	}

	public class station implements Serializable {

		private String big_station; // 最大桩号
		private String small_station;// 最小桩号
		private String index;// 图片索引
		private String station_size;// 里程条大小

		public String getBig_station() {
			return big_station;
		}

		public void setBig_station(String big_station) {
			this.big_station = big_station;
		}

		public String getSmall_station() {
			return small_station;
		}

		public void setSmall_station(String small_station) {
			this.small_station = small_station;
		}

		public String getIndex() {
			return index;
		}

		public void setIndex(String index) {
			this.index = index;
		}

		public String getStation_size() {
			return station_size;
		}

		public void setStation_size(String station_size) {
			this.station_size = station_size;
		}

	}
}
