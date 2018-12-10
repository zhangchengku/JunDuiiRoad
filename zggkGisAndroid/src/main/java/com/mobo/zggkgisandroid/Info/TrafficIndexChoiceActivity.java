package com.mobo.zggkgisandroid.Info;

import java.util.ArrayList;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.Indicator;
import com.mobo.zggkgisandroid.DBEntity.DB_Value;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.IndicatorResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 路线统计,路面统计指标选择
 * 
 * @author WuXiaoHao
 *
 */
public class TrafficIndexChoiceActivity extends Activity {
	private ListView listView;
	private String tag;
	private CityList currentCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bridge_choice_activity);
		tag = getIntent().getStringExtra("tag");
		currentCity = (CityList) getIntent().getSerializableExtra("currentCity");
		listView = (ListView) findViewById(R.id.lv_bridge_choice);
		if (tag.equals("ROUTE_STATISTIC") || tag.equals("PAVEMENT_STATISTICS")) {
			new ThreadWithProgressDialog().Run(this, new GetIndicatorTypeNumTask(), R.string.com_loading);
		} else if (tag.equals("BRIDGE_LEVEL_STATISTICS")) {
			ArrayList<DB_Value> db_Values = CustomApp.app.dbManager.queryValuesByType("桥梁类型");
			listView.setAdapter(new ListAdapter(null, db_Values));
		} else if (tag.equals("TUNNEL_LEVEL_STATISTICS")) {
			ArrayList<DB_Value> db_Values = CustomApp.app.dbManager.queryValuesByType("隧道类型");
			listView.setAdapter(new ListAdapter(null, db_Values));
		}
	}

	private class GetIndicatorTypeNumTask implements ThreadWithProgressDialogTask {
		IndicatorResult result;

		@Override
		public boolean TaskMain() {

			result = CustomApp.app.connInteface.getIndicator();

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
				ArrayList<Indicator> indicators = new ArrayList<Indicator>();
				for (int i = 0; i < result.getResults().size(); i++) {
					Indicator indicator = result.getResults().get(i);
					if (tag.equals("ROUTE_STATISTIC") && ("MQI".equals(indicator.getIndicator_id())
							|| "PQI".equals(indicator.getIndicator_id()) || "SCI".equals(indicator.getIndicator_id())
							|| "BCI".equals(indicator.getIndicator_id())
							|| "TCI".equals(indicator.getIndicator_id()))) {
						indicators.add(indicator);
					} else if (tag.equals("PAVEMENT_STATISTICS") && ("PCI".equals(indicator.getIndicator_id())
							|| "RQI".equals(indicator.getIndicator_id()) || "RDI".equals(indicator.getIndicator_id())
							|| "SRI".equals(indicator.getIndicator_id())
							|| "PSSI".equals(indicator.getIndicator_id()))) {
						indicators.add(indicator);
					}
				}
				listView.setAdapter(new ListAdapter(indicators, null));
			} else if (state == 2) {
				CustomApp.app.exit(TrafficIndexChoiceActivity.this);
			}
			return true;
		}

	}

	/**
	 * 适配器
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class ListAdapter extends BaseAdapter {
		private ArrayList<Indicator> indicators;
		private ArrayList<DB_Value> db_Values;
		private int index = -1;

		public ListAdapter(ArrayList<Indicator> indicators, ArrayList<DB_Value> db_Values) {
			this.indicators = indicators;
			this.db_Values = db_Values;
		}

		@Override
		public int getCount() {
			if (db_Values == null) {
				return indicators != null ? indicators.size() : 0;
			} else {
				return db_Values != null ? db_Values.size() : 0;
			}
		}

		@Override
		public Object getItem(int arg0) {
			if (db_Values == null) {
				return indicators.get(arg0);
			} else {
				return db_Values.get(arg0);
			}
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
				arg1 = LayoutInflater.from(TrafficIndexChoiceActivity.this).inflate(R.layout.add_kpi_listitem_layout, null);
				viewHolder.tvTitle = (TextView) arg1.findViewById(R.id.query_bridge_detai_bridgename);
				viewHolder.imgChoice = (ImageView) arg1.findViewById(R.id.query_bridge_detai_imagebutton);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			final Indicator indicator;
			final DB_Value db_Value;
			if (db_Values == null) {
				db_Value = null;
				indicator = (Indicator) getItem(arg0);
				viewHolder.tvTitle.setText(indicator.getIndicator_value().toUpperCase() + ":");
			} else {
				indicator = null;
				db_Value = (DB_Value) getItem(arg0);
				viewHolder.tvTitle.setText(db_Value.getDic_desc().toUpperCase() + ":");
			}

			if (arg0 == index) {
				viewHolder.imgChoice.setImageResource(R.drawable.kpi_switch_open);
			} else {
				viewHolder.imgChoice.setImageResource(R.drawable.kpi_switch_close);
			}
			viewHolder.imgChoice.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					index = arg0;
					notifyDataSetChanged();
					Intent intent = new Intent(TrafficIndexChoiceActivity.this, StatisticsActivityDetailB.class);
					intent.putExtra("currentCity", currentCity);
					if (db_Values == null) {
						intent.putExtra("indicator_id", indicator.getIndicator_id());
						intent.putExtra("indicator_value", indicator.getIndicator_value());
					} else {
						intent.putExtra("indicator_id", db_Value.getDic_code());
						intent.putExtra("indicator_value", db_Value.getDic_desc());
					}
					intent.putExtra("tag", tag);
					startActivity(intent);
					finish();
				}
			});
			return arg1;
		}

		private class ViewHolder {
			TextView tvTitle;// 指标名
			ImageView imgChoice;// 指标单选图标
		}
	}
}
