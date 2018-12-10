package com.mobo.zggkgisandroid.ServiceArea;

import java.util.ArrayList;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.BaseActivity;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Search.SearchActivity;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.ServiceAreaListResult;
import com.mobo.zggkgisandroid.WebModel.ServiceAreaListResult.ServiceArea;
import com.mobo.zggkgisandroid.WebModel.ExpresswayListResult.ExpresswayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 高速公路列表
 * 
 * @author WuXiaoHao
 *
 */
public class ExpresswayListActivity extends BaseActivity {
	private CityList currentCity;// 当前所选的省份
	private ArrayList<ExpresswayList> expresswayLists;
	private String locX, locY;// 经纬度

	private ListView listView;// 列表

	private int type = 0;// 0服务区，1施工点，2灾毁

	private int hegiht;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentXml(R.layout.expresswaylist_activity_layout);
		init();
	}

	private void init() {
		expresswayLists = (ArrayList<ExpresswayList>) getIntent().getSerializableExtra("result");
		currentCity = (CityList) getIntent().getSerializableExtra("currentCity");
		locX = getIntent().getStringExtra("locX");
		locY = getIntent().getStringExtra("locY");
		type = getIntent().getIntExtra("type", 0);

		tvLeft.setText(currentCity.getProvince_name());
		tvTitle.setText(R.string.expressway_list_title);
		imgLeft.setImageResource(R.drawable.rank_back_btn_sel);

		listView = (ListView) findViewById(R.id.list_expressway);
		listView.setAdapter(new ListAdapter());
	}

	@Override
	protected void onClickLeft(View view) {
		super.onClickLeft(view);
		finish();
	}

	private class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return expresswayLists != null ? expresswayLists.size() : 0;
		}

		@Override
		public ExpresswayList getItem(int arg0) {
			return expresswayLists.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder viewHolder;
			if (arg1 == null) {
				viewHolder = new ViewHolder();
				arg1 = LayoutInflater.from(ExpresswayListActivity.this).inflate(R.layout.expressway_list_item, arg2,
						false);
				viewHolder.tvName = (TextView) arg1.findViewById(R.id.tv_expressway_list_item);
				viewHolder.tvCode = (TextView) arg1.findViewById(R.id.tv_expressway_list_item_code);
				viewHolder.tvNameLeft = (TextView) arg1.findViewById(R.id.tv_expressway_list_item_left_name);
				viewHolder.tvCodeLeft = (TextView) arg1.findViewById(R.id.tv_expressway_list_item_left_code);
				viewHolder.layoutLeft = (LinearLayout) arg1.findViewById(R.id.linear_expressway_list_item_left);
				viewHolder.layoutRight = (LinearLayout) arg1.findViewById(R.id.linear_expressway_list_item_right);
				if (hegiht == 0)
					hegiht = getViewHeight(viewHolder.layoutRight);
				viewHolder.layoutLeft.getLayoutParams().width = hegiht - Utils.dip2px(ExpresswayListActivity.this, 7);
				viewHolder.layoutLeft.getLayoutParams().height = viewHolder.layoutLeft.getLayoutParams().width;
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			final ExpresswayList expresswayList = getItem(arg0);
			viewHolder.tvName.setText(expresswayList.getRoad_name());
			viewHolder.tvCode.setText(expresswayList.getRoad_code());
			viewHolder.tvNameLeft.setText(expresswayList.getRoad_name());
			viewHolder.tvCodeLeft.setText(expresswayList.getRoad_code());
			arg1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					new ThreadWithProgressDialog().Run(ExpresswayListActivity.this,
							new getServiceAreaList(expresswayList.getRoad_code(), expresswayList.getRoad_name()),
							R.string.com_loading);
				}
			});
			return arg1;
		}

		private int getViewHeight(View view) {
			int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			view.measure(width, height);
			return view.getMeasuredHeight();

		}

		private class ViewHolder {
			LinearLayout layoutLeft, layoutRight;
			TextView tvNameLeft, tvCodeLeft;// 左侧公路名称,公路代码
			TextView tvName, tvCode;// 公路名字 公路代码
		}
	}

	/**
	 * 获取服务区列表
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class getServiceAreaList implements ThreadWithProgressDialogTask {
		private ServiceAreaListResult result;
		private String road_code;
		private String road_name;

		public getServiceAreaList(String road_code, String road_name) {
			this.road_code = road_code;
			this.road_name = road_name;
		}

		@Override
		public boolean TaskMain() {
			result = CustomApp.app.connInteface.getServiceAreaList(road_code, locX, locY);
			result = new ServiceAreaListResult();
			ArrayList<ServiceArea> areas = new ArrayList<ServiceArea>();
			ServiceArea area = new ServiceAreaListResult().new ServiceArea();
			area.setDistance("12311");
			area.setRoad_code(road_code);
			area.setRoad_name("京藏高速");
			area.setService_area_code("T1921");
			area.setService_area_img("http://img3.imgtn.bdimg.com/it/u=2419394602,4143314823&fm=21&gp=0.jpg");
			area.setService_area_name("京藏服务站");
			areas.add(area);
			area = new ServiceAreaListResult().new ServiceArea();
			area.setDistance("12311");
			area.setRoad_code(road_code);
			area.setRoad_name("京藏高速");
			area.setService_area_code("T1921");
			area.setService_area_img("http://img3.imgtn.bdimg.com/it/u=2419394602,4143314823&fm=21&gp=0.jpg");
			area.setService_area_name("京藏服务站");
			areas.add(area);
			area = new ServiceAreaListResult().new ServiceArea();
			area.setDistance("12311");
			area.setRoad_code(road_code);
			area.setRoad_name("京藏高速");
			area.setService_area_code("T1921");
			area.setService_area_img("http://img3.imgtn.bdimg.com/it/u=2419394602,4143314823&fm=21&gp=0.jpg");
			area.setService_area_name("京藏服务站");
			areas.add(area);
			area = new ServiceAreaListResult().new ServiceArea();
			area.setDistance("12311");
			area.setRoad_code(road_code);
			area.setRoad_name("京藏高速");
			area.setService_area_code("T1921");
			area.setService_area_img("http://img3.imgtn.bdimg.com/it/u=2419394602,4143314823&fm=21&gp=0.jpg");
			area.setService_area_name("京藏服务站");
			areas.add(area);
			area = new ServiceAreaListResult().new ServiceArea();
			area.setDistance("12311");
			area.setRoad_code(road_code);
			area.setRoad_name("京藏高速");
			area.setService_area_code("T1921");
			area.setService_area_img("http://img3.imgtn.bdimg.com/it/u=2419394602,4143314823&fm=21&gp=0.jpg");
			area.setService_area_name("京藏服务站");
			areas.add(area);
			area = new ServiceAreaListResult().new ServiceArea();
			area.setDistance("12311");
			area.setRoad_code(road_code);
			area.setRoad_name("京藏高速");
			area.setService_area_code("T1921");
			area.setService_area_img("http://img3.imgtn.bdimg.com/it/u=2419394602,4143314823&fm=21&gp=0.jpg");
			area.setService_area_name("京藏服务站");
			areas.add(area);
			area = new ServiceAreaListResult().new ServiceArea();
			area.setDistance("12311");
			area.setRoad_code(road_code);
			area.setRoad_name("京藏高速");
			area.setService_area_code("T1921");
			area.setService_area_img("http://img3.imgtn.bdimg.com/it/u=2419394602,4143314823&fm=21&gp=0.jpg");
			area.setService_area_name("京藏服务站");
			areas.add(area);
			result.setResults(areas);
			result.setStatus("Y");
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			int state = Utils.isCorrect(result, true);
			if (state == 0) {
				Intent intent = new Intent(ExpresswayListActivity.this, ServiceAreaListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("result", result.getResults());
				intent.putExtra("road_name", road_code + " " + road_name);
				intent.putExtra("hegiht", hegiht);
				startActivity(intent);
			} else if (state == 2) {
				CustomApp.app.exit(ExpresswayListActivity.this);
			}
			return false;
		}

	}
}
