package com.mobo.zggkgisandroid.Info;

import com.mobo.zggkgisandroid.R;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 路况等级页面弹出框
 * 
 * @author WuXiaoHao
 *
 */
public class TrafficIndexPopupWindow {
	private PopupWindow popMenu;
	private PopupOnClickListener clickListener;
	private View tvMap;
	private TextView vAll, vYou, vLiang, vZhong, vCi, vCa, vQingKong;// 全部,优,良,中,次,差,清空

	public TrafficIndexPopupWindow(Activity activity, View tvMap) {
		this.tvMap = tvMap;
		View popView = LayoutInflater.from(activity).inflate(R.layout.traffic_info_popup_layout, null);
		popMenu = new PopupWindow(popView, tvMap.getWidth(), LayoutParams.WRAP_CONTENT);
		popMenu.setFocusable(true);
		popView.setFocusableInTouchMode(false);
		popMenu.setOutsideTouchable(true);
		popMenu.setBackgroundDrawable(new BitmapDrawable());
		popMenu.setTouchable(true);
		popView.setBackgroundColor(0xffffffff);
		vAll = (TextView) popView.findViewById(R.id.tv_traffic_info_popup_all);
		vYou = (TextView) popView.findViewById(R.id.tv_traffic_info_popup_you);
		vLiang = (TextView) popView.findViewById(R.id.tv_traffic_info_popup_liang);
		vZhong = (TextView) popView.findViewById(R.id.tv_traffic_info_popup_zhong);
		vCi = (TextView) popView.findViewById(R.id.tv_traffic_info_popup_ci);
		vCa = (TextView) popView.findViewById(R.id.tv_traffic_info_popup_ca);
		vQingKong = (TextView) popView.findViewById(R.id.tv_traffic_info_popup_qingkong);

		vAll.setOnClickListener(new MyOnClickListener());
		popView.findViewById(R.id.rl_traffic_info_popup_you).setOnClickListener(new MyOnClickListener());
		popView.findViewById(R.id.rl_traffic_info_popup_liang).setOnClickListener(new MyOnClickListener());
		popView.findViewById(R.id.rl_traffic_info_popup_zhong).setOnClickListener(new MyOnClickListener());
		popView.findViewById(R.id.rl_traffic_info_popup_ci).setOnClickListener(new MyOnClickListener());
		popView.findViewById(R.id.rl_traffic_info_popup_ca).setOnClickListener(new MyOnClickListener());
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
			String t = view.getTag().toString();
			if (t.equals("0")) {
				if (vYou.getText().toString().indexOf("✔") == -1)
					vYou.setText(vYou.getText() + "✔");
				if (vLiang.getText().toString().indexOf("✔") == -1)
					vLiang.setText(vLiang.getText() + "✔");
				if (vZhong.getText().toString().indexOf("✔") == -1)
					vZhong.setText(vZhong.getText() + "✔");
				if (vCi.getText().toString().indexOf("✔") == -1)
					vCi.setText(vCi.getText() + "✔");
				if (vCa.getText().toString().indexOf("✔") == -1)
					vCa.setText(vCa.getText() + "✔");
				dismiss();
				if (clickListener != null)
					clickListener.OnClick(t, true);
			} else if (t.equals("6")) {

				if (vYou.getText().toString().indexOf("✔") != -1)
					vYou.setText(vYou.getText().toString().replace("✔", ""));
				if (vLiang.getText().toString().indexOf("✔") != -1)
					vLiang.setText(vLiang.getText().toString().replace("✔", ""));
				if (vZhong.getText().toString().indexOf("✔") != -1)
					vZhong.setText(vZhong.getText().toString().replace("✔", ""));
				if (vCi.getText().toString().indexOf("✔") != -1)
					vCi.setText(vCi.getText().toString().replace("✔", ""));
				if (vCa.getText().toString().indexOf("✔") != -1)
					vCa.setText(vCa.getText().toString().replace("✔", ""));
				dismiss();
				if (clickListener != null)
					clickListener.OnClick(t, true);
			} else {
				TextView textView = (TextView) ((RelativeLayout) view).getChildAt(1);
				if (textView.getText().toString().equals("✔")) {
					textView.setText("");
					if (clickListener != null)
						clickListener.OnClick(t, true);
				} else {
					textView.setText("✔");
					if (clickListener != null)
						clickListener.OnClick(t, false);
				}
			}

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
		void OnClick(String tag, boolean b);
	}
}
