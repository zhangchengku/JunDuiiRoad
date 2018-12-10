package com.mobo.zggkgisandroid.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
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
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgeNationalInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgePositionInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.ServiceNationalInfo;
import com.mobo.zggkgisandroid.ClassfyInfo.ServicePositionInfo;

public class ServiceHelper {

	private LayoutInflater inflater;
	private GraphicsLayer graphicsLayer;

	public ServiceHelper(Context context, GraphicsLayer graphicsLayer) {
		// this.context = context;
		this.graphicsLayer = graphicsLayer;
		inflater = LayoutInflater.from(context);
	}

	/**
	 * 地图上展示桥梁要素的处理方法
	 * 
	 * @param serviceNationalInfoList
	 *            全国桥梁结果类
	 * @param servicePositionInfoList
	 *            省份桥梁结果类
	 */
	public void showServiceMarkers(
			ArrayList<ServiceNationalInfo> serviceNationalInfoList,
			ArrayList<ServicePositionInfo> servicePositionInfoList) {

		Bitmap bm = null;
		Drawable drawable = null;
		Graphic[] graphics = null;
		PictureMarkerSymbol pictureMarkerSymbol = null;
		Point point = null;
		Graphic graphic = null;

		// 全国服务区
		if (serviceNationalInfoList != null
				&& serviceNationalInfoList.size() != 0) {
			graphics = new Graphic[serviceNationalInfoList.size()];

			View redTxtBubble = inflater.inflate(R.layout.quanguo_data_layout,
					null);

			TextView numTxt = (TextView) redTxtBubble
					.findViewById(R.id.quanguo_red_bubble_txt);

			for (int i = 0; i < serviceNationalInfoList.size(); i++) {

				numTxt.setText(serviceNationalInfoList.get(i)
						.getService_area_num());

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

				point = new Point(Double.valueOf(serviceNationalInfoList.get(i)
						.getLongitude()),
						Double.valueOf(serviceNationalInfoList.get(i)
								.getLatitude()));

				Map<String, Object> atts = new HashMap<String, Object>();
				atts.put("province_code", serviceNationalInfoList.get(i)
						.getProvince_code());
				atts.put("province_name", serviceNationalInfoList.get(i)
						.getProvince_name());

				graphic = new Graphic(point, pictureMarkerSymbol, atts);

				graphics[i] = graphic;
			}
		}

		// 省份服务区
		if (servicePositionInfoList != null
				&& servicePositionInfoList.size() != 0) {

			graphics = new Graphic[servicePositionInfoList.size()];
			View redTxtBubble = inflater.inflate(
					R.layout.only_red_bubble_layout, null);

			for (int i = 0; i < servicePositionInfoList.size(); i++) {

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

				String bridge_longitude = servicePositionInfoList.get(i)
						.getLongitude();
				String bridge_latitude = servicePositionInfoList.get(i)
						.getLatitude();

				if ("".equals(bridge_longitude) || "".equals(bridge_latitude)) {

				} else {

					point = new Point(Double.valueOf(bridge_longitude),
							Double.valueOf(bridge_latitude));

					// graphic = new Graphic(point, pictureMarkerSymbol);

					Map<String, Object> atts = new HashMap<String, Object>();
					atts.put("service_area_code", servicePositionInfoList
							.get(i).getService_area_code());
					atts.put("service_area_name", servicePositionInfoList
							.get(i).getService_area_name());
					atts.put("road_code", servicePositionInfoList.get(i)
							.getRoad_code());
					atts.put("road_name", servicePositionInfoList.get(i)
							.getRoad_name());
					atts.put("longitude", servicePositionInfoList.get(i)
							.getLongitude());
					atts.put("latitude", servicePositionInfoList.get(i)
							.getLatitude());
					atts.put("service_area_center_station",
							servicePositionInfoList.get(i)
									.getService_area_center_station());

					graphic = new Graphic(point, pictureMarkerSymbol, atts);

					graphics[i] = graphic;
				}
			}

			graphicsLayer.addGraphics(graphics);

		}

		graphicsLayer.addGraphics(graphics);

		bm.recycle();

	}

	/**
	 * 省份服务区点击
	 */
	public void mClickOnService(Context context, Graphic ServiceGraphic,
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

		if (ServiceGraphic != null) {

			bridge_station = (String) ServiceGraphic
					.getAttributeValue("bridge_center_station");
			bridge_name = (String) ServiceGraphic
					.getAttributeValue("bridge_name");
			bridge_code = (String) ServiceGraphic
					.getAttributeValue("bridge_code");

			bridge_belong_info = (String) ServiceGraphic
					.getAttributeValue("bridge_location_route");

			bridge_length = (String) ServiceGraphic
					.getAttributeValue("bridge_length");
			bridge_width = (String) ServiceGraphic
					.getAttributeValue("bridge_width");
			bridge_load_level = (String) ServiceGraphic
					.getAttributeValue("load_weight_level");
			bridge_navigation_level = (String) ServiceGraphic
					.getAttributeValue("navigation_level");
			bridge_lonitude = (String) ServiceGraphic
					.getAttributeValue("longitude");
			bridge_latitude = (String) ServiceGraphic
					.getAttributeValue("latitude");

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

		}

		mapUtils.zoomToPoint(bridge_lonitude, bridge_latitude);

		vSearchResultContainer.removeAllViews();

		TextView bridgeNameTxt = new TextView(context);

		bridgeNameTxt.setText(bridge_name);
		bridgeNameTxt.setTextSize(16);
		bridgeNameTxt.setBackgroundColor(Color.WHITE);
		TextPaint tp = bridgeNameTxt.getPaint();
		tp.setFakeBoldText(true);

		callout.setOffsetDp(0, -35);
		callout.setContent(bridgeNameTxt);
		callout.setCoordinates(new Point(Double.valueOf(bridge_lonitude),
				Double.valueOf(bridge_latitude)));

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
		TextView bridgeDetailBelongTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_belong_info_txt);
		TextView bridgeDetailWidthTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_width_txt);
		TextView bridgeDetailLenghtTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_length_txt);
		TextView bridgeDetailWeightTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_weight_txt);
		TextView bridgeDetailLevelTxt = (TextView) vSearchResultContainer
				.findViewById(R.id.bridge_detail_level_txt);

		bridgeDetailNameTxt.setText(bridge_name);
		bridgeDetailBelongTxt.setText(bridge_belong_info + " "
				+ mapUtils.changeStationToStandard(bridge_station));
		bridgeDetailLenghtTxt.setText(bridge_length);
		bridgeDetailWidthTxt.setText(bridge_width);
		bridgeDetailWeightTxt.setText(bridge_load_level);
		bridgeDetailLevelTxt.setText(bridge_navigation_level);

		vSearchResultContainer.setVisibility(View.VISIBLE);

	}
}
