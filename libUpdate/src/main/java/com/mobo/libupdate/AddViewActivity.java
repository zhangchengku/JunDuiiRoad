package com.mobo.libupdate;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 测试类
 * 
 * @author dxv
 * 
 */
public class AddViewActivity extends Activity {

	private LinearLayout main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout.LayoutParams mainPm = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		main = new LinearLayout(this);
		setContentView(main, mainPm);
		initView();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		LinearLayout.LayoutParams titlePm = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		titlePm.gravity = Gravity.LEFT;
		titlePm.setMargins(toDip(10), toDip(20), toDip(10), toDip(15));
		TextView titleTv = new TextView(this);
		titleTv.setTextSize(20);
		titleTv.setId(1);
		main.addView(titleTv, titlePm);

		LinearLayout.LayoutParams titleLinePm = new LayoutParams(
				LayoutParams.WRAP_CONTENT, toDip(2));
		titleLinePm.setMargins((int) toDip(6), 0, (int) toDip(6), 0);
		ImageView titleLine = new ImageView(this);
		titleLine.setBackgroundColor(getResources().getColor(R.color.bule));
		main.addView(titleTv);
	}

	/**
	 * 值转换为dip
	 * 
	 * @param value
	 *            值
	 * @return 相对dip值
	 */
	public int toDip(int value) {
		int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, getResources().getDisplayMetrics());
		return dip;
	}
}
