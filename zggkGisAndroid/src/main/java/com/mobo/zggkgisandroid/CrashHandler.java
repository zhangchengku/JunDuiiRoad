package com.mobo.zggkgisandroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.tooklkit.Tooklkit;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * 
 * @author user
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";
	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();
	// 程序的Context对象
	private Context mContext;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	// 用于格式化日期,作为日志文件名的一部分
	private String sdCard;

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	// ConnectionInterface cInterface;

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context, String errorPath) {
		// cInterface = new ConnectionInterface(context);
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
		sdCard = Environment.getExternalStorageDirectory().getPath() + errorPath;
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
				e.printStackTrace();
			}
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(startMain);
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信息
		// new Thread() {
		// @Override
		// public void run() {
		// Looper.prepare();
		// Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG)
		// .show();
		// Looper.loop();
		// }
		// }.start();
		// 收集设备参数信息
		collectDeviceInfo(mContext);
		// 保存日志文件
		new Thread(new Runnable() {

			@Override
			public void run() {
				saveCrashInfo2File(ex);
				ex.printStackTrace();
			}
		}).start();

		return true;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex) {

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			String time = Tooklkit.getTimestamp("yyyy-MM-dd-HH-mm-ss", Tooklkit.getDate("yyyy-MM-dd-HH-mm-ss")) + "";
			String appName = mContext.getResources().getString(R.string.app_name);
			String fileName = time + "_" + appName;
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File dir = new File(sdCard);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(sdCard + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
				posetLog(sb.toString(), time, sdCard + fileName);
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
	}

	/**
	 * 上传
	 * 
	 * @param log
	 * @param time
	 * @param filePath
	 */
	private void posetLog(String log, String time, String filePath) {
//		BaseResult result = cInterface.errorLog(CustomApp.app.login != null ? CustomApp.app.login.getUser_id() : "",
//				time, "机器类型:" + android.os.Build.MODEL.replace(" ", "") + "系统版本：" + android.os.Build.VERSION.RELEASE,
//				log);
//		if (Util.isCorrect(result, false) == 0) {
//			deleteFile(new File(filePath));
//		}
	}

	public void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
		}
	}

	/**
	 * 上传错误信息
	 * 
	 * @param fileName
	 *            文件名
	 */
	public void postError(final String fileName) {
		try {
			final String time = fileName.split("_")[0];
			new Thread(new Runnable() {
				@Override
				public void run() {
					String res = "";
					try {
						FileInputStream fin = new FileInputStream(sdCard + fileName);
						int length = fin.available();
						byte[] buffer = new byte[length];
						fin.read(buffer);
						res = EncodingUtils.getString(buffer, "UTF-8");
						fin.close();
						posetLog(res, time, sdCard + fileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		} catch (Exception e) {
			return;
		}
	}

	public Vector<String> GetVideoFileName() {
		Vector<String> vecFile = new Vector<String>();
		File file = new File(sdCard);
		if (!file.exists()) {
			return vecFile;
		}
		File[] subFile = file.listFiles();
		for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
			// 判断是否为文件夹
			if (!subFile[iFileLength].isDirectory()) {
				String filename = subFile[iFileLength].getName();
				// // 判断是否为log结尾
				// if (filename.trim().toLowerCase().endsWith(".log")) {
				vecFile.add(filename);
				// }
			}
		}
		return vecFile;
	}
}
