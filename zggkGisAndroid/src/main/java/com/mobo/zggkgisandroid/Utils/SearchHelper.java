package com.mobo.zggkgisandroid.Utils;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgeNationalInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgePositionInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.ConstructionInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.FacilityInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadPCIInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.SearchInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.TunnelInfo;
import com.mobo.zggkgisandroid.WebModel.BridgeMarksResult;
import com.mobo.zggkgisandroid.WebModel.RoadPCIResult;
import com.mobo.zggkgisandroid.WebModel.SearchResult;

/**
 * 搜索结果分类处理工具类
 * 
 * @author wjm
 * 
 */
public class SearchHelper {

	private Context context;
	private MapUtils mapUtils;
	private MapView vMap;
	private GraphicsLayer graphicsLayer;
	private LinearLayout vSearchResultContainer, vZoomContainer;// 底部弹窗容器
	private ImageButton vBackBtn;// 清空搜索按钮
	private ImageButton vPlayImageBegin;// 查看大图
	private LayoutInflater inflater;

	private RelativeLayout.LayoutParams paramsV = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.WRAP_CONTENT,
			RelativeLayout.LayoutParams.WRAP_CONTENT);
	private RelativeLayout.LayoutParams paramsG = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.WRAP_CONTENT,
			RelativeLayout.LayoutParams.WRAP_CONTENT);

	/**
	 * 
	 * @param context
	 *            context对象
	 * @param mapUtils
	 *            地图工具类
	 * @param vMap
	 *            地图控件
	 * @param graphicsLayer
	 *            绘制图层
	 * @param vSearchResultContainer
	 *            底部弹窗
	 * @param vBackBtn
	 *            返回按钮
	 */
	public SearchHelper(Context context, MapUtils mapUtils, MapView vMap,
			GraphicsLayer graphicsLayer, LinearLayout vSearchResultContainer,
			ImageButton vBackBtn, ImageButton vPlayImageBegin,
			LinearLayout vZoomContainer) {
		this.context = context;
		this.mapUtils = mapUtils;
		this.vMap = vMap;
		this.graphicsLayer = graphicsLayer;
		this.vSearchResultContainer = vSearchResultContainer;
		this.vBackBtn = vBackBtn;
		this.vPlayImageBegin = vPlayImageBegin;
		this.vZoomContainer = vZoomContainer;

		inflater = LayoutInflater.from(context);
		paramsV.addRule(RelativeLayout.ABOVE, R.id.search_result_container);
		paramsV.setMargins(Utils.dip2px(context, 10), 0, 0,
				Utils.dip2px(context, 20));

		paramsG.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		paramsG.setMargins(Utils.dip2px(context, 10), 0, 0,
				Utils.dip2px(context, 35));

	}

	/**
	 * 搜索结果为公路时的处理方法
	 * 
	 * @param searchResult
	 *            搜索结果
	 * @param vRoadPCIDetail
	 *            底部弹窗布局view
	 * @param vRoadSkillKpi
	 *            公路技术指标显示按钮
	 * @param fr
	 *            搜索结果所在省份特征结果
	 * 
	 */

	public void showRoadsInfo(SearchResult searchResult,
			LinearLayout vRoadPCIDetail, ImageButton vRoadSkillKpi,
			FeatureResult fr) {

		if (searchResult != null) {

			// 搜索结果是单条公路，放大地图到公路上；
			if (searchResult.getResults().get(0).getRoad_list().size() != 0
					&& searchResult.getResults().get(0).getRoad_list().size() == 1) {
				ArrayList<RoadPCIInfo> road_indicator_distributes = searchResult
						.getResults().get(0).getRoad_list().get(0)
						.getRoad_indicator_distribute();

				// 显示指标图标
				if (searchResult.getResults().get(0).getHas_pci_data()
						.equals("1")) {

					vRoadSkillKpi.setVisibility(View.GONE);
				} else {
					vRoadSkillKpi.setVisibility(View.VISIBLE);
				}

				// 显示景观图片
				if (searchResult.getResults().get(0).getHas_img_data()
						.equals("1")
						|| searchResult.getResults().get(0).getHas_img_data()
								.equals("null")
						|| searchResult.getResults().get(0).getHas_img_data()
								.equals("")) {
					vPlayImageBegin.setVisibility(View.GONE);
				} else {
					vPlayImageBegin.setVisibility(View.VISIBLE);
				}

				mapUtils.ZoomToPCIRoad(road_indicator_distributes);

				mapUtils.animationContainerUpAndDown(vSearchResultContainer,
						"UP");
				// 底部弹窗显示
				bottomContainerInfosShow(searchResult.getResults().get(0)
						.getRoad_list().get(0), vRoadPCIDetail, searchResult
						.getResults().get(0).getProvince_name(), searchResult
						.getResults().get(0).getProvince_code());

				vSearchResultContainer.setVisibility(View.VISIBLE);
				vZoomContainer.setLayoutParams(paramsV);

				Log.i("========",
						"=====searchResult222222==="
								+ String.valueOf(searchResult == null));

			} else {

				vSearchResultContainer.removeAllViews();
				vSearchResultContainer.addView(LayoutInflater.from(context)
						.inflate(R.layout.search_results_layout, null));

				// 多条公路显示详细信息
				vSearchResultContainer.setVisibility(View.VISIBLE);
				vZoomContainer.setLayoutParams(paramsV);

				LinearLayout layout_road = (LinearLayout) vSearchResultContainer
						.findViewById(R.id.search_layout_road);
				LinearLayout layout_bridge = (LinearLayout) vSearchResultContainer
						.findViewById(R.id.search_layout_bridge);
				LinearLayout layout_tunnel = (LinearLayout) vSearchResultContainer
						.findViewById(R.id.search_layout_tunnel);
				LinearLayout layout_facility = (LinearLayout) vSearchResultContainer
						.findViewById(R.id.search_layout_FACILITY);
				LinearLayout layout_construction = (LinearLayout) vSearchResultContainer
						.findViewById(R.id.search_layout_CONSTRUCTION);

				TextView vRoadNum = (TextView) layout_road
						.findViewById(R.id.search_result_road_total_number);
				TextView vRoadKM = (TextView) layout_road
						.findViewById(R.id.search_result_road_total_km);

				vRoadNum.setText("");
				vRoadKM.setText("");

				layout_road.setVisibility(View.VISIBLE);
				layout_bridge.setVisibility(View.GONE);
				layout_tunnel.setVisibility(View.GONE);
				layout_facility.setVisibility(View.GONE);
				layout_construction.setVisibility(View.GONE);

				vRoadNum.setText(searchResult.getResults().get(0)
						.getResult_size()
						+ " 条");

				if (searchResult.getResults().get(0).getProvince_code()
						.equals("123001")) {

					vRoadKM.setText(searchResult.getResults().get(0)
							.getResult_mil_num()
							+ " 公里");
				} else {
					vRoadKM.setText(searchResult.getResults().get(0)
							.getResult_mil_num()
							+ " 公里"
							+ ","
							+ searchResult.getResults().get(0)
									.getProvince_name());
				}

				if (searchResult.getResults().get(0).getHas_pci_data()
						.equals("1")) {
					vRoadSkillKpi.setVisibility(View.GONE);
				} else {
					vRoadSkillKpi.setVisibility(View.VISIBLE);
				}
				vPlayImageBegin.setVisibility(View.GONE);

				mapUtils.ZoomToProvince(fr);
			}

			// 公路高亮显示（搜索结果类，放大显示探针重画标志）
			vBackBtn.setVisibility(View.VISIBLE);

		}

		Log.i("========",
				"=====searchResult3333333==="
						+ String.valueOf(searchResult == null));
	}

	/**
	 * 搜索结果为单条高亮公路时的处理方法
	 * 
	 * @param searchResult
	 *            搜索结果
	 * @param vRoadPCIDetail
	 *            底部弹窗布局view
	 * @param vRoadSkillKpi
	 *            公路技术指标显示按钮
	 * @param fr
	 *            搜索结果所在省份特征结果
	 * 
	 */
	public void bottomContainerInfosShow(RoadInfo roadInfos,
			LinearLayout vRoadNormalDetail, String province_name,
			String province_code) {
		// 单条公路显示PCI数据
		vSearchResultContainer.removeAllViews();

		TextView vRoadName = (TextView) vRoadNormalDetail
				.findViewById(R.id.query_road_detail_roadname);// 公路名称
		TextView vRoadKM = (TextView) vRoadNormalDetail
				.findViewById(R.id.query_road_detail_roadkm);// 总里程数
		TextView vRoadPath = (TextView) vRoadNormalDetail
				.findViewById(R.id.query_road_detail_path);// 途径
		ImageView vRoadPathImg = (ImageView) vRoadNormalDetail
				.findViewById(R.id.query_road_detail_path_img);// 途径
		// TextView vRoadTraffic = (TextView) vRoadPCIDetail
		// .findViewById(R.id.query_road_detail_traffic);// 交通量
		TextView vRoadStartstake = (TextView) vRoadNormalDetail
				.findViewById(R.id.query_road_detail_startstake);// 起始桩号
		TextView vRoadEndstake = (TextView) vRoadNormalDetail
				.findViewById(R.id.query_road_detail_endstake);// 终点桩号

		// 设置要显示的公路名称，总里程，途径，交通量，起始桩号，终点桩号
		vRoadName.setText(roadInfos.getRoad_code() + " "
				+ roadInfos.getRoad_name());

		if (!province_code.equals("123001")) {

			vRoadKM.setText(roadInfos.getRoad_mil_num() + " 公里" + ",  "
					+ province_name);
		} else {
			vRoadKM.setText(roadInfos.getRoad_mil_num() + " 公里");
		}
		vRoadPath.setText(roadInfos.getRoad_through_place());

		setText(vRoadPath, vRoadPathImg, vRoadKM);
		// vRoadTraffic.setText(roadInfos.getRoad_traffic_num() + " 辆/天");
		vRoadStartstake.setText(mapUtils.changeStationToStandard(roadInfos
				.getRoad_start_station()));
		vRoadEndstake.setText(mapUtils.changeStationToStandard(roadInfos
				.getRoad_end_station()));

		vSearchResultContainer.addView(vRoadNormalDetail);
	}

	private void setText(final TextView textView, final ImageView imageView,
			final TextView vRoadKM) {
		imageView.setVisibility(View.GONE);
		ViewTreeObserver vto = textView.getViewTreeObserver();

		vto.addOnGlobalLayoutListener(new

		OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				textView.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);
				int width = textView.getMeasuredWidth();
				if (width > vRoadKM.getWidth()) {
					imageView.setVisibility(View.VISIBLE);
				} else {
				}
			}

		});
	}

	/**
	 * 单座桥梁显示的处理方法
	 * 
	 * @param bridgeInfo
	 *            桥梁信息类
	 * 
	 */
	public void showUniqueBridge(BridgePositionInfo bridgeInfo) {

		String s_longitude = bridgeInfo.getLongitude();
		String s_latitude = bridgeInfo.getLatitude();

		// 为了不被服务器数据坑死。。。
		if (s_longitude == null || s_latitude == null) {
			Toast.makeText(context, "亲，服务器接口返回的桥梁位置为空！", Toast.LENGTH_SHORT)
					.show();
		} else {
			Double lontitude = Double.valueOf(s_longitude);
			Double latitude = Double.valueOf(s_latitude);

			vMap.zoomToScale(new Point(lontitude, latitude), 450000);

			vSearchResultContainer.removeAllViews();

			vSearchResultContainer.addView(LayoutInflater.from(context)
					.inflate(R.layout.bridge_details_layout, null));

			TextView vBridgeName = (TextView) vSearchResultContainer
					.findViewById(R.id.query_bridge_detai_bridgename);
			TextView vBridgeBelongRoad = (TextView) vSearchResultContainer
					.findViewById(R.id.bridge_details_belong_road);
			TextView vBridgeCenterStation = (TextView) vSearchResultContainer
					.findViewById(R.id.bridge_details_center_stake);
			TextView vBridgeLength = (TextView) vSearchResultContainer
					.findViewById(R.id.bridge_details_bridge_length);
			TextView vBridgeType = (TextView) vSearchResultContainer
					.findViewById(R.id.bridge_details_class);
			TextView vBridgeGrade = (TextView) vSearchResultContainer
					.findViewById(R.id.bridge_details_technical_grade);
			ImageButton vBridgeNgtc = (ImageButton) vSearchResultContainer
					.findViewById(R.id.query_bridge_detai_imagebutton);

			vBridgeName.setText("");

			// 为控件赋值
			vBridgeName.setText(bridgeInfo.getBridge_name());
			vBridgeBelongRoad.setText(bridgeInfo.getRoad_code());
			vBridgeCenterStation.setText(bridgeInfo.getBridge_center_station());
			vBridgeLength.setText(bridgeInfo.getBridge_length());
			vBridgeType.setText(bridgeInfo.getBridge_span_type());// 来自键值
			vBridgeGrade.setText(bridgeInfo.getBridge_technical_grade());
			vBridgeNgtc.setVisibility(View.GONE);

			// 是否国检判断
			if (bridgeInfo.getBridge_is_ngtc().equals("Y")) {
				vBridgeNgtc.setVisibility(View.VISIBLE);
			}

			showSingleResultThumbImage(s_longitude, s_latitude,
					bridgeInfo.getBridge_thumb_img_data());

			// 显示桥梁详细信息
			vSearchResultContainer.setVisibility(View.VISIBLE);
			vZoomContainer.setLayoutParams(paramsV);
		}
	}

	/**
	 * 单个隧道时的处理方法
	 * 
	 * @param tunnelInfo
	 *            隧道信息类
	 * 
	 */
	public void showUniqueTunnel(TunnelInfo tunnelInfo) {
		String s_longitude = tunnelInfo.getLongitude();
		String s_latitude = tunnelInfo.getLatitude();

		// 为了不被服务器数据坑死。。。
		if (s_longitude == null || s_latitude == null) {
			Toast.makeText(context, "亲，服务器接口返回的桥梁位置为空！", Toast.LENGTH_SHORT)
					.show();
		} else {
			Double lontitude = Double.valueOf(s_longitude);
			Double latitude = Double.valueOf(s_latitude);

			vMap.zoomToScale(new Point(lontitude, latitude), 450000);

			vSearchResultContainer.removeAllViews();

			vSearchResultContainer.addView(LayoutInflater.from(context)
					.inflate(R.layout.query_tunne_detail, null));

			TextView vTunnelName = (TextView) vSearchResultContainer
					.findViewById(R.id.query_tunne_detail_tunneName);
			TextView vTunnelBelongRoad = (TextView) vSearchResultContainer
					.findViewById(R.id.query_tunne_detail_road);
			TextView vTunnelCenterStation = (TextView) vSearchResultContainer
					.findViewById(R.id.query_tunne_detail_station);
			TextView vTunnelLength = (TextView) vSearchResultContainer
					.findViewById(R.id.query_tunne_detail_tunnelength);
			TextView vTunnelHeight = (TextView) vSearchResultContainer
					.findViewById(R.id.query_tunne_detail_heigh);
			TextView vTunnelGrade = (TextView) vSearchResultContainer
					.findViewById(R.id.query_tunne_detail_grade);

			vTunnelName.setText("");
			vTunnelBelongRoad.setText("");
			vTunnelCenterStation.setText("");
			vTunnelHeight.setText("");
			vTunnelLength.setText("");
			vTunnelGrade.setText("");

			// 为控件赋值
			vTunnelName.setText(tunnelInfo.getTunnel_name());
			vTunnelBelongRoad.setText(tunnelInfo.getRoad_code());
			vTunnelCenterStation.setText(tunnelInfo.getTunnel_center_station());
			vTunnelLength.setText(tunnelInfo.getTunnel_length() + " 米");
			vTunnelHeight.setText(tunnelInfo.getTunnel_height() + " 米");
			vTunnelGrade.setText(tunnelInfo.getTunnel_evaluate_grade());// 来自键值

			showSingleResultThumbImage(s_longitude, s_latitude,
					tunnelInfo.getTunnel_thumb_img_data());

			// 显示桥梁详细信息
			vSearchResultContainer.setVisibility(View.VISIBLE);
			vZoomContainer.setLayoutParams(paramsV);
		}
	}

	/**
	 * 单个设施时的处理方法
	 * 
	 * @param facilityInfo
	 *            设施信息类
	 * 
	 */
	public void showUniqueFacility(FacilityInfo facilityInfo) {
		String s_longitude = facilityInfo.getLongitude();
		String s_latitude = facilityInfo.getLatitude();

		// 为了不被服务器数据坑死。。。
		if (s_longitude == null || s_latitude == null) {
			Toast.makeText(context, "亲，服务器接口返回的桥梁位置为空！", Toast.LENGTH_SHORT)
					.show();
		} else {
			Double lontitude = Double.valueOf(s_longitude);
			Double latitude = Double.valueOf(s_latitude);

			vMap.zoomToScale(new Point(lontitude, latitude), 450000);

			vSearchResultContainer.removeAllViews();

			vSearchResultContainer.addView(LayoutInflater.from(context)
					.inflate(R.layout.qurey_facility_detail, null));

			TextView vFacilityName = (TextView) vSearchResultContainer
					.findViewById(R.id.query_facility_detail_name);
			TextView vFacilityBelongRoad = (TextView) vSearchResultContainer
					.findViewById(R.id.query_facility_detail_road);
			TextView vFacilityCenterStation = (TextView) vSearchResultContainer
					.findViewById(R.id.query_facility_detail_station);

			vFacilityName.setText("");
			vFacilityBelongRoad.setText("");
			vFacilityCenterStation.setText("");

			// 为控件赋值
			vFacilityName.setText(facilityInfo.getFacility_name());
			vFacilityBelongRoad.setText(facilityInfo
					.getFacility_location_route());
			vFacilityCenterStation.setText(facilityInfo
					.getFacility_center_station());

			showSingleResultThumbImage(s_longitude, s_latitude,
					facilityInfo.getFacility_thumb_img_data());

			// 显示桥梁详细信息
			vSearchResultContainer.setVisibility(View.VISIBLE);
			vZoomContainer.setLayoutParams(paramsV);
		}
	}

	/**
	 * 单个施工时的处理方法
	 * 
	 * @param constructionInfo
	 *            施工信息类
	 * 
	 */
	public void showUniqueConstrution(ConstructionInfo constructionInfo) {
		String s_longitude = constructionInfo.getLongitude();
		String s_latitude = constructionInfo.getLatitude();

		// 为了不被服务器数据坑死。。。
		if (s_longitude == null || s_latitude == null) {
			Toast.makeText(context, "亲，服务器接口返回的桥梁位置为空！", Toast.LENGTH_SHORT)
					.show();
		} else {
			Double lontitude = Double.valueOf(s_longitude);
			Double latitude = Double.valueOf(s_latitude);

			vMap.zoomToScale(new Point(lontitude, latitude), 450000);

			vSearchResultContainer.removeAllViews();

			vSearchResultContainer.addView(LayoutInflater.from(context)
					.inflate(R.layout.qurey_facility_detail, null));

			// 控件初始化
			TextView vConstructionName = (TextView) vSearchResultContainer
					.findViewById(R.id.qurey_construction_detail_name);
			TextView vConstructionBelongRoad = (TextView) vSearchResultContainer
					.findViewById(R.id.qurey_construction_detail_road);
			TextView vConstructionCenterStation = (TextView) vSearchResultContainer
					.findViewById(R.id.qurey_construction_detail_station);
			TextView vConstructionMoney = (TextView) vSearchResultContainer
					.findViewById(R.id.qurey_construction_detail_investment_money);
			TextView vConstructionProgress = (TextView) vSearchResultContainer
					.findViewById(R.id.qurey_construction_detail_comp_progress);
			TextView vConstructionManager = (TextView) vSearchResultContainer
					.findViewById(R.id.qurey_construction_detail_manage_unit);
			TextView vConstructionConstrctor = (TextView) vSearchResultContainer
					.findViewById(R.id.qurey_construction_detail_manage_unit);
			TextView vConstructionGrade = (TextView) vSearchResultContainer
					.findViewById(R.id.qurey_construction_detail_technical_grade);

			vConstructionName.setText("");
			vConstructionBelongRoad.setText("");
			vConstructionCenterStation.setText("");
			vConstructionMoney.setText("");
			vConstructionProgress.setText("");
			vConstructionManager.setText("");
			vConstructionConstrctor.setText("");
			vConstructionGrade.setText("");

			// 为控件赋值
			vConstructionName.setText(constructionInfo.getConstruction_name());
			vConstructionBelongRoad.setText(constructionInfo.getRoad_code());
			vConstructionCenterStation.setText(constructionInfo
					.getConstruction_center_station());
			vConstructionMoney.setText(constructionInfo
					.getConstruction_investment_money() + " 万");
			vConstructionProgress.setText(constructionInfo
					.getConstruction_comp_progress());
			vConstructionManager.setText(constructionInfo
					.getConstruction_manage_unit());
			vConstructionConstrctor.setText(constructionInfo
					.getConstruction_construct_unit());
			vConstructionGrade.setText(constructionInfo
					.getConstruction_technical_grade());

			showSingleResultThumbImage(s_longitude, s_latitude,
					constructionInfo.getConstruction_thumb_img_data());

			// 显示桥梁详细信息
			vSearchResultContainer.setVisibility(View.VISIBLE);
			vZoomContainer.setLayoutParams(paramsV);
		}
	}

	/**
	 * 单个搜索结果显示小图时的处理方法
	 * 
	 * @param s_longitude
	 *            经度
	 * @param s_latitude
	 *            纬度
	 * @param image_data
	 *            小图数据字符串
	 * 
	 */
	public void showSingleResultThumbImage(String s_longitude,
			String s_latitude, String image_data) {

		Double longitude = Double.valueOf(s_longitude);
		Double latitude = Double.valueOf(s_latitude) + 0.01;

		Point point = new Point(longitude, latitude);

		Bitmap bm = mapUtils.changeViewToBitmap(image_data);

		Drawable drawable = new BitmapDrawable(null, bm);

		Graphic graphic = new Graphic(point, new PictureMarkerSymbol(context,
				drawable));

		int uid = graphicsLayer.addGraphic(graphic);
		graphicsLayer.updateGraphic(uid, graphic);

	}

	/**
	 * 多条公路显示底部弹窗的处理方法
	 * 
	 * @param s_longitude
	 *            经度
	 * @param s_latitude
	 *            纬度
	 * @param image_data
	 *            小图数据字符串
	 * 
	 */
	public void showManyRoadsBottomInfo(SearchInfo searchInfo) {

		vSearchResultContainer.removeAllViews();
		vSearchResultContainer.addView(LayoutInflater.from(context).inflate(
				R.layout.search_results_layout, null));

		// 多条公路显示详细信息
		vSearchResultContainer.setVisibility(View.VISIBLE);

		LinearLayout layout_road = (LinearLayout) vSearchResultContainer
				.findViewById(R.id.search_layout_road);
		LinearLayout layout_bridge = (LinearLayout) vSearchResultContainer
				.findViewById(R.id.search_layout_bridge);
		LinearLayout layout_tunnel = (LinearLayout) vSearchResultContainer
				.findViewById(R.id.search_layout_tunnel);
		LinearLayout layout_facility = (LinearLayout) vSearchResultContainer
				.findViewById(R.id.search_layout_FACILITY);
		LinearLayout layout_construction = (LinearLayout) vSearchResultContainer
				.findViewById(R.id.search_layout_CONSTRUCTION);

		TextView vRoadNum = (TextView) layout_road
				.findViewById(R.id.search_result_road_total_number);
		TextView vRoadKM = (TextView) layout_road
				.findViewById(R.id.search_result_road_total_km);

		vRoadNum.setText("");
		vRoadKM.setText("");

		layout_road.setVisibility(View.VISIBLE);
		layout_bridge.setVisibility(View.GONE);
		layout_tunnel.setVisibility(View.GONE);
		layout_facility.setVisibility(View.GONE);
		layout_construction.setVisibility(View.GONE);

		vRoadNum.setText(searchInfo.getResult_size() + " 条");

		if (searchInfo.getProvince_code().equals("123001")) {

			vRoadKM.setText(searchInfo.getResult_mil_num() + " 公里");
		} else {
			vRoadKM.setText(searchInfo.getResult_mil_num() + " 公里" + ","
					+ searchInfo.getProvince_name());
		}

	}
}
