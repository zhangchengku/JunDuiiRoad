package com.mobo.zggkgisandroid.Info;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mobo.zggkgisandroid.R;

;

/**
 * 年份选择popwindow
 * 
 * @author xsw
 * 
 */
public class GetYearPopWindowTwo {

	private Activity mAty;
	private ListView lv;// 年
	private LinearLayout vParentRly;// 父布局
	private View vGroup;

	private List<String> listdata;
	private PopupWindow popupWindow;// popWindow
	private OnClickYear onClickYear;

	public GetYearPopWindowTwo(Activity aty, List<String> listdata, View vGroup) {
		this.mAty = aty;
		this.listdata = listdata;
		this.vGroup = vGroup;
		View contentView = View.inflate(mAty, R.layout.set_year_pop_window, null);
		popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		popupWindow.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(mAty.getResources().getColor(R.color.zl_black_transparency));
		popupWindow.setBackgroundDrawable(dw);
		lv = (ListView) contentView.findViewById(R.id.year_lv);
		vParentRly = (LinearLayout) contentView.findViewById(R.id.pop_parent_lin);

		lv.setAdapter(new MyAdpater(mAty, 0, listdata));
		vParentRly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				popupWindow.dismiss();
			}
		});

	}

	public void setOnClickYear(OnClickYear onClickYear) {
		this.onClickYear = onClickYear;
	}

	/**
	 * 适配器
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class MyAdpater extends ArrayAdapter<String> {

		public MyAdpater(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			TextView text_view;
			if (convertView == null) {
				convertView = LayoutInflater.from(mAty).inflate(R.layout.text_view, null);
				text_view = (TextView) convertView.findViewById(R.id.comment_tv);
				convertView.setTag(text_view);
				convertView.setBackgroundResource(R.drawable.list_item_sel);
			} else {
				text_view = (TextView) convertView.getTag();
			}

			text_view.setText(listdata.get(position));
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					if (onClickYear != null) {
						onClickYear.onClick(position);
					}
					popupWindow.dismiss();
				}
			});
			return convertView;
		}
	}

	/**
	 * 弹出View
	 */
	public void showPop() {

		lv.setTranslationX(vGroup.getX());
		popupWindow.showAsDropDown(vGroup, (int) (vGroup.getX()), (int) (vGroup.getY()));
	}

	/**
	 * 关闭pop
	 */
	public void dismissPop() {
		if (popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
	}

	/**
	 * 是否当前为弹出显示状态
	 * 
	 * @return true 显示
	 */
	public boolean isShowing() {
		return popupWindow.isShowing();
	}

	public interface OnClickYear {
		void onClick(int position);
	}
}
