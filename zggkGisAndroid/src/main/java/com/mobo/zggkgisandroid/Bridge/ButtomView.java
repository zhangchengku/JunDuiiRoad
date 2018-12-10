package com.mobo.zggkgisandroid.Bridge;

import com.tooklkit.Tooklkit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 桥梁明细底部圆圈
 * 
 * @author wuhao
 *
 */
public class ButtomView extends View {
	boolean isSel;// 是否选中
	private Paint paint;// 画笔

	public ButtomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
	}

	public void setSel(boolean isSel) {
		this.isSel = isSel;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isSel) {
			paint.setColor(0xff41719c);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
			paint.setColor(0xff5b9bd5);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - Tooklkit.dip2px(getContext(), 2),
					paint);
		} else {
			paint.setColor(0xff41719c);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
			paint.setColor(0xffffffff);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - Tooklkit.dip2px(getContext(), 2),
					paint);
		}
	}
}
