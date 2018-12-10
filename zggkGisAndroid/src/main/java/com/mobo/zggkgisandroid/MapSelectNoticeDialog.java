package com.mobo.zggkgisandroid;

import java.util.List;

import com.tooklkit.Tooklkit;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 好友详情dialog
 * 
 * @author WuXiaoHao
 * 
 * @time 下午4:21:50
 */
public class MapSelectNoticeDialog {
	private Activity activity;
	private Dialog dialog;
	private View view;

	public MapSelectNoticeDialog(final Activity activity) {
		this.activity = activity;
		dialog = new Dialog(activity, R.style.CustomDialogStyle);
		view = LayoutInflater.from(activity).inflate(R.layout.map_select_notice_dialog_layout, null);
		dialog.setContentView(view);
		final ImageView imageView = (ImageView) view.findViewById(R.id.img_map_select_notice);
		final TextView textView = (TextView) view.findViewById(R.id.tv_map_select_notice);
		view.findViewById(R.id.ll_map_select_notice).setOnClickListener(// 退出
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (CustomApp.app.pr.getString("map_select_notice_dialog_layout").equals("")) {
							imageView.setImageResource(R.drawable.com_xieyi_duigou);
							CustomApp.app.pr.saveString("map_select_notice_dialog_layout", "1");
						} else {
							imageView.setImageResource(R.drawable.com_xieyi_duigou_pressed);
							CustomApp.app.pr.saveString("map_select_notice_dialog_layout", "");
						}
					}
				});
		ViewTreeObserver vto = textView.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int height = textView.getMeasuredHeight();
				imageView.setLayoutParams(new LayoutParams(height, height));

			}

		});

	}

	public void show() {
		dialog.show();

	}

	public void dismiss() {
		dialog.dismiss();

	}

}
