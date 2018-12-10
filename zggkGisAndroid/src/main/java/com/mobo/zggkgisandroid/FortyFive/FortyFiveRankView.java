package com.mobo.zggkgisandroid.FortyFive;

import com.mobo.zggkgisandroid.Utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 四五类桥进度条
 * 
 * @author WuXiaoHao
 *
 */
public class FortyFiveRankView extends View {
	private final double radius;
	private Paint paint;
	private double maxCount;// 最大值
	private double count;// 当前值
	private double width = -1;
	private boolean isDrawCircle = true;

	public void setDrawCircle(boolean isDrawCircle) {
		this.isDrawCircle = isDrawCircle;
	}

	public FortyFiveRankView(Context context, AttributeSet attrs) {
		super(context, attrs);
		radius = Utils.dip2px(context, 15) / 2;
		paint = new Paint();
		paint.setColor(0xfffa5402);
		paint.setAntiAlias(true);
	}

	public void setColor(int color) {
		paint.setColor(color);
	}

	public void setMaxCount(double maxCount) {
		this.maxCount = maxCount;
	}

	public void setCount(double count) {
		this.count = count;
		width = -1;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (count == 0)
			return;
		if (width == -1) {
			if (isDrawCircle) {
				width = (((double) getWidth()) - radius * 2d) / (maxCount / count);
			} else {
				width = ((double) getWidth()) / (maxCount / count);
			}
		}
		if (width < 1)
			width = 1;
		if (isDrawCircle) {
			canvas.drawCircle((int) radius, (int) radius, (int) radius, paint);
			canvas.drawRect((int) radius, getHeight() / 3, (int) radius * 2, getHeight() / 3 * 2, paint);
			canvas.drawRect((int) radius * 2, getHeight() / 3, (int) (radius * 2 + width), getHeight() / 3 * 2, paint);
		} else {
			canvas.drawRect(0, getHeight() / 3, (int) width, getHeight() / 3 * 2, paint);
		}

	}
}
