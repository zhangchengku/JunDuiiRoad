package com.mobo.zggkgisandroid.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * 
 * @author wuhao
 *
 */
public class GridViewForScrollView extends GridView {   
	/**
	 * 构造方法
	 * @param context context对象
	 * @param attrs 样式参数
	 */
    public GridViewForScrollView(Context context, AttributeSet attrs) {   
        super(context, attrs);   
    }   
    /**
     * 构造方法
     * @param context context对象
     */
    public GridViewForScrollView(Context context) {   
        super(context);   
    }   
    /**
     * 构造方法
     * @param context context对象
     * @param attrs 样式参数
     * @param defStyle 默认样式
     */
    public GridViewForScrollView(Context context, AttributeSet attrs, int defStyle) {   
        super(context, attrs, defStyle);   
    }   
  
    @Override   
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
  
        int expandSpec = MeasureSpec.makeMeasureSpec(   
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);   
        super.onMeasure(widthMeasureSpec, expandSpec);   
    }   
}   
