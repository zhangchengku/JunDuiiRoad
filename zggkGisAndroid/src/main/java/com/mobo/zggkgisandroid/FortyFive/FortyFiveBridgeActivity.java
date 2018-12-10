package com.mobo.zggkgisandroid.FortyFive;

import java.util.ArrayList;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.MainActivity;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.City.SelectCityActivity;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.BridgeMarksResult;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.FortyFiveBridgeResult;
import com.mobo.zggkgisandroid.WebModel.FortyFiveBridgeResult.FortyFiveBridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 四五类桥分布
 * 
 * @author WuXiaoHao
 * 
 */
public class FortyFiveBridgeActivity extends Activity {
	private TextView tvRange;// 省份选择
	private EditText etSearch;// 搜索
	private ListView listView;

	private CityList currentCity;// 当前所选的省份

	private static final int code = 9712;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fortyfivebridge_activity_layout);
		CustomApp.app.mActiveLists.add(this);
		init();
		setRange();
	}

	private void init() {
		tvRange = (TextView) findViewById(R.id.tv_search_range);
		etSearch = (EditText) findViewById(R.id.et_search);
		listView = (ListView) findViewById(R.id.list_forty_five);

		currentCity = (CityList) getIntent()
				.getSerializableExtra("currentCity");

		tvRange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(FortyFiveBridgeActivity.this,
						SelectCityActivity.class);
				intent.putExtra("currentCity", currentCity);
				startActivityForResult(intent, code);
			}
		});
		etSearch.setOnKeyListener(new OnKeyListener() {// 搜索

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& arg2.getAction() == KeyEvent.ACTION_UP) {
					String content = etSearch.getText().toString().trim();
					if (content.equals(""))
						return true;
					new ThreadWithProgressDialog().Run(
							FortyFiveBridgeActivity.this, new DialogTask("",
									content), R.string.com_loading);
					return true;
				}
				return false;
			}
		});
	}

	/**
	 * 设置省份数据
	 */
	private void setRange() {
		if (currentCity == null)
			return;
		try {
			tvRange.setText(currentCity.getProvince_name().length() > 2 ? currentCity
					.getProvince_name().substring(0, 2) : currentCity
					.getProvince_name());
		} catch (Exception e) {
			tvRange.setText("");
		}
		if ("123001".equals(currentCity.getProvince_code())) {// 全国
			tvRange.setText("全国");
			currentCity.setProvince_name("全国");
		} else {// 省份

		}
		new ThreadWithProgressDialog().Run(this,
				new DialogTask(currentCity.getProvince_code(), ""),
				R.string.com_loading);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == code && resultCode == SelectCityActivity.code) {
			CityList cityList = (CityList) data
					.getSerializableExtra("currentCity");
			if (cityList == null)
				return;
			if (cityList != null
					&& currentCity != null
					&& cityList.getProvince_code().equals(
							currentCity.getProvince_code()))
				return;
			currentCity = cityList;
			setRange();
		}
	}

	/**
	 * 获取45类桥列表
	 * 
	 * @author WuXiaoHao
	 * 
	 */
	private class DialogTask implements ThreadWithProgressDialogTask {
		private String province_code;
		private String Search_str;

		private FortyFiveBridgeResult result;

		public DialogTask(String province_code, String Search_str) {
			this.province_code = province_code;
			this.Search_str = Search_str;
		}

		@Override
		public boolean TaskMain() {
			result = CustomApp.app.connInteface.fortyFiveBridge(province_code,
					Search_str);
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
				listView.setAdapter(new ListAdapter(result.getResults()));
			} else if (state == 2) {
				CustomApp.app.exit(FortyFiveBridgeActivity.this);
			}
			return false;
		}

	}

	/**
	 * 跳转危桥分布
	 * 
	 * @author WuXiaoHao
	 * 
	 */
	private class GetBridgeMarksUrl implements ThreadWithProgressDialogTask {
		private FortyFiveBridge fortyFiveBridge;
		private BridgeMarksResult result;

		public GetBridgeMarksUrl(FortyFiveBridge fortyFiveBridge) {
			this.fortyFiveBridge = fortyFiveBridge;
		}

		@Override
		public boolean TaskMain() {
			result = CustomApp.app.connInteface.getBridgeMarksUrl(
					CustomApp.app.token, fortyFiveBridge.getProvince_code(),"","",
					fortyFiveBridge.getCity_code(),
					fortyFiveBridge.getCounty_code(), "Y");
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			int state = Utils.isCorrect(result, true);
			if (state == 0 && result.getResults() != null) {
				Intent intent = new Intent(FortyFiveBridgeActivity.this,
						MainActivity.class);
				intent.putExtra("province_code",
						fortyFiveBridge.getProvince_code());
				intent.putExtra("province_name",
						fortyFiveBridge.getProvince_name());
				intent.putExtra("FortyFiveBridgeP_Result", result.getResults());
				startActivity(intent);
			} else if (state == 2) {
				CustomApp.app.exit(FortyFiveBridgeActivity.this);
			}
			return false;
		}

	}

	/**
	 * 适配器
	 * 
	 * @author WuXiaoHao
	 * 
	 */
	private class ListAdapter extends BaseAdapter {
		private ArrayList<FortyFiveBridge> arrayList;
		private int max;

		public ListAdapter(ArrayList<FortyFiveBridge> arrayList) {
			this.arrayList = arrayList;
			if (arrayList != null) {
				for (int i = 0; i < arrayList.size(); i++) {
					try {
						int m = Integer.parseInt(arrayList.get(i)
								.getBridge_num());
						if (m > max) {
							max = m;
						}
					} catch (Exception e) {
						arrayList.get(i).setBridge_num("0");
					}

				}
			}
		}

		@Override
		public int getCount() {
			return arrayList != null ? arrayList.size() : 0;
		}

		@Override
		public FortyFiveBridge getItem(int arg0) {
			return arrayList.get(arg0);
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
				arg1 = LayoutInflater.from(FortyFiveBridgeActivity.this)
						.inflate(R.layout.fortyfive_item_layout, arg2, false);
				viewHolder.tvRank = (TextView) arg1
						.findViewById(R.id.tv_fortyfive_rank);
				viewHolder.tvName = (TextView) arg1
						.findViewById(R.id.tv_fortyfive_name);
				viewHolder.tvCount = (TextView) arg1
						.findViewById(R.id.tv_fortyfive_count);
				viewHolder.rankView = (FortyFiveRankView) arg1
						.findViewById(R.id.v_fortyfive_progress);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			final FortyFiveBridge fortyFiveBridge = getItem(arg0);
			if (fortyFiveBridge != null) {
				viewHolder.tvRank.setText((arg0 + 1) + "");
				if (!Utils.isNull(fortyFiveBridge.getCounty_code())) {
					viewHolder.tvName.setText(fortyFiveBridge.getCounty_name());
				} else if (!Utils.isNull(fortyFiveBridge.getCity_code())) {
					viewHolder.tvName.setText(fortyFiveBridge.getCity_name());
				} else {
					viewHolder.tvName.setText(fortyFiveBridge
							.getProvince_name());

				}

				viewHolder.tvCount.setText(fortyFiveBridge.getBridge_num());
				viewHolder.rankView.setMaxCount(max);
				viewHolder.rankView.setDrawCircle(false);
				viewHolder.rankView.setCount(Integer.parseInt(fortyFiveBridge
						.getBridge_num()));
				arg1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (!Utils.isNull(fortyFiveBridge.getProvince_code())) {
							new ThreadWithProgressDialog().Run(
									FortyFiveBridgeActivity.this,
									new GetBridgeMarksUrl(fortyFiveBridge),
									R.string.com_loading);
						}
					}
				});
			}
			return arg1;
		}

		private class ViewHolder {
			TextView tvRank;// 排名
			TextView tvName;// 名字
			TextView tvCount;// 数量
			FortyFiveRankView rankView;// 排名进度条

		}
	}
}
