package com.mobo.zggkgisandroid.Info;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.mobo.zggkgisandroid.BaseActivity;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.AddKpi.AddKpiActivity;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.Utils.constant;
import com.mobo.zggkgisandroid.WebModel.CityList;

public class StatisticsActivity extends BaseActivity implements OnClickListener {

	// 标题
	private TextView statisticsClassifyTitle;
	// 技术等级
	private TextView statisticsClassifyTechnicalLevel;
	// 路面类型
	private TextView statisticsClassifyPavementType;
	// 行政等级
	private TextView statisticsClassifyAdministrativeLevels;
	// 桥梁分类
	private TextView statisticsClassifyBridgeClassification;

	private TextView statisticsClassifyrank;
	// 隧道分类
	private TextView statisticsClassifyTunnelClassification;
	// 启动
	private Intent intent;
	private Intent intent2;
	// 当前城市
	private CityList currentCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentXml(R.layout.statistics_classify);
		CustomApp.app.mActiveLists.add(this);
		currentCity = (CityList) getIntent().getSerializableExtra("currentCity");
		if (currentCity == null) {

			currentCity = new CityList();
			currentCity.setProvince_code("123001");
			currentCity.setProvince_name("全国");

		}
		findViews();

	}

	private void findViews() {
		// 返回键
		imgLeft.setImageResource(R.drawable.rank_back_btn_sel);
		imgLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				StatisticsActivity.this.finish();

			}
		});
		tvTitle.setTextSize(Utils.dip2px(StatisticsActivity.this, 7.0d));
		tvTitle.setTextColor(Color.WHITE);
		TextPaint paint = tvTitle.getPaint();
		paint.setFakeBoldText(true);
		statisticsClassifyTitle = (TextView) findViewById(R.id.statistics_classify_title);
		statisticsClassifyTechnicalLevel = (TextView) findViewById(R.id.statistics_classify_Technical_level);
		statisticsClassifyPavementType = (TextView) findViewById(R.id.statistics_classify_Pavement_type);
		statisticsClassifyAdministrativeLevels = (TextView) findViewById(R.id.statistics_classify_Administrative_levels);
		statisticsClassifyBridgeClassification = (TextView) findViewById(R.id.statistics_classify_Bridge_classification);
		statisticsClassifyTunnelClassification = (TextView) findViewById(R.id.statistics_classify_Tunnel_classification);
		statisticsClassifyrank = (TextView) findViewById(R.id.statistics_classify_rank);
		// 初始化标题
		if (currentCity.getProvince_code().equals("123001")) {
			// statisticsClassifyTitle.setText("全国路况数据统计");

			tvTitle.setText("全国路况统计");
		} else {

			tvTitle.setText(currentCity.getProvince_name().substring(0, 2) + "路况统计");
		}

		statisticsClassifyTechnicalLevel.setOnClickListener(this);
		statisticsClassifyPavementType.setOnClickListener(this);
		statisticsClassifyAdministrativeLevels.setOnClickListener(this);
		statisticsClassifyBridgeClassification.setOnClickListener(this);
		statisticsClassifyTunnelClassification.setOnClickListener(this);
		statisticsClassifyrank.setOnClickListener(this);
		intent = new Intent(StatisticsActivity.this, StatisticsActivityDetail.class);
		intent2 = new Intent(StatisticsActivity.this, StatisticsActivityDetailB.class);
		// 路線統計
		findViewById(R.id.statistics_classify_road_grade_b).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(StatisticsActivity.this, TrafficIndexChoiceActivity.class);
				intent.putExtra("currentCity", currentCity);
				intent.putExtra("tag", "ROUTE_STATISTIC");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
		// 橋樑統計
		findViewById(R.id.statistics_classify_bridge_grade_b).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StatisticsActivity.this, TrafficIndexChoiceActivity.class);
				intent.putExtra("tag", "BRIDGE_LEVEL_STATISTICS");
				intent.putExtra("currentCity", currentCity);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
		// 隧道統計
		findViewById(R.id.statistics_classify_tunnel_level_b).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StatisticsActivity.this, TrafficIndexChoiceActivity.class);
				intent.putExtra("tag", "TUNNEL_LEVEL_STATISTICS");
				intent.putExtra("currentCity", currentCity);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				// CustomApp.app.customToast(Gravity.CENTER,
				// CustomApp.SHOW_TOAST_TIMES, R.string.in_development);

			}
		});

		;
		// 路面統計
		findViewById(R.id.statistics_classify_road_mian).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(StatisticsActivity.this, TrafficIndexChoiceActivity.class);
				intent.putExtra("currentCity", currentCity);
				intent.putExtra("tag", "PAVEMENT_STATISTICS");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
		// 路况等级
		findViewById(R.id.statistics_classify_road_grade_c).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(StatisticsActivity.this, AddKpiActivity.class);
				intent.putExtra("xswtag", 2);
				intent.putExtra("currentCity", currentCity);
				startActivity(intent);

			}
		});
		// 桥梁等级
		findViewById(R.id.statistics_classify_bridge_grade_c).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, R.string.in_development);

			}
		});
		findViewById(R.id.statistics_classify_tunnle_grade_c).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, R.string.in_development);// TODO
																												// Auto-generated
																												// method
																												// stub

			}
		});
		findViewById(R.id.statistics_classify_flux_grade_c).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, R.string.in_development);// TODO
																												// Auto-generated
																												// method
																												// stub

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		// 技术等级
		case R.id.statistics_classify_Technical_level:

			intent.putExtra("key", constant.TECH_LEVEL);
			intent.putExtra("currentCity", currentCity);
			startActivity(intent);

			break;
		// 路面类型
		case R.id.statistics_classify_Pavement_type:

			intent.putExtra("key", constant.ROAD_FACE_TYPE);
			intent.putExtra("currentCity", currentCity);
			startActivity(intent);
			break;
		// 行政等级
		case R.id.statistics_classify_Administrative_levels:
			intent.putExtra("key", constant.ADMIN_LEVEL);
			intent.putExtra("currentCity", currentCity);
			startActivity(intent);
			break;
		// 桥梁分类
		case R.id.statistics_classify_Bridge_classification:
			intent.putExtra("key", constant.BRIDGE_CLASS);
			intent.putExtra("currentCity", currentCity);
			startActivity(intent);
			break;
		// 隧道分类
		case R.id.statistics_classify_Tunnel_classification:
			intent.putExtra("key", constant.TUNNEL_CLASS);
			intent.putExtra("currentCity", currentCity);
			startActivity(intent);

			break;
		case R.id.statistics_classify_rank:

			intent.putExtra("key", constant.RANK_LEVEL);
			intent.putExtra("currentCity", currentCity);
			startActivity(intent);

			break;

		default:
			break;
		}

	}
}