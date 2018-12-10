package com.mobo.zggkgisandroid.Info;

import java.util.List;

import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.View.ListVIewForScrollView;
import com.mobo.zggkgisandroid.WebModel.StatisticsModel.ResultsBean.ColomDataBean.DataValuesColomBean;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class statistics_grid_adapter extends ArrayAdapter<DataValuesColomBean> {

	private Context context;
	private List<DataValuesColomBean> object;
	private int resource;
	private int lvPosition;

	public statistics_grid_adapter(Context context, int resource,
			List<DataValuesColomBean> objects, int lvPosition) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub

		this.context = context;
		this.object = objects;
		this.resource = resource;
		this.lvPosition = lvPosition;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// return super.getView(position, convertView, parent);
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.statistics_gridview_layout, null);
			viewHolder.txView = (TextView) convertView
					.findViewById(R.id.statistics_tv);
			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();

		}

		if (lvPosition == 0) {

			viewHolder.txView.setBackgroundColor(Color.parseColor("#4f81ff"));
		} else {
			if ((lvPosition - 1) % 2 == 0) {

				viewHolder.txView.setBackgroundColor(0xffd0d8e8);
				viewHolder.txView.setTop(20);

			} else {

				viewHolder.txView.setBackgroundColor(0xffe9edf4);
			}

		}
		viewHolder.txView.setText(object.get(position).getData_value());

		return convertView;
	}

	class ViewHolder

	{
		TextView txView;

	}

}
