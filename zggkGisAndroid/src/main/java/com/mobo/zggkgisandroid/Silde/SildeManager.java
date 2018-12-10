package com.mobo.zggkgisandroid.Silde;

import java.lang.reflect.Field;

import com.mobo.libupdate.util.Util;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.Text;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 可滑动的dialog
 * 
 * @author WuXiaoHao
 * 
 */
public class SildeManager {
	private Activity activity;
	private View sildeView;// 滑动控件的布局s
	private View mainView;// 主页面布局
	private TextView vTop;// 顶部控件
	public ListView listView;// 列表
	public View vButtom;// 底部布局
	private LinearLayout llLeft, llRight;// 底部左侧按钮.底部右侧按钮
	private ImageView imgLeft, imgRight;// 底部左侧图片，底部右侧图片
	private TextView tvLeft, tvRight;// 底部左侧文字，底部右侧文字
	private boolean isOpenAnimator = true;// 是否开滑动功能

	private SildeListener sildeListener;// 各种监听

	private int type;// 0最底部，1中间，2全屏
	private int marginTop;// 控件marginTop

	private boolean move = false;// 判断动画是否正在执行

	private int topHeight;// 顶部标题的高度

	/**
	 * 是否开启滑动功能
	 * 
	 * @param isOpenAnimator
	 */
	public void setOpenAnimator(boolean isOpenAnimator) {
		this.isOpenAnimator = isOpenAnimator;
		if (!isOpenAnimator) {
			int h = topHeight + Utils.dip2px(activity, 40.75);
//			int h = topHeight;
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, h);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,
					RelativeLayout.TRUE);
			layoutParams.setMargins(0, Utils.getHeight(activity)
					- getStatusBarHeight(activity) - h, 0, 0);
			sildeView.setLayoutParams(layoutParams);
			listView.setVisibility(View.GONE);
		} else {
//			listView.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1,
//					new String[] { "aa", "bb", "cc", "dd", "ee", "ff", "aa", "bb", "cc", "dd", "ee", "ff" }));
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					Utils.getHeight(activity) - getStatusBarHeight(activity));
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			marginTop = Utils.getHeight(activity) - getStatusBarHeight(activity) - topHeight;

			layoutParams.setMargins(0, marginTop, 0, 0);
			sildeView.setLayoutParams(layoutParams);
			listView.setVisibility(View.VISIBLE);
		}
	}

	public void setSildeListener(SildeListener sildeListener) {
		this.sildeListener = sildeListener;
	}

	public SildeManager(Activity activity, View mainView) {
		this.activity = activity;
		this.mainView = mainView;
		topHeight = Utils.dip2px(activity, 50);
	}

	public void init() {
		sildeView = activity.findViewById(R.id.silde_view);
		vTop = (TextView) activity.findViewById(R.id.v_silde_top);
		listView = (ListView) activity.findViewById(R.id.list_silde);
		llLeft = (LinearLayout) activity.findViewById(R.id.v_silde_buttom_left);
		llRight = (LinearLayout) activity
				.findViewById(R.id.v_silde_buttom_right);
		vButtom = activity.findViewById(R.id.v_silde_buttom);
		vButtom.setVisibility(View.GONE);
		imgLeft = (ImageView) llLeft.getChildAt(0);
		imgRight = (ImageView) llRight.getChildAt(0);
		tvLeft = (TextView) llLeft.getChildAt(1);
		tvRight = (TextView) llRight.getChildAt(1);

		llLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sildeListener != null)
					sildeListener.onButtomLeftClick(arg0);
			}
		});
		llRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sildeListener != null)
					sildeListener.onButtomRightClick(arg0);
			}
		});
		setOpenAnimator(isOpenAnimator);

		vTop.setOnTouchListener(new OnTouchListener() {
			private boolean isMove = false;// 是否移动
			private boolean isDown;// 判断是否按下
			private float moveY;// 移动时的Y坐标
			private float downY;// 按下时的Y坐标

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (!isOpenAnimator)
					return false;
				if (move)
					return true;
				switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE:
					if (isDown && v.getTag().equals("1")) {
						isMove = true;
						moveY = event.getY();

						float difference = downY - moveY;
						float moveMarginTop = marginTop - difference;
						if (moveMarginTop > mainView.getHeight() - topHeight) {
							marginTop = mainView.getHeight() - topHeight;
						} else if (moveMarginTop < 0) {
							marginTop = 0;
						} else {
							marginTop = (int) moveMarginTop;
						}
						setSildeViewLayoutParams(marginTop,
								mainView.getHeight() - marginTop);
					}
					break;
				case MotionEvent.ACTION_DOWN:
					if (!isDown && v.getTag().equals("0")) {
						v.setTag("1");
						isDown = true;
						downY = event.getY();
					}
					break;
				case MotionEvent.ACTION_UP:
					if (isDown && v.getTag().equals("1")) {
						if (!isMove) {
							onClickTop();// 顶部textview点击事件
						} else {
							actionUp();
						}
						v.setTag("0");
						isMove = false;
						isDown = false;
					}
					break;
				}
				return true;
			}
		});
		sildeView.setOnTouchListener(new OnTouchListener() {
			boolean isMove = false;// 是否移动
			private boolean isDown;// 判断是否按下
			private float moveY;// 移动时的Y坐标
			private float downY;// 按下时的Y坐标

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (!isOpenAnimator)
					return false;
				if (move)
					return true;
				switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE:
					if (isDown && v.getTag().equals("1")) {// 判断按下过并且点击次数不为1
						isMove = true;// 移动状态变为true
						moveY = event.getY();// 记录移动时的坐标
						// 计算实际移动到的距离
						float difference = downY - moveY;// 需要移动的距离
						float moveMarginTop = marginTop - difference;
						if (moveMarginTop > mainView.getHeight() - topHeight) {
							marginTop = mainView.getHeight() - topHeight;
						} else if (moveMarginTop < 0) {
							marginTop = 0;
						} else {
							marginTop = (int) moveMarginTop;
						}
						setSildeViewLayoutParams(marginTop,
								mainView.getHeight() - marginTop);
					}
					break;
				case MotionEvent.ACTION_DOWN:
					if (!isDown && v.getTag().equals("0")) {
						v.setTag("1");
						isDown = true;
						downY = event.getY();
					}
					break;
				case MotionEvent.ACTION_UP:
					if (isDown && v.getTag().equals("1")) {
						if (isMove) {
							actionUp();
						}
						v.setTag("0");
						isMove = false;
						isDown = false;
					}
					break;
				}
				return true;
			}
		});
		listView.setOnTouchListener(new OnTouchListener() {// 监听listview滑动
			boolean isMove = false;// 是否移动
			private boolean isDown;// 判断是否按下
			private boolean isScrollList = false;;// 是否滚动了listview
			private float moveY;// 移动时的Y坐标
			private float downY;// 按下时的Y坐标
			private boolean isFirstScrollList = false;// 判断上一次移动是否执行了listview滚动

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (!isOpenAnimator)
					return false;
				if (move)
					return true;
				switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE:
					if (isDown && v.getTag().equals("1")) {// 判断按下过并且点击次数不为1
						moveY = event.getY();// 记录移动时的Y坐标
						float difference = downY - moveY;// 需要移动的距离
						if (difference == 0) {
							break;
						}
						if (type == 2 && (difference > 0 || getScrollY() != 0)
								&& !isMove) {// 判断当前是全屏状态且手指是向上滑动或者listview没有滑动到顶部
							isScrollList = true;// listview滚动状态变为true
							isFirstScrollList = true;
							// 把焦点抛给listView的其他监听
							return false;
						}
						if (isFirstScrollList) {// 上一次移动执行了listview 滚动
							isFirstScrollList = false;
							// 刷新按下时的坐标点
							downY = moveY;
							return true;
						}
						isMove = true;// 移动状态变为true
						// 计算实际移动到的距离
						float moveMarginTop = marginTop - difference;// 预计移动到的距离
						if (moveMarginTop > mainView.getHeight() - topHeight) {// 判断预计移动到的距离会覆盖顶部textView
							marginTop = mainView.getHeight() - topHeight;// 实际移动到的距离放在初始位置
						} else if (moveMarginTop < 0) {// 预计移动到的距离超出屏幕顶部
							marginTop = 0;
						} else {
							marginTop = (int) moveMarginTop;
						}
						// 移动
						setSildeViewLayoutParams(marginTop,
								mainView.getHeight() - marginTop);
					}
					break;
				case MotionEvent.ACTION_DOWN:
					if (!isDown && v.getTag().equals("0")) {// 没有执行按下操作且是第0次点击
						v.setTag("1");// 点击次数变为1
						isDown = true;// 修改按下操作变为true
						downY = event.getY();// 记录按下时的Y坐标
					}
					break;
				case MotionEvent.ACTION_UP:
					if (isDown && v.getTag().equals("1")) {// 执行了按下操作并且不是第二次点击
						if (isMove) {// 没有移动过手指
							actionUp();
						} else if (!isScrollList) {// 判断没有滑动过listView
							// 把焦点抛给listView的其他监听
							isMove = false;
							isDown = false;
							isScrollList = false;
							v.setTag("0");// 点击次数变为0
							return false;
						}

						// 复位

					}
					isMove = false;
					isDown = false;
					isScrollList = false;
					v.setTag("0");// 点击次数变为0
					break;
				}
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (sildeListener != null)
					sildeListener.onItemClick(parent, view, position, id);
			}
		});
	}

	/**
	 * 复位
	 * 
	 * @return 返回false代表动画还在执行请不要做任何操作
	 */
	public boolean reset() {
		switch (type) {
		case 1:
			type = 0;
			move(mainView.getHeight() - topHeight - marginTop);
			break;
		case 2:
			type = 0;
			move(mainView.getHeight() - topHeight);
			break;
		}
		return !move;
	}

	/**
	 * 获取listview滑动的Y
	 * 
	 * @return
	 */
	public int getScrollY() {
		View c = listView.getChildAt(0);
		if (c == null) {
			return 0;
		}
		int firstVisiblePosition = listView.getFirstVisiblePosition();
		int top = c.getTop();
		return -top + firstVisiblePosition * c.getHeight();
	}

	/**
	 * 顶部标题按钮点击
	 */
	private void onClickTop() {
		switch (type) {
		case 0:
			type = 1;
			move(mainView.getHeight() / 2 - marginTop);
			break;

		case 1:
			type = 0;
			move(mainView.getHeight() - topHeight - marginTop);
			break;
		case 2:
			type = 0;
			move(mainView.getHeight() - topHeight);
			break;
		}
	}

	/**
	 * 松开手指后的滑动方法
	 */
	private void actionUp() {
		if (marginTop > mainView.getHeight() / 4 * 3
				&& marginTop != mainView.getHeight() - topHeight) {// 高度大于屏幕的四分之三切小于初始
			type = 0;
			move(mainView.getHeight() - topHeight - marginTop);
		}
		if (marginTop == mainView.getHeight() - topHeight) {// 高度为初始
			type = 0;
		}
		if (marginTop > mainView.getHeight() / 2
				&& marginTop < mainView.getHeight() / 4 * 3) {// 高度小于四分之三且高度大于二分之一
			type = 1;
			move(mainView.getHeight() / 2 - marginTop);
		}
		if (marginTop == mainView.getHeight() / 2) {// 高度等于二分之一
			type = 1;
		}
		if (marginTop < mainView.getHeight() / 2
				&& marginTop > mainView.getHeight() / 4) {// 高度小于二分之一且高度大于四分之一
			type = 1;
			move(mainView.getHeight() / 2 - marginTop);
		}
		if (marginTop < mainView.getHeight() / 4 && marginTop > 0) {// 高度小于四分之一切高度大于0
			move(-marginTop);
			type = 2;
		}
		if (marginTop == 0) {// 高度等于0
			type = 2;
		}
	}

	/**
	 * 位移动画
	 * 
	 * @param lastHeight
	 * @param toHeight
	 */
	private void move(final int toYDelta) {
		move = true;
		new Thread(new Runnable() {
			public void run() {

				int i = 1;
				int max = 6;
				int y = 0;// 第六次移动的距离
				int moveY = 0;// 前20次移动的距离
				int yDelta = toYDelta;
				if (Math.abs(yDelta) < 20) {
					max = Math.abs(yDelta) + 1;
					moveY = toYDelta < 0 ? -1 : 1;
				} else if (yDelta % 20 != 0) {
					moveY = (yDelta - (yDelta % 20)) / 20;
					y = yDelta % 20;
					yDelta = yDelta - yDelta % 20;
					max = 21;
				} else if (yDelta % 20 == 0) {
					moveY = yDelta / 20;
					max = 21;
				}
				while (i != max) {
					if (y == 0) {
						marginTop = marginTop + moveY;
					} else {
						marginTop = marginTop + moveY + (y < 0 ? -1 : 1);
						if (y < 0) {
							y++;
						} else {
							y--;
						}
					}
					i++;
					toMove(i == max);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	/**
	 * 设置标题栏文字
	 * 
	 * @param str
	 */
	public void setTitleText(String str) {
		vTop.setText(str);
	}

	/**
	 * 设置底部左侧图片
	 * 
	 * @param id
	 */
	public void setLeftImageIcon(int id) {
		imgLeft.setBackgroundResource(id);
	}

	/**
	 * 设置底部右侧图片
	 * 
	 * @param id
	 */
	public void setRightImageIcon(int id) {
		imgRight.setBackgroundResource(id);
	}

	/**
	 * 设置底部左侧文字
	 * 
	 * @param id
	 */
	public void setLeftText(int id) {
		tvLeft.setText(id);
	}

	/**
	 * 设置底部右侧文字
	 * 
	 * @param id
	 */
	public void setRightText(int id) {
		tvRight.setText(id);
	}

	/**
	 * 隐藏底部按钮
	 */
	public void goneButtom() {
		vButtom.setVisibility(View.GONE);
	}

	/**
	 * 获取通知栏高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 设置移动view的高度
	 * 
	 * @param marginTop
	 * @param height
	 */
	private void setSildeViewLayoutParams(int marginTop, int height) {
		LayoutParams layoutParams = (LayoutParams) sildeView.getLayoutParams();
		layoutParams.setMargins(0, marginTop, 0, 0);
		layoutParams.height = height;
		sildeView.setLayoutParams(layoutParams);
	}

	private void toMove(final boolean isEnd) {
		sildeView.post(new Runnable() {

			@Override
			public void run() {
				setSildeViewLayoutParams(marginTop, mainView.getHeight()
						- marginTop);
				if (isEnd) {
					move = false;
					if (sildeListener != null)
						sildeListener.animatorEnd(type);
				}
			}
		});
	}

}
