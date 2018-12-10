package com.mobo.zggkgisandroid.Info;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xclcharts.chart.PieData;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Info.GetYearPopWindowTwo.OnClickYear;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.Utils.constant;
import com.mobo.zggkgisandroid.View.ListVIewForScrollView;
import com.mobo.zggkgisandroid.View.PieChart01View;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.RoadTrafficResult;
import com.mobo.zggkgisandroid.WebModel.RoadTrafficResult.RoadTraffic;
import com.mobo.zggkgisandroid.WebModel.RoadTrafficResult.RoadTraffic.DataCircle;
import com.mobo.zggkgisandroid.WebModel.RoadTrafficResult.RoadTraffic.DataCircle.DataCircleData;
import com.mobo.zggkgisandroid.WebModel.RoadTrafficResult.RoadTraffic.TableData;
import com.mobo.zggkgisandroid.photoView.PhotoView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 路线统计,路面统计
 * 
 * @author WuXiaoHao
 *
 */
public class StatisticsActivityDetailB extends Activity {
	private TextView tvTitle;// 标题
	private TextView tvTableTitle;// 表格标题
	public TextView tvYear;// 年份
	private LinearLayout tvGroup;// 内容布局
	private TextView tvKilometre, tvPercentage;

	private ListView listView;
	private PieChart01View piechart_one;// 饼图

	private String tag = "";// ROUTE_STATISTIC路线统计、
	// PAVEMENT_STATISTICS路面统计
	// BRIDGE_LEVEL_STATISTICS桥梁等级统计
	// TUNNEL_LEVEL_STATISTICS隧道等级统计
	private CityList currentCity;// 城市
	private String indicator_id;// 指标id
	private String indicator_value;// 指标名称
	private ArrayList<RoadTraffic> roadTraffics;// 数据
	private HashMap<String, Integer> color = new HashMap<String, Integer>();// 颜色集合
	private ArrayList<String> year;
	private int index = 0;
	private MyAdatper adatper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics_layout);
		CustomApp.app.mActiveLists.add(this);
		Intent intent = getIntent();
		indicator_id = intent.getStringExtra("indicator_id");
		currentCity = (CityList) intent.getSerializableExtra("currentCity");
		indicator_value = intent.getStringExtra("indicator_value");
		tag = getIntent().getStringExtra("tag");
		init();
		new ThreadWithProgressDialog().Run(this, new MyDialogTask(), R.string.com_loading);
	}

	public void init() {
		tvGroup = (LinearLayout) findViewById(R.id.statistics_container);
		View inflate = getLayoutInflater().inflate(R.layout.route_statistics_activity_layout, null);
		tvGroup.addView(inflate);
		tvYear = (TextView) findViewById(R.id.statistics_year);
		tvTitle = (TextView) findViewById(R.id.statistics_title);
		tvKilometre = (TextView) findViewById(R.id.tv_route_statistics_kilometre);
		tvPercentage = (TextView) findViewById(R.id.tv_route_statistics_percentage);

		tvTableTitle = (TextView) findViewById(R.id.tv_route_statistics_title);
		piechart_one = (PieChart01View) findViewById(R.id.piechart_one_route_statistics);
		listView = (ListView) findViewById(R.id.lv_route_statistics);
		if (tag.equals("ROUTE_STATISTIC") || tag.equals("PAVEMENT_STATISTICS")) {
			tvTableTitle.setText("路况等级，" + indicator_id);
			tvKilometre.setText("长度 (km)");
			tvTitle.setText((currentCity.getProvince_name().indexOf("黑龙江") != -1 ? "黑龙江" : currentCity.getProvince_name().substring(0, 2))
					+ indicator_value + "统计");
		} else if (tag.equals("BRIDGE_LEVEL_STATISTICS")) {
			tvKilometre.setText("数量 (座)");

			tvTableTitle.setText("桥梁等级");
			if ("全部".equals(indicator_value)) {
				tvTitle.setText((currentCity.getProvince_name().indexOf("黑龙江") != -1 ? "黑龙江" : currentCity.getProvince_name().substring(0, 2))
						+ "桥梁等级统计");
			} else {
				tvTitle.setText((currentCity.getProvince_name().indexOf("黑龙江") != -1 ? "黑龙江" : currentCity.getProvince_name().substring(0, 2))
						+ indicator_value + "等级统计");
			}
		} else {
			tvKilometre.setText("数量 (个)");
			tvTableTitle.setText("隧道等级");
			if ("全部".equals(indicator_value)) {
				tvTitle.setText((currentCity.getProvince_name().indexOf("黑龙江") != -1 ? "黑龙江" : currentCity.getProvince_name().substring(0, 2))
						+ "隧道等级统计");
			} else {
				tvTitle.setText((currentCity.getProvince_name().indexOf("黑龙江") != -1 ? "黑龙江" : currentCity.getProvince_name().substring(0, 2))
						+ indicator_value + "等级统计");
			}
		}
		// 隐藏不需要的布局
		findViewById(R.id.static_linearlayout).setVisibility(View.GONE);
		// 添加监听
		tvYear.setOnClickListener(new OnClickListener() {// 年份

			@Override
			public void onClick(View v) {
				if (year != null) {
					GetYearPopWindowTwo popWindowTwo = new GetYearPopWindowTwo(StatisticsActivityDetailB.this, year, tvYear);
					popWindowTwo.showPop();
					popWindowTwo.setOnClickYear(new OnClickYear() {

						@Override
						public void onClick(int position) {
							if (index == position)
								return;
							index = position;
							tvYear.setText(roadTraffics.get(index).getYear());
							setListView(roadTraffics.get(index).getTable_data());
							setChartView(roadTraffics.get(index).getData_circle(), roadTraffics.get(index).getTable_data());
						}
					});
				}
			}
		});
		findViewById(R.id.statistics_back).setOnClickListener(new OnClickListener() {// 返回按钮

					@Override
					public void onClick(View v) {
						finish();
					}
				});
	}

	/**
	 * 获取数据
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class MyDialogTask implements ThreadWithProgressDialogTask {
		RoadTrafficResult result;

		@Override
		public boolean TaskMain() {
			result = CustomApp.app.connInteface.roadTraffic(tag, currentCity.getProvince_code(), indicator_id);
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			int state = Utils.isCorrect(result, true);
			if (state == 0 && result.getResults() != null && result.getResults().size() > 0) {
				roadTraffics = result.getResults();
				year = new ArrayList<String>();
				for (int i = 0; i < roadTraffics.size(); i++) {
					year.add(roadTraffics.get(i).getYear());
				}

				tvYear.setText(roadTraffics.get(index).getYear());

				color.put("1", 0xff78e039);
				color.put("2", 0xff61fbe7);
				color.put("3", 0xfff4ee73);
				color.put("4", 0xffffaa52);
				color.put("5", 0xfffa5402);

				setListView(roadTraffics.get(index).getTable_data());
				setChartView(roadTraffics.get(index).getData_circle(), roadTraffics.get(index).getTable_data());

			} else if (state == 2) {
				CustomApp.app.exit(StatisticsActivityDetailB.this);
			}
			return false;
		}

	}

	/**
	 * 显示list数据
	 * 
	 * @param tableDatas
	 */
	private void setListView(ArrayList<TableData> tableDatas) {
		if (adatper == null) {
			adatper = new MyAdatper(tableDatas);
			listView.setAdapter(adatper);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					if (tag.equals("ROUTE_STATISTIC") || tag.equals("PAVEMENT_STATISTICS")) {
						Intent intent = new Intent(StatisticsActivityDetailB.this, TrafficIndexActivity.class);
						intent.putExtra("currentCity", currentCity);
						intent.putExtra("indicator_id", indicator_id);
						intent.putExtra("year", tvYear.getText().toString().replace("年", ""));
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					} else if (tag.equals("BRIDGE_LEVEL_STATISTICS")) {
						Intent intent = new Intent(StatisticsActivityDetailB.this, MapStatisticsActivity.class);
						intent.putExtra("currentCity", currentCity);
						intent.putExtra("year", tvYear.getText().toString().replace("年", ""));
						intent.putExtra("key", constant.BRIDGE_CLASS);
						intent.putExtra("type", indicator_value);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						if (!"全部".equals(indicator_value)) {
							startActivity(intent);
						}

					} else {

						Intent intent = new Intent(StatisticsActivityDetailB.this, MapStatisticsActivity.class);
						intent.putExtra("currentCity", currentCity);
						intent.putExtra("key", constant.TUNNEL_CLASS);
						intent.putExtra("year", tvYear.getText().toString().replace("年", ""));
						intent.putExtra("type", indicator_value);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						if (!"全部".equals(indicator_value)) {
							startActivity(intent);
						}

					}

				}
			});
		} else {
			adatper.notifyDataSetChanged(tableDatas);
		}
	}

	/**
	 * 显示饼图
	 * 
	 * @param dataCircles
	 * @param arrayList
	 */
	private void setChartView(ArrayList<DataCircle> dataCircles, ArrayList<TableData> arrayList) {
		piechart_one.chartData.clear();
		if (dataCircles != null) {
			for (int i = 0; i < dataCircles.size(); i++) {
				DataCircle dataCircle = dataCircles.get(i);
				if (dataCircle == null)
					continue;
				DataCircleData dataCircleData = dataCircle.getData_circle_data();
				if (dataCircleData == null)
					continue;
				String label;
				try {
					label = dataCircleData.getData_circle_name() + "：" + Double.valueOf(dataCircleData.getData_circle_value()) + "%";
				} catch (Exception e) {
					label = dataCircleData.getData_circle_name() + "：" + dataCircleData.getData_circle_value() + "%";
				}
				double percent;
				try {
					percent = Double.valueOf(dataCircleData.getData_circle_value());
				} catch (Exception e) {
					percent = 0;
				}
				try {

				} catch (Exception e) {
					// TODO: handle exception
				}
				int c;
				try {
					c = color.get(arrayList.get(i).getIndicator_grade());
				} catch (Exception e) {
					c = 0xfffa5402;
				}

				PieData pieData = new PieData(label, percent, c);
				// if (percent < 2) {
				// pieData.setItemLabelRotateAngle(45.f);
				// }
				piechart_one.chartData.add(pieData);
			}
			piechart_one.initView();
		}
	}

	/**
	 * 适配器
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class MyAdatper extends BaseAdapter {
		private ArrayList<TableData> tableDatas;

		public MyAdatper(ArrayList<TableData> tableDatas) {
			this.tableDatas = tableDatas;

		}

		public void notifyDataSetChanged(ArrayList<TableData> tableDatas) {
			this.tableDatas = tableDatas;
			notifyDataSetChanged();

		}

		@Override
		public int getCount() {
			return tableDatas != null ? tableDatas.size() : 0;
		}

		@Override
		public TableData getItem(int arg0) {
			return tableDatas.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(StatisticsActivityDetailB.this).inflate(R.layout.route_statistics_activity_item_layout, parent,
						false);
				viewHolder.lvGrade = (ImageView) convertView.findViewById(R.id.img_route_statistics_item_indicator_grade);
				viewHolder.tvGrade = (TextView) convertView.findViewById(R.id.tv_route_statistics_item_indicator_grade);
				viewHolder.tvKilometre = (TextView) convertView.findViewById(R.id.tv_route_statistics_item_kilometre);
				viewHolder.tvPercentage = (TextView) convertView.findViewById(R.id.tv_route_statistics_item_percentage);
				viewHolder.vGrade = convertView.findViewById(R.id.ll_route_statistics_item_indicator_grade);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			if (position % 2 == 0) {
				viewHolder.tvKilometre.setBackgroundColor(0xffd0d8e8);
				viewHolder.tvPercentage.setBackgroundColor(0xffd0d8e8);
				viewHolder.vGrade.setBackgroundColor(0xffd0d8e8);
			} else {
				viewHolder.tvKilometre.setBackgroundColor(0xffe9edf4);
				viewHolder.tvPercentage.setBackgroundColor(0xffe9edf4);
				viewHolder.vGrade.setBackgroundColor(0xffe9edf4);
			}
			TableData tableData = getItem(position);
			if (!color.containsKey(tableData.getIndicator_grade())) {
				viewHolder.lvGrade.setBackgroundColor(0xfffa5402);
			} else {
				viewHolder.lvGrade.setBackgroundColor(color.get(tableData.getIndicator_grade()));
			}
			viewHolder.tvGrade.setText(tableData.getIndicator_level_name());
			viewHolder.tvKilometre.setText(Utils.isNull(tableData.getIndicator_number()) ? "0" : tableData.getIndicator_number());
			viewHolder.tvPercentage.setText(Utils.isNull(tableData.getIndicator_percentage()) ? "0" : tableData.getIndicator_percentage());

			return convertView;
		}

		private class ViewHolder {
			View vGrade;// 等级布局
			TextView tvGrade;// 等级文字
			ImageView lvGrade;// 等级图片
			TextView tvKilometre;// 公里
			TextView tvPercentage;// 百分比

		}

	}
}
