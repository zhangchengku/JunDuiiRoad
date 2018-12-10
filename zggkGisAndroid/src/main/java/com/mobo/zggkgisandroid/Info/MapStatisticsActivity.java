package com.mobo.zggkgisandroid.Info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.BaseActivity;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.MainActivity;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.Utils.constant;
import com.mobo.zggkgisandroid.WebModel.BaseResult;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.MapStaticsModel;
import com.mobo.zggkgisandroid.WebModel.MapStaticsModel.ResultsBean;
import com.mobo.zggkgisandroid.WebModel.MapStaticsModel.ResultsBean.DatalistBean;
import com.mobo.zggkgisandroid.WebModel.ProvinceRoadDataResult.ProvinceRoadData;

public class MapStatisticsActivity extends BaseActivity {
	private CityList cityList;// 当前选中的省份
	private String type;
	private String title;
	private RelativeLayout llTrafficRoadName;
	private TextView mapStatisticsName;
	private ImageView imgMapStatisticsName;
	private RelativeLayout mapStatisticsLength;
	private TextView mapStatisticsLengthTv;
	private ImageView imgMapStatisticsLength;
	private LinearLayout mapStatisticsMap;
	private ListView mapStatisticsRouteLv;
	private ThreadWithProgressDialog dialog;
	private Map<String, DatalistBean> saveData = new LinkedHashMap<String, DatalistBean>();
	private MapStaticsPopWindow mapStaticsPopWindow;
	// private List<String> saveData = new ArrayList<String>();

	/************************ 网络请求参数 *********************************/
	private String year;
	private String statistic_type;
	private String order_rule = constant.ORDER_DESC.toLowerCase();
	private String order_type = constant.NAME.toLowerCase();
	private String province_code;
	private MapStaticsModel mapStaticsbase;
	private String bridge_type;
	private String tunnel_type;
	public BaseResult mapStaticsbridge;
	public BaseResult mapStaticsTunnel;
	private MyAdater adater;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentXml(R.layout.map_statistics_activity);
		CustomApp.app.mActiveLists.add(this);
		cityList = (CityList) getIntent().getSerializableExtra("currentCity");
		if (!(cityList == null)) {
			province_code = cityList.getProvince_code();
		}
		type = getIntent().getStringExtra("key");
		year = getIntent().getStringExtra("year");
		dialog = new ThreadWithProgressDialog();
		initview();
		findViews();
		initTitle();
		order();
		startThread(getType(type));
	};

	private void initview() {
		imgLeft.setImageResource(R.drawable.rank_back_btn_sel);
		imgLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MapStatisticsActivity.this.finish();
			}
		});
		tvRight.setText("确定");
		tvRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (getType(type)) {
				case 1:
					if (!saveData.isEmpty()) {
						Intent intent = new Intent(MapStatisticsActivity.this, MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("currentCity", cityList);
						intent.putExtra("from", "statisticRode");
						String road_code = "";
						for (Entry<String, DatalistBean> dt : saveData.entrySet()) {
							road_code += "," + dt.getValue().getCode();
						}

						CharSequence subSequence = road_code.substring(1, road_code.length());

						intent.putExtra("road_code", subSequence);

						// intent.putExtra(name, value)
						startActivity(intent);
						finish();
						break;
					}
				case 2:
					if (!saveData.isEmpty()) {
						Intent intent = new Intent(MapStatisticsActivity.this, MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("currentCity", cityList);
						intent.putExtra("from", "statisticBridge");
						String bridge_code = "";
						for (Entry<String, DatalistBean> dt : saveData.entrySet()) {
							bridge_code += "," + dt.getValue().getCode();
						}

						CharSequence subSequence = bridge_code.substring(1, bridge_code.length());

						intent.putExtra("bridge_code", subSequence);
						startActivity(intent);
						break;
					}
				case 3:
					// if (!saveData.isEmpty()) {
					// Intent intent = new Intent(MapStatisticsActivity.this,
					// MainActivity.class);
					// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// intent.putExtra("currentCity", cityList);
					// intent.putExtra("from", "statisticTunnel");
					// String tunnel_code = "";
					// for (Entry<String, DatalistBean> dt : saveData
					// .entrySet()) {
					// tunnel_code += "," + dt.getValue().getCode();
					// }
					//
					// CharSequence subSequence = tunnel_code.substring(1,
					// tunnel_code.length());
					//
					// intent.putExtra("tunnel_code", subSequence);
					// startActivity(intent);
					// break;
					// }
					CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, R.string.in_development);
					break;

				default:
					break;
				}

			}
		});

	}

	private void findViews() {
		llTrafficRoadName = (RelativeLayout) findViewById(R.id.ll_traffic_road_name);
		mapStatisticsName = (TextView) findViewById(R.id.map_statistics_name);
		imgMapStatisticsName = (ImageView) findViewById(R.id.img_map_statistics_name);
		mapStatisticsLength = (RelativeLayout) findViewById(R.id.map_statistics_length);
		mapStatisticsLengthTv = (TextView) findViewById(R.id.map_statistics_length_tv);
		imgMapStatisticsLength = (ImageView) findViewById(R.id.img_map_statistics_length);
		mapStatisticsMap = (LinearLayout) findViewById(R.id.map_statistics_map);
		mapStatisticsRouteLv = (ListView) findViewById(R.id.map_statistics_route_lv);
//		mapStatisticsMap.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				if (!(mapStaticsbase == null)) {
//					if (mapStaticsPopWindow == null) {
//
//						try {
//							String typelist = mapStaticsbase.getResults().get(0).getTypelist();
//							String[] split = typelist.split(",");
//							// List<String> data = Arrays.asList("全部", "清空");
//							List<String> data = new ArrayList<String>();
//							data.add("全部");
//							data.add("清空");
//							// for (String string : split) {
//							// data.add(string);
//							// }
//							for (int i = 0; i < split.length; i++) {
//								data.add(split[i]);
//							}
//							// mapStaticsPopWindow = new
//							// MapStaticsPopWindow(data,
//							// mapStatisticsMap,
//							// MapStatisticsActivity.this, new myClick() {
//							//
//							// @Override
//							// public void OnClick(String tag,
//							// int position) {
//							// // TODO Auto-generated method stub
//							//
//							// }
//							// });
//							mapStaticsPopWindow = new MapStaticsPopWindow(data, mapStatisticsMap, MapStatisticsActivity.this, new myClick() {
//
//								@Override
//								public void OnClick(String tag, int position) {
//									// TODO Auto-generated method stub
//									List<DatalistBean> DatalistBean = mapStaticsbase.getResults().get(0).getDatalist();
//
//									if (tag.equals("全部")) {
//										saveData.clear();
//										for (int i = 0; i < DatalistBean.size(); i++) {
//											saveData.put(DatalistBean.get(i).getGuid(), DatalistBean.get(i));
//										}
//									} else if (tag.equals("清空")) {
//										saveData.clear();
//									} else {
//
//										saveData.clear();
//										for (int i = 0; i < DatalistBean.size(); i++) {
//											if (DatalistBean.get(i).getType().equals(tag)) {
//												saveData.put(DatalistBean.get(i).getGuid(), DatalistBean.get(i));
//											}
//
//										}
//									}
//									adater.notifyDataSetChanged();
//								}
//							});
//							mapStaticsPopWindow.show();
//						} catch (Exception e) {
//							// TODO: handle exception
//							e.printStackTrace();
//						}
//
//					} else {
//
//						mapStaticsPopWindow.show();
//					}
//				}
//
//			}
//		});
	}

	class MyAdater extends ArrayAdapter<DatalistBean> {

		private List<ResultsBean> objects;

		public MyAdater(Context context, int resource, List<ResultsBean> objects) {
			super(context, resource, objects.get(0).getDatalist());
			// TODO Auto-generated constructor stub
			this.objects = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(MapStatisticsActivity.this).inflate(R.layout.map_statics_order, parent, false);

				viewHolder.mapStatictisLvName = (TextView) convertView.findViewById(R.id.map_statictis_lv_name);
				viewHolder.mapStatictisLvMail = (TextView) convertView.findViewById(R.id.map_statictis_lv_mail);
				viewHolder.mapStatictisLvType = (TextView) convertView.findViewById(R.id.map_statictis_lv_type);
				viewHolder.llMapStatictisLv = (LinearLayout) convertView.findViewById(R.id.ll_map_statictis_lv);
				viewHolder.imgMapStatictisLv = (ImageView) convertView.findViewById(R.id.img_map_statictis_lv);

				// viewHolder.tvRoadName = (TextView) convertView
				// .findViewById(R.id.tv_traffic_list_road_name);
				// viewHolder.tvRoadMail = (TextView) convertView
				// .findViewById(R.id.tv_traffic_list_road_mail);
				// viewHolder.tvIndicatorNumber = (TextView) convertView
				// .findViewById(R.id.tv_traffic_list_indicator_number);
				// viewHolder.tvIndicatorGrade = (TextView) convertView
				// .findViewById(R.id.tv_traffic_list_indicator_grade);
				// viewHolder.imgIndicatorGrade = (ImageView) convertView
				// .findViewById(R.id.img_traffic_list_indicator_grade);
				// viewHolder.imgXS = (ImageView) convertView
				// .findViewById(R.id.img_traffic_list_xs);
				// viewHolder.llIndicatorGrade = (LinearLayout) convertView
				// .findViewById(R.id.ll_traffic_list_indicator_grade);
				// viewHolder.llXS = (LinearLayout) convertView
				// .findViewById(R.id.ll_traffic_list_xs);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (position % 2 == 0) {
				viewHolder.mapStatictisLvName.setBackgroundColor(0xffd0d8e8);
				viewHolder.mapStatictisLvMail.setBackgroundColor(0xffd0d8e8);
				viewHolder.mapStatictisLvType.setBackgroundColor(0xffd0d8e8);
				viewHolder.llMapStatictisLv.setBackgroundColor(0xffd0d8e8);
				// viewHolder.llXS.setBackgroundColor(0xffd0d8e8);
			} else {
				viewHolder.mapStatictisLvName.setBackgroundColor(0xffe9edf4);
				viewHolder.mapStatictisLvMail.setBackgroundColor(0xffe9edf4);
				viewHolder.mapStatictisLvType.setBackgroundColor(0xffe9edf4);
				viewHolder.llMapStatictisLv.setBackgroundColor(0xffe9edf4);
				// viewHolder.llXS.setBackgroundColor(0xffe9edf4);

			}
			final DatalistBean datalistBean = objects.get(0).getDatalist().get(position);
			viewHolder.mapStatictisLvName.setText(datalistBean.getName());
			viewHolder.mapStatictisLvMail.setText(datalistBean.getMail());
			viewHolder.mapStatictisLvType.setText(datalistBean.getType());
			if (saveData.containsKey(datalistBean.getGuid())) {
				viewHolder.imgMapStatictisLv.setImageResource(R.drawable.kpi_switch_open);
			} else {
				viewHolder.imgMapStatictisLv.setImageResource(R.drawable.kpi_switch_close);
			}
			viewHolder.imgMapStatictisLv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!saveData.containsKey(datalistBean.getGuid())) {
						saveData.put(datalistBean.getGuid(), datalistBean);
					} else {
						saveData.remove(datalistBean.getGuid());
					}
					notifyDataSetChanged();
				}
			});
			return convertView;

		}

		private class ViewHolder {
			// TextView tvRoadName, tvRoadMail, tvIndicatorNumber,
			// tvIndicatorGrade;// 路线名称，路线长度，PCI，评定等级
			// ImageView imgIndicatorGrade, imgXS;// 评定等级,地图显示
			// LinearLayout llIndicatorGrade, llXS;// 评定等级，地图显示

			TextView mapStatictisLvName;// 路线名称，路线长度，评定等级
			TextView mapStatictisLvMail;
			TextView mapStatictisLvType;
			LinearLayout llMapStatictisLv;// 地图显示
			ImageView imgMapStatictisLv;

		}

	}

	private void order() {
		llTrafficRoadName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!order_type.equals(constant.NAME.toLowerCase())) {
					order_type = constant.NAME.toLowerCase();
					order_rule = constant.ORDER_DESC.toLowerCase();
				} else {
					if (order_rule.toUpperCase().equals(constant.ORDER_ASC)) {
						order_rule = constant.ORDER_DESC.toLowerCase();
					} else {
						order_rule = constant.ORDER_ASC.toLowerCase();
					}

				}

				startThread(getType(type));
			}
		});
		mapStatisticsLength.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (order_type.equals(constant.MAIEL.toLowerCase())) {
					if (order_rule.toUpperCase().equals(constant.ORDER_ASC)) {
						order_rule = constant.ORDER_DESC.toLowerCase();
					} else {
						order_rule = constant.ORDER_ASC.toLowerCase();
					}

				} else {
					order_type = constant.MAIEL.toLowerCase();
					order_rule = constant.ORDER_DESC.toLowerCase();
				}

				startThread(getType(type));

			}
		});

	}

	private void initTitle() {

		title = cityList.getProvince_name().indexOf("黑龙江") != -1 ? "黑龙江" : cityList.getProvince_name().substring(0, 2);

		if (type.equals(constant.ADMIN_LEVEL)) {
			title += StatisticsActivityDetail.ADMIN_LEVEL;
		}
		if (type.equals(constant.BRIDGE_CLASS)) {
			title += StatisticsActivityDetail.BRIDGE_CLASS;
		}
		if (type.equals(constant.RANK_LEVEL)) {
			title += StatisticsActivityDetail.RANK_LEVEL;
		}
		if (type.equals(constant.ROAD_FACE_TYPE)) {
			title += StatisticsActivityDetail.ROAD_FACE_TYPE;
		}
		if (type.equals(constant.TECH_LEVEL)) {
			title += StatisticsActivityDetail.TECH_LEVEL;
		}
		if (type.equals(constant.TUNNEL_CLASS)) {
			title += StatisticsActivityDetail.TUNNEL_CLASS;
		}
		tvTitle.setText(title);

	}

	public void startThread(int type) {

		switch (type) {
		// 基础类的调用网络方式
		case 1:
			dialog.Run(MapStatisticsActivity.this, new baseNet(), R.string.com_loading);
			break;
		case 2:
			dialog.Run(MapStatisticsActivity.this, new bridgeNet(), R.string.com_loading);
			break;
		case 3:
			dialog.Run(MapStatisticsActivity.this, new tunnelNet(), R.string.com_loading);
			break;

		default:
			break;
		}

	}

	private void SetData(List<ResultsBean> objects) {

		adater = new MyAdater(MapStatisticsActivity.this, 0, objects);
		mapStatisticsRouteLv.setAdapter(adater);
		adater.notifyDataSetChanged();
		if (constant.NAME.toLowerCase().equals(order_type)) {
			imgMapStatisticsLength.setVisibility(View.INVISIBLE);
			imgMapStatisticsName.setVisibility(View.VISIBLE);
			if (constant.ORDER_ASC.toLowerCase().equals(order_rule)) {
				imgMapStatisticsName.setBackgroundResource(R.drawable.sort_top);

			} else {
				imgMapStatisticsName.setBackgroundResource(R.drawable.sort_buttom);
			}

		} else {
			imgMapStatisticsLength.setVisibility(View.VISIBLE);
			imgMapStatisticsName.setVisibility(View.INVISIBLE);

			if (constant.ORDER_ASC.toLowerCase().equals(order_rule)) {
				imgMapStatisticsLength.setBackgroundResource(R.drawable.sort_top);

			} else {
				imgMapStatisticsLength.setBackgroundResource(R.drawable.sort_buttom);
			}

		}

	}

	class baseNet implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub

			mapStaticsbase = CustomApp.app.connInteface.MapStaticsbase(year, province_code, order_rule, order_type, statistic_type);

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			// TODO Auto-generated method stub

			int correct = Utils.isCorrect(mapStaticsbase, false);
			if (correct == 0) {
				SetData(mapStaticsbase.getResults());
			} else if (correct == 2) {
				CustomApp.app.exit(MapStatisticsActivity.this);
			}
			return false;
		}

	}

	class bridgeNet implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			mapStaticsbase = CustomApp.app.connInteface.MapStaticsbridge(year, province_code, order_rule, order_type, bridge_type);
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			// TODO Auto-generated method stub
			int correct = Utils.isCorrect(mapStaticsbase, false);
			if (correct == 0) {
				SetData(mapStaticsbase.getResults());
			} else if (correct == 2) {
				CustomApp.app.exit(MapStatisticsActivity.this);
			}
			return false;
		}

	}

	class tunnelNet implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			mapStaticsbase = CustomApp.app.connInteface.MapStaticsTunnel(year, province_code, order_rule, order_type, tunnel_type);
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			// TODO Auto-generated method stub
			int correct = Utils.isCorrect(mapStaticsbase, false);
			if (correct == 0) {
				SetData(mapStaticsbase.getResults());
			} else if (correct == 2) {
				CustomApp.app.exit(MapStatisticsActivity.this);
			}
			return false;
		}

	}

	private int getType(String type) {

		if (type.equals(constant.ADMIN_LEVEL)) {
			statistic_type = constant.ADMIN_LEVEL.toLowerCase();
			return 1;
		}
		if (type.equals(constant.ROAD_FACE_TYPE)) {
			statistic_type = constant.ROAD_FACE_TYPE.toLowerCase();
			return 1;
		}
		if (type.equals(constant.TECH_LEVEL)) {
			statistic_type = constant.TECH_LEVEL.toLowerCase();
			return 1;
		}
		if (type.equals(constant.BRIDGE_CLASS)) {
			if (Utils.isNull(bridge_type)) {
				bridge_type = getIntent().getStringExtra("type");
			}
			return 2;
		}
		if (type.equals(constant.TUNNEL_CLASS)) {
			if (Utils.isNull(tunnel_type)) {
				tunnel_type = getIntent().getStringExtra("type");
			}
			return 3;
		}

		return -1;
	}
}
