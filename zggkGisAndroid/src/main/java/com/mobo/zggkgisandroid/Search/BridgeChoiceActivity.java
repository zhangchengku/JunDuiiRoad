package com.mobo.zggkgisandroid.Search;

import java.util.ArrayList;

import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.DBEntity.DB_Value;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择桥梁类型界面
 * 
 * @author WuXiaoHao
 *
 */
public class BridgeChoiceActivity extends Activity {
	ListView listView;
	public final static int code = 9123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bridge_choice_activity);
		listView = (ListView) findViewById(R.id.lv_bridge_choice);
		listView.setAdapter(new ListAdapter());
	}

	/**
	 * 适配器
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class ListAdapter extends BaseAdapter {
		private int index = -1;
		private ArrayList<DB_Value> db_Values;

		public ListAdapter() {
			db_Values = CustomApp.app.dbManager.queryValuesByType("桥梁类型");
		}

		@Override
		public int getCount() {
			return db_Values != null ? db_Values.size() : 0;
		}

		@Override
		public DB_Value getItem(int arg0) {
			return db_Values.get(arg0);
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
				arg1 = LayoutInflater.from(BridgeChoiceActivity.this).inflate(R.layout.add_kpi_listitem_layout, null);
				viewHolder.tvTitle = (TextView) arg1.findViewById(R.id.query_bridge_detai_bridgename);
				viewHolder.imgChoice = (ImageView) arg1.findViewById(R.id.query_bridge_detai_imagebutton);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			final DB_Value db_Value = getItem(arg0);
			viewHolder.tvTitle.setText(db_Value.getDic_desc());
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
					Intent intent = new Intent();
					intent.putExtra("bridgeType", db_Value.getDic_code());
					setResult(code, intent);
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
