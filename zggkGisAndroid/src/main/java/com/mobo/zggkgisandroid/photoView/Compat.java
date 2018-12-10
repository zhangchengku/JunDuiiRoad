package com.mobo.zggkgisandroid.photoView;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
/**
 * 设置线程执行时间间隔
 * @author dongxiaoqing
 *
 */
public class Compat {
	
	private static final int SIXTY_FPS_INTERVAL = 1000 / 60;
	/**
	 * 设置线程执行时间间隔
	 * @param view 控件
	 * @param runnable  子线程
	 */
	public static void postOnAnimation(View view, Runnable runnable) {
		if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
			SDK16.postOnAnimation(view, runnable);
		} else {
			view.postDelayed(runnable, SIXTY_FPS_INTERVAL);
		}
	}

}
