package com.mobo.zggkgisandroid.Info;

import com.mobo.zggkgisandroid.R;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 路况等级页面弹出框
 * 
 * @author WuXiaoHao
 *
 */
public class TrafficInfoPopupWindow {
	private PopupWindow popMenu;
	private PopupOnClickListener clickListener;
	private View tvMap;
	private View vAll, vYou, vLiang, vZhong, vCi, vCa, vQingKong;// 全部,优,良,中,次,差,清空

	public TrafficInfoPopupWindow(Activity activity, View tvMap) {
		this.tvMap = tvMap;
		View popView = LayoutInflater.from(activity).inflate(R.layout.traffic_info_popup_layout, null);
		popMenu = new PopupWindow(popView, tvMap.getWidth(), LayoutParams.WRAP_CONTENT);
		popMenu.setFocusable(true);
		popView.setFocusableInTouchMode(false);
		popMenu.setOutsideTouchable(true);
		popMenu.setBackgroundDrawable(new BitmapDrawable());
		popMenu.setTouchable(true);
		popView.setBackgroundColor(0xffffffff);
		vAll = popView.findViewById(R.id.tv_traffic_info_popup_all);
		vYou = popView.findViewById(R.id.tv_traffic_info_popup_you);
		vLiang = popView.findViewById(R.id.tv_traffic_info_popup_liang);
		vZhong = popView.findViewById(R.id.tv_traffic_info_popup_zhong);
		vCi = popView.findViewById(R.id.tv_traffic_info_popup_ci);
		vCa = popView.findViewById(R.id.tv_traffic_info_popup_ca);
		vQingKong = popView.findViewById(R.id.tv_traffic_info_popup_qingkong);

		vAll.setOnClickListener(new MyOnClickListener());
		vYou.setOnClickListener(new MyOnClickListener());
		vLiang.setOnClickListener(new MyOnClickListener());
		vZhong.setOnClickListener(new MyOnClickListener());
		vCi.setOnClickListener(new MyOnClickListener());
		vCa.setOnClickListener(new MyOnClickListener());
		vQingKong.setOnClickListener(new MyOnClickListener());
	}

	public void setClickListener(PopupOnClickListener clickListener) {
		this.clickListener = clickListener;
	}

	/**
	 * 监听
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			TextView textView = (TextView) view;
			if (clickListener != null)
				clickListener.OnClick(textView.getTag().toString());
			dismiss();
		}

	}

	public void show() {
		popMenu.showAsDropDown(tvMap, 0, 0);

	}

	public void dismiss() {
		if (popMenu.isShowing()) {
			popMenu.dismiss();
		}
	}

	public interface PopupOnClickListener {
		void OnClick(String tag);
	}
}
