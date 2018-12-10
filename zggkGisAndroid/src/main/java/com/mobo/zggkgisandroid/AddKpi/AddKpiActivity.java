package com.mobo.zggkgisandroid.AddKpi;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.MainActivity;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.AddKpi.KpiAdapter.SwitchCallback;
import com.mobo.zggkgisandroid.ClassfyInfo.Indicator;
import com.mobo.zggkgisandroid.Info.TrafficIndexActivity;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebInterface.ConnectionInteface;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.IndicatorResult;
import com.mobo.zggkgisandroid.WebModel.ProvinceSituationResult;
import com.mobo.zggkgisandroid.WebModel.SearchResult;

/**
 * 
 * 
 * 
 * @author xushiwei 此类为添加指标的类
 */
public class AddKpiActivity extends Activity {

	/******************** s声明控件 **************************/

	private ListView vAdd_kpi_lv;//
	private TextView vOk_txtv;
	private TextView vCanecl_txtv;
	private List list;
	private SearchResult searchResult;// 搜索结果类
	private ThreadWithProgressDialog threadManage;// 线程管理者
	private IndicatorResult indicatorResult;// 指标列表
	private ArrayList<Indicator> indicators;// 原始数据类
	private KpiAdapter adapter;// 适配器
	private Intent intent2;// 启动界面的意图
	private Intent intent;// 返回意图
	private String object;
	public static Activity instance;
	private String isFinish;
	private String road_code;
	private CityList cityList;
	private String isProvince;// 选择省份后选择列表
	private String key;
	private String province_name;
	private String province_code;
	private String indicator_id;
	private int xswtag;
	private CityList currentCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CustomApp.app.mActiveLists.add(this);
		Window window = this.getWindow();
		LayoutParams attributes = window.getAttributes();
		attributes.alpha = 0.8f;

		window.setAttributes(attributes);
		// 去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置页面
		setContentView(com.mobo.zggkgisandroid.R.layout.add_kpi_background);
		// 初始化控件

		instance = this;
		init();
		// 初始化数据
		initData();
		// 联网获取相应的指标信息
		getData();

	}

	private void initData() {

		startTheard(0);
	}

	/**
	 * 
	 * 控件初始化
	 */
	public void init() {
		// 获得启动该页面的意图里面的数据
		intent2 = getIntent();
		Bundle extras = intent2.getExtras();
		object = (String) extras.get("query_indicator_id");

		Log.i("============", "====indicator_id==" + object);

		isFinish = intent2.getStringExtra("isFinish");

		if (isFinish != null) {
			road_code = intent2.getStringExtra("road_code");
			cityList = (CityList) intent2.getSerializableExtra("cityList");
		}

		isProvince = intent2.getStringExtra("province");
		if (isProvince != null) {

			cityList = (CityList) intent2.getSerializableExtra("currentCity");
		}
		key = intent2.getStringExtra("key");
		if (!Utils.isNull(key)) {
			province_name = intent2.getStringExtra("province_name");
			province_code = intent2.getStringExtra("province_code");
		}
		xswtag = intent2.getIntExtra("xswtag", 0);
		if (xswtag == 2) {
			currentCity = (CityList) intent2
					.getSerializableExtra("currentCity");

		}

		// 初始化线程管理
		threadManage = new ThreadWithProgressDialog();
		// 显示的listview
		vAdd_kpi_lv = (ListView) findViewById(com.mobo.zggkgisandroid.R.id.add_kpi_lv);
		// 返回数据的意图类
		intent = new Intent();
		// 确定按钮
		// vOk_txtv = (TextView) findViewById(R.id.add_kpi_background_ok);

		// vOk_txtv.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// // 获取当前选择的选项传到返回意图中
		// intent.putExtra("qureyIndicatorId",
		// indicators.get(adapter.getIndex()).getIndicator_id());
		// setResult(1, intent);
		// AddKpiActivity.this.finish();
		//
		// }
		// });
		// // 取消按钮
		// vCanecl_txtv = (TextView)
		// findViewById(R.id.add_kpi_background_cancel);
		// vCanecl_txtv.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// // 返回没做处理 添加相应的返回码
		// setResult(2, intent);
		// AddKpiActivity.this.finish();
		//
		// }
		// });

		// CustomApp.app.connInteface.Login(user_name, password)

	}

	/**
	 * 获取指标信息
	 * 
	 */
	private void getData() {
		if (searchResult != null) {
			// String province_code,String token,String road_code,String
			// indicator_id
			String province_code = searchResult.getResults().get(0)
					.getProvince_code();
			String token = CustomApp.app.token;

			int result_size = 0;

			if (searchResult.getResults().get(0).getResult_size() != null) {

				result_size = Integer.valueOf(searchResult.getResults().get(0)
						.getResult_size());
				StringBuffer road_code = new StringBuffer();
				for (int i = 0; i < result_size; i++) {
					road_code.append(searchResult.getResults().get(0)
							.getRoad_list().get(i).getRoad_code());
					// searchResult.getResults().get(0).getRoad_list().get(i).getRoad_code()+"";
					if (i < result_size - 1) {
						road_code.append(",");
					}
				}

				String result_type = searchResult.getResults().get(0)
						.getResult_type();// 返回类型
				String road_codes = road_code.toString();
			}

			// String indicator_id=

		}

	}

	/**
	 * 起线程
	 * 
	 * @param type
	 *            类型
	 */
	private void startTheard(int type) {
		if (Utils.detect(this)) {
			// threadType = type;

			switch (type) {
			case 0:// 获取指标类型数量
				threadManage.Run(this, new GetIndicatorTypeNumTask(),
						R.string.com_loading);
				break;

			default:
				break;
			}

		} else {
			CustomApp.app.customToast(Gravity.CENTER,
					CustomApp.SHOW_TOAST_TIMES, R.string.com_network_wrong);
		}
	}

	/**
	 * 动态获取指类型数量
	 * 
	 * 
	 */
	private class GetIndicatorTypeNumTask implements
			ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {

			CustomApp.app.connInteface = new ConnectionInteface(
					AddKpiActivity.this);

			indicatorResult = CustomApp.app.connInteface.getIndicator();

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {

			if (indicatorResult != null
					&& indicatorResult.getStatus().equals("Y")) {

				indicators = indicatorResult.getResults();

				// adapter = new AddKpiAdapter(
				// AddKpiActivity.this,
				// com.mobo.zggkgisandroid.R.layout.add_kpi_listitem_layout,
				// indicators);

				adapter = new KpiAdapter(AddKpiActivity.this,
						R.layout.add_kpi_listitem_layout, indicators,
						new SwitchCallback() {

							@Override
							public void SwitchClick(View v) {

								for (int i = 0; i < indicators.size(); i++) {

									ImageView imageButton = (ImageView) vAdd_kpi_lv
											.getChildAt(i).findViewById(i);
									imageButton
											.setImageResource(R.drawable.kpi_switch_close);

									if (((ImageView) v).getTag() == null) {

										((ImageView) v).setTag(0);
									}

									if (imageButton.getId() == v.getId()) {

										if (((ImageView) v).getTag().equals(0)) {
											((ImageView) v)
													.setImageResource(R.drawable.kpi_switch_open);
											((ImageView) v).setTag(1);
										} else {
											((ImageView) v).setTag(0);
										}
									} else {
										imageButton.setTag(0);
									}

								}

								// 开
								if (((ImageView) v).getTag().equals(1)) {

									if (indicators.get(((ImageView) v).getId())
											.getIndicator_id() != null) {

										intent.putExtra(
												"qureyIndicatorId",
												indicators
														.get(((ImageView) v)
																.getId())
														.getIndicator_id());

									} else {

										intent.putExtra("qureyIndicatorId", "");
									}

								} else {
									intent.putExtra("qureyIndicatorId", "0");
								}

								if (isFinish != null && "yes".equals(isFinish)) {

									if (((ImageView) v).getTag().equals(1)) {

										new ThreadWithProgressDialog()
												.Run(AddKpiActivity.this,
														new ProvinceSituationTask(
																cityList,
																indicators
																		.get(((ImageView) v)
																				.getId())
																		.getIndicator_id()),
														R.string.com_loading);
									}

								} else if (isProvince != null) {

									Intent intent = new Intent();

									intent.setClass(AddKpiActivity.this,
											TrafficIndexActivity.class);
									intent.putExtra("indicator_id", indicators
											.get(((ImageView) v).getId())
											.getIndicator_id());

									intent.putExtra("currentCity", cityList);

									startActivity(intent);

								} else if (key != null) {
									Intent intent = new Intent();

									intent.setClass(AddKpiActivity.this,
											MainActivity.class);
									intent.putExtra("indicator_id", indicators
											.get(((ImageView) v).getId())
											.getIndicator_id());
									intent.putExtra("key", key);
									intent.putExtra("province_name",
											province_name);
									intent.putExtra("province_code",
											province_code);
									intent.putExtra("road_code", "");
									intent.putExtra("province_code",
											province_code);
									startActivity(intent);
									finish();

								} else if (xswtag == 2) {

									Intent intent = new Intent();

									intent.setClass(AddKpiActivity.this,
											TrafficIndexActivity.class);
									intent.putExtra("currentCity", currentCity);
									intent.putExtra("indicator_id", indicators
											.get(((ImageView) v).getId())
											.getIndicator_id());

									startActivity(intent);
									finish();

								} else {

									setResult(1, intent);
									finish();
								}

							}
						});
				// 单选当前的指标按钮 为启动该页面的指标一致
				for (int i = 0; i < indicators.size(); i++) {

					if (indicators.get(i).getIndicator_id() != null) {

						if (object != null) {

							if (object.toLowerCase().equals(
									indicators.get(i).getIndicator_id()
											.toLowerCase())) {

								adapter.setIndex(i);
								break;
							} else {
								adapter.setIndex(-1);
							}
						} else {
							adapter.setIndex(-1);
						}
					}

				}

				vAdd_kpi_lv.setAdapter(adapter);

				Utils.setListViewHeightBasedOnChildren(vAdd_kpi_lv);

			}

			return true;
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
			result = CustomApp.app.connInteface.provinceSituation(indicator_id,
					cityList.getProvince_code(), road_code);
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
					result.getResults().setProvince_code(
							cityList.getProvince_code());
					Intent intent = new Intent(AddKpiActivity.this,
							MainActivity.class);
					intent.putExtra("province_name",
							cityList.getProvince_name());
					intent.putExtra("indicator_id", indicator_id);

					intent.putExtra("road_code", road_code);
					intent.putExtra("province_situation_result",
							result.getResults());

					startActivity(intent);
				}
			} else if (state == 2) {
				CustomApp.app.exit(AddKpiActivity.this);
			}
			return false;
		}

	}

}
