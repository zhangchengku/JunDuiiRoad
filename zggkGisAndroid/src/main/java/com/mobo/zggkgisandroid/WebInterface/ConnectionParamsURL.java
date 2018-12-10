package com.mobo.zggkgisandroid.WebInterface;

import android.util.Log;

/**
 * 接口访问地址连接管理类
 * 
 */
public class ConnectionParamsURL {

	/*********** 测试环境 ***************/
	// 接口访问地址
	public static final String BASE_ACCESS_URL = "http://106.37.229.146:1212/iRoadService.svc/";

	//
	// public static final String BASE_ACCESS_URL =
	// "http://106.37.229.146:1213/iRoadService.svc/";

	// public static final String BASE_ACCESS_URL =
	// "http://106.37.229.146:1213/iRoadService.svc/";
	// public static final String BASE_ACCESS_URL =
	// "http://106.37.229.146:1212/iRoadService.svc/";
	// 123.124.221.1
	// public static final String BASE_ACCESS_URL =
	// "http://106.37.229.146:1213/iRoadService.svc/";
	// http://123.124.221.1:1213/iRoadService.svc/
	// public static final String BASE_ACCESS_URL =
	// "http://106.37.229.146:1213/iRoadService.svc/";

	/**
	 * 获取用户登录访问地址
	 * 
	 * @param service_code
	 *            服务标识
	 * @param version
	 *            版本号
	 * @param user_name
	 *            用户名
	 * @param pwd
	 *            密码
	 * 
	 * @return 用户登录接口地址
	 */
	public static String getLoginUrl(String service_code, String version, String user_name, String pwd) {

		return BASE_ACCESS_URL + service_code + "?" + "version=" + version + "&" + "user_name=" + user_name + "&" + "pwd=" + pwd;
	}

	/**
	 * 获取搜索访问地址
	 * 
	 * @param service_code
	 *            服务标识
	 * @param version
	 *            版本号
	 * @param provice_code
	 *            省份编码
	 * @param token
	 *            令牌
	 * @param research_str
	 *            关键词（多个关键词加空格间隔）
	 * @param map_level
	 *            当前地图缩放等级
	 * 
	 * @return 关键词搜索接口地址
	 */
	public static String getSearchUrl(String service_code, String version, String provice_code, String token, String research_str, String map_level) {

		return BASE_ACCESS_URL + service_code + "?" + "version=" + version + "&" + "provice_code=" + provice_code + "&" + "token=" + token + "&"
				+ "research_str=" + research_str + "&" + "map_level=" + map_level;
	}

	/**
	 * 
	 * 
	 * 获取大图播放地址
	 * 
	 * @param service_code
	 *            服务标识
	 * @param version
	 *            版本号
	 * @param province_code
	 *            省份编码
	 * @param token
	 *            令牌
	 * @param road_code
	 *            线路编码
	 * @param going_direction
	 *            上下行
	 * @param forward_or_back
	 *            前进后退
	 * @param road_station
	 *            桩号
	 * @param page_size
	 *            每次返回几条数据
	 * @param page
	 *            请求第几页
	 * 
	 * 
	 * @return 获取大图播放地址
	 */

	public static String getPlayImageUrl(String service_code, String version, String province_code, String token, String road_code,
			String going_direction, String forward_or_back, String road_station, String page_size, String page, String is_station,
			String station_index) {
		Log.v("接口", BASE_ACCESS_URL + service_code + "?" + "version=" + version + "&" + "province_code=" + province_code + "&" + "token=" + token
				+ "&" + "&" + "road_code=" + road_code + "&" + "going_direction=" + going_direction + "&" + "forward_or_back=" + forward_or_back
				+ "&" + "road_station=" + road_station + "&" + "page_size=" + page_size + "&" + "page=" + page + "&" + "is_station=" + is_station
				+ "&" + "station_index=" + station_index);

		return BASE_ACCESS_URL + service_code + "?" + "version=" + version + "&" + "province_code=" + province_code + "&" + "token=" + token + "&"
				+ "&" + "road_code=" + road_code + "&" + "going_direction=" + going_direction + "&" + "forward_or_back=" + forward_or_back + "&"
				+ "road_station=" + road_station + "&" + "page_size=" + page_size + "&" + "page=" + page + "&" + "is_station=" + is_station + "&"
				+ "station_index=" + station_index;

	}

	/**
	 * 
	 * 
	 * 查看里程条
	 * 
	 * @param service_code
	 *            服务编码
	 * @param version
	 *            版本
	 * @param token
	 *            令牌
	 * @param province_code
	 *            省份编码
	 * @param road_code
	 *            道路编码
	 * @return
	 */
	public static String getMellList(String service_code, String version, String token, String province_code, String road_code, String station) {
		Log.v("开始调用接口", BASE_ACCESS_URL + service_code + "?" + "version=" + version + "&" + "token=" + token + "&" + "province_code=" + province_code
				+ "&" + "road_code=" + road_code + "&" + "station=" + station);
		return BASE_ACCESS_URL + service_code + "?" + "version=" + version + "&" + "token=" + token + "&" + "province_code=" + province_code + "&"
				+ "road_code=" + road_code + "&" + "station=" + station;
	}

	/**
	 * 
	 * 升级参数
	 * 
	 * @param service_code
	 *            服务编码
	 * @param version
	 *            版本
	 * @param platform
	 *            平台
	 * @return
	 */
	public static String getupdate(String service_code, String version, String platform) {
		return BASE_ACCESS_URL + service_code + "?" + "version" + version + "&" + "platform" + platform;

	}

	/**
	 * 获取键值列表访问地址
	 * 
	 * @param service_code
	 *            服务标识
	 * @param version
	 *            版本号
	 * @param value_md5
	 *            本地存储的MD5
	 * 
	 * @return 键值列表接口地址
	 */
	public static String getKeyValueUrl(String service_code, String version, String value_md5) {

		return BASE_ACCESS_URL + service_code + "?" + "version=" + version + "&" + "value_md5=" + value_md5;
	}

	/**
	 * 获取指标列表访问地址
	 * 
	 * @param service_code
	 *            服务标识
	 * @param version
	 *            版本号
	 * 
	 * @return 指标信息接口地址
	 */
	public static String getIndicatorUrl(String service_code, String version) {

		return BASE_ACCESS_URL + service_code + "?" + "version=" + version;
	}

	/**
	 * 获取指标列表访问地址
	 * 
	 * @param service_code
	 *            服务标识
	 * @param version
	 *            版本号
	 * @param province_code
	 *            省份编码
	 * @param token
	 *            令牌
	 * @param road_code
	 *            公路编码
	 * @param indicator_id
	 *            指标id
	 * 
	 * @return 公路指标信息接口地址
	 */
	public static String getRoadsPCIUrl(String service_code, String version, String province_code, String token, String road_code, String indicator_id) {

		return BASE_ACCESS_URL + service_code + "?" + "version=" + version + "&" + "province_code=" + province_code + "&" + "token=" + token + "&"
				+ "road_code=" + road_code + "&" + "indicator_id=" + indicator_id;
	}

	/**
	 * 获取桥梁图片访问地址
	 * 
	 * @param service_code
	 *            服务标识
	 * @param token
	 *            令牌
	 * @param version
	 *            接口版本
	 * @param province_code
	 *            省份编码
	 * @param bridge_code
	 *            桥梁编码
	 * @param page_size
	 *            每次返回几条数据
	 * @param page
	 *            请求第几页
	 * @return
	 */
	public static String getBridgeUrl(String service_code, String token, String version, String province_code, String bridge_code, String page_size,
			String page) {

		return BASE_ACCESS_URL + service_code + "?" + "token=" + token + "&" + "version=" + version + "&" + "province_code=" + province_code + "&"
				+ "bridge_code=" + bridge_code + "&" + "page_size=" + page_size + "&" + "page=" + page;
	}

	/**
	 * 获取显示小图访问地址
	 * 
	 * @param service_code
	 *            （Y） 服务标识
	 * @param version
	 *            （Y） 版本号
	 * @param token
	 *            （Y） 令牌
	 * @param province_code
	 *            （N） 省份编码
	 * @param going_direction
	 *            （Y） 上下行
	 * @param road_code
	 *            （Y） 公路编码
	 * @param road_station
	 *            （Y） 桩号
	 * 
	 * @return 小图接口地址
	 */
	public static String getSmallImgUrl(String service_code, String version, String token, String province_code, String going_direction,
			String road_code, String road_station) {

		return BASE_ACCESS_URL + service_code + "?" + "version=" + version + "&" + "token=" + token + "&" + "province_code=" + province_code + "&"
				+ "going_direction=" + going_direction + "&" + "road_code=" + road_code + "&" + "road_station=" + road_station;
	}

	/**
	 * 获取显示小图访问地址
	 * 
	 * @param service_code
	 *            （Y） 服务标识
	 * @param version
	 *            （Y） 版本号
	 * 
	 * @return 全国省份列表接口地址
	 */
	public static String getProvincesList(String service_code, String version) {

		return BASE_ACCESS_URL + service_code + "?version=" + version;
	}

	/**
	 * 获取搜索自动提示访问地址
	 * 
	 * @param service_code
	 *            （Y） 服务标识
	 * @param version
	 *            （Y） 版本号
	 * @param search_str
	 *            （Y） 搜索字符串
	 * @param province_code
	 *            （Y） 省份编码
	 * 
	 * @return 搜索自动提示地址
	 */
	public static String getSearchNoticeUrl(String service_code, String version, String search_str, String province_code, String page, String pageSize) {

		return BASE_ACCESS_URL + service_code + "?version=" + version + "&search_str=" + search_str + "&province_code=" + province_code + "&page="
				+ page + "&page_size=" + pageSize;
	}

	/**
	 * 获取搜长按地图某点高亮显示公路访问地址
	 * 
	 * @param service_code
	 *            （Y） 服务标识
	 * @param version
	 *            （Y） 版本号
	 * @param token
	 *            （Y） 令牌
	 * @param province_code
	 *            （N） 省份编码
	 * @param click_province_code
	 *            （N） 省份编码
	 * @return 长按地图某点显示公路地址
	 */
	public static String getLongPressRoadUrl(String service_code, String version, String token, String province_code, String longitude,
			String latitude, String click_province_code) {

		return BASE_ACCESS_URL + service_code + "?version=" + version + "&token=" + token + "&province_code=" + province_code + "&longitude="
				+ longitude + "&latitude=" + latitude + "&click_province_code=" + click_province_code;
	}

	/**
	 * 9.1获取搜长按地图某点高亮显示公路访问地址
	 * 
	 * @param service_code
	 *            （Y） 服务标识
	 * @param version
	 *            （Y） 版本号
	 * @param token
	 *            （Y） 令牌
	 * @param province_code
	 *            （N） 省份编码
	 * 
	 * @return 长按地图某点显示公路地址
	 */
	public static String getBridgeMarksUrl(String service_code, String version, String token, String province_code) {

		return BASE_ACCESS_URL + service_code + "?version=" + version + "&token=" + token + "&province_code=" + province_code;
	}

	/**
	 * 2.5获取公路经纬度数据地址
	 * 
	 * @param service_code
	 *            （Y） 服务标识
	 * @param version
	 *            （Y） 版本号
	 * @param token
	 *            （Y） 令牌
	 * @param province_code
	 *            （Y） 省份编码
	 * @param road_code
	 *            （Y）路线编码
	 * @return 长按地图某点显示公路地址
	 */
	public static String getNormalRoadUrl(String service_code, String version, String token, String province_code, String road_code) {

		return BASE_ACCESS_URL + service_code + "?version=" + version + "&token=" + token + "&province_code=" + province_code + "&road_code="
				+ road_code;
	}

	/**
	 * 
	 * 
	 * @param service_code
	 *            （Y） 服务标识
	 * @param version
	 *            （Y） 版本号
	 * @param token
	 *            （Y） 令牌
	 * @param statistical_type
	 *            (Y)统计类型
	 * @return 显示统计信息
	 */

	public static String getStatisticsUrl(String service_code, String version, String token, String statistical_type) {

		return BASE_ACCESS_URL + service_code + "?version=" + version + "&token=" + token + "&statistical_type=" + statistical_type;

	}

	/*********** 生产环境 ***************/
	// 接口访问地址
	// public static final String BASE_ACCESS_URL ="";

	/*********** 服务标识 ***************/

}
