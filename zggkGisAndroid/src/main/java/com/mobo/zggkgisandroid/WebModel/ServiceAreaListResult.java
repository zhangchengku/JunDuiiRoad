package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 11.2.服务区列表
 * 
 * @author WuXiaoHao
 *
 */
public class ServiceAreaListResult extends BaseResult implements Serializable {
	ArrayList<ServiceArea> results;

	public ArrayList<ServiceArea> getResults() {
		return results;
	}

	public void setResults(ArrayList<ServiceArea> results) {
		this.results = results;
	}

	public class ServiceArea implements Serializable {
		String service_area_code;// 服务区编码 VARCHAR
		String service_area_name;// 服务区名称 VARCHAR
		String service_area_img;// 服务区图片 VARCHAR
		String road_name;// 服务区所在的高速公路名称 VARCHAR
		String road_code;// 服务区所在的高速公路编码 VARCHAR
		String distance;// 距离当前多少距离 VARCHAR 单位米

		String service_area_introduce;// 服务区简介 VARCHAR
		String service_area_phone;// 服务区电话 VARCHAR
		String service_area_address;// 服务区地址 VARCHAR
		String parking_space;// 停车位总数 VARCHAR
		String available_parking_spaces;// 可用停车位 VARCHAR
		String service_content;// 服务内容 VARCHAR
		String current_state;// 当前状态

		public String getCurrent_state() {
			return current_state;
		}

		public void setCurrent_state(String current_state) {
			this.current_state = current_state;
		}

		public String getService_area_introduce() {
			return service_area_introduce;
		}

		public void setService_area_introduce(String service_area_introduce) {
			this.service_area_introduce = service_area_introduce;
		}

		public String getService_area_phone() {
			return service_area_phone;
		}

		public void setService_area_phone(String service_area_phone) {
			this.service_area_phone = service_area_phone;
		}

		public String getService_area_address() {
			return service_area_address;
		}

		public void setService_area_address(String service_area_address) {
			this.service_area_address = service_area_address;
		}

		public String getParking_space() {
			return parking_space;
		}

		public void setParking_space(String parking_space) {
			this.parking_space = parking_space;
		}

		public String getAvailable_parking_spaces() {
			return available_parking_spaces;
		}

		public void setAvailable_parking_spaces(String available_parking_spaces) {
			this.available_parking_spaces = available_parking_spaces;
		}

		public String getService_content() {
			return service_content;
		}

		public void setService_content(String service_content) {
			this.service_content = service_content;
		}

		public String getService_area_code() {
			return service_area_code;
		}

		public void setService_area_code(String service_area_code) {
			this.service_area_code = service_area_code;
		}

		public String getService_area_name() {
			return service_area_name;
		}

		public void setService_area_name(String service_area_name) {
			this.service_area_name = service_area_name;
		}

		public String getService_area_img() {
			return service_area_img;
		}

		public void setService_area_img(String service_area_img) {
			this.service_area_img = service_area_img;
		}

		public String getRoad_name() {
			return road_name;
		}

		public void setRoad_name(String road_name) {
			this.road_name = road_name;
		}

		public String getRoad_code() {
			return road_code;
		}

		public void setRoad_code(String road_code) {
			this.road_code = road_code;
		}

		public String getDistance() {
			return distance;
		}

		public void setDistance(String distance) {
			this.distance = distance;
		}

	}
}
