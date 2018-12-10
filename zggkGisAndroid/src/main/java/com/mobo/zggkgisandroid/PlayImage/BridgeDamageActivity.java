package com.mobo.zggkgisandroid.PlayImage;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.WebInterface.ConnectionInteface;

/**
 * 
 * 
 * 此activity为桥梁病害信息显示页面
 * 
 * @author xushiwei
 * 
 */

public class BridgeDamageActivity extends Activity implements OnGestureListener
{
	/*************** 定义变量 *****************************/
	private int mindex;// 访问桥梁病害图片的下标
	private ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();
	private ViewFlipper bridge_damage_viewfliper;// 桥梁病害图片容器
	private GestureDetector detector;// 手势操作控制
	private Animation top2bottminAnimation;// 顶部进入动画
	private Animation top2bottmoutAnimation;// 顶部出去动画
	private Animation bottom2topinAnimation;// 底部进入动画
	private Animation bottom2topoutAnimation;// 底部出去动画
	private int screenHeight;// 屏幕高度

	private LinearLayout bridge_damage_backgroud;
	/************************** 网络参数列表 ********************************/
	private ConnectionInteface conn;// 网络连接
	private String token;// 令牌
	private String bridge_id;// 桥梁id
	private String bridge_code;// 桥梁编码

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bridge_damage_info_layout);

	}

	/**
	 * 
	 * 初始化变量数据
	 */
	public void init()
	{

		/****/
		detector = new GestureDetector(BridgeDamageActivity.this);
		/*********** 初始化bridge_damage_viewfliper ****************/

		bridge_damage_viewfliper = (ViewFlipper) findViewById(R.id.bridge_damage_viewfliper);
		bridge_damage_viewfliper.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// 定义bridge_damage_viewfliper手势监听转向
				return BridgeDamageActivity.this.detector.onTouchEvent(event);
			}
		});
		/*************** 屏幕高度 *************************************/
		screenHeight = getResources().getDisplayMetrics().heightPixels;
		/********************** 动画效果 *****************************/
		top2bottminAnimation.setDuration(1000);
		top2bottmoutAnimation = new TranslateAnimation(0, 0, 0, screenHeight);
		top2bottmoutAnimation.setDuration(1000);
		bottom2topinAnimation = new TranslateAnimation(0, 0, screenHeight, 0);
		bottom2topinAnimation.setDuration(1000);
		bottom2topoutAnimation = new TranslateAnimation(0, 0, 0, -screenHeight);
		bottom2topoutAnimation.setDuration(1000);
		bridge_damage_backgroud = (LinearLayout) findViewById(R.id.bridge_damage_backgroud);
	}

	@Override
	public boolean onDown(MotionEvent e)
	{
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e)
	{

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e)
	{

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{

		if (e1.getY() - e2.getY() > 120) {

			bridge_damage_viewfliper.setInAnimation(bottom2topinAnimation);
			bridge_damage_viewfliper.setOutAnimation(bottom2topoutAnimation);
			bridge_damage_viewfliper.showPrevious();
		} else if (e1.getY() - e2.getY() < -120) {
			bridge_damage_viewfliper.setInAnimation(top2bottminAnimation);
			bridge_damage_viewfliper.setOutAnimation(top2bottmoutAnimation);
			bridge_damage_viewfliper.showNext();
		}
		return false;
	}

}
