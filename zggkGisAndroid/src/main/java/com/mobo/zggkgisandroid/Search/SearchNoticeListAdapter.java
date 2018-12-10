package com.mobo.zggkgisandroid.Search;

import java.util.List;

import com.mobo.zggkgisandroid.WebModel.SearchNoticesList;
import com.mobo.zggkgisandroid.WebModel.SearchNoticesList.SearchListBean;
import com.mobo.zggkgisandroid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 搜索自动提示列表
 *      2016/10/18
 */
public class SearchNoticeListAdapter extends BaseAdapter{

	private List<SearchListBean> lists;//搜索数据源
	private Context context;
	
	public SearchNoticeListAdapter(Context context,List<SearchListBean> lists){
		this.context = context;
		this.lists = lists;
	}
	
	@Override
	public int getCount() {
		return lists != null ? lists.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.search_list_item, parent, false);
			holder = new ViewHolder();
			holder.tv_serch_list_item = (TextView) convertView.findViewById(R.id.tv_serch_list_item);
			holder.tv_serch_list_item_length = (TextView) convertView.findViewById(R.id.tv_serch_list_item_length);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		SearchListBean bean = lists.get(position);
		holder.tv_serch_list_item.setText(bean.getRoad_code()+" "+bean.getResult_name()+" "+bean.getProvince_name());
		holder.tv_serch_list_item_length.setText(bean.getResult_mil_num());
		return convertView;
	}
	
	class ViewHolder{
		private TextView tv_serch_list_item;
		private TextView tv_serch_list_item_length;
	}

}
