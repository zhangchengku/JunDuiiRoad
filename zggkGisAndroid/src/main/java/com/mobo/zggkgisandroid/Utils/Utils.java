package com.mobo.zggkgisandroid.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.WebInterface.ConnectionParamsURL;
import com.mobo.zggkgisandroid.WebModel.BaseResult;

/**
 * 工具类
 * 
 * 
 */
public class Utils {
	/**
	 * 判断字符串是否为null或为空
	 * 
	 * @param str
	 *            目标字符串
	 * @return boolean if true is null else other
	 */
	public static boolean isNull(String str) {
		if (str != null) {
			str = str.trim();
		}
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断网络是否可用
	 * 
	 * @param act
	 *            context对象
	 * @return boolean ture 可用 false 不可用
	 */
	public static boolean detect(Context act) {
		ConnectivityManager manager = (ConnectivityManager) act.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 获取版本号
	 * 
	 * @param context
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 *            上下文对象
	 * @param dpValue
	 *            dp值
	 * @return px值
	 */
	public static int dip2px(Context context, double dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 动态设置gridView的高度
	 * 
	 * @param gridView
	 *            布局
	 * @param column
	 *            列数
	 */
	public static void setGridViewHeightBasedOnChildren(GridView gridView, int column) {
		if (gridView == null)
			return;

		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0);
			if (i % column == 0) {
				totalHeight += listItem.getMeasuredHeight();
			}
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight + (listAdapter.getCount() / column + 1);
		// + (gridView.getVerticalSpacing() * (listAdapter.getCount()
		// / column + 1));
		gridView.setLayoutParams(params);
	}

	/**
	 * 动态设置LinearLayout的高度
	 * 
	 * @param layout
	 */
	public static void setLinearLayoutHeightBasedOnChildren(LinearLayout layout) {
		if (layout == null)
			return;

		int totalHeight = 0;

		for (int i = 0; i < layout.getChildCount(); i++) {
			View item = layout.getChildAt(i);

			totalHeight += item.getLayoutParams().height;
		}

		ViewGroup.LayoutParams params = layout.getLayoutParams();
		params.height = totalHeight;
		layout.setLayoutParams(params);
	}

	/**
	 * 将图片文件转换为String 数据
	 * 
	 * @param imgPath
	 *            图片地址
	 * @return 图片数据
	 * @throws IOException
	 */
	public static String bmpToString(String imgPath) throws IOException {

		// 用io流读取到要上传的图片，用Base64编码成字节流的字符串,得到uploadBuffer（要传到webservice接口上）
		FileInputStream fis = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String uploadBuffer = null;
		if (Utils.isNull(imgPath)) {
			return "";
		}
		fis = new FileInputStream(imgPath);
		byte[] buffer = new byte[1024];
		int count = -1;
		while ((count = fis.read(buffer)) >= 0) {
			baos.write(buffer, 0, count);
		}
		// 进行Base64编码得到字符串
		uploadBuffer = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
		return uploadBuffer;
	}

	/**
	 * 获取对象中属性的值
	 * 
	 * @param object
	 *            对象
	 * @param name
	 *            属性名
	 * @return 对象中属性的值
	 */
	public static Object getField(Object object, String name) {
		try {
			Field field = object.getClass().getDeclaredField(name);
			boolean accessFlag = field.isAccessible();
			field.setAccessible(true);
			Object o = field.get(object);
			field.setAccessible(accessFlag);
			return o;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 切换点击效果图片
	 * 
	 * @param context
	 * @param v
	 *            控件
	 * 
	 * @param drawableId
	 *            图片资源id
	 **/
	public static void changeDrawable(Context context, TextView v, int drawableId) {
		Drawable drawable;
		drawable = context.getResources().getDrawable(drawableId);
		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		v.setCompoundDrawables(null, drawable, null, null); // 设置左图标
	}

	/**
	 * 设置view的宽度
	 * 
	 * @param gView
	 *            网格 view
	 */
	public static void setGridViewWidth(GridView gView) {
		ArrayAdapter adapter = (ArrayAdapter) gView.getAdapter();
		int count = adapter.getCount();
		int totalWidth = 0;
		int height = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			View listItem = adapter.getView(i, null, gView);
			listItem.measure(0, 0);
			totalWidth += listItem.getMeasuredWidth();
			height = listItem.getMeasuredHeight();
		}
		LayoutParams params = (LayoutParams) gView.getLayoutParams();
		params.width = totalWidth;
		// params.height = height;
		gView.setLayoutParams(params);
	}

	/**
	 * 验证密码格式是否正确
	 * 
	 * @param password
	 * @return true 格式正确
	 */
	public static boolean verifyPassword(String password) {
		Pattern p = Pattern.compile(".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	/**
	 * 邮箱格式验证
	 * 
	 * @param email
	 * @return 邮箱格式正确true
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 判断是否是电话号码
	 * 
	 * @param mobiles
	 * @return true是电话号码
	 */
	public static boolean isMobile(String mobiles) {
		String telRegex = "[1]\\d{10}";// "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		Pattern p = Pattern.compile(telRegex);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断是否是电话号码
	 * 
	 * @param str
	 * @return true是
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	/**
	 * 提示文字设置
	 * 
	 * @param context
	 * @param hintId
	 * @param tv
	 */
	public static void setHint(Context context, int hintId, TextView tv) {
		// 新建一个可以添加属性的文本对象
		SpannableString ss = new SpannableString(context.getResources().getString(hintId));
		// 新建一个属性对象,设置文字的大小
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(11, true);
		// 附加属性到文本
		ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 设置hint
		tv.setHint(new SpannedString(ss));
	}

	/**
	 * 修改类的null变量
	 * 
	 * @param f
	 */
	public static void setAllComponentsName(Object f) {
		// 获取f对象对应类中的所有属性域
		Field[] fields = f.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			// 对于每个属性，获取属性名
			String varName = fields[i].getName();
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(f);
				if (o == null) {
					setFileValue(f, varName, "");
				}
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 修改成员变量的值
	 * 
	 * @param object
	 *            修改对象
	 * @param filedName
	 *            指定成员变量名
	 * @param filedValue
	 *            修改的值
	 */
	public static void setFileValue(Object object, String filedName, Object filedValue) {
		Class<?> classType = object.getClass();
		Field fild = null;
		try {
			fild = classType.getDeclaredField(filedName);
			fild.setAccessible(true);// 设置安全检查，访问私有成员变量必须
			fild.set(object, filedValue);
		} catch (NoSuchFieldException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
	 * 
	 * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
	 * 
	 * B.本地路径:url="file://mnt/sdcard/photo/image.png";
	 * 
	 * C.支持的图片格式 ,png, jpg,bmp,gif等等
	 * 
	 * @param url
	 * @return Bitmap
	 */
	public static Bitmap GetLocalOrNetBitmap(String url) {
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new URL(url).openStream(), 2 * 1024);
			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, 2 * 1024);
			copy(in, out);
			out.flush();
			byte[] data = dataStream.toByteArray();
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			data = null;
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * return a bitmap from service
	 * 
	 * @param url
	 * @return bitmap type
	 */
	public final static Bitmap returnBitMap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;

		try {
			myFileUrl = new URL(url);
			HttpURLConnection conn;

			conn = (HttpURLConnection) myFileUrl.openConnection();

			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 读取图片流
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	private static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] b = new byte[2 * 1024];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}

	/**
	 * 获取sdk版本
	 * 
	 * @return sdk版本
	 */
	public static int getAndroidSDKVersion() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
		}
		return version;
	}

	/**
	 * 获取选择相册的路径
	 * 
	 * @param context
	 * @param contentUri
	 * @return 路径
	 */
	@SuppressLint("NewApi")
	public static String getFilePath(Context context, Uri contentUri) {
		String filePath = "";
		if (getAndroidSDKVersion() >= 19 && DocumentsContract.isDocumentUri(context, contentUri)) {
			String wholeID = DocumentsContract.getDocumentId(contentUri);
			String id = wholeID.split(":")[1];
			String[] column = { MediaStore.Images.Media.DATA };
			String sel = MediaStore.Images.Media._ID + "=?";
			Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[] { id }, null);
			try {
				int columnIndex = cursor.getColumnIndex(column[0]);
				if (cursor.moveToFirst()) {
					filePath = cursor.getString(columnIndex);
				}
			} catch (NullPointerException e) {
				return "";
			}
			cursor.close();
		} else {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
			try {
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				filePath = cursor.getString(column_index);
			} catch (NullPointerException e) {
				return "";
			}

		}
		return filePath;
	}

	/**
	 * 按大小压缩图片 100kb以下
	 * 
	 * @param bmp
	 *            图片
	 * @param path
	 *            所存的路径
	 */
	public static Bitmap compressBmpToFile(Bitmap bmp, String path) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 80;
		int index = 0;
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 100 && index < 15) {
			baos.reset();
			bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
			index++;
		}
		return bmp;
		// try {
		// // 文件夹
		// File fileParent = new File(CustomApp.app.APP_FILE_SAVE_PATH + "/");
		// // 文件夹不存在则创建
		// if (!fileParent.exists())
		// fileParent.mkdirs();
		// // 创建图片文件
		// File file = new File(path);
		// FileOutputStream fos = new FileOutputStream(file);
		// fos.write(baos.toByteArray());
		// fos.flush();
		// fos.close();
		// return bmp;
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return null;
	}

	/**
	 * 获取图片缩略图
	 * 
	 * @param imagePath
	 *            图片路径
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return 图片缩略图
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		if (h < w) {
			int size = width;
			width = height;
			height = size;
		}
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		// bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
		// ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 将bitmap转化为byte
	 * 
	 * @param bm
	 *            bitmap
	 * @return byte[]
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return degree;
		}
		return degree;
	}

	/**
	 * 旋转图片，使图片保持正确的方向。
	 * 
	 * @param bitmap
	 *            原始图片
	 * @param degrees
	 *            原始图片的角度
	 * @return Bitmap 旋转后的图片
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
		if (degrees == 0 || null == bitmap) {
			return bitmap;
		}
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
		Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		if (null != bitmap) {
			bitmap.recycle();
		}
		return bmp;
	}

	/**
	 * 将bitmap对象转换为二进制数组
	 * 
	 * @param bitmap
	 * @return 二进制数组
	 */
	public byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * 判断 获取的图片是不是bitmap
	 * 
	 * @param str
	 * @return true 是
	 */
	public static boolean isBitmap(String str) {
		boolean isBitmap = false;
		if (str != null) {
			if (!"".equals(str)) {
				int index = str.lastIndexOf(".");
				if (index != -1) {
					String houzui = str.substring(index + 1, str.length());
					if (houzui.equals("PNG") || houzui.equals("png") || houzui.equals("jpg") || houzui.equals("JPG") || houzui.equals("JPEG")
							|| houzui.equals("jpeg")) {
						return true;
					}
				}
				return isBitmap;
			}
			return isBitmap;
		}
		return isBitmap;
	}

	/**
	 * 判断接口访问是否为正确
	 * 
	 * @param result
	 *            接口对象
	 * @param isToast
	 *            是否打印提示框
	 * @return 接口访问是否为正确 1不正确 2过期
	 */
	public static int isCorrect(BaseResult result, boolean isToast) {
		try {
			if (result == null) {
				if (isToast) {

					if (detect(CustomApp.app)) {

					} else {

						CustomApp.app.customToast(R.string.com_network_wrong);
					}
				}
				return 1;
			}
			Object object2 = getField(result, "status", true);
			if (object2 == null) {
				object2 = getField(result, "status", false);
				if (object2 == null) {
					return 1;
				}
			}
			if ("N".equals(result.getStatus())) {
				String errorCode = result.getError_code();
				if ("4001".equals(errorCode)) {
					result.setError_msg("登录超时,请重新登录");
					CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, result.getError_msg());
					return 2;
				}
				if (isToast && "4002".equals(errorCode)) {
					if (result.getError_msg() != null) {
						CustomApp.app.customToast(Gravity.CENTER, CustomApp.SHOW_TOAST_TIMES, result.getError_msg());
					}
				}
				return 1;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	/**
	 * 获取对象中属性的值
	 * 
	 * @param object
	 *            对象
	 * @param name
	 *            属性名
	 * @param issuper
	 *            是否是父类的名字
	 * @return 对象中属性的值
	 */
	public static Object getField(Object object, String name, boolean issuper) {
		try {
			Field field;
			if (issuper) {
				field = object.getClass().getSuperclass().getDeclaredField(name);
			} else {
				field = object.getClass().getDeclaredField(name);
			}

			boolean accessFlag = field.isAccessible();
			field.setAccessible(true);
			Object o = field.get(object);
			field.setAccessible(accessFlag);
			return o;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断程序是否启动
	 * 
	 * @param context
	 * @param packName
	 * @return true 启动
	 */
	public static boolean isOppenApp(Context context, String packName) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		for (RunningTaskInfo info : list) {

			if (info.topActivity.getPackageName().equals(packName) && info.baseActivity.getPackageName().equals(packName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenHeigh = dm.heightPixels;
		return screenHeigh;
	}

	/**
	 * 设置listview高度
	 * 
	 * @param listView
	 */

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	/**
	 * 登录页获取左上角显示的日期和端口
	 */
	public static String getVersionDate(String time) {
		if (!Utils.isNull(time)) {
			String port = ConnectionParamsURL.BASE_ACCESS_URL;
			String portType = port.substring(port.lastIndexOf(":") + 3, port.lastIndexOf(":") + 5);
			if ("12".equals(portType)) {
				portType = "测试版";
			} else {
				portType = "正式版";
			}
			return time + "-" + portType;
		}
		return "";
	}
}
