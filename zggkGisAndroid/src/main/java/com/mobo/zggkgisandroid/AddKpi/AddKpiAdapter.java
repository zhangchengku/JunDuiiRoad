package com.mobo.zggkgisandroid.AddKpi;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.Indicator;

/**
 * 
 * 为道路指标类适配器 道路类为addkpiactivity
 * 
 * @author xushiwei
 * 
 */
public class AddKpiAdapter extends ArrayAdapter<Indicator> {
	private Context context; // 上下文
	private ArrayList<Indicator> objects; // 原始数据
	private int index = 0;// 选择的按钮位置也就是listview单选的的位置
	private int switch_flag = -1;
	private ArrayList<String> switch_flags_list = new ArrayList<String>();
	private int flag_index = -1;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public AddKpiAdapter(Context context, int resource,
			ArrayList<Indicator> objects) {
		super(context, resource, objects);
		this.context = context;
		this.objects = objects;

		switch_flags_list.clear();
		for (int i = 0; i < objects.size(); i++) {
			switch_flags_list.add("0");
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// 设置相应的viewholder
		Viewholder viewholder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.add_kpi_listitem_layout, null);
			viewholder = new Viewholder();
			viewholder.query_bridge_detai_bridgename = (TextView) convertView
					.findViewById(R.id.query_bridge_detai_bridgename);
			viewholder.query_bridge_detai_imagebutton = (ImageView) convertView
					.findViewById(R.id.query_bridge_detai_imagebutton);
			convertView.setTag(viewholder);

		}
		viewholder = (Viewholder) convertView.getTag();
		viewholder.query_bridge_detai_bridgename.setText(objects.get(position)
				.getIndicator_value().toUpperCase()
				+ ":");

		if (switch_flags_list.get(position) != "0") {
			viewholder.query_bridge_detai_imagebutton
					.setImageResource(R.drawable.kpi_switch_open);

		} else {
			viewholder.query_bridge_detai_imagebutton
					.setImageResource(R.drawable.kpi_switch_close);
		}

		if (index == position) {
			viewholder.query_bridge_detai_imagebutton
					.setImageResource(R.drawable.kpi_switch_open);
		}

		// viewholder.query_bridge_detai_imagebutton
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// flag_index = position;
		//
		// if (switch_flags_list.get(position) == "0") {
		//
		// switch_flags_list.set(position, "1");
		// } else {
		// switch_flags_list.set(position, "0");
		// }
		//
		// for (int i = 0; i < switch_flags_list.size(); i++) {
		//
		// if (switch_flags_list.get(position) == "1") {
		//
		// switch_flags_list.set(i, "0");
		//
		// switch_flags_list.set(position, "1");
		// } else {
		//
		// switch_flags_list.set(i, "0");
		//
		// }
		// }
		//
		// AddKpiAdapter.this.notifyDataSetChanged();
		//
		//
		// handler.postDelayed(run,3000);
		// }
		// });

		return convertView;

	}

	/**
	 * 
	 * listview的holder
	 * 
	 * @author xushiwei
	 * 
	 */
	class Viewholder {
		TextView query_bridge_detai_bridgename;// 指标名

		ImageView query_bridge_detai_imagebutton;// 指标单选图标

	}

	Handler handler = new Handler();

	Runnable run = new Runnable() {
		public void run() {

			Log.i("====================", "===switch_flag=="
					+ switch_flags_list.get(flag_index));

			Intent intent = new Intent();

			if (switch_flags_list.get(flag_index) == "0") {

				intent.putExtra("qureyIndicatorId", "0");
			} else {

				intent.putExtra("qureyIndicatorId", objects.get(flag_index)
						.getIndicator_id());
			}

			AddKpiActivity.instance.setResult(1, intent);
			AddKpiActivity.instance.finish();

		}
	};

}