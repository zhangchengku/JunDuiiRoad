package com.LibLoading;

import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * 测试类
 * 
 * @author qym
 * 
 */
public class LibLoadingActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// test
		LoadProgressDialog myDialog = new LoadProgressDialog(this,
				R.style.CustomProgressDialog, R.layout.progress_dialog,
				R.anim.rotate_refresh, 0, R.drawable.loading_refresh,
				R.drawable.loading_dismiss, "正在加载...");
		myDialog.show();
	}
}