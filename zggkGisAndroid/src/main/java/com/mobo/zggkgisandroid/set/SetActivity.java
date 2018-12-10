package com.mobo.zggkgisandroid.set;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.libupdate.LibAutoUpdate;
import com.mobo.libupdate.LibAutoUpdate.OnStartInstallListener;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebInterface.ConnectionParamsURL;
import com.mobo.zggkgisandroid.WebModel.UpdateResult;

/**
 * 设置页面
 * 
 * @author xushiwei
 */
public class SetActivity extends Activity
{
	/***************** 控件申明 **********************/

	private ImageButton vback_btn;// 返回按钮
	private LinearLayout vlayout_lly;// 更新布局

	private ThreadWithProgressDialog update;// 线程管理类
	private UpdateResult updateRestult;// 更新接口返回接送解析后的对象

	/***************** 变量申明 **********************/

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);

		setContentView(R.layout.set_layout);
		// 初始化控件
		init();
	}

	/**
	 * @author xushiwei 初始化操作控件
	 * 
	 */
	public void init()
	{

		// 返回按钮
		vback_btn = (ImageButton) findViewById(R.id.set_back_btn);
		vback_btn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				back();

			}
		});
		// 更新界面的linearlayou
		vlayout_lly = (LinearLayout) findViewById(R.id.set_update_system);
		vlayout_lly.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// 更新系统的方法
				update_system();

			}
		});

	}

	/**
	 * 
	 * @author xushiwei 返回操作
	 */
	public void back()
	{

		SetActivity.this.finish();

	}

	/**
	 * 
	 * 
	 * @author xushiwei 更新系统的方式
	 */
	public void update_system()
	{
		// 起线程
		update = new ThreadWithProgressDialog();
		update.Run(SetActivity.this, new MyTast(), R.string.com_loading);
	}

	/**
	 * 
	 * 线程管理类
	 */

	class MyTast implements ThreadWithProgressDialogTask
	{

		@Override
		public boolean TaskMain()
		{
			// 调用接口获取版本
			updateRestult = CustomApp.app.connInteface.Update();
			return true;
		}

		@Override
		public boolean OnTaskDismissed()
		{

			return false;
		}

		@Override
		public boolean OnTaskDone()
		{

			if (Utils.isCorrect(updateRestult, true) == 0) {
				if (updateRestult != null) {
					if (Float.parseFloat(Utils.getVersion(SetActivity.this)) < Float.parseFloat(updateRestult.getResults().get(0).getUp_version())) {

						if ("Y".equals(updateRestult.getResults().get(0).getUp_ismupdate())) {

						} else {

						}
						if (updateRestult.getResults().get(0).getUp_downurl().contains(".") && updateRestult.getResults().get(0).getUp_downurl().contains("/")) {
							updataApp();
						} else {

							CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "服务器地址错误");
						}

					} else {
						CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "版本是最新的");

					}

				} else {

					CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, "版本是最新的");
				}

			}

			return false;
		}
	}

	public void updataApp()
	{

		LibAutoUpdate autoUpdate = new LibAutoUpdate(this, ConnectionParamsURL.BASE_ACCESS_URL + updateRestult.getResults().get(0).getUp_downurl(), 0, updateRestult.getResults()
				.get(0).getUp_content(), updateRestult.getResults().get(0).getUp_version(), getPackageName(), false);
		autoUpdate.checkUpByVersionName(getResources().getString(R.string.setting_update_version_to) + updateRestult.getResults().get(0).getUp_version(), false,
				new OnStartInstallListener()
				{

					@Override
					public void onStartInstall(File result)
					{
					}

				});

	}
}
