package com.mobo.zggkisandroid.AdapterClass;

import java.util.ArrayList;

import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.ClassfyInfo.BridgePositionInfo;
import com.mobo.zggkisandroid.AdapterClass.BridgesAdapter.ViewHolder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BridgeCityAdapter extends BaseAdapter {

	private ArrayList<BridgePositionInfo> bridgePositionInfos;
	private LayoutInflater inflater;

	public BridgeCityAdapter(Context context,
			ArrayList<BridgePositionInfo> bridgePositionInfos) {

		this.bridgePositionInfos = bridgePositionInfos;
		inflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bridgePositionInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bridgePositionInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder mHolder;

		if (convertView == null) {

			mHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.bridge_list_item, null);

			mHolder.bridgeName = (TextView) convertView
					.findViewById(R.id.bridge_item_name_txt);
			mHolder.bridgeBelongRoadCode = (TextView) convertView
					.findViewById(R.id.bridge_belong_roadcode_txt);
			mHolder.bridgeBelongRoadName = (TextView) convertView
					.findViewById(R.id.bridge_belong_roadname_txt);
			mHolder.bridgeStation = (TextView) convertView
					.findViewById(R.id.bridge_belong_roadstation_txt);

			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		mHolder.bridgeName.setText(bridgePositionInfos.get(position)
				.getBridge_name());
		mHolder.bridgeBelongRoadCode.setText(bridgePositionInfos.get(position)
				.getRoad_code());
		mHolder.bridgeBelongRoadName.setText(bridgePositionInfos.get(position)
				.getRoad_name());
		mHolder.bridgeStation.setText(changeStationToStandard(bridgePositionInfos.get(position)
				.getBridge_center_station()));

		return convertView;
	}

	class ViewHolder {
		TextView bridgeName;// 桥梁名称
		TextView bridgeBelongRoadCode;// 所属路线编码
		TextView bridgeBelongRoadName;// 所属路线名称
		TextView bridgeStation;// 所属桩号
	}
	
	/**
	 * 将桩号由小数点形式转换为K整数+小数（三位）（1987.000 to K1987+000）
	 * 
	 * @param station
	 *            要素标志经度
	 * 
	 * @return 桩号的标准形式
	 */
	public String changeStationToStandard(String station) {

		if (!TextUtils.isEmpty(station)) {

			if (station.contains(".")) {

				String standard = (station.replace(".", "+"));

				int index = standard.indexOf("+");

				String normal = null;

				if (standard.length() < index + 4) {

					if (standard.length() > 1) {
						normal = standard.substring(0, standard.length() - 1);

					} else {
						normal = station;
					}

				} else {

					normal = standard.substring(0, index + 4);
				}

				if (!TextUtils.isEmpty(normal)) {
					return normal;
				}

			} else {
				return station+"+000";
			}
		}

		return null;
	}

}
