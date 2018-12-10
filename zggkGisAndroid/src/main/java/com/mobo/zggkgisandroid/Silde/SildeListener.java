package com.mobo.zggkgisandroid.Silde;

import android.view.View;
import android.widget.AdapterView;

/**
 * 滑动弹出框的各种监听
 * 
 * @author WuXiaoHao
 *
 */
public interface SildeListener {
	/**
	 * 位移动画结束
	 * 
	 * @param type
	 *            0位移到最底部，1中间，2顶部
	 */
	void animatorEnd(int type);

	/**
	 * 底部左侧按钮点击监听
	 * 
	 * @param view
	 */
	void onButtomLeftClick(View view);

	/**
	 * 底部右侧按钮点击监听
	 * 
	 * @param view
	 */
	void onButtomRightClick(View view);

	/**
	 * listview监听
	 * 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	void onItemClick(AdapterView<?> parent, View view, int position, long id);
}
