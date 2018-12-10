package com.mobo.zggkgisandroid.Info;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobo.zggkgisandroid.R;

;
/**
 * 年份选择popwindow
 * 
 * @author xsw
 * 
 */
public class GetYearPopWindow implements android.view.View.OnClickListener {

	private Activity mAty;
	public PopupWindow vInnerPop;// popWindow
	private List<String> listdata;

	public PopupWindow getvInnerPop() {
		return vInnerPop;
	}

	private ListView lv;// 年
	private LinearLayout vParentRly;// 父布局

	public GetYearPopWindow(Activity aty, List<String> listdata) {
		this.mAty = aty;
		this.listdata = listdata;
		View contentView = View.inflate(mAty, R.layout.set_year_pop_window,
				null);
		vInnerPop = new PopupWindow(contentView,
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		vInnerPop.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(mAty.getResources().getColor(
				R.color.zl_black_transparency));
		vInnerPop.setBackgroundDrawable(dw);
		lv = (ListView) contentView.findViewById(R.id.year_lv);
		vParentRly = (LinearLayout) contentView
				.findViewById(R.id.pop_parent_lin);

		lv.setAdapter(new ArrayAdapter<String>(mAty, 0, listdata) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub

				convertView = LayoutInflater.from(mAty).inflate(
						R.layout.text_view, null);

				TextView text_view = (TextView) convertView
						.findViewById(R.id.comment_tv);
				// LayoutParams layoutParams = convertView.getLayoutParams();
				// layoutParams.width = ((StatisticsActivity)
				// mAty).statistics_year.getWidth();
				// layoutParams.height = layoutParams.WRAP_CONTENT;
				// convertView.setLayoutParams(layoutParams);
				text_view.setText(GetYearPopWindow.this.listdata.get(position));
				return convertView;
			}

		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				if (((StatisticsActivityDetail) mAty).getType() == 1) {
					((StatisticsActivityDetail) mAty)
							.SetDataone(((StatisticsActivityDetail) mAty)
									.getResults().get(position));
				} else if (((StatisticsActivityDetail) mAty).getType() == 2) {
					
//					(StatisticsActivityDetail) mAty)
				}

				dismissPop();

			}
		});

		vParentRly.setOnClickListener(this);
		showPop();

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {

		case R.id.pop_parent_lin:// 点击黑色区域
			vInnerPop.dismiss();
			break;
		default:
			break;
		}
	}

	/**
	 * 弹出View
	 */
	public void showPop() {
		// vInnerPop.showAtLocation(vParentRly, Gravity.CENTER_HORIZONTAL, 0,
		// 0);

		lv.setTranslationX(((StatisticsActivityDetail) mAty).statistics_year
				.getX());
		vInnerPop.showAsDropDown(
				((StatisticsActivityDetail) mAty).statistics_year,
				(int) ((StatisticsActivityDetail) mAty).statistics_year.getX(),
				(int) ((StatisticsActivityDetail) mAty).statistics_year.getY());
	}

	/**
	 * 关闭pop
	 */
	public void dismissPop() {
		if (vInnerPop.isShowing()) {
			vInnerPop.dismiss();
		}
	}

	/**
	 * 是否当前为弹出显示状态
	 * 
	 * @return true 显示
	 */
	public boolean isShowing() {
		return vInnerPop.isShowing();
	}

}
