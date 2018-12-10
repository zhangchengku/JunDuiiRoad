package com.mobo.zggkgisandroid.Info;

import java.util.ArrayList;
import java.util.HashMap;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.BaseActivity;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.MainActivity;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Info.TrafficIndexPopupWindow.PopupOnClickListener;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.ProvinceRoadDataResult;
import com.mobo.zggkgisandroid.WebModel.ProvinceRoadDataResult.ProvinceRoadData;
import com.mobo.zggkgisandroid.WebModel.ProvinceSituationResult;
import com.tooklkit.Tooklkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 路况
 * 
 * @author WuXiaoHao
 * 
 */
public class TrafficIndexActivity extends BaseActivity {
	private CityList cityList;// 当前选中的省份
	private ListView listRoute;// 路线分布列表

	private View vRoadName;// 路线名称
	private ImageView imgRoadName;// 路线名称排序箭头

	private View vMap;// 地图区域;

	private View vRoadLength;// 路线长度
	private ImageView imgRoadLength;// 路线长度排序箭头

	private View vIndicatorNumber;;// 路线分布指标
	private TextView tvIndicatorNumber;// 路线分布指标名称
	private ImageView imgIndicatorNumber;// 路线分布指标排序箭头

	private RouteListAdapter adapter;// 适配器
	private String order_type = TrafficIndexId.ROAD_NAME;// 排序类型
	private String order_rule = TrafficIndexId.ORDER_ASC;// 排序规则
	private String indicator_id;// 指标id
	private HashMap<String, ProvinceRoadData> roadCodesMap = new HashMap<String, ProvinceRoadData>();
	private String road_code;// 集合

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentXml(R.layout.traffic_index_activity);
		CustomApp.app.mActiveLists.add(this);
		indicator_id = getIntent().getStringExtra("indicator_id");
		cityList = (CityList) getIntent().getSerializableExtra("currentCity");
		init();

		// 点击排序
		mOrderByClick();
		// setOnTouchListener();
		tvIndicatorNumber.setText(indicator_id);
		new ThreadWithProgressDialog().Run(this, new ProvinceRoadDataDialogTask(indicator_id, order_type, order_rule),
				R.string.com_loading);

	}

	TrafficIndexPopupWindow infoPopupWindow;

	private void init() {
		imgLeft.setImageResource(R.drawable.rank_back_btn_sel);
		tvTitle.setText(
				(cityList.getProvince_name().indexOf("黑龙江") != -1 ? "黑龙江" : cityList.getProvince_name().substring(0, 2))
						+ "路况");
		tvRight.setText("确定");
		listRoute = (ListView) findViewById(R.id.list_traffic_route);
		vRoadName = findViewById(R.id.ll_traffic_road_name);
		imgRoadName = (ImageView) findViewById(R.id.img_traffic_road_name);

		vRoadLength = findViewById(R.id.ll_traffic_road_length);
		imgRoadLength = (ImageView) findViewById(R.id.img_traffic_road_length);

		vIndicatorNumber = findViewById(R.id.ll_traffic_indicator_number);
		tvIndicatorNumber = (TextView) findViewById(R.id.tv_traffic_indicator_number);
		imgIndicatorNumber = (ImageView) findViewById(R.id.img_traffic_indicator_number);
		vMap = findViewById(R.id.ll_traffic_indicator_map);
		vMap.setOnClickListener(new OnClickListener() {// 弹出选择弹出框

			@Override
			public void onClick(View arg0) {
				if (listRoute.getAdapter() == null || listRoute.getAdapter().getCount() == 0)
					return;
				if (infoPopupWindow == null) {
					infoPopupWindow = new TrafficIndexPopupWindow(TrafficIndexActivity.this, arg0);
				}
				infoPopupWindow.show();
				infoPopupWindow.setClickListener(new PopupOnClickListener() {

					@Override
					public void OnClick(String tag, boolean b) {
						if (tag.equals("0")) {
							all();
						} else if (tag.equals("6")) {
							empty();
						} else {
							chick(tag, b);
						}
					}
				});
			}
		});
	}

	boolean isActionMap = false;// 判断按下时是否点击在地图区域

	private void setOnTouchListener() {
		listRoute.setOnTouchListener(new OnTouchListener() {
			int mapWidth;// 地图选取的宽度
			int listWidth;// listView的宽度
			int listHeight;// listView的高度
			int listItemHeight;// listView item的高度
			int x;// 按下的X坐标
			int y;// 按下的Y坐标
			int position = -1;// 上一次滑动到的item

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (listRoute.getAdapter() == null || listRoute.getAdapter().getCount() == 0) {
					isActionMap = false;
					return isActionMap;
				}
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (listWidth == 0) {
						listWidth = listRoute.getWidth();
						mapWidth = vMap.getWidth();
						View firstChild = listRoute.getChildAt(0);
						listItemHeight = firstChild.getHeight() + Tooklkit.dip2px(getApplicationContext(), 2);
						listHeight = listItemHeight * adapter.getCount() - Tooklkit.dip2px(getApplicationContext(), 2);
					}
					x = (int) arg1.getX();
					if (x > listWidth - mapWidth) {
						isActionMap = true;
					}
					break;

				case MotionEvent.ACTION_MOVE:
					if (isActionMap) {
						y = (int) arg1.getY();
						// 判断是否划出边界
						if (y < 0 || y > listHeight)
							return isActionMap;
						// 计算滑动到第几个item
						int index = (int) (y / listItemHeight);
						if (y % listItemHeight != 0) {
							index = index + 1;
						}
						index = index - 1;

						if (position != index) {
							try {
								ProvinceRoadData roadContrast = adapter.getItem(index);
								LinearLayout firstChild = (LinearLayout) listRoute.getChildAt(index);
								ImageView imageView = (ImageView) ((LinearLayout) (firstChild.getChildAt(4)))
										.getChildAt(0);
								if (!roadCodesMap.containsKey(roadContrast.getGuid())) {
									roadCodesMap.put(roadContrast.getGuid(), roadContrast);
									//
									imageView.setImageResource(R.drawable.kpi_switch_open);
								} else {
									roadCodesMap.remove(roadContrast.getGuid());
									//
									imageView.setImageResource(R.drawable.kpi_switch_close);
								}
								position = index;
							} catch (Exception e) {
								return isActionMap;
							}
						}

					}
					break;

				case MotionEvent.ACTION_UP:
					isActionMap = false;
					break;
				}
				return isActionMap;
			}
		});
	}

	/**
	 * 获取listview滑动的Y
	 * 
	 * @return
	 */
	public int getScrollY() {
		View c = listRoute.getChildAt(0);
		if (c == null) {
			return 0;
		}
		int firstVisiblePosition = listRoute.getFirstVisiblePosition();
		int top = c.getTop();
		return -top + firstVisiblePosition * c.getHeight();
	}

	/**
	 * 全部
	 */
	private void all() {
		if (roadCodesMap.size() == adapter.getCount())
			return;
		for (int i = 0; i < adapter.getCount(); i++) {
			ProvinceRoadData data = adapter.getItem(i);
			if (!roadCodesMap.containsKey(data.getGuid())) {
				roadCodesMap.put(data.getGuid(), data);
			}
		}
		adapter.notifyDataSetChanged();
	}

	/**
	 * 清空
	 */
	private void empty() {
		if (roadCodesMap.size() == 0)
			return;
		roadCodesMap.clear();
		adapter.notifyDataSetChanged();
	}

	private void chick(String tag, boolean b) {
		if (b) {
			ArrayList<String> arrayList = new ArrayList<String>();
			for (ProvinceRoadData data : roadCodesMap.values()) {
				if (tag.equals(data.getIndicator_grade())) {
					arrayList.add(data.getGuid());
				}
			}
			for (int i = 0; i < arrayList.size(); i++) {
				roadCodesMap.remove(arrayList.get(i));
			}
		} else {
			for (int i = 0; i < adapter.getCount(); i++) {
				ProvinceRoadData data = adapter.getItem(i);
				if (tag.equals(data.getIndicator_grade()) && !roadCodesMap.containsKey(data.getGuid())) {
					roadCodesMap.put(data.getGuid(), data);
				}
			}
		}

		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onClickLeft(View view) {
		super.onClickLeft(view);
		finish();
	}

	@Override
	protected void onClickRight(View view) {
		super.onClickRight(view);
		// 多条公路用逗号拼接成字符串
		if (roadCodesMap.size() == 0)
			return;
		String road_codes = "";
		for (ProvinceRoadData data : roadCodesMap.values()) {
			road_codes = road_codes + "," + data.getRoad_code();
		}
		road_codes = road_codes.replaceFirst(",", "");

		road_code = road_codes.toString().trim();

		new ThreadWithProgressDialog().Run(TrafficIndexActivity.this, new ProvinceSituationTask(cityList, indicator_id),
				R.string.com_loading);
	}

	/**
	 * 设置列表数据
	 * 
	 * @param roadContrasts
	 * @param indicator_id
	 */
	private void setRouteData(ArrayList<ProvinceRoadData> roadContrasts) {
		if (adapter == null) {
			adapter = new RouteListAdapter(roadContrasts);
			listRoute.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged(roadContrasts);
		}
	}

	/**
	 * 路线分布数据
	 * 
	 * @author WuXiaoHao
	 * 
	 */
	public class ProvinceRoadDataDialogTask implements ThreadWithProgressDialogTask {
		private ProvinceRoadDataResult result;
		private String indicator_id;
		private String orderType;// 排序类型
		private String ordeRule;// 排序方式

		public ProvinceRoadDataDialogTask(String indicator_id, String orderType, String ordeRule) {
			this.indicator_id = indicator_id;
			this.orderType = orderType;
			this.ordeRule = ordeRule;
		}

		@Override
		public boolean TaskMain() {
			result = CustomApp.app.connInteface.provinceRoadData(indicator_id, cityList.getProvince_code(), ordeRule,
					orderType);

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			int state = Utils.isCorrect(result, true);
			if (state == 0) {
				order_type = orderType;
				order_rule = ordeRule;
				if (order_type.equals(TrafficIndexId.ROAD_NAME)) {
					imgRoadName.setVisibility(View.VISIBLE);
					imgRoadLength.setVisibility(View.INVISIBLE);
					imgIndicatorNumber.setVisibility(View.INVISIBLE);
					if (order_rule.equals(TrafficIndexId.ORDER_ASC)) {
						imgRoadName.setBackgroundResource(R.drawable.sort_buttom);
					} else {
						imgRoadName.setBackgroundResource(R.drawable.sort_top);
					}
				} else if (order_type.equals(TrafficIndexId.ROAD_MAIEL)) {
					imgRoadName.setVisibility(View.INVISIBLE);
					imgRoadLength.setVisibility(View.VISIBLE);
					imgIndicatorNumber.setVisibility(View.INVISIBLE);
					if (order_rule.equals(TrafficIndexId.ORDER_ASC)) {
						imgRoadLength.setBackgroundResource(R.drawable.sort_buttom);
					} else {
						imgRoadLength.setBackgroundResource(R.drawable.sort_top);
					}
				} else {
					imgRoadName.setVisibility(View.INVISIBLE);
					imgRoadLength.setVisibility(View.INVISIBLE);
					imgIndicatorNumber.setVisibility(View.VISIBLE);
					if (order_rule.equals(TrafficIndexId.ORDER_ASC)) {
						imgIndicatorNumber.setBackgroundResource(R.drawable.sort_buttom);
					} else {
						imgIndicatorNumber.setBackgroundResource(R.drawable.sort_top);
					}
				}
				setRouteData(result.getResults());
			} else if (state == 2) {
				CustomApp.app.exit(TrafficIndexActivity.this);
			}
			return false;
		}

	}

	/**
	 * 路况指标表格
	 * 
	 * @author WuXiaoHao
	 * 
	 */
	private class RouteListAdapter extends BaseAdapter {
		private ArrayList<ProvinceRoadData> provinceRoadDatas;

		public RouteListAdapter(ArrayList<ProvinceRoadData> provinceRoadDatas) {
			this.provinceRoadDatas = provinceRoadDatas;
		}

		public void notifyDataSetChanged(ArrayList<ProvinceRoadData> provinceRoadDatas) {
			this.provinceRoadDatas = provinceRoadDatas;
			super.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return provinceRoadDatas != null ? provinceRoadDatas.size() : 0;
		}

		@Override
		public ProvinceRoadData getItem(int arg0) {
			return provinceRoadDatas.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			final ViewHolder viewHolder;
			if (arg1 == null) {
				viewHolder = new ViewHolder();
				arg1 = LayoutInflater.from(TrafficIndexActivity.this).inflate(R.layout.traffic_route_list_item, arg2,
						false);
				viewHolder.tvRoadName = (TextView) arg1.findViewById(R.id.tv_traffic_list_road_name);
				viewHolder.tvRoadMail = (TextView) arg1.findViewById(R.id.tv_traffic_list_road_mail);
				viewHolder.tvIndicatorNumber = (TextView) arg1.findViewById(R.id.tv_traffic_list_indicator_number);
				viewHolder.tvIndicatorGrade = (TextView) arg1.findViewById(R.id.tv_traffic_list_indicator_grade);
				viewHolder.imgIndicatorGrade = (ImageView) arg1.findViewById(R.id.img_traffic_list_indicator_grade);
				viewHolder.imgXS = (ImageView) arg1.findViewById(R.id.img_traffic_list_xs);
				viewHolder.llIndicatorGrade = (LinearLayout) arg1.findViewById(R.id.ll_traffic_list_indicator_grade);
				viewHolder.llXS = (LinearLayout) arg1.findViewById(R.id.ll_traffic_list_xs);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			if (arg0 % 2 == 0) {
				viewHolder.tvRoadName.setBackgroundColor(0xffd0d8e8);
				viewHolder.tvRoadMail.setBackgroundColor(0xffd0d8e8);
				viewHolder.tvIndicatorNumber.setBackgroundColor(0xffd0d8e8);
				viewHolder.llIndicatorGrade.setBackgroundColor(0xffd0d8e8);
				viewHolder.llXS.setBackgroundColor(0xffd0d8e8);
			} else {
				viewHolder.tvRoadName.setBackgroundColor(0xffe9edf4);
				viewHolder.tvRoadMail.setBackgroundColor(0xffe9edf4);
				viewHolder.tvIndicatorNumber.setBackgroundColor(0xffe9edf4);
				viewHolder.llIndicatorGrade.setBackgroundColor(0xffe9edf4);
				viewHolder.llXS.setBackgroundColor(0xffe9edf4);

			}
			final ProvinceRoadData roadContrast = getItem(arg0);
			if (roadContrast != null) {
				viewHolder.tvRoadName.setText(roadContrast.getRoad_code() + " " + roadContrast.getRoad_name());
				viewHolder.tvRoadMail.setText(roadContrast.getRoad_mail());
				viewHolder.tvIndicatorNumber.setText(roadContrast.getIndicator_number());
				if ("1".equals(roadContrast.getIndicator_grade())) {
					viewHolder.tvIndicatorGrade.setText("优");
					viewHolder.imgIndicatorGrade.setBackgroundColor(0xff78e039);
				} else if ("2".equals(roadContrast.getIndicator_grade())) {
					viewHolder.tvIndicatorGrade.setText("良");
					viewHolder.imgIndicatorGrade.setBackgroundColor(0xff61fbe7);
				} else if ("3".equals(roadContrast.getIndicator_grade())) {
					viewHolder.tvIndicatorGrade.setText("中");
					viewHolder.imgIndicatorGrade.setBackgroundColor(0xfff4ee73);
				} else if ("4".equals(roadContrast.getIndicator_grade())) {
					viewHolder.tvIndicatorGrade.setText("次");
					viewHolder.imgIndicatorGrade.setBackgroundColor(0xffffaa52);
				} else {
					viewHolder.tvIndicatorGrade.setText("差");
					viewHolder.imgIndicatorGrade.setBackgroundColor(0xfffa5402);
				}
				if (roadCodesMap.containsKey(roadContrast.getGuid())) {
					viewHolder.imgXS.setImageResource(R.drawable.kpi_switch_open);
				} else {
					viewHolder.imgXS.setImageResource(R.drawable.kpi_switch_close);
				}
				viewHolder.imgXS.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!roadCodesMap.containsKey(roadContrast.getGuid())) {
							roadCodesMap.put(roadContrast.getGuid(), roadContrast);
						} else {
							roadCodesMap.remove(roadContrast.getGuid());
						}
						notifyDataSetChanged();
					}
				});

			}

			return arg1;
		}

		private class ViewHolder {
			TextView tvRoadName, tvRoadMail, tvIndicatorNumber, tvIndicatorGrade;// 路线名称，路线长度，PCI，评定等级
			ImageView imgIndicatorGrade, imgXS;// 评定等级,地图显示
			LinearLayout llIndicatorGrade, llXS;// 评定等级，地图显示
		}

	}

	/**
	 * 8.2.省份概况
	 * 
	 * @author WuXiaoHao
	 * 
	 */
	private class ProvinceSituationTask implements ThreadWithProgressDialogTask {
		private ProvinceSituationResult result;
		private CityList cityList;
		private String indicator_id;

		public ProvinceSituationTask(CityList cityList, String indicator_id) {
			this.cityList = cityList;
			this.indicator_id = indicator_id;
		}

		@Override
		public boolean TaskMain() {
			result = CustomApp.app.connInteface.provinceSituation(indicator_id, cityList.getProvince_code(), road_code);
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			int state = Utils.isCorrect(result, true);
			if (state == 0) {
				if (result.getResults() != null) {
					result.getResults().setProvince_code(cityList.getProvince_code());
					Intent intent = new Intent(TrafficIndexActivity.this, MainActivity.class);
					intent.putExtra("province_name", cityList.getProvince_name());
					intent.putExtra("indicator_id", indicator_id);

					intent.putExtra("road_code", road_code);
					intent.putExtra("province_situation_result", result.getResults());

					startActivity(intent);
				}
			} else if (state == 2) {
				CustomApp.app.exit(TrafficIndexActivity.this);
			}
			return false;
		}

	}

	private void mOrderByClick() {

		// 路线名称排序
		vRoadName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String orderType;// 排序类型
				String ordeRule;// 排序方式
				if (order_type.equals(TrafficIndexId.ROAD_NAME)) {
					if (order_rule.equals(TrafficIndexId.ORDER_ASC)) {
						ordeRule = TrafficIndexId.ORDER_DESC;
					} else {
						ordeRule = TrafficIndexId.ORDER_ASC;
					}
					orderType = order_type;
				} else {
					orderType = TrafficIndexId.ROAD_NAME;
					ordeRule = TrafficIndexId.ORDER_ASC;
				}
				new ThreadWithProgressDialog().Run(TrafficIndexActivity.this,
						new ProvinceRoadDataDialogTask(indicator_id, orderType, ordeRule), R.string.com_loading);

			}
		});

		// 路线长度排序
		vRoadLength.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String orderType;// 排序类型
				String ordeRule;// 排序方式
				if (order_type.equals(TrafficIndexId.ROAD_MAIEL)) {
					if (order_rule.equals(TrafficIndexId.ORDER_ASC)) {
						ordeRule = TrafficIndexId.ORDER_DESC;
					} else {
						ordeRule = TrafficIndexId.ORDER_ASC;
					}
					orderType = order_type;
				} else {
					orderType = TrafficIndexId.ROAD_MAIEL;
					ordeRule = TrafficIndexId.ORDER_ASC;
				}
				new ThreadWithProgressDialog().Run(TrafficIndexActivity.this,
						new ProvinceRoadDataDialogTask(indicator_id, orderType, ordeRule), R.string.com_loading);
			}
		});

		// 指标值排序
		vIndicatorNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String orderType;// 排序类型
				String ordeRule;// 排序方式
				if (order_type.equals(TrafficIndexId.PCI)) {
					if (order_rule.equals(TrafficIndexId.ORDER_ASC)) {
						ordeRule = TrafficIndexId.ORDER_DESC;
					} else {
						ordeRule = TrafficIndexId.ORDER_ASC;
					}
					orderType = order_type;
				} else {
					orderType = TrafficIndexId.PCI;
					ordeRule = TrafficIndexId.ORDER_ASC;
				}
				new ThreadWithProgressDialog().Run(TrafficIndexActivity.this,
						new ProvinceRoadDataDialogTask(indicator_id, orderType, ordeRule), R.string.com_loading);
			}
		});

	}

}
