package com.mobo.zggkgisandroid.FortyFive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 四五类桥列表排名
 * 
 * @author WuXiaoHao
 *
 */
public class FortyFiveRankTextView extends TextView {
	private Paint paint;

	public FortyFiveRankTextView(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(0xff84c1ff);
		paint.setAntiAlias(true);
	}

	public FortyFiveRankTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setColor(0xff84c1ff);
		paint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);
		super.onDraw(canvas);
	}
}
