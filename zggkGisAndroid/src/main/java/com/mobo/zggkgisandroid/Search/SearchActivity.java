package com.mobo.zggkgisandroid.Search;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.MainActivity;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.City.SelectCityActivity;
import com.mobo.zggkgisandroid.DBEntity.Province;
import com.mobo.zggkgisandroid.DBEntity.SearchContent;
import com.mobo.zggkgisandroid.Search.SearchPopupWindow.PopupOnClickListener;
import com.mobo.zggkgisandroid.ServiceArea.ExpresswayListActivity;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.CityList;
import com.mobo.zggkgisandroid.WebModel.ExpresswayListResult;
import com.mobo.zggkgisandroid.WebModel.ExpresswayListResult.ExpresswayList;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesList;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesList.SearchListBean;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesResult;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesResult.SearchNotice;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 搜索页面
 * 
 * @author WuXiaoHao
 * 
 */
public class SearchActivity extends Activity implements OnRefreshListener {
	private TextView tvRange;// 省份选择
	private EditText etSearch;// 搜索
	private ListView listView;// 底部列表
	private TextView tvHighway;// 省份公路标题
	private LinearLayout llTop;// 顶部内容块
	private LinearLayout linearSearchHistory;// 搜索历史标题
	private LinearLayout linearEmpty;// 清空搜索记录

	private PullToRefreshListView pullToRefreshListView;// 下拉刷新View
	private ListView pListView;// 下拉刷新listview

	private CityList currentCity;// 当前所选的省份

	private List<SearchContent> searchContentsl;// 搜索记录数据

	private String locX, locY;// 经纬度
	private static final int code = 9712;

	private ArrayList<Province> provinces_list;// 省份
	private Activity activity;

	private SearchNoticeListAdapter listAdapter;// 自动搜索列表adapter
	private String research_str;// 搜索关键字
	private String message;
	private String searchType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity_layout);
		CustomApp.app.mActiveLists.add(this);
		activity = this;
		init();
		initContent();
		setRange();
	}

	private void init() {
		tvRange = (TextView) findViewById(R.id.tv_search_range);
		etSearch = (EditText) findViewById(R.id.et_search);
		listView = (ListView) findViewById(R.id.list_search);

		tvHighway = (TextView) findViewById(R.id.tv_search_highway_title);
		llTop = (LinearLayout) findViewById(R.id.ll_search_list);
		linearSearchHistory = (LinearLayout) findViewById(R.id.ll_search_history_title);

		linearEmpty = (LinearLayout) findViewById(R.id.ll_search_history_empty);
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list_search_city);
		pListView = pullToRefreshListView.getRefreshableView();
		pListView.setAdapter(null);
		pullToRefreshListView.setOnRefreshListener(this);

		searchContentsl = CustomApp.app.dbManager.getSearchList();
		currentCity = (CityList) getIntent().getSerializableExtra("currentCity");
		locX = getIntent().getStringExtra("locX");
		locY = getIntent().getStringExtra("locY");
		provinces_list = CustomApp.app.dbManager.queryValuesByType();
		tvRange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("0".equals(CustomApp.app.privilege))
					return;
				Intent intent = new Intent(activity, SelectCityActivity.class);
				intent.putExtra("currentCity", currentCity);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, code);
			}
		});
		etSearch.setOnKeyListener(new OnKeyListener() {// 搜索

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
				if (keyCode == KeyEvent.KEYCODE_ENTER && arg2.getAction() == KeyEvent.ACTION_UP) {
					String content = etSearch.getText().toString().trim();
					if (content.equals(""))
						return true;
					Intent intent = new Intent(activity, MainActivity.class);
					intent.putExtra("province_code", currentCity != null ? currentCity.getProvince_code() : "");
					intent.putExtra("locX", currentCity == null ? locX : "");
					intent.putExtra("locY", currentCity == null ? locY : "");
					intent.putExtra("research_str", etSearch.getText().toString().trim());
					intent.putExtra("type", 10086);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
					return true;
				}
				return false;
			}
		});

		// 新增需求 2016/10/17 15:25
		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (popupWindow != null) {
					popupWindow.dismiss();
				}
				if (s != null && s.length() != 0) {
					new SearchNoticeTask().execute(s.toString());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		// 新增需求 end

		linearEmpty.setOnClickListener(new OnClickListener() {// 清空

					@Override
					public void onClick(View arg0) {
						CustomApp.app.dbManager.calcelSearch();
						if (listView.getAdapter() instanceof SearchAdapter) {// 若是全国列表则刷新一下数据
							searchContentsl.clear();
							((SearchAdapter) listView.getAdapter()).notifyDataSetChanged();
						}
					}
				});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (listView.getAdapter() instanceof SearchAdapter) {// 若是全国列表则刷新一下数据
					SearchContent searchContent = ((SearchAdapter) listView.getAdapter()).getItem(arg2);
					searchContent.setTime(System.currentTimeMillis());
					CustomApp.app.dbManager.updataSearch(searchContent);

					Intent intent = new Intent(activity, MainActivity.class);
					intent.putExtra("province_code", searchContent.getProvince_code());
					intent.putExtra("locX", currentCity == null ? locX : "");
					intent.putExtra("locY", currentCity == null ? locY : "");
					if ("0".equals(searchContent.getType())) {
						intent.putExtra("research_str", searchContent.getBridge_name());
						intent.putExtra("bridge_code", searchContent.getBridge_code());
					} else {
						//intent.putExtra("research_str", searchContent.getCity_name() + " " + searchContent.getRoute_code());
						intent.putExtra("research_str", searchContent.getRoute_code());
					}
					SearchListBean bean = new SearchNoticesList().new SearchListBean();
					bean.setBridge_code(searchContent.getBridge_code());
					bean.setProvince_code(searchContent.getProvince_code());
					bean.setType("2");
					intent.putExtra("SearchListBean", bean);
					intent.putExtra("type", 10086);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();

					// new ThreadWithProgressDialog().Run(activity,
					// new SearchDialogTask(searchContent.getContent()),
					// R.string.com_loading);
				}
			}
		});
		setEdit();
	}

	// 新增自动搜索列表需求 2016/10/18
	/**
	 * 自动搜索列表 2016/10/18
	 */
	/*
	 * private class SearchListTask implements ThreadWithProgressDialogTask{
	 * 
	 * //private String research_str;// 搜索关键字 private SearchNoticesList result;
	 * 
	 * @Override public boolean TaskMain() { //处理耗时任务 result =
	 * CustomApp.app.connInteface.getSearchNoticeList(research_str, currentCity
	 * != null ? currentCity.getProvince_code() : "");
	 * 
	 * return true; }
	 * 
	 * @Override public boolean OnTaskDismissed() { return false; }
	 * 
	 * @Override public boolean OnTaskDone() { // TODO 显示UI操作 int state =
	 * Utils.isCorrect(result, true); if (state == 0) { //
	 * listview去setAdapter将数据显示到UI上 List<SearchListBean> list = new
	 * ArrayList<SearchNoticesList.SearchListBean>(); list =
	 * result.getResults(); listAdapter = new SearchNoticeListAdapter(activity,
	 * list); listView.setAdapter(listAdapter); } else if (state == 2) {
	 * CustomApp.app.exit(activity); } return false; }
	 * 
	 * }
	 */
	// end

	private boolean singleTap_flag;// 点击不自动提示标识

	private void setEdit() {
		/*
		 * etSearch.addTextChangedListener(new TextWatcher() {
		 * 
		 * @Override public void onTextChanged(CharSequence s, int start, int
		 * before, int count) { if (isClcik) { isClcik = false; return; } if
		 * (popupWindow != null) { popupWindow.dismiss(); } ArrayList<String>
		 * notice_strs = new ArrayList<String>(); if (s != null && s.length() !=
		 * 0 && !singleTap_flag) {
		 * 
		 * // 删除操作 if (before != 0 && count == 0) {
		 * 
		 * boolean pro_or_roa_flag = false;
		 * 
		 * for (int i = 0; i < provinces_list.size(); i++) {
		 * 
		 * String province_name = provinces_list.get(i).getProvince_name();
		 * 
		 * if (province_name.contains(s)) {
		 * 
		 * pro_or_roa_flag = true;
		 * 
		 * }
		 * 
		 * }
		 * 
		 * // 省份删除操作 if (pro_or_roa_flag) {
		 * 
		 * for (int i = 0; i < provinces_list.size(); i++) {
		 * 
		 * String province_name = provinces_list.get(i).getProvince_name();
		 * notice_strs.add(province_name);
		 * 
		 * } initPopWindow(notice_strs);
		 * 
		 * // 公路删除操作 } else {
		 * 
		 * // noticeAdapter.notifyDataSetChanged(); new
		 * SearchNoticeTask().execute(s.toString());
		 * 
		 * // }
		 * 
		 * // 输入操作 } else { boolean pro_or_roa_flag = false;
		 * 
		 * // for (int i = 0; i < provinces_list.size(); i++) { // // if //
		 * (provinces_list.get(i).getProvince_name().contains(s)) // { // //
		 * pro_or_roa_flag = true; // // } // } // // notice_strs.clear(); // //
		 * // 省份输入操作 // if (pro_or_roa_flag) { // // for (int i = 0; i <
		 * provinces_list.size(); i++) { // // String province_name = //
		 * provinces_list.get(i).getProvince_name(); //
		 * notice_strs.add(province_name); // // } //
		 * initPopWindow(notice_strs); // // // 公路输入操作 // } else {
		 * 
		 * new SearchNoticeTask().execute(s.toString());
		 * 
		 * // }
		 * 
		 * }
		 * 
		 * } }
		 * 
		 * @Override public void beforeTextChanged(CharSequence s, int start,
		 * int count, int after) {
		 * 
		 * }
		 * 
		 * @Override public void afterTextChanged(Editable s) {
		 * 
		 * } });
		 */
	}

	boolean isClcik = false;
	SearchPopupWindow popupWindow;

	/**
	 * 初始化popwindow
	 */
	public void initPopWindow(List<SearchListBean> lists, String parm, String word) {
		try {
			if (popupWindow != null) {
				popupWindow.dismiss();
			}
			popupWindow = new SearchPopupWindow(this, etSearch, lists, parm, word);
			popupWindow.setClickListener(new PopupOnClickListener() {

				@Override
				public void myOnClick(String text, SearchListBean bean) {
					isClcik = true;
					etSearch.setText(text);

					String content = etSearch.getText().toString().trim();
					Intent intent = new Intent(activity, MainActivity.class);
					intent.putExtra("province_code", currentCity != null ? currentCity.getProvince_code() : "");
					intent.putExtra("locX", currentCity == null ? locX : "");
					intent.putExtra("locY", currentCity == null ? locY : "");
					intent.putExtra("research_str", etSearch.getText().toString().trim());
					intent.putExtra("SearchListBean", bean);
					intent.putExtra("type", 10086);

					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				}
			});
			popupWindow.show(etSearch);
		} catch (Exception e) {
		}

	}

	/**
	 * 搜索提示异步任务
	 * 
	 * 
	 */

	private class SearchNoticeTask extends AsyncTask<String, Void, String> {
		// SearchNoticesResult result;
		SearchNoticesList results;
		// ArrayList<String> notice_strs = new ArrayList<String>();
		List<SearchListBean> list = new ArrayList<SearchNoticesList.SearchListBean>();

		@Override
		protected void onPostExecute(String r) {

			if (Utils.isCorrect(results, false) == 0 && r.equals(etSearch.getText().toString().trim()) && results.getResults() != null
					&& list.size() != 0) {
				initPopWindow(list, currentCity != null ? currentCity.getProvince_code() : "", search);
			} else if (Utils.isCorrect(results, false) != 0 && r.equals(etSearch.getText().toString().trim()) && list.size() != 0) {
				initPopWindow(list, currentCity != null ? currentCity.getProvince_code() : "", search);
			}

		}

		private String search;

		@Override
		protected String doInBackground(String... params) {
			search = params[0];
			if (currentCity != null && !"123001".equals(currentCity.getProvince_code())) {
				search = currentCity.getProvince_name() + " " + params[0];
			}
			results = CustomApp.app.connInteface.getSearchNoticeList(search, currentCity != null ? currentCity.getProvince_code() : "", "1", "10");
			/*
			 * result = CustomApp.app.connInteface.getSearchNotice(search,
			 * currentCity != null ? currentCity.getProvince_code() : "");
			 */

			if (Utils.isCorrect(results, false) == 0 && params[0].equals(etSearch.getText().toString().trim()) && results.getResults() != null) {
				list.clear();
				list = results.getResults();

				// notice_strs.clear();
				/*
				 * for (SearchListBean searchNotice : results.getResults()) {
				 * notice_strs
				 * .add(searchNotice.getProvince_name()+" "+searchNotice
				 * .getRoad_code()); }
				 */

			} else if (Utils.isCorrect(results, false) != 0 && params[0].equals(etSearch.getText().toString().trim())) {
				list.clear();
			}
			if (results != null) {
				if ("500".equals(results.error_code)) {
					list.clear();
					message = results.error_msg;
					SearchNoticesList.SearchListBean bean = new SearchNoticesList().new SearchListBean();
					bean.setProvince_code("");
					bean.setProvince_name("");
					bean.setResult_mil_num("");
					bean.setResult_name(message);
					bean.setRoad_code("");
					bean.setType("");
					String s = bean.getResult_name();
					list.add(bean);
				}

			}

			return params[0];
		}

	}

	int height;

	private void initContent() {
		height = (Utils.getWidth(this) - Utils.dip2px(this, 60)) / 5 / 4 * 3;
		LinearLayout layout = (LinearLayout) findViewById(R.id.ll_search_list);
		for (int i = 0; i < 2; i++) {
			ViewGroup viewGroup = (ViewGroup) layout.getChildAt(i);
			for (int j = 0; j < viewGroup.getChildCount(); j++) {
				ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(j);
				viewGroup2.setTag(j + i * 5);
				View view = viewGroup2.getChildAt(0);
				view.getLayoutParams().height = height;
				view.getLayoutParams().width = height;
				viewGroup2.setOnClickListener(new MyOnClickListener());
			}
		}
	}

	/**
	 * 按钮点击监听
	 * 
	 * @author WuXiaoHao
	 * 
	 */
	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			int tag = (Integer) arg0.getTag();
			switch (tag) {
			case 0:// 桥梁
				if ("0".equals(CustomApp.app.privilege)) {
					Intent intent = new Intent(SearchActivity.this, BridgeChoiceActivity.class);
					startActivityForResult(intent, code);
				} else {
					Intent intent = new Intent(activity, BridgeCityActivity.class);
					intent.putExtra("currentCity", currentCity);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
				break;
			case 1:// 隧道
				CustomApp.app.customToast(R.string.on_the_line_please_look_forward_to);
				break;
			case 2:// 服务区
				CustomApp.app.customToast(R.string.on_the_line_please_look_forward_to);

				// if (currentCity == null) {// 附近
				//
				// } else if ("123001".equals(currentCity.getProvince_code()))
				// {// 全国
				//
				// Intent sIntent = new Intent(activity, MainActivity.class);
				// sIntent.putExtra("currentCity", currentCity);
				// sIntent.putExtra("showType", "service");
				// sIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// startActivity(sIntent);
				//
				// } else {// 省份
				// new ThreadWithProgressDialog().Run(activity, new
				// getExpresswayList(0), R.string.com_loading);
				// }
				break;
			case 3:// 收费站
				CustomApp.app.customToast(R.string.on_the_line_please_look_forward_to);
				break;
			case 4:// 加油站
				CustomApp.app.customToast(R.string.on_the_line_please_look_forward_to);
				break;
			case 5:// 养护站
				CustomApp.app.customToast(R.string.on_the_line_please_look_forward_to);
				break;
			case 6:// 施工点
				CustomApp.app.customToast(R.string.on_the_line_please_look_forward_to);
				// if (currentCity == null) {// 附近
				//
				// } else if ("123001".equals(currentCity.getProvince_code()))
				// {// 全国
				//
				// } else {// 省份
				// new ThreadWithProgressDialog().Run(activity, new
				// getExpresswayList(1), R.string.com_loading);
				// }
				break;
			case 7:// 灾毁
				CustomApp.app.customToast(R.string.on_the_line_please_look_forward_to);
				// if (currentCity == null) {// 附近
				//
				// } else if ("123001".equals(currentCity.getProvince_code()))
				// {// 全国
				//
				// } else {// 省份
				// new ThreadWithProgressDialog().Run(activity, new
				// getExpresswayList(2), R.string.com_loading);
				// }
				break;
			case 8:// 视频
				CustomApp.app.customToast(R.string.on_the_line_please_look_forward_to);
				break;
			case 9:// 其他
				CustomApp.app.customToast(R.string.on_the_line_please_look_forward_to);
				break;
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == code && resultCode == SelectCityActivity.code) {
			CityList cityList = (CityList) data.getSerializableExtra("currentCity");
			if (cityList == null && currentCity == null)
				return;
			if (cityList != null && currentCity != null && cityList.getProvince_code().equals(currentCity.getProvince_code()))
				return;
			currentCity = cityList;
			setRange();
		}
		if (requestCode == code && resultCode == BridgeChoiceActivity.code) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("showType", "bridge");
			intent.putExtra("currentCity", currentCity);
			intent.putExtra("bridgeType", data.getStringExtra("bridgeType"));
			startActivity(intent);
		}
	}

	/**
	 * 设置省份数据
	 */
	private void setRange() {
		listView.setAdapter(null);
		pListView.setAdapter(null);
		current_page = 0;
		result = null;
		results = null;
		if (currentCity == null) {
			tvRange.setText("附近");
			tvHighway.setText("附近路线");
			tvHighway.setVisibility(View.VISIBLE);
			linearSearchHistory.setVisibility(View.GONE);
			pullToRefreshListView.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
			pullToRefreshListView.setRefreshing(false);
		} else {
			try {
				tvRange.setText(currentCity.getProvince_name().length() > 2 ? currentCity.getProvince_name().substring(0, 2) : currentCity
						.getProvince_name());
			} catch (Exception e) {
				tvRange.setText("");
			}
			if ("123001".equals(currentCity.getProvince_code())) {// 全国
				listView.setAdapter(new SearchAdapter());
				tvHighway.setVisibility(View.GONE);
				linearSearchHistory.setVisibility(View.VISIBLE);
				pullToRefreshListView.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
			} else {// 省份
				tvHighway.setText(currentCity.getProvince_name() + "路线");
				tvHighway.setVisibility(View.VISIBLE);
				linearSearchHistory.setVisibility(View.GONE);
				pullToRefreshListView.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
				pullToRefreshListView.setRefreshing(false);
			}

		}
	}

	/**
	 * 公路适配器
	 * 
	 * @author WuXiaoHao
	 * 
	 */
	private class HighwayAdapter extends BaseAdapter {
		ArrayList<ExpresswayList> expresswayLists;
		DecimalFormat df = new DecimalFormat("0.00");

		public HighwayAdapter(ArrayList<ExpresswayList> expresswayLists) {
			this.expresswayLists = expresswayLists;
		}

		@Override
		public int getCount() {
			return expresswayLists != null ? expresswayLists.size() : 0;
		}

		@Override
		public ExpresswayList getItem(int arg0) {
			return expresswayLists.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder viewHolder;
			if (arg1 == null) {
				viewHolder = new ViewHolder();
				arg1 = LayoutInflater.from(activity).inflate(R.layout.search_highway_list_item, arg2, false);
				viewHolder.tvName = (TextView) arg1.findViewById(R.id.tv_serch_highway_list_item);
				viewHolder.tvDistance = (TextView) arg1.findViewById(R.id.tv_serch_highway_list_item_distance);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			ExpresswayList expresswayList = getItem(arg0);
			if (expresswayList != null) {
				viewHolder.tvName.setText(expresswayList.getRoad_name());
				viewHolder.tvDistance.setText(expresswayList.getRoad_mail() + " 公里");
			}
			return arg1;
		}

		private class ViewHolder {
			TextView tvName;// 公路名字 公路代码
			TextView tvDistance;// 全程
		}

		public void notifyDataSetChanged(ArrayList<ExpresswayList> results) {
			this.expresswayLists = results;
			notifyDataSetChanged();
		}
	}

	/**
	 * 搜索历史适配器
	 * 
	 * @author WuXiaoHao
	 * 
	 */
	private class SearchAdapter extends BaseAdapter {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");

		public SearchAdapter() {
			searchContentsl = CustomApp.app.dbManager.getSearchList();
		}

		@Override
		public int getCount() {
			return searchContentsl != null ? searchContentsl.size() : 0;
		}

		@Override
		public SearchContent getItem(int arg0) {
			return searchContentsl.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder viewHolder;
			if (arg1 == null) {
				viewHolder = new ViewHolder();
				arg1 = LayoutInflater.from(activity).inflate(R.layout.search_list_item, arg2, false);
				viewHolder.tvSearch = (TextView) arg1.findViewById(R.id.tv_serch_list_item);
				viewHolder.tvLength = (TextView) arg1.findViewById(R.id.tv_serch_list_item_length);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			SearchContent content = getItem(arg0);
			if ("0".equals(content.getType())) {// 桥梁
				try {
					viewHolder.tvSearch.setText(content.getBridge_name() + " " + content.getRoute_code() + " "
							+ (content.getCity_name().indexOf("黑龙江") != -1 ? "黑龙江" : content.getCity_name().substring(0, 2)));
				} catch (Exception e) {
					viewHolder.tvSearch.setText(content.getBridge_name() + " " + content.getRoute_code() + " ");
				}

				viewHolder.tvLength.setText(content.getBridge_length() + " m");
			} else if ("1".equals(content.getType())) {
				try {
					viewHolder.tvSearch.setText(content.getRoute_code() + " " + content.getRoute_name() + " "
							+ (content.getCity_name().indexOf("黑龙江") != -1 ? "黑龙江" : content.getCity_name().substring(0, 2)));
				} catch (Exception e) {
					viewHolder.tvSearch.setText(content.getRoute_code() + " " + content.getRoute_name() + " ");
				}
				viewHolder.tvLength.setText(content.getRoute_length() + " km");
			} else {
				viewHolder.tvSearch.setText(content.getRoute_code() + " " + content.getRoute_name());
				viewHolder.tvLength.setText(content.getRoute_length() + " km");
			}
			return arg1;
		}

		private class ViewHolder {
			TextView tvSearch;// 搜索历史
			TextView tvLength;
		}
	}

	// /**
	// * 搜索
	// *
	// * @author WuXiaoHao
	// *
	// */
	// private class SearchDialogTask implements ThreadWithProgressDialogTask {
	// private String research_str;// 搜索关键字
	// private SearchResult result;
	//
	// public SearchDialogTask(String research_str) {
	// this.research_str = research_str;
	// }
	//
	// @Override
	// public boolean TaskMain() {
	// result = CustomApp.app.connInteface.SearchInfo(currentCity != null ?
	// currentCity.getProvince_code() : "",
	// currentCity == null ? locX : "", currentCity == null ? locY : "", "",
	// CustomApp.app.token,
	// research_str, "1");
	// return true;
	// }
	//
	// @Override
	// public boolean OnTaskDismissed() {
	// return false;
	// }
	//
	// @Override
	// public boolean OnTaskDone() {
	// int state = Utils.isCorrect(result, true);
	// if (state == 0) {
	// Intent intent = new Intent(activity, MainActivity.class);
	// intent.putExtra("result", result);
	// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	//
	// startActivity(intent);
	// activity.finish();
	// } else if (state == 2) {
	// CustomApp.app.exit(activity);
	// }
	// return false;
	// }
	//
	// }

	/**
	 * 获取高速公路列表
	 * 
	 * @author WuXiaoHao
	 * 
	 */
	private class getExpresswayList implements ThreadWithProgressDialogTask {
		private ExpresswayListResult result;
		private int type;

		public getExpresswayList(int type) {
			this.type = type;
		}

		@Override
		public boolean TaskMain() {
			result = CustomApp.app.connInteface.getExpresswayList(currentCity.getProvince_code(), "", "", "0", "10");
			result = new ExpresswayListResult();
			result.setStatus("Y");
			ArrayList<ExpresswayList> list = new ArrayList<ExpresswayList>();
			ExpresswayList expresswayList = new ExpresswayListResult().new ExpresswayList();
			expresswayList.setRoad_code("G312");
			expresswayList.setRoad_name("京藏公路");
			list.add(expresswayList);
			expresswayList = new ExpresswayListResult().new ExpresswayList();
			expresswayList.setRoad_code("G312");
			expresswayList.setRoad_name("京藏公路");
			list.add(expresswayList);
			expresswayList = new ExpresswayListResult().new ExpresswayList();
			expresswayList.setRoad_code("G312");
			expresswayList.setRoad_name("京藏公路");
			list.add(expresswayList);
			expresswayList = new ExpresswayListResult().new ExpresswayList();
			expresswayList.setRoad_code("G312");
			expresswayList.setRoad_name("京藏公路");
			list.add(expresswayList);
			expresswayList = new ExpresswayListResult().new ExpresswayList();
			expresswayList.setRoad_code("G312");
			expresswayList.setRoad_name("京藏公路");
			list.add(expresswayList);
			result.setResults(list);
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
				Intent intent = new Intent();
				intent.setClass(activity, ExpresswayListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("result", result.getResults());
				intent.putExtra("currentCity", currentCity);
				intent.putExtra("locX", locX);
				intent.putExtra("locY", locY);
				intent.putExtra("type", type);
				startActivity(intent);
			} else if (state == 2) {
				CustomApp.app.exit(activity);
			}
			return false;
		}

	}

	private ExpresswayListResult result;
	private int current_page = 1;
	private HighwayAdapter highwayAdapter;
	private ArrayList<ExpresswayList> results;

	private void getData(int current_page) {
		result = CustomApp.app.connInteface.getExpresswayList(currentCity != null ? currentCity.getProvince_code() : "", currentCity == null ? locX
				: "", currentCity == null ? locY : "", current_page + "", "10");
		if (Utils.isCorrect(result, false) == 0) {
			this.current_page = current_page;
		}
	}

	@Override
	public boolean onRefresh() {
		getData(1);
		return false;
	}

	@Override
	public void onRefreshView(boolean message) {
		int status = Utils.isCorrect(result, true);
		if (status == 0) {
			results = result.getResults();
			highwayAdapter = new HighwayAdapter(result.getResults());
			pListView.setAdapter(highwayAdapter);
		} else if (status == 2) {
			CustomApp.app.exit(activity);
		}
		// if (highwayAdapter == null || highwayAdapter.getCount() == 0) {
		// netWork.setVisibility(View.VISIBLE);
		// } else {
		// netWork.setVisibility(View.GONE);
		// }
	}

	@Override
	public boolean onLoad() {
		if (highwayAdapter == null || highwayAdapter.getCount() == 0) {
			getData(1);
		} else {
			getData(current_page + 1);
		}
		return false;
	}

	@Override
	public void onLoadView(boolean message) {
		int status = Utils.isCorrect(result, true);
		if (status == 0) {
			if (result.getResults() != null) {
				if (results == null) {
					results = result.getResults();
				} else if (result.getResults() != null) {
					results.addAll(result.getResults());
				}
				if (highwayAdapter == null) {
					highwayAdapter = new HighwayAdapter(results);
					pListView.setAdapter(highwayAdapter);
				} else {
					highwayAdapter.notifyDataSetChanged(results);
				}
			}
		} else if (status == 2) {
			CustomApp.app.exit(activity);
		}
		// if (highwayAdapter == null || highwayAdapter.getCount() == 0) {
		// v.setVisibility(View.INVISIBLE);
		// } else {
		// v.setVisibility(View.VISIBLE);
		// }
	}

}
