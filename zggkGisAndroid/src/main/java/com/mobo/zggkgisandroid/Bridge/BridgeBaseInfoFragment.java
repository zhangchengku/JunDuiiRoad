package com.mobo.zggkgisandroid.Bridge;

import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.WebModel.BridgeDetailInfoResult.BridgeDetailInfo.BaseInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * 桥梁基本信息
 * 
 */
public class BridgeBaseInfoFragment extends Fragment {
	BaseInfo base_info;

	public BridgeBaseInfoFragment() {
	}

	public BridgeBaseInfoFragment(BaseInfo base_info) {
		this.base_info = base_info;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bridge_base_info,
				container, false);
		TextView tvBaseName = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_base_bridge_name);// 桥梁名称
		TextView tvTubeFeedUnit = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_base_tube_feed_unit);// 管养单位
		TextView tvAdministrativeRegion = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_base_administrative_region);// 所在政区
		TextView tvRoute = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_base_route);// 所属路线
		TextView tvClassification = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_base_bridge_classification);// 桥梁分类
		TextView tvType = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_base_bridge_type);// 桥梁类型
		TextView tvPosition = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_base_bridge_position);// 桥梁位置
		TextView tvLength = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_bridge_length);// 桥梁长度
		TextView tvHeight = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_base_bridge_height);// 桥梁净高
		TextView tvRepairLife = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_repair_life);// 修理年限
		TextView tvTechnicalLevel = (TextView) view
				.findViewById(R.id.tv_fragment_bridge_base_technical_level);// 技术等级
		if (base_info == null)
			return view;

		tvBaseName.setText(base_info.getBridge_name());
		tvTubeFeedUnit.setText(base_info.getManage_unit());
		tvAdministrativeRegion.setText(base_info.getBelong_area());
		tvRoute.setText(base_info.getRoad_code());
		tvClassification.setText(base_info.getBridge_type());
		tvType.setText(base_info.getBridge_materia_type());
		tvPosition.setText(base_info.getBridge_station());
		tvLength.setText(base_info.getBridge_length());
		tvHeight.setText(base_info.getBridge_clean_height());
		tvRepairLife.setText(base_info.getBridge_year());
		tvTechnicalLevel.setText(base_info.getBridge_technical_grade());
		return view;
	}
}
