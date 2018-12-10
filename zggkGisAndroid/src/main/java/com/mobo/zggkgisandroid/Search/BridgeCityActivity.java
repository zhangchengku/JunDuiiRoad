package com.mobo.zggkgisandroid.Search;

import java.util.List;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.BaseActivity;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.MainActivity;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.AddKpi.AddKpiActivity;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.CityListResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 桥梁城市选择页面
 * 
 * @author WuXiaoHao
 *
 */
public class BridgeCityActivity extends BaseActivity {
	private GridView gridCity;// 城市列表
	private CityList cityList;// 当前选中的城市

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentXml(R.layout.info_layout);
		CustomApp.app.mActiveLists.add(this);
		init();
		cityList = (CityList) getIntent().getSerializableExtra("currentCity");
		new ThreadWithProgressDialog().Run(this, new GetCityDialogTask(), R.string.com_loading);
	}

	private void init() {
		imgLeft.setImageResource(R.drawable.rank_back_btn_sel);
		tvTitle.setText("桥梁数据查询");
		tvRight.setText("确定");
		gridCity = (GridView) findViewById(R.id.grid_traffic);
	}

	@Override
	protected void onClickLeft(View view) {
		super.onClickLeft(view);
		finish();
	}

	@Override
	protected void onClickRight(View view) {
		super.onClickRight(view);
		Intent intent = new Intent(this, BridgeChoiceActivity.class);
		startActivityForResult(intent, code);
	}

	public final static int code = 9527;

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == code && arg1 == BridgeChoiceActivity.code) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("showType", "bridge");
			intent.putExtra("currentCity", cityList);
			intent.putExtra("bridgeType", arg2.getStringExtra("bridgeType"));
			startActivity(intent);
		}
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
				gridCity.setAdapter(new GridViewAdapter(cityList));
			} else if (state == 2) {
				CustomApp.app.exit(BridgeCityActivity.this);
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
				arg1 = LayoutInflater.from(BridgeCityActivity.this).inflate(R.layout.traffic_grid_item, arg2, false);
				viewHolder.tv = (TextView) arg1.findViewById(R.id.tv_city_grid_item);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			final CityList cityList = getItem(arg0);
			viewHolder.tv.setText(cityList.getProvince_name().indexOf("黑龙江") != -1 ? "黑龙江"
					: cityList.getProvince_name().substring(0, 2));
			if (BridgeCityActivity.this.cityList.getProvince_code().equals(cityList.getProvince_code())) {
				arg1.setBackgroundResource(R.drawable.city_item_selected_sel);
			} else {
				arg1.setBackgroundResource(R.drawable.city_item_sel);
			}

			if (!"123004".equals(cityList.getProvince_code()) && !"123003".equals(cityList.getProvince_code())
					&& !"123002".equals(cityList.getProvince_code())) {
				arg1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						BridgeCityActivity.this.cityList = cityList;
						notifyDataSetChanged();
					}
				});
			}
			return arg1;
		}

		private class ViewHolder {
			TextView tv;
		}

	}
}
