package com.mobo.zggkgisandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.esri.android.map.Callout;
import com.esri.android.map.CalloutStyle;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.GraphicsLayer.RenderingMode;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.LocationDisplayManager.AutoPanMode;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnLongPressListener;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.renderer.SimpleRenderer;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.mobo.libupdate.LibAutoUpdate.OnStartInstallListener;
import com.mobo.zggkgisandroid.AddKpi.AddKpiActivity;
import com.mobo.zggkgisandroid.Bridge.BridgeDetailInfoActivity;
import com.mobo.zggkgisandroid.City.SelectCityActivity;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgeMarksInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgeNationalInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgePositionInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.LonLatsInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.PCIInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadLonLatProvince;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadPCIInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadsInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.SearchInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.ServiceMarkerInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.ServiceNationalInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.ServicePositionInfo;
import com.mobo.zggkgisandroid.DBEntity.SearchContent;
import com.mobo.zggkgisandroid.DBHelper.DBManager;
import com.mobo.zggkgisandroid.FortyFive.FortyFiveBridgeActivity;
import com.mobo.zggkgisandroid.Info.CityListActivity;
import com.mobo.zggkgisandroid.Info.StatisticsActivity;
import com.mobo.zggkgisandroid.Login.LoginActivity;
import com.mobo.zggkgisandroid.More.MorePopupWindow;
import com.mobo.zggkgisandroid.PlayImage.PlayImage;
import com.mobo.zggkgisandroid.PlayImage.playImageActivity;
import com.mobo.zggkgisandroid.Search.SearchActivity;
import com.mobo.zggkgisandroid.ServiceArea.ServiceAreaDetailsActivity;
import com.mobo.zggkgisandroid.Silde.SildeListener;
import com.mobo.zggkgisandroid.Silde.SildeManager;
import com.mobo.zggkgisandroid.Utils.BridgeHelper;
import com.mobo.zggkgisandroid.Utils.MapUtils;
import com.mobo.zggkgisandroid.Utils.RoadsHelper;
import com.mobo.zggkgisandroid.Utils.SearchHelper;
import com.mobo.zggkgisandroid.Utils.ServiceHelper;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.Utils.constant;
import com.mobo.zggkgisandroid.WebInterface.ConnectionInteface;
import com.mobo.zggkgisandroid.WebModel.BaseResult;
import com.mobo.zggkgisandroid.WebModel.BridgeImageModel;
import com.mobo.zggkgisandroid.WebModel.BridgeMarksResult;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.MapStaticsModel;
import com.mobo.zggkgisandroid.WebModel.MilListResult;
import com.mobo.zggkgisandroid.WebModel.MilListResult.station;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult.PlayImageMess.image_list;
import com.mobo.zggkgisandroid.WebModel.PressRoadInfoResult;
import com.mobo.zggkgisandroid.WebModel.ProvinceSituationResult;
import com.mobo.zggkgisandroid.WebModel.ProvinceSituationResult.ProvinceSituation;
import com.mobo.zggkgisandroid.WebModel.RoadPCIResult;
import com.mobo.zggkgisandroid.WebModel.SearchInfoResult;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesList.SearchListBean;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesResult;
import com.mobo.zggkgisandroid.WebModel.SearchResult;
import com.mobo.zggkgisandroid.WebModel.ServiceMarkerResult;
import com.mobo.zggkgisandroid.WebModel.SingleBridgeDataResult;
import com.mobo.zggkgisandroid.photoView.PhotoView;
import com.mobo.zggkisandroid.AdapterClass.BridgesAdapter;
import com.mobo.zggkisandroid.AdapterClass.BridgesPrAdapter;

/**
 * 此类为地图的主类 显示地图，和搜索功能的界面
 * 
 */
public class MainActivity extends Activity {

	/***************** 声明控件 *********************/

	// 搜索栏
	private LinearLayout vSearchBarLayout;// 搜索栏
	private ImageButton vBackBtn; // 返回按钮
	private View vBlackView;// 空白view
	private TextView vSearchEdtTxt;//搜索文本
	// private AutoCompleteTextView vSearchEdt;// 搜索文字输入框
	// private Button vSearchBtn;// 搜索按钮

	// 屏幕右侧按钮
	private ImageButton vRoadSkillKpi;// 道路技术指标按钮
	private ImageButton vPlayImageBegin;// 从开始播放当前选中的公路
	private ImageButton vRoadInfoIbt;// 公路信息弹窗按钮
	private ImageButton vRoadNextStationIbt;// 下一个桩号
	private ImageButton vRoadForwardStationIbt;// 上一个桩号
	// private ImageButton vLocationIb;// 定位按钮

	private MapView vMap;// 地图控件
	private TextView vshowDetailIbt;// 搜索结果 点击查看详情按钮

	private LinearLayout vSearchResultContainer;// 搜索结果容器布局
	private ImageView vSearchThumbImg;// 搜索缩略图
	private GraphicsLayer graphicsLayer = new GraphicsLayer(RenderingMode.DYNAMIC);
	private PopupWindow roadIndicatorPopWindow;// 路况选择弹窗

	private ListView vlistView;
	// private LinearLayout vRoadDetailKpi;// 单条公路紫色显示时布局

	private LinearLayout vZoomContainer;//缩放

	// 底部按钮
	private LinearLayout vNearbyLayout, vRoadIndiatorLayout, vSearchLayout, vInfoLayout, vGpsLayout;// 实景，路况，查询，统计，导航
	private LinearLayout vBottomRoadInfoLayout;// 概况，路况，统计
	private TextView vProvinceChoicesTxt;// 当前省份

	// 单条公路指标信息底部弹窗
	private View roadIndicatorBottomView;//指标底部
	private LinearLayout vblackView;// 指标信息列表的容器
	private LinearLayout vRoadNormalDetail;// 单条公路的信息
	private TextView vRoadNameTxt;//道路名称
	// private TextView vRoadIndicatorNameTxt;// 指标名称
	private TextView vCurrentRoadWidthTxt;// 路面全宽
	private TextView vCurrentRoadFaceTypeTxt;// 路面类型
	private TextView vCurrentDailyTrafficNumTxt;// 日交通量
	private TextView vCurrentBuildYearsTxt;// 修建年度
	private TextView vCurrentConservationYearTxt;// 养护年度 ,公路设施
	private TextView vCurrentRoadSurConditionTxt;// 路面状况
	private TextView vCurrentRoadDataYearTxt;// 数据更新

	private TextView VCurrentConservationAdviseTxt;// 养护年度

	private TextView vCurrentStationTxt;// 当前桩号
	private TextView vCurrentLocationTxt;// 当前位置
	private TextView vCurrentFeedUnitTxt;// 管养单位
	// private TextView vKpiRoadsValue;// 当前桩号的指标值

	private ImageView vCurrentFeedUnitImg;// 管养单位箭头

	private LinearLayout vBottomBtnLayout;// 附近，信息所在布局

	// 桥梁
	private LinearLayout vBottomBridgeImgLayout;// 桥梁底部按钮
	private LinearLayout vBridgeImgBtn, vComeToBtn;// 前方图像，路况指标

	private RelativeLayout vLargeImgLayout;// 大图布局

	private RelativeLayout vBottomDialogLayout;// 底部dialog
	private LinearLayout vBottomLargeImgLayout;// 底部大图容器
	// private ImageView upImg;// 底部图片的上一张
	// private ImageView downImg;// 底部图片的下一张
	private PhotoView myImageVIew;// 底部大图imgview
	private ImageButton bottomScreen;// 地图底部大图全屏按钮
	private ImageButton bottomBack;// 地图底部大图返回按钮
	private ImageView up;// 地图底部向上按钮
	private ImageView down;// 地图底部向下按钮
	private TextView vRoadImgYearTxt;// 图片年份
	private TextView vRoadImgDirectionTxt;// 图片上下行

	private LinearLayout vBottomStatistic;// 统计桥梁返回布局
	private LinearLayout vBottomStatisticLayout;// 统计桥梁返回底部包含布局
	private TextView vBridgeCounttxtView;//桥梁城市
	private TextView vBridgeMeterView;//桥梁距离
	private TextView vBridgeInfoTxtv;//桥梁信息
	private LinearLayout vBottomRoadLeftLly;//道路
	/***************** 变量 *********************/
	private ThreadWithProgressDialog threadManage;// 线程管理者

	private FeatureResult fr = null;// 查询返回的图层信息对象
	private MapUtils mapUtils;// 地图操作工具类
	private RoadsHelper roadsHelper;//道路管理类
	private ServiceHelper serviceHelper;//服务管理类
	private LayoutInflater inflater;//布局加载

	private SearchHelper searchHelper;// 搜索结果处理工具类

	// 公路
	private GraphicsLayer transparentLayer = new GraphicsLayer();//三方地图交通
	private GraphicsLayer provinceLayer = new GraphicsLayer();//三方地图省份
	private String road_name;// 公路名称
	private String road_station;// 桩号
	private String road_code;// 线路编码
	private RoadInfo roadInfo;// 单条公路的信息
	private String going_direction;// 桩号上下行
	private SearchInfoResult searchInfoResult;// 2.5接口，公路经纬度信息
	private String startStation;// 起点桩号
	private String endStation;// 终点桩号

	// 公路指标相关
	private RoadPCIResult roadIndicatorsResult;// 查询公路的各种指标信息
	private String query_indicator_id;// 查询多条公路的指标信息
	private Graphic[] roadIndicatorGraphics;//公路指标绘制

	// 定位
	private String locX, locY;// 定位的经纬度
	private GraphicsLayer gpsLayer = new GraphicsLayer();//gps绘制

	// 搜索
	private SearchResult searchResult;// 查询结果类
	private int searchType;// 搜索查询标识
	private String nearLocX;// 附近搜索经度
	private String nearLocY;// 附近搜索纬度
	private String input_str;// 输入的字符串

	private ConnectionInteface connectionInteface;//接口工具
	private Point point;// 点选政区着点

	// 长按
	private String longPress_longitude;// 长按某点的经度
	private String longPress_latitude;// 长按某点的纬度
	private PressRoadInfoResult pressRoadInfoResult;// 长按返回结果类
	private String click_province_code;// 长按点所在路线编码

	// 暂时无用
	private SearchNoticesResult searchNoticesResult;// 搜索自动提示结果类

	private String province_code = "123001";// 省份编码
	private String province_name = "全国";// 省份名称
	private String city_code = "";// 城市编码
	private String city_name = "";// 城市名称

	private String pre_province_code = "";// 记录上一次获取里程条的省份编码
	private String pre_road_code = "";// 记录上一次获取里程条的线路编码
	private ArrayList<station> mileageListstation;//点集合

	private int index_Liststation = -1;//栈点
	private int time;//时间
	private BridgeMarksInfo info;//桥梁标记详情
	private boolean isPr = false;// 是否经过了带数字的气泡显示
	private ArcGISTiledMapServiceLayer normal_layer = new ArcGISTiledMapServiceLayer(
			"http://106.37.229.146:90/arcgis/rest/services/iRoadDT_G1/MapServer");//服务地址
	// private ArcGISDynamicMapServiceLayer featureLayer = new
	// ArcGISDynamicMapServiceLayer(
	// "http://106.37.229.146:90/arcgis/rest/services/iRoadBZ_G1/MapServer");
	private ArcGISTiledMapServiceLayer feature_layer = new ArcGISTiledMapServiceLayer(
			"http://106.37.229.146:90/arcgis/rest/services/iRoadBZ_G2/MapServer");//服务地址

	private Envelope envelope;// 地图显示范围

	// 定位
	private PictureMarkerSymbol locationSymbol;// 定位图标
	private LocationDisplayManager lDisplayManager;//控制显示
	private Location loc;//坐标位置
	private Point mapPoint;// 定位地点
	private Drawable drawable;// 方向图像
	private int dir_uid = -1;//uid
	private int start_flag = -1;// 是否定位标识

	private CityList currentCity;// 当前所选的省份
	private CityList dCity;// 当前定位的省份

	private LonLatsInfo imagemoveLatsInfo;// 图片移动十字针所在桩号经纬度信息

	// 十字针
	private Point startPoint;// 十字针的初始位置的地图坐标点
	private int shizizhen_uid;// 十字针标识的uid
	private View pointInfoView;// 十字针上小弹窗view
	private TextView pointInfoLocationTxt;// 小弹窗上当前位置
	private TextView pointInfoStationTxt;// 小弹窗上的当前桩号
	private Callout callout;// 小浮窗
	private String currentFeedUnit;// 当前管养单位
	private String currentPosition;// 当前位置
	private boolean isMoveShizi = false;//是否移动十字针

	private Map<String, Object> shizizhenMap = new HashMap<String, Object>();
	private int[] shiziGraphics = new int[0];

	// private View shizizhenView;
	private PictureMarkerSymbol pSymbol;// 正常十字针
	private PictureMarkerSymbol pRedSymbol;// 红色十字针
	private Graphic shizizhenGraphic;//绘制十字针
	private GraphicsLayer shizizhenLayer = new GraphicsLayer(RenderingMode.DYNAMIC);//十字针类型
	private ArrayList<LonLatsInfo> moveLatsInfoList = new ArrayList<LonLatsInfo>();// 十字针所在路线的经纬度集合

	private PlayImageResult playImageResult;// 底部大图异步结果集
	private Bitmap bottomBitmap;// 地图bitmap;
	private ArrayList<image_list> arrayimage_list = new ArrayList<PlayImageResult.PlayImageMess.image_list>();// 图片结果缓冲
	private Point MoveEndPoint;// 手滑动抬起位置的地图坐标点
	private int locPrvinceFlag;// 省份编码定位还是点击定位 0点击 1省份编码

	// 路况
	private RoadPCIResult roadsIndicatorResult;//pci结果集
	private int kpiORsearch = -1;// 0代表路况，1代表搜索
	private PCIInfo provinceRoadPCIInfo;// 省份某条公路的指标信息集合
	private RoadPCIResult roadPCIResult;// 省份某条公路的指标信息集合
	private String currentIndicatorValue;// 当前位置的指标信息
	private String currentTecLevel;// 当前技术等级
	private String currentRoadWidth;// 当前路面宽度
	private String currentCarRoadNum;// 当前车道数
	private String currentRoadFaceType;// 当前路面类型
	private String currentDailyTrafficNum;// 日交通量
	private String currentConservationYears;// 养护年度
	private String currentBuildYears;// 修建年度
	private String currentRoadCondition;// 路面状况
	private String currentRoadDataYear;// 数据更新年度
	private String currentRoadDirection;// 上下行

	private String currentConservationAdvise;// 养护建议

	// 统计
	private ProvinceSituation provinceSituation;// 省份统计结果类

	private String statisticalType;// 从统计返回标识
	private ProvinceSituationResult provinceSituationResult;//站点结果集

	// 桥梁
	private GraphicsLayer bridgeLayer = new GraphicsLayer();//绘制桥
	private BridgeMarksResult bridgeMarksResult;// 桥梁结果集
	private SildeManager sildeManager;//可滑动的dialog
	private BridgeHelper bridgeHelper;//桥梁工具类
	private BridgePositionInfo bridgePositionInfo;// 省份桥梁
	// private BridgeNationalInfo bridgeNationalInfo;// 全国桥梁
	private ArrayList<BridgePositionInfo> bridgePositionInfos = new ArrayList<BridgePositionInfo>();// 省份桥梁集合
	private ArrayList<BridgeNationalInfo> bridgeNationalInfos = new ArrayList<BridgeNationalInfo>();// 全国桥梁集合
	private String bridge_station;// 桥梁中心桩号
	private ArrayList<BridgeMarksInfo> bmNextList;// 省级所有桥梁集合
	private BridgeMarksInfo bridgeMarksInfo;// 市级桥梁集合
	private boolean isFourOrFiveBridge = false;// 是否是四五类桥
	private String bridge_code;//桥梁编码
	private String bridge_name;//桥梁名称
	private int bridge_image_index;// 桥梁图片索引

	private String image_type;// 为ROAD 或BRIDGE
	private SearchListBean searchBridgeType;//桥梁类型

	// 服务区
	private ServiceMarkerInfo serviceMarkerInfo;//服务标志信息
	private ArrayList<ServiceNationalInfo> serviceNationalInfoList = new ArrayList<ServiceNationalInfo>();// 全国服务区
	private ArrayList<ServicePositionInfo> servicePositionInfoList = new ArrayList<ServicePositionInfo>();// 省份服务区

	private String search_type;// 搜索返回的搜索类型
	private int function_type;// 桥梁，服务区，视频，灾毁等
	private String bridge_type;// 桥梁种类
	private String bridge_type_value;//桥梁类型

	// private int TYPE_ROAD = 0;// 公路
	private final int TYPE_BRIDGE = 1;// 桥梁
	private final int TYPE_SERVICE_AREA = 2;// 服务区
	private final int TYPE_TUNNEL = 3;// 隧道
	private final int TYPE_FACILITY = 4;// 设施
	private final int TYPE_CONSTRUCTION = 5;// 施工
	private final int TYPE_DAMAGE = 6;// 灾毁
	private final int TYPE_VIDEO = 7;// 视频

	private boolean isClearAll = true;// 返回地图初始化状态
	private boolean isSelectProvince = true;// 是否是选择省份
	private boolean isBackToRoads = false;// 返回到多条公路状态
	private boolean statisticFlag = false;//从统计返回

	public MilListResult millList;// 里程条
	private final float STEP = 0.025F;// 桩号增加的固定步伐
	private String preStation;//station

	private MorePopupWindow morePopupWindow;// 更多弹出框
	private CityList tCity;// 统计所传省
	private String from;// 从统计回传，statistic
	// private boolean rqiSelect = false;// 实景前是否选过rqi
	private boolean vSearchVisit = true;// 是否显示线路详情
	private BridgeImageModel bridgeImage;// 桥梁图片返回

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CustomApp.app.mActiveLists.add(this);

		initView();

	
		initGps();
		if (start_flag != 1) {

			
			initLocation();
		}
		
		if ("0".equals(CustomApp.app.privilege) && CustomApp.app.clickLogin) {
			CustomApp.app.clickLogin = false;
			startTheard(2);
		}
		/****************************************** 测试 ***************************/

		// 滑动
		vMap.setOnTouchListener(new MapOnTouchListener(MainActivity.this, vMap) {

			@Override
			public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {

				index_Liststation = -1;

				if (startPoint != null) {

					// 判断手按下的点是否在十字针上
					shiziGraphics = shizizhenLayer.getGraphicIDs(from.getX(), from.getY(), 15, 1);

					if (shiziGraphics.length != 0) {

						isMoveShizi = true;

						time = 0;

						// 有公路高亮显示十字针标识才可滑动
						if (moveLatsInfoList.size() != 0) {

							MoveEndPoint = vMap.toMapPoint(new Point(Double.valueOf(to.getX()), Double.valueOf(to.getY())));

							int[] roadGraphicId = transparentLayer.getGraphicIDs(to.getX(), to.getY(), 20, 1);

							// 移动后点在公路上
							if (roadGraphicId.length != 0) {

								Graphic line = transparentLayer.getGraphic(roadGraphicId[0]);

								mSetDynamicData(line, new Point(to.getX(), to.getY()), null);

							}
							// 移动后点不再公路上
							else {

								/********************* 移动十字针到最近的桩号上 **********************/

								LonLatsInfo latsInfo = mapUtils.moveToNextStation(road_station, moveLatsInfoList);

								if (latsInfo != null) {
									mSetDynamicData(null, null, latsInfo);
								}
							}
							return true;
						} else {
							return super.onDragPointerMove(from, to);
						}
					} else {

						return super.onDragPointerMove(from, to);
					}
				} else {
					return super.onDragPointerMove(from, to);
				}
			}

			@Override
			public boolean onDragPointerUp(MotionEvent from, MotionEvent to) {

				MoveEndPoint = vMap.toMapPoint(new Point(Double.valueOf(to.getX()), Double.valueOf(to.getY())));

				if (moveLatsInfoList.size() != 0) {

					// 判断手按下的点是否在十字针上
					shiziGraphics = shizizhenLayer.getGraphicIDs(from.getX(), from.getY(), 15, 1);

					if (shiziGraphics.length != 0) {

						LonLatsInfo latsInfo = mapUtils.findLonLats(road_station, moveLatsInfoList);

						if (latsInfo != null) {

							startPoint = new Point(Double.valueOf(latsInfo.getLongitude()), Double.valueOf(latsInfo.getLatitude()));

							shizizhenLayer.updateGraphic(shizizhen_uid, pSymbol);
							shizizhenLayer.updateGraphic(shizizhen_uid, startPoint);

							road_station = latsInfo.getRoad_station();
							currentPosition = latsInfo.getCurrent_position();
							currentFeedUnit = latsInfo.getCurrent_feed_unit();

							if (road_station == null) {
								road_station = "";
							}

							if (currentPosition == null) {
								currentPosition = "";
							}
							if (currentFeedUnit == null) {
								currentFeedUnit = "";
							}

						
							if (roadIndicatorsResult != null || provinceRoadPCIInfo != null) {

								currentTecLevel = latsInfo.getBridge_technical_grade();
								currentRoadWidth = latsInfo.getRoad_way_width();
								currentCarRoadNum = latsInfo.getRoad_lane_num();
								currentRoadFaceType = latsInfo.getRoad_face_type();
								currentIndicatorValue = latsInfo.getCurrent_indicator_value();

								if (currentTecLevel == null) {
									currentTecLevel = "";
								}
								if (currentRoadWidth == null) {
									currentRoadWidth = "";
								}
								if (currentCarRoadNum == null) {
									currentCarRoadNum = "";

								}
								if (currentRoadFaceType == null) {
									currentRoadFaceType = "";
								}
								if (currentIndicatorValue == null) {
									currentIndicatorValue = "";
								}

								// vCurrentTecLevelTxt.setText(currentTecLevel);
								vCurrentRoadWidthTxt.setText(currentRoadWidth + "m");
								// vCurrentCarRoadNumTxt
								// .setText(currentCarRoadNum);
								vCurrentRoadFaceTypeTxt.setText(currentRoadFaceType);
								// vCurrentIndicatorNumTxt
								// .setText(currentIndicatorValue);

							
								pointInfoStationTxt.setText(mapUtils.changeStationToStandard(road_station) + " " + query_indicator_id.toUpperCase()

								+ currentIndicatorValue);

							} else {

								vCurrentFeedUnitTxt.setText(currentFeedUnit);

								// 小浮窗
								pointInfoStationTxt.setText(mapUtils.changeStationToStandard(road_station));

							}

							// 小浮窗

							pointInfoLocationTxt.setText(road_code + " " + currentPosition);

							vCurrentStationTxt.setText(mapUtils.changeStationToStandard(road_station));
							vCurrentLocationTxt.setText(currentPosition);

							if (callout != null && callout.isShowing()) {

								callout.move(startPoint);
							}

							// 底部图片
							if (vBottomLargeImgLayout.getVisibility() == View.VISIBLE) {
								startTheard(7);
							}

						}
					} else {

						if (isMoveShizi) {

							// 底部图片
							if (vBottomLargeImgLayout.getVisibility() == View.VISIBLE) {
								startTheard(7);
							}
						}

					}

				}

				isMoveShizi = false;
				return super.onDragPointerUp(from, to);
			}

		});

	}

	/**
	 * 
	 * 初始化控件
	 * 
	 */
	public void initView() {

		/****************************** 地图显示初始化相关 ***********************************/

		vMap = (MapView) findViewById(R.id.map);
		vMap.addLayer(normal_layer);
		vMap.addLayer(provinceLayer);
		// vMap.setMapBackground(0xffffff, 0, 0, 0);
		vMap.addLayer(transparentLayer);
		vMap.addLayer(graphicsLayer);
		// vMap.addLayer(featureLayer);
		envelope = new Envelope();
		envelope.setXMin(95.92488868);
		envelope.setYMin(14.236114);
		envelope.setXMax(125.32730256);
		envelope.setYMax(38.47956465);
		// vMap.setExtent(envelope);

		vMap.setEsriLogoVisible(false);

		vMap.setExtent(envelope);
		// vMap.setMaxExtent(normal_layer.getExtent());
		// vMap.setScale(9000000);

		vMap.addLayer(feature_layer);
		vMap.addLayer(gpsLayer);
		vMap.addLayer(shizizhenLayer);
		vMap.addLayer(bridgeLayer);

		vSearchBarLayout = (LinearLayout) findViewById(R.id.search_layout);
		vBackBtn = (ImageButton) findViewById(R.id.search_back);
		vBackBtn.setVisibility(View.GONE);

		vBlackView = findViewById(R.id.search_black_view);
		vBlackView.setVisibility(View.GONE);

		// vSearchEdt = (AutoCompleteTextView)
		// findViewById(R.id.search_details);
		// vSearchBtn = (Button) findViewById(R.id.search_btn);
		threadManage = new ThreadWithProgressDialog();
		inflater = getLayoutInflater();

		vSearchResultContainer = (LinearLayout) findViewById(R.id.search_result_container);
		vSearchResultContainer.setVisibility(View.GONE);

		vZoomContainer = (LinearLayout) findViewById(R.id.zoom_container_layout);

		vNearbyLayout = (LinearLayout) findViewById(R.id.bottom_nearby_layout);
		vRoadIndiatorLayout = (LinearLayout) findViewById(R.id.bottom_road_indicator_layout);
		vSearchLayout = (LinearLayout) findViewById(R.id.bottom_search_layout);
		vInfoLayout = (LinearLayout) findViewById(R.id.bottom_info_layout);
		vGpsLayout = (LinearLayout) findViewById(R.id.bottom_gps_layout);

		vProvinceChoicesTxt = (TextView) findViewById(R.id.province_choices_txt);
		vSearchEdtTxt = (TextView) findViewById(R.id.search_edit_txt);

		// 隐藏底部弹窗
		vshowDetailIbt = (TextView) findViewById(R.id.search_see_detail);
		vshowDetailIbt.setVisibility(View.GONE);
		vBottomBtnLayout = (LinearLayout) findViewById(R.id.bottom_info_container);

		vBottomDialogLayout = (RelativeLayout) findViewById(R.id.bottom_scroll_dialog_layout);
		vBottomDialogLayout.setVisibility(View.GONE);

		vBottomRoadInfoLayout = (LinearLayout) findViewById(R.id.bottom_road_info_container);
		vBottomRoadInfoLayout.setVisibility(View.GONE);

		// 桥梁（前方图像，到这里去）
		vBottomBridgeImgLayout = (LinearLayout) findViewById(R.id.bottom_road_imgKpi_container);
		vBottomRoadLeftLly = (LinearLayout) findViewById(R.id.bottom_road_left_layout);
		vBottomBridgeImgLayout.setVisibility(View.GONE);
		vBridgeImgBtn = (LinearLayout) vBottomBridgeImgLayout.findViewById(R.id.bottom_road_img_layout);
		vComeToBtn = (LinearLayout) vBottomBridgeImgLayout.findViewById(R.id.bottom_road_pci_layout);

		/**************************** 屏幕右侧按钮 ***********************************/
		// 技术指标
		vRoadSkillKpi = (ImageButton) findViewById(R.id.road_skill_kpi);
		vRoadSkillKpi.setVisibility(View.GONE);
		roadIndicatorBottomView = inflater.inflate(R.layout.road_current_info_layout, null);

		// 开始播放
		vPlayImageBegin = (ImageButton) findViewById(R.id.play_image_begin);
		vPlayImageBegin.setVisibility(View.GONE);
		// 公路信息弹窗显示按钮
		vRoadInfoIbt = (ImageButton) findViewById(R.id.road_info_ib);
		vRoadInfoIbt.setVisibility(View.GONE);

		// 上一个桩号
		vRoadNextStationIbt = (ImageButton) findViewById(R.id.next_station_ibt);
		vRoadNextStationIbt.setVisibility(View.GONE);

		// 下一个桩号
		vRoadForwardStationIbt = (ImageButton) findViewById(R.id.forward_station_ibt);
		vRoadForwardStationIbt.setVisibility(View.GONE);

		/******************************* 公路底部大图相关 *********************************/
		// 公路底部大图
		vLargeImgLayout = (RelativeLayout) findViewById(R.id.bottom_large_img_layout_ic);
		myImageVIew = (PhotoView) vLargeImgLayout.findViewById(R.id.bottom_large_image);
		bottomScreen = (ImageButton) vLargeImgLayout.findViewById(R.id.bottom_image_all_screen_ibt);
		bottomBack = (ImageButton) vLargeImgLayout.findViewById(R.id.bottom_image_back);
		myImageVIew.setZoomable(true);

		up = (ImageView) vLargeImgLayout.findViewById(R.id.bottom_play_up);

		up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (image_type.equals(constant.BIRDGE)) {
					int correct = Utils.isCorrect(bridgeImage, true);
					if (correct == 0 && bridgeImage.getResults().get(0) != null && bridgeImage.getResults().get(0).getImage_list() != null) {
						if (bridge_image_index < bridgeImage.getResults().get(0).getImage_list().size() - 1) {
							bridge_image_index++;
							myImageVIew.setImageBitmap(mapUtils.handlerBitmap(bridgeImage.getResults().get(0).getImage_list().get(bridge_image_index)
									.getImage_data()));
						} else {
							CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "没有跟多的数据了");
						}

					} else {
						CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "无图片数据");
					}
				} else {
					if (Float.valueOf(road_station) < Float.valueOf(endStation)) {
						if (preStation == null) {
							preStation = road_station;
						}
						road_station = (Float.valueOf(road_station) + STEP) + "";
						startTheard(7);
						if (roadIndicatorsResult != null || provinceRoadPCIInfo != null) {

							pointInfoStationTxt.setText(PlayImage.creatNewStation(road_station)

							+ query_indicator_id.toUpperCase() + " " + currentIndicatorValue);

						} else {

							pointInfoStationTxt.setText(PlayImage.creatNewStation(road_station));
						}
						vCurrentStationTxt.setText(PlayImage.creatNewStation(road_station));
						pointInfoStationTxt.setText(PlayImage.creatNewStation(road_station));
						if (Float.valueOf(road_station) - Float.valueOf(preStation) >= 1) {
							int positon = getPositon(preStation);
							if (positon >= 0) {
								preStation = moveLatsInfoList.get(positon + 1).getRoad_station();

							}
							while (Float.valueOf(road_station) - Float.valueOf(preStation) >= 1) {
								int positon2 = getPositon(preStation);
								if (positon2 >= 0) {
									preStation = moveLatsInfoList.get(positon2 + 1).getRoad_station();

								} else {
									return;
								}

							}
							setShiZiZhen(moveLatsInfoList.get(getPositon(preStation)));
						}

					} else {
						CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "暂时没有更多的数据了");
					}

				}
			}
		});
		down = (ImageView) vLargeImgLayout.findViewById(R.id.bottom_play_down);
		down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (image_type.equals(constant.BIRDGE)) {
					int correct = Utils.isCorrect(bridgeImage, true);
					if (correct == 0 && bridgeImage.getResults().get(0) != null && bridgeImage.getResults().get(0).getImage_list() != null) {
						if (bridge_image_index > 0) {
							bridge_image_index--;
							myImageVIew.setImageBitmap(mapUtils.handlerBitmap(bridgeImage.getResults().get(0).getImage_list().get(bridge_image_index)
									.getImage_data()));
						} else {
							CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "没有跟多的数据了");
						}
					} else {
						CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "无图片数据");
					}

				} else {

					if (Float.valueOf(road_station) > Float.valueOf(startStation)) {
						if (preStation == null) {
							preStation = road_station;
						}
						road_station = Float.valueOf(road_station) - STEP + "";
						startTheard(7);
						if (roadIndicatorsResult != null || provinceRoadPCIInfo != null) {

							pointInfoStationTxt.setText(PlayImage.creatNewStation(road_station)

							+ query_indicator_id.toUpperCase() + " " + currentIndicatorValue);

						} else {

							pointInfoStationTxt.setText(PlayImage.creatNewStation(road_station));
						}
						vCurrentStationTxt.setText(PlayImage.creatNewStation(road_station));

						if (Math.abs(Float.valueOf(road_station) - Float.valueOf(preStation)) >= 1) {
							int positon = getPositon(preStation);
							if (positon >= 0) {
								preStation = moveLatsInfoList.get(positon - 1).getRoad_station();

							}
							while (Math.abs(Float.valueOf(road_station) - Float.valueOf(preStation)) >= 1) {
								int positon2 = getPositon(preStation);
								if (positon2 >= 0) {
									preStation = moveLatsInfoList.get(positon - 1).getRoad_station();

								} else {
									return;
								}

							}
							setShiZiZhen(moveLatsInfoList.get(getPositon(preStation)));
						}

					} else {
						CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "暂时没有更多的数据了");
					}

				}
			}
		});

		/***************************** 工具类初始化相关 ***********************************/

		mapUtils = new MapUtils(MainActivity.this, graphicsLayer, transparentLayer, provinceLayer, vMap);
		// 搜索结果分类处理工具
		searchHelper = new SearchHelper(MainActivity.this, mapUtils, vMap, graphicsLayer, vSearchResultContainer, vBackBtn, vPlayImageBegin,
				vZoomContainer);
		bridgeHelper = new BridgeHelper(MainActivity.this, bridgeLayer);
		roadsHelper = new RoadsHelper(MainActivity.this, vSearchResultContainer, vshowDetailIbt, graphicsLayer, transparentLayer);
		serviceHelper = new ServiceHelper(MainActivity.this, bridgeLayer);
		// 接口工具
		connectionInteface = CustomApp.app.connInteface;

		/****************************** 十字针和上边的信息小框初始化 ***********************************/

		// shizizhenView=getLayoutInflater().inflate(R.layout.shizizhenview,
		// null);
		//
		// Drawable shiziDrawable=mapUtils.changeViewToDrawable(shizizhenView);

		pointInfoView = getLayoutInflater().inflate(R.layout.point_info_layout, null);

		pointInfoLocationTxt = (TextView) pointInfoView.findViewById(R.id.point_info_location);
		pointInfoStationTxt = (TextView) pointInfoView.findViewById(R.id.point_info_station);

		// 十字针
		Drawable shiziDrawable = getResources().getDrawable(R.drawable.shizizhen_normal);

		Drawable shiziRedDrawable = getResources().getDrawable(R.drawable.shizizhen_red);

		pSymbol = new PictureMarkerSymbol(shiziDrawable);
		pRedSymbol = new PictureMarkerSymbol(shiziRedDrawable);

		// 十字针
		shizizhenGraphic = new Graphic(startPoint, pSymbol);

		/****************************** 公路类型相关 ***********************************/

		// 缩略图
		vSearchThumbImg = (ImageView) findViewById(R.id.search_thumb_img);
		vSearchThumbImg.setVisibility(View.GONE);

		// 每段公路指标信息列表
		vblackView = (LinearLayout) inflater.inflate(R.layout.black_listview, null);
		// 显示桩号附近的指标信息
		vlistView = (ListView) vblackView.findViewById(R.id.ll);
		// 单条公路的详细信息弹窗
		vRoadNormalDetail = (LinearLayout) inflater.inflate(R.layout.query_road_detail_layout, null);

		// vRoadDetailKpi = (LinearLayout) vRoadNormalDetail
		// .findViewById(R.id.query_road_detail_kpi);
		// vRoadDetailKpi.setVisibility(View.GONE);

		// 多条公路的指标及详细信息弹窗
		vBottomLargeImgLayout = (LinearLayout) findViewById(R.id.bottom_large_img_container);

		// 公路小弹窗
		callout = vMap.getCallout();

		callout.setOffsetDp(0, 30);
		callout.setContent(pointInfoView);
		callout.setMaxHeightDp(20);
		callout.setMaxWidth(45);
		/****************************** 桥梁类型相关 ***********************************/

		// 从搜索界面返回
		currentCity = (CityList) getIntent().getSerializableExtra("currentCity");
		if (currentCity != null) {

			// 不可点选省份
			isSelectProvince = false;

			province_code = currentCity.getProvince_code();
			province_name = mapUtils.subStringProvinceName(currentCity.getProvince_name(), province_code);

			Log.i("===============", "===province_code==" + province_code);

			start_flag = 1;

			// 放大地图到省份
			locPrvinceFlag = 3;
			startTheard(1);

			vProvinceChoicesTxt.setText(mapUtils.subStringProvinceName(province_name, province_code));

			shizizhenLayer.removeAll();
			vSearchResultContainer.setVisibility(View.GONE);

			// 点击"桥梁，视屏，灾毁"等按钮返回地图时
			search_type = getIntent().getStringExtra("showType");
			bridge_type = getIntent().getStringExtra("bridgeType");
			if ("特大桥".equals(bridge_type)) {
				bridge_type_value = "SUPER_LARGE_BRIDGE";
			} else if ("大桥".equals(bridge_type)) {
				bridge_type_value = "LARGE_BRIDGE";
			} else if ("中桥".equals(bridge_type)) {
				bridge_type_value = "MIDDLE_BRIDGE";
			} else if ("小桥".equals(bridge_type)) {
				bridge_type_value = "SMALL_BRIDGE";
			} else {
				bridge_type_value = "ALL_BRIDGE";
			}
			if (search_type != null) {

				// 桥梁
				if ("bridge".equals(search_type)) {

					function_type = 1;
					startTheard(6);
				}

				// 服务区
				if ("service".equals(search_type)) {
					startTheard(10);
				}

			}

		}

		// 点击搜索按钮返回地图主页面时
		searchType = getIntent().getIntExtra("type", -1);

		if (searchType == 10086) {

			// 不可点选省份
			isSelectProvince = false;

			province_code = getIntent().getStringExtra("province_code");
			nearLocX = getIntent().getStringExtra("locX");
			nearLocY = getIntent().getStringExtra("locY");
			input_str = getIntent().getStringExtra("research_str");
			bridge_code = getIntent().getStringExtra("bridge_code");
			searchBridgeType = (SearchListBean) getIntent().getSerializableExtra("SearchListBean");

			if (searchBridgeType != null) {

				// 桥
				if (searchBridgeType.getType().equals("2")) {

					bridge_code = searchBridgeType.getBridge_code();
					if (Utils.isNull(bridge_code)) {
						bridge_code = searchBridgeType.getRoad_code();
					}
					province_code = searchBridgeType.getProvince_code();
				}

			}

			start_flag = 1;

			locPrvinceFlag = 5;
			kpiORsearch = 1;

			startTheard(8);

		}

		// 从省份对比路况界面返回
		provinceSituation = (ProvinceSituation) getIntent().getSerializableExtra("province_situation_result");
		if (provinceSituation != null) {

			// 不可点选省份
			isSelectProvince = false;

			// 返回三角按钮
			vBackBtn.setVisibility(View.VISIBLE);
			vBlackView.setVisibility(View.VISIBLE);

			// 返回上一层
			isClearAll = false;

			kpiORsearch = 0;

			start_flag = 1;

			province_code = provinceSituation.getProvince_code();

			road_code = getIntent().getStringExtra("road_code");
			query_indicator_id = getIntent().getStringExtra("indicator_id");
			province_name = getIntent().getStringExtra("province_name");

			startTheard(8);

		}

		roadPCIResult = (RoadPCIResult) getIntent().getSerializableExtra("TrafficDistributionActivity_PCIInfo");

		if (roadPCIResult != null) {

			provinceRoadPCIInfo = roadPCIResult.getResults();

			// 不可点选省份
			isSelectProvince = false;
			// 返回上一层
			isClearAll = false;
			start_flag = 1;

			query_indicator_id = getIntent().getStringExtra("indicator_id");
			// query_indicator_value = "损坏状况";

			province_name = getIntent().getStringExtra("province_name");
			province_code = getIntent().getStringExtra("province_code");

			if (provinceRoadPCIInfo.getRoad_indicator_list().size() == 1) {

				vRoadSkillKpi.setVisibility(View.VISIBLE);
				vPlayImageBegin.setVisibility(View.VISIBLE);

				if (!"123001".equals(province_code)) {

					locPrvinceFlag = 5;

					startTheard(1);
				}

				mSingleIndicatorRoad(provinceRoadPCIInfo.getRoad_indicator_list().get(0), roadPCIResult.getResults());
			} else {

				vRoadSkillKpi.setVisibility(View.GONE);
				vPlayImageBegin.setVisibility(View.GONE);

				vRoadInfoIbt.setVisibility(View.VISIBLE);
				mapUtils.ZoomToProvince(fr);

				// roadsHelper.multiRoadsIndicatorManage(null, roadPCIResult,
				// province_code, province_name, query_indicator_id);

			}

		}

		statisticalType = getIntent().getStringExtra("key");

		if (statisticalType != null) {

			isSelectProvince = false;

			vBackBtn.setVisibility(View.VISIBLE);
			vBlackView.setVisibility(View.VISIBLE);

			// 返回上一层
			isClearAll = false;

			kpiORsearch = 0;

			start_flag = 1;

			province_code = getIntent().getStringExtra("province_code");
			province_name = getIntent().getStringExtra("province_name");

			road_code = getIntent().getStringExtra("road_code");
			query_indicator_id = getIntent().getStringExtra("indicator_id");

			startTheard(8);

		}

		// 从四五类桥点击返回
		bridgeMarksInfo = (BridgeMarksInfo) getIntent().getSerializableExtra("FortyFiveBridgeP_Result");
		if (bridgeMarksInfo != null) {

			// 不可点选省份
			isSelectProvince = false;
			// 返回上一层
			isClearAll = false;
			isFourOrFiveBridge = true;

			province_name = getIntent().getStringExtra("province_name");
			province_code = getIntent().getStringExtra("province_code");

			if (!"123001".equals(province_code)) {

				locPrvinceFlag = 3;

				startTheard(1);
			}

			if (currentCity == null) {
				currentCity = new CityList();
			}

			currentCity.setProvince_code(province_code);
			currentCity.setProvince_name(province_name);

			// 搜索栏设置
			vSearchEdtTxt.setText("4、5类桥统计结果");
			vProvinceChoicesTxt.setText(mapUtils.subStringProvinceName(province_name, province_code));

			// 滑动菜单初始化
			mInitSildingManager();

			// 滑动菜单设置
			sildeManager.setTitleText("共找到“4、5类桥”:" + bridgeMarksInfo.getBridge_positions_list().size() + "座(滑动查看详情)");

			// 显示桥梁数据
			mShowBridgesBubble(bridgeMarksInfo);

		}

		/******************************* 屏幕按钮点击和地图Touch事件处理 *********************************/
		// 首页省份选择
		vProvinceChoicesTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("0".equals(CustomApp.app.privilege)) {
					return;
				}
				Intent intent = new Intent(MainActivity.this, SelectCityActivity.class);
				currentCity = new CityList();

				if ("".equals(province_name) || province_name == null) {
					province_name = "全国";
					province_code = "123001";
				}
				currentCity.setProvince_name(province_name);
				currentCity.setProvince_code(province_code);
				intent.putExtra("currentCity", currentCity);
				intent.putExtra("dCity", dCity);
				startActivityForResult(intent, 9090);
			}
		});

		// 搜索框
		vSearchEdtTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("0".equals(CustomApp.app.privilege)) {
					if (currentCity == null) {
						currentCity = new CityList();
					}
					currentCity.setProvince_name(CustomApp.app.belong_province_name);
					currentCity.setProvince_code(CustomApp.app.belong_province_code);

				} else {
					if (currentCity == null) {
						currentCity = new CityList();
					}
					if (province_name == null || "".equals(province_name)) {
						province_name = "全国";
						province_code = "123001";
					}
					currentCity.setProvince_name(province_name);
					currentCity.setProvince_code(province_code);
				}
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("currentCity", currentCity);
				startActivity(intent);

			}
		});

		// 公路信息按钮
		vRoadInfoIbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("0".equals(CustomApp.app.privilege) && CustomApp.app.belong_province_code.equals(province_code)) {
					CustomApp.app.customToast("无此权限");
					return;
				}
				if (vBottomLargeImgLayout.getVisibility() == View.VISIBLE) {

					vBottomLargeImgLayout.setVisibility(View.GONE);
				}

				if (imagemoveLatsInfo != null) {

					vCurrentStationTxt.setText(imagemoveLatsInfo.getRoad_station());
					vCurrentLocationTxt.setText(imagemoveLatsInfo.getCurrent_position());
					vCurrentFeedUnitTxt.setText(imagemoveLatsInfo.getCurrent_feed_unit());
				}
				vBottomBtnLayout.setVisibility(View.VISIBLE);

				vSearchResultContainer.setVisibility(View.VISIBLE);
				vshowDetailIbt.setVisibility(View.GONE);

			}
		});


		// 沿线信息
		vNearbyLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		// 路况
		vRoadIndiatorLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MapSelectNoticeDialog dialog = new MapSelectNoticeDialog(MainActivity.this);
				dialog.show();
			}
		});

		// 查询
		vSearchLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("0".equals(CustomApp.app.privilege)) {
					if (currentCity == null) {
						currentCity = new CityList();
					}
					currentCity.setProvince_name(CustomApp.app.belong_province_name);
					currentCity.setProvince_code(CustomApp.app.belong_province_code);

				} else {
					if (currentCity == null) {
						currentCity = new CityList();
					}
					if (province_name == null || "".equals(province_name)) {
						province_name = "全国";
						province_code = "123001";
					}
					currentCity.setProvince_name(province_name);
					currentCity.setProvince_code(province_code);
				}
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("currentCity", currentCity);
				startActivity(intent);
			}
		});

		// 统计
		vInfoLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if ("0".equals(CustomApp.app.privilege)) {
					if (currentCity == null) {
						currentCity = new CityList();
					}
					currentCity.setProvince_name(CustomApp.app.belong_province_name);
					currentCity.setProvince_code(CustomApp.app.belong_province_code);
					Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
					intent.putExtra("currentCity", currentCity);
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					if (currentCity == null) {
						currentCity = new CityList();
						currentCity.setProvince_code("123001");
						currentCity.setProvince_name("全国");
						// intent.putExtra("currentCity", currentCity);
					}
					tCity = new CityList();
					tCity.setProvince_name(province_name);
					tCity.setProvince_code(province_code);
					intent.putExtra("currentCity", tCity);

					// intent.putExtra("currentCity", currentCity);
					intent.setClass(MainActivity.this, CityListActivity.class);
					startActivity(intent);
				}
			}
		});

		vGpsLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (morePopupWindow != null && morePopupWindow.isShowing())
					return;
				morePopupWindow = new MorePopupWindow(MainActivity.this, vBottomBtnLayout);
				morePopupWindow.show();

			}
		});

		// 搜索结果详细信息
		vSearchResultContainer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mapUtils.animationContainerUpAndDown(vSearchResultContainer, "DOWN");

				vshowDetailIbt.setVisibility(View.VISIBLE);

				vSearchResultContainer.setVisibility(View.GONE);

			}
		});

		// 搜索结果弹窗隐藏按钮
		vshowDetailIbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mapUtils.animationContainerUpAndDown(vSearchResultContainer, "UP");

				vSearchResultContainer.setVisibility(View.VISIBLE);
				//

				vshowDetailIbt.setVisibility(View.GONE);
			}
		});

		// 长按某一点监听
		vMap.setOnLongPressListener(new OnLongPressListener() {

			@Override
			public boolean onLongPress(float arg0, float arg1) {
				// 手在十字针上时长按无效，否则算是长按路线
				if (shiziGraphics != null && shiziGraphics.length != 0) {

				} else {

					point = vMap.toMapPoint(arg0, arg1);

					longPress_longitude = String.valueOf(point.getX());
					longPress_latitude = String.valueOf(point.getY());

					locPrvinceFlag = 4;

					startTheard(1);
				}

				return false;
			}
		});
		// 地图单击监听
		vMap.setOnSingleTapListener(new OnSingleTapListener() {
			@Override
			public void onSingleTap(float arg0, float arg1) {
				/************************
				 * 手指点在公路上，移动十字针到点击点
				 *****************************/
				int[] singleRoadGraphicUid = transparentLayer.getGraphicIDs(arg0, arg1, 20, 1);

				if (singleRoadGraphicUid.length != 0) {

					Graphic singleRoadGraphic = transparentLayer.getGraphic(singleRoadGraphicUid[0]);

					if (provinceSituation != null && singleRoadGraphic.getAttributeValue("road_index") != null) {

						int road_index = (Integer) singleRoadGraphic.getAttributeValue("road_index");

						Log.i("====================", "=======+road_index===" + road_index);

						ArrayList<RoadPCIInfo> road_lon_lat_list = roadsIndicatorResult.getResults().getRoad_indicator_list().get(road_index)
								.getRoad_indicator_distribute();

						if (road_lon_lat_list != null && road_lon_lat_list.size() != 0) {

							transparentLayer.removeAll();
							graphicsLayer.removeAll();

							road_code = roadsIndicatorResult.getResults().getRoad_indicator_list().get(road_index).getRoad_code();

							isBackToRoads = true;
							clearDynaticData();

							startTheard(4);

						}

					}

					// 普通多条公路点击,flag避免从统计画完路线点击pci十字针时重走改方法画线
					else if ("statisticRode".equals(from) && statisticFlag) {

						longPress_longitude = (String) singleRoadGraphic.getAttributeValue("longitude");
						longPress_latitude = (String) singleRoadGraphic.getAttributeValue("latitude");

						click_province_code = province_code;
						statisticFlag =false;
						startTheard(5);

					}

					else {

						mSetDynamicData(singleRoadGraphic, null, null);

						if (vBottomLargeImgLayout.getVisibility() == View.VISIBLE) {

							startTheard(7);

						}
					}

				}
				if (!isSelectProvince) {
					vBottomStatisticLayout.setVisibility(View.INVISIBLE);
					// 全国范围内的桥梁，服务区，视频等
					if ("123001".equals(province_code)) {
						isPr = false;
						int[] uids = bridgeLayer.getGraphicIDs(arg0, arg1, 5, 1);

						if (uids != null && uids.length != 0) {

							int uid = uids[0];
							Graphic hhGraphic = bridgeLayer.getGraphic(uid);
							province_code = (String) hhGraphic.getAttributeValue("province_code");
							province_name = (String) hhGraphic.getAttributeValue("province_name");

							if (uids != null && uids.length != 0) {

								locPrvinceFlag = 3;
								startTheard(1);
								bridgeLayer.removeAll();

								switch (function_type) {
								case TYPE_BRIDGE:
									startTheard(6);
									break;
								case TYPE_SERVICE_AREA:
									startTheard(10);
									break;
								case TYPE_TUNNEL:

									break;
								case TYPE_FACILITY:

									break;

								case TYPE_CONSTRUCTION:

									break;
								case TYPE_DAMAGE:

									break;
								case TYPE_VIDEO:

									break;
								default:
									break;
								}

							}
						}

					} else if ("110000".equals(province_code) || "120000".equals(province_code) || "310000".equals(province_code)
							|| "500000".equals(province_code)) {

						int[] uids = bridgeLayer.getGraphicIDs(arg0, arg1, 5, 1);

						if (uids != null && uids.length != 0) {
							int uid = uids[0];
							Graphic hhGraphic = bridgeLayer.getGraphic(uid);

							switch (function_type) {
							case TYPE_BRIDGE:
								mBridgeOnClick(hhGraphic, null);

								break;
							case TYPE_SERVICE_AREA:

								Intent servicceIntent = new Intent();
								servicceIntent.setClass(MainActivity.this, ServiceAreaDetailsActivity.class);
								startActivity(servicceIntent);

								break;
							case TYPE_TUNNEL:

								break;
							case TYPE_FACILITY:

								break;

							case TYPE_CONSTRUCTION:

								break;
							case TYPE_DAMAGE:

								break;
							case TYPE_VIDEO:

								break;
							default:
								break;
							}

						}

					} else {
						if (!isPr && from == null && searchBridgeType == null) {

							if (bmNextList != null && bmNextList.size() > 0) {
								provinceLayer.removeAll();
								isPr = true;
								int[] uids = bridgeLayer.getGraphicIDs(arg0, arg1, 15, 1);

								if (uids != null && uids.length != 0) {

									int uid = uids[0];
									Graphic hhGraphic = bridgeLayer.getGraphic(uid);
									city_code = (String) hhGraphic.getAttributeValue("city_code");
									city_name = (String) hhGraphic.getAttributeValue("city_name");
								}
								for (int i = 0; i < bmNextList.size(); i++) {
									if (city_code.equals(bmNextList.get(i).getArea_code())) {
										info = bmNextList.get(i);
										break;
									}
								}
								bridgeLayer.removeAll();
								threadManage.Run(MainActivity.this, new frTask(), R.string.com_loading);
								sildeManager.setTitleText(info.getArea_name() + bridge_type + "：" + info.getBridge_number() + "座" + "（滑动查看详情）");
								mShowBridgesBubble(info);
							}

						} else {
							int[] uids = bridgeLayer.getGraphicIDs(arg0, arg1, 5, 1);

							if (uids != null && uids.length != 0) {
								int uid = uids[0];
								Graphic hhGraphic = bridgeLayer.getGraphic(uid);

								switch (function_type) {
								case TYPE_BRIDGE:
									mBridgeOnClick(hhGraphic, null);

									break;
								case TYPE_SERVICE_AREA:

									Intent servicceIntent = new Intent();
									servicceIntent.setClass(MainActivity.this, ServiceAreaDetailsActivity.class);
									startActivity(servicceIntent);

									break;
								case TYPE_TUNNEL:

									break;
								case TYPE_FACILITY:

									break;

								case TYPE_CONSTRUCTION:

									break;
								case TYPE_DAMAGE:

									break;
								case TYPE_VIDEO:

									break;
								default:
									break;
								}

							}

						}
					}

				} else if (roadIndicatorPopWindow != null && roadIndicatorPopWindow.isShowing()) {

					Log.i("======================", "====woqunimeide======" + roadIndicatorPopWindow.isShowing());

				}
				// 点选省份
				else {

					vSearchResultContainer.setVisibility(View.GONE);
					vRoadInfoIbt.setVisibility(View.GONE);
					vshowDetailIbt.setVisibility(View.GONE);
					vPlayImageBegin.setVisibility(View.GONE);
					vBottomLargeImgLayout.setVisibility(View.GONE);
					vBottomBtnLayout.setVisibility(View.VISIBLE);

					moveLatsInfoList.clear();
					pressRoadInfoResult = null;
					graphicsLayer.removeAll();
					provinceLayer.removeAll();
					if (callout != null && callout.isShowing()) {
						callout.hide();
					}

					point = vMap.toMapPoint(arg0, arg1);
					locPrvinceFlag = 0;

					startTheard(1);

				}

			}
		});

		// 清空所有搜索结果
		vBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isClearAll) {

					clearAll();
				} else {
					MainActivity.this.finish();
				}

			}
		});

		// 缩放监听：手势放大后显示探针，并重画PCI路线
		vMap.setOnZoomListener(new OnZoomListener() {

			@Override
			public void preAction(float arg0, float arg1, double arg2) {

			}

			@Override
			public void postAction(float arg0, float arg1, double arg2) {

				Log.i("================", "===vMap.scale==" + vMap.getScale());

				Log.i("================", "===roadindicatorGraphic==" + String.valueOf(roadIndicatorGraphics == null));

				if (roadIndicatorGraphics != null) {
					Log.i("================", "===roadindicatorGraphic.length==" + roadIndicatorGraphics.length);

					for (int i = 0; i < roadIndicatorGraphics.length; i++) {

						SimpleLineSymbol simpleLineSymbol = (SimpleLineSymbol) roadIndicatorGraphics[i].getSymbol();

						Log.i("================", "===simpleLineSymbol.getWidth()==" + simpleLineSymbol.getWidth());
						simpleLineSymbol.setWidth(10);

						int uid = roadIndicatorGraphics[i].getUid();

						Log.i("================", "===simpleLineSymbo.uid==" + uid);

						graphicsLayer.updateGraphic(uid, simpleLineSymbol);

					}

				}

				if (moveLatsInfoList.size() != 0) {

					shizizhenLayer.updateGraphic(shizizhen_uid, startPoint);// 更新数据显示
				}
			}

		});

		// 添加指标信息
		vRoadSkillKpi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				vSearchVisit = true;
				Intent intent = new Intent(MainActivity.this, AddKpiActivity.class);

				Bundle bundle = new Bundle();

				bundle.putString("query_indicator_id", query_indicator_id);

				intent.putExtras(bundle);

				startActivityForResult(intent, 0);

			}
		});
		// 底部图片
		vPlayImageBegin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				image_type = constant.ROAD;
				bottomScreen.setVisibility(View.VISIBLE);
				vBottomBtnLayout.setVisibility(View.INVISIBLE);
				vSearchResultContainer.setVisibility(View.GONE);
				vshowDetailIbt.setVisibility(View.GONE);
				vRoadInfoIbt.setVisibility(View.VISIBLE);
				vSearchBarLayout.setVisibility(View.INVISIBLE);
				vBottomLargeImgLayout.setVisibility(View.VISIBLE);
				vMap.setScale(750000);
				vSearchVisit = false;
				if (moveLatsInfoList.size() != 0) {

					vMap.centerAt(new Point(Double.valueOf(startPoint.getX()), Double.valueOf(startPoint.getY()) - 0.06), false);
					startTheard(7);
				}

			}
		});

		// 下一个桩号
		vRoadForwardStationIbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (moveLatsInfoList.size() != 0) {

					LonLatsInfo lonLatsInfo = mapUtils.findNextOrForwardStation(road_station, moveLatsInfoList, "next");

					if (lonLatsInfo != null) {

						Log.i("============", "=====next_road_station==" + lonLatsInfo.getRoad_station());

						// 设置小浮窗数据、十字针位置和底部弹窗的动态数据
						mSetDynamicData(null, null, lonLatsInfo);

					}

					if (vBottomLargeImgLayout.getVisibility() == View.VISIBLE) {

						startTheard(7);
					}
				}
			}
		});

		// 上一个桩号
		vRoadNextStationIbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (moveLatsInfoList.size() != 0) {

					LonLatsInfo lonLatsInfo = mapUtils.findNextOrForwardStation(road_station, moveLatsInfoList, "forward");

					if (lonLatsInfo != null) {

						Log.i("============", "=====forward_road_station==" + lonLatsInfo.getRoad_station());

						// 设置小浮窗数据、十字针位置和底部弹窗的动态数据
						mSetDynamicData(null, null, lonLatsInfo);

					}

					if (vBottomLargeImgLayout.getVisibility() == View.VISIBLE) {

						startTheard(7);
					}
				}

			}
		});

		// 点击隐藏公路kpi底部弹窗
		vlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				mapUtils.animationContainerUpAndDown(vSearchResultContainer, "DOWN");
				vSearchResultContainer.setVisibility(View.GONE);

				vshowDetailIbt.setVisibility(View.VISIBLE);

			}
		});
		// 统计桥梁返回
		statisticView();
	}

	private void initGps() {

		lDisplayManager = vMap.getLocationDisplayManager();
		if (lDisplayManager.isStarted()) {
			lDisplayManager.stop();
		} else {
			lDisplayManager.setAutoPanMode(AutoPanMode.OFF);

			View gpsView0 = getLayoutInflater().inflate(R.layout.location_gps, null);

			if (gpsView0 != null) {

				gpsView0.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				gpsView0.layout(0, 0, gpsView0.getMeasuredWidth(), gpsView0.getMeasuredHeight());
			}

			gpsView0.buildDrawingCache();
			gpsView0.setDrawingCacheEnabled(true);

			Bitmap bm = Bitmap.createBitmap(gpsView0.getDrawingCache(true));

			drawable = new BitmapDrawable(null, bm);

			gpsView0.destroyDrawingCache();
			gpsView0.setDrawingCacheEnabled(false);

			// drawable=getResources().getDrawable(R.drawable.location_gps);

			lDisplayManager.setAllowNetworkLocation(true);

			locationSymbol = new PictureMarkerSymbol(drawable);
			lDisplayManager.setShowLocation(false);

			lDisplayManager.start();

			lDisplayManager.setLocationListener(new LocationListener() {

				boolean locationChanged = false;

				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {

				}

				@Override
				public void onProviderEnabled(String provider) {

				}

				@Override
				public void onProviderDisabled(String provider) {

				}

				@Override
				public void onLocationChanged(Location location) {
					if (!locationChanged) {
						loc = location;
						if (loc != null) {
							locationChanged = true;
							markLocation(loc);
						}
					}

				}
			});
		}

	}

	/**
	 * 
	 * 添加定位标记
	 * 
	 */
	private void markLocation(Location location) {

		gpsLayer.removeGraphic(dir_uid);
		double locx = location.getLongitude();
		double locy = location.getLatitude();
		locX = String.valueOf(locx);
		locY = String.valueOf(locy);

		mapPoint = GeometryEngine.project(locx, locy, SpatialReference.create(4326));

		locPrvinceFlag = 5;
		startTheard(1);

		locationSymbol = new PictureMarkerSymbol(drawable);

		dir_uid = gpsLayer.addGraphic(new Graphic(mapPoint, locationSymbol));

		gpsLayer.updateGraphic(dir_uid, mapPoint);

		/**
		 * 定位省份高亮
		 */
		if ("1".equals(CustomApp.app.privilege) && CustomApp.app.clickLogin) {
			point = new Point(locx, locy);
			// point = vMap.toMapPoint(point);
			Log.i("经纬度", locx + "--" + locy);
			locPrvinceFlag = 0;
			CustomApp.app.clickLogin = false;
			startTheard(1);
		}
	}

	/**
	 * 
	 * 定位用户所属省份高亮显示
	 * 
	 */
	private void initLocation() {

		if (lDisplayManager != null && lDisplayManager.isStarted()) {

			loc = lDisplayManager.getLocation();

			if (loc == null) {
				// 定位失败
				Toast.makeText(MainActivity.this, "定位失败！", Toast.LENGTH_SHORT).show();
			} else {

				locPrvinceFlag = 5;
				markLocation(loc);
				lDisplayManager.stop();
			}
		}

		if (CustomApp.app.belong_province_code != null) {
			locPrvinceFlag = 5;

			// startTheard(2);

		}

	}

	/**
	 * 起线程
	 * 
	 * @param type
	 *            类型
	 */
	private void startTheard(int type) {
		if (Utils.detect(this)) {

			switch (type) {
			case 0:// 点搜索高亮显示省份
					// threadManage.Run(this, new SearchDialogTask(),
					// R.string.com_loading);

				break;

			case 1:
				threadManage.Run(this, new SingleTapProvinceTask(), R.string.com_loading);
				break;
			case 2:
				threadManage.Run(this, new UserBelongProvinceTask(), R.string.com_loading);
				break;
			case 3:

				// if (vMap.isLoaded()) {
				//
				// threadManage.Run(this, new ShowSmallImageTask(),
				// R.string.com_loading);
				// }
				break;
			case 4:// 多条公路指标路况
				threadManage.Run(this, new ShowRoadsIndicatorTypeTask(), R.string.com_loading);
				break;
			case 5:
				threadManage.Run(this, new LongPressPoint(), R.string.com_loading);
				break;
			case 6:
				threadManage.Run(this, new showBridgeMarks(), R.string.com_loading);
				break;
			case 7:// 获取大图异步任务
				threadManage.Run(this, new getLargeImage(), R.string.com_loading);
				break;
			case 8:// 从搜索,路况界面返回的异步任务
				threadManage.Run(this, new getSearchResultTask(), R.string.com_loading);
				break;
			case 9:// 获取紫色公路的异步任务
				threadManage.Run(this, new getNormalRoadTask(), R.string.com_loading);
				break;
			case 10:// 获取服务区数据的异步任务
				threadManage.Run(this, new getServiceDataTask(), R.string.com_loading);
				break;

			case 11:
				threadManage.Run(this, new getSingleBridgeDataTask(), R.string.com_loading);
				break;
			case 12:

				threadManage.Run(this, new GetBridgeImageTask(), R.string.com_loading);
				break;
			default:
				break;
			}

		} else {
			CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, R.string.com_network_wrong);
		}
	}

	/****************************** 异步任务 ***********************************/

	/**
	 * 点选政区异步任务
	 * 
	 * @param type
	 *            类型
	 */
	private class SingleTapProvinceTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {

			if (locPrvinceFlag == 0 || locPrvinceFlag == 4) {
				searchResult = null;
				// 获取省份特征
				fr = mapUtils.SearchProvince(null, point);
				// roadFr = mapUtils.SearchRoad("京沪高速", null);
			} else if (locPrvinceFlag == 1 || locPrvinceFlag == 2) {
				searchResult = null;
				// 获取省份特征
				fr = mapUtils.SearchProvince(province_code, null);
			} else {
				fr = mapUtils.SearchProvince(province_code, null);
			}
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {

			// if (fr != null && fr.featureCount() != 0) {
			if (fr != null) {
				if (locPrvinceFlag == 0 || locPrvinceFlag == 1) {

					vSearchResultContainer.setVisibility(View.GONE);
					vRoadInfoIbt.setVisibility(View.GONE);

					roadIndicatorsResult = null;
					pressRoadInfoResult = null;

					shizizhenLayer.removeAll();
					graphicsLayer.removeAll();
					vRoadSkillKpi.setVisibility(View.GONE);
					vSearchResultContainer.setVisibility(View.GONE);
					vRoadInfoIbt.setVisibility(View.GONE);
					Iterator<Object> objectIterator = fr.iterator();

					while (objectIterator.hasNext()) {

						Feature feature = (Feature) objectIterator.next();

						province_code = (String) feature.getAttributeValue("代码");

					}
					if ("0".equals(CustomApp.app.privilege) && !CustomApp.app.belong_province_code.equals(province_code)) {
						CustomApp.app.customToast("无此权限");
						return true;
					}
					// 省份边界高亮显示
					province_name = mapUtils.heightLightProvince(fr);
					if (province_name == null || province_name.equals("")) {
						vProvinceChoicesTxt.setText(R.string.country);
					} else {
						if ("0".equals(CustomApp.app.privilege)) {
							vProvinceChoicesTxt.setText(CustomApp.app.belong_province_name);
						} else {

							vProvinceChoicesTxt.setText(mapUtils.subStringProvinceName(province_name, province_code));
						}
					}
					if (currentCity != null) {
						currentCity.setProvince_code(province_code);
						currentCity.setProvince_name(province_name);
					}
					if (vMap.isLoaded()) {

						mapUtils.ZoomToProvince(fr);
					}
					// mapUtils.heightLightProvince(fr);
				} else if (locPrvinceFlag == 3 || locPrvinceFlag == 5) {

					if (province_name == null || province_name.equals("")) {
						vProvinceChoicesTxt.setText(R.string.country);
					} else {
						if ("0".equals(CustomApp.app.privilege)) {
							vProvinceChoicesTxt.setText(CustomApp.app.belong_province_name);
						} else {
							vProvinceChoicesTxt.setText(mapUtils.subStringProvinceName(province_name, province_code));
						}
					}

					Iterator<Object> objectIterator = fr.iterator();

					while (objectIterator.hasNext()) {

						Feature feature = (Feature) objectIterator.next();

						province_code = (String) feature.getAttributeValue("代码");

					}
					if ("0".equals(CustomApp.app.privilege) && !CustomApp.app.belong_province_code.equals(province_code)) {
						CustomApp.app.customToast("无此权限");
						return true;
					}
					// 省份边界高亮显示
					mapUtils.heightLightProvince(fr);
					mapUtils.ZoomToProvince(fr);
					// if (locPrvinceFlag == 3) {
					//
					// mapUtils.ZoomToProvince(fr);
					// } else {
					//
					// }

				} else if (locPrvinceFlag == 2) {

					vSearchResultContainer.setVisibility(View.GONE);
					vRoadInfoIbt.setVisibility(View.GONE);

					try {

						Iterator<Object> objectIterator = fr.iterator();

						while (objectIterator.hasNext()) {

							Feature feature = (Feature) objectIterator.next();
							Polygon polygon = (Polygon) feature.getGeometry();

							SimpleLineSymbol symbol = new SimpleLineSymbol(Color.argb(128, 50, 180, 255), 2, SimpleLineSymbol.STYLE.SOLID);
							graphicsLayer.setRenderer(new SimpleRenderer(symbol));

							Graphic graphic = new Graphic(polygon, symbol);

							graphicsLayer.addGraphic(graphic);

							dCity = new CityList();
							dCity.setProvince_name((String) feature.getAttributeValue("简称"));
							dCity.setProvince_code((String) feature.getAttributeValue("代码"));

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (locPrvinceFlag == 4) {
					Iterator<Object> objectIterator = fr.iterator();

					while (objectIterator.hasNext()) {

						Feature feature = (Feature) objectIterator.next();

						click_province_code = (String) feature.getAttributeValue("代码");

					}
					if ("0".equals(CustomApp.app.privilege) && !CustomApp.app.belong_province_code.equals(click_province_code)) {
						CustomApp.app.customToast("无此权限");
						return true;
					}
					startTheard(5);

				}
			} else if (CustomApp.app.belong_province_name.equals("全国")) {

			} else {

			}

			return true;
		}

	}

	/**
	 * 用户所属省份初始化
	 * 
	 * @param type
	 *            类型
	 */
	private class UserBelongProvinceTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {

			fr = mapUtils.SearchProvince(CustomApp.app.belong_province_code, null);
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {

			mapUtils.heightLightProvince(fr);
			mapUtils.ZoomToProvince(fr);
			return true;
		}

	}

	/**
	 * 公路指标信息显示异步任务
	 * 
	 * 
	 */

	private class ShowRoadsIndicatorTypeTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			roadIndicatorsResult = connectionInteface.getRoadsPCI(province_code, CustomApp.app.token, road_code.trim(), query_indicator_id);

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {

			return false;
		}

		@Override
		public boolean OnTaskDone() {
			shizizhenLayer.removeAll();
			vBottomLargeImgLayout.setVisibility(View.GONE);
			vBottomBtnLayout.setVisibility(View.VISIBLE);
			if (callout.isShowing()) {
				callout.hide();
			}

			// 移除紫色路线
			graphicsLayer.removeAll();
			transparentLayer.removeAll();

			Log.i("=============", "=====province_code==" + province_code);

			if (fr == null || fr.featureCount() == 0 || province_code == null || "123001".equals(province_code)) {
				provinceLayer.removeAll();

			} else {

				mapUtils.heightLightProvince(fr);
			}

			int resultCode = Utils.isCorrect(roadIndicatorsResult, true);

			if (resultCode == 0) {

				if (roadIndicatorsResult.getResults() != null) {

					vBlackView.setVisibility(View.VISIBLE);

					// 公路指标重画需要的数据集合
					ArrayList<RoadsInfo> road_indicator_list = roadIndicatorsResult.getResults().getRoad_indicator_list();

					if (road_indicator_list != null && road_indicator_list.size() != 0) {

						/**************************** 多条公路处理 ***********************************/
						if (road_indicator_list.size() != 1) {

							vRoadInfoIbt.setVisibility(View.VISIBLE);
							mapUtils.ZoomToProvince(fr);

							roadsHelper.multiRoadsIndicatorManage(searchResult, roadIndicatorsResult, province_code, province_name,
									query_indicator_id);
						}

						/**************************** 单条公路处理 ***********************************/
						else {

							PCIInfo pciInfo = roadIndicatorsResult.getResults();

							mSingleIndicatorRoad(pciInfo.getRoad_indicator_list().get(0), pciInfo);

						}

					}
				} else {
					CustomApp.app.customToast(getString(R.string.no_data));
				}

			} else if (resultCode == 2) {

				CustomApp.app.exit(MainActivity.this);
			}
			return true;
		}
	}

	/**
	 * 长按地图某点高亮显示公路异步任务
	 * 
	 * 
	 */
	private class LongPressPoint implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			pressRoadInfoResult = connectionInteface.getLongPressUrl(CustomApp.app.token, province_code, longPress_longitude, longPress_latitude,
					click_province_code);

			return true;

		}

		@Override
		public boolean OnTaskDismissed() {

			return false;
		}

		@Override
		public boolean OnTaskDone() {
			isSelectProvince = false;

			query_indicator_id = null;
			transparentLayer.removeAll();
			moveLatsInfoList.clear();
			road_station = null;
			startPoint = null;
			currentFeedUnit = null;
			currentPosition = null;
			currentRoadWidth = null;
			bridgeLayer.removeAll();

			vSearchEdtTxt.setText("");
			vBottomLargeImgLayout.setVisibility(View.GONE);
			shizizhenLayer.removeGraphic(shizizhen_uid);
			if (fr == null || fr.featureCount() == 0 || province_code == null || "".equals(province_code) || "123001".equals(province_code)) {
				provinceLayer.removeAll();
			} else {

				mapUtils.heightLightProvince(fr);
			}

			int resultCode = Utils.isCorrect(pressRoadInfoResult, true);

			if (resultCode == 0) {

				searchResult = null;
				roadIndicatorsResult = null;
				vBottomLargeImgLayout.setVisibility(View.GONE);
				vBottomBtnLayout.setVisibility(View.VISIBLE);
				vRoadInfoIbt.setVisibility(View.VISIBLE);
				vBlackView.setVisibility(View.VISIBLE);
				arrayimage_list.clear();
				graphicsLayer.removeAll();

				if (callout.isShowing()) {
					callout.hide();
				}

				if (pressRoadInfoResult.getResults() != null) {

					roadInfo = pressRoadInfoResult.getResults();
					// 长按
					mInitSingleRoad(roadInfo);

					/***********************
					 * "路线"，"路况","图像"显示处理
					 ****************/
					// "路线"按钮
					vRoadInfoIbt.setVisibility(View.VISIBLE);
					// "路况"按钮
					if (roadInfo.getHas_pci_data() == null || roadInfo.getHas_pci_data().equals("1")

					|| roadInfo.getHas_pci_data().equals("")) {

						vRoadSkillKpi.setVisibility(View.GONE);
					} else {
						vRoadSkillKpi.setVisibility(View.VISIBLE);
					}

					// "图像"按钮
					if (roadInfo.getHas_pci_data() == null || roadInfo.getHas_img_data().equals("1")

					|| roadInfo.getHas_img_data().equals("")) {
						vPlayImageBegin.setVisibility(View.GONE);
					} else {
						vPlayImageBegin.setVisibility(View.VISIBLE);
					}

				}
			} else if (resultCode == 2) {

				CustomApp.app.exit(MainActivity.this);
			} else {

				// 隐藏小浮窗
				if (callout.isShowing()) {
					callout.hide();
				}

				vBottomBtnLayout.setVisibility(View.VISIBLE);
				vshowDetailIbt.setVisibility(View.GONE);
				vSearchResultContainer.setVisibility(View.GONE);
				vRoadInfoIbt.setVisibility(View.GONE);
				vPlayImageBegin.setVisibility(View.GONE);
				vRoadSkillKpi.setVisibility(View.GONE);
				vSearchThumbImg.setVisibility(View.GONE);
				graphicsLayer.removeAll();

				pressRoadInfoResult = null;

			}
			return true;
		}
	}

	/**
	 * 统计桥梁返回
	 */
	private void statisticView() {
		statisticFlag = true;
		vBottomStatisticLayout = (LinearLayout) findViewById(R.id.bottom_statistic_layout);
		vBridgeCounttxtView = (TextView) vBottomStatisticLayout.findViewById(R.id.bridge_count_txtv);
		vBridgeMeterView = (TextView) vBottomStatisticLayout.findViewById(R.id.bridge_meter_txtv);
		vBridgeInfoTxtv = (TextView) vBottomStatisticLayout.findViewById(R.id.bridge_info_txtv);
		vBottomStatisticLayout.clearAnimation();
		vBottomStatisticLayout.setVisibility(View.INVISIBLE);
		vBridgeInfoTxtv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				vBottomStatisticLayout.setVisibility(View.GONE);
				sildeManager.setTitleText(province_name + bridge_type + "：" + bridgeMarksResult.getResults().getBridge_totle_num() + "座");
			}
		});
		from = getIntent().getStringExtra("from");
		if ("statisticRode".equals(from)) {
			currentCity = (CityList) getIntent().getSerializableExtra("currentCity");
			if (currentCity != null) {
				province_code = currentCity.getProvince_code();
			}
			road_code = getIntent().getStringExtra("road_code");
			startTheard(9);
		} else if ("statisticBridge".equals(from)) {
			currentCity = (CityList) getIntent().getSerializableExtra("currentCity");
			if (currentCity != null) {
				province_code = currentCity.getProvince_code();
			}
			bridge_code = getIntent().getStringExtra("bridge_code");

			kpiORsearch = 1;
			startTheard(8);

			// if ("特大桥".equals(bridge_type)) {
			// bridge_type_value = "SUPER_LARGE_BRIDGE";
			// } else if ("大桥".equals(bridge_type)) {
			// bridge_type_value = "LARGE_BRIDGE";
			// } else if ("中桥".equals(bridge_type)) {
			// bridge_type_value = "MIDDLE_BRIDGE";
			// } else if ("小桥".equals(bridge_type)) {
			// bridge_type_value = "SMALL_BRIDGE";
			// } else {
			// bridge_type_value = "ALL_BRIDGE";
			// }
			// function_type = 1;
			// startTheard(6);
		}
	}

	private class showBridgeMarks implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {

			// 全国桥梁
			if ("123001".equals(province_code)) {
				searchResult = CustomApp.app.connInteface.SearchInfo(currentCity != null ? currentCity.getProvince_code() : "",
						currentCity == null ? locX : "", currentCity == null ? locY : "", "TYPE_BRIDGE", CustomApp.app.token, "", "1");
			} else if ("110000".equals(province_code) || "120000".equals(province_code) || "310000".equals(province_code)
					|| "500000".equals(province_code)) {
				// 市级桥梁
				fr = mapUtils.SearchProvince(province_code, null);
				bridgeMarksResult = connectionInteface.getBridgeMarksUrl(CustomApp.app.token, province_code, bridge_type_value, "Y", "", "", "N");
			}
			// 省份桥梁
			else {
				fr = mapUtils.SearchProvince(province_code, null);
				bridgeMarksResult = connectionInteface.getBridgeMarksUrl(CustomApp.app.token, province_code, bridge_type_value, "Y", "", "", "N");
			}
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {

			mInitSildingManager();
			if ("123001".equals(province_code)) {

				int resultCodeG = Utils.isCorrect(searchResult, true);

				if (resultCodeG == 0) {
					province_name = "全国";

					bridgeNationalInfos.clear();
					bridgeNationalInfos.addAll(searchResult.getResults().get(0).getBridge_national_list());
					if (bridgeNationalInfos.size() != 0) {
						if ("statisticBridge".equals(from)) {// 从统计进入
							vBottomStatisticLayout.setVisibility(View.VISIBLE);
							vBridgeCounttxtView.setText("桥梁总数：" + searchResult.getResults().get(0).getResult_size() + "座");
							vBridgeMeterView.setText("总延米数：" + searchResult.getResults().get(0).getResult_mil_num() + "km");
						} else {
							vBottomStatisticLayout.setVisibility(View.GONE);
							sildeManager.setTitleText(province_name + bridge_type + "：" + searchResult.getResults().get(0).getResult_size() + "座");
						}
						bridgeHelper.showBridgeMarkers(bridgeNationalInfos, null, -1);

					}

				} else if (resultCodeG == 2) {
					CustomApp.app.exit(MainActivity.this);
				}

			} else if ("110000".equals(province_code) || "120000".equals(province_code) || "310000".equals(province_code)
					|| "500000".equals(province_code)) {

				int resultCodeS = Utils.isCorrect(bridgeMarksResult, true);

				if (resultCodeS == 0) {
					if (fr != null && fr.featureCount() != 0) {
						province_name = mapUtils.heightLightProvince(fr);
						mapUtils.ZoomToProvince(fr);

					}
					if ("statisticBridge".equals(from)) {// 从统计进入
						vBottomStatisticLayout.setVisibility(View.VISIBLE);
						vBridgeCounttxtView.setText("桥梁总数：" + bridgeMarksResult.getResults().getBridge_totle_num() + "座");
						vBridgeMeterView.setText("总延米数：" + "");
					} else {
						vBottomStatisticLayout.setVisibility(View.GONE);
						sildeManager.setTitleText(province_name + bridge_type + "："
								+ bridgeMarksResult.getResults().getBridge_next_level().get(0).getBridge_number() + "座" + "（滑动查看详情）");
					}

					mShowBridgesBubble(bridgeMarksResult.getResults().getBridge_next_level().get(0));

				} else if (resultCodeS == 2) {

					CustomApp.app.exit(MainActivity.this);
				} else {

					sildeManager.setOpenAnimator(false);
					vBottomBtnLayout.setVisibility(View.INVISIBLE);

					if (bridgeMarksResult == null) {
						sildeManager.setTitleText(getString(R.string.com_network_wrong));
					} else {
						sildeManager.setTitleText(bridgeMarksResult.getError_msg());
					}
				}

			} else {// 省桥梁显示
				int resultCodeG = Utils.isCorrect(bridgeMarksResult, true);
				ArrayList<BridgeNationalInfo> pInfo = new ArrayList<BridgeNationalInfo>();
				if (resultCodeG == 0) {
					bmNextList = bridgeMarksResult.getResults().getBridge_next_level();
					for (int i = 0; i < bridgeMarksResult.getResults().getBridge_next_level().size(); i++) {
						info = bridgeMarksResult.getResults().getBridge_next_level().get(i);
						BridgeNationalInfo bpInfo = new BridgeNationalInfo();
						bpInfo.setBridge_num(info.getBridge_number());
						bpInfo.setProvince_code(info.getArea_code());
						bpInfo.setProvince_name(info.getArea_name());
						bpInfo.setLongitude(info.getLongitude());
						bpInfo.setLatitude(info.getLatitude());
						pInfo.add(bpInfo);
					}
					// bridgeNationalInfos.clear();
					// bridgeNationalInfos.addAll(pInfo);
					if (fr != null && fr.featureCount() != 0) {
						province_name = mapUtils.heightLightProvince(fr);
						mapUtils.ZoomToProvince(fr);

					}
					if (pInfo.size() != 0) {
						if ("statisticBridge".equals(from)) {// 从统计进入
							vBottomStatisticLayout.setVisibility(View.VISIBLE);
							vBridgeCounttxtView.setText("桥梁总数：" + bridgeMarksResult.getResults().getBridge_totle_num() + "座");
							vBridgeMeterView.setText("总延米数：" + "");
						} else {
							vBottomStatisticLayout.setVisibility(View.GONE);
							sildeManager.setTitleText(province_name + bridge_type + "：" + bridgeMarksResult.getResults().getBridge_totle_num() + "座");

						}
						bridgeHelper.showBridgeMarkers(pInfo, null, -1);

					}
					mShowBridgesBubblePr(bmNextList);
				} else if (resultCodeG == 2) {
					CustomApp.app.exit(MainActivity.this);
				}

			}
			return false;
		}
	}

	/**
	 * 获取大图异步任务
	 * 
	 */
	private class getLargeImage implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			if (going_direction == null) {
				going_direction = "UP";
			}
			playImageResult = connectionInteface.Playimage(province_code, CustomApp.app.token, road_code, going_direction, "FOR", road_station, "1",
					"1", "1", "");
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			// 返回
			bottomBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// 大图窗口消失，信息弹窗消失
					vRoadInfoIbt.setVisibility(View.VISIBLE);
					vBottomLargeImgLayout.setVisibility(View.GONE);
					vSearchResultContainer.setVisibility(View.GONE);
					vshowDetailIbt.setVisibility(View.VISIBLE);
					vSearchBarLayout.setVisibility(View.VISIBLE);

					if (imagemoveLatsInfo != null) {

						vCurrentStationTxt.setText(imagemoveLatsInfo.getRoad_station());
						vCurrentLocationTxt.setText(imagemoveLatsInfo.getCurrent_position());
						vCurrentFeedUnitTxt.setText(imagemoveLatsInfo.getCurrent_feed_unit());
					}
					vBottomBtnLayout.setVisibility(View.VISIBLE);
				}
			});
			// 全屏
			bottomScreen.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, playImageActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("province_code", province_code);
					bundle.putString("road_code", road_code);
					bundle.putString("road_station", road_station);
					bundle.putString("going_direction", "up");
					// bundle.putSerializable("list", millList);
					bundle.putBoolean("list", true);
					// CustomApp.app.millList = millList;
					intent.putExtras(bundle);
					startActivityForResult(intent, 256);

					// startActivity(intent);

				}
			});
			// 大图
			vBottomLargeImgLayout.setVisibility(View.VISIBLE);

			int resultCode = Utils.isCorrect(playImageResult, true);
			// if (!pre_province_code.equals(province_code)
			// || !pre_road_code.equals(road_code)) {
			//
			// int resultCode2 = Utils.isCorrect(millList, false);
			//
			// if (resultCode2 == 0) {
			// if (mileageListstation != null) {
			// mileageListstation.clear();
			// }
			// mileageListstation = millList.getResults();
			// Log.v("mylog里程条大小", millList.getResults().size() + "");
			// pre_province_code = province_code;
			// pre_road_code = road_code;
			//
			// } else {
			// pre_province_code = "";
			// pre_road_code = "";
			//
			// }
			//
			// }

			if (resultCode == 0) {

				arrayimage_list.clear();
				arrayimage_list.addAll(playImageResult.getResults().get(0).getLarge_image_list());

				bottomBitmap = mapUtils.handlerBitmap(arrayimage_list.get(0).getRoad_large_img().getRoad_large_img_data());

				vRoadImgYearTxt = (TextView) vBottomLargeImgLayout.findViewById(R.id.road_img_year_txt);
				vRoadImgDirectionTxt = (TextView) vBottomLargeImgLayout.findViewById(R.id.road_img_direction_txt);

				if (arrayimage_list.get(0).getRoad_large_img().getRoad_img_year() != null
						&& arrayimage_list.get(0).getRoad_large_img().getRoad_img_direction() != null) {

					vRoadImgYearTxt.setText(arrayimage_list.get(0).getRoad_large_img().getRoad_img_year());
					vRoadImgDirectionTxt.setText(arrayimage_list.get(0).getRoad_large_img().getRoad_img_direction());

				} else {

					vRoadImgYearTxt.setText("");
					vRoadImgDirectionTxt.setText("");

				}

				if (arrayimage_list.size() == 1) {

					myImageVIew.setImageBitmap(bottomBitmap);
					myImageVIew.setZoomable(true);

				} else {
					myImageVIew.setImageBitmap(bottomBitmap);
					myImageVIew.setZoomable(true);
				}

			} else if (resultCode == 2) {
				CustomApp.app.exit(MainActivity.this);
			} else if (resultCode == 1) {

				myImageVIew.setImageBitmap(null);
			}

			return false;
		}

	}

	class GetBridgeImageTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			bridgeImage = CustomApp.app.connInteface.getBridgeImage(province_code, bridge_code, "");
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			bottomBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 大图窗口消失，信息弹窗消失
					vBottomLargeImgLayout.setVisibility(View.GONE);
					vSearchResultContainer.setVisibility(View.GONE);
					vshowDetailIbt.setVisibility(View.VISIBLE);
					vSearchBarLayout.setVisibility(View.VISIBLE);

				}
			});

			int correct = Utils.isCorrect(bridgeImage, true);
			if (correct == 0) {

				try {
					vBottomLargeImgLayout.setVisibility(View.VISIBLE);

					if (bridgeImage.getResults().get(0) != null && bridgeImage.getResults().get(0).getImage_list() != null) {
						bottomBitmap = mapUtils.handlerBitmap(bridgeImage.getResults().get(0).getImage_list().get(0).getImage_data());
						bridge_image_index = 0;
						myImageVIew.setImageBitmap(bottomBitmap);
						myImageVIew.setZoomable(true);
					} else {
						CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "无图像数据");
					}

				} catch (Exception e) {
				}

			} else if (correct == 2) {
				CustomApp.app.exit(MainActivity.this);
			}
			return false;
		}

	}

	/**
	 * 搜索返回结果获取省份边界的异步任务
	 * 
	 */
	private class getSearchResultTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {

			if (searchType == 10086) {

				if (bridge_code != null && !"".equals(bridge_code)) {
					searchResult = CustomApp.app.connInteface.SearchInfo(province_code, "", "", "TYPE_BRIDGE", CustomApp.app.token, bridge_code, "1");
					fr = mapUtils.SearchProvince(province_code, null);
				} else {

					searchResult = CustomApp.app.connInteface.SearchInfo(province_code, nearLocX == null ? locX : "", nearLocY == null ? locY : "",
							"", CustomApp.app.token, input_str, "1");

				}

			}

			else if (from != null && "statisticBridge".equals(from)) {

				searchResult = CustomApp.app.connInteface.SearchInfo(province_code, "", "", "TYPE_BRIDGE", CustomApp.app.token, bridge_code, "1");

			}

			else {

				roadIndicatorsResult = CustomApp.app.connInteface.getRoadsPCI(province_code, CustomApp.app.token, road_code, query_indicator_id);

				if (statisticalType != null) {
					provinceSituationResult = CustomApp.app.connInteface.provinceSituation(query_indicator_id, province_code, road_code);
				}

				fr = mapUtils.SearchProvince(province_code, null);
			}

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {

			return false;

		}

		@Override
		public boolean OnTaskDone() {

			// 路况返回处理
			if (kpiORsearch == 0) {
				if (fr != null) {

					if (fr.featureCount() != 0) {

						province_name = mapUtils.heightLightProvince(fr);
						mapUtils.ZoomToProvince(fr);

						if (!"".equals(road_code)) {
							vSearchEdtTxt.setText(road_code);
						} else {
							vSearchEdtTxt.setText(province_name + "路况");
						}
						vProvinceChoicesTxt.setText(mapUtils.subStringProvinceName(province_name, province_code));

					}

					/**************************** 地图底部弹窗信息 *************************/

					if (statisticalType != null) {

						int resultCode = Utils.isCorrect(provinceSituationResult, true);

						if (resultCode == 0) {
							provinceSituation = provinceSituationResult.getResults();
						} else if (resultCode == 2) {

							CustomApp.app.exit(MainActivity.this);

						}

					}

					multRoadsBottomshow();

				}

				int resultCode = Utils.isCorrect(roadIndicatorsResult, true);

				if (resultCode == 0) {

					if (provinceSituation != null && statisticalType == null) {

						roadsIndicatorResult = roadIndicatorsResult;
						pre_road_code = road_code;
					}

					transparentLayer.removeAll();
					graphicsLayer.removeAll();

					roadsHelper.multiRoadsIndicatorManage(null, roadIndicatorsResult, province_code, province_name, query_indicator_id);

				} else if (resultCode == 2) {
					CustomApp.app.exit(MainActivity.this);

				}

			}

			// 搜索关键字处理
			if (kpiORsearch == 1) {

				int resultCode = Utils.isCorrect(searchResult, true);

				if (resultCode == 0) {

					SearchInfo searchInfo = searchResult.getResults().get(0);

					province_name = searchInfo.getProvince_name();
					province_code = searchInfo.getProvince_code();

					// 搜索栏相关
					vProvinceChoicesTxt.setText(mapUtils.subStringProvinceName(province_name, province_code));

					vSearchEdtTxt.setText(input_str);
					vBlackView.setVisibility(View.VISIBLE);
					vBackBtn.setVisibility(View.VISIBLE);

					String searchResultType = searchInfo.getResult_type();

					if (searchResultType.equals("空")) {

						locPrvinceFlag = 1;
						startTheard(1);

						vSearchResultContainer.setVisibility(View.GONE);
						vRoadInfoIbt.setVisibility(View.GONE);
						vRoadSkillKpi.setVisibility(View.GONE);
						vPlayImageBegin.setVisibility(View.GONE);
					}

					// 公路
					if (searchInfo != null && "TYPE_ROAD".equals(searchResultType)) {
						// 十字针所在经纬度集合
						moveLatsInfoList.clear();
						vRoadInfoIbt.setVisibility(View.VISIBLE);

						if (shizizhen_uid != 0) {
							shizizhenLayer.removeGraphic(shizizhen_uid);
						}

						// 公路结果信息集合
						ArrayList<RoadInfo> road_lists = searchInfo.getRoad_list();

						if (road_lists != null && road_lists.size() != 0) {

							/************************ 单条公路处理 ***********************/
							if (road_lists.size() == 1) {

								// 缩放到公路
								locPrvinceFlag = 5;
								startTheard(1);

								roadInfo = road_lists.get(0);
								// 搜索
								mInitSingleRoad(roadInfo);

								// "图像"按钮
								if (searchInfo.getHas_pci_data() == null || searchInfo.getHas_img_data().equals("1")
										|| searchInfo.getHas_img_data().equals("")) {
									vPlayImageBegin.setVisibility(View.GONE);
								} else {
									vPlayImageBegin.setVisibility(View.VISIBLE);
								}
							}

							/************************ 多条公路处理 ***********************/
							else {

								// 缩放到省份
								locPrvinceFlag = 3;
								startTheard(1);

								// 展示底部弹窗信息
								searchHelper.showManyRoadsBottomInfo(searchInfo);
								// 画线
								for (int i = 0; i < road_lists.size(); i++) {

									ArrayList<RoadLonLatProvince> road_lon_lat_province = road_lists.get(i).getRoad_lon_lat_province();

									if (road_lon_lat_province.size() != 0) {

										for (int j = 0; j < road_lon_lat_province.size(); j++) {
											mapUtils.drawNormalRoads(road_lon_lat_province.get(j).getRoad_lon_lat_list(), 1, 0);
										}

									}

								}
								// 多条公路用逗号拼接成字符串
								StringBuffer road_codes = new StringBuffer();
								for (int i = 0; i < road_lists.size(); i++) {
									road_codes.append(road_lists.get(i).getRoad_code());

									if (i < road_lists.size() - 1) {
										road_codes.append(",");
									}
								}
								road_code = road_codes.toString().trim();

							}
							// "路线"按钮
							vRoadInfoIbt.setVisibility(View.VISIBLE);
							// "路况"按钮
							if (searchInfo.getHas_pci_data() == null || searchInfo.getHas_pci_data().equals("1")

							|| searchInfo.getHas_pci_data().equals("")) {

								vRoadSkillKpi.setVisibility(View.GONE);
							} else {
								vRoadSkillKpi.setVisibility(View.VISIBLE);
							}

						}
						// 更新数据库
						SearchContent content;
						for (int i = 0; i < road_lists.size(); i++) {
							content = new SearchContent();
							content.setRoute_code(road_lists.get(i).getRoad_code());
							content.setRoute_name(road_lists.get(i).getRoad_name());
							content.setProvince_code(province_code);
							try {
								content.setRoute_length(road_lists.get(i).getRoad_mil_num());
							} catch (Exception e) {
								content.setRoute_length("0");
							}
							content.setCity_name(province_name);
							String codeString = road_lists.get(i).getRoad_code().charAt(0) + "";
							if ("G".equals(codeString)) {
								content.setType("1");
							} else {
								content.setType("2");
							}
							content.setTime(System.currentTimeMillis());
							CustomApp.app.dbManager.updataSearch(content);
						}
					}

					// 桥梁
					if ("TYPE_BRIDGE".equals(searchResultType)) {

						graphicsLayer.removeAll();
						function_type = 1;

						if ((from != null && "statisticBridge".equals(from)) || searchBridgeType != null) {

						} else {

							mInitSildingManager();
						}

						bridgeNationalInfos.clear();
						bridgePositionInfos.clear();

						bridgeNationalInfos.addAll(searchInfo.getBridge_national_list());
						bridgePositionInfos.addAll(searchInfo.getBridge_positions_list());
						// 更新数据库
						ArrayList<BridgePositionInfo> infos = searchInfo.getBridge_positions_list();
						SearchContent content;
						for (int i = 0; i < infos.size(); i++) {
							content = new SearchContent();
							content.setRoute_code(infos.get(i).getRoad_code());
							content.setRoute_name(infos.get(i).getRoad_name());
							content.setBridge_name(infos.get(i).getBridge_name());
							content.setBridge_code(infos.get(i).getBridge_code());
							content.setProvince_code(searchInfo.getProvince_code());
							try {
								content.setBridge_length(infos.get(i).getBridge_length());
							} catch (Exception e) {
								content.setBridge_length("0");
							}
							content.setCity_name(province_name);
							content.setType("0");
							content.setTime(System.currentTimeMillis());
							CustomApp.app.dbManager.updataSearch(content);
						}
						// 搜索全国桥梁
						if (bridgeNationalInfos.size() != 0) {

							bridgeHelper.showBridgeMarkers(bridgeNationalInfos, null, -1);
						}

						// 搜索省份桥梁
						if (bridgePositionInfos.size() != 0) {

							bridgeHelper.showBridgeMarkers(null, bridgePositionInfos, -1);
							if (fr != null) {
								if (fr.featureCount() != 0) {
									mapUtils.heightLightProvince(fr);
									mapUtils.ZoomToProvince(fr);
								}
							}

						}

					}

					// 隧道
					if ("TYPE_TUUNEL".equals(searchResultType)) {
						function_type = 3;
					}

					// 设施
					if ("TYPE_FACILITY".equals(searchResultType)) {
						function_type = 4;
					}

					// 施工点
					if ("TYPE_CONSTRUCTION".equals(searchResultType)) {
						function_type = 5;
					}

					// 服务区
					if ("TYPE_SERVICE_AREA".equals(searchResultType)) {
						function_type = 2;
					}

					// 灾毁
					if ("TYPE_DAMAGE".equals(searchResultType)) {
						function_type = 6;
					}

					// 视频
					if ("TYPE_VIDEO".equals(searchResultType)) {
						function_type = 7;
					}
				} else if (resultCode == 2) {
					CustomApp.app.exit(MainActivity.this);
				}
			}

			return false;
		}
	}

	/**
	 * 2.5. 取消公路指标分布
	 * 
	 */
	private class getNormalRoadTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {

			searchInfoResult = connectionInteface.getNormalRoad(CustomApp.app.token, province_code, road_code);

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {

			roadIndicatorsResult = null;
			shizizhenLayer.removeAll();
			transparentLayer.removeAll();
			graphicsLayer.removeAll();
			moveLatsInfoList.clear();

			int resultCode = Utils.isCorrect(searchInfoResult, true);

			if (resultCode == 0) {

				// 公路结果信息集合
				ArrayList<RoadInfo> road_lists = searchInfoResult.getResults().getRoad_list();

				if (road_lists != null && road_lists.size() != 0) {

					// 展示底部弹窗
					vSearchResultContainer.removeAllViews();

					/********************** 多条公路处理 ************************/
					if (road_lists.size() != 1) {

						SearchInfo searchInfo = searchInfoResult.getResults();
						// 底部弹窗处理
						searchHelper.showManyRoadsBottomInfo(searchInfo);

						for (int i = 0; i < road_lists.size(); i++) {

							ArrayList<RoadLonLatProvince> road_lon_lat_province = road_lists.get(i).getRoad_lon_lat_province();

							if (road_lon_lat_province.size() != 0) {

								for (int j = 0; j < road_lon_lat_province.size(); j++) {

									ArrayList<LonLatsInfo> road_lon_lat_list = road_lon_lat_province.get(j).getRoad_lon_lat_list();

									mapUtils.drawNormalRoads(road_lon_lat_list, 1, 0);
									mapUtils.drawNormalRoadsTest(road_lon_lat_list, 1);
								}

							}

						}

					}

					/********************** 单条公路处理 ************************/
					else {

						// 高亮显示省份轮廓
						if (fr == null || fr.featureCount() == 0 || province_code == null || "123001".equals(province_code)) {

							provinceLayer.removeAll();
						} else {

							mapUtils.heightLightProvince(fr);
						}
						roadInfo = road_lists.get(0);
						// 2.5取消公路
						mInitSingleRoad(roadInfo);

						// "图像"按钮
						if (searchInfoResult.getResults().getHas_pci_data() == null || searchInfoResult.getResults().getHas_img_data().equals("1")

						|| searchInfoResult.getResults().getHas_img_data().equals("")) {
							vPlayImageBegin.setVisibility(View.GONE);
						} else {
							vPlayImageBegin.setVisibility(View.VISIBLE);
						}

					}
					// "路线"按钮
					vRoadInfoIbt.setVisibility(View.VISIBLE);
					// "路况"按钮
					if (searchInfoResult.getResults().getHas_pci_data() == null || searchInfoResult.getResults().getHas_pci_data().equals("1")

					|| searchInfoResult.getResults().getHas_pci_data().equals("")) {

						vRoadSkillKpi.setVisibility(View.GONE);
					} else {
						vRoadSkillKpi.setVisibility(View.VISIBLE);
					}
				}

			} else if (resultCode == 2) {
				CustomApp.app.exit(MainActivity.this);
			}

			return false;
		}

	}

	/**
	 * 获取服务区的异步任务
	 */
	private class getServiceDataTask implements ThreadWithProgressDialogTask {

		private ServiceMarkerResult serviceMarkerResult;

		@Override
		public boolean TaskMain() {

			// 全国服务区
			if ("123001".equals(province_code)) {
				searchResult = CustomApp.app.connInteface.SearchInfo(currentCity != null ? currentCity.getProvince_code() : "",
						currentCity == null ? locX : "", currentCity == null ? locY : "", "TYPE_SERVICE_ARE", CustomApp.app.token, "", "1");
			}

			// 省份服务区
			else {

				serviceMarkerResult = connectionInteface.servicePositionResult("", province_code);
			}

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {

			function_type = 2;
			mInitSildingManager();

			// 全国服务区
			if ("123001".equals(province_code)) {

				int resultCode = Utils.isCorrect(searchResult, true);

				if (resultCode == 0) {

					serviceNationalInfoList.clear();
					serviceNationalInfoList.addAll(searchResult.getResults().get(0).getService_area_national_list());

					serviceHelper.showServiceMarkers(serviceNationalInfoList, null);

				} else if (resultCode == 2) {

					CustomApp.app.exit(MainActivity.this);
				}

			}

			// 省份服务区
			else {

				int resultCode = Utils.isCorrect(serviceMarkerResult, true);

				if (resultCode == 0) {

					servicePositionInfoList.clear();
					serviceMarkerInfo = serviceMarkerResult.getResults();
					servicePositionInfoList.addAll(serviceMarkerInfo.getService_area_position_list());

					serviceHelper.showServiceMarkers(null, servicePositionInfoList);

				} else if (resultCode == 2) {

					CustomApp.app.exit(MainActivity.this);
				}
			}

			return false;
		}
	}

	/**
	 * 获取某座桥梁的数据
	 * 
	 * @author user
	 * 
	 */
	private class getSingleBridgeDataTask implements ThreadWithProgressDialogTask {

		SingleBridgeDataResult singleBridgeDataResult;

		@Override
		public boolean TaskMain() {

			singleBridgeDataResult = CustomApp.app.connInteface.getSingleBridgeData(province_code, bridge_code);

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {

			int resultCode = Utils.isCorrect(singleBridgeDataResult, true);

			if (resultCode == 0) {

				bridgePositionInfo = singleBridgeDataResult.getResult();

				if (bridgePositionInfo != null) {

					mBridgeOnClick(null, bridgePositionInfo);

				}

			}

			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		// 指标信息
		if (requestCode == 0 && resultCode == 1) {

			Bundle extras = data.getExtras();
			String object = (String) extras.get("qureyIndicatorId");
			query_indicator_id = object.toLowerCase();

			if (query_indicator_id != null && !"".equals(query_indicator_id)) {

				if ("0".equals(query_indicator_id)) {

					startTheard(9);

				} else {
					startTheard(4);
				}

			} else {

			}
			// 省份选择
		} else if (requestCode == 9090 && resultCode == 9090) {

			fr = null;
			currentCity = (CityList) data.getSerializableExtra("currentCity");

			provinceLayer.removeAll();
			shizizhenLayer.removeAll();
			graphicsLayer.removeAll();
			if (callout != null) {
				callout.hide();
			}

			moveLatsInfoList.clear();

			vPlayImageBegin.setVisibility(View.GONE);
			vRoadSkillKpi.setVisibility(View.GONE);
			vRoadInfoIbt.setVisibility(View.GONE);
			vSearchEdtTxt.setText("");
			vBottomLargeImgLayout.setVisibility(View.GONE);
			vSearchResultContainer.setVisibility(View.GONE);

			if (currentCity != null) {

				province_code = currentCity.getProvince_code();
				province_name = currentCity.getProvince_name();

				vBottomBtnLayout.setVisibility(View.VISIBLE);
				vBottomDialogLayout.setVisibility(View.GONE);

				vProvinceChoicesTxt.setText(mapUtils.subStringProvinceName(province_name, province_code));

				if ("123001".equals(province_code)) {

					vMap.setExtent(envelope);

				} else {

					locPrvinceFlag = 1;

					startTheard(1);
				}

			}
		} else if (requestCode == 256 && resultCode == 257) {

			road_station = data.getStringExtra("station");
			myImageVIew.setImageBitmap(CustomApp.app.bitmap);

			if (roadIndicatorsResult != null || provinceRoadPCIInfo != null) {

				pointInfoStationTxt.setText(PlayImage.creatNewStation(road_station)

				+ query_indicator_id.toUpperCase() + " " + currentIndicatorValue);

			} else {

				pointInfoStationTxt.setText(PlayImage.creatNewStation(road_station));
			}
			vCurrentStationTxt.setText(PlayImage.creatNewStation(road_station));
			pointInfoStationTxt.setText(PlayImage.creatNewStation(road_station));

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			CustomApp.app.firstzoom = true;
			vBottomStatisticLayout.setVisibility(View.INVISIBLE);
			if (morePopupWindow != null && morePopupWindow.isShowing()) {
				if (!morePopupWindow.isDismiss()) {
					morePopupWindow.dismiss();
				} else {
					morePopupWindow.setListView();
				}
				return true;
			}
			if ((searchResult != null || roadIndicatorsResult != null || pressRoadInfoResult != null || (fr != null && fr.featureCount() != 0)
					|| searchNoticesResult != null || bridgeMarksResult != null || bridgeNationalInfos.size() != 0)
					&& isClearAll) {

				clearAll();

			} else if ((provinceRoadPCIInfo != null || bridgeMarksInfo != null || (provinceSituation != null)) && !isClearAll) {

				if (isBackToRoads) {

					transparentLayer.removeAll();
					graphicsLayer.removeAll();

					// 返回上一层
					isClearAll = false;
					isBackToRoads = false;
					if (callout != null) {
						callout.hide();
					}

					shizizhenLayer.removeAll();

					roadsHelper.multiRoadsIndicatorManage(null, roadsIndicatorResult, province_code, province_name, query_indicator_id);

					multRoadsBottomshow();

					if (fr != null && fr.featureCount() != 0) {

						mapUtils.ZoomToProvince(fr);
					}

				} else {

					this.finish();
				}

			} else if (roadIndicatorPopWindow != null && roadIndicatorPopWindow.isShowing()) {
				roadIndicatorPopWindow.dismiss();
			}

			else {

				// 创建退出对话框
				AlertDialog.Builder isExit = new AlertDialog.Builder(this);

				// 设置对话框标题
				isExit.setTitle("系统提示");

				// 设置对话框消息
				isExit.setMessage("确定要退出吗?");
				// 添加选择按钮并注册监听

				isExit.setPositiveButton("确定", listener).setNegativeButton("取消", listener);

				// 显示对话框
				isExit.show();
			}

		}

		return false;

	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序

				if (LoginActivity.instance != null) {
					LoginActivity.instance.finish();
				}
				CustomApp.app.exit();
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		if (lDisplayManager != null) {
			lDisplayManager.start();
		}
	}

	@Override
	protected void onPause() {

		super.onPause();
		if (lDisplayManager != null) {
			lDisplayManager.pause();
		}
		vMap.pause();

	}

	@Override
	protected void onResume() {

		super.onResume();
		if ("0".equals(CustomApp.app.privilege)) {
			province_code = CustomApp.app.belong_province_code;
			province_name = CustomApp.app.belong_province_name;
		}
		if (lDisplayManager != null) {
			if (loc != null) {
				lDisplayManager.stop();
			}

		}
		vMap.unpause();
	}

	@Override
	protected void onStop() {

		if (lDisplayManager != null) {
			lDisplayManager.stop();

		}
		vMap.destroyDrawingCache();
		super.onStop();
	}

	private void setText(final TextView textView, final ImageView imageView) {
		if (textView == null || imageView == null)
			return;
		imageView.setVisibility(View.GONE);
		ViewTreeObserver vto = textView.getViewTreeObserver();

		vto.addOnGlobalLayoutListener(new

		OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int width = textView.getMeasuredWidth();
				if (width > vCurrentLocationTxt.getWidth()) {
					imageView.setVisibility(View.VISIBLE);
				} else {
				}
			}

		});
	}

	/**
	 * 动态设置十字针的方法
	 * 
	 * @param lonLatsInfo
	 */
	private void setShiZiZhen(LonLatsInfo lonLatsInfo) {

		mSetDynamicData(null, null, lonLatsInfo);

	}

	// 获取图片所在线路的索引

	private int getPositon(String road_station) {

		if (Utils.isNull(road_station)) {

			return -1;
		} else {

			for (int i = 0; i < moveLatsInfoList.size(); i++) {
				if (moveLatsInfoList.get(i).getRoad_station().equals(road_station)) {
					return i;
				}

			}

		}
		return -2;
	}

	/**
	 * 获取里程条相近桩号
	 * 
	 * @param station
	 * @return
	 */
	// private int getNearly(String station) {
	// int low, high, mid;
	// mid = -1;
	// low = 0;
	// high = mileageListstation.size() - 1;
	// while (low <= high) {
	// mid = (low + high) / 2;
	// if (Float.valueOf(station) < Float.valueOf(mileageListstation.get(
	// mid).getRoad_station()))
	// high = mid - 1;
	// else if (Float.valueOf(station) > Float.valueOf(mileageListstation
	// .get(mid).getRoad_station()))
	// low = mid + 1;
	// else
	// // 如果等于则直接还回下标值
	// return mid;
	// }
	// return mid;
	// }

	private int getNearly2(String station) {
		int low, high, mid;
		mid = -1;
		low = 0;
		high = moveLatsInfoList.size() - 1;
		while (low <= high) {
			mid = (low + high) / 2;
			if (Float.valueOf(station) < Float.valueOf(moveLatsInfoList.get(mid).getRoad_station()))
				high = mid - 1;
			else if (Float.valueOf(station) > Float.valueOf(moveLatsInfoList.get(mid).getRoad_station()))
				low = mid + 1;
			else
				// 如果等于则直接还回下标值
				return mid;
		}
		return mid;
	}

	/**
	 * 公路指标信息底部弹窗控件的findviewById
	 */
	private void roadIndicatorFindId() {

		vRoadNameTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_name_txt);
		vCurrentStationTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_station_txt);
		vCurrentLocationTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_position_txt);
		vCurrentRoadWidthTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_roadwidth_txt);
		vCurrentRoadFaceTypeTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_roadtype_txt);

		vCurrentDailyTrafficNumTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_trafficnum_txt);
		vCurrentBuildYearsTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_buildyear_txt);
		vCurrentConservationYearTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_conservationyear_txt);
		vCurrentRoadSurConditionTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_surfaceconditon_txt);
		VCurrentConservationAdviseTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_conservationadvise_txt);
		vCurrentRoadDataYearTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_current_datayear_txt);

	}

	/**
	 * 紫色公路画线以及十字针初始化
	 * 
	 * @param roadInfo
	 */
	private void mInitSingleRoad(RoadInfo roadInfo) {
		if ("0".equals(CustomApp.app.privilege) && !CustomApp.app.belong_province_code.equals(province_code)) {
			CustomApp.app.customToast("无此权限");
			return;
		}
		road_code = roadInfo.getRoad_code().trim();
		road_name = roadInfo.getRoad_name();
		startStation = roadInfo.getRoad_start_station();
		endStation = roadInfo.getRoad_end_station();

		// 搜索栏提示
		vSearchEdtTxt.setText(road_code + " " + road_name);
		vProvinceChoicesTxt.setText(mapUtils.subStringProvinceName(province_name, province_code));

		// 上一桩号，下一桩号
		vRoadNextStationIbt.setVisibility(View.VISIBLE);
		vRoadForwardStationIbt.setVisibility(View.VISIBLE);

		/*********************** 底部弹窗处理 ************************/
		vSearchResultContainer.removeAllViews();
		vSearchResultContainer.addView(vRoadNormalDetail);
		searchHelper.bottomContainerInfosShow(roadInfo, vRoadNormalDetail, province_name, province_code);

		// 动态变化数据
		vCurrentStationTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_detail_current_station_txt);
		vCurrentLocationTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_detail_current_location_txt);

		vCurrentFeedUnitTxt = (TextView) vSearchResultContainer.findViewById(R.id.road_detail_feed_unit_txt);
		vCurrentFeedUnitImg = (ImageView) vSearchResultContainer.findViewById(R.id.road_detail_feed_unit_img);
		vSearchResultContainer.setVisibility(View.VISIBLE);

		/*********************** 公路高亮显示处理 ************************/
		ArrayList<RoadLonLatProvince> road_lon_lat_province = roadInfo.getRoad_lon_lat_province();

		Graphic[] normalRoadGraphics = new Graphic[road_lon_lat_province.size()];

		if (road_lon_lat_province.size() != 0) {

			for (int j = 0; j < road_lon_lat_province.size(); j++) {

				ArrayList<LonLatsInfo> road_lon_lat_list = road_lon_lat_province.get(j).getRoad_lon_lat_list();

				moveLatsInfoList.addAll(road_lon_lat_list);

				// for (int i = 0; i < road_lon_lat_province.size(); i++) {
				// moveLatsInfoList.add(road_lon_lat_list.get(i));
				// }

				Graphic normalRoadGraphic = mapUtils.drawNormalRoads(road_lon_lat_list, 1, 1);

				normalRoadGraphics[j] = normalRoadGraphic;

				mapUtils.drawNormalRoadsTest(road_lon_lat_list, 1);
			}

			int[] noramlRoadsUids = graphicsLayer.addGraphics(normalRoadGraphics);
			graphicsLayer.updateGraphics(noramlRoadsUids, normalRoadGraphics);
		}

		mapUtils.ZoomToRoad(moveLatsInfoList);

		/********************* 十字针相关信息及动态数据初始化 **********************/
		LonLatsInfo lonLatsInfo = roadInfo.getRoad_lon_lat();

		if (startPoint == null) {

			startPoint = new Point(Double.valueOf(lonLatsInfo.getLongitude()), Double.valueOf(lonLatsInfo.getLatitude()));
		}

		if (road_station == null) {
			road_station = lonLatsInfo.getRoad_station();

		}

		vCurrentStationTxt.setText(mapUtils.changeStationToStandard(road_station));
		pointInfoStationTxt.setText(mapUtils.changeStationToStandard(road_station));

		if (currentPosition == null) {

			if (lonLatsInfo.getCurrent_position() != null) {
				currentPosition = lonLatsInfo.getCurrent_position();
			}
		}

		vCurrentLocationTxt.setText(currentPosition);
		pointInfoLocationTxt.setText(road_code + " " + currentPosition);

		if (currentFeedUnit == null) {
			currentFeedUnit = lonLatsInfo.getCurrent_feed_unit();
		}

		vCurrentFeedUnitTxt.setText(currentFeedUnit);

		shizizhenMap.clear();
		shizizhenMap.put("road_station", road_station);
		shizizhenMap.put("current_position", currentPosition);

		// 设置浮窗样式
		callout.setContent(pointInfoView);
		CalloutStyle calloutStyle = new CalloutStyle(MainActivity.this);
		callout.setOffsetDp(0, 30);
		callout.setStyle(calloutStyle);
		callout.setCoordinates(startPoint);
		callout.show();

		shizizhen_uid = shizizhenLayer.addGraphic(shizizhenGraphic);
		shizizhenLayer.updateGraphic(shizizhen_uid, pSymbol);
		shizizhenLayer.updateGraphic(shizizhen_uid, startPoint);
		shizizhenLayer.updateGraphic(shizizhen_uid, shizizhenMap);
	}

	/**
	 * 设置动态数据
	 * 
	 * @param to
	 *            滑动中的手指落点
	 * @param
	 */
	private void mSetDynamicData(Graphic line, Point to, LonLatsInfo lonLatsInfo) {

		if (lonLatsInfo != null) {

			road_station = lonLatsInfo.getRoad_station();
			currentPosition = lonLatsInfo.getCurrent_position();

			String longitude = lonLatsInfo.getLongitude();
			String latitude = lonLatsInfo.getLatitude();

			startPoint = new Point(Double.valueOf(longitude), Double.valueOf(latitude));

		} else {

			road_station = (String) line.getAttributeValue("road_station");
			currentPosition = (String) line.getAttributeValue("current_position");

			String longitude = (String) line.getAttributeValue("longitude");
			String latitude = (String) line.getAttributeValue("latitude");

			if (to != null && line != null) {

				startPoint = vMap.toMapPoint(to);

			} else {
				startPoint = new Point(Double.valueOf(longitude), Double.valueOf(latitude));
			}
		}

		if (road_station == null) {
			road_station = "";
		}

		if (currentPosition == null) {
			currentPosition = "";
		}

		// 指标分布
		if (roadIndicatorsResult != null || provinceRoadPCIInfo != null) {

			if (lonLatsInfo != null) {
				currentRoadWidth = lonLatsInfo.getRoad_way_width();
				currentRoadFaceType = lonLatsInfo.getRoad_face_type();
				currentIndicatorValue = lonLatsInfo.getCurrent_indicator_value();
				currentDailyTrafficNum = lonLatsInfo.getDaily_traffic_volume();
				currentBuildYears = lonLatsInfo.getRoad_build_years();
				currentConservationYears = lonLatsInfo.getRoad_conservation_years();
				currentRoadCondition = lonLatsInfo.getRoad_surface_condition();
				currentConservationAdvise = lonLatsInfo.getRoad_conservation_advise();
				currentRoadDataYear = lonLatsInfo.getRoad_data_year();
				currentRoadDirection = lonLatsInfo.getRoad_direction();

			} else {

				currentRoadWidth = (String) line.getAttributeValue("current_road_width");
				currentRoadFaceType = (String) line.getAttributeValue("current_road_facetype");
				currentIndicatorValue = (String) line.getAttributeValue("current_indicator_num");
				currentDailyTrafficNum = (String) line.getAttributeValue("current_daily_traffic");
				currentBuildYears = (String) line.getAttributeValue("current_build_years");
				currentConservationYears = (String) line.getAttributeValue("current_conservation_years");
				currentRoadCondition = (String) line.getAttributeValue("current_road_condition");
				currentConservationAdvise = (String) line.getAttributeValue("current_conservation_advise");

				currentRoadDataYear = (String) line.getAttributeValue("current_road_data_year");
				currentRoadDirection = (String) line.getAttributeValue("road_direction");

			}

			if (currentIndicatorValue == null) {
				currentIndicatorValue = "";
			}
			if (currentDailyTrafficNum == null) {
				currentDailyTrafficNum = "";
			}
			if (currentBuildYears == null) {
				currentBuildYears = "";
			}
			if (currentConservationAdvise == null) {
				currentConservationAdvise = "";
			}
			if (currentRoadCondition == null) {
				currentRoadCondition = "";
			}
			if (currentConservationYears == null) {
				currentConservationYears = "";
			}

			if (currentRoadDataYear == null) {
				currentConservationYears = "";
			}
			if (currentRoadWidth == null) {
				currentRoadWidth = "";
			}
			if (currentRoadFaceType == null) {
				currentRoadFaceType = "";
			}
			if (currentIndicatorValue == null) {
				currentIndicatorValue = "";
			}

			vCurrentRoadWidthTxt.setText(currentRoadWidth + "m");
			vCurrentRoadFaceTypeTxt.setText(currentRoadFaceType);
			vCurrentDailyTrafficNumTxt.setText(currentDailyTrafficNum + "辆");
			vCurrentBuildYearsTxt.setText(currentBuildYears);
			vCurrentConservationYearTxt.setText(currentConservationYears);
			vCurrentRoadSurConditionTxt.setText(currentRoadCondition);
			VCurrentConservationAdviseTxt.setText(currentConservationAdvise);
			vCurrentRoadDataYearTxt.setText(currentRoadDataYear);

			// 小浮窗
			pointInfoStationTxt.setText(mapUtils.changeStationToStandard(road_station) + " " + query_indicator_id.toUpperCase()

			+ currentIndicatorValue);

		} else {

			if (lonLatsInfo != null) {
				currentFeedUnit = lonLatsInfo.getCurrent_feed_unit();
			} else {
				currentFeedUnit = (String) line.getAttributeValue("current_unit");

			}

			if (currentFeedUnit == null) {
				currentFeedUnit = "";
			}
			vCurrentFeedUnitTxt.setText(currentFeedUnit);
			// 小浮窗
			pointInfoStationTxt.setText(mapUtils.changeStationToStandard(road_station));

		}

		// 小浮窗
		pointInfoLocationTxt.setText(road_code + " " + currentPosition);

		if (currentRoadDirection == null) {
			currentRoadDirection = "";
		}
		Log.i("========================", "=====currentRoadDirection===" + currentRoadDirection);

		// 底部弹窗
		vCurrentStationTxt.setText(mapUtils.changeStationToStandard(road_station) + " " + currentRoadDirection);
		vCurrentLocationTxt.setText(currentPosition);

		if (to != null && line != null) {

			shizizhenLayer.updateGraphic(shizizhen_uid, shizizhenMap);
			shizizhenLayer.updateGraphic(shizizhen_uid, pRedSymbol);
			shizizhenLayer.updateGraphic(shizizhen_uid, MoveEndPoint);

			if (callout != null && callout.isShowing()) {

				callout.move(MoveEndPoint);
			}

		} else {

			shizizhenLayer.updateGraphic(shizizhen_uid, pSymbol);
			shizizhenLayer.updateGraphic(shizizhen_uid, startPoint);

			if (callout != null && callout.isShowing()) {

				callout.move(startPoint);
			}

		}

	}

	/**
	 * 单条公路指标显示时处理方法
	 * 
	 * @param pciInfo
	 *            公路指标信息类
	 */
	private void mSingleIndicatorRoad(RoadsInfo roadInfo, PCIInfo pciInfo) {

		road_code = roadInfo.getRoad_code().trim();
		road_name = roadInfo.getRoad_name();
		startStation = pciInfo.getRoad_start_station();
		endStation = pciInfo.getRoad_end_station();

		// 搜索栏显示
		vSearchEdtTxt.setText(road_code + " " + road_name);
		vProvinceChoicesTxt.setText(mapUtils.subStringProvinceName(province_name, province_code));

		// 右侧按钮
		vRoadInfoIbt.setVisibility(View.VISIBLE);
		vRoadSkillKpi.setVisibility(View.VISIBLE);
		vPlayImageBegin.setVisibility(View.VISIBLE);

		// 上一桩号，下一桩号
		vRoadNextStationIbt.setVisibility(View.VISIBLE);
		vRoadForwardStationIbt.setVisibility(View.VISIBLE);

		// 底部按钮
		vBottomBtnLayout.setVisibility(View.VISIBLE);
		vBottomBridgeImgLayout.setVisibility(View.GONE);
		vBottomRoadInfoLayout.setVisibility(View.GONE);

		moveLatsInfoList.clear();

		ArrayList<RoadPCIInfo> road_distributes_list = roadInfo.getRoad_indicator_distribute();

		// 公路经纬度坐标初始化
		if (road_distributes_list != null && road_distributes_list.size() != 0) {
			for (int i = 0; i < road_distributes_list.size(); i++) {
				ArrayList<LonLatsInfo> road_lonlats_list = road_distributes_list.get(i).getRoad_lon_lat_list();

				if (road_lonlats_list != null && road_lonlats_list.size() != 0) {

					for (int j = 0; j < road_lonlats_list.size(); j++) {

						moveLatsInfoList.add(road_lonlats_list.get(j));
					}
				}
			}

		}

		if (moveLatsInfoList.size() != 0) {

			/************************ 底部弹窗信息 ***********************/
			vshowDetailIbt.setVisibility(View.GONE);
			vSearchResultContainer.removeAllViews();
			vSearchResultContainer.addView(roadIndicatorBottomView);
			if (vSearchVisit) {
				vSearchResultContainer.setVisibility(View.VISIBLE);
			} else {
				vSearchResultContainer.setVisibility(View.GONE);
			}
			// findViewById
			roadIndicatorFindId();

			vRoadNameTxt.setText(road_code + " " + road_name);

			LonLatsInfo lonLatsInfo = null;

			// 桩号的起始数据
			if (road_station == null && startPoint == null && currentPosition == null) {

				lonLatsInfo = moveLatsInfoList.get(0);

			} else {

				lonLatsInfo = mapUtils.findLonLats(road_station, moveLatsInfoList);

			}

			// 当前桩号
			road_station = lonLatsInfo.getRoad_station();

			// 当前十字针点
			startPoint = new Point(Double.valueOf(lonLatsInfo.getLongitude()), Double.valueOf(lonLatsInfo.getLatitude()));

			// 当前位置
			currentPosition = lonLatsInfo.getCurrent_position();
			// 当前指标值
			currentIndicatorValue = lonLatsInfo.getCurrent_indicator_value();
			// 当前路面宽度
			currentRoadWidth = lonLatsInfo.getRoad_way_width();
			// 当前路面类型
			currentRoadFaceType = lonLatsInfo.getRoad_face_type();
			// 当前日交通量
			currentDailyTrafficNum = lonLatsInfo.getDaily_traffic_volume();
			// 当前修建年度
			currentBuildYears = lonLatsInfo.getRoad_build_years();
			// 当前养护年度
			currentConservationAdvise = lonLatsInfo.getRoad_conservation_years();
			// 当前路面状况
			currentRoadCondition = lonLatsInfo.getRoad_surface_condition();
			// 当前养护建议
			currentConservationAdvise = lonLatsInfo.getRoad_conservation_advise();
			// 当前数据年度
			currentRoadDataYear = lonLatsInfo.getRoad_data_year();
			// 上下行
			currentRoadDirection = lonLatsInfo.getRoad_direction();
			// 当前养护年度
			currentConservationYears = lonLatsInfo.getRoad_conservation_years();
			// 当前车道数
			// currentCarRoadNum = lonLatsInfo.getRoad_lane_num();
			// 当前技术等级
			// currentTecLevel = lonLatsInfo.getBridge_technical_grade();
			// 当前管养单位
			// currentFeedUnit = lonLatsInfo.getCurrent_feed_unit();

			if (currentIndicatorValue == null) {
				currentIndicatorValue = "";
			}

			if (currentFeedUnit == null) {
				currentFeedUnit = "";
			}

			if (currentPosition == null) {
				currentPosition = "";
			}

			if (currentRoadWidth == null) {
				currentRoadWidth = "";
			}

			if (currentRoadFaceType == null) {
				currentRoadFaceType = "";
			}
			if (currentIndicatorValue == null) {
				currentIndicatorValue = "";
			}
			if (currentDailyTrafficNum == null) {
				currentDailyTrafficNum = "";
			}
			if (currentBuildYears == null) {
				currentBuildYears = "";
			}
			if (currentConservationYears == null) {
				currentConservationYears = "";
			}
			if (currentRoadCondition == null) {
				currentRoadCondition = "";
			}
			if (currentConservationAdvise == null) {
				currentConservationAdvise = "";
			}

			if (currentRoadDataYear == null) {
				currentRoadDataYear = "";
			}

			if (currentRoadDirection == null) {
				currentRoadDirection = "";
			}
			// 底部弹窗
			vCurrentRoadWidthTxt.setText(currentRoadWidth + "m");
			vCurrentRoadFaceTypeTxt.setText(currentRoadFaceType);
			vCurrentDailyTrafficNumTxt.setText(currentDailyTrafficNum + "辆");
			vCurrentBuildYearsTxt.setText(currentBuildYears);
			vCurrentConservationYearTxt.setText(currentConservationYears);
			vCurrentRoadSurConditionTxt.setText(currentRoadCondition);
			VCurrentConservationAdviseTxt.setText(currentConservationAdvise);
			vCurrentRoadDataYearTxt.setText(currentRoadDataYear);

			shizizhenMap.clear();
			shizizhenMap.put("road_station", road_station);
			shizizhenMap.put("current_position", currentPosition);

			shizizhen_uid = shizizhenLayer.addGraphic(shizizhenGraphic);
			shizizhenLayer.updateGraphic(shizizhen_uid, pSymbol);
			shizizhenLayer.updateGraphic(shizizhen_uid, startPoint);
			shizizhenLayer.updateGraphic(shizizhen_uid, shizizhenMap);

			if (callout != null) {

				pointInfoLocationTxt.setText(road_code + " " + currentPosition);

				pointInfoStationTxt.setText(mapUtils.changeStationToStandard(road_station) + ", " + query_indicator_id.toUpperCase()

				+ currentIndicatorValue);

				vCurrentLocationTxt.setText(currentPosition);
				vCurrentStationTxt.setText(mapUtils.changeStationToStandard(road_station) + " " + currentRoadDirection);
				// vCurrentFeedUnitTxt.setText(currentFeedUnit);

				// 设置浮窗样式
				callout.setContent(pointInfoView);
				CalloutStyle calloutStyle = new CalloutStyle(MainActivity.this);
				callout.setOffsetDp(0, 30);
				callout.setStyle(calloutStyle);
				callout.setCoordinates(startPoint);
				callout.show();

			}

			/******** 异步结果status为Y,但pci数据集合却为空 *****************/
			if (roadInfo.getRoad_indicator_distribute() == null || roadInfo.getRoad_indicator_distribute().size() == 0) {
				CustomApp.app.customToast("该路段暂无检测数据，亲换条路试试吧~");

			}

			/******************** 画公路指标颜色线的处理 *********************/
			roadIndicatorGraphics = mapUtils.drawRoadPCI(roadInfo.getRoad_indicator_distribute(), 1);
			mapUtils.drawRoadPCITest(roadInfo.getRoad_indicator_distribute(), -1);

			// 缩放地图
			mapUtils.ZoomToRoad(moveLatsInfoList);

			// 高亮省份轮廓..
			if (fr == null || fr.featureCount() == 0 || province_code == null || "123001".equals(province_code)) {
				provinceLayer.removeAll();

			} else {

				mapUtils.heightLightProvince(fr);
			}

		}

	}

	/**
	 * 省份以下桥梁气泡显示处理
	 */
	private void mShowBridgesBubble(BridgeMarksInfo bridgeMarksInfo) {
		bridgePositionInfos.clear();
		bridgePositionInfos.addAll(bridgeMarksInfo.getBridge_positions_list());

		if (bridgePositionInfos.size() != 0) {

			BridgesAdapter adapter = new BridgesAdapter(MainActivity.this, bridgePositionInfos);

			sildeManager.listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();

			bridgeHelper.showBridgeMarkers(null, bridgeMarksInfo.getBridge_positions_list(), -1);

		}

	}

	/**
	 * 桥梁气泡显示处理
	 */
	private void mShowBridgesBubblePr(ArrayList<BridgeMarksInfo> bridgeMarksInfos) {
		if (bmNextList.size() != 0) {

			BridgesPrAdapter adapter = new BridgesPrAdapter(MainActivity.this, bmNextList);

			sildeManager.listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			bridgeHelper.showBridgeMarkers2(bridgeMarksInfos, null);

		}

	}

	/**
	 * 桥梁气泡点击处理
	 */
	private void mBridgeOnClick(Graphic bridgeGraphic, BridgePositionInfo bridgePositionInfo) {
		vBottomStatisticLayout.setVisibility(View.INVISIBLE);
		if (callout == null) {
			callout = vMap.getCallout();
		}

		bridge_name = "";
		bridge_station = "";
		road_code = "";
		bridge_code = "";

		// 统计返回桥梁
		if (bridgeGraphic != null) {

			// 跳转大图需要的参数
			bridge_station = (String) bridgeGraphic.getAttributeValue("bridge_center_station");
			road_code = ((String) bridgeGraphic.getAttributeValue("road_code")).trim();
			bridge_name = ((String) bridgeGraphic.getAttributeValue("bridge_name")).trim();
			bridge_code = ((String) bridgeGraphic.getAttributeValue("bridge_code")).trim();

			int index = (Integer) bridgeGraphic.getAttributeValue("list_index");

			ArrayList<BridgePositionInfo> city_bridge_list = null;

			bridgeLayer.removeAll();
			if (info != null) {

				city_bridge_list = info.getBridge_positions_list();

			} else {
				city_bridge_list = bridgePositionInfos;
			}
			bridgeHelper.showBridgeMarkers(null, city_bridge_list, index);

		} else {
			bridge_station = bridgePositionInfo.getBridge_center_station();
			road_code = bridgePositionInfo.getRoad_code().trim();

			bridge_name = bridgePositionInfo.getBridge_name();
			bridge_code = bridgePositionInfo.getBridge_code();

		}
		// 地图底部按钮
		vBottomBtnLayout.setVisibility(View.INVISIBLE);
		vBottomBridgeImgLayout.setVisibility(View.VISIBLE);
		vBottomRoadInfoLayout.setVisibility(View.GONE);
		vBottomLargeImgLayout.setVisibility(View.GONE);
		vBottomDialogLayout.setVisibility(View.GONE);

		bridgeHelper.mClickOnBridge(MainActivity.this, bridgeGraphic, bridgePositionInfo, callout, mapUtils, vSearchResultContainer);
		// 桥梁图片
		vBottomRoadLeftLly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				image_type = constant.BIRDGE;
				bottomScreen.setVisibility(View.INVISIBLE);
				vBottomLargeImgLayout.setVisibility(View.VISIBLE);
				startTheard(12);

			}
		});
		//桥梁明细
		vBridgeImgBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, BridgeDetailInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("province_code", province_code);
				bundle.putString("bridge_code", bridge_code);
				bundle.putString("bridge_name", bridge_name);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		// 桥梁到这里去
		vComeToBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CustomApp.app.customToast(getString(R.string.on_the_line_please_look_forward_to));

			}
		});
	}

	/**
	 * sildingManager初始化
	 */
	private void mInitSildingManager() {

		vBottomBtnLayout.setVisibility(View.GONE);
		vBottomDialogLayout.setVisibility(View.VISIBLE);
		sildeManager = new SildeManager(MainActivity.this, findViewById(R.id.container));

		sildeManager.init();

		if (isFourOrFiveBridge) {
			sildeManager.vButtom.setVisibility(View.GONE);
		}

		// 是否能滑动
		if ("".equals(province_code) || province_code == null || "123001".equals(province_code)) {
			sildeManager.setOpenAnimator(false);

		} else {
			sildeManager.setOpenAnimator(true);
		}

		sildeManager.setLeftText(R.string.classification_statistic);
		sildeManager.setLeftImageIcon(R.drawable.classfy_statistic);
		sildeManager.setRightText(R.string.fourfive_bridge);
		sildeManager.setRightImageIcon(R.drawable.fourfive_bridge);

		sildeManager.setSildeListener(new SildeListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!isPr) {
					bridgeLayer.removeAll();
					provinceLayer.removeAll();
					isPr = true;
					if (bmNextList != null && bmNextList.size() > 0) {
						city_code = bmNextList.get(position).getArea_code();
						city_name = bmNextList.get(position).getArea_name();
						for (int i = 0; i < bmNextList.size(); i++) {
							if (city_code.equals(bmNextList.get(i).getArea_code())) {
								info = bmNextList.get(i);
								break;
							}
						}
						Envelope envelope2 = new Envelope();
						envelope2.setXMin(Double.parseDouble(info.getLongitude()) - 1);// 95.92488868
						envelope2.setYMin(Double.parseDouble(info.getLatitude()) - 1);// 14.236114
						envelope2.setXMax(Double.parseDouble(info.getLongitude()) + 1);// 125.32730256
						envelope2.setYMax(Double.parseDouble(info.getLatitude()) + 1);// 38.47956465
						vMap.setExtent(envelope2);
						sildeManager.setTitleText(info.getArea_name() + bridge_type + "：" + info.getBridge_number() + "座" + "（滑动查看详情）");
						mShowBridgesBubble(info);
					}
				} else {

					bridgePositionInfo = bridgePositionInfos.get(position);

					if (bridgePositionInfo != null) {

						mBridgeOnClick(null, bridgePositionInfo);

					}
				}

			}

			// 45类桥分布
			@Override
			public void onButtomRightClick(View view) {
				Intent intent = new Intent(MainActivity.this, FortyFiveBridgeActivity.class);

				// 每次都要置空
				bridgeMarksInfo = null;

				if (currentCity == null) {
					currentCity = new CityList();
					province_name = "全国";
					province_code = "123001";
				}

				currentCity.setProvince_code(province_code);
				currentCity.setProvince_name(province_name);

				intent.putExtra("currentCity", currentCity);
				startActivity(intent);

			}

			// 分类统计
			@Override
			public void onButtomLeftClick(View view) {
			}

			@Override
			public void animatorEnd(int type) {
			}
		});
	}

	/**
	 * 多条公路的底部弹窗
	 */

	private void multRoadsBottomshow() {

		if (pre_road_code != null && !"".equals(pre_road_code)) {

			// 搜索栏
			vSearchEdtTxt.setText(pre_road_code);
		}

		View provinceInfoView = inflater.inflate(R.layout.province_all_info_container, null);
		vSearchResultContainer.removeAllViews();

		TextView provinceInfoNameTxt = (TextView) provinceInfoView.findViewById(R.id.road_indicator_province_info_txt);
		TextView provinceTotalNumTxt = (TextView) provinceInfoView.findViewById(R.id.road_indicator_total_num_txt);
		TextView provinceMilisTxt = (TextView) provinceInfoView.findViewById(R.id.road_indicator_mil_txt);
		TextView provinceIndicatorInfoTxt = (TextView) provinceInfoView.findViewById(R.id.road_indicator_info_txt);

		provinceInfoNameTxt.setText(province_name + " " + query_indicator_id.toUpperCase() + "指标分布图");

		provinceInfoNameTxt.setVisibility(View.GONE);

		provinceTotalNumTxt.setText(provinceSituation.getResult_total_num() + "条");

		provinceMilisTxt.setText(provinceSituation.getResult_mil_num() + "公里");
		provinceIndicatorInfoTxt.setText(provinceSituation.getResult_indicator_value());

		// TextView provinceInfoNameTxt = (TextView)
		// provinceInfoView
		// .findViewById(R.id.province_info_name_txt);
		// TextView provinceInfoGRoadTxt = (TextView)
		// provinceInfoView
		// .findViewById(R.id.province_info_guodao_num_txt);
		// TextView provinceInfoSRoadTxt = (TextView)
		// provinceInfoView
		// .findViewById(R.id.province_info_shengdao_num_txt);
		// TextView provinceInfoXRoadTxt = (TextView)
		// provinceInfoView
		// .findViewById(R.id.province_info_xiandao_num_txt);
		// TextView provinceInfoTotalMiliTxt = (TextView)
		// provinceInfoView
		// .findViewById(R.id.province_info_total_mili_txt);
		// TextView provinveInfoBridgeNumTxt = (TextView)
		// provinceInfoView
		// .findViewById(R.id.province_info_bridge_total_num_txt);
		// TextView provinceInfoBridgeMiliTxt = (TextView)
		// provinceInfoView
		// .findViewById(R.id.province_info_bridge_total_mili_txt);
		// TextView provinceInfoNewRoadTxt = (TextView)
		// provinceInfoView
		// .findViewById(R.id.province_info_new_road_mili_txt);
		//
		//
		//
		//
		// provinceInfoNameTxt.setText(province_name + "省总体概况");
		//
		// provinceInfoGRoadTxt.setText(provinceSituation
		// .getNational_road_num() + "条");
		// provinceInfoSRoadTxt.setText(provinceSituation
		// .getProvince_road_num() + "条");
		//
		// if (provinceSituation.getCounty_road_num() != null) {
		// provinceInfoXRoadTxt.setText(provinceSituation
		// .getCounty_road_num() + "条");
		//
		// } else {
		// provinceInfoXRoadTxt.setText("0条");
		//
		// }
		// provinceInfoTotalMiliTxt.setText(provinceSituation
		// .getRoad_mil_num() + "公里");
		// provinveInfoBridgeNumTxt.setText(provinceSituation
		// .getBridge_num() + "座");
		// provinceInfoBridgeMiliTxt.setText(provinceSituation
		// .getBridge_mil_num() + "延米");
		// provinceInfoNewRoadTxt.setText(provinceSituation
		// .getNew_road_mil_num() + "公里");

		vSearchResultContainer.addView(provinceInfoView);
		vSearchResultContainer.setVisibility(View.VISIBLE);

		vBottomBtnLayout.setVisibility(View.VISIBLE);

	}

	/**
	 * 十字针及动态数据清空
	 */
	private void clearDynaticData() {

		if (callout != null && callout.isShowing()) {
			callout.hide();
		}
		imagemoveLatsInfo = null;
		roadInfo = null;
		road_station = null;
		startPoint = null;
		currentPosition = null;
		currentFeedUnit = null;
		currentIndicatorValue = null;
		currentRoadFaceType = null;
		currentRoadWidth = null;
		currentTecLevel = null;
		currentDailyTrafficNum = null;
		currentBuildYears = null;
		currentConservationYears = null;
		currentConservationAdvise = null;
		currentRoadCondition = null;
		currentRoadDirection = null;
		currentRoadDataYear = null;

	}

	/**
	 * 清空结果
	 */
	private void clearAll() {

		/******************** 变量 *******************/
		// 搜索相关
		searchResult = null;

		// 公路相关
		query_indicator_id = null;
		roadIndicatorsResult = null;
		pressRoadInfoResult = null;
		searchNoticesResult = null;
		startStation = null;
		endStation = null;

		playImageResult = null;
		moveLatsInfoList.clear();
		provinceRoadPCIInfo = null;
		provinceSituation = null;
		currentCity = null;
		arrayimage_list.clear();
		roadIndicatorGraphics = null;

		// 统计相关
		statisticalType = null;

		// 十字针相关
		clearDynaticData();

		// 桥梁相关
		bridgeNationalInfos.clear();
		bridgePositionInfos.clear();
		bridgeMarksResult = null;
		isFourOrFiveBridge = false;

		// 服务区相关
		serviceNationalInfoList.clear();
		servicePositionInfoList.clear();
		serviceHelper = null;

		// 省份信息
		province_code = "123001";
		province_name = getResources().getString(R.string.country);

		// 清空标识
		isSelectProvince = true;
		isClearAll = true;
		isBackToRoads = false;

		/******************** 控件 ********************/
		// 公路相关
		vSearchThumbImg.setVisibility(View.GONE);

		// 搜索栏相关
		vBackBtn.setVisibility(View.GONE);
		vBlackView.setVisibility(View.GONE);
		vSearchBarLayout.setVisibility(View.VISIBLE);
		vSearchEdtTxt.setText(null);
		vProvinceChoicesTxt.setText(mapUtils.subStringProvinceName(province_name, province_code));

		// 右侧按钮
		vRoadSkillKpi.setVisibility(View.GONE);
		vPlayImageBegin.setVisibility(View.GONE);
		vRoadInfoIbt.setVisibility(View.GONE);
		vRoadNextStationIbt.setVisibility(View.GONE);
		vRoadForwardStationIbt.setVisibility(View.GONE);

		// 底部弹窗和按钮
		vSearchResultContainer.setVisibility(View.GONE);
		vshowDetailIbt.setVisibility(View.GONE);
		vBottomLargeImgLayout.setVisibility(View.GONE);
		vBottomBtnLayout.setVisibility(View.VISIBLE);
		vBottomDialogLayout.setVisibility(View.GONE);
		vBottomBridgeImgLayout.setVisibility(View.GONE);

		// 地图相关
		graphicsLayer.removeAll();
		transparentLayer.removeAll();
		provinceLayer.removeAll();
		normal_layer.setVisible(true);
		bridgeLayer.removeAll();
		shizizhenLayer.removeAll();
		fr = null;
		vMap.setExtent(envelope);
	}

	private class frTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			fr = mapUtils.SearchCity(city_name, null);
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			if (fr != null && fr.featureCount() != 0) {
				city_name = mapUtils.heightLightProvince(fr);
				mapUtils.ZoomToProvince(fr);
			}
			return false;
		}
	}

}
