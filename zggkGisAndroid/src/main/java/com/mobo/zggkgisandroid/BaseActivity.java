package com.mobo.zggkgisandroid;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

/**
 * base页面
 * 
 * @author WuXiaoHao
 *
 */
public class BaseActivity extends FragmentActivity {
	public TextView tvLeft, tvTitle, tvRight;
	public ImageView imgLeft, imgRight;
	private ViewGroup group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_layout);
		tvTitle = (TextView) findViewById(R.id.base_title_tv);
		tvLeft = (TextView) findViewById(R.id.base_top_left_tv);
		imgLeft = (ImageView) findViewById(R.id.base_top_left_img);
		imgRight = (ImageView) findViewById(R.id.base_top_right_img);
		tvRight = (TextView) findViewById(R.id.base_top_right_tv);
		group = (ViewGroup) findViewById(R.id.base_content_rl);
		imgLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onClickLeft(arg0);
			}
		});
		imgRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onClickRight(arg0);
			}
		});
		tvRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onClickRight(arg0);
			}
		});
	}

	protected void onClickLeft(View view) {
	}

	protected void onClickRight(View view) {
	}

	/**
	 * 设置标题
	 * 
	 * @param charSequence
	 */
	public void setTitleText(CharSequence charSequence) {
		tvTitle.setText(charSequence);
	}

	/**
	 * 设置标题
	 * 
	 * @param id
	 *            标题id
	 */
	public void setTitleText(int id) {
		tvTitle.setText(id);
	}

	/**
	 * 加载布局
	 * 
	 * @param layoutResID
	 */
	public void setContentXml(int layoutResID) {
		View view = View.inflate(this, layoutResID, null);
		group.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

	// @Override
	// public void onPositiveButtonClicked(int requestCode) {// 积极
	//
	// }
	//
	// @Override
	// public void onNegativeButtonClicked(int requestCode) {// 消极
	//
	// }
}
