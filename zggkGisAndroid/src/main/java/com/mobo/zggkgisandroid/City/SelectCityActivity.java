package com.mobo.zggkgisandroid.City;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.BaseActivity;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.CityListResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 选择城市
 * 
 * @author WuXiaoHao
 *
 */
public class SelectCityActivity extends BaseActivity {
	private TextView tvDCity;// 已定位的省份
	private TextView tvQuan;// 全国
	private GridView gridLately;// 最近访问的省份列表
	private GridView gridAll;// 所有省份列表

	private CityList dCity;// 当前定位的省份
	private List<CityList> latelyList;// 最近访问的省份列表
	private List<CityList> cityList;// 省份列表
	private CityList currentCity;// 当前所选的省份

	public final static int code = 9090;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentXml(R.layout.selectcity_activity_layout);
		init();
		new ThreadWithProgressDialog().Run(this, new GetCityDialogTask(), R.string.com_loading);
	}

	private void init() {
		imgLeft.setImageResource(R.drawable.rank_back_btn_sel);
		gridAll = (GridView) findViewById(R.id.grid_city_all);
		gridLately = (GridView) findViewById(R.id.grid_city_lately);
		tvQuan = (TextView) findViewById(R.id.tv_city_quan);
		tvDCity = (TextView) findViewById(R.id.tv_city_dinwei);

		tvDCity.getLayoutParams().width = (Utils.getWidth(this) - Utils.dip2px(this, 40)) / 3;
		tvQuan.getLayoutParams().width = (Utils.getWidth(this) - Utils.dip2px(this, 40)) / 3;

		latelyList = CustomApp.app.dbManager.queryCityList();
		gridLately.setAdapter(new GridViewAdapter(latelyList));
		if (latelyList.size() == 0) {
			gridLately.setVisibility(View.GONE);
			findViewById(R.id.ll_city_lately_title).setVisibility(View.GONE);
		}

		// 处理当前范围
		currentCity = (CityList) getIntent().getSerializableExtra("currentCity");
		if (currentCity != null) {
			tvTitle.setText("当前范围-" + currentCity.getProvince_name());
		} else {
			tvTitle.setText("当前范围-附近");
		}
		// 处理已定位省份
		dCity = (CityList) getIntent().getSerializableExtra("dCity");
		if (dCity != null) {
			tvDCity.setText(dCity.getProvince_name());
		} else {
			tvDCity.setVisibility(View.GONE);
			findViewById(R.id.ll_city_dinwei_title).setVisibility(View.GONE);
		}
		tvDCity.setOnClickListener(new OnClickListener() {// 当前定位的

			@Override
			public void onClick(View arg0) {
				selectCity(dCity);
			}
		});
		tvQuan.setOnClickListener(new OnClickListener() {// 全国

			@Override
			public void onClick(View arg0) {
				CityList cityList = new CityList();
				cityList.setProvince_code("123001");
				cityList.setProvince_name("全国");
				selectCity(cityList);
			}
		});
	}

	@Override
	protected void onClickLeft(View view) {
		super.onClickLeft(view);
		finish();
	}

	/**
	 * 获取城市列表
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class GetCityDialogTask implements ThreadWithProgressDialogTask {
		private CityListResult result;

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
				gridAll.setAdapter(new GridViewAdapter(cityList));
			} else if (state == 2) {
				CustomApp.app.exit(SelectCityActivity.this);
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
		private List<CityList> cityList;

		public GridViewAdapter(List<CityList> cityList) {
			this.cityList = cityList;
		}

		@Override
		public int getCount() {
			return cityList != null ? cityList.size() : 0;
		}

		@Override
		public CityList getItem(int arg0) {
			return cityList.get(arg0);
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
				arg1 = LayoutInflater.from(SelectCityActivity.this).inflate(R.layout.select_city_grid_item, arg2,
						false);
				viewHolder.tv = (TextView) arg1.findViewById(R.id.tv_city_grid_item);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			final CityList cityList = getItem(arg0);
			viewHolder.tv.setText(cityList.getProvince_name().indexOf("黑龙江")!=-1 ? "黑龙江"
					: cityList.getProvince_name().substring(0, 2));
			arg1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					selectCity(cityList);
				}
			});
			return arg1;
		}

		private class ViewHolder {
			TextView tv;
		}

	}

	/**
	 * 选择省份
	 */
	private void selectCity(CityList cityList) {
		Intent intent = new Intent();
		if (!"123001".equals(cityList.getProvince_code())) {
			if (latelyList.size() >= 6 && CustomApp.app.dbManager.getCity(cityList.getProvince_code()) == null) {
				CustomApp.app.dbManager.deleteCity(latelyList.get(latelyList.size() - 1));
			}
			cityList.setTime(System.currentTimeMillis());
			CustomApp.app.dbManager.updataCity(cityList);
		}
		intent.putExtra("currentCity", cityList);
		setResult(code, intent);
		finish();
	}
}
