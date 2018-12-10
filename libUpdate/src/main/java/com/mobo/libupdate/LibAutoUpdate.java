package com.mobo.libupdate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.mobo.libupdate.dialog.DefaultDialog;
import com.mobo.libupdate.dialog.DefaultDialog.OnLeftListener;
import com.mobo.libupdate.dialog.DefaultDialog.OnRightListener;
import com.mobo.libupdate.util.Util;

/**
 * 版本升级工具类
 * 
 * @author dxv
 * 
 */
public class LibAutoUpdate {

	// 当前版本号
	private int versionCode;
	// 调用升级的activity
	private Activity activity;
	// 当前版本名称
	private String versionName;
	private static final String TAG = "AutoUpdate";
	// 获得文件扩展名字符串
	private String fileEx = "";
	// 获得文件名字符串
	private String fileNa = "";
	// 应用的包名
	private String strPackageName;

	// 新版本下载地址
	private String downloadUrl;
	// 最新版本号
	private int newVersionCode;
	// 新版本描述
	private String newVersionDescribe;
	// 新版本名称
	private String newVersionName;
	// 安装包文件临时路径
	private String currentTempFilePath = "";

	private DefaultDialog progressDialog;// 进度条dialog

	// 是否可停止下载
	private boolean canStop = false;
	// 是否启动安装器
	private boolean isInstallStart = false;
	// 中止下载
	private boolean isStopDownload = false;
	private boolean isForce = false;

	private String dialogToastTxt = "下载失败";// 下载失败dialog提示信息
	private OnStartInstallListener installListener;// 开始安装监听

	/**
	 * 
	 * @param activity
	 *            调用此升级功能的activity
	 * @param downloadUrl
	 *            新版本apk下载路径
	 * @param newVersionCode
	 *            新版本版本号
	 * @param newVersionDescribe
	 *            新版本简介
	 * @param newVersionName
	 *            新版本的名称
	 * @param strPackageName
	 *            包名
	 * @param canStop
	 *            是否可以中止下载
	 */
	public LibAutoUpdate(Activity activity, String downloadUrl,
			int newVersionCode, String newVersionDescribe,
			String newVersionName, String strPackageName, boolean canStop) {
		super();
		this.activity = activity;
		this.downloadUrl = downloadUrl.trim();
		this.newVersionCode = newVersionCode;
		this.newVersionDescribe = newVersionDescribe;
		this.newVersionName = newVersionName;
		this.canStop = canStop;
		this.strPackageName = strPackageName;
		getCurrentVersion(strPackageName);

		if ("".equals(newVersionName) || null == newVersionName) {
			newVersionName = "最新版本:" + newVersionCode;
		} else {
			newVersionName = newVersionName + ":" + newVersionCode;
		}
	}

	/**
	 * 检查 更新
	 * 
	 * @param force
	 *            是否强制升级
	 */
	public void checkUp(String title, boolean force) {
		isForce = force;
		if ("".equals(downloadUrl) || downloadUrl == null) {

		} else {
			if (!Util.checkNetWork(activity)) {
				Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show();
			} else {
				if (newVersionCode > versionCode) {
					DefaultDialog dialog = new DefaultDialog(activity);
					dialog.setTitleValue(title);
					dialog.showTextDialog(newVersionDescribe);
					dialog.setCanceledOnTouchOutside(false);
					dialog.setRightButtonListener(new OnRightListener() {

						@Override
						public void onRightListener() {
							if (Util.isExistSD()) {
								new UpgradeDownloadTask().execute(downloadUrl);
							}
						}
					});
					if (force) {
						dialog.OnlyButton();
					} else {
						dialog.showLine();
					}
					dialog.show();
				}
			}
		}
	}

	/**
	 * 检查 更新以versionName判断
	 * 
	 * @param force
	 *            是否强制升级
	 */
	public void checkUpByVersionName(String title, final boolean force,
			OnStartInstallListener installListener) {
		isForce = force;
		this.installListener = installListener;
		if (!Util.checkNetWork(activity)) {
			Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show();
		} else {

			if (!newVersionName.equals(versionName)) {
				DefaultDialog dialog = new DefaultDialog(activity);
				dialog.setTitleValue(title);
				dialog.showTextDialog(newVersionDescribe);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(DialogInterface dialog, int keyCode,
							KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_BACK
								&& event.getRepeatCount() == 0) {
							return force;
						}
						return false;
					}
				});
				dialog.setRightButtonListener(new OnRightListener() {

					@Override
					public void onRightListener() {
						if (Util.isExistSD()) {
							new UpgradeDownloadTask().execute(downloadUrl);
						}
					}
				});
				if (force) {
					dialog.OnlyButton();
				} else {
					dialog.showLine();
				}
				dialog.show();
			}
		}
	}

	/**
	 * 获取当前的版本信息
	 * 
	 * @param strPackageName
	 *            应用的包名
	 */
	public void getCurrentVersion(String strPackageName) {
		try {
			PackageInfo info = activity.getPackageManager().getPackageInfo(
					strPackageName, 0);
			this.versionCode = info.versionCode;
			this.versionName = info.versionName;
			Log.d(TAG, versionCode + "versionCode");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查是否成功安装升级版本，成功则删除安装包 【需在activity的onResume()】
	 * 
	 * @return true installed and file delected false uninstall
	 */
	public boolean checkFinishInstallFileDel() {
		if (isInstallStart) {
			if (Util.checkInstalled(activity.getPackageManager(),
					strPackageName, newVersionCode) == Util.INSTALLED) {
				delFile();
				return true;
			}
		}
		return false;
	}

	/**
	 * 弹出 progressbar dialog
	 */
	public void showProgressDialog() {
		progressDialog = new DefaultDialog(activity);
		progressDialog.setTitleValue("正在下载安装包,请等待......");
		progressDialog.setCanceledOnTouchOutside(false);
		if (canStop) {
			progressDialog.setLeftBtnValue("取消下载");
			progressDialog.setLeftButtonLintener(new OnLeftListener() {

				@Override
				public void onLeftListener() {
					delFile();
					isStopDownload = true;
				}
			});
		} else {
			progressDialog.btnAlone();
		}
		if (isForce) {
			progressDialog.setCancelable(false);
			progressDialog.setRightButtonListener(null);
			progressDialog.setRightBtnValueCol(Color.parseColor("#747474"));
		} else {
			progressDialog.setRightButtonListener(new OnRightListener() {

				@Override
				public void onRightListener() {
				}
			});
		}
		progressDialog.setRightBtnValue("后台下载");
		progressDialog.show();
	}

	/**
	 * 删除临时路径里的安装包
	 */
	public void delFile() {
		Log.i(TAG, "The TempFile(" + currentTempFilePath + ") was deleted.");
		File myFile = new File(currentTempFilePath);
		if (myFile.exists()) {
			myFile.delete();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @author dingxuewei
	 * 
	 */
	private class UpgradeDownloadTask extends AsyncTask<String, String, File> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// 获得文件文件扩展名字符串
			fileEx = downloadUrl.substring(downloadUrl.lastIndexOf(".") + 1,
					downloadUrl.length()).toLowerCase();
			// 获得文件文件名字符串
			fileNa = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1,
					downloadUrl.lastIndexOf("."));
			showProgressDialog();
		}

		@Override
		protected void onPostExecute(File result) {
			if (result != null) {
				if (installListener != null) {
					installListener.onStartInstall(result);
				}
				Util.openFile(result, activity);
				progressDialog.dismiss();
				isInstallStart = true;
			} else {
				progressDialog.setTitleValue("提示：");
				progressDialog.changeTextDialog(dialogToastTxt);
				progressDialog.btnAlone();
				progressDialog.setRightBtnValue("关闭");
				delFile();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			if (values.length > 0) {
				progressDialog.setProgressBarValue(Integer.valueOf(values[0]));
				progressDialog.setProgressTvValue(values[1]);
				progressDialog.setFileSizeDownload(values[2]);
			}
		}

		@SuppressWarnings("resource")
		@Override
		protected File doInBackground(String... params) {
			String strPath = params[0];
			Log.i(TAG, "getDataSource()");
			File myTempFile = null;
			// 判断strPath是否为网络地址
			if (!URLUtil.isNetworkUrl(strPath)) {
				Log.i(TAG, "服务器地址错误！");
			} else {
				URL myURL;
				try {
					myURL = new URL(strPath);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					return null;
				}
				URLConnection conn;
				InputStream is = null;
				FileOutputStream fos = null;
				try {
					conn = myURL.openConnection();
					conn.connect();
					is = conn.getInputStream();
					if (is == null) {
						return null;
					}
					long fileLength = conn.getContentLength();
					if (fileLength < 1) {
						return null;
					}

					File sdFile = new File("sdcard");
					if (!sdFile.exists()) {
						// sd卡不存在
						return null;
					}
					myTempFile = new File("sdcard/" + fileNa + "." + fileEx);
					if (!myTempFile.getParentFile().exists()) {
						myTempFile.getParentFile().mkdirs();
						Log.d("test", myTempFile.getParentFile()
								.getAbsolutePath());
					}
					if (!myTempFile.exists()) {
						boolean isCreated = myTempFile.createNewFile();
						if (!isCreated) {
							dialogToastTxt = "请检查SD卡";
							return null;
						}
					}
					// 安装包文件临时路径
					currentTempFilePath = myTempFile.getAbsolutePath();
					fos = new FileOutputStream(myTempFile);
					byte buf[] = new byte[1024];
					int readLen = 0, loopCount = 0;
					long downloadSize = 0;
					while ((readLen = is.read(buf)) != -1) {
						if (isStopDownload) {
							if (fos != null) {
								fos.close();
							}
							if (is != null) {
								is.close();
							}
							return null;
						}
						loopCount++;
						downloadSize += readLen;
						fos.write(buf, 0, readLen);
						if (loopCount == 1 || loopCount % 100 == 0) {
							publishProgress(new String[] {
									(downloadSize * 100 / fileLength) + "",
									(downloadSize * 100 / fileLength) + "%",
									Util.formatFileSize(downloadSize) + "/"
											+ Util.formatFileSize(fileLength) });
							Log.i(TAG, "downloadSize" + downloadSize
									+ "|fileLength:" + fileLength);
						}
					}
					publishProgress("100", "100",
							Util.formatFileSize(fileLength));

					// myTempFile.renameTo(new File("sdcard/" + fileNa + "."
					// + fileEx));
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				} finally {
					try {
						if (fos != null) {
							fos.close();
						}
						if (is != null) {
							is.close();
						}
					} catch (Exception ex) {
						Log.e(TAG, "getDataSource() error: " + ex.getMessage(),
								ex);
					}
				}
			}
			return myTempFile;
		}

	}

	public interface OnStartInstallListener {
		/**
		 * 开始调用安装程序流程
		 */
		public void onStartInstall(File result);
	}

}
