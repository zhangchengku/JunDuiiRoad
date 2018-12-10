package com.mobo.zggkgisandroid.PlayImage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MyImageVIew extends ImageView
{

	public MyImageVIew(Context context)
	{
		super(context);
	}

	public MyImageVIew(Context context, AttributeSet paramAttributeSet)
	{
		super(context, paramAttributeSet);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return super.onTouchEvent(event);
	}

}
