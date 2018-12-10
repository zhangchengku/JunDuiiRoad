package com.mobo.zggkgisandroid.AddKpi;

import java.util.ArrayList;

import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.Indicator;
import com.mobo.zggkgisandroid.Utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class KpiAdapter extends BaseAdapter implements OnClickListener {

	private Context context; // 上下文
	private ArrayList<Indicator> objects; // 原始数据
	private int index = 0;// 选择的按钮位置也就是listview单选的的位置
	private int switch_flag = -1;
	private ArrayList<String> switch_flags_list = new ArrayList<String>();
	private int flag_index = -1;
	private SwitchCallback mCallback;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return objects.get(position);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public KpiAdapter(Context context, int resource,
			ArrayList<Indicator> objects, SwitchCallback callback) {

		this.context = context;
		this.objects = objects;
		mCallback = callback;

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

			// 1,6

			if (position == 1 || position == 6) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.add_kpi_biantai_layout, null);
			} else {

				convertView = LayoutInflater.from(context).inflate(
						R.layout.add_kpi_listitem_layout, null);
			}

			viewholder = new Viewholder();
			viewholder.query_bridge_detai_bridgename = (TextView) convertView
					.findViewById(R.id.query_bridge_detai_bridgename);
			viewholder.query_bridge_detai_imagebutton = (ImageView) convertView
					.findViewById(R.id.query_bridge_detai_imagebutton);

			if (position > 1 && position < 7) {

				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(

				0, LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.gravity = Gravity.CENTER_VERTICAL;
				lp.weight = 1;
				lp.setMargins(Utils.dip2px(context, 18), 2, 0, 2);
				viewholder.query_bridge_detai_bridgename.setLayoutParams(lp);

			} else {

				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.gravity = Gravity.CENTER_VERTICAL;
				lp.weight = 1;
				lp.setMargins(Utils.dip2px(context, 5), 2, 0, 2);
				viewholder.query_bridge_detai_bridgename.setLayoutParams(lp);

			}

			convertView.setTag(viewholder);

		}
		viewholder = (Viewholder) convertView.getTag();

		if (objects.get(position).getIndicator_value() != null) {

			viewholder.query_bridge_detai_bridgename.setText(objects
					.get(position).getIndicator_value().toUpperCase()
					+ ":");

		} else {

			viewholder.query_bridge_detai_bridgename.setText("");

		}

		if (index == position) {
			viewholder.query_bridge_detai_imagebutton
					.setImageResource(R.drawable.kpi_switch_open);
			viewholder.query_bridge_detai_imagebutton.setTag(1);
		} else {
			viewholder.query_bridge_detai_imagebutton
					.setImageResource(R.drawable.kpi_switch_close);
			viewholder.query_bridge_detai_imagebutton.setTag(0);
		}

		viewholder.query_bridge_detai_imagebutton.setId(position);
		viewholder.query_bridge_detai_imagebutton.setOnClickListener(this);

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

	public interface SwitchCallback {

		public void SwitchClick(View v);

	}

	@Override
	public void onClick(View v) {
		mCallback.SwitchClick(v);

	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
