package com.mobo.zggkgisandroid.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.renderer.SimpleRenderer;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.Symbol;
import com.esri.core.symbol.advanced.SymbolDictionary;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgeNationalInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgePositionInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.ConstructionInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.FacilityInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.IndicatorInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.LonLatsInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.RoadPCIInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.TunnelInfo;
import com.mobo.zggkgisandroid.WebModel.BridgeMarksResult;
import com.mobo.zggkgisandroid.WebModel.SearchResult;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult.PlayImageMess.image_list.bridge;

/**
 * 地图画公路，省份边界工具类
 * 
 * @author wjm
 */

public class MapUtils {

	private Context context;
	private GraphicsLayer graphicsLayer;// 图层类
	private GraphicsLayer transparentLayer;
	private MapView mapView;// 地图类
	private int roadNormalUid;
	private int roadPciUid;
	private Graphic provinceGraphic;
	private GraphicsLayer provinceLayer;

	public MapUtils(Context context, GraphicsLayer graphicsLayer,
			GraphicsLayer transparentLayer, GraphicsLayer provinceLayer,
			MapView mapView) {

		this.context = context;
		this.graphicsLayer = graphicsLayer;
		this.transparentLayer = transparentLayer;
		this.mapView = mapView;
		this.provinceLayer = provinceLayer;

	}

	/**
	 * 画高亮公路方法
	 * 
	 * @param road_info
	 *            存储经纬度的集合
	 * 
	 */
	public Graphic drawNormalRoads(ArrayList<LonLatsInfo> lonlat_road_list,
			int type, int single) {

		Polyline polyline = new Polyline();
		Point startPoint = null;
		Point endPoint = null;

		if (lonlat_road_list.size() != 0) {

			for (int j = 0; j < lonlat_road_list.size() - 1; j++) {

				double longtitude = Double.valueOf(lonlat_road_list.get(j)
						.getLongitude());// 经度
				double latitude = Double.valueOf(lonlat_road_list.get(j)
						.getLatitude());// 纬度

				double longtitude_next = Double.valueOf(lonlat_road_list.get(
						j + 1).getLongitude());// 下一个点经度
				double latitude_next = Double.valueOf(lonlat_road_list.get(
						j + 1).getLatitude());// 下一个点经度

				startPoint = GeometryEngine.project(longtitude, latitude,
						SpatialReference.create(4326));

				endPoint = GeometryEngine.project(longtitude_next,
						latitude_next, SpatialReference.create(4326));

				Line line = new Line();

				line.setStart(startPoint);
				line.setEnd(endPoint);
				polyline.addSegment(line, false);

			}

			int type_number = 1;
			switch (type) {
			case 0:
				type_number = 1;
				break;
			case 1:
				type_number = 2;
				break;
			case 2:
				type_number = 3;
				break;
			case 3:
				type_number = 6;
				break;
			default:
				break;
			}

			Graphic g = new Graphic(polyline, new SimpleLineSymbol(context
					.getResources().getColor(R.color.road_heilight),
					type_number));

			if (single != 1) {

				roadNormalUid = graphicsLayer.addGraphic(g);

				graphicsLayer.updateGraphic(roadNormalUid, g);
			}

			return g;

		}

		return null;

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
	public Graphic[] drawRoadPCI(ArrayList<RoadPCIInfo> result, int type) {

		boolean flag = true;

		Graphic[] roadIndicatorGraphics = new Graphic[result.size()];
		if (result.size() != 0 && flag) {

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
							type_number = 4;
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

					roadIndicatorGraphics[i] = road_indicator_graphic;

					roadPciUid = graphicsLayer
							.addGraphic(road_indicator_graphic);

					Log.i("================", "===roadPicuid==" + roadPciUid);

					graphicsLayer.updateGraphic(roadPciUid,
							road_indicator_graphic);

				}

			}

		}

		return roadIndicatorGraphics;
	}

	/**
	 * 查询政区
	 * 
	 * @param province_code
	 *            省份编码
	 * @param point
	 *            地图点
	 * @return FeatureResult 政区图层信息
	 * 
	 */
	public FeatureResult SearchProvince(String province_code, Point point) {

		Query query = new Query();

		QueryParameters queryParameters = new QueryParameters();

		if (Utils.isNull(province_code) && point != null) {
			query.setGeometry(point);

			queryParameters.setGeometry(point);

		} else if (!Utils.isNull(province_code)) {

			query.setWhere("代码 like '%" + province_code + "%'");

			queryParameters.setWhere("代码 like '%" + province_code + "%'");
		} else {
			return null;
		}

		queryParameters.setReturnGeometry(true);

		queryParameters.setOutFields(new String[] { "FID", "Shape", "名称", "代码",
				"简称", "JC" });
		// QueryTask dQueryTask = new QueryTask(
		// "http://106.37.229.146:89/ArcGIS/rest/services/GJ2014/MapServer/68");
		// 123.124.221.1
		QueryTask dQueryTask = new QueryTask(
				"http://106.37.229.146:89/ArcGIS/rest/services/GJ2014/MapServer/68");

		FeatureResult fr = null;
		try {

			fr = dQueryTask.execute(queryParameters);

			Iterator<Object> objectIterator = fr.iterator();

			while (objectIterator.hasNext()) {

				Feature feature = (Feature) objectIterator.next();
				Polygon polygon = (Polygon) feature.getGeometry();

				feature.getSymbol();

				SimpleLineSymbol symbol = new SimpleLineSymbol(Color.argb(128,
						0, 0, 255), 2, SimpleLineSymbol.STYLE.DASHDOT);

			}

			return fr;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 查询市
	 * 
	 * @param province_code
	 *            省份编码
	 * @param point
	 *            地图点
	 * @return FeatureResult 政区图层信息
	 * 
	 */
	public FeatureResult SearchCity(String city_name, Point point) {

		Query query = new Query();

		QueryParameters queryParameters = new QueryParameters();

		if (Utils.isNull(city_name) && point != null) {
			query.setGeometry(point);

			queryParameters.setGeometry(point);

		} else if (!Utils.isNull(city_name)) {

			query.setWhere("NAME like '%" + city_name + "%'");

			queryParameters.setWhere("NAME like '%" + city_name + "%'");
		} else {
			return null;
		}

		queryParameters.setReturnGeometry(true);

		queryParameters.setOutFields(new String[] { "FID", "Shape", "NAME",
				"KIND" });
		// QueryTask dQueryTask = new QueryTask(
		// "http://106.37.229.146:89/ArcGIS/rest/services/GJ2014/MapServer/68");
		// 123.124.221.1
		QueryTask dQueryTask = new QueryTask(
				"http://106.37.229.146:89/ArcGIS/rest/services/GJ2014/MapServer/70");

		FeatureResult fr = null;
		try {

			fr = dQueryTask.execute(queryParameters);

			Iterator<Object> objectIterator = fr.iterator();

			while (objectIterator.hasNext()) {

				Feature feature = (Feature) objectIterator.next();
				Polygon polygon = (Polygon) feature.getGeometry();

				feature.getSymbol();

				SimpleLineSymbol symbol = new SimpleLineSymbol(Color.argb(128,
						0, 0, 255), 2, SimpleLineSymbol.STYLE.DASHDOT);

			}

			return fr;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 高亮显示政区
	 * 
	 * @param fr
	 *            政区信息集
	 * 
	 * @return short_name 省份简称
	 * 
	 */
	public String heightLightProvince(FeatureResult fr) {

		if (fr == null) {

		} else {

			try {

				Iterator<Object> objectIterator = fr.iterator();

				while (objectIterator.hasNext()) {

					Feature feature = (Feature) objectIterator.next();
					Polygon polygon = (Polygon) feature.getGeometry();

					SimpleLineSymbol symbol = new SimpleLineSymbol(Color.argb(
							128, 0, 0, 255), 2, SimpleLineSymbol.STYLE.SOLID);
					provinceLayer.setRenderer(new SimpleRenderer(symbol));

					provinceGraphic = new Graphic(polygon, symbol);

					provinceLayer.addGraphic(provinceGraphic);

					String short_name = (String) feature
							.getAttributeValue("简称");

					return short_name;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	String getValue(Graphic graphic, String key, String defaultVal) {
		Object obj = graphic.getAttributeValue(key);
		if (obj == null)
			return defaultVal;
		else
			return obj.toString();
	}

	/**
	 * 放大到指定政区
	 * 
	 * @param fr
	 *            省份特征结果
	 * 
	 */
	public void ZoomToProvince(FeatureResult fr) {

		if (fr != null && fr.featureCount() != 0) {

			Point point = new Point();

			Envelope envelope = null;
			double Xmin, Xmax, Ymin, Ymax;

			if (mapView.isLoaded()) {

				Xmin = mapView.getMaxExtent().getXMin();
				Xmax = mapView.getMaxExtent().getXMax();
				Ymin = mapView.getMaxExtent().getYMin();
				Ymax = mapView.getMaxExtent().getYMax();

				Polygon polygon = null;
				Iterator<Object> objeIterator = fr.iterator();
				while (objeIterator.hasNext()) {
					Feature feature = (Feature) objeIterator.next();

					polygon = (Polygon) feature.getGeometry();
				}

				if (polygon != null) {

					for (int i = 0; i < polygon.getPointCount(); i++) {

						point = polygon.getPoint(i);

						// 获取要显示的区域的范围的指标
						if (Xmin < point.getX())
							Xmin = point.getX();
						if (Ymin < point.getY())
							Ymin = point.getY();
						if (Xmax > point.getX())
							Xmax = point.getX();
						if (Ymax > point.getY())
							Ymax = point.getY();

					}

					// 要显示的区域
					envelope = new Envelope(Xmin, Ymin, Xmax, Ymax);

					mapView.setExtent(envelope); // 显示
				}
			}

		}
	}

	/**
	 * 放大到指定公路
	 * 
	 * @param result
	 *            公路PCI信息集合
	 * 
	 */
	public void ZoomToRoad(ArrayList<LonLatsInfo> result) {

		// 获取公路的中心点，以此粗定位
		if (result != null && result.size() != 0) {

			Envelope envelope = null;
			double Xmin = 1000, Xmax = -1, Ymin = 1000, Ymax = -1;

			for (int i = 0; i < result.size(); i++) {

				double longitude = Double.valueOf(result.get(i).getLongitude());
				double latitude = Double.valueOf(result.get(i).getLatitude());

				if (Xmin > longitude) {
					Xmin = Double.valueOf(result.get(i).getLongitude());
				}
				if (Xmax < longitude) {
					Xmax = Double.valueOf(result.get(i).getLongitude());
				}
				if (Ymin > latitude) {
					Ymin = Double.valueOf(result.get(i).getLatitude());
				}
				if (Ymax < latitude) {
					Ymax = Double.valueOf(result.get(i).getLatitude());
				}
			}

			// 要显示的区域
			envelope = new Envelope(Xmin, Ymin, Xmax, Ymax);
			mapView.setExtent(envelope, 300);
		}
	}

	/**
	 * 放大到指定公路
	 * 
	 * @param result
	 *            公路PCI信息集合
	 * 
	 */
	public void ZoomToPCIRoad(ArrayList<RoadPCIInfo> result) {

		// 获取公路的中心点，以此粗定位
		if (result != null && result.size() != 0) {

			Envelope envelope = null;
			double Xmin = 1000, Xmax = -1, Ymin = 1000, Ymax = -1;
			ArrayList<LonLatsInfo> road_lon_lat_list = new ArrayList<LonLatsInfo>();
			for (int i = 0; i < result.size(); i++) {

				road_lon_lat_list.clear();
				road_lon_lat_list.addAll(result.get(i).getRoad_lon_lat_list());

				if (road_lon_lat_list.size() != 0) {

					for (int j = 0; j < road_lon_lat_list.size(); j++) {

						double longitude = Double.valueOf(road_lon_lat_list
								.get(j).getLongitude());
						double latitude = Double.valueOf(road_lon_lat_list.get(
								j).getLatitude());

						if (Xmin > longitude) {
							Xmin = Double.valueOf(road_lon_lat_list.get(j)
									.getLongitude());
						}
						if (Xmax < longitude) {
							Xmax = Double.valueOf(road_lon_lat_list.get(j)
									.getLongitude());
						}
						if (Ymin > latitude) {
							Ymin = Double.valueOf(road_lon_lat_list.get(j)
									.getLatitude());
						}
						if (Ymax < latitude) {
							Ymax = Double.valueOf(road_lon_lat_list.get(j)
									.getLatitude());
						}
					}
				}
			}

			// 要显示的区域
			envelope = new Envelope(Xmin, Ymin, Xmax, Ymax);
			mapView.setExtent(envelope, 200);
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
	 */
	public void drawRoadsKpi(ArrayList<RoadPCIInfo> result, int type) {

		// 指标等级
		// int indicator_pci_grade = -1;
		// 指标值

		if (result.size() != 0) {

			// Graphic g=null;

			for (int i = 0; i < result.size(); i++) {

				// 指标等级
				int indicator_pci_grade = -1;

				Polyline polyline = new Polyline();
				int type_number = 0;

				// 获取每段的指标信息集合
				IndicatorInfo indicator_info = result.get(i)
						.getIndicator_info();

				ArrayList<LonLatsInfo> roadLonsLatsInfos = result.get(i)
						.getRoad_lon_lat_list();

				int PCI_color = -1;

				if (indicator_info != null) {

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

				if (roadLonsLatsInfos != null && roadLonsLatsInfos.size() != 0) {

					for (int j = 0; j < roadLonsLatsInfos.size() - 1; j++) {

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

						Point startPoint = GeometryEngine.project(longtitude,
								latitude, SpatialReference.create(4326));

						Point endPoint = GeometryEngine.project(
								longtitude_next, latitude_next,
								SpatialReference.create(4326));

						Line line = new Line();
						line.setStart(startPoint);
						line.setEnd(endPoint);

						polyline.addSegment(line, false);

					}
					// 公路PCI路线粗细选择

					switch (type) {
					case 0:
						type_number = 12;
						break;
					case 1:
						type_number = 14;
						break;
					case 2:
						type_number = 4;
						break;
					case 3:
						type_number = 3;
						break;
					default:
						break;
					}

					// graphics[i] = g;
				}
				Graphic g = new Graphic(polyline, new SimpleLineSymbol(
						PCI_color, type_number));

				int uid = graphicsLayer.addGraphic(g);

				graphicsLayer.updateGraphic(uid, g);
			}

		}
	}

	/**
	 * 桥梁显示方法
	 * 
	 * @param searchResult
	 *            搜索结果类
	 * 
	 */
	public void showMarkersLoction(SearchResult searchResult,
			BridgeMarksResult bridgeMarksResult, int showExtent) {

		if (searchResult != null || bridgeMarksResult != null) {

			ArrayList<BridgeNationalInfo> bridge_lists = null;
			ArrayList<TunnelInfo> tunnel_lists = null;
			ArrayList<FacilityInfo> facility_lists = null;
			ArrayList<ConstructionInfo> construction_lists = null;

			if (searchResult != null) {

				bridge_lists = searchResult.getResults().get(0)
						.getBridge_national_list();
				tunnel_lists = searchResult.getResults().get(0)
						.getTunnel_list();

				facility_lists = searchResult.getResults().get(0)
						.getFacility_list();

				construction_lists = searchResult.getResults().get(0)
						.getConstruction_list();
			}

			Point point;
			Graphic[] graphics;
			Drawable image = context.getResources().getDrawable(
					R.drawable.bubble);

			// 搜索结果为桥梁时
			if ((searchResult.getResults().get(0).getResult_type()
					.equals("TYPE_BRIDGE") && bridge_lists.size() != 0)
					|| bridgeMarksResult != null) {

				graphics = new Graphic[bridge_lists.size()];

				for (int i = 0; i < bridge_lists.size(); i++) {

					point = new Point(Double.valueOf(bridge_lists.get(i)
							.getLongitude()), Double.valueOf(bridge_lists
							.get(i).getLatitude()));

					Graphic graphic = new Graphic(point,
							new PictureMarkerSymbol(image));

					graphics[i] = graphic;
				}

				int[] uid = graphicsLayer.addGraphics(graphics);
				graphicsLayer.updateGraphics(uid, graphics);
			}
			// 搜索结果为隧道时
			else if (searchResult.getResults().get(0).getResult_type()
					.equals("TYPE_TUNNEL")
					&& tunnel_lists.size() != 0) {

				graphics = new Graphic[bridge_lists.size()];

				for (int i = 0; i < tunnel_lists.size(); i++) {

					point = new Point(Double.valueOf(tunnel_lists.get(i)
							.getLongitude()), Double.valueOf(tunnel_lists
							.get(i).getLatitude()));

					Graphic graphic = new Graphic(point,
							new PictureMarkerSymbol(image));

					graphics[i] = graphic;
				}

				int[] uid = graphicsLayer.addGraphics(graphics);
				graphicsLayer.updateGraphics(uid, graphics);
			}
			// 搜索结果为设施时
			else if (searchResult.getResults().get(0).getResult_type()
					.equals("TYPE_FACILITY")
					&& facility_lists.size() != 0) {

				graphics = new Graphic[bridge_lists.size()];

				for (int i = 0; i < facility_lists.size(); i++) {

					point = new Point(Double.valueOf(facility_lists.get(i)
							.getLongitude()), Double.valueOf(facility_lists
							.get(i).getLatitude()));

					Graphic graphic = new Graphic(point,
							new PictureMarkerSymbol(image));

					graphics[i] = graphic;
				}

				int[] uid = graphicsLayer.addGraphics(graphics);
				graphicsLayer.updateGraphics(uid, graphics);
			}
			// 搜索结果为施工时
			else if (searchResult.getResults().get(0).getResult_type()
					.equals("TYPE_CONSTRUCTION")
					&& construction_lists.size() != 0) {

				graphics = new Graphic[bridge_lists.size()];

				for (int i = 0; i < construction_lists.size(); i++) {

					point = new Point(Double.valueOf(construction_lists.get(i)
							.getLongitude()), Double.valueOf(construction_lists
							.get(i).getLatitude()));

					Graphic graphic = new Graphic(point,
							new PictureMarkerSymbol(image));

					graphics[i] = graphic;
				}
				int[] uid = graphicsLayer.addGraphics(graphics);
				graphicsLayer.updateGraphics(uid, graphics);
			}

		}
	}

	/**
	 * 判断屏幕点击的点是否在显示的桥梁上
	 * 
	 * @param searchResult
	 *            搜索结果类
	 * @param point
	 *            点击在地图上的点
	 * @param type
	 *            点击类型（0代表点击要素标识，1代表点击小图）
	 * 
	 * 
	 * @return 被点击的要素所在集合的索引位置
	 */
	public int isPtOnMarker(SearchResult searchResult, Point point, int type) {

		if (searchResult != null) {

			ArrayList<BridgePositionInfo> bridge_lists = searchResult
					.getResults().get(0).getBridge_positions_list();

			ArrayList<TunnelInfo> tunnel_lists = searchResult.getResults()
					.get(0).getTunnel_list();

			ArrayList<FacilityInfo> facility_lists = searchResult.getResults()
					.get(0).getFacility_list();

			ArrayList<ConstructionInfo> construction_lists = searchResult
					.getResults().get(0).getConstruction_list();

			String s_longitude;
			String s_latitude;

			String result_type = searchResult.getResults().get(0)
					.getResult_type();

			// 是否点击在某一桥梁或桥梁小图上
			if (result_type.equals("TYPE_BRIDGE") && bridge_lists.size() != 0) {

				for (int i = 0; i < bridge_lists.size(); i++) {

					s_longitude = bridge_lists.get(i).getLongitude();
					s_latitude = bridge_lists.get(i).getLatitude();

					int flag = MapHelper.isPtBelongMarker(s_longitude,
							s_latitude, String.valueOf(point.getX()),
							String.valueOf(point.getY()));

					if (flag == 1) {

						return i;
					}
				}
				// 是否点击在某一隧道或隧道小图上
			} else if (result_type.equals("TYPE_TUNNEL")
					&& tunnel_lists.size() != 0) {
				for (int i = 0; i < tunnel_lists.size(); i++) {

					s_longitude = tunnel_lists.get(i).getLongitude();
					s_latitude = tunnel_lists.get(i).getLatitude();

					int flag = MapHelper.isPtBelongMarker(s_longitude,
							s_latitude, String.valueOf(point.getX()),
							String.valueOf(point.getY()));

					if (flag == 1) {

						return i;
					}
				}
				// 是否点击在某一设施或设施小图上
			} else if (result_type.equals("TYPE_FACILITY")
					&& facility_lists.size() != 0) {
				for (int i = 0; i < facility_lists.size(); i++) {

					s_longitude = facility_lists.get(i).getLongitude();
					s_latitude = facility_lists.get(i).getLatitude();

					int flag = MapHelper.isPtBelongMarker(s_longitude,
							s_latitude, String.valueOf(point.getX()),
							String.valueOf(point.getY()));

					if (flag == 1) {

						return i;
					}
				}
				// 是否点击在某一施工或施工小图上
			} else if (result_type.equals("TYPE_CONSTRUTION")
					&& construction_lists.size() != 0) {
				for (int i = 0; i < construction_lists.size(); i++) {

					s_longitude = construction_lists.get(i).getLongitude();
					s_latitude = construction_lists.get(i).getLatitude();

					int flag = MapHelper.isPtBelongMarker(s_longitude,
							s_latitude, String.valueOf(point.getX()),
							String.valueOf(point.getY()));

					if (flag == 1) {

						return i;
					}
				}
			}
		}
		return -1;
	}

	/**
	 * 小图布局view转换为Bitmap
	 * 
	 * @param imageByte
	 *            返回的小图数据
	 * 
	 * @return Bitmap
	 */
	public Bitmap changeViewToBitmap(String imageByte) {

		// 小图所在的布局view
		View vlayout = LayoutInflater.from(context).inflate(
				R.layout.thumb_img_layout, null);

		ImageView vThumbImageView = (ImageView) vlayout
				.findViewById(R.id.small_img);

		TextView vTextView = (TextView) vlayout
				.findViewById(R.id.small_img_station);
		vTextView.setTextColor(Color.WHITE);
		vTextView.setPadding(1, 1, 1, 2);
		vTextView.setTextSize(12);

		if (imageByte != null) {

			byte[] image_data = Base64.decode(imageByte, 0);

			Bitmap thumb_bm = BitmapFactory.decodeByteArray(image_data, 0,
					image_data.length);

			if (thumb_bm != null) {

				if (!thumb_bm.equals("Loading")) {

					thumb_bm = Bitmap
							.createScaledBitmap(thumb_bm, 60, 40, true);

					vThumbImageView.setImageBitmap(thumb_bm);
					vTextView.setText("图像");
				} else {
					vThumbImageView.setImageResource(R.drawable.default_thumb);
					vTextView.setText("加载中");
				}
			}
		} else {
			vThumbImageView.setImageResource(R.drawable.default_thumb);
			vTextView.setText("暂无数据");
		}

		vlayout.measure(
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		vlayout.layout(0, 0, vlayout.getMeasuredWidth(),
				vlayout.getMeasuredHeight());
		vlayout.buildDrawingCache();
		vlayout.setDrawingCacheEnabled(true);
		Bitmap bm = vlayout.getDrawingCache();

		return bm;
	}

	/**
	 * 判断是否点击在省份的某个桥梁上
	 * 
	 * @param bridgeNationalInfoList
	 *            全国桥梁结果类
	 * @param bridgePositionInfos
	 *            省份桥梁结果类
	 */
	public BridgePositionInfo isPtOnBridgeBubble(
			ArrayList<BridgeNationalInfo> bridgeNationalInfos,
			ArrayList<BridgePositionInfo> bridgePositionInfos,
			String longitude, String latitude) {

		String s_longitude = null;
		String s_latitude = null;

		int flag = -1;

		if (bridgeNationalInfos != null && bridgeNationalInfos.size() != 0) {

			for (int i = 0; i < bridgePositionInfos.size(); i++) {

				s_longitude = bridgePositionInfos.get(i).getLongitude();
				s_latitude = bridgePositionInfos.get(i).getLatitude();

				if ("".equals(s_longitude) || "".equals(s_latitude)) {

				} else {

					flag = MapHelper.isPtBelongMarker(s_longitude, s_latitude,
							longitude, latitude);
				}

				if (flag == 1) {

					return bridgePositionInfos.get(i);
				}
			}

		}

		BridgePositionInfo bridgePositionInfo = null;

		if (bridgePositionInfos != null && bridgePositionInfos.size() != 0) {

			for (int i = 0; i < bridgePositionInfos.size(); i++) {

				s_longitude = bridgePositionInfos.get(i).getLongitude();
				s_latitude = bridgePositionInfos.get(i).getLatitude();

				flag = MapHelper.isPtBelongMarker(s_longitude, s_latitude,
						longitude, latitude);

				if (flag == 1) {

					bridgePositionInfo = bridgePositionInfos.get(i);
					break;
				}
			}
		}

		return bridgePositionInfo;
	}

	/**
	 * 将桩号由小数点形式转换为K整数+小数（三位）（1987.000 to K1987+000）
	 * 
	 * @param station
	 *            要素标志经度
	 * 
	 * @return 桩号的标准形式
	 */
	public String changeStationToStandard(String station) {

		if (!TextUtils.isEmpty(station)) {

			if (station.contains(".")) {

				String standard = (station.replace(".", "+"));

				int index = standard.indexOf("+");

				String normal = null;

				if (standard.length() < index + 4) {

					if (standard.length() > 1) {
						normal = standard.substring(0, standard.length() - 1);

					} else {
						normal = station;
					}

				} else {

					normal = standard.substring(0, index + 4);
				}

				if (!TextUtils.isEmpty(normal)) {
					return normal;
				}

			} else {
				return station + "+000";
			}
		}

		return null;
	}

	/**
	 * 搜索详细底部弹窗动画效果
	 * 
	 * @param vSearchResultContainer
	 *            弹窗内容明细
	 * @param type
	 *            弹出类型（弹入or弹出）
	 * 
	 */
	public void animationContainerUpAndDown(
			final LinearLayout vSearchResultContainer, String type) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation;

		if ("UP".equals(type)) {
			translateAnimation = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
					0f, Animation.RELATIVE_TO_SELF, 1f,
					Animation.RELATIVE_TO_SELF, 0f);
		} else {

			translateAnimation = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
					0f, Animation.RELATIVE_TO_SELF, 0f,
					Animation.RELATIVE_TO_SELF, 1f);
		}

		translateAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				vSearchResultContainer.setEnabled(false);
				vSearchResultContainer.setClickable(false);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				vSearchResultContainer.setEnabled(false);
				vSearchResultContainer.setClickable(false);

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				vSearchResultContainer.setEnabled(true);
				vSearchResultContainer.setClickable(true);

			}
		});

		translateAnimation.setDuration(500);
		translateAnimation.setFillAfter(true);
		animationSet.addAnimation(translateAnimation);
		vSearchResultContainer.setAnimation(animationSet);
	}

	/**
	 * 服务器返回数据为空
	 * 
	 * @param object
	 *            返回数据
	 * 
	 */
	public void resultIsNull() {
		Toast.makeText(context, "数据出错啦！", Toast.LENGTH_SHORT).show();
	}

	/***
	 * 把字符串转化成bitmap对象
	 * 
	 * @param bitmapstring
	 * @return
	 */
	public Bitmap handlerBitmap(String bitmapstring) {
		if (bitmapstring == null) {

			return null;
		}
		byte[] temp = Base64.decode(bitmapstring, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(temp, 0, temp.length);

	}

	/**
	 * 将view转为drawable
	 * 
	 * @param view
	 *            返回的小图数据
	 * 
	 * @return Bitmap
	 */
	public Drawable changeViewToDrawable(View view) {

		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

		view.buildDrawingCache();
		view.setDrawingCacheEnabled(true);

		Bitmap bm = Bitmap.createBitmap(view.getDrawingCache(true));

		Drawable drawable = new BitmapDrawable(null, bm);

		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(false);

		return drawable;
	}

	/**
	 * 查询政区
	 * 
	 * @param province_code
	 *            省份编码
	 * @param point
	 *            地图点
	 * @return FeatureResult 政区图层信息
	 * 
	 */
	public FeatureResult SearchRoad(String province_code, Point point) {

		Query query = new Query();

		QueryParameters queryParameters = new QueryParameters();

		if (Utils.isNull(province_code) && point != null) {
			query.setGeometry(point);

			queryParameters.setGeometry(point);

		} else if (!Utils.isNull(province_code)) {

			query.setWhere("LXMC like '%" + province_code + "%'");

			queryParameters.setWhere("LXMC like '%" + province_code + "%'");
		} else {
			return null;
		}

		queryParameters.setReturnGeometry(true);

		queryParameters.setOutFields(new String[] { "FID", "LXBM", "LXMC",
				"LDBM", "QDZH", "ZDZH", "TJLC", "LDLX", "DLLX" });
		// QueryTask dQueryTask = new QueryTask(
		// "http://106.37.229.146:89/ArcGIS/rest/services/GJ2014/MapServer/4");

		QueryTask dQueryTask = new QueryTask(
				"http://123.124.221.1:89/ArcGIS/rest/services/GJ2014/MapServer/4");

		FeatureResult fr = null;
		try {

			fr = dQueryTask.execute(queryParameters);

			Iterator<Object> objectIterator = fr.iterator();

			while (objectIterator.hasNext()) {

				Feature feature = (Feature) objectIterator.next();
				Polygon polygon = (Polygon) feature.getGeometry();

				SimpleLineSymbol symbol = new SimpleLineSymbol(Color.argb(128,
						0, 0, 255), 2, SimpleLineSymbol.STYLE.DASHDOT);
				graphicsLayer.setRenderer(new SimpleRenderer(symbol));

				Graphic graphic = new Graphic(polygon, symbol);

				graphicsLayer.addGraphic(graphic);

			}

			return fr;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int getUid() {

		return roadNormalUid;

	}

	public Graphic getProvinceGraphic() {

		return provinceGraphic;

	}

	/**
	 * 缩放到某经纬度点
	 * 
	 * @param longitude
	 * @param latitude
	 */

	public void zoomToPoint(String longitude, String latitude) {
		try {
			mapView.centerAt(Double.valueOf(latitude),
					Double.valueOf(longitude), false);
			mapView.setScale(100000);
		} catch (Exception e) {
		}

	}

	/**
	 * 查询与对应桩号最近的桩号
	 * 
	 * @param road_station
	 * @param lonLatsInfos
	 */

	public LonLatsInfo findLonLats(String road_station,
			ArrayList<LonLatsInfo> lonLatsInfos) {

		LonLatsInfo lonLatsInfo = null;
		String lonLats_road_station = null;

		for (int i = 0; i < lonLatsInfos.size(); i++) {

			lonLats_road_station = lonLatsInfos.get(i).getRoad_station();

			if (lonLats_road_station.equals(road_station)) {

				lonLatsInfo = lonLatsInfos.get(i);
				break;
			}

		}
		if (lonLatsInfo == null) {

			for (int i = 0; i < lonLatsInfos.size(); i++) {

				lonLats_road_station = lonLatsInfos.get(i).getRoad_station();

				double d_road_station = Double.valueOf(road_station);
				double d_lonLats_road_station = Double
						.valueOf(lonLats_road_station);

				if (d_lonLats_road_station >= d_road_station) {

					lonLatsInfo = lonLatsInfos.get(i);
					break;

				}

			}
		}

		if (lonLatsInfo == null) {

			for (int i = 0; i < lonLatsInfos.size(); i++) {

				lonLats_road_station = lonLatsInfos.get(i).getRoad_station();

				double d_road_station = Double.valueOf(road_station);
				double d_lonLats_road_station = Double
						.valueOf(lonLats_road_station);

				if (d_lonLats_road_station <= d_road_station) {

					lonLatsInfo = lonLatsInfos.get(i - 1);
					break;

				}

			}
		}

		return lonLatsInfo;
	}

	/**
	 * 画多条高亮公路方法
	 * 
	 * @param road_info
	 *            存储经纬度的集合
	 * 
	 */
	public void drawNormalRoadsTest(ArrayList<LonLatsInfo> lonlat_road_list,
			int road_index) {

		if (lonlat_road_list.size() != 0) {

			for (int j = 0; j < lonlat_road_list.size() - 1; j++) {

				Polyline polyline = new Polyline();
				Point startPoint = null;
				Point endPoint = null;
				double longtitude = Double.valueOf(lonlat_road_list.get(j)
						.getLongitude());// 经度
				double latitude = Double.valueOf(lonlat_road_list.get(j)
						.getLatitude());// 纬度

				double longtitude_next = Double.valueOf(lonlat_road_list.get(
						j + 1).getLongitude());// 下一个点经度
				double latitude_next = Double.valueOf(lonlat_road_list.get(
						j + 1).getLatitude());// 下一个点经度

				startPoint = GeometryEngine.project(longtitude, latitude,
						SpatialReference.create(4326));

				endPoint = GeometryEngine.project(longtitude_next,
						latitude_next, SpatialReference.create(4326));

				Line line = new Line();

				line.setStart(startPoint);
				line.setEnd(endPoint);
				polyline.addSegment(line, false);

				Graphic g = new Graphic(polyline, new SimpleLineSymbol(context
						.getResources().getColor(R.color.full_transparent), 2));

				int roadNormalUid = transparentLayer.addGraphic(g);

				Map<String, Object> roadsAttr = new HashMap<String, Object>();

				roadsAttr.put("road_index", road_index);
				
				roadsAttr.put("road_station", lonlat_road_list.get(j)
						.getRoad_station());
				roadsAttr.put("current_position", lonlat_road_list.get(j)
						.getCurrent_position());
				roadsAttr.put("current_unit", lonlat_road_list.get(j)
						.getCurrent_feed_unit());
				roadsAttr.put("longitude", lonlat_road_list.get(j)
						.getLongitude());
				roadsAttr
						.put("latitude", lonlat_road_list.get(j).getLatitude());

				transparentLayer.updateGraphic(roadNormalUid, g);
				transparentLayer.updateGraphic(roadNormalUid, roadsAttr);

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
	public Graphic drawRoadPCITest(ArrayList<RoadPCIInfo> result, int road_index) {

		if (result.size() != 0) {

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
							new SimpleLineSymbol(R.color.full_transparent, 3));

					int roadNormalUid = transparentLayer
							.addGraphic(road_indicator_graphic);

					Map<String, Object> roadsAttr = new HashMap<String, Object>();

					roadsAttr.put("road_station", roadLonsLatsInfos.get(j)
							.getRoad_station());
					roadsAttr.put("current_position", roadLonsLatsInfos.get(j)
							.getCurrent_position());
					roadsAttr.put("current_unit", roadLonsLatsInfos.get(j)
							.getCurrent_feed_unit());
					roadsAttr.put("current_tec_level", roadLonsLatsInfos.get(j)
							.getBridge_technical_grade());
					roadsAttr.put("current_road_width", roadLonsLatsInfos
							.get(j).getRoad_way_width());
					roadsAttr.put("current_road_facetype", roadLonsLatsInfos
							.get(j).getRoad_face_type());
					roadsAttr.put("current_lane_num", roadLonsLatsInfos.get(j)
							.getRoad_lane_num());
					roadsAttr.put("current_indicator_num", roadLonsLatsInfos
							.get(j).getCurrent_indicator_value());
					roadsAttr.put("current_daily_traffic", roadLonsLatsInfos
							.get(j).getDaily_traffic_volume());
					roadsAttr.put("current_build_years",
							roadLonsLatsInfos.get(j).getRoad_build_years());
					roadsAttr.put("current_conservation_years",
							roadLonsLatsInfos.get(j)
									.getRoad_conservation_years());
					roadsAttr.put("current_road_condition", roadLonsLatsInfos
							.get(j).getRoad_surface_condition());
					roadsAttr.put("current_conservation_advise",
							roadLonsLatsInfos.get(j)
									.getRoad_conservation_advise());
					roadsAttr.put("longitude", roadLonsLatsInfos.get(j)
							.getLongitude());
					roadsAttr.put("latitude", roadLonsLatsInfos.get(j)
							.getLatitude());
					roadsAttr.put("current_road_data_year", roadLonsLatsInfos
							.get(j).getRoad_data_year());
					roadsAttr.put("road_direction", roadLonsLatsInfos.get(j)
							.getRoad_direction());

					transparentLayer.updateGraphic(roadNormalUid,
							road_indicator_graphic);
					transparentLayer.updateGraphic(roadNormalUid, roadsAttr);
				}

			}

		}

		return null;
	}

	/**
	 * 断线时跳到上，下一个桩号位置
	 * 
	 * @param road_station
	 *            当前桩号
	 * @param lonLatsInfos
	 *            公路经纬度集合
	 */

	public LonLatsInfo moveToNextStation(String road_station,
			ArrayList<LonLatsInfo> lonLatsInfos) {

		LonLatsInfo lonLatsInfo = null;
		int index = -1;

		for (int i = 0; i < lonLatsInfos.size(); i++) {

			String lonLats_road_station = lonLatsInfos.get(i).getRoad_station();

			if (lonLats_road_station.equals(road_station)) {

				index = i;
				Log.i("==========", "======road_station==" + road_station);

				// 当前桩号的起始经纬度
				double road_start_longitude = Double.valueOf(lonLatsInfos.get(
						index).getLongitude());
				double road_start_latitude = Double.valueOf(lonLatsInfos.get(
						index).getLatitude());

				// 当前桩号的结束经纬度
				double road_end_longitude = -1;
				double road_end_latitude = -1;

				if (lonLatsInfos.get(index).getEnd_longitude() != null
						&& lonLatsInfos.get(index).getEnd_latitude() != null) {

					road_end_longitude = Double.valueOf(lonLatsInfos.get(index)
							.getLongitude());
					road_end_latitude = Double.valueOf(lonLatsInfos.get(index)
							.getLatitude());
				}

				// 下一个桩号的起始经纬度
				double road_next_longitude = -1;
				double road_next_latitude = -1;

				if (index < lonLatsInfos.size() - 1) {
					road_next_longitude = Double.valueOf(lonLatsInfos.get(
							index + 1).getLongitude());
					road_next_latitude = Double.valueOf(lonLatsInfos.get(
							index + 1).getLatitude());
				}

				// 上一个桩号的结束经纬度
				double road_forward_end_longitude = -1;
				double road_forward_end_latitude = -1;
				if (index != 0
						&& lonLatsInfos.get(index - 1).getEnd_longitude() != null
						&& lonLatsInfos.get(index - 1).getEnd_latitude() != null) {

					road_forward_end_longitude = Double.valueOf(lonLatsInfos
							.get(index - 1).getEnd_longitude());
					road_forward_end_latitude = Double.valueOf(lonLatsInfos
							.get(index - 1).getEnd_latitude());
				}

				lonLatsInfo = lonLatsInfos.get(index);

				/****************** 断线向上一个桩号滑动 ******************/
				// 有结束经纬度的断线
				if (index != 0 && road_forward_end_longitude != -1
						&& road_forward_end_latitude != -1) {

					if (road_start_longitude != road_forward_end_longitude
							&& road_start_latitude != road_forward_end_latitude) {

						lonLatsInfo = lonLatsInfos.get(index - 1);
						break;

					}

				}

				/****************** 断线向下一个桩号滑动 ******************/
				// 有结束经纬度的断线
				if (index != lonLatsInfos.size() - 1
						&& road_end_longitude != -1 && road_end_latitude != -1) {

					if (road_end_longitude != road_next_longitude
							&& road_end_latitude != road_next_latitude) {

						lonLatsInfo = lonLatsInfos.get(index + 1);
						break;

					}

				}

				break;

			}

		}

		if (lonLatsInfo == null) {

			for (int i = 0; i < lonLatsInfos.size(); i++) {

				double d_road_station = Double.valueOf(road_station);
				double d_lonLats_road_station = Double.valueOf(lonLatsInfos
						.get(i).getRoad_station());

				if (d_lonLats_road_station >= d_road_station) {

					lonLatsInfo = lonLatsInfos.get(i);
					break;

				}

			}
		}

		return lonLatsInfo;

	}

	/**
	 * 获取下一个，上一个桩号的信息
	 * 
	 * @param road_station
	 *            桩号
	 * @param lonLatsInfos
	 *            经纬度信息集合
	 * @param clicktype
	 *            "上一个"，"下一个"
	 */
	public LonLatsInfo findNextOrForwardStation(String road_station,
			ArrayList<LonLatsInfo> lonLatsInfos, String clicktype) {
		int index = -1;
		String lonLats_road_station = null;

		for (int i = 0; i < lonLatsInfos.size(); i++) {

			lonLats_road_station = lonLatsInfos.get(i).getRoad_station();

			if (lonLats_road_station.equals(road_station)) {

				index = i;
				break;
			}

		}

		Log.i("=========", "=======wolege0000===" + index);

		if (index == -1) {

			for (int i = 0; i < lonLatsInfos.size(); i++) {

				lonLats_road_station = lonLatsInfos.get(i).getRoad_station();

				int int_road_station = (int) Math.floor((Double
						.valueOf(road_station)));

				int int_data_road_station = (int) Math.floor(Double
						.valueOf(lonLats_road_station));

				if (int_road_station == int_data_road_station
						|| int_road_station == (int_data_road_station + 1)
						|| int_road_station == (int_data_road_station - 1)) {

					index = i;
					break;

				}

			}
		}

		Log.i("=========", "=======wolege11111===" + index);

		if ("next".equals(clicktype)) {

			if (index < lonLatsInfos.size() - 1) {
				return lonLatsInfos.get(index + 1);

			} else {
				return null;
			}

		} else {

			if (index > 0) {

				return lonLatsInfos.get(index - 1);
			} else {

				return null;
			}

		}

	}

	/**
	 * 截取省份名称字符串
	 * 
	 * @param province_name
	 *            省份名称
	 * @param province_code
	 *            省份编码
	 */
	public String subStringProvinceName(String province_name,
			String province_code) {

		if (province_name.length() >= 4) {

			if (province_code == null) {

			} else {

				if ("230000".equals(province_code)) {
					province_name = province_name.substring(0, 3);
				} else {

					province_name = province_name.substring(0, 2);
				}

			}

		}

		return province_name;
	}

	/**
	 * 获取相应路段的信息
	 * 
	 * @param transparentLayer
	 * @param screenX
	 * @param screenY
	 */
	public LonLatsInfo getCurrentStationInfo(GraphicsLayer transparentLayer,
			String screenX, String screenY) {

		int[] graphicUids = transparentLayer.getGraphicIDs(
				Float.valueOf(screenX), Float.valueOf(screenY), 15, 1);

		if (graphicUids != null && graphicUids.length == 1) {

			Graphic line = transparentLayer.getGraphic(graphicUids[0]);

			LonLatsInfo lonLatsInfo = (LonLatsInfo) line
					.getAttributeValue("lonlatsInfo");

			return lonLatsInfo;

		}

		return null;
	}

}
