package com.mobo.zggkgisandroid.Login;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.mobo.zggkgisandroid.R;

/**
 * 
 * 
 * @author xushiwei 此类为欢迎页 实现功能：软件一开启隔3秒跳到主页面
 * 
 */
public class WelcomeActivity extends Activity {
	private ImageView vWelcomeImg;// 欢迎图片
	private int[] imgs = { R.drawable.welcome_1, R.drawable.welcome_2,
			R.drawable.welcome_3, R.drawable.welcome_4, R.drawable.welcome_5,
			R.drawable.welcome_6, R.drawable.welcome_7 };
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_layout);
		initView();
		handler = new Handler();
		// handler.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// Intent intent = new Intent(WelcomeActivity.this,
		// LoginActivity.class);
		// startActivity(intent);
		// WelcomeActivity.this.finish();
		//
		// }
		// }, 3000);
	}

	/**
	 * 图片初始化
	 */
	private void initView() {
		vWelcomeImg = (ImageView) findViewById(R.id.welcome_img);
		Random random = new Random();
		int index = random.nextInt(imgs.length);
		vWelcomeImg.setBackgroundResource(imgs[index]);
		vWelcomeImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				WelcomeActivity.this.finish();

			}
		});
	}
}
