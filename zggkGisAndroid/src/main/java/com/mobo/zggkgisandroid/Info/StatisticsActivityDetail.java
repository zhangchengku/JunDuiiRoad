package com.mobo.zggkgisandroid.Info;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BarData;
import org.xclcharts.chart.PieData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AbsListView.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.AddKpi.AddKpiActivity;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.Utils.constant;
import com.mobo.zggkgisandroid.View.BarChart03View;
import com.mobo.zggkgisandroid.View.ListVIewForScrollView;
import com.mobo.zggkgisandroid.View.PieChart01View;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.StatisticsModel;
import com.mobo.zggkgisandroid.WebModel.StatisticsModel.ResultsBean;
import com.mobo.zggkgisandroid.WebModel.StatisticsModel.ResultsBean.ColomDataBean;
import com.mobo.zggkgisandroid.WebModel.StatisticsModel.ResultsBean.ColomDataBean.DataValuesColomBean;
import com.mobo.zggkgisandroid.WebModel.StatisticsModel.ResultsBean.DataCircleBean;
import com.mobo.zggkgisandroid.WebModel.StatisticsModelTwo;
import com.mobo.zggkgisandroid.WebModel.StatisticsModelTwo.ResultsBean.IndicatorInfoBean;
import com.mobo.zggkgisandroid.WebModel.StatisticsModelTwo.ResultsBean.LineBarBean;
import com.mobo.zggkgisandroid.photoView.PhotoView;

;

//统计界面

public class StatisticsActivityDetail extends Activity {

	private TextView statisticsTitle;// 标题
	private ListVIewForScrollView statisticslv; // 表格
	private statistics_grid_adapter statistics_grid_adapter;// 表格适配器
	private LinearLayout statistics_container;
	public TextView statistics_year;// 年份
	private PhotoView statistics_im;
	private TextView statistics_message;
	private ArrayList<String> listdata;
	private ThreadWithProgressDialog threadWithProgressDialog;
	private ThreadWithProgressDialogTask dialogTask;
	private StatisticsModel statisticsModel;
	private String key;
	private List<ResultsBean> results;
	private statistics_lv_adapter statistics_lv_adapter;
	public static final String TECH_LEVEL = "公路技术等级统计";
	public static final String ROAD_FACE_TYPE = "公路路面类型统计";
	public static final String ADMIN_LEVEL = "公路行政等级统计";
	public static final String BRIDGE_CLASS = "公路桥梁分类统计";
	public static final String TUNNEL_CLASS = "公路隧道分类统计";
	public static final String RANK_LEVEL = "路况等级统计";

	private String temp;// 临时变量
	private CityList currentCity;// 当前城市
	private PieChart01View piechart_one;// 扇形图

	private BarChart03View barChart03View;// 柱状图
	private static final int TYPEONE = 1;// view类型
	private static final int TYPETWO = 2;
	private View inflate1;
	private View inflate2;
	private ListView inflate2listView;
	private ListView child_one_lv1;
	private ListView child_one_lv2;
	List<com.mobo.zggkgisandroid.WebModel.StatisticsModelTwo.ResultsBean> results2;
	private int type;
	private LinearLayout static_linearlayout;
	private StatisticsModelTwo newstatistical;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		CustomApp.app.mActiveLists.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.statistics_layout);
		key = getIntent().getStringExtra("key");
		currentCity = (CityList) getIntent()
				.getSerializableExtra("currentCity");

		if (key.equals(constant.ADMIN_LEVEL)) {
			type = TYPEONE;

			if (currentCity.getProvince_code().equals("123001")) {
				temp = "全国" + ADMIN_LEVEL;

			} else {
				temp = currentCity.getProvince_name().substring(0, 2)
						+ ADMIN_LEVEL;
			}

		}
		if (key.equals(constant.BRIDGE_CLASS)) {
			type = TYPEONE;
			if (currentCity.getProvince_code().equals("123001")) {
				temp = "全国" + BRIDGE_CLASS;

			} else {
				temp = currentCity.getProvince_name().substring(0, 2)
						+ BRIDGE_CLASS;
			}

		}
		if (key.equals(constant.ROAD_FACE_TYPE)) {
			type = TYPEONE;

			if (currentCity.getProvince_code().equals("123001")) {
				temp = "全国" + ROAD_FACE_TYPE;

			} else {
				temp = currentCity.getProvince_name().substring(0, 2)
						+ ROAD_FACE_TYPE;
			}

		}
		if (key.equals(constant.TECH_LEVEL)) {
			type = TYPEONE;

			if (currentCity.getProvince_code().equals("123001")) {
				temp = "全国" + TECH_LEVEL;

			} else {
				temp = currentCity.getProvince_name().substring(0, 2)
						+ TECH_LEVEL;
			}

		}
		if (key.equals(constant.TUNNEL_CLASS)) {
			type = TYPEONE;
			if (currentCity.getProvince_code().equals("123001")) {
				temp = "全国" + TUNNEL_CLASS;

			} else {
				temp = currentCity.getProvince_name().substring(0, 2)
						+ TUNNEL_CLASS;
			}

		}
		if (key.equals(constant.RANK_LEVEL)) {
			type = TYPETWO;
			if (currentCity.getProvince_code().equals("123001")) {
				temp = "全国" + RANK_LEVEL;

			} else {
				temp = currentCity.getProvince_name().substring(0, 2)
						+ RANK_LEVEL;
			}

		}
		threadWithProgressDialog = new ThreadWithProgressDialog();
		init(type);
		startThread(type);

	}

	public void startThread(int type) {

		switch (type) {
		case TYPEONE:
			threadWithProgressDialog.Run(this, new MyTask(),
					R.string.com_loading);

			break;
		case TYPETWO:
			threadWithProgressDialog.Run(this, new NewMyTask(),
					R.string.com_loading);

		default:
			break;
		}

	}

	class NewMyTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {

			newstatistical = CustomApp.app.connInteface.newstatistical("",
					currentCity.getProvince_code());
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {

			int correct = Utils.isCorrect(newstatistical, true);
			if (correct == 0) {
				results2 = newstatistical.getResults();
				for (int i = 0; i < results2.size(); i++) {
					listdata.add(results2.get(i).getIndicator_year());
				}
				com.mobo.zggkgisandroid.WebModel.StatisticsModelTwo.ResultsBean resultsBean = results2
						.get(0);
				SetDataTwo(results2.get(0));

			}
			return false;
		}
	}

	public void init(int type) {
		statistics_year = (TextView) findViewById(R.id.statistics_year);

		listdata = new ArrayList<String>();

		// for (int i = 0; i < 5; i++) {
		// listdata.add(2010 + i + "年");
		//
		// }
		// ss = (PieChart01View) findViewById(R.id.piechart);
		statistics_year.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new GetYearPopWindow(StatisticsActivityDetail.this, listdata);

			}
		});
		ImageView findViewById = (ImageView) findViewById(R.id.statistics_back);
		findViewById.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StatisticsActivityDetail.this.finish();

			}
		});
		statisticsTitle = (TextView) findViewById(R.id.statistics_title);
		statisticsTitle.setText(temp);
		statistics_container = (LinearLayout) findViewById(R.id.statistics_container);
		static_linearlayout = (LinearLayout) findViewById(R.id.static_linearlayout);

		if (type == 1) {
			findViews();

		} else if (type == 2) {

			findviewTwo();

		}

	}

	class MyTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub

			statisticsModel = CustomApp.app.connInteface.statistical(key,
					currentCity.getProvince_code());
			// Log.v(key, statistical.toString());
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			// TODO Auto-generated method stub
			// ss.initView();
			int correct = Utils.isCorrect(statisticsModel, true);
			if (correct == 0) {
				results = statisticsModel.getResults();
				if (results != null && results.size() > 0) {

					for (int i = 0; i < results.size(); i++) {
						listdata.add(results.get(i).getYear());
					}

					SetDataone(results.get(0));

				}

			} else {

			}
			return false;
		}
	}

	public void SetDataone(ResultsBean resultsBean) {
		statistics_year.setText(resultsBean.getYear() + "年");
		// statisticsTitle.setText(temp);

		piechart_one.chartData.clear();

		try {
			for (int i = 0; i < resultsBean.getData_circle().size(); i++) {
				piechart_one.chartData.add(new PieData(resultsBean
						.getData_circle().get(i).getData_circle_data()
						.getData_circle_name()
						+ "："
						+ resultsBean.getData_circle().get(i)
								.getData_circle_data().getData_circle_value()
						+ "%", Double.valueOf(resultsBean.getData_circle()
						.get(i).getData_circle_data().getData_circle_value()),
						constant.color[i % constant.color.length]));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}

		piechart_one.initView();
		// piechart_one.refreshChart();
		List<ColomDataBean> colom_data = resultsBean.getColom_data();
		for (int i = 0; i < colom_data.size(); i++) {
			if (i > 1) {
				break;
			}

			List<DataValuesColomBean> data_values_colom = colom_data.get(i)
					.getData_values_colom();
			List<DataValuesColomBean> temp = new ArrayList<StatisticsModel.ResultsBean.ColomDataBean.DataValuesColomBean>();
			temp.addAll(data_values_colom);
			DataValuesColomBean dataValuesColomBean = new DataValuesColomBean();
			dataValuesColomBean.setData_name(colom_data.get(i)
					.getData_key_name());
			dataValuesColomBean.setData_value(colom_data.get(i)
					.getData_value_name());
			temp.add(0, dataValuesColomBean);
			if (i == 0) {
				listOneAdapter listOneAdapter = new listOneAdapter(
						StatisticsActivityDetail.this, 0, temp, i, resultsBean);
				child_one_lv1.setAdapter(listOneAdapter);
				listOneAdapter.notifyDataSetChanged();

			}
			if (i == 1) {
				listOneAdapter listOneAdapter = new listOneAdapter(
						StatisticsActivityDetail.this, 0, temp, i, resultsBean);
				child_one_lv2.setAdapter(listOneAdapter);
				listOneAdapter.notifyDataSetChanged();
			}

			child_one_lv1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(StatisticsActivityDetail.this,
							MapStatisticsActivity.class);
					intent.putExtra("year", statistics_year.getText()
							.toString().replace("年", ""));
					intent.putExtra("currentCity", currentCity);
					intent.putExtra("key", key);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);

				}
			});
			child_one_lv2.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(StatisticsActivityDetail.this,
							MapStatisticsActivity.class);
					intent.putExtra("year", statistics_year.getText()
							.toString().replace("年", ""));
					intent.putExtra("currentCity", currentCity);
					intent.putExtra("key", key);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);

				}
			});

		}
		// statistics_lv_adapter = new statistics_lv_adapter(
		// StatisticsActivityDetail.this, 0, resultsBean.getColom_data());
		// statisticslv.setAdapter(statistics_lv_adapter);
		/*
		 * Bitmap handlerBitmap = PlayImage.handlerBitmap(resultsBean
		 * .getData_circle_img()); statistics_im.setImageBitmap(handlerBitmap);
		 * 
		 * statistics_message.setVisibility(View.VISIBLE);
		 * statistics_message.setText(getString1(resultsBean));
		 */

	}

	// #00BFFF

	public void SetDataTwo(
			com.mobo.zggkgisandroid.WebModel.StatisticsModelTwo.ResultsBean resultsBean1) {
		barChart03View.chartLabels.clear();
		barChart03View.chartData.clear();
		barChart03View.mCustomLineDataset.clear();

		List<LineBarBean> line_bar = resultsBean1.getLine_bar();
		List<Double> dataSeries = new ArrayList<Double>();
		List<Integer> dataColorA = new LinkedList<Integer>();
		for (int i = 0; i < line_bar.size(); i++) {
			barChart03View.chartLabels.add(resultsBean1.getLine_bar().get(i)
					.getLine_bar_data().getLine_bar_name());
			dataSeries.add(Double.valueOf(resultsBean1.getLine_bar().get(i)
					.getLine_bar_data().getLine_bar_value()));
			for (int j = 0; j < resultsBean1.getIndicator_info().size(); j++) {
				if (resultsBean1
						.getLine_bar()
						.get(i)
						.getLine_bar_data()
						.getLine_bar_name()
						.toLowerCase()
						.trim()
						.equals(resultsBean1.getIndicator_info().get(j)
								.getIndicator().toLowerCase().trim())) {
					String text = resultsBean1.getIndicator_info().get(j)
							.getIndicator_grade();
					// 优良中等差
					if ("1".equals(text)) {
						dataColorA.add(0xff78e039);
					} else if ("2".equals(text)) {
						dataColorA.add(0xff61fbe7);
					} else if ("3".equals(text)) {
						dataColorA.add(0xfff4ee73);
					} else if ("4".equals(text)) {
						dataColorA.add(0xffffaa52);
					} else {
						dataColorA.add(0xfffa5402);
					}
					break;
				} else {
					if (j == resultsBean1.getIndicator_info().size() - 1) {
						dataColorA.add(Color.BLUE);
					}
					;
				}

			}

		}
		barChart03View.chartData.add(new BarData("", dataSeries, dataColorA,
				Color.BLUE));
		barChart03View.initView();
		statistics_year.setText(resultsBean1.getIndicator_year() + "年");
		final List<IndicatorInfoBean> indicator_info = resultsBean1
				.getIndicator_info();
		ListAdapter adapter = new ListAdapter(indicator_info);
		inflate2listView.setAdapter(adapter);
		inflate2listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent cityList = new Intent(StatisticsActivityDetail.this,
						TrafficIndexActivity.class);
				cityList.putExtra("currentCity", currentCity);
				cityList.putExtra("indicator_id", indicator_info.get(position)
						.getIndicator());
				cityList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(cityList);
				// indicator_id = getIntent().getStringExtra("indicator_id");
				// cityList = (CityList)
				// getIntent().getSerializableExtra("currentCity");

			}
		});
		adapter.notifyDataSetChanged();

	}

	/*
	 * private String getString1(ResultsBean resultsBean) {
	 * 
	 * String temp = ""; String temp2 = "";
	 * 
	 * int max = getMaxLengh(resultsBean);
	 * 
	 * for (int i = 0; i < resultsBean.getColom_data().size(); i++) {
	 * 
	 * if (key.equals(constant.TECH_LEVEL) ||
	 * key.equals(constant.ROAD_FACE_TYPE) || key.equals(constant.ADMIN_LEVEL))
	 * { temp2 = "公里";
	 * 
	 * } else {
	 * 
	 * if (i <= 4) { temp2 = "座";
	 * 
	 * } else { temp2 = "延米"; } } temp +=
	 * resultsBean.getColom_data().get(i).getData_values_colom() .getData_name()
	 * + getduiqiString("：", max - resultsBean.getColom_data().get(i)
	 * .getData_values_colom().getData_name() .length()) +
	 * resultsBean.getColom_data().get(i).getData_values_colom()
	 * .getData_value() + temp2 + "\n";
	 * 
	 * } ; return temp.substring(0, temp.length() - 1);
	 * 
	 * }
	 */

	/*
	 * private int getMaxLengh(ResultsBean resultsBean) {
	 * 
	 * int max = 0; for (int i = 0; i < resultsBean.getColom_data().size(); i++)
	 * {
	 * 
	 * if (resultsBean.getColom_data().get(i).getData_values_colom()
	 * .getData_name().length() > max) {
	 * 
	 * max = resultsBean.getColom_data().get(i).getData_values_colom()
	 * .getData_name().length(); }
	 * 
	 * }
	 * 
	 * return max;
	 * 
	 * }
	 */

	private String getduiqiString(String daiduiqi, int chalengh) {

		if (Utils.isNull(daiduiqi)) {
			return null;

		} else {

			for (int i = 0; i <= chalengh; i++) {
				daiduiqi = StatisticsActivityDetail.this.getResources()
						.getString(R.string.nullge) + daiduiqi;
			}

			Log.v("长度", daiduiqi.length() + "");
			return daiduiqi;

		}

	}

	private void findViews() {
		static_linearlayout.setVisibility(View.GONE);
		inflate1 = getLayoutInflater().inflate(
				R.layout.statistics_layout_child_one, null);
		piechart_one = (PieChart01View) inflate1
				.findViewById(R.id.piechart_one);
		child_one_lv1 = (ListView) inflate1
				.findViewById(R.id.statistics_layout_child_one_lv1);
		child_one_lv2 = (ListView) inflate1
				.findViewById(R.id.statistics_layout_child_one_lv2);

		// statisticslv = (ListVIewForScrollView)
		// findViewById(R.id.statistics_lv);

		// Lv_Data lv_Data = new Lv_Data();
		// lv_Data.data = new ArrayList<String>();
		// for (int i = 0; i < 3; i++) {
		// lv_Data.data.add(i + "测试数据");
		//
		// }
		// for (int i = 0; i < 7; i++) {
		// Gv_Data gv_Data = new Gv_Data();
		// gv_Data.setLv_data(lv_Data);
		// listGvData.add(gv_Data);
		//
		// }
		//
		// statisticsGv.setNumColumns(listGvData.size());
		// statistics_grid_adapter = new statistics_grid_adapter(this,
		// R.layout.statistics_gridview_layout, listGvData);
		// statisticsGv.setAdapter(statistics_grid_adapter);
		// statistics_im = (PhotoView)
		// inflate1.findViewById(R.id.statistics_im);
		// statistics_message = (TextView) inflate1
		// .findViewById(R.id.statistics_message);
		statistics_container.addView(inflate1);
	}

	private void findviewTwo() {
		static_linearlayout.setVisibility(View.GONE);
		inflate2 = getLayoutInflater().inflate(
				R.layout.statistics_layout_child_two, null);
		barChart03View = (BarChart03View) inflate2
				.findViewById(R.id.statistics_layout_child_two_bar_chart);
		inflate2listView = (ListView) inflate2
				.findViewById(R.id.list_assessment);
		statistics_container.addView(inflate2);

	}

	private class listOneAdapter extends ArrayAdapter<DataValuesColomBean> {

		private Context context;
		private List<DataValuesColomBean> listdata;
		private int belong;
		private ResultsBean resultsBean;

		public listOneAdapter(Context context, int resource,
				List<DataValuesColomBean> objects, int belong,
				ResultsBean resultsBean) {
			super(context, resource, objects);

			this.context = context;
			this.listdata = objects;
			this.belong = belong;
			this.resultsBean = resultsBean;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater
						.from(StatisticsActivityDetail.this).inflate(
								R.layout.child_one_item, parent, false);
				viewHolder.tvIndex = (TextView) convertView
						.findViewById(R.id.child_index);
				viewHolder.tvIndexName = (TextView) convertView
						.findViewById(R.id.child_name);
				viewHolder.child_proportion = (TextView) convertView
						.findViewById(R.id.child_proportion);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			// LayoutParams layoutParams = (LayoutParams) convertView
			// .getLayoutParams();
			if (belong == 1) {
				viewHolder.child_proportion.setVisibility(View.GONE);
			}
			if (position == 0) {
				viewHolder.tvIndex.setBackgroundColor(0xff4f81ff);

				viewHolder.tvIndexName.setBackgroundColor(0xff4f81ff);
				viewHolder.child_proportion.setBackgroundColor(0xff4f81ff);
				viewHolder.tvIndex.setGravity(Gravity.CENTER);
				viewHolder.child_proportion.setGravity(Gravity.CENTER);
				viewHolder.tvIndexName.setGravity(Gravity.CENTER);
				viewHolder.tvIndex.setTextColor(Color.WHITE);
				viewHolder.tvIndexName.setTextColor(Color.WHITE);
				viewHolder.child_proportion.setTextColor(Color.WHITE);

				// viewHolder.tvIndex.setPadding(left, top, right, bottom)
				// layoutParams.height = Utils.dip2px(
				// StatisticsActivityDetail.this, 30);
				// viewHolder.tvIndex.ge
				// viewHolder.tvIndex.setLayoutParams(layoutParams);
			} else {
				viewHolder.tvIndex.setGravity(Gravity.LEFT
						| Gravity.CENTER_VERTICAL);
				viewHolder.tvIndexName.setGravity(Gravity.CENTER_VERTICAL
						| Gravity.RIGHT);
				viewHolder.child_proportion.setGravity(Gravity.RIGHT
						| Gravity.CENTER_VERTICAL);
				viewHolder.tvIndex.setTextColor(Color.BLACK);
				viewHolder.tvIndexName.setTextColor(Color.BLACK);
				viewHolder.child_proportion.setTextColor(Color.BLACK);
				// viewHolder.tvIndex.setLayoutParams(layoutParams);
				// viewHolder.tvIndexName.setLayoutParams(layoutParams);
				if ((position - 1) % 2 == 0) {
					viewHolder.tvIndex.setBackgroundColor(0xffd0d8e8);
					viewHolder.tvIndexName.setBackgroundColor(0xffd0d8e8);
					viewHolder.child_proportion.setBackgroundColor(0xffd0d8e8);
				} else {
					viewHolder.tvIndex.setBackgroundColor(0xffe9edf4);
					viewHolder.tvIndexName.setBackgroundColor(0xffe9edf4);
					viewHolder.child_proportion.setBackgroundColor(0xffe9edf4);

				}
			}

			viewHolder.tvIndex.setText("  "
					+ listdata.get(position).getData_name());
			if (belong == 1) {
				viewHolder.tvIndexName.setText(listdata.get(position)
						.getData_value() + "  ");
			} else {
				viewHolder.tvIndexName.setText(listdata.get(position)
						.getData_value());
			}

			if (position == 0) {
				viewHolder.child_proportion.setText("比列（%）");
			} else if (position == 1) {
				viewHolder.child_proportion.setText("100" + "  ");
			} else {

				List<DataCircleBean> data_circle = resultsBean.getData_circle();

				for (int j = 0; j < data_circle.size(); j++) {

					if (listdata
							.get(position)
							.getData_name()
							.equals(data_circle.get(j).getData_circle_data()
									.getData_circle_name())) {
						viewHolder.child_proportion.setText(data_circle.get(j)
								.getData_circle_data().getData_circle_value()
								+ "  ");
						break;
					}

				}

			}

			return convertView;
		}

		private class ViewHolder {

			TextView tvIndex;// 评定等级
			TextView tvIndexName;// 评定等级
			TextView child_proportion;// 比列
		}
	}

	private class ListAdapter extends BaseAdapter {

		List<com.mobo.zggkgisandroid.WebModel.StatisticsModelTwo.ResultsBean.IndicatorInfoBean> listdata;

		ListAdapter(
				List<com.mobo.zggkgisandroid.WebModel.StatisticsModelTwo.ResultsBean.IndicatorInfoBean> data) {
			this.listdata = data;
		}

		@Override
		public int getCount() {
			return this.listdata.size();
		}

		@Override
		public Object getItem(int arg0) {
			return listdata.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			final ViewHolder viewHolder;
			if (arg1 == null) {
				viewHolder = new ViewHolder();
				arg1 = LayoutInflater.from(StatisticsActivityDetail.this)
						.inflate(R.layout.assessment_level_list_item, arg2,
								false);
				viewHolder.tvIndex = (TextView) arg1
						.findViewById(R.id.tv_assessment_list_index);
				viewHolder.tvIndexName = (TextView) arg1
						.findViewById(R.id.tv_assessment_list_index_name);
				viewHolder.tvExponential = (TextView) arg1
						.findViewById(R.id.tv_assessment_list_exponential);
				viewHolder.tvLevel = (TextView) arg1
						.findViewById(R.id.tv_assessment_list_level_grade);
				viewHolder.imgLevel = (ImageView) arg1
						.findViewById(R.id.img_assessment_list_level_grade);
				viewHolder.llLevel = (LinearLayout) arg1
						.findViewById(R.id.ll_assessment_list_level);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			if (arg0 % 2 == 0) {
				viewHolder.tvIndex.setBackgroundColor(0xffd0d8e8);
				viewHolder.tvIndexName.setBackgroundColor(0xffd0d8e8);
				viewHolder.tvExponential.setBackgroundColor(0xffd0d8e8);
				viewHolder.llLevel.setBackgroundColor(0xffd0d8e8);
			} else {
				viewHolder.tvIndex.setBackgroundColor(0xffe9edf4);
				viewHolder.tvIndexName.setBackgroundColor(0xffe9edf4);
				viewHolder.tvExponential.setBackgroundColor(0xffe9edf4);
				viewHolder.llLevel.setBackgroundColor(0xffe9edf4);

			}
			viewHolder.tvIndex.setText(listdata.get(arg0).getIndicator());
			viewHolder.tvIndexName.setText(listdata.get(arg0)
					.getIndicator_name());
			viewHolder.tvExponential.setText(listdata.get(arg0)
					.getIndicator_number());
			// sss;
			String text = listdata.get(arg0).getIndicator_grade();// 指标等级
			if ("1".equals(text)) {
				viewHolder.tvLevel.setText("优");
				viewHolder.imgLevel.setBackgroundColor(0xff78e039);
			} else if ("2".equals(text)) {
				viewHolder.tvLevel.setText("良");
				viewHolder.imgLevel.setBackgroundColor(0xff61fbe7);
			} else if ("3".equals(text)) {
				viewHolder.tvLevel.setText("中");
				viewHolder.imgLevel.setBackgroundColor(0xfff4ee73);
			} else if ("4".equals(text)) {
				viewHolder.tvLevel.setText("次");
				viewHolder.imgLevel.setBackgroundColor(0xffffaa52);
			} else {
				viewHolder.tvLevel.setText("差");
				viewHolder.imgLevel.setBackgroundColor(0xfffa5402);
			}

			return arg1;
		}

		private class ViewHolder {
			TextView tvIndex, tvIndexName, tvExponential, tvLevel;// 指标，指标名称，指数，评定等级
			ImageView imgLevel;// 评定等级
			LinearLayout llLevel;// 评定等级
		}

	}

	/**
	 * 等级分布
	 * 
	 * @param view
	 */
	public void onClickRank(View view) {
		Intent intent = new Intent(StatisticsActivityDetail.this,
				AddKpiActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("key", "Statistics");
		bundle.putString("province_code", currentCity.getProvince_code());
		bundle.putString("province_name", currentCity.getProvince_name());
		bundle.putString("province_code", currentCity.getProvince_code());
		intent.putExtras(bundle);
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 1 && arg1 == 1) {
		}
	}

	/**
	 * 设置单行的gridView 根据
	 * 
	 * @param gridView
	 *            要设置的GridView
	 * @param nameList
	 *            GridView的数据源
	 * @param context
	 */
	protected void setGridViewCount(GridView gridView, List<String> nameList,
			Activity context) {
		// 得到像素密度

		// 根据item的数目，动态设定gridview的宽度,现假定每个item的宽度和高度均为100dp，列间距为5dp
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		int swidth = 0;
		int density = 0;
		int itemWidth = (int) (swidth / 4);
		int spacingWidth = (int) (0 * density);

		params.width = itemWidth * nameList.size() + (nameList.size() - 1)
				* spacingWidth;
		params.height = LayoutParams.WRAP_CONTENT;
		gridView.setStretchMode(GridView.NO_STRETCH); // 设置为禁止拉伸模式
		gridView.setNumColumns(nameList.size());
		gridView.setHorizontalSpacing(spacingWidth);
		gridView.setColumnWidth(itemWidth);
		gridView.setLayoutParams(params);
	}

	public List<ResultsBean> getResults() {
		return results;
	}

	public void setResults(List<ResultsBean> results) {
		this.results = results;
	}

	public int getType() {
		return type;
	}

}
