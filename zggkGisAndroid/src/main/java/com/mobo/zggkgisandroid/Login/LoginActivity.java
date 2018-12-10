package com.mobo.zggkgisandroid.Login;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.MainActivity;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.DBEntity.DB_Value;
import com.mobo.zggkgisandroid.DBEntity.Province;
import com.mobo.zggkgisandroid.Utils.DesUtils;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.BaseResult;
import com.mobo.zggkgisandroid.WebModel.KeyValueResult;
import com.mobo.zggkgisandroid.WebModel.LoginResult;
import com.mobo.zggkgisandroid.WebModel.LoginResult.LoginMess;
import com.mobo.zggkgisandroid.WebModel.ProvincesResult;
import com.mobo.zggkgisandroid.WebModel.bindPhoneModel;
import com.mobo.zggkgisandroid.set.SetActivity;

/**
 * 此类为登陆类出现登陆界面
 * 
 * @author xushiwei
 * 
 */
public class LoginActivity extends Activity {
	/************************** 声明控件 **************************/
	private Button vlogin_btn;// 登陆按钮
	private ImageButton vset_btn;// 设置密码
	private EditText vuser_txtv;// 用户名
	private EditText vpassword_txtv;// 用户密码
	private CheckBox vremenber_password_ckb;// 记住密码
	private TextView vLoginVersion;// 左上角版本
	private final static String DES = "des";
	private String value_md5;// 本地md5值
	public static Activity instance;
	private String DEVICEID;
	private String state_code = "";
	private RelativeLayout vLoginXmLayout;// 登录布局
	private LinearLayout vLoginInputLayout;// 输入框布局

	/************************** 声明变量 **************************/

	private ThreadWithProgressDialog threadManage;// 线程管理者
	public bindPhoneModel bindPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(TELEPHONY_SERVICE);
		DEVICEID = tm.getDeviceId();
		init();
	}

	/* 初始化控件 */
	public void init() {

		instance = this;
		threadManage = new ThreadWithProgressDialog();

		new getResValueTask().execute();

		vLoginXmLayout = (RelativeLayout) findViewById(R.id.login_activity_layout);
		vLoginInputLayout = (LinearLayout) findViewById(R.id.login_input_layout);

		vlogin_btn = (Button) findViewById(R.id.login_login_btn); // 登陆按钮
		vset_btn = (ImageButton) findViewById(R.id.login_set_btn);// 设置按钮
		vuser_txtv = (EditText) findViewById(R.id.login_id_txtv);// 用户名编辑框
		vpassword_txtv = (EditText) findViewById(R.id.login_password_txtv);// 密码编辑框
		vremenber_password_ckb = (CheckBox) findViewById(R.id.login_remenber_radiobtn);// 记住密码复选框
		vLoginVersion = (TextView) findViewById(R.id.tv_version);
		if (!Utils.isNull(CustomApp.app.dateStr)) {
			vLoginVersion.setText("当前版本 ："
					+ Utils.getVersionDate(CustomApp.app.dateStr));
		}
		vLoginXmLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				vLoginInputLayout.setVisibility(View.VISIBLE);

			}
		});

		// 判断是不是第一次登陆
		if (CustomApp.app.pr.getString("IsFirst") == null) {
			CustomApp.app.pr.saveString("IsFirst", "yes");
		} else {
			// 获取用户名密码账号 如果为空记住密码复选框 勾选
			String name = CustomApp.app.pr.getString("user_name");
			String word = CustomApp.app.pr.getString("user_password");
			Boolean check;
			if (Utils.isNull(name) || Utils.isNull(word)) {
				check = true;
			} else {
				// 获取上次登录时有没有勾选复选框
				check = CustomApp.app.pr.getBoolean("ischeck");
			}
			// 填写密码账号 填写上次勾选复选框没有
			if (name != null && word != null) {
				vuser_txtv.setText(name);
				vpassword_txtv.setText(word);
				vremenber_password_ckb.setChecked(check);

			}

		}
		// 登陆按钮设置监听
		vlogin_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				login();
			}
		});
		// 设置俺就设置监听
		vset_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				set();

			}
		});

	}

	/**
	 * 
	 * @author xushiwei 设置页面跳转
	 */
	public void set() {
		Intent intent = new Intent(LoginActivity.this, SetActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

	}

	/**
	 * 页面登陆方法
	 */
	public void login() {
		// 判断为空不
		if (isnull()) {

			CustomApp.app.customToast(Gravity.CENTER,
					CustomApp.app.SHOW_TOAST_TIMES, R.string.isnull);

		} else {

			String string = CustomApp.app.pr.getString("bind");
			String string2 = CustomApp.app.pr.getString("bindaccount");
			if (string.equals("100")
					&& string2.equals(vuser_txtv.getText().toString())) {
				startTheard(1);
			} else {
				startTheard(1);
			}

		}

		// Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		// startActivity(intent);
		// LoginActivity.this.finish();

	}

	/**
	 * 
	 * @return true 为空 否则不为空
	 */
	public boolean isnull() {
		if (Utils.isNull(vuser_txtv.getText().toString())
				|| Utils.isNull(vpassword_txtv.getText().toString())) {
			return true;
		}
		return false;
	}

	// 起线程
	private void startTheard(int type) {

		switch (type) {
		case 0:
			if (Utils.detect(this)) {
				threadManage.Run(this, new LoginTask(), R.string.com_loading);

			} else {

				CustomApp.app.customToast(Gravity.CENTER,
						CustomApp.SHOW_TOAST_TIMES, R.string.com_network_wrong);
			}
			break;
		case 1:
			if (Utils.detect(this)) {
				threadManage.Run(this, new bindPhone(), R.string.com_loading);

			} else {

				CustomApp.app.customToast(Gravity.CENTER,
						CustomApp.SHOW_TOAST_TIMES, R.string.com_network_wrong);
			}
			break;

		default:
			break;
		}

	}

	/**
	 * 
	 * 此类为登陆验证 连接服务器的
	 * 
	 * @author xushiwei
	 * 
	 */
	public class LoginTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {

			try {
				CustomApp.app.loginresult = CustomApp.app.connInteface.Login(
						vuser_txtv.getText().toString(), DesUtils.decode(
								"roadmaint", vpassword_txtv.getText()
										.toString()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {

			if (CustomApp.app.loginresult != null) {
				if (CustomApp.app.loginresult.getStatus().equals("Y")) {
					if (vremenber_password_ckb.isChecked()) {

						savedata(CustomApp.app.loginresult);
					} else {
						CustomApp.app.pr.saveString("user_name", "");
						CustomApp.app.pr.saveString("user_password", "");
						CustomApp.app.pr.saveBoolean("ischeck", false);

					}
					CustomApp.app.clickLogin = true;
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					// Intent intent = new Intent(LoginActivity.this,
					// FortyFiveBridgeActivity.class);
					// CityList cityList = new CityList();
					// cityList.setProvince_code("123001");
					// cityList.setProvince_name("全国");
					// intent.putExtra("currentCity", cityList);
					startActivity(intent);
					// LoginActivity.this.finish();
				} else {
					CustomApp.app.customToast(Gravity.CENTER,
							CustomApp.SHOW_TOAST_TIMES,
							CustomApp.app.loginresult.getError_msg());
				}
			} else {
				CustomApp.app.customToast(Gravity.CENTER,
						CustomApp.SHOW_TOAST_TIMES, R.string.linkServerFail);
			}

			return false;
		}

	}

	public class bindPhone implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			bindPhone = CustomApp.app.connInteface.bindPhone(vuser_txtv
					.getText().toString(), DesUtils.decode("roadmaint",
					vpassword_txtv.getText().toString()), DEVICEID, state_code);
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			// TODO Auto-generated method stub

			int correct = Utils.isCorrect(bindPhone, false);
			if (bindPhone != null) {

				if (correct == 0) {

					if (bindPhone.getResults().equals("100")) {
						CustomApp.app.pr.saveString("bind", "100");
						CustomApp.app.pr.saveString("bindaccount", vuser_txtv
								.getText().toString());
						startTheard(0);

					} else if (bindPhone.getResults().equals("200")) {
						showdialog("");
					} else {
						CustomApp.app.customToast(Gravity.CENTER,
								CustomApp.SHOW_TOAST_TIMES,
								bindPhone.getError_msg());
					}

				} else {
					CustomApp.app.customToast(Gravity.CENTER,
							CustomApp.SHOW_TOAST_TIMES,
							bindPhone.getError_msg());
				}
			} else {
				CustomApp.app.customToast(R.string.com_network_wrong);
			}
			return false;
		}
	}

	/** 保存用户数据到本地 **/

	public void savedata(LoginResult loginResult) {

		LoginMess loginMess = loginResult.getResults().get(0);
		String user_id = loginMess.getUser_id();// 用户id
		String user_name = vuser_txtv.getText().toString();// 用户名称
		String token = loginMess.getToken();// 令牌
		String privilege = loginMess.getPrivilege();// 权限
		String belong_province_code = loginMess.getBelong_province_code();// 所属省份编码
		String belong_province_name = loginMess.getBelong_province_name();// 所属省份名称

		CustomApp.app.token = token;// 保存令牌到全局变量
		CustomApp.app.belong_province_code = belong_province_code;// 保存省份编码到全局变量
		CustomApp.app.privilege = privilege;// 保存权限到全局变量
		CustomApp.app.belong_province_name = belong_province_name;// 保存所属省份名称到全局变量

		CustomApp.app.pr.saveBoolean("ischeck", true);
		CustomApp.app.pr.saveString("user_id", user_id);
		CustomApp.app.pr.saveString("user_name", user_name);
		CustomApp.app.pr.saveString("user_password", vpassword_txtv.getText()
				.toString());
		CustomApp.app.pr.saveString("token", token);
		CustomApp.app.pr.saveString("privilege", privilege);

	}

	/**
	 * 获取MD5值的异步任务类
	 * 
	 */
	private class getResValueTask extends AsyncTask<Void, Void, String> {

		private KeyValueResult keyValueResult;
		private ProvincesResult provinces;

		@Override
		protected String doInBackground(Void... arg0) {

			value_md5 = CustomApp.app.pr.getString("value_md5");

			keyValueResult = CustomApp.app.connInteface.getKeyValue(value_md5);

			provinces = CustomApp.app.connInteface.getProvinces();

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			if (provinces != null && provinces.getStatus().equals("Y")) {
				List<Province> provinces_list = provinces.getResults();

				CustomApp.app.dbManager.updateProvinceSet(provinces_list);

				ArrayList<Province> provinces2ceshi = CustomApp.app.dbManager
						.queryValuesByType();

				if (provinces2ceshi != null && provinces2ceshi.size() != 0) {
					for (int i = 0; i < provinces2ceshi.size(); i++) {
						System.out.println("66666666666666666666"
								+ provinces2ceshi.get(i).getProvince_name());
					}
				}
			}

			// 存储键值对
			if (keyValueResult != null
					&& keyValueResult.getStatus().equals("Y")) {
				value_md5 = keyValueResult.getResults().get(0).getRes_md5();

				CustomApp.app.pr.saveString("value_md5", value_md5);// 保存最新的MD5值到本地

				System.out.println("vvvvvvvvvvvvvvvvvv" + value_md5);

				List<DB_Value> newValueSet = keyValueResult.getResults().get(0)
						.getRes_value().getRes_value();

				CustomApp.app.dbManager.updateValueSet(newValueSet);

				String dic_type = "桥梁类型";
				String dic_order = "0";

				ArrayList<DB_Value> ceshi = CustomApp.app.dbManager
						.queryValuesByType(dic_type);

				if (ceshi.size() != 0) {
					for (int i = 0; i < ceshi.size(); i++) {

						System.out.println("11111111111111111111"
								+ ceshi.get(i).getDic_desc());
					}
				}

			}

		}

	}

	public void showdialog(String text) {
		/** 监听对话框里面的button点击事件 */

		// 创建退出对话框
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
					state_code = "bind";
					dialog.dismiss();
					startTheard(1);
					break;
				case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
					break;
				default:
					break;
				}
			}
		};

		// 设置对话框标题
		dialog.setTitle("绑定提示");

		// 设置对话框消息
		dialog.setMessage("需绑定手机，您确定绑定吗");
		// 添加选择按钮并注册监听

		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "否", listener);
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "是", listener);

		// 显示对话框
		dialog.show();
		// dialog.setTitle("绑定提示");
		//
		// dialog.setMessage("是否登录？").setPositiveButton("确定", this)
		// .setNeutralButton("退出", this).create();

	}
}