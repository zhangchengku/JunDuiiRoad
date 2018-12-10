package com.mobo.zggkgisandroid.AddKpi;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.IndicatorInfo;


/**
 * 公路指标信息适配器
 * 
 * @author wjm
 */
public class IndicatorsShowAdapter extends ArrayAdapter<IndicatorInfo> {

	private LayoutInflater inflater;
	private Context context;
	private ArrayList<IndicatorInfo> list;
	private int resource;

	public IndicatorsShowAdapter(Context context, int resource,
			ArrayList<IndicatorInfo> list) {
		super(context, resource, list);

		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LinearLayout imageListView;
		// 获取数据
		IndicatorInfo indicator_info = getItem(position);
		String indicator_name = indicator_info.getIndicator_value();
		String indicator_num = indicator_info.getIndicator_num();
		int indicator_grade = Integer.valueOf(indicator_info
				.getIndicator_grade());

		int PCI_color = 0;

		switch (indicator_grade) {
		case 1:
			PCI_color = Color.rgb(120, 224, 57);
			break;
		case 2:
			PCI_color = Color.rgb(97, 251, 231);
			break;
		case 3:
			PCI_color = Color.rgb(224, 238, 115);
			break;
		case 4:
			PCI_color = Color.rgb(255, 170, 82);
			break;
		case 5:
			PCI_color = Color.rgb(250, 84, 2);
			break;
		default:
			PCI_color = Color.argb(125, 127, 127, 127);
			break;
		}

		if (convertView == null) {
			imageListView = new LinearLayout(getContext());
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(resource, imageListView, true);// 把image_item.xml布局解析到LinearLayout里面
		} else {
			imageListView = (LinearLayout) convertView;
		}

		TextView vindicator_name = (TextView) imageListView
				.findViewById(R.id.qureyKmDataPqi);
		TextView vindicator_num = (TextView) imageListView
				.findViewById(R.id.qureyKmDataPqiData);
		TextView vindicator_color = (TextView) imageListView
				.findViewById(R.id.qureyKmDataPqiColor);

		vindicator_name.setText(indicator_name.toUpperCase() + ":");
		vindicator_num.setText(indicator_num);

		//vindicator_color.setVisibility(View.GONE);
		vindicator_color.setBackgroundColor(PCI_color);

		return imageListView;

	}

}
