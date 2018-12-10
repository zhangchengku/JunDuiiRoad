package com.mobo.zggkgisandroid.Bridge;

import java.lang.annotation.Retention;
import java.util.ArrayList;

import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.WebModel.BridgeImagesBeanResult;
import com.mobo.zggkgisandroid.WebModel.BridgeDetailInfoResult.BridgeDetailInfo.DamageInfo;
import com.mobo.zggkgisandroid.WebModel.BridgeImagesBeanResult.BridgeImagesBean.ImageList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * 桥梁病害信息
 * 
 */
public class BridgeDiseaseInfoFragment extends Fragment {
	private ArrayList<DamageInfo> damage_info;//详情
	private ArrayList<ImageList> image_list;// 顶部图片集合
	private ListAdpater listAdpater;//适配器
	private BridgeDetailInfoActivity activity;//桥梁详情
	private int position = -1;//list下标

	public BridgeDiseaseInfoFragment() {
	}

	public BridgeDiseaseInfoFragment(ArrayList<DamageInfo> damage_info) {
		this.damage_info = damage_info;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = (BridgeDetailInfoActivity) getActivity();
		View view = inflater.inflate(
				R.layout.bridge_disease_info_fragment_layout, container, false);
		ListView listView = (ListView) view
				.findViewById(R.id.list_bridge_disease_info);
		listAdpater = new ListAdpater();
		listView.setAdapter(listAdpater);
		return view;
	}

	private class ListAdpater extends BaseAdapter {

		@Override
		public int getCount() {
			return damage_info != null ? damage_info.size() : 0;
		}

		@Override
		public DamageInfo getItem(int position) {
			return damage_info.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(activity)
						.inflate(R.layout.bridge_disease_info_item_layout,
								parent, false);
				viewHolder.tvDiseaseCategory = (TextView) convertView
						.findViewById(R.id.tv_bridge_disease_info_item_disease_category);
				viewHolder.tvPartOfThe = (TextView) convertView
						.findViewById(R.id.tv_bridge_disease_info_item_part_of_the);
				viewHolder.tvDefectLocation = (TextView) convertView
						.findViewById(R.id.tv_bridge_disease_info_item_defect_location);
				viewHolder.tvWhereComponent = (TextView) convertView
						.findViewById(R.id.tv_bridge_disease_info_item_where_component);
				viewHolder.tvScaling = (TextView) convertView
						.findViewById(R.id.tv_bridge_disease_info_item_scaling);
				viewHolder.tvDiseaseDescription = (TextView) convertView
						.findViewById(R.id.tv_bridge_disease_info_item_disease_description);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			final DamageInfo damageInfo = getItem(position);
			if (BridgeDiseaseInfoFragment.this.position == position) {
				convertView
						.setBackgroundResource(R.drawable.bridge_disease_info_list_item_sel);
			} else {
				convertView
						.setBackgroundResource(R.drawable.bridge_disease_info_list_click_item_sel);
			}
			if (damageInfo == null)
				return convertView;
			viewHolder.tvDiseaseCategory.setText(damageInfo.getDamage_class());
			viewHolder.tvPartOfThe.setText(damageInfo.getBelong_parts());
			viewHolder.tvDefectLocation
					.setText(damageInfo.getDefect_position());
			viewHolder.tvWhereComponent.setText(damageInfo.getAt_component());
			viewHolder.tvScaling.setText(damageInfo.getScale());
			viewHolder.tvDiseaseDescription.setText(damageInfo
					.getDamage_detail());
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (position == BridgeDiseaseInfoFragment.this.position)
						return;
					activity.getImageBitmap(damageInfo.getDamage_id(), position);
				}
			});
			return convertView;
		}

		private class ViewHolder {
			TextView tvDiseaseCategory;// 病害类别
			TextView tvPartOfThe;// 所属部件
			TextView tvDefectLocation;// 缺损位置
			TextView tvWhereComponent;// 所在构件
			TextView tvScaling;// 标度
			TextView tvDiseaseDescription;// 病害描述
		}

	}

	/**
	 * 获取病类id
	 * 
	 * @return
	 */
	public String getDamageid() {
		try {
			return damage_info.get(position == -1 ? 0 : position)
					.getDamage_id();
		} catch (Exception e) {
			return "";
		}

	}

	public ArrayList<ImageList> getImage_list() {
		return image_list;
	}

	public void setImage_list(ArrayList<ImageList> image_list) {
		this.image_list = image_list;
	}

	public int getPosition() {
		return position == -1 ? 0 : position;
	}

	public void setPosition(int position) {
		this.position = position;
		listAdpater.notifyDataSetChanged();
	}
}
