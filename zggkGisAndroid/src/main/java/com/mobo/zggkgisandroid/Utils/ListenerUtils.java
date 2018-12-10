package com.mobo.zggkgisandroid.Utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.esri.core.map.FeatureResult;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgeNationalInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgePositionInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.ConstructionInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.FacilityInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadPCIInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadsInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.TunnelInfo;
import com.mobo.zggkgisandroid.PlayImage.PlayImage;
import com.mobo.zggkgisandroid.WebModel.PressRoadInfoResult;
import com.mobo.zggkgisandroid.WebModel.RoadPCIResult;
import com.mobo.zggkgisandroid.WebModel.SearchResult;

/**
 * 地图，控件事件监听处理工具类
 * 
 * @author wjm
 */
public class ListenerUtils {

	private Context context;
	private MapView vMap;
	private MapUtils mapUtils;
	private SearchHelper searchUtils;
	private GraphicsLayer graphicsLayer;
	private LinearLayout vSearchResultContainer, vZoomContainer;// 底部弹窗容器
	private TextView vshowDetailIbt;
	private ImageButton vPlayImageBegin;
	private RelativeLayout.LayoutParams paramsV = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.WRAP_CONTENT,
			RelativeLayout.LayoutParams.WRAP_CONTENT);
	private RelativeLayout.LayoutParams paramsG = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.WRAP_CONTENT,
			RelativeLayout.LayoutParams.WRAP_CONTENT);

	public ListenerUtils(Context context, MapView vMap,
			GraphicsLayer graphicsLayer, MapUtils mapUtils,
			SearchHelper searchUtils, LinearLayout vSearchResultContainer,
			TextView vshowDetailIbt, ImageButton vPlayImageBegin,
			LinearLayout vZoomContainer) {
		this.context = context;
		this.vMap = vMap;
		this.mapUtils = mapUtils;
		this.vSearchResultContainer = vSearchResultContainer;
		this.graphicsLayer = graphicsLayer;
		this.searchUtils = searchUtils;
		this.vshowDetailIbt = vshowDetailIbt;
		this.vPlayImageBegin = vPlayImageBegin;
		this.vZoomContainer = vZoomContainer;
		paramsV.addRule(RelativeLayout.ABOVE, R.id.search_result_container);
		paramsV.setMargins(Utils.dip2px(context, 10), 0, 0,
				Utils.dip2px(context, 20));

		paramsG.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		paramsG.setMargins(Utils.dip2px(context, 10), 0, 0,
				Utils.dip2px(context, 35));
	}

	/**
	 * 地图缩放监听事件处理
	 * 
	 * @param searchResult
	 *            搜索结果
	 * @param roadIndicatorsResult
	 *            公路指标信息接口结果类
	 * @param fr
	 *            搜索结果所在省份特征结果
	 * @param vSearchBubble
	 *            探针图
	 * @param vSearchThumbImg
	 *            小图
	 * @param vRoadPCIDetail
	 *            公路信息详情弹窗view
	 * @param vRoadSkillKpi
	 *            公路指标信息按钮
	 */
	public void manageZoomListener(SearchResult searchResult,
			RoadPCIResult roadIndicatorsResult,
			PressRoadInfoResult pressRoadInfoResult, FeatureResult fr,
			ImageView vSearchBubble, ImageView vSearchThumbImg,
			LinearLayout vRoadPCIDetail, ImageButton vRoadSkillKpi) {

		// if (CustomApp.app.belong_province_code != null) {
		// mapUtils.heightLightProvince(fr);
		// }

		// // 搜索结果为空时，放大地图不显示探针
		// if (searchResult == null && roadIndicatorsResult == null) {
		// vSearchBubble.setVisibility(View.GONE);
		// }

		// Log.i("=============", "===mapScale==" + vMap.getScale());
		//
		// // 放大到最大级别后，公路加粗显示
		// if (vMap.getScale() <= 510000) {
		//
		// // 搜索结果为单条公路时
		// if (searchResult != null && searchResult.getStatus().equals("Y")
		// && roadIndicatorsResult == null) {
		//
		// // 重画公路
		// mapUtils.drawRoads(searchResult, 0, vPlayImageBegin);
		//
		// // 高亮显示身份轮廓
		// mapUtils.heightLightProvince(fr);
		//
		// // 显示探针
		// // vSearchBubble.setVisibility(View.VISIBLE);
		//
		// // 弹窗显示单条公路详细信息
		// vSearchResultContainer.removeAllViews();
		// vSearchResultContainer.addView(vRoadPCIDetail);
		//
		// if (vshowDetailIbt.getVisibility() == View.VISIBLE) {
		// vSearchResultContainer.setVisibility(View.GONE);
		// //vZoomContainer.setLayoutParams(paramsG);
		// }
		//
		// else {
		//
		// vSearchResultContainer.setVisibility(View.VISIBLE);
		// //vZoomContainer.setLayoutParams(paramsV);
		// }
		// }
		// // 点击技术指标后的方法处理
		// if (roadIndicatorsResult != null
		// && roadIndicatorsResult.getStatus().equals("Y")) {
		//
		// // 显示探针
		//
		// if (roadIndicatorsResult != null
		// && roadIndicatorsResult.getStatus().equals("Y")
		// && roadIndicatorsResult.getResults()
		// .getRoad_indicator_list() != null
		// && roadIndicatorsResult.getResults()
		// .getRoad_indicator_list().get(0)
		// .getRoad_indicator_distribute() != null) {
		//
		// // vSearchBubble.setVisibility(View.VISIBLE);
		// } else {
		//
		// vSearchBubble.setVisibility(View.GONE);
		// }
		//
		// // 公路指标重画需要的数据集合
		// ArrayList<RoadsInfo> road_indicator_list = roadIndicatorsResult
		// .getResults().getRoad_indicator_list();
		//
		// if (road_indicator_list != null
		// && road_indicator_list.size() != 0) {
		//
		// for (int i = 0; i < road_indicator_list.size(); i++) {
		//
		// ArrayList<RoadPCIInfo> roads_kpi = road_indicator_list
		// .get(i).getRoad_indicator_distribute();
		//
		// if (roads_kpi != null && roads_kpi.size() != 0) {
		//
		// // 画公路kpi
		// // mapUtils.drawRoadPCI(roads_kpi, 3);
		// mapUtils.drawRoadPCI(roads_kpi, 0);
		// }
		// }
		// }
		// }
		//
		// if (roadIndicatorsResult != null
		// && roadIndicatorsResult.getStatus().equals("Y")
		// && roadIndicatorsResult.getResults()
		// .getRoad_indicator_list() != null
		// && roadIndicatorsResult.getResults()
		// .getRoad_indicator_list().get(0)
		// .getRoad_indicator_distribute().size() == 0) {
		//
		// vSearchBubble.setVisibility(View.GONE);
		// }
		//
		// if (searchResult == null && roadIndicatorsResult == null
		// && pressRoadInfoResult == null) {
		// vSearchResultContainer.setVisibility(View.GONE);
		// vZoomContainer.setLayoutParams(paramsG);
		// vPlayImageBegin.setVisibility(View.GONE);
		// vRoadSkillKpi.setVisibility(View.GONE);
		// vSearchBubble.setVisibility(View.GONE);
		// }
		//
		// // 高亮显示身份轮廓
		// mapUtils.heightLightProvince(fr);
		// // 地图缩小重画PCI
		// }
		// else if (vMap.getScale() > 510000 && vMap.getScale() <= 2000000) {
		//
		// // 让探针与小图消失
		// vSearchBubble.setVisibility(View.GONE);
		// vSearchThumbImg.setVisibility(View.GONE);
		//
		// if (searchResult != null && searchResult.getStatus().equals("Y")
		// && roadIndicatorsResult == null) {
		//
		// // 显示单条公路的详细信息
		// vSearchResultContainer.removeAllViews();
		// vSearchResultContainer.addView(vRoadPCIDetail);
		//
		// if (vshowDetailIbt.getVisibility() == View.VISIBLE) {
		// vSearchResultContainer.setVisibility(View.GONE);
		// vZoomContainer.setLayoutParams(paramsG);
		// } else {
		// vSearchResultContainer.setVisibility(View.VISIBLE);
		// vZoomContainer.setLayoutParams(paramsV);
		// }
		//
		// vRoadSkillKpi.setVisibility(View.VISIBLE);
		//
		// // 移除公路图层
		// graphicsLayer.removeAll();
		// // 重画公路
		// mapUtils.drawRoads(searchResult, 1, vPlayImageBegin);
		//
		// }
		// if (roadIndicatorsResult != null
		// && roadIndicatorsResult.getStatus().equals("Y")) {
		//
		// // 显示单条公路的详细信息
		// vSearchResultContainer.removeAllViews();
		// vSearchResultContainer.addView(vRoadPCIDetail);
		//
		// if (vshowDetailIbt.getVisibility() == View.VISIBLE) {
		// vSearchResultContainer.setVisibility(View.GONE);
		// vZoomContainer.setLayoutParams(paramsG);
		// } else {
		// vSearchResultContainer.setVisibility(View.VISIBLE);
		// vZoomContainer.setLayoutParams(paramsV);
		// }
		//
		// vRoadSkillKpi.setVisibility(View.VISIBLE);
		//
		// // 移除公路图层
		// graphicsLayer.removeAll();
		//
		// // 公路指标重画需要的数据集合
		// ArrayList<RoadsInfo> road_indicator_list = roadIndicatorsResult
		// .getResults().getRoad_indicator_list();
		//
		// if (road_indicator_list != null
		// && road_indicator_list.size() != 0) {
		//
		// for (int i = 0; i < road_indicator_list.size(); i++) {
		//
		// ArrayList<RoadPCIInfo> roads_kpi = road_indicator_list
		// .get(i).getRoad_indicator_distribute();
		//
		// if (roads_kpi != null && roads_kpi.size() != 0) {
		//
		// // 画公路kpi
		//
		// mapUtils.drawRoadPCI(roads_kpi, 1);
		// }
		// }
		// }
		// }
		//
		// if (searchResult == null && roadIndicatorsResult == null
		// && pressRoadInfoResult == null) {
		// vSearchResultContainer.setVisibility(View.GONE);
		// vZoomContainer.setLayoutParams(paramsG);
		// vPlayImageBegin.setVisibility(View.GONE);
		// vRoadSkillKpi.setVisibility(View.GONE);
		// }
		// // 高亮显示身份轮廓
		// mapUtils.heightLightProvince(fr);
		//
		// } else if (vMap.getScale() > 2000000 && vMap.getScale() < 3000000) {
		//
		// // 探针与小图消失
		// vSearchBubble.setVisibility(View.GONE);
		// vSearchThumbImg.setVisibility(View.GONE);
		// // 弹窗公路详细信息
		// // vSearchResultContainer.setVisibility(View.GONE);
		// // vRoadSkillKpi.setVisibility(View.GONE);
		// // vshowDetailIbt.setVisibility(View.GONE);
		//
		// if (searchResult != null && searchResult.getStatus().equals("Y")
		// && roadIndicatorsResult == null) {
		//
		// // 移除公路线路图像
		// graphicsLayer.removeAll();
		//
		// // 弹窗公路详细信息
		// if (vshowDetailIbt.getVisibility() == View.VISIBLE) {
		// vSearchResultContainer.setVisibility(View.GONE);
		// vZoomContainer.setLayoutParams(paramsG);
		// }
		//
		// else {
		//
		// vSearchResultContainer.setVisibility(View.VISIBLE);
		// vZoomContainer.setLayoutParams(paramsV);
		// }
		// vRoadSkillKpi.setVisibility(View.VISIBLE);
		//
		// // 重画公路（缩小公路粗细）
		// mapUtils.drawRoads(searchResult, 3, vPlayImageBegin);
		//
		// // 高亮显示身份轮廓
		// // mapUtils.heightLightProvince(fs);
		// }
		// if (roadIndicatorsResult != null
		// && roadIndicatorsResult.getStatus().equals("Y")) {
		//
		// // 移除公路线路图像
		// graphicsLayer.removeAll();
		//
		// // 公路指标重画需要的数据集合
		// ArrayList<RoadsInfo> road_indicator_list = roadIndicatorsResult
		// .getResults().getRoad_indicator_list();
		//
		// if (road_indicator_list != null
		// && road_indicator_list.size() != 0) {
		//
		// for (int i = 0; i < road_indicator_list.size(); i++) {
		//
		// ArrayList<RoadPCIInfo> roads_kpi = road_indicator_list
		// .get(i).getRoad_indicator_distribute();
		//
		// if (roads_kpi != null && roads_kpi.size() != 0) {
		//
		// // 画公路kpi
		// // mapUtils.drawRoadPCI(roads_kpi, 0);
		// mapUtils.drawRoadPCI(roads_kpi, 3);
		// }
		// }
		//
		// }
		// }
		//
		// if (searchResult == null && roadIndicatorsResult == null
		// && pressRoadInfoResult == null) {
		// vSearchResultContainer.setVisibility(View.GONE);
		// vZoomContainer.setLayoutParams(paramsG);
		// vPlayImageBegin.setVisibility(View.GONE);
		// vRoadSkillKpi.setVisibility(View.GONE);
		// }
		// mapUtils.heightLightProvince(fr);
		//
		// }
		// else if (vMap.getScale() >= 600000000) {
		//
		// searchResult = null;
		// roadIndicatorsResult = null;
		// pressRoadInfoResult = null;
		//
		// // 探针，小图，指标，公路详细弹窗都消失
		// vSearchBubble.setVisibility(View.GONE);
		// vSearchThumbImg.setVisibility(View.GONE);
		// vRoadSkillKpi.setVisibility(View.GONE);
		// vSearchResultContainer.setVisibility(View.GONE);
		// vZoomContainer.setLayoutParams(paramsG);
		// vshowDetailIbt.setVisibility(View.GONE);
		// vPlayImageBegin.setVisibility(View.GONE);
		// if (fr != null) {
		//
		// // 移除公路线路
		// graphicsLayer.removeAll();
		//
		// // 省份边界高亮显示
		// mapUtils.heightLightProvince(fr);
		// }
		// }
	}

	/**
	 * 地图单击监听事件处理
	 * 
	 * @param searchResult
	 *            搜索结果
	 * @param point
	 *            屏幕点击点
	 * 
	 */
	public void manageSingleTabListener(SearchResult searchResult, Point point) {

		vSearchResultContainer.removeAllViews();

		// 如果搜索结果是桥梁类
		if (searchResult.getResults().get(0).getResult_type()
				.equals("TYPE_BRIDGE")) {

			// 判断点击的点是否在桥梁图标上,0代表桥梁标识，1代表小图
			int index = mapUtils.isPtOnMarker(searchResult, point, 0);

			BridgePositionInfo bridgeInfo = searchResult.getResults().get(0)
					.getBridge_positions_list().get(index);

			if (bridgeInfo != null) {

				searchUtils.showUniqueBridge(bridgeInfo);

				// 桥梁小图显示在标识之上
				Point thumb_point = new Point(Double.valueOf(bridgeInfo
						.getLongitude()), Double.valueOf(bridgeInfo
						.getLatitude()) + 0.02);

				// 判断屏幕点击的点是否点击在小图上
				int index_thumb = mapUtils.isPtOnMarker(searchResult,
						thumb_point, 1);

				bridgeInfo = searchResult.getResults().get(0)
						.getBridge_positions_list().get(index_thumb);

				if (bridgeInfo != null) {

					// 跳转到大图页面
					Intent intent = new Intent(context, PlayImage.class);

					Bundle bundle = new Bundle();
					bundle.putString("province_code", searchResult.getResults()
							.get(0).getProvince_code());
					bundle.putString("road_code", bridgeInfo.getRoad_code());
					bundle.putString("road_station",
							bridgeInfo.getBridge_center_station());
					bundle.putString("result_type", searchResult.getResults()
							.get(0).getResult_type());

					String going_direction;
					if (searchResult.getResults().get(0).getUp_or_down() == null) {
						going_direction = "UP";
					} else {
						going_direction = searchResult.getResults().get(0)
								.getUp_or_down();
					}

					bundle.putString("going_direction", going_direction);

					intent.putExtras(bundle);

					context.startActivity(intent);

				}

			}
		} else if (searchResult.getResults().get(0).getResult_type()
				.equals("TYPE_TUNNEL")) {

			// 判断点击的点是否在桥梁图标上,0代表桥梁标识，1代表小图
			int index = mapUtils.isPtOnMarker(searchResult, point, 0);

			TunnelInfo tunnelInfo = searchResult.getResults().get(0)
					.getTunnel_list().get(index);

			if (tunnelInfo != null) {

				searchUtils.showUniqueTunnel(tunnelInfo);

				// 桥梁小图显示在标识之上
				Point thumb_point = new Point(Double.valueOf(tunnelInfo
						.getLongitude()), Double.valueOf(tunnelInfo
						.getLatitude()) + 0.02);

				// 判断屏幕点击的点是否点击在小图上
				int index_thumb = mapUtils.isPtOnMarker(searchResult,
						thumb_point, 1);

				tunnelInfo = searchResult.getResults().get(0).getTunnel_list()
						.get(index_thumb);

				if (tunnelInfo != null) {

					// 跳转到大图页面
					Intent intent = new Intent(context, PlayImage.class);

					Bundle bundle = new Bundle();
					bundle.putString("province_code", searchResult.getResults()
							.get(0).getProvince_code());
					bundle.putString("road_code", tunnelInfo.getRoad_code());
					bundle.putString("road_station",
							tunnelInfo.getTunnel_center_station());
					bundle.putString("result_type", searchResult.getResults()
							.get(0).getResult_type());

					String going_direction;
					if (searchResult.getResults().get(0).getUp_or_down() == null) {
						going_direction = "UP";
					} else {
						going_direction = searchResult.getResults().get(0)
								.getUp_or_down();
					}

					bundle.putString("going_direction", going_direction);

					intent.putExtras(bundle);

					context.startActivity(intent);

				}

			}
		} else if (searchResult.getResults().get(0).getResult_type()
				.equals("TYPE_FACILITY")) {

			// 判断点击的点是否在桥梁图标上,0代表桥梁标识，1代表小图
			int index = mapUtils.isPtOnMarker(searchResult, point, 0);

			FacilityInfo facilityInfo = searchResult.getResults().get(0)
					.getFacility_list().get(index);

			if (facilityInfo != null) {

				searchUtils.showUniqueFacility(facilityInfo);

				// 桥梁小图显示在标识之上
				Point thumb_point = new Point(Double.valueOf(facilityInfo
						.getLongitude()), Double.valueOf(facilityInfo
						.getLatitude()) + 0.02);

				// 判断屏幕点击的点是否点击在小图上
				int index_thumb = mapUtils.isPtOnMarker(searchResult,
						thumb_point, 1);

				facilityInfo = searchResult.getResults().get(0)
						.getFacility_list().get(index_thumb);

				if (facilityInfo != null) {

					// 跳转到大图页面

					Intent intent = new Intent(context, PlayImage.class);

					Bundle bundle = new Bundle();
					bundle.putString("province_code", searchResult.getResults()
							.get(0).getProvince_code());
					bundle.putString("road_code", facilityInfo.getRoad_code());
					bundle.putString("road_station",
							facilityInfo.getFacility_center_station());
					bundle.putString("result_type", searchResult.getResults()
							.get(0).getResult_type());

					String going_direction;
					if (searchResult.getResults().get(0).getUp_or_down() == null) {
						going_direction = "UP";
					} else {
						going_direction = searchResult.getResults().get(0)
								.getUp_or_down();
					}

					bundle.putString("going_direction", going_direction);

					intent.putExtras(bundle);

					context.startActivity(intent);

				}

			}
		} else if (searchResult.getResults().get(0).getResult_type()
				.equals("TYPE_CONSTRUCTION")) {

			// 判断点击的点是否在桥梁图标上,0代表桥梁标识，1代表小图
			int index = mapUtils.isPtOnMarker(searchResult, point, 0);

			ConstructionInfo constructionInfo = searchResult.getResults()
					.get(0).getConstruction_list().get(index);

			if (constructionInfo != null) {

				searchUtils.showUniqueConstrution(constructionInfo);

				// 桥梁小图显示在标识之上
				Point thumb_point = new Point(Double.valueOf(constructionInfo
						.getLongitude()), Double.valueOf(constructionInfo
						.getLatitude()) + 0.02);

				// 判断屏幕点击的点是否点击在小图上
				int index_thumb = mapUtils.isPtOnMarker(searchResult,
						thumb_point, 1);

				constructionInfo = searchResult.getResults().get(0)
						.getConstruction_list().get(index_thumb);

				if (constructionInfo != null) {

					// 跳转到大图页面
					Intent intent = new Intent(context, PlayImage.class);

					Bundle bundle = new Bundle();
					bundle.putString("province_code", searchResult.getResults()
							.get(0).getProvince_code());
					bundle.putString("road_code",
							constructionInfo.getRoad_code());
					bundle.putString("road_station",
							constructionInfo.getConstruction_center_station());
					bundle.putString("result_type", searchResult.getResults()
							.get(0).getResult_type());

					String going_direction;
					if (searchResult.getResults().get(0).getUp_or_down() == null) {
						going_direction = "UP";
					} else {
						going_direction = searchResult.getResults().get(0)
								.getUp_or_down();
					}

					bundle.putString("going_direction", going_direction);

					intent.putExtras(bundle);

					context.startActivity(intent);

				}

			}
		}

	}
}
