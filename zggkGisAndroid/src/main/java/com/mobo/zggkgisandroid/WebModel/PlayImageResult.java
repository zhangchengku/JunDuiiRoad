package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

public class PlayImageResult extends BaseResult {
	private ArrayList<PlayImageMess> results;

	public ArrayList<PlayImageMess> getResults() {
		return results;
	}

	public void setResults(ArrayList<PlayImageMess> results) {
		this.results = results;
	}

	public class PlayImageMess {
		private String province_code;
		private String province_name;
		private ArrayList<image_list> large_image_list;

		public String getProvince_code() {
			return province_code;
		}

		public void setProvince_code(String province_code) {
			this.province_code = province_code;
		}

		public String getProvince_name() {
			return province_name;
		}

		public void setProvince_name(String province_name) {
			this.province_name = province_name;
		}

		public ArrayList<image_list> getLarge_image_list() {
			return large_image_list;
		}

		public void setLarge_image_list(ArrayList<image_list> large_image_list) {
			this.large_image_list = large_image_list;
		}

		public class image_list {
			private String result_type;// 返回结果类型 VARCHAR

			public String getResult_type() {
				return result_type;
			}

			public void setResult_type(String result_type) {
				this.result_type = result_type;
			}

			private road road_large_img;// 公路大图实体
			private bridge bridge_large_img;// 桥梁大图实体
			private tunnel tunnel_large_img;// ;//隧道大图实体
			private facility facility_large_img;// ;//设施大图实体
			private construction construction_large_img;// ;//施工大图实体

			public road getRoad_large_img() {
				return road_large_img;
			}

			public void setRoad_large_img(road road_large_img) {
				this.road_large_img = road_large_img;
			}

			public bridge getBridge_large_img() {
				return bridge_large_img;
			}

			public void setBridge_large_img(bridge bridge_large_img) {
				this.bridge_large_img = bridge_large_img;
			}

			public tunnel getTunnel_large_img() {
				return tunnel_large_img;
			}

			public void setTunnel_large_img(tunnel tunnel_large_img) {
				this.tunnel_large_img = tunnel_large_img;
			}

			public facility getFacility_large_img() {
				return facility_large_img;
			}

			public void setFacility_large_img(facility facility_large_img) {
				this.facility_large_img = facility_large_img;
			}

			public construction getConstruction_large_img() {
				return construction_large_img;
			}

			public void setConstruction_large_img(
					construction construction_large_img) {
				this.construction_large_img = construction_large_img;
			}

			public class road {
				
				private String current_indicator_value;
			    private String current_location;
			    private String current_station;
			    private String daily_traffic_volume;
			    private String road_build_years;
			    private String road_current_land;
			    private String road_face_type;
			    private String road_img_direction;
			    private String road_img_year;
			    private String road_lane_num;
			    private String road_large_img_data;
			    private String road_location_route;
			    private String road_manage_unit;
			    private String road_side_type;
			    private String road_surface_condition;
			    private String road_technical_grade;
			    private String road_thumb_img_data;
			    private String road_way_width;
			    private String road_conservation_years;

			    public String getRoad_conservation_years() {
					return road_conservation_years;
				}

				public void setRoad_conservation_years(String road_conservation_years) {
					this.road_conservation_years = road_conservation_years;
				}

				public String getCurrent_indicator_value() {
			        return current_indicator_value;
			    }

			    public void setCurrent_indicator_value(String current_indicator_value) {
			        this.current_indicator_value = current_indicator_value;
			    }

			    public String getCurrent_location() {
			        return current_location;
			    }

			    public void setCurrent_location(String current_location) {
			        this.current_location = current_location;
			    }

			    public String getCurrent_station() {
			        return current_station;
			    }

			    public void setCurrent_station(String current_station) {
			        this.current_station = current_station;
			    }

			    public String getDaily_traffic_volume() {
			        return daily_traffic_volume;
			    }

			    public void setDaily_traffic_volume(String daily_traffic_volume) {
			        this.daily_traffic_volume = daily_traffic_volume;
			    }

			    public String getRoad_build_years() {
			        return road_build_years;
			    }

			    public void setRoad_build_years(String road_build_years) {
			        this.road_build_years = road_build_years;
			    }

			    public String getRoad_current_land() {
			        return road_current_land;
			    }

			    public void setRoad_current_land(String road_current_land) {
			        this.road_current_land = road_current_land;
			    }

			    public String getRoad_face_type() {
			        return road_face_type;
			    }

			    public void setRoad_face_type(String road_face_type) {
			        this.road_face_type = road_face_type;
			    }

			    public String getRoad_img_direction() {
			        return road_img_direction;
			    }

			    public void setRoad_img_direction(String road_img_direction) {
			        this.road_img_direction = road_img_direction;
			    }

			    public String getRoad_img_year() {
			        return road_img_year;
			    }

			    public void setRoad_img_year(String road_img_year) {
			        this.road_img_year = road_img_year;
			    }

			    public String getRoad_lane_num() {
			        return road_lane_num;
			    }

			    public void setRoad_lane_num(String road_lane_num) {
			        this.road_lane_num = road_lane_num;
			    }

			    public String getRoad_large_img_data() {
			        return road_large_img_data;
			    }

			    public void setRoad_large_img_data(String road_large_img_data) {
			        this.road_large_img_data = road_large_img_data;
			    }

			    public String getRoad_location_route() {
			        return road_location_route;
			    }

			    public void setRoad_location_route(String road_location_route) {
			        this.road_location_route = road_location_route;
			    }

			    public String getRoad_manage_unit() {
			        return road_manage_unit;
			    }

			    public void setRoad_manage_unit(String road_manage_unit) {
			        this.road_manage_unit = road_manage_unit;
			    }

			    public String getRoad_side_type() {
			        return road_side_type;
			    }

			    public void setRoad_side_type(String road_side_type) {
			        this.road_side_type = road_side_type;
			    }

			    public String getRoad_surface_condition() {
			        return road_surface_condition;
			    }

			    public void setRoad_surface_condition(String road_surface_condition) {
			        this.road_surface_condition = road_surface_condition;
			    }

			    public String getRoad_technical_grade() {
			        return road_technical_grade;
			    }

			    public void setRoad_technical_grade(String road_technical_grade) {
			        this.road_technical_grade = road_technical_grade;
			    }

			    public String getRoad_thumb_img_data() {
			        return road_thumb_img_data;
			    }

			    public void setRoad_thumb_img_data(String road_thumb_img_data) {
			        this.road_thumb_img_data = road_thumb_img_data;
			    }

			    public String getRoad_way_width() {
			        return road_way_width;
			    }

			    public void setRoad_way_width(String road_way_width) {
			        this.road_way_width = road_way_width;
			    }
				
			}

			public class bridge {

				public String getCurrent_station() {
					return current_station;
				}

				public void setCurrent_station(String current_station) {
					this.current_station = current_station;
				}

				public String getCurrent_location() {
					return current_location;
				}

				public void setCurrent_location(String current_location) {
					this.current_location = current_location;
				}

				public String getBridge_id() {
					return bridge_id;
				}

				public void setBridge_id(String bridge_id) {
					this.bridge_id = bridge_id;
				}

				public String getBridge_name() {
					return bridge_name;
				}

				public void setBridge_name(String bridge_name) {
					this.bridge_name = bridge_name;
				}

				public String getBridge_code() {
					return bridge_code;
				}

				public void setBridge_code(String bridge_code) {
					this.bridge_code = bridge_code;
				}

				public String getBridge_is_ngtc() {
					return bridge_is_ngtc;
				}

				public void setBridge_is_ngtc(String bridge_is_ngtc) {
					this.bridge_is_ngtc = bridge_is_ngtc;
				}

				public String getBridge_thumb_img_data() {
					return bridge_thumb_img_data;
				}

				public void setBridge_thumb_img_data(
						String bridge_thumb_img_data) {
					this.bridge_thumb_img_data = bridge_thumb_img_data;
				}

				public String getBridge_large_img_data() {
					return bridge_large_img_data;
				}

				public void setBridge_large_img_data(
						String bridge_large_img_data) {
					this.bridge_large_img_data = bridge_large_img_data;
				}

				public String getBridge_location_route() {
					return bridge_location_route;
				}

				public void setBridge_location_route(
						String bridge_location_route) {
					this.bridge_location_route = bridge_location_route;
				}

				public String getBridge_length() {
					return bridge_length;
				}

				public void setBridge_length(String bridge_length) {
					this.bridge_length = bridge_length;
				}

				public String getBridge_span_type() {
					return bridge_span_type;
				}

				public void setBridge_span_type(String bridge_span_type) {
					this.bridge_span_type = bridge_span_type;
				}

				public String getBridge_technical_grade() {
					return bridge_technical_grade;
				}

				public void setBridge_technical_grade(
						String bridge_technical_grade) {
					this.bridge_technical_grade = bridge_technical_grade;
				}

				public String getBridge_design_unit() {
					return bridge_design_unit;
				}

				public void setBridge_design_unit(String bridge_design_unit) {
					this.bridge_design_unit = bridge_design_unit;
				}

				public String getBridge_construct_unit() {
					return bridge_construct_unit;
				}

				public void setBridge_construct_unit(
						String bridge_construct_unit) {
					this.bridge_construct_unit = bridge_construct_unit;
				}

				public String getBridge_manage_unit() {
					return bridge_manage_unit;
				}

				public void setBridge_manage_unit(String bridge_manage_unit) {
					this.bridge_manage_unit = bridge_manage_unit;
				}

				public String getBridge_fix_year() {
					return bridge_fix_year;
				}

				public void setBridge_fix_year(String bridge_fix_year) {
					this.bridge_fix_year = bridge_fix_year;
				}

				private String current_station;// 当前桩号
				private String current_location;// 当前位于
				private String bridge_id;// 桥梁id
				private String bridge_name;// 桥梁名称
				private String bridge_code;// 桥梁编码
				private String bridge_is_ngtc;// 是否国检
				private String bridge_thumb_img_data;// 小图
				private String bridge_large_img_data;// 大图
				private String bridge_location_route;// 所在路线
				private String bridge_length;// 桥梁全长
				private String bridge_span_type;// 跨径类型
				private String bridge_technical_grade;// 技术状况
				private String bridge_design_unit;// 设计单位
				private String bridge_construct_unit;// 施工单位
				private String bridge_manage_unit;// 监理单位
				private String bridge_fix_year;// 修建年度
			}

			public class tunnel {

				public String getCurrent_station() {
					return current_station;
				}

				public void setCurrent_station(String current_station) {
					this.current_station = current_station;
				}

				public String getCurrent_location() {
					return current_location;
				}

				public void setCurrent_location(String current_location) {
					this.current_location = current_location;
				}

				public String getTunnel_id() {
					return tunnel_id;
				}

				public void setTunnel_id(String tunnel_id) {
					this.tunnel_id = tunnel_id;
				}

				public String getTunnel_name() {
					return tunnel_name;
				}

				public void setTunnel_name(String tunnel_name) {
					this.tunnel_name = tunnel_name;
				}

				public String getTunnel_code() {
					return tunnel_code;
				}

				public void setTunnel_code(String tunnel_code) {
					this.tunnel_code = tunnel_code;
				}

				public String getTunnel_thumb_img_data() {
					return tunnel_thumb_img_data;
				}

				public void setTunnel_thumb_img_data(
						String tunnel_thumb_img_data) {
					this.tunnel_thumb_img_data = tunnel_thumb_img_data;
				}

				public String getTunnel_large_img_data() {
					return tunnel_large_img_data;
				}

				public void setTunnel_large_img_data(
						String tunnel_large_img_data) {
					this.tunnel_large_img_data = tunnel_large_img_data;
				}

				public String getTunnel_location_route() {
					return tunnel_location_route;
				}

				public void setTunnel_location_route(
						String tunnel_location_route) {
					this.tunnel_location_route = tunnel_location_route;
				}

				public String getTunnel_center_station() {
					return tunnel_center_station;
				}

				public void setTunnel_center_station(
						String tunnel_center_station) {
					this.tunnel_center_station = tunnel_center_station;
				}

				public String getTunnel_length() {
					return tunnel_length;
				}

				public void setTunnel_length(String tunnel_length) {
					this.tunnel_length = tunnel_length;
				}

				public String getTunnel_height() {
					return tunnel_height;
				}

				public void setTunnel_height(String tunnel_height) {
					this.tunnel_height = tunnel_height;
				}

				public String getTunnel_evaluate_grade() {
					return tunnel_evaluate_grade;
				}

				public void setTunnel_evaluate_grade(
						String tunnel_evaluate_grade) {
					this.tunnel_evaluate_grade = tunnel_evaluate_grade;
				}

				public String getTunnel_design_unit() {
					return tunnel_design_unit;
				}

				public void setTunnel_design_unit(String tunnel_design_unit) {
					this.tunnel_design_unit = tunnel_design_unit;
				}

				public String getTunnel_construct_unit() {
					return tunnel_construct_unit;
				}

				public void setTunnel_construct_unit(
						String tunnel_construct_unit) {
					this.tunnel_construct_unit = tunnel_construct_unit;
				}

				public String getTunnel_manage_unit() {
					return tunnel_manage_unit;
				}

				public void setTunnel_manage_unit(String tunnel_manage_unit) {
					this.tunnel_manage_unit = tunnel_manage_unit;
				}

				public String getTunnel_fix_year() {
					return tunnel_fix_year;
				}

				public void setTunnel_fix_year(String tunnel_fix_year) {
					this.tunnel_fix_year = tunnel_fix_year;
				}

				private String current_station;// 当前桩号
				private String current_location;// 当前位于
				private String tunnel_id;// 隧道id
				private String tunnel_name;// 隧道名称
				private String tunnel_code;// 隧道编码
				private String tunnel_thumb_img_data;// 小图
				private String tunnel_large_img_data;// 大图
				private String tunnel_location_route;// 所在路线
				private String tunnel_center_station;// 中心桩号
				private String tunnel_length;// 隧道长度
				private String tunnel_height;// 隧道净高
				private String tunnel_evaluate_grade;// 评定等级
				private String tunnel_design_unit;// 设计单位
				private String tunnel_construct_unit;// 施工单位
				private String tunnel_manage_unit;// 监理单位
				private String tunnel_fix_year;// 修建年度

			}

			public class facility {

				public String getCurrent_station() {
					return current_station;
				}

				public void setCurrent_station(String current_station) {
					this.current_station = current_station;
				}

				public String getCurrent_location() {
					return current_location;
				}

				public void setCurrent_location(String current_location) {
					this.current_location = current_location;
				}

				public String getFacility_id() {
					return facility_id;
				}

				public void setFacility_id(String facility_id) {
					this.facility_id = facility_id;
				}

				public String getFacility_name() {
					return facility_name;
				}

				public void setFacility_name(String facility_name) {
					this.facility_name = facility_name;
				}

				public String getFacility_code() {
					return facility_code;
				}

				public void setFacility_code(String facility_code) {
					this.facility_code = facility_code;
				}

				public String getFacility_thumb_img_data() {
					return facility_thumb_img_data;
				}

				public void setFacility_thumb_img_data(
						String facility_thumb_img_data) {
					this.facility_thumb_img_data = facility_thumb_img_data;
				}

				public String getFacility_large_img_data() {
					return facility_large_img_data;
				}

				public void setFacility_large_img_data(
						String facility_large_img_data) {
					this.facility_large_img_data = facility_large_img_data;
				}

				public String getLongitude() {
					return longitude;
				}

				public void setLongitude(String longitude) {
					this.longitude = longitude;
				}

				public String getLatitude() {
					return latitude;
				}

				public void setLatitude(String latitude) {
					this.latitude = latitude;
				}

				public String getFacility_location_route() {
					return facility_location_route;
				}

				public void setFacility_location_route(
						String facility_location_route) {
					this.facility_location_route = facility_location_route;
				}

				public String getFacility_center_station() {
					return facility_center_station;
				}

				public void setFacility_center_station(
						String facility_center_station) {
					this.facility_center_station = facility_center_station;
				}

				private String current_station;// 当前桩号
				private String current_location;// 当前位于
				private String facility_id;// 设施id
				private String facility_name;// 设施名称
				private String facility_code;// 设施编码
				private String facility_thumb_img_data;// 小图
				private String facility_large_img_data;// 大图
				private String longitude;// 经度
				private String latitude;// 纬度
				private String facility_location_route;// 所在路线
				private String facility_center_station;// 中心桩号

			}

			public class construction {

				private String current_station;// 当前桩号
				private String current_location;// 当前位于
				private String construction_id;// 施工id
				private String construction_name;// 工程名称
				private String construction_code;// 施工编码
				private String construction_thumb_img_data;// 小图
				private String construction_large_img_data;// 大图
				private String construction_location_route;// 所在路线
				private String construction_investment_money;// 投资金额

				public String getCurrent_station() {
					return current_station;
				}

				public void setCurrent_station(String current_station) {
					this.current_station = current_station;
				}

				public String getCurrent_location() {
					return current_location;
				}

				public void setCurrent_location(String current_location) {
					this.current_location = current_location;
				}

				public String getConstruction_id() {
					return construction_id;
				}

				public void setConstruction_id(String construction_id) {
					this.construction_id = construction_id;
				}

				public String getConstruction_name() {
					return construction_name;
				}

				public void setConstruction_name(String construction_name) {
					this.construction_name = construction_name;
				}

				public String getConstruction_code() {
					return construction_code;
				}

				public void setConstruction_code(String construction_code) {
					this.construction_code = construction_code;
				}

				public String getConstruction_thumb_img_data() {
					return construction_thumb_img_data;
				}

				public void setConstruction_thumb_img_data(
						String construction_thumb_img_data) {
					this.construction_thumb_img_data = construction_thumb_img_data;
				}

				public String getConstruction_large_img_data() {
					return construction_large_img_data;
				}

				public void setConstruction_large_img_data(
						String construction_large_img_data) {
					this.construction_large_img_data = construction_large_img_data;
				}

				public String getConstruction_location_route() {
					return construction_location_route;
				}

				public void setConstruction_location_route(
						String construction_location_route) {
					this.construction_location_route = construction_location_route;
				}

				public String getConstruction_investment_money() {
					return construction_investment_money;
				}

				public void setConstruction_investment_money(
						String construction_investment_money) {
					this.construction_investment_money = construction_investment_money;
				}

				public String getConstruction_comp_progress() {
					return construction_comp_progress;
				}

				public void setConstruction_comp_progress(
						String construction_comp_progress) {
					this.construction_comp_progress = construction_comp_progress;
				}

				public String getConstruction_manage_unit() {
					return construction_manage_unit;
				}

				public void setConstruction_manage_unit(
						String construction_manage_unit) {
					this.construction_manage_unit = construction_manage_unit;
				}

				public String getConstruction_construct_unit() {
					return construction_construct_unit;
				}

				public void setConstruction_construct_unit(
						String construction_construct_unit) {
					this.construction_construct_unit = construction_construct_unit;
				}

				public String getConstruction_technical_grade() {
					return construction_technical_grade;
				}

				public void setConstruction_technical_grade(
						String construction_technical_grade) {
					this.construction_technical_grade = construction_technical_grade;
				}

				private String construction_comp_progress;// 完成进度
				private String construction_manage_unit;// 管养单位
				private String construction_construct_unit;// 施工单位
				private String construction_technical_grade;// 技术等级

			}

		}

	}

}
