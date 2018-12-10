package com.mobo.zggkgisandroid.Utils;

import java.util.Iterator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;

public class MapHelper {

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
	public static FeatureResult SearchProvince(String province_code, Point point) {

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
		QueryTask dQueryTask = new QueryTask(
				"http://106.37.229.146:89/ArcGIS/rest/services/GJ2014/MapServer/68");

		FeatureResult fr;

		try {

			fr = dQueryTask.execute(queryParameters);

			Iterator<Object> objectIterator = fr.iterator();

			while (objectIterator.hasNext()) {

				Feature feature = (Feature) objectIterator.next();
				Polygon polygon = (Polygon) feature.getGeometry();

				SimpleLineSymbol symbol = new SimpleLineSymbol(Color.argb(128,
						0, 0, 255), 2, SimpleLineSymbol.STYLE.DASHDOT);
				// graphicsLayer.setRenderer(new SimpleRenderer(symbol));

				// Graphic graphic = new Graphic(polygon, symbol);

				// graphicsLayer.addGraphic(graphic);

			}

			return fr;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 文字转换BitMap
	 * 
	 * @param text
	 * @return
	 */
	public static Drawable createMapBitMap(String text) {

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(60);

		paint.setAntiAlias(true);
		paint.setTextAlign(Align.RIGHT);

		float textLength = paint.measureText(text);

		int width = (int) textLength + 10;
		int height = 120;

		Bitmap newb = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		Canvas cv = new Canvas(newb);
		cv.drawColor(Color.parseColor("#00000000"));

		cv.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));

		cv.drawText(text, width / 2, 60, paint);

		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储

		return new BitmapDrawable(null, newb);

	}
	
	/**
	 * 判断点击的点是否属于某一要素标志（桥梁，隧道，设施，施工）
	 * 
	 * @param s_longitude
	 *            要素标志经度
	 * @param s_latitude
	 *            要素标志纬度
	 * @param type
	 *            点击类型（0代表点击在要素上，1代表点击在小图上）
	 * 
	 * @return int 返回值（1代表属于功能的某一区域，-1代表不属于）
	 */
	public static int isPtBelongMarker(String s_longitude, String s_latitude,
			String c_longitude, String c_latitude) {

		Double max_longitude = 0.0;
		Double max_latitude = 0.0;
		Double min_longitude = 0.0;
		Double min_latitude = 0.0;

		
		
		Double longitude = Double.valueOf(c_longitude);
		Double latitude = Double.valueOf(c_latitude);

		Point b_point=new Point(longitude,latitude);
		
		
				
				
		
		if (!"".equals(s_longitude) && !"".equals(s_latitude)) {

			Point c_point=new Point(Double.valueOf(s_longitude),Double.valueOf(s_latitude));
			
			double distance=GeometryEngine.distance(b_point, c_point, SpatialReference.create(4326));
			
			Log.i("=====================", "======+distance==="+distance);
			
//			max_longitude = Double.valueOf(s_longitude) + 0.02;
//			max_latitude = Double.valueOf(s_latitude) + 0.04;
//			min_longitude = Double.valueOf(s_longitude) - 0.02;
//			min_latitude = Double.valueOf(s_latitude) - 0.01;
//
//			Log.i("==============", "==000000" + max_longitude + "=="
//					+ min_longitude + "==" + max_latitude + "==" + min_latitude
//					+ "==" + c_longitude + "==" + c_latitude);
//
//			if (longitude <= max_longitude && longitude >= min_longitude
//					&& latitude <= max_latitude && latitude >= min_latitude) {
//
//				return 1;
//
//			}
			
			
			if (distance<=0.001) {
				return 1;
			}
		}

		return -1;
	}
}
