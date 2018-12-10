package com.mobo.zggkgisandroid.Info;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.View.GridViewForScrollView;
import com.mobo.zggkgisandroid.WebModel.StatisticsModel.ResultsBean.ColomDataBean;

public class statistics_lv_adapter extends ArrayAdapter<ColomDataBean> {

	private Context context;
	private List<ColomDataBean> object;

	public statistics_lv_adapter(Context context, int resource,
			List<ColomDataBean> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.object = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// return super.getView(position, convertView, parent);

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.statistics_lv_layout, null);
			holder.gridViewForScrollView = (GridViewForScrollView) convertView
					.findViewById(R.id.statistics_gv);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

//		holder.gridViewForScrollView.setNumColumns(this.object.get(position).getData_values_colom().size());
//		statistics_grid_adapter statistics_grid_adapter = new statistics_grid_adapter(
//				context, 0, this.object.get(position).getData_values_colom(), position);
		// statisticsGv.setAdapter(statistics_grid_adapter);
//		holder.gridViewForScrollView.setAdapter(statistics_grid_adapter);

		return convertView;
	}

	class ViewHolder {

		GridViewForScrollView gridViewForScrollView;

	}
}
