package com.mobo.zggkgisandroid.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.esri.android.map.GraphicsLayer;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.IndicatorInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.LonLatsInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadPCIInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadsInfo;
import com.mobo.zggkgisandroid.WebModel.RoadPCIResult;
import com.mobo.zggkgisandroid.WebModel.SearchResult;

public class RoadsHelper {

	private LayoutInflater inflater;
	private LinearLayout vSearchResultContainer;
	private TextView vshowDetailIbt;
	private GraphicsLayer graphicsLayer;
	private GraphicsLayer transparentLayer;

	public RoadsHelper(Context context, LinearLayout vSearchResultContainer,
			TextView vshowDetailIbt, GraphicsLayer graphicsLayer,
			GraphicsLayer transparentLayer) {

		inflater = LayoutInflater.from(context);
		this.vSearchResultContainer = vSearchResultContainer;
		this.graphicsLayer = graphicsLayer;
		this.vshowDetailIbt = vshowDetailIbt;
		this.transparentLayer = transparentLayer;

		transparentLayer.setVisible(false);

	}

	/**
	 * 多条公路的pci处理和底部弹窗显示
	 */
	public void multiRoadsIndicatorManage(SearchResult searchResult,
			RoadPCIResult roadIndicatorsResult, String province_code,
			String province_name, String query_indicator_id) {

		// 弹窗要显示信息
		ArrayList<IndicatorInfo> indicator_info = roadIndicatorsResult
				.getResults().getIndicator_info();

		String query_indicator_value = null;
		String query_indicator_num = null;

		// 公路指标重画需要的数据集合
		ArrayList<RoadsInfo> road_indicator_list = roadIndicatorsResult
				.getResults().getRoad_indicator_list();

		if (road_indicator_list != null && road_indicator_list.size() != 0) {

			if (searchResult != null) {

				for (int i = 0; i < indicator_info.size(); i++) {

					if ((indicator_info.get(i).getIndicator_id().toLowerCase())
							.equals(query_indicator_id)) {

						query_indicator_value = indicator_info.get(i)
								.getIndicator_value();
						query_indicator_num = indicator_info.get(i)
								.getIndicator_num();

					}
				}

				/**************************** 多条公路底部弹窗处理 ***********************************/
				if (road_indicator_list.size() != 1) {

					LinearLayout vRoadsIndicatorinfo = (LinearLayout) inflater
							.inflate(R.layout.search_result_road_kpi, null);

					// 添加新的要显示的view
					TextView vKpiRoadsNum = (TextView) vRoadsIndicatorinfo
							.findViewById(R.id.search_result_road_kpi_total_number);
					TextView vKpiRoadsKM = (TextView) vRoadsIndicatorinfo
							.findViewById(R.id.search_result_road_kpi_total_km);
					TextView vKpiRoadsName = (TextView) vRoadsIndicatorinfo
							.findViewById(R.id.search_result_road_kpi_name);
					TextView vKpiRoadsValue = (TextView) vRoadsIndicatorinfo
							.findViewById(R.id.search_result_road_kpi_value);
					TextView vKpiRoadsColor = (TextView) vRoadsIndicatorinfo
							.findViewById(R.id.search_result_road_kpi_color);

					// 清空控件内容
					vKpiRoadsNum.setText("");
					vKpiRoadsKM.setText("");
					vKpiRoadsName.setText("");
					vKpiRoadsValue.setText("");

					// 设置控件显示内容
					vKpiRoadsNum.setText(searchResult.getResults().get(0)
							.getResult_size()
							+ " 条");

					if (province_code.equals("123001")) {

						vKpiRoadsKM.setText(searchResult.getResults().get(0)
								.getResult_mil_num()
								+ " 公里");
					} else {
						vKpiRoadsKM.setText(searchResult.getResults().get(0)
								.getResult_mil_num()
								+ " 公里" + "," + province_name);
					}

					vKpiRoadsName.setText(query_indicator_value.toUpperCase()
							+ query_indicator_id.toUpperCase() + ": ");
					vKpiRoadsValue.setText(query_indicator_num);

					// 移除弹窗公路信息
					vSearchResultContainer.removeAllViews();
					// vKpiRoadsColor.setBackgroundColor(PCI_color);
					vSearchResultContainer.addView(vRoadsIndicatorinfo);

					vSearchResultContainer.setVisibility(View.VISIBLE);
					vshowDetailIbt.setVisibility(View.GONE);

				}

			}

			/******************** 画公路指标颜色线的处理 *********************/

			Log.i("=======hehe========", "=========road_size==="
					+ road_indicator_list.size());
			ArrayList<RoadPCIInfo> road_indicator_distribut = new ArrayList<RoadPCIInfo>();

			for (int i = 0; i < road_indicator_list.size(); i++) {

				road_indicator_distribut.clear();

				road_indicator_distribut.addAll(road_indicator_list.get(i)
						.getRoad_indicator_distribute());

				int road_index = i;

				if (road_indicator_distribut != null
						&& road_indicator_distribut.size() != 0) {

					drawRoadPCI(road_indicator_distribut, 1);
					drawRoadPCITest(road_indicator_distribut, road_index);

				}

			}

		}
	}

	/**
	 * 公路PCI显示方法
	 * 
	 * @param result
	 *            存储经纬度的集合
	 * 
	 * @param type
	 *            缩放手势重画标志
	 * 
	 */
	public Graphic drawRoadPCI(ArrayList<RoadPCIInfo> result, int type) {

		boolean flag = true;

		if (result.size() != 0 && flag) {

			Log.i("=======haha========",
					"=========road_station===" + result.size());

			for (int i = 0; i < result.size() && flag; i++) {

				// 指标等级
				int indicator_pci_grade = -1;

				IndicatorInfo indicator_info = result.get(i)
						.getIndicator_info();

				ArrayList<LonLatsInfo> roadLonsLatsInfos = result.get(i)
						.getRoad_lon_lat_list();

				int PCI_color = 0;

				if (indicator_info != null && !"".equals(indicator_info)) {

					if (!"".equals(indicator_info.getIndicator_grade())) {

						// 指标等级
						indicator_pci_grade = Integer.valueOf(indicator_info
								.getIndicator_grade());

						switch (indicator_pci_grade) {
						case 1:
							PCI_color = Color.rgb(120, 224, 57);
							break;
						case 2:
							PCI_color = Color.rgb(97, 251, 231);
							break;
						case 3:
							PCI_color = Color.rgb(224, 238, 115);
							break;
						case 4:
							PCI_color = Color.rgb(255, 170, 82);
							break;
						case 5:
							PCI_color = Color.rgb(250, 84, 2);
							break;
						default:
							PCI_color = Color.argb(127, 160, 160, 160);
							break;
						}
					}

				}

				if (indicator_info != null && roadLonsLatsInfos != null
						&& roadLonsLatsInfos.size() != 0) {

					Polyline polyline = new Polyline();
					Point startPoint;
					Point endPoint;

					int type_number = 0;

					for (int j = 0; j < roadLonsLatsInfos.size(); j++) {

						String s_longitude = roadLonsLatsInfos.get(j)
								.getLongitude();
						String s_latitude = roadLonsLatsInfos.get(j)
								.getLatitude();
						String s_longtitude_next = null;
						String s_latitude_next = null;

						if (roadLonsLatsInfos.get(j).getEnd_longitude() == null
								&& j < roadLonsLatsInfos.size() - 1) {

							s_longtitude_next = roadLonsLatsInfos.get(j + 1)
									.getLongitude();
							s_latitude_next = roadLonsLatsInfos.get(j + 1)
									.getLatitude();
						} else if (roadLonsLatsInfos.get(j).getEnd_longitude() != null) {

							s_longtitude_next = roadLonsLatsInfos.get(j)
									.getEnd_longitude();
							s_latitude_next = roadLonsLatsInfos.get(j)
									.getEnd_latitude();
						} else {

						}
						double longtitude = Double.valueOf(s_longitude);// 经度
						double latitude = Double.valueOf(s_latitude);// 纬度

						double longtitude_next = Double
								.valueOf(s_longtitude_next);// 下一个点经度
						double latitude_next = Double.valueOf(s_latitude_next);// 下一个点经度

						startPoint = GeometryEngine.project(longtitude,
								latitude, SpatialReference.create(4326));

						endPoint = GeometryEngine.project(longtitude_next,
								latitude_next, SpatialReference.create(4326));

						Line line = new Line();
						line.setStart(startPoint);
						line.setEnd(endPoint);

						polyline.addSegment(line, false);

						// 公路PCI路线粗细选择

						switch (type) {
						case 0:
							type_number = 6;
							break;
						case 1:// 地图缩放等级（road_max:500000）
							type_number = 5;
							break;
						case 2:// 地图缩放等级（500000，2000000）
							type_number = 3;
							break;
						case 3:// 地图缩放等级（2000000，3000000）
							type_number = 2;
							break;
						default:
							break;
						}

					}

					Graphic road_indicator_graphic = new Graphic(polyline,
							new SimpleLineSymbol(PCI_color, type_number));

					int roadPciUid = graphicsLayer
							.addGraphic(road_indicator_graphic);

					graphicsLayer.updateGraphic(roadPciUid,
							road_indicator_graphic);

				}

			}

		}

		return null;
	}

	/**
	 * 公路PCI显示方法
	 * 
	 * @param result
	 *            存储经纬度的集合
	 * 
	 * @param road_index
	 *            公路序号
	 * 
	 */
	public Graphic drawRoadPCITest(ArrayList<RoadPCIInfo> result, int road_index) {

		if (result.size() != 0) {

			SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol(
					R.color.red, 3);

			for (int i = 0; i < result.size(); i++) {

				ArrayList<LonLatsInfo> roadLonsLatsInfos = result.get(i)
						.getRoad_lon_lat_list();

				for (int j = 0; j < roadLonsLatsInfos.size(); j++) {

					Polyline polyline = new Polyline();
					Point startPoint;
					Point endPoint;

					String s_longitude = roadLonsLatsInfos.get(j)
							.getLongitude();
					String s_latitude = roadLonsLatsInfos.get(j).getLatitude();
					String s_longtitude_next = null;
					String s_latitude_next = null;

					if (roadLonsLatsInfos.get(j).getEnd_longitude() == null
							&& j < roadLonsLatsInfos.size() - 1) {

						s_longtitude_next = roadLonsLatsInfos.get(j + 1)
								.getLongitude();
						s_latitude_next = roadLonsLatsInfos.get(j + 1)
								.getLatitude();
					} else if (roadLonsLatsInfos.get(j).getEnd_longitude() != null) {

						s_longtitude_next = roadLonsLatsInfos.get(j)
								.getEnd_longitude();
						s_latitude_next = roadLonsLatsInfos.get(j)
								.getEnd_latitude();
					} else {

					}
					double longtitude = Double.valueOf(s_longitude);// 经度
					double latitude = Double.valueOf(s_latitude);// 纬度

					double longtitude_next = Double.valueOf(s_longtitude_next);// 下一个点经度
					double latitude_next = Double.valueOf(s_latitude_next);// 下一个点经度

					startPoint = GeometryEngine.project(longtitude, latitude,
							SpatialReference.create(4326));

					endPoint = GeometryEngine.project(longtitude_next,
							latitude_next, SpatialReference.create(4326));

					Line line = new Line();
					line.setStart(startPoint);
					line.setEnd(endPoint);

					polyline.addSegment(line, false);

					Graphic road_indicator_graphic = new Graphic(polyline,
							simpleLineSymbol);

					int roadNormalUid = transparentLayer
							.addGraphic(road_indicator_graphic);

					Map<String, Object> roadsAttr = new HashMap<String, Object>();

					roadsAttr.put("road_index", road_index);
//					roadsAttr.put("road_station", roadLonsLatsInfos.get(j)
//							.getRoad_station());
//					roadsAttr.put("current_position", roadLonsLatsInfos.get(j)
//							.getCurrent_position());
//					roadsAttr.put("current_unit", roadLonsLatsInfos.get(j)
//							.getCurrent_feed_unit());
//					roadsAttr.put("current_tec_level", roadLonsLatsInfos.get(j)
//							.getBridge_technical_grade());
//					roadsAttr.put("current_road_width", roadLonsLatsInfos
//							.get(j).getRoad_way_width());
//					roadsAttr.put("current_road_facetype", roadLonsLatsInfos
//							.get(j).getRoad_face_type());
//					roadsAttr.put("current_lane_num", roadLonsLatsInfos.get(j)
//							.getRoad_lane_num());
//					roadsAttr.put("current_indicator_num", roadLonsLatsInfos
//							.get(j).getCurrent_indicator_value());
//					roadsAttr.put("current_daily_traffic", roadLonsLatsInfos
//							.get(j).getDaily_traffic_volume());
//					roadsAttr.put("current_build_years",
//							roadLonsLatsInfos.get(j).getRoad_build_years());
//					roadsAttr.put("current_conservation_years",
//							roadLonsLatsInfos.get(j)
//									.getRoad_conservation_years());
//					roadsAttr.put("current_road_condition", roadLonsLatsInfos
//							.get(j).getRoad_surface_condition());
//					roadsAttr.put("current_conservation_advise",
//							roadLonsLatsInfos.get(j)
//									.getRoad_conservation_advise());
//					roadsAttr.put("longitude", roadLonsLatsInfos.get(j)
//							.getLongitude());
//					roadsAttr.put("latitude", roadLonsLatsInfos.get(j)
//							.getLatitude());

					transparentLayer.updateGraphic(roadNormalUid,
							road_indicator_graphic);
					transparentLayer.updateGraphic(roadNormalUid, roadsAttr);
				}
			}

		}

		return null;
	}
}
