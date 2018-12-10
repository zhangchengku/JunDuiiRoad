package com.mobo.zggkgisandroid.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.android.map.Callout;
import com.esri.android.map.CalloutStyle;
import com.esri.android.map.GraphicsLayer;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgeMarksInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgeNationalInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgePositionInfo;

/**
 * 桥梁数据处理工具类
 * 
 * @author wjm
 * 
 */
public class BridgeHelper {

	private LayoutInflater inflater;//inflater
	private GraphicsLayer graphicsLayer;//绘制

	public BridgeHelper(Context context, GraphicsLayer graphicsLayer) {
		// this.context = context;
		this.graphicsLayer = graphicsLayer;
		inflater = LayoutInflater.from(context);
	}

	/**
	 * 地图上展示桥梁要素的处理方法
	 * 
	 * @param bridgeNationalInfoList
	 *            全国桥梁结果类
	 * @param bridgePositionInfos
	 *            省份桥梁结果类
	 */
	public void showBridgeMarkers(
			ArrayList<BridgeNationalInfo> bridgeNationalInfoList,
			ArrayList<BridgePositionInfo> bridgePositionInfos, int index) {

		Bitmap bm = null;
		Bitmap blueBm = null;
		Drawable drawable = null;
		Drawable bluedDrawable = null;
		Graphic[] graphics = null;
		PictureMarkerSymbol pictureMarkerSymbol = null;
		PictureMarkerSymbol blueMarkerSymbol = null;
		Point point = null;
		Graphic graphic = null;

		// 全国桥梁
		if (bridgeNationalInfoList != null
				&& bridgeNationalInfoList.size() != 0) {
			graphics = new Graphic[bridgeNationalInfoList.size()];

			View redTxtBubble = inflater.inflate(R.layout.quanguo_data_layout,
					null);

			TextView numTxt = (TextView) redTxtBubble
					.findViewById(R.id.quanguo_red_bubble_txt);

			for (int i = 0; i < bridgeNationalInfoList.size(); i++) {

				numTxt.setText(bridgeNationalInfoList.get(i).getBridge_num());

				if (redTxtBubble != null) {

					redTxtBubble.measure(MeasureSpec.makeMeasureSpec(0,
							MeasureSpec.UNSPECIFIED), MeasureSpec
							.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
					redTxtBubble.layout(0, 0, redTxtBubble.getMeasuredWidth(),
							redTxtBubble.getMeasuredHeight());
				}

				redTxtBubble.buildDrawingCache();
				redTxtBubble.setDrawingCacheEnabled(true);

				bm = Bitmap.createBitmap(redTxtBubble.getDrawingCache(true));

				drawable = new BitmapDrawable(null, bm);

				redTxtBubble.destroyDrawingCache();
				redTxtBubble.setDrawingCacheEnabled(false);

				pictureMarkerSymbol = new PictureMarkerSymbol(drawable);

				point = new Point(Double.valueOf(bridgeNationalInfoList.get(i)
						.getLongitude()), Double.valueOf(bridgeNationalInfoList
						.get(i).getLatitude()));

				Map<String, Object> atts = new HashMap<String, Object>();
				atts.put("province_code", bridgeNationalInfoList.get(i)
						.getProvince_code());
				atts.put("province_name", bridgeNationalInfoList.get(i)
						.getProvince_name());

				graphic = new Graphic(point, pictureMarkerSymbol, atts);

				graphics[i] = graphic;
			}
		}

		// 省份桥梁
		if (bridgePositionInfos != null && bridgePositionInfos.size() != 0) {

			graphics = new Graphic[bridgePositionInfos.size()];
			View redTxtBubble = inflater.inflate(
					R.layout.only_red_bubble_layout, null);

			View blueBubble = inflater.inflate(R.layout.only_blue_bubble_layout,
					null);

			if (redTxtBubble != null) {

				redTxtBubble
						.measure(MeasureSpec.makeMeasureSpec(0,
								MeasureSpec.UNSPECIFIED), MeasureSpec
								.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				redTxtBubble.layout(0, 0, redTxtBubble.getMeasuredWidth(),
						redTxtBubble.getMeasuredHeight());
			}

			redTxtBubble.buildDrawingCache();
			redTxtBubble.setDrawingCacheEnabled(true);

			bm = Bitmap.createBitmap(redTxtBubble.getDrawingCache(true));

			drawable = new BitmapDrawable(null, bm);

			redTxtBubble.destroyDrawingCache();
			redTxtBubble.setDrawingCacheEnabled(false);

			pictureMarkerSymbol = new PictureMarkerSymbol(drawable);

			if (blueBubble != null) {

				blueBubble
						.measure(MeasureSpec.makeMeasureSpec(0,
								MeasureSpec.UNSPECIFIED), MeasureSpec
								.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				blueBubble.layout(0, 0, blueBubble.getMeasuredWidth(),
						blueBubble.getMeasuredHeight());
			}

			blueBubble.buildDrawingCache();
			blueBubble.setDrawingCacheEnabled(true);

			blueBm = Bitmap.createBitmap(blueBubble.getDrawingCache(true));

			bluedDrawable = new BitmapDrawable(null, blueBm);

			blueBubble.destroyDrawingCache();
			blueBubble.setDrawingCacheEnabled(false);

			blueMarkerSymbol = new PictureMarkerSymbol(bluedDrawable);

			for (int i = 0; i < bridgePositionInfos.size(); i++) {

				String bridge_longitude = bridgePositionInfos.get(i)
						.getLongitude();
				String bridge_latitude = bridgePositionInfos.get(i)
						.getLatitude();

				if ("".equals(bridge_longitude) || "".equals(bridge_latitude)) {

				} else {

					point = new Point(Double.valueOf(bridge_longitude),
							Double.valueOf(bridge_latitude));

					// graphic = new Graphic(point, pictureMarkerSymbol);

					Map<String, Object> atts = new HashMap<String, Object>();
					atts.put("list_index", i);
					atts.put("bridge_code", bridgePositionInfos.get(i)
							.getBridge_code());
					atts.put("bridge_name", bridgePositionInfos.get(i)
							.getBridge_name());
					atts.put("bridge_length", bridgePositionInfos.get(i)
							.getBridge_length());
					atts.put("bridge_width", bridgePositionInfos.get(i)
							.getBridge_width());
					atts.put("bridge_center_station", bridgePositionInfos
							.get(i).getBridge_center_station());
					atts.put("load_weight_level", bridgePositionInfos.get(i)
							.getLoad_weight_level());
					atts.put("navigation_level", bridgePositionInfos.get(i)
							.getNavigation_level());
					atts.put("bridge_location_route", bridgePositionInfos
							.get(i).getBridge_location_route());
					atts.put("longitude", bridgePositionInfos.get(i)
							.getLongitude());
					atts.put("latitude", bridgePositionInfos.get(i)
							.getLatitude());
					atts.put("road_code", bridgePositionInfos.get(i)
							.getRoad_code());

					atts.put("bridge_technical_grade",
							bridgePositionInfos.get(i)
									.getBridge_technical_grade());
					atts.put("bridge_wight", bridgePositionInfos.get(i)
							.getBridge_wight());
					atts.put("bridge_clean_height", bridgePositionInfos.get(i)
							.getBridge_clean_height());
					atts.put("bridge_year", bridgePositionInfos.get(i)
							.getBridge_year());
					atts.put("bridge_type", bridgePositionInfos.get(i)
							.getBridge_type());
					atts.put("bridge_span_type", bridgePositionInfos.get(i)
							.getBridge_span_type());

					if (index!=-1&&i == index) {

						graphic = new Graphic(point, blueMarkerSymbol, atts);

					} else {
						graphic = new Graphic(point, pictureMarkerSymbol, atts);
					}

					graphics[i] = graphic;
				}
			}

			graphicsLayer.addGraphics(graphics);

		}

		graphicsLayer.addGraphics(graphics);

		bm.recycle();

	}

	/**
	 * 省份桥梁点击
	 */
	public void mClickOnBridge(Context context, Graphic bridgeGraphic,
			BridgePositionInfo bridgePositionInfo, Callout callout,
			MapUtils mapUtils, LinearLayout vSearchResultContainer) {

		String bridge_station = "";
		String bridge_name = "";
		String bridge_code = "";
		String bridge_belong_info = "";
		String bridge_length = "";
		String bridge_width = "";
		String bridge_load_level = "";
		String bridge_navigation_level = "";
		String bridge_lonitude = "";
		String bridge_latitude = "";

		String bridge_technical_grade = "";
		String bridge_wight = "";
		String bridge_clean_height = "";
		String bridge_year = "";
		String bridge_type = "";
		String bridge_span_type = "";

		if (bridgeGraphic != null) {

			bridge_station = (String) bridgeGraphic
					.getAttributeValue("bridge_center_station");
			bridge_name = (String) bridgeGraphic
					.getAttributeValue("bridge_name");
			bridge_code = (String) bridgeGraphic
					.getAttributeValue("bridge_code");

			bridge_belong_info = (String) bridgeGraphic
					.getAttributeValue("bridge_location_route");

			bridge_length = (String) bridgeGraphic
					.getAttributeValue("bridge_length");
			bridge_width = (String) bridgeGraphic
					.getAttributeValue("bridge_width");
			bridge_load_level = (String) bridgeGraphic
					.getAttributeValue("load_weight_level");
			bridge_navigation_level = (String) bridgeGraphic
					.getAttributeValue("navigation_level");
			bridge_lonitude = (String) bridgeGraphic
					.getAttributeValue("longitude");
			bridge_latitude = (String) bridgeGraphic
					.getAttributeValue("latitude");

			bridge_type = (String) bridgeGraphic
					.getAttributeValue("bridge_type");
			bridge_technical_grade = (String) bridgeGraphic
					.getAttributeValue("bridge_technical_grade");
			bridge_wight = (String) bridgeGraphic
					.getAttributeValue("bridge_wight");
			bridge_clean_height = (String) bridgeGraphic
					.getAttributeValue("bridge_clean_height");
			bridge_year = (String) bridgeGraphic
					.getAttributeValue("bridge_year");
			bridge_span_type = (String) bridgeGraphic
					.getAttributeValue("bridge_span_type");

		} else {

			bridge_station = bridgePositionInfo.getBridge_center_station();
			bridge_name = bridgePositionInfo.getBridge_name();
			bridge_code = bridgePositionInfo.getBridge_code();

			bridge_belong_info = bridgePositionInfo.getBridge_location_route();

			bridge_length = bridgePositionInfo.getBridge_length();
			bridge_width = bridgePositionInfo.getBridge_width();
			bridge_load_level = bridgePositionInfo.getLoad_weight_level();
			bridge_navigation_level = bridgePositionInfo.getNavigation_level();
			bridge_lonitude = bridgePositionInfo.getLongitude();
			bridge_latitude = bridgePositionInfo.getLatitude();

			bridge_type = bridgePositionInfo.getBridge_type();
			bridge_technical_grade = bridgePositionInfo
					.getBridge_technical_grade();
			bridge_wight = bridgePositionInfo.getBridge_wight();
			bridge_clean_height = bridgePositionInfo.getBridge_clean_height();
			bridge_year = bridgePositionInfo.getBridge_year();
			bridge_span_type = bridgePositionInfo.getBridge_span_type();

		}
		if(CustomApp.app.firstzoom){
			mapUtils.zoomToPoint(bridge_lonitude, bridge_latitude);
			CustomApp.app.firstzoom = false;
		}

		vSearchResultContainer.removeAllViews();

		TextView bridgeNameTxt = new TextView(context);

		bridgeNameTxt.setText(bridge_name);
		bridgeNameTxt.setTextSize(16);
		bridgeNameTxt.setBackgroundColor(Color.WHITE);
		TextPaint tp = bridgeNameTxt.getPaint();
		tp.setFakeBoldText(true);

		callout.setOffsetDp(0, -35);
		callout.setContent(bridgeNameTxt);
		try {
			callout.setCoordinates(new Point(Double.valueOf(bridge_lonitude),
					Double.valueOf(bridge_latitude)));
		} catch (Exception e) {
		}

		CalloutStyle calloutStyle = new CalloutStyle(context);
		calloutStyle.setFrameColor(Color.WHITE);
		calloutStyle.setCornerCurveDp(0, context);

		calloutStyle.setAnchor(6);

		callout.setStyle(calloutStyle);

		callout.show();

		// 桥梁item点击
		View bridgeDetailview = inflater.inflate(R.layout.bridge_detail_layout,
				null);

		vSearchResultContainer.addView(bridgeDetailview);

		TextView bridgeDetailNameTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_name_txt);

		TextView bridgeDetailLevelTypeTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_level_type_txt);
		TextView bridgeDetailPositionTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_position_txt);
		TextView bridgeDetailTypeTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_type_txt);
		TextView bridgeDetailWidthTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_width_txt);
		TextView bridgeDetailLenghtTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_length_txt);
		TextView bridgeDetailHeightTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_height_txt);
		TextView bridgeDetailBuildYearTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_build_year_txt);
		TextView bridgeDetailTecLevelTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_tec_level_txt);

		// TextView bridgeDetailWeightTxt = (TextView) vSearchResultContainer
		// .findViewById(R.id.bridge_detail_weight_txt);
		// TextView bridgeDetailLevelTxt = (TextView) vSearchResultContainer
		// .findViewById(R.id.bridge_detail_level_txt);

		// TextView bridgeDetailBelongTxt = (TextView) vSearchResultContainer
		// .findViewById(R.id.bridge_detail_belong_info_txt);
		bridgeDetailNameTxt.setText(bridge_name);
		// bridgeDetailBelongTxt.setText(bridge_belong_info + " "
		// + mapUtils.changeStationToStandard(bridge_station));
		bridgeDetailLenghtTxt.setText(bridge_length);
		bridgeDetailWidthTxt.setText(bridge_wight);
		// bridgeDetailWeightTxt.setText(bridge_load_level);
		// bridgeDetailLevelTxt.setText(bridge_navigation_level);

		bridgeDetailPositionTxt.setText(mapUtils
				.changeStationToStandard(bridge_station));
		bridgeDetailLevelTypeTxt.setText(bridge_type);
		bridgeDetailTypeTxt.setText(bridge_span_type);
		bridgeDetailBuildYearTxt.setText(bridge_year);
		bridgeDetailTecLevelTxt.setText(bridge_technical_grade);
		bridgeDetailHeightTxt.setText(bridge_clean_height);

		vSearchResultContainer.setVisibility(View.VISIBLE);

	}

	/**
	 * 用于存城市
	 * 
	 * @param bridgeNationalInfoList
	 * @param bridgePositionInfos
	 */
	public void showBridgeMarkers2(ArrayList<BridgeMarksInfo> bridgeMarksInfos,
			ArrayList<BridgePositionInfo> bridgePositionInfos) {

		Bitmap bm = null;
		Drawable drawable = null;
		Graphic[] graphics = null;
		PictureMarkerSymbol pictureMarkerSymbol = null;
		Point point = null;
		Graphic graphic = null;

		// 全国桥梁
		if (bridgeMarksInfos != null && bridgeMarksInfos.size() != 0) {
			graphics = new Graphic[bridgeMarksInfos.size()];

			View redTxtBubble = inflater.inflate(R.layout.quanguo_data_layout,
					null);

			TextView numTxt = (TextView) redTxtBubble
					.findViewById(R.id.quanguo_red_bubble_txt);

			for (int i = 0; i < bridgeMarksInfos.size(); i++) {

				numTxt.setText(String.valueOf(bridgeMarksInfos.get(i)
						.getBridge_number()));

				if (redTxtBubble != null) {

					redTxtBubble.measure(MeasureSpec.makeMeasureSpec(0,
							MeasureSpec.UNSPECIFIED), MeasureSpec
							.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
					redTxtBubble.layout(0, 0, redTxtBubble.getMeasuredWidth(),
							redTxtBubble.getMeasuredHeight());
				}

				redTxtBubble.buildDrawingCache();
				redTxtBubble.setDrawingCacheEnabled(true);

				bm = Bitmap.createBitmap(redTxtBubble.getDrawingCache(true));

				drawable = new BitmapDrawable(null, bm);

				redTxtBubble.destroyDrawingCache();
				redTxtBubble.setDrawingCacheEnabled(false);

				pictureMarkerSymbol = new PictureMarkerSymbol(drawable);

				point = new Point(Double.valueOf(bridgeMarksInfos.get(i)
						.getLongitude()), Double.valueOf(bridgeMarksInfos
						.get(i).getLatitude()));

				Map<String, Object> atts = new HashMap<String, Object>();
				atts.put("city_code", bridgeMarksInfos.get(i).getArea_code());
				atts.put("city_name", bridgeMarksInfos.get(i).getArea_name());

				graphic = new Graphic(point, pictureMarkerSymbol, atts);

				graphics[i] = graphic;
			}
		}

		// 省份桥梁
		if (bridgePositionInfos != null && bridgePositionInfos.size() != 0) {

			graphics = new Graphic[bridgePositionInfos.size()];
			View redTxtBubble = inflater.inflate(
					R.layout.only_red_bubble_layout, null);

			for (int i = 0; i < bridgePositionInfos.size(); i++) {

				if (redTxtBubble != null) {

					redTxtBubble.measure(MeasureSpec.makeMeasureSpec(0,
							MeasureSpec.UNSPECIFIED), MeasureSpec
							.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
					redTxtBubble.layout(0, 0, redTxtBubble.getMeasuredWidth(),
							redTxtBubble.getMeasuredHeight());
				}

				redTxtBubble.buildDrawingCache();
				redTxtBubble.setDrawingCacheEnabled(true);

				bm = Bitmap.createBitmap(redTxtBubble.getDrawingCache(true));

				drawable = new BitmapDrawable(null, bm);

				redTxtBubble.destroyDrawingCache();
				redTxtBubble.setDrawingCacheEnabled(false);

				pictureMarkerSymbol = new PictureMarkerSymbol(drawable);

				String bridge_longitude = bridgePositionInfos.get(i)
						.getLongitude();
				String bridge_latitude = bridgePositionInfos.get(i)
						.getLatitude();

				if ("".equals(bridge_longitude) || "".equals(bridge_latitude)) {

				} else {

					point = new Point(Double.valueOf(bridge_longitude),
							Double.valueOf(bridge_latitude));

					// graphic = new Graphic(point, pictureMarkerSymbol);

					Map<String, Object> atts = new HashMap<String, Object>();
					atts.put("bridge_code", bridgePositionInfos.get(i)
							.getBridge_code());
					atts.put("bridge_name", bridgePositionInfos.get(i)
							.getBridge_name());
					atts.put("bridge_length", bridgePositionInfos.get(i)
							.getBridge_length());
					atts.put("bridge_width", bridgePositionInfos.get(i)
							.getBridge_width());
					atts.put("bridge_center_station", bridgePositionInfos
							.get(i).getBridge_center_station());
					atts.put("load_weight_level", bridgePositionInfos.get(i)
							.getLoad_weight_level());
					atts.put("navigation_level", bridgePositionInfos.get(i)
							.getNavigation_level());
					atts.put("bridge_location_route", bridgePositionInfos
							.get(i).getBridge_location_route());
					atts.put("longitude", bridgePositionInfos.get(i)
							.getLongitude());
					atts.put("latitude", bridgePositionInfos.get(i)
							.getLatitude());
					atts.put("road_code", bridgePositionInfos.get(i)
							.getRoad_code());

					atts.put("bridge_technical_grade",
							bridgePositionInfos.get(i)
									.getBridge_technical_grade());
					atts.put("bridge_wight", bridgePositionInfos.get(i)
							.getBridge_wight());
					atts.put("bridge_clean_height", bridgePositionInfos.get(i)
							.getBridge_clean_height());
					atts.put("bridge_year", bridgePositionInfos.get(i)
							.getBridge_year());
					atts.put("bridge_type", bridgePositionInfos.get(i)
							.getBridge_type());
					atts.put("bridge_span_type", bridgePositionInfos.get(i)
							.getBridge_span_type());

					graphic = new Graphic(point, pictureMarkerSymbol, atts);

					graphics[i] = graphic;
				}
			}

			graphicsLayer.addGraphics(graphics);

		}

		graphicsLayer.addGraphics(graphics);

		bm.recycle();

	}
}
