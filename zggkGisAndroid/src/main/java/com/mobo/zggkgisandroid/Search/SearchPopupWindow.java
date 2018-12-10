package com.mobo.zggkgisandroid.Search;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesList;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesList.SearchListBean;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 搜索页面弹出框
 * 
 * @author WuXiaoHao
 *
 */
public class SearchPopupWindow implements OnRefreshListener{
	
	private PopupWindow popMenu;//弹窗
	private ListView listView;//listview控件
	private PullToRefreshListView listview;//第三方控件，带刷新的listview
	private PopupOnClickListener clickListener;//监听器
	int page = 1;//第几页
	int pageSize = 10;//每页显示几项
	private SearchNoticesList results;
	private String parm;//省份编码
	private String word;//关键字
	private SearchPopupAdpater adapter;
	//private List<String> obj;
	private List<SearchListBean> lists;
	private boolean noData;//取消点击事件

	public SearchPopupWindow(Activity activity, View etSearch, List<SearchListBean> lists,String parm,String word) {
		this.parm = parm;
		this.word = word;
		this.lists = lists;
		View popView = LayoutInflater.from(activity).inflate(R.layout.search_popup_layout, null);
		popMenu = new PopupWindow(popView, etSearch.getWidth(), Utils.dip2px(activity, 250));
		// popMenu.setFocusable(true);
		// popView.setFocusableInTouchMode(false);
		// popMenu.setOutsideTouchable(true);
		popMenu.setBackgroundDrawable(new BitmapDrawable());
		popMenu.setTouchable(true);
		popMenu.setOutsideTouchable(true);
		popView.setBackgroundColor(0xffffffff);
		listview = (PullToRefreshListView) popView.findViewById(R.id.list_searth);
		adapter = new SearchPopupAdpater(lists,activity);
		listView = listview.getRefreshableView();
		listview.setOnRefreshListener(this);
		listView.setAdapter(new SearchPopupAdpater(lists, activity));
	}

	public void setClickListener(PopupOnClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public void show(View etSearch) {
		popMenu.showAsDropDown(etSearch, 0, 0);

	}

	public void dismiss() {
		if (popMenu.isShowing()) {
			popMenu.dismiss();
		}
	}

	private class SearchPopupAdpater extends BaseAdapter {
	//	List<String> mObjects;
		Activity activity;
		private List<SearchListBean> lists;

		public SearchPopupAdpater(List<SearchListBean> lists, Activity activity) {
			this.lists = lists;
			this.activity = activity;
		}

		@Override
		public int getCount() {
			return lists != null ? lists.size() : 0;
		}

		@Override
		public SearchListBean getItem(int arg0) {
			return lists.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null){
				convertView = LayoutInflater.from(activity).inflate(R.layout.search_list_item, parent, false);
				holder = new ViewHolder();
				holder.tv_serch_list_item = (TextView) convertView.findViewById(R.id.tv_serch_list_item);
				holder.tv_serch_list_item_length = (TextView) convertView.findViewById(R.id.tv_serch_list_item_length);
				holder.image_left = (ImageView)convertView.findViewById(R.id.image_left);
				holder.image_right = (ImageView)convertView.findViewById(R.id.image_right);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			final SearchListBean bean = lists.get(position);
			holder.tv_serch_list_item.setText(bean.getRoad_code()+" "+bean.getResult_name()+" "+bean.getProvince_name());
			if(bean.getResult_mil_num() != null && !"".equals(bean.getResult_mil_num())){
				holder.tv_serch_list_item_length.setText(bean.getResult_mil_num()+" 公里");
				holder.image_left.setVisibility(View.VISIBLE);
				holder.image_right.setVisibility(View.VISIBLE);
			} else{
				noData = true;
				holder.image_left.setVisibility(View.INVISIBLE);
				holder.image_right.setVisibility(View.INVISIBLE);
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					if(noData){
						return;
					}
					popMenu.dismiss();
					//clickListener.myOnClick(getItem(position).getRoad_code()+" "+getItem(position).getResult_name()+" "+getItem(position).getProvince_name());
					clickListener.myOnClick(getItem(position).getRoad_code(),bean);
				}
			});
			return convertView;
		}

		class ViewHolder{
			private TextView tv_serch_list_item;
			private TextView tv_serch_list_item_length;
			private ImageView image_left;
			private ImageView image_right;
		}
		
		public void delete(){
			lists.clear();
			notifyDataSetChanged();
		}
		
		public void add(List<SearchListBean> lists){
			this.lists.addAll(lists);
			notifyDataSetChanged();
		}

	}

	public interface PopupOnClickListener {
		void myOnClick(String text,SearchListBean bean);
	}

	
	@Override
	public boolean onRefresh() {
		// TODO 下拉调接口
		page = 1;
		//调接口
		results = CustomApp.app.connInteface.getSearchNoticeList(word, parm,String.valueOf(page),String.valueOf(pageSize));
		
		return true;
	}

	@Override
	public void onRefreshView(boolean message) {
		// TODO Auto-generated method stub
		adapter.delete();
		if(results.status.equals("Y")){
			lists = new ArrayList<SearchListBean>();
			lists = results.getResults();
			adapter.add(lists);
			
		}
	
	}

	@Override
	public boolean onLoad() {
		// TODO 上拉加载更多
		page++;
		//调接口
		results = CustomApp.app.connInteface.getSearchNoticeList(word, parm,String.valueOf(page),String.valueOf(pageSize));
		return true;
	}

	@Override
	public void onLoadView(boolean message) {
		if(results.status.equals("Y")){
			lists = new ArrayList<SearchListBean>();
			lists = results.getResults();
			adapter.add(lists);
			
		}
	}
}
