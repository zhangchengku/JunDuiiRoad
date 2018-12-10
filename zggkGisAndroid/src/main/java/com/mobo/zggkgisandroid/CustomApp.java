package com.mobo.zggkgisandroid;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.RemoveTypeLastUsedTimeFirst;
import cn.trinea.android.common.service.impl.ImageCache.CompressListener;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;
import cn.trinea.android.common.util.FileUtils;
import cn.trinea.android.common.util.ObjectUtils;

import com.LibUtil.LibMoboDebug.MoboDebug;
import com.LibUtil.LibSharedPreferences.Preferences;
import com.mobo.zggkgisandroid.DBHelper.DBManager;
import com.mobo.zggkgisandroid.Login.LoginActivity;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebInterface.ConnectionInteface;
import com.mobo.zggkgisandroid.WebModel.LoginResult;
import com.mobo.zggkgisandroid.WebModel.MilListResult;
import com.mobo.zggkgisandroid.WebModel.SearchResult;
import com.squareup.okhttp.OkHttpClient;

import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

/**
 * 应用定制类
 * 
 * @author Qym
 * 
 */
public class CustomApp extends Application {

	public static CustomApp app;// app 实体
	public final String LOG = "Reading_androidApp";// 日志TAG
	public final boolean isTest = false;// 是否测试项目(发布时改false)
	public Preferences pr;// 本地数据
	public static final int SHOW_TOAST_TIMES = 500;// 土司通知显示时长
	public final String DATABASE_NAME = "zggkGisDB.db";// 数据库文件的名称
	public final String APP_FILE_SAVE_PATH = "/mnt/sdcard/mobozggkgis"; // 应用文件储存路径
	public boolean isLogin = false;// false未登录 true已登录
	public boolean isFirst = false;//
	public MoboDebug debug;// 打印log及toast内容d
	public String currentVersion;// 当前版本号
	public ConnectionInteface connInteface;//
	public LoginResult loginresult;
	public String dateStr = "20161108";
	public String token;// 登陆令牌
	public String belong_province_code;// 省份编码
	public String privilege;// 用户查询权限
	public String belong_province_name;// 省份名称
	public String value_md5;// 本地md5值
	public DBManager dbManager;// 数据库管理工具类
	public List<Activity> mActiveLists = new LinkedList<Activity>();// 所有activity集合
	public MilListResult millList;
	public Bitmap bitmap;
	public OkHttpClient client;
	public boolean firstzoom = true;// 桥梁详情是否缩放
	public boolean clickLogin = false;// 点击登录进入主页面

	@Override
	public void onCreate() {
		super.onCreate();
		isFirst = true;
		client = new OkHttpClient();
		client.setConnectTimeout(60, TimeUnit.SECONDS);
		app = this;
		// 图片缓存
		// IMAGE_CACHE.initData(app, TAG_CACHE);
		// IMAGE_CACHE.setContext(app);
		// IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);
		/** 1.日志打印 **/
		debug = new MoboDebug(this, true, false);
		// 设置日志打印名称tag
		debug.setLogTag(LOG);
		// 设置日志打印类型 包括(d,e,i,v,w) - 默认d
		debug.setLogType('d');
		/** 2.简易本地数据保存 **/
		pr = new Preferences(this);
		connInteface = new ConnectionInteface(this);
		currentVersion = Utils.getVersion(this);
		dbManager = new DBManager(this);

		IMAGE_CACHE.initData(app, TAG_CACHE);
		IMAGE_CACHE.setContext(app);
		IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);
		// CrashHandler crashHandler = CrashHandler.getInstance();
		// crashHandler.init(getApplicationContext(), "/mobo_zggk/");
		// Vector<String> vector = crashHandler.GetVideoFileName();
		// for (int i = 0; i < vector.size(); i++) {
		// crashHandler.postError(vector.get(i));
		// }
	}

	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			return sdDir.toString();
		}
		return "/mnt/sdcard";
	}

	/************************************** 公用方法 **************************************/
	/**
	 * 检查登录超时设置重新登录
	 * 
	 * @param activity
	 *            跳转至登录的activity
	 * @param className
	 *            如需登录后通过intent跳回之前的界面，此处传完整包名，否则传“”
	 * 
	 */
	public void reLogin(Activity activity, String className) {
		customToast(R.string.login_out);
		exitLogin();// 退出登陆
		Intent intent = new Intent(activity, LoginActivity.class);// 跳转至登录
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("className", className);
		activity.startActivity(intent);
	}

	/**
	 * 检查登录超时设置重新登录
	 * 
	 * @param activity
	 *            跳转至登录的activity
	 * @param className
	 *            如需登录后通过intent跳回之前的界面，此处传完整包名，否则传“”
	 * @param type
	 *            类型
	 */
	public void reLogin(Activity activity, String className, int[] type) {
		customToast(R.string.login_out);
		exitLogin();
		Intent intent = new Intent(activity, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("type", type);
		intent.putExtra("className", className);
		activity.startActivity(intent);
	}

	/**
	 * 退出登陆
	 */
	public void exitLogin() {
		this.isLogin = false;
		pr.saveBoolean("isLogin", false);
		pr.saveString("access_token", "");
	}

	/**************************************
	 * toast 自定义
	 **************************************/
	/**
	 * 自定义显示位置的Toast
	 * 
	 * @param location
	 *            显示位置
	 * @param time
	 *            显示时间
	 */
	public void customToast(int location, int time, int textId) {
		customToast(location, time, getResources().getString(textId));
	}

	/**
	 * 自定义显示位置的Toast
	 * 
	 * @param textId
	 *            显示文本
	 */
	public void customToast(int textId) {
		customToast(Gravity.CENTER, SHOW_TOAST_TIMES, getResources().getString(textId));
	}

	/**
	 * 自定义显示位置的Toast
	 * 
	 * @param location
	 *            显示位置 Gravity.
	 * @param time
	 *            显示时间
	 */
	public void customToast(int location, int time, String showTxt) {
		Toast toast = new Toast(this);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(location, 0, 0);
		View toastView = View.inflate(app, R.layout.custom_toast_layout, null);
		TextView toastTv = (TextView) toastView.findViewById(R.id.toast_tv);
		toastTv.setText(showTxt);
		toast.setView(toastView);
		toast.show();
	}

	/**
	 * 自定义显示位置的Toast
	 * 
	 * @param textId
	 *            显示文本
	 */
	public void customToast(String textId) {
		customToast(Gravity.CENTER, SHOW_TOAST_TIMES, textId);
	}

	/**************************************
	 * toast 自定义
	 **************************************/

	/**
	 * 显示弹出选择框dialog
	 * 
	 * @param context
	 *            context对象
	 * @param fragmentManager
	 *            fragment管理器
	 * @param showContent
	 *            提示内容
	 * @param positiveTxt
	 *            积极的显示文本
	 * @param negativeTxt
	 *            消极的
	 */
	public void showDialog(Context context, FragmentManager fragmentManager, String showContent, String positiveTxt, String negativeTxt) {
		context.setTheme(R.style.CustomLightTheme);
		SimpleDialogFragment.createBuilder(context, fragmentManager).setTitle(R.string.prompt).setMessage(showContent)
				.setPositiveButtonText(positiveTxt).setNegativeButtonText(negativeTxt).setRequestCode(42).show();
	}

	/**************************************
	 * toast 自定义
	 **************************************/

	/**************************************
	 * icon cache 缓存图片
	 **************************************/
	public static final String TAG_CACHE = "image_cache";
	/** cache folder path which be used when saving images **/
	public static final String DEFAULT_CACHE_FOLDER = new StringBuilder().append(Environment.getExternalStorageDirectory().getAbsolutePath())
			.append(File.separator).append("mobo").append(File.separator).append("CHM").append(File.separator).append("ImageCache").toString();// cache
																																				// folder
																																				// path
																																				// which
																																				// be
																																				// used
																																				// when
																																				// saving
																																				// images

	public static final ImageCache IMAGE_CACHE = new ImageCache();

	static {
		/** init icon cache **/
		OnImageCallbackListener imageCallBack = new OnImageCallbackListener() {

			/**
			 * callback function after get image successfully, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param loadedImage
			 *            bitmap
			 * @param view
			 *            view need the image
			 * @param isInCache
			 *            whether already in cache or got realtime
			 */
			@Override
			public void onGetSuccess(String imageUrl, Bitmap loadedImage, View view, boolean isInCache) {
				if (view != null && loadedImage != null) {
					ImageView imageView = (ImageView) view;
					// imageView.setImageBitmap(loadedImage);
					// first time show with animation

					String imageUrlTag = (String) imageView.getTag();
					//
					if (imageUrlTag == null || ObjectUtils.isEquals(imageUrlTag, imageUrl)) {
						imageView.setImageBitmap(loadedImage);
					}
					if (!isInCache) {
						imageView.startAnimation(getInAlphaAnimation(1000));
					}

					// auto set height accroding to rate between height and
					// weight
					// LayoutParams imageParams = (LayoutParams) imageView
					// .getLayoutParams();
					// imageParams.height = imageParams.width
					// * loadedImage.getHeight() / loadedImage.getWidth();
					// imageView.setScaleType(ScaleType.FIT_XY);
				}
			}

			/**
			 * callback function before get image, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param view
			 *            view need the image
			 */
			@Override
			public void onPreGet(String imageUrl, View view) {
				// Log.e(TAG_CACHE, "pre get image");
			}

			/**
			 * callback function after get image failed, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param loadedImage
			 *            bitmap
			 * @param view
			 *            view need the image
			 * @param failedReason
			 *            failed reason for get image
			 */
			@Override
			public void onGetFailed(String imageUrl, Bitmap loadedImage, View view, FailedReason failedReason) {
				CustomApp.app.debug.printLogAndToast(new StringBuilder(128).append("get image ").append(imageUrl).append(" error, failed type is: ")
						.append(failedReason.getFailedType()).append(", failed reason is: ").append(failedReason.getCause().getMessage()).toString());
			}

			@Override
			public void onGetNotInCache(String imageUrl, View view) {
				if (view != null) {
					if (view.getTag(R.id.default_appoint_image) != null) {
					}
				}
			}

		};
		IMAGE_CACHE.setOnImageCallbackListener(imageCallBack);
		IMAGE_CACHE.setCacheFullRemoveType(new RemoveTypeLastUsedTimeFirst<Bitmap>());

		// 设置图片读取http超时
		IMAGE_CACHE.setHttpReadTimeOut(10000);
		// 是否启用队列,不能设置false，导致第一张图失败
		IMAGE_CACHE.setOpenWaitingQueue(true);
		// 缓存元素有效时间 小于0表示不会失效
		IMAGE_CACHE.setValidTime(-1);
		// 设置context，网络连接失败不会新建线程请求图片。
		IMAGE_CACHE.setContext(app);
		// 设置图片压缩比例，防止内存溢出
		IMAGE_CACHE.setCompressListener(new CompressListener() {
			@Override
			public int getCompressSize(String imagePath) {
				if (FileUtils.isFileExist(imagePath)) {
					long fileSize = FileUtils.getFileSize(imagePath) / 1024;
					/**
					 * if image bigger than 100k, compress to 1/(n + 1) width
					 * and 1/(n + 1) height, n is fileSize / 100k
					 **/
					if (fileSize > 400) {
						return (int) (fileSize / 350) + 1;
					}
				}
				return 1;
			}

		});
		/**
		 * close connection, default is connect keep-alive to reuse connection.
		 * if image is from different server, you can set this
		 */
		// IMAGE_CACHE.setRequestProperty("Connection", "false");

	}

	/**
	 * 获取图片的旋转角度
	 * 
	 * @param durationMillis
	 *            传入毫秒
	 * @return 角度对象
	 */
	public static AlphaAnimation getInAlphaAnimation(long durationMillis) {
		AlphaAnimation inAlphaAnimation = new AlphaAnimation(0, 1);
		inAlphaAnimation.setDuration(durationMillis);
		return inAlphaAnimation;
	}

	/**************************************
	 * icon cache 缓存图片
	 **************************************/
	/**
	 * 登录超时退出登录
	 * 
	 * @param context
	 */
	public void exit(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	/**
	 * 退出所有activity
	 */
	public void exit() {
		if (CustomApp.app.mActiveLists != null && CustomApp.app.mActiveLists.size() != 0) {
			for (int i = CustomApp.app.mActiveLists.size() - 1; i >= 0; i--) {
				CustomApp.app.mActiveLists.get(i).finish();
			}
		}
	}
}
