package com.mobo.zggkgisandroid.Info;

import java.util.HashMap;
import java.util.List;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.BaseActivity;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.R.id;
import com.mobo.zggkgisandroid.R.layout;
import com.mobo.zggkgisandroid.R.menu;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.CityListResult;

import android.app.Activity;
import android.content.Intent;
import android.drm.DrmStore.RightsStatus;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 统计页面城市列表类
 * 
 * @author mobo
 *
 */
public class CityListActivity extends BaseActivity {
	// 保存所选城市
	private HashMap<String, CityList> hashMap = new HashMap<String, CityList>();
	// 城市列表gridview
	private GridView gridCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentXml(R.layout.activity_city_list);

		init();
		// 获取上个页面传递过来的所选页面

		CityList cityList = (CityList) getIntent().getSerializableExtra(
				"currentCity");
		hashMap.put(cityList.getProvince_code(), cityList);
		// 获取城市接口
		new ThreadWithProgressDialog().Run(this, new GetCityDialogTask(),
				R.string.com_loading);
	}

	/**
	 * 页面初始化
	 */

	private void init() {
		// 返回按钮
		imgLeft.setImageResource(R.drawable.rank_back_btn_sel);
		imgLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				CityListActivity.this.finish();

			}
		});
		tvTitle.setText("全国公路养护数据统计");
		tvRight.setText("确定");
		gridCity = (GridView) findViewById(R.id.citylist);

	}

	/**
	 * 获取城市列表
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class GetCityDialogTask implements ThreadWithProgressDialogTask {
		private CityListResult result;
		private List<CityList> cityList;// 省份列表

		@Override
		public boolean TaskMain() {
			result = CustomApp.app.connInteface.getCityList();
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
				cityList = result.getResults();
				CityList object = new CityList();
				// object.setProvince_code("123001");
				// object.setProvince_name("全国");
				// cityList.add(object);
				gridCity.setAdapter(new GridViewAdapter(cityList));

				if (cityList.size() > 0) {
					tvRight.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(CityListActivity.this,
									StatisticsActivity.class);
							intent.putExtra(
									"currentCity",
									hashMap.get(hashMap.keySet().iterator()
											.next()));
							startActivity(intent);

						}
					});
				}

			} else if (state == 2) {
				CustomApp.app.exit(CityListActivity.this);
			}
			return false;
		}
	}

	/**
	 * 列表适配器
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class GridViewAdapter extends BaseAdapter {
		private List<CityList> cityLists;

		public GridViewAdapter(List<CityList> cityList) {
			this.cityLists = cityList;
		}

		@Override
		public int getCount() {
			return cityLists != null ? cityLists.size() : 0;
		}

		@Override
		public CityList getItem(int arg0) {
			return cityLists.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			ViewHolder viewHolder;
			if (arg1 == null) {
				viewHolder = new ViewHolder();
				arg1 = LayoutInflater.from(CityListActivity.this).inflate(
						R.layout.traffic_grid_item, arg2, false);
				viewHolder.tv = (TextView) arg1
						.findViewById(R.id.tv_city_grid_item);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			final CityList cityList;
			viewHolder.tv.setTextColor(0xff333333);
			cityList = getItem(arg0);
			if (cityList.getProvince_name().contains("黑龙")) {
				viewHolder.tv.setText(cityList.getProvince_name().substring(0, 3));

			} else {
				viewHolder.tv.setText(cityList.getProvince_name().substring(0, 2));
			}

			if (hashMap.containsKey(cityList.getProvince_code())) {
				arg1.setBackgroundResource(R.drawable.city_item_selected_sel);
			} else {
				arg1.setBackgroundResource(R.drawable.city_item_sel);
			}
			arg1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					if ("1".equals(CustomApp.app.privilege)) {
						hashMap.clear();
						hashMap.put(cityList.getProvince_code(), cityList);
						notifyDataSetChanged();
					}else{
						CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "您无权查看其它省份");
					}
					

				}
			});
			return arg1;
		}

		private class ViewHolder {
			TextView tv;
		}

	}

}
