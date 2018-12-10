package com.mobo.libupdate.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobo.libupdate.R;

/**
 * 默认 dialog
 * 
 * @author dxv
 * 
 */
public class DefaultDialog extends Dialog {

	private WindowManager.LayoutParams params;// 外框params
	private View dialogXML;// dialog的xml布局

	private OnLeftListener LeftListener = null;
	private OnRightListener rightListener = null;
	protected TextView titleTv;
	// private RelativeLayout contentRly;//内容layout
	protected TextView leftBtn;
	protected TextView rightBtn;// 右侧按钮
	private ProgressBar bar;// 进度条
	private TextView progressTv;// 文件下载进度
	private TextView fileSizeTv;// 文件大小
	private TextView showTxtTv; // 文本dialog 文本view
	private ImageView iLine;// 竖线

	public DefaultDialog(Context context) {
		super(context);
		dialogXML = View.inflate(context, R.layout.dialog, null);
		initView();
	}

	/**
	 * 初始化view
	 */
	public void initView() {
		titleTv = (TextView) findView(R.id.dialog_title_tv);
		leftBtn = (TextView) findView(R.id.dialog_button_left);
		rightBtn = (TextView) findView(R.id.dialog_button_right);
		iLine = (ImageView) findView(R.id.im_iline);
		bar = (ProgressBar) findView(R.id.dialog_progress_bar);
		bar.setProgress(0);

		// ----------------------------------添加progressbar的进度图片
		int r = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
				getContext().getResources().getDisplayMetrics());
		float[] outerR = new float[] { r, r, r, r, r, r, r, r };
		RoundRectShape rr = new RoundRectShape(outerR, null, null);
		ShapeDrawable drawableBgProgress = new ShapeDrawable();// 创建圆角的drawable
		drawableBgProgress.setShape(rr);
		drawableBgProgress.getPaint().setColor(
				getContext().getResources().getColor(R.color.bule));
		ShapeDrawable drawableBg = new ShapeDrawable();
		drawableBg.setShape(rr);
		drawableBg.getPaint().setColor(
				getContext().getResources().getColor(R.color.gray));
		Drawable layers[] = {
				drawableBg,
				new ClipDrawable(drawableBgProgress, Gravity.LEFT,
						ClipDrawable.HORIZONTAL) };
		LayerDrawable progressDrawable = new LayerDrawable(layers);
		progressDrawable.setId(0, android.R.id.background);
		progressDrawable.setId(1, android.R.id.progress);
		bar.setProgressDrawable(progressDrawable);
		// -----------------------------------------------------------------

		progressTv = (TextView) findView(R.id.dialog_content_planTv);
		progressTv.setText("0%");
		fileSizeTv = (TextView) findView(R.id.file_size_tv);
		showTxtTv = (TextView) findView(R.id.dialog_showTxt_tv);
	}

	/**
	 * title 值设置
	 * 
	 * @param value
	 *            值
	 */
	public void setTitleValue(String value) {
		titleTv.setText(value);
	}

	public void setRightBg(Drawable drawable) {
		rightBtn.setBackgroundDrawable(drawable);
	}

	public void setRightcor(int color) {
		rightBtn.setBackgroundColor(color);
	}

	/**
	 * 将progressbar dialog 转变为text dialog
	 * 
	 * @param showText
	 *            显示文本
	 */
	public void changeTextDialog(String showText) {
		bar.setVisibility(View.GONE);
		progressTv.setVisibility(View.GONE);
		fileSizeTv.setVisibility(View.GONE);
		showTxtTv.setVisibility(View.VISIBLE);
		showTxtTv.setText(showText);
	}

	/**
	 * 左侧按钮设置
	 * 
	 * @param value
	 *            值
	 */
	public void setLeftBtnValue(String value) {
		leftBtn.setText(value);
	}

	/**
	 * 右侧按钮设置
	 * 
	 * @param value
	 *            值
	 */
	public void setRightBtnValue(String value) {
		rightBtn.setText(value);
	}

	/**
	 * 右侧按钮设置
	 * 
	 * @param value
	 *            值
	 */
	public void setRightBtnValueCol(int value) {
		rightBtn.setTextColor(value);
	}

	/**
	 * 单按钮
	 */
	public void btnAlone() {
		leftBtn.setVisibility(View.GONE);

	}

	/**
	 * 将text dialog 转化为默认的progressbar dialog
	 * 
	 * @param barProgress
	 *            progressbar 的进度值
	 * @param textProgress
	 *            下载比例值
	 * @param fileSizeValue
	 *            下载文件大小值
	 */
	public void changeToDefault(String barProgress, String textProgress,
			String fileSizeValue) {
		bar.setVisibility(View.VISIBLE);
		progressTv.setVisibility(View.VISIBLE);
		fileSizeTv.setVisibility(View.VISIBLE);
		bar.setProgress(Integer.valueOf(barProgress));
		progressTv.setText(textProgress);
		fileSizeTv.setText(fileSizeValue);
	}

	/**
	 * 设置progressbar 的进度值
	 * 
	 * @param value
	 */
	public void setProgressBarValue(int value) {
		bar.setProgress(value);
	}

	/**
	 * 设置text 进度值
	 * 
	 * @param value
	 */
	public void setProgressTvValue(String value) {
		Log.d("dialog", value);
		progressTv.setText(value);
	}

	/**
	 * 设置已下载量
	 * 
	 * @param value
	 */
	public void setFileSizeDownload(String value) {
		fileSizeTv.setText(value);
	}

	/**
	 * 初始化view
	 * 
	 * @param id
	 * @return
	 */
	public View findView(int id) {
		return dialogXML.findViewById(id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标头
		getWindow().getDecorView().getBackground().setAlpha(0);// 设置透明度
		params = getWindow().getAttributes();// 获取params
		getWindow().setContentView(dialogXML);// 加载xml
		getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);
		// 获得屏幕宽
		params.height = LayoutParams.WRAP_CONTENT;
		params.width = LayoutParams.WRAP_CONTENT;

		rightBtn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (rightListener != null) {
					rightListener.onRightListener();
					cancel();
				}
			}
		});
		leftBtn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (LeftListener != null) {
					LeftListener.onLeftListener();
				}
				cancel();
			}
		});
	}

	/**
	 * 显示 text形式的dialog
	 */
	public void showTextDialog(String explain) {
		bar.setVisibility(View.GONE);
		progressTv.setVisibility(View.GONE);
		fileSizeTv.setVisibility(View.GONE);
		showTxtTv.setVisibility(View.VISIBLE);
		showTxtTv.setText(explain);
	}

	/**
	 * dialog左侧按钮监听
	 * 
	 * @author dingxuewei
	 * 
	 */
	public interface OnLeftListener {
		public void onLeftListener();
	}

	/**
	 * dialog 右侧按钮监听
	 * 
	 * @author dingxuewei
	 * 
	 */
	public interface OnRightListener {
		public void onRightListener();
	}

	/**
	 * 右按钮的listener
	 * 
	 * @param onLeftLintener
	 */
	public void setRightButtonListener(OnRightListener onRightListener) {
		rightListener = onRightListener;

	}

	/**
	 * 左按钮的listener
	 * 
	 * @param onLeftLintener
	 */
	public void setLeftButtonLintener(OnLeftListener onLeftListener) {
		LeftListener = onLeftListener;

	}

	/**
	 * 设置只有一个按钮
	 */
	public void OnlyButton() {
		leftBtn.setVisibility(View.GONE);
		iLine.setVisibility(View.GONE);
	}
	/**
	 * 设置显示竖条
	 */
	public void showLine() {
		iLine.setVisibility(View.VISIBLE);
	}

}
