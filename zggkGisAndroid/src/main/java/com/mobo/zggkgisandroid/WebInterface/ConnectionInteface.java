package com.mobo.zggkgisandroid.WebInterface;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgePositionInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.SearchInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.ServicePositionInfo;
import com.mobo.zggkgisandroid.Utils.DesUtils;
import com.mobo.zggkgisandroid.Utils.Gzip;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.Utils.constant;
import com.mobo.zggkgisandroid.WebModel.BaseResult;
import com.mobo.zggkgisandroid.WebModel.BridgeDetailInfoResult;
import com.mobo.zggkgisandroid.WebModel.BridgeImageModel;
import com.mobo.zggkgisandroid.WebModel.BridgeImagesBeanResult;
import com.mobo.zggkgisandroid.WebModel.BridgeMarksResult;
import com.mobo.zggkgisandroid.WebModel.CityListResult;
import com.mobo.zggkgisandroid.WebModel.ExpresswayListResult;
import com.mobo.zggkgisandroid.WebModel.FortyFiveBridgeResult;
import com.mobo.zggkgisandroid.WebModel.IndicatorResult;
import com.mobo.zggkgisandroid.WebModel.KeyValueResult;
import com.mobo.zggkgisandroid.WebModel.LoginResult;
import com.mobo.zggkgisandroid.WebModel.MapStaticsModel;
import com.mobo.zggkgisandroid.WebModel.MilListResult;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult;
import com.mobo.zggkgisandroid.WebModel.PressRoadInfoResult;
import com.mobo.zggkgisandroid.WebModel.ProvinceRoadDataResult;
import com.mobo.zggkgisandroid.WebModel.ProvinceSituationResult;
import com.mobo.zggkgisandroid.WebModel.ProvincesResult;
import com.mobo.zggkgisandroid.WebModel.RoadContrastResult;
import com.mobo.zggkgisandroid.WebModel.RoadPCIResult;
import com.mobo.zggkgisandroid.WebModel.RoadTrafficResult;
import com.mobo.zggkgisandroid.WebModel.SearchInfoResult;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesList;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesResult;
import com.mobo.zggkgisandroid.WebModel.SearchResult;
import com.mobo.zggkgisandroid.WebModel.ServiceAreaListResult;
import com.mobo.zggkgisandroid.WebModel.ServiceAreaResult;
import com.mobo.zggkgisandroid.WebModel.ServiceMarkerResult;
import com.mobo.zggkgisandroid.WebModel.SingleBridgeDataResult;
import com.mobo.zggkgisandroid.WebModel.SmallImageResult;
import com.mobo.zggkgisandroid.WebModel.StatisticsModel;
import com.mobo.zggkgisandroid.WebModel.StatisticsModelTwo;
import com.mobo.zggkgisandroid.WebModel.UpdateResult;
import com.mobo.zggkgisandroid.WebModel.bindPhoneModel;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 接口访问通用类
 * 
 */
public class ConnectionInteface {

	private final String DEVICE_TYPE = "device_type";// 设备标识
	private final String SERVICE_CODE = "service_code";// 服务标识
	private final String VERSION = "version";// 接口版本

	// 接口固定值
	private String version = "1.1";
	private Gson gson;
	private Context context;// context对象
	private HttpUtils httpUtils;
	SimpleDateFormat dateFormat;
	private String service_code;// 服务标识

	/**
	 * 构造
	 * 
	 * @param context
	 *            上下文对象
	 */
	public ConnectionInteface(Context context) {
		this.gson = new Gson();
		this.context = context;
		httpUtils = new HttpUtils();

		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public HashMap<String, String> getMap() {
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
		hashMap.put("version", version);
		return hashMap;
	}

	private String forUrl(Map<String, String> params, String service_code) {

		String url = ConnectionParamsURL.BASE_ACCESS_URL /* "http://106.37.229.146:1212/iRoadService.svc/" */
				+ service_code + "?";
		for (Map.Entry<String, String> entry : params.entrySet()) {
			url = url + entry.getKey() + "=" + entry.getValue() + "&";
		}
		url = url.substring(0, url.length() - 1);
		Log.v("url", url);
		return url;
	}

	/**
	 * 登录接口
	 * 
	 * @param user_name
	 *            用户名
	 * @param password
	 *            密码
	 * 
	 * @return LoginResult
	 */
	public LoginResult Login(String user_name, String password) {

		service_code = "ZGGK_LOGIN_SYSTEM";

		String data = password;
		String key = "roadmaint";

		String password_des = DesUtils.encode(key, data);

		String resulst = httpUtils
				.getSendRequest(ConnectionParamsURL.getLoginUrl(service_code, version, user_name, password_des));
		if (resulst != null) {

			return gson.fromJson(resulst, LoginResult.class);
		}
		return null;
	}

	/**
	 * 2.1.搜索结果列表
	 * 
	 * @param province_code
	 *            城市编码 VARCHAR 如果传入城市编码，结果返回该城市的。如果不传，根据关键字返回
	 * @param longitude
	 *            经度 VARCHAR 搜索附近的时候，传入经纬度
	 * @param latitude
	 *            纬度 VARCHAR 搜索附近的时候，传入经纬度
	 * @param research_type
	 *            搜索类型 搜索类型对应搜索界面的“桥梁”、“服务区”等按钮，点击按钮传入对应的值。搜索类型和搜索关键字二选一传给接口！！！
	 *            TYPE_ROAD 公路 TYPE_BRIDGE 桥梁 TYPE_TUNNEL 隧道 TYPE_FACILITY 设施
	 *            TYPE_CONSTRUCTION 施工点 TYPE_SERVICE_AREA服务区 TYPE_DAMAGE灾毁
	 *            TYPE_VIDEO视频
	 * @param token
	 *            令牌 VARCHAR
	 * @param research_str
	 *            搜索关键字 VARCHAR 搜索框输入关键字，然后点击搜索按钮的时候，传入该字段。搜索类型和搜索关键字二选一传给接口！！！
	 * @param map_level
	 *            地图缩放等级 VARCHAR
	 * 
	 * @return
	 */
	public SearchResult SearchInfo(String province_code, String longitude, String latitude, String research_type,
			String token, String research_str, String map_level) {
		Map<String, String> params = getMap();
		params.put("province_code", province_code);
		params.put("longitude", longitude);
		params.put("latitude", latitude);
		params.put("research_type", research_type);
		params.put("token", token);
		//research_str = research_str.replace(" ", "%20");
		params.put("research_str", research_str);
		params.put("map_level", map_level);
		String url = forUrl(params, "ZGGK_RESEARCH_GET_LIST");
		try {
			String json = httpUtils.getSendRequest(url);
			String temp = Gzip.unGzip(json);

			System.out.println("========返回的结果的为========" + temp);

			return gson.fromJson(temp, SearchResult.class);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * 
	 * 播放图片 接口
	 * 
	 * @param province_code
	 * @param token
	 * @param result_type
	 * @param road_code
	 * @param going_direction
	 * @param road_min_station
	 * @param page_size
	 * @param page
	 * @return
	 */

	public PlayImageResult Playimage(String province_code, String token, String road_code, String going_direction,
			String forward_or_back, String road_station, String page_size, String page, String is_station,
			String station_index) {
		service_code = "ZGGK_LARGE_IMAGE_LIST";
		Log.v("mylog图片开始调用", dateFormat.format(new Date(System.currentTimeMillis())));
		Request.Builder builder = new Request.Builder();
		Request request = builder.url(ConnectionParamsURL.getPlayImageUrl(service_code, version, province_code, token,
				road_code, going_direction, forward_or_back, road_station, page_size, page, is_station, station_index))

				.build();

		Log.v("接口调用", ConnectionParamsURL.getPlayImageUrl(service_code, version, province_code, token, road_code,
				going_direction, forward_or_back, road_station, page_size, page, is_station, station_index));
		Response response = null;
		try {
			response = CustomApp.app.client.newCall(request).execute();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (response.isSuccessful()) {

			Log.v("mylog图片结束调用", dateFormat.format(new Date(System.currentTimeMillis())));
			try {
				String string = response.body().string();
				Log.v("mylog图片json解析开始", dateFormat.format(new Date(System.currentTimeMillis())));
				String unGzip = Gzip.unGzip(string);
				PlayImageResult fromJson = gson.fromJson(unGzip, PlayImageResult.class);
				Log.v("mylog图片json解析结束", dateFormat.format(new Date(System.currentTimeMillis())));

				return fromJson;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Log.v("mylog图片结束调用", dateFormat.format(new Date(System.currentTimeMillis())));

		}

		// Log.v("mylog图片开始调用",
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// String result = httpUtils.getSendRequest(ConnectionParamsURL
		// .getPlayImageUrl(service_code, version, province_code, token,
		// result_type, road_code, going_direction,
		// road_min_station, page_size, page));
		// Log.v("mylog图片结束调用",
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// if (result != null) {
		// Log.v("mylog图片json解析开始",
		// dateFormat.format(new Date(System.currentTimeMillis())));
		//
		// PlayImageResult fromJson = gson.fromJson(result,
		// PlayImageResult.class);
		//
		// Log.v("mylog图片json解析结束",
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// return fromJson;
		//
		// }

		return null;

	}

	public MilListResult MillList(String token, String province_code, String road_code, String station) {

		service_code = "ZGGK_MEIL_LIST";
		Log.v("mylog里程条开始调用", dateFormat.format(new Date(System.currentTimeMillis())));

		Request request = new Request.Builder()
				.url(ConnectionParamsURL.getMellList(service_code, version, token, province_code, road_code, station))
				.build();
		Response response = null;
		try {
			response = CustomApp.app.client.newCall(request).execute();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (response.isSuccessful()) {

			Log.v("mylog里程条结束调用", dateFormat.format(new Date(System.currentTimeMillis())));
			try {
				String string = response.body().string();
				Log.v("mylog里程条解析开始",

						dateFormat.format(new Date(System.currentTimeMillis())));
				// String temp = Gzip.unGzip(string);
				MilListResult fromJson = gson.fromJson(string, MilListResult.class);

				Log.v("mylog里程条解析结束", dateFormat.format(new Date(System.currentTimeMillis())));

				return fromJson;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Log.v("mylog图片结束调用", dateFormat.format(new Date(System.currentTimeMillis())));

		}

		// Log.v("mylog里程条开始调用",
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// String result = httpUtils.getSendRequest(ConnectionParamsURL
		// .getMellList(service_code, version, token, province_code,
		// road_code));
		//
		// Log.v("mylog里程条结束调用",
		//
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// if (result != null) {
		// Log.v("mylog里程条解析开始",
		//
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// MilListResult fromJson = gson.fromJson(result, MilListResult.class);
		// Log.v("mylog里程条解析结束",
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// return fromJson;
		//
		// }

		return null;
	}

	public MilListResult MillList2(String token, String province_code, String road_code, String station) {

		service_code = "ZGGK_MEIL_SON_LIST";
		Log.v("mylog里程条开始调用", dateFormat.format(new Date(System.currentTimeMillis())));

		Request request = new Request.Builder()
				.url(ConnectionParamsURL.getMellList(service_code, version, token, province_code, road_code, station))
				.build();
		Response response = null;
		try {
			response = CustomApp.app.client.newCall(request).execute();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (response.isSuccessful()) {

			try {
				String string = response.body().string();
				Log.v("mylog里程条结束调用", dateFormat.format(new Date(System.currentTimeMillis())));

				Log.v("mylog里程条解析开始",

						dateFormat.format(new Date(System.currentTimeMillis())));
				Log.v("mylog里程条解压开始",

						dateFormat.format(new Date(System.currentTimeMillis())));
				// String temp = Gzip.unGzip(string);
				Log.v("mylog里程条解压结束",

						dateFormat.format(new Date(System.currentTimeMillis())));
				MilListResult fromJson = gson.fromJson(string, MilListResult.class);
				Log.v("mylog里程条解析结束", dateFormat.format(new Date(System.currentTimeMillis())));

				return fromJson;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Log.v("mylog图片结束调用", dateFormat.format(new Date(System.currentTimeMillis())));

		}

		// Log.v("mylog里程条开始调用",
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// String result = httpUtils.getSendRequest(ConnectionParamsURL
		// .getMellList(service_code, version, token, province_code,
		// road_code));
		//
		// Log.v("mylog里程条结束调用",
		//
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// if (result != null) {
		// Log.v("mylog里程条解析开始",
		//
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// MilListResult fromJson = gson.fromJson(result, MilListResult.class);
		// Log.v("mylog里程条解析结束",
		// dateFormat.format(new Date(System.currentTimeMillis())));
		// return fromJson;
		//
		// }

		return null;
	}

	public UpdateResult Update() {
		service_code = "ZGGK_SYSTEM_UPDATE";
		String platform = "PLATFORM_ANDROID";
		String Result = httpUtils.getSendRequest(ConnectionParamsURL.getupdate(service_code, version, platform));
		if (Result != null) {

			return gson.fromJson(Result, UpdateResult.class);

		}
		return null;
	}

	/**
	 * 键值列表接口
	 * 
	 * @param value_md5
	 *            本地md5值
	 * 
	 * @return KeyValueResult
	 */
	public KeyValueResult getKeyValue(String value_md5) {

		service_code = "ZGGK_SET_VALUE";

		String result = httpUtils.getSendRequest(ConnectionParamsURL.getKeyValueUrl(service_code, version, value_md5));

		if (!Utils.isNull(result)) {
			return gson.fromJson(result, KeyValueResult.class);
		}

		return null;
	}

	/**
	 * 2.5键值列表接口
	 * 
	 * @param token
	 *            token
	 * @param province_code
	 *            省份编码
	 * @param road_code
	 *            路线编码
	 * @return SearchInfo
	 */
	public SearchInfoResult getNormalRoad(String token, String province_code, String road_code) {

		service_code = "ZGGK_ROAD_CANCEL_PCI_SYSTEM";

		String result = httpUtils.getSendRequest(
				ConnectionParamsURL.getNormalRoadUrl(service_code, version, token, province_code, road_code));

		if (!Utils.isNull(result)) {
			return gson.fromJson(result, SearchInfoResult.class);
		}

		return null;
	}

	/**
	 * 获取指标列表接口数据
	 * 
	 * @return IndicatorResult
	 */
	public IndicatorResult getIndicator() {

		service_code = "ZGGK_INDICTOR_LIST";

		String result = httpUtils.getSendRequest(ConnectionParamsURL.getIndicatorUrl(service_code, version));

		if (!Utils.isNull(result)) {
			return gson.fromJson(result, IndicatorResult.class);
		}

		return null;
	}

	/**
	 * 2.2获取公路PCI接口数据
	 * 
	 * @param province_code
	 *            所属省份code
	 * @param token
	 *            令牌
	 * @param road_code
	 *            公路编码
	 * @param indicator_id
	 *            指标ID
	 * 
	 * @return RoadPCIResult
	 */
	public RoadPCIResult getRoadsPCI(String province_code, String token, String road_code, String indicator_id) {

		service_code = "ZGGK_ROAD_PCI_SYSTEM";

		String result = httpUtils.getSendRequest(ConnectionParamsURL.getRoadsPCIUrl(service_code, version,
				province_code, token, road_code, indicator_id));

		if (!Utils.isNull(result)) {

			try {
				String temp = Gzip.unGzip(result);

				System.out.println("========返回的结果的为========" + temp);

				return gson.fromJson(temp, RoadPCIResult.class);
			} catch (IOException e) {
				System.out.println(e);
			}

		}

		return null;
	}

	/**
	 * 获取小图接口数据
	 * 
	 * @param token
	 *            令牌
	 * @param province_code
	 *            所属省份code
	 * @param going_direction
	 *            上下行
	 * @param road_code
	 *            公路编码
	 * @param road_station
	 *            桩号
	 * 
	 * @return 小图数据类
	 */
	public SmallImageResult getSmallImage(String token, String province_code, String going_direction, String road_code,
			String road_station) {

		service_code = "ZGGK_ROAD_SMALL_IMG_INFO";

		String result = httpUtils.getSendRequest(ConnectionParamsURL.getSmallImgUrl(service_code, version, token,
				province_code, going_direction, road_code, road_station));
		if (!Utils.isNull(result)) {
			return gson.fromJson(result, SmallImageResult.class);
		}

		return null;
	}

	/**
	 * 获取全国省份列表接口数据
	 * 
	 * @return
	 */
	public ProvincesResult getProvinces() {

		service_code = "ZGGK_CITY_LIST";

		String result = httpUtils.getSendRequest(ConnectionParamsURL.getProvincesList(service_code, version));

		if (!Utils.isNull(result)) {
			return gson.fromJson(result, ProvincesResult.class);
		}

		return null;
	}

	/**
	 * 获取搜索自动提示（省份列表）接口数据
	 * 
	 * @return 省份列表
	 */
	public SearchNoticesResult getSearchNotice(String search_str, String province_code) {

		service_code = "ZGGK_SEARCH_NOTICE_LIST";
		String search_strs = null;
		try {
			search_strs = URLEncoder.encode(search_str, "utf-8").toString();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		String

		result = httpUtils.getSendRequest(
				ConnectionParamsURL.getSearchNoticeUrl(service_code, version, search_strs, province_code, "1", "10"));

		if (!Utils.isNull(result)) {
			return gson.fromJson(result, SearchNoticesResult.class);
		}

		return null;
	}

	/**
	 * 5.4获取搜索自动提示接口数据 2016/10/18
	 * 
	 * @return 省份列表
	 */
	public SearchNoticesList getSearchNoticeList(String search_str, String province_code, String page,
			String pageSize) {

		service_code = "ZGGK_SEARCH_NOTICE_LIST";
		String search_strs = null;
		try {
			search_strs = URLEncoder.encode(search_str, "utf-8").toString();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		String

		result = httpUtils.getSendRequest(ConnectionParamsURL.getSearchNoticeUrl(service_code, version, search_strs,
				province_code, page, pageSize));

		if (!Utils.isNull(result)) {
			return gson.fromJson(result, SearchNoticesList.class);
		}

		return null;
	}

	/**
	 * 获取长按某点（高亮显示公路）接口数据
	 * 
	 * @return 省份列表
	 */
	public PressRoadInfoResult getLongPressUrl(String token, String province_code, String longitude, String latitude,
			String click_province_code) {

		service_code = "ZGGK_GET_ORAD_BY_POINT";

		String result = httpUtils.getSendRequest(ConnectionParamsURL.getLongPressRoadUrl(service_code, version, token,
				province_code, longitude, latitude, click_province_code));

		if (!Utils.isNull(result)) {
			return gson.fromJson(result, PressRoadInfoResult.class);
		}

		return null;
	}

	/**
	 * 5.3.城市列表
	 */
	public CityListResult getCityList() {
		Map<String, String> params = getMap();
		String url = forUrl(params, "ZGGK_CITY_LIST");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, CityListResult.class);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 9.1获取长按某点（高亮显示公路）接口数据
	 * 
	 * @param token
	 *            令牌
	 * @param province_code
	 *            省份编码
	 * @param city_code
	 *            城市编码
	 * @param county_code
	 *            县编码
	 * @param is_45_type
	 *            是否查询45类桥 VARCHAR Y 是 N 不是 默认为N
	 * @return
	 */
	public BridgeMarksResult getBridgeMarksUrl(String token, String province_code, String bridge_type,
			String is_get_detail, String city_code, String county_code, String is_45_type) {

		Map<String, String> params = getMap();
		params.put("province_code", Utils.isNull(province_code) ? "" : province_code);
		params.put("token", token);
		params.put("bridge_type", bridge_type);
		params.put("is_get_detail", is_get_detail);
		params.put("city_code", Utils.isNull(city_code) ? "" : city_code);
		params.put("county_code", Utils.isNull(county_code) ? "" : county_code);
		params.put("is_45_type", is_45_type);
		String url = forUrl(params, "ZGGK_PROVINCE_BRIDGE");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, BridgeMarksResult.class);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 11.4.省份高速公路列表
	 * 
	 * @param province_code
	 *            省份编码
	 * @return
	 */
	public ExpresswayListResult getExpresswayList(String province_code, String longitude, String latitude,
			String current_page, String page_size) {
		Map<String, String> params = getMap();
		params.put("province_code", province_code);
		params.put("token", CustomApp.app.token);
		params.put("longitude", longitude);
		params.put("latitude", latitude);
		params.put("current_page", current_page);
		params.put("page_size", page_size);
		String url = forUrl(params, "ZGGK_SERVICE_EXPRESSWAY_LIST");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, ExpresswayListResult.class);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 11.2.服务区列表
	 * 
	 * @param road_code
	 *            高速公路编码 VARCHAR 不传这个字段则更具经纬度返回附近的服务区，反之返回改高速公路上的所有服务区
	 * @param longitude
	 *            经度 VARCHAR
	 * @param latitude
	 *            纬度 VARCHAR
	 * @return
	 */
	public ServiceAreaListResult getServiceAreaList(String road_code, String longitude, String latitude) {
		Map<String, String> params = getMap();
		params.put("road_code", road_code);
		params.put("longitude", longitude);
		params.put("latitude", latitude);
		params.put("token", CustomApp.app.token);
		String url = forUrl(params, "ZGGK_PROVINCE_HIGH_WAY_DATA");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, ServiceAreaListResult.class);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 11.3.服务区详情
	 * 
	 * @param road_code
	 *            高速公路编码 VARCHAR 服务区所在高速公路的编码
	 * @param service_area_code
	 *            服务区编码 VARCHAR
	 * @return
	 */
	public ServiceAreaResult getServiceArea(String road_code, String service_area_code) {
		Map<String, String> params = getMap();
		params.put("road_code", road_code);
		params.put("service_area_code", service_area_code);
		params.put("token", CustomApp.app.token);
		String url = forUrl(params, "ZGGK_SERVICE_AREA_DETAILS");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, ServiceAreaResult.class);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 8.1.路况省份对比
	 * 
	 * @param province_code
	 *            多个省份英文逗号“,”相隔
	 * @return
	 */
	public RoadContrastResult roadContrast(String province_code) {
		Map<String, String> params = getMap();
		params.put("province_code", province_code);
		params.put("token", CustomApp.app.token);
		String url = forUrl(params, "ZGGK_ROAD_CONTRAST");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, RoadContrastResult.class);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 8.2.省份概况
	 * 
	 * @param province_code
	 *            省份编码
	 * @return
	 */
	public ProvinceSituationResult provinceSituation(String indicator_id, String province_code, String road_code) {
		Map<String, String> params = getMap();
		params.put("province_code", province_code);
		params.put("indicator_id", indicator_id);
		params.put("token", CustomApp.app.token);
		params.put("road_code", road_code);
		String url = forUrl(params, "ZGGK_PROVINCE_SITUATION");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, ProvinceSituationResult.class);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 9.3.45类桥分布
	 * 
	 * @param province_code
	 *            省份编码
	 * @return
	 */
	public FortyFiveBridgeResult fortyFiveBridge(String province_code, String Search_str) {
		Map<String, String> params = getMap();
		params.put("province_code", province_code);
		params.put("token", CustomApp.app.token);
		params.put("Search_str", Search_str);
		String url = forUrl(params, "ZGGK_45_BRIDGE_DISTRIBUTE_DATA");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, FortyFiveBridgeResult.class);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 8.3.省份路况数据
	 * 
	 * @param province_code
	 *            省份编码
	 * @param indicator_id
	 *            指标id
	 * @param order_rule
	 *            排序规则
	 * @param order_type
	 *            排序类型
	 * @return
	 */
	public ProvinceRoadDataResult provinceRoadData(String indicator_id, String province_code, String order_rule,
			String order_type) {
		Map<String, String> params = getMap();
		params.put("province_code", province_code);
		params.put("token", CustomApp.app.token);
		params.put("indicator_id", indicator_id);
		params.put("order_rule", order_rule);
		params.put("order_type", order_type);
		String url = forUrl(params, "ZGGK_PROVINCE_ROAD_DATA");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, ProvinceRoadDataResult.class);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 7.1接口 统计
	 * 
	 * @param statistical_type
	 * @param province_code
	 * @return
	 */
	public StatisticsModel statistical(String statistical_type, String province_code) {

		Map<String, String> params = getMap();
		params.put("token", CustomApp.app.token);
		params.put("statistical_type", statistical_type);
		params.put("province_code", province_code);
		String url = forUrl(params, "ZGGK_STATISTICAL_DATE");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, StatisticsModel.class);
		} catch (Exception e) {

		}
		return null;

	}

	/**
	 * 7.2统计接口
	 * 
	 * @param statistical_type
	 * @param province_code
	 * @return
	 */

	public StatisticsModelTwo newstatistical(String statistical_type, String province_code) {

		Map<String, String> params = getMap();
		params.put("token", CustomApp.app.token);
		params.put("statistical_type", constant.RANK_LEVEL);
		params.put("province_code", province_code);
		String url = forUrl(params, "ZGGK_STATISTICAL_INDICATOR");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, StatisticsModelTwo.class);
		} catch (Exception e) {

		}
		return null;

	}

	/**
	 * 7.3统计-路况统计
	 * 
	 * @param road_traffic_type
	 * @param province_code
	 * @param indicator_id
	 * @return
	 */
	public RoadTrafficResult roadTraffic(String road_traffic_type, String province_code, String indicator_id) {
		Map<String, String> params = getMap();
		params.put("token", CustomApp.app.token);
		params.put("road_traffic_type", road_traffic_type);
		params.put("province_code", province_code);
		params.put("indicator_id", indicator_id == null ? "" : indicator_id);
		String url = forUrl(params, "ZGGK_STATISTICAL_ROAD_TRAFFIC");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, RoadTrafficResult.class);
		} catch (Exception e) {
			System.out.println();
		}
		return null;
	}

	/**
	 * 11.1 省份服务区数据
	 * 
	 * @param province_code
	 *            省份编码
	 * @return
	 */
	public ServiceMarkerResult servicePositionResult(String city_code, String province_code) {
		Map<String, String> params = getMap();
		params.put("province_code", province_code);
		params.put("token", CustomApp.app.token);
		params.put("city_code", city_code);
		String url = forUrl(params, "ZGGK_PROVINCE_SERVICE_AREA");
		try {
			String json = httpUtils.getSendRequest(url);
			return gson.fromJson(json, ServiceMarkerResult.class);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 绑定手机接口说明：进入登录页面，点击登录，调用该接口,状态参数是空，返回100，直接调用登录接口登录，返回300、400、500返回登录页面。
	 * 如果返回200 ，弹出选择提示框，如果用户选择“是”，调用该接口，状态参数是“bind”,如果是“否”，参数状态是“cancel”;
	 * 返回结果按照刚刚上面说的操作
	 * 
	 * @param user_name
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param machine_code
	 *            imei
	 * @param state_code
	 *            绑定码”bind“,”cancel“
	 * @return （1）100 ，正常登录 （2）200，是否绑定。 （3）300，返回登录页面 （4）400，返回登录页面
	 *         （5）500，返回登录页面
	 */
	public bindPhoneModel bindPhone(String user_name, String pwd, String machine_code, String state_code) {
		Map<String, String> params = getMap();
		String data = pwd;
		String key = "roadmaint";
		String password_des = DesUtils.encode(key, data);
		params.put("user_name", user_name);
		params.put("machine_code", machine_code);
		params.put("state_code", state_code);
		params.put("pwd", password_des);
		String url = forUrl(params, "ZGGK_BIND_MACHINECODE");
		// Request request = new Request.Builder().url(url).build();
		// Response response = null;
		// try {
		// response = CustomApp.app.client.newCall(request).execute();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		try {
			String sendRequest = httpUtils.getSendRequest(url);
			return gson.fromJson(sendRequest, bindPhoneModel.class);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * 
	 * 
	 * 
	 * @param year
	 * @param province_code
	 * @param order_rule
	 * @param statistic_type
	 * @return
	 */
	// 7.7接口
	public MapStaticsModel MapStaticsbase(String year, String province_code, String order_rule, String order_type,
			String statistic_type) {
		Map<String, String> params = getMap();
		params.put("token", CustomApp.app.token);
		params.put("year", year);
		params.put("province_code", province_code);
		params.put("order_rule", order_rule);
		params.put("order_type", order_type);
		params.put("statistic_type", statistic_type);
		String url = forUrl(params, "ZGGK_STATISTIC_TYPE");
		try {
			String sendRequest = httpUtils.getSendRequest(url);
			String unGzip = Gzip.unGzip(sendRequest);

			return gson.fromJson(unGzip, MapStaticsModel.class);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * 
	 * 
	 * @param year
	 * @param province_code
	 * @param order_rule
	 * @param bridge_type
	 * @return
	 */
	public MapStaticsModel MapStaticsbridge(String year, String province_code, String order_rule, String order_type,
			String bridge_type) {
		Map<String, String> params = getMap();
		params.put("token", CustomApp.app.token);
		params.put("year", year);
		params.put("province_code", province_code);
		params.put("order_rule", order_rule);
		// params.put("order_type", order_type);
		if (order_type.equals(constant.NAME.toLowerCase())) {
			params.put("order_type", constant.BRIDGE_NAME);
		} else {
			params.put("order_type", constant.BRIDGE_MAIEL);
		}
		if (!Utils.isNull(bridge_type)) {
			params.put("bridge_type", bridge_type);
		}
		String url = forUrl(params, "ZGGK_STATISTICAL_ROAD_DISTRIBUTION_BRIDGE");
		try {
			String sendRequest = httpUtils.getSendRequest(url);
			String unGzip = Gzip.unGzip(sendRequest);
			System.out.println(unGzip);
			return gson.fromJson(unGzip, MapStaticsModel.class);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * 
	 * 
	 * @param year
	 * @param province_code
	 * @param order_rule
	 * @param tunnel_type
	 * @return
	 */
	public MapStaticsModel MapStaticsTunnel(String year, String province_code, String order_rule, String order_type,
			String tunnel_type) {
		Map<String, String> params = getMap();
		params.put("token", CustomApp.app.token);
		params.put("year", year);
		params.put("province_code", province_code);
		params.put("order_rule", order_rule);
		if (order_type.equals(constant.NAME.toLowerCase())) {
			params.put("order_type", constant.TUNNEL_NAME);
		} else {
			params.put("order_type", constant.TUNNEL_MAIEL);
		}
		if (!Utils.isNull(tunnel_type)) {
			params.put("tunnel_type", tunnel_type);
		}

		// params.put("order_type", order_type);
		String url = forUrl(params, "ZGGK_STATISTICAL_ROAD_DISTRIBUTION_TUNNEL");
		try {
			String sendRequest = httpUtils.getSendRequest(url);
			String unGzip = Gzip.unGzip(sendRequest);
			return gson.fromJson(unGzip, MapStaticsModel.class);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * 9.2
	 * 
	 * @param province_code
	 * @param bridge_code
	 * @return
	 */
	public SingleBridgeDataResult getSingleBridgeData(String province_code, String bridge_code) {

		Map<String, String> params = getMap();
		params.put("token", CustomApp.app.token);
		params.put("version", version);
		params.put("province_code", province_code);
		params.put("bridge_code", bridge_code);

		String url = forUrl(params, "ZGGK_BRIDGE_DATA");
		try {
			String sendRequest = httpUtils.getSendRequest(url);
			String unGzip = Gzip.unGzip(sendRequest);
			return gson.fromJson(unGzip, SingleBridgeDataResult.class);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 9.4 获取桥梁更具体的信息
	 * 
	 * @param province_code
	 * @param bridge_code
	 */
	public BridgeDetailInfoResult bridgeDetailInfo(String province_code, String bridge_code) {
		Map<String, String> params = getMap();
		params.put("token", CustomApp.app.token);
		params.put("province_code", province_code);
		params.put("bridge_code", bridge_code);
		String url = forUrl(params, "ZGGK_BRIDGE_DETAIL_INFO");
		try {
			String sendRequest = httpUtils.getSendRequest(url);
			//String unGzip = Gzip.unGzip(sendRequest);
			return gson.fromJson(sendRequest, BridgeDetailInfoResult.class);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 获取桥梁图片 2016/10/21
	 * 
	 * @param token
	 *            令牌
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

	public BridgeImagesBeanResult getBridgeImages(String province_code, String bridge_code, String image_class,
			String damage_id) {
		Map<String, String> params = getMap();
		params.put("token", CustomApp.app.token);
		params.put("province_code", province_code);
		params.put("bridge_code", bridge_code);
		params.put("image_class", image_class);
		params.put("damage_id", damage_id);
		String url = forUrl(params, "ZGGK_BRIDGE_IMAGE_INFO");
		try {
			String sendRequest = httpUtils.getSendRequest(url);
			//String unGzip = Gzip.unGzip(sendRequest);
			return gson.fromJson(sendRequest, BridgeImagesBeanResult.class);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 
	 * 9.5获取桥梁图片
	 * 
	 * @param province_code
	 * @param bridge_code
	 * @param image_class
	 * @return
	 */

	public BridgeImageModel getBridgeImage(String province_code, String bridge_code, String image_class) {

		Map<String, String> params = getMap();
		params.put("token", CustomApp.app.token);
		params.put("version", version);
		params.put("province_code", province_code);
		params.put("bridge_code", bridge_code);
		params.put("image_class", "BASE");
		String url = forUrl(params, "ZGGK_BRIDGE_IMAGE_INFO");
		try {
			String sendRequest = httpUtils.getSendRequest(url);
			return gson.fromJson(sendRequest, BridgeImageModel.class);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}
}
