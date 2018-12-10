package com.mobo.zggkgisandroid.ServiceArea;

import com.mobo.zggkgisandroid.BaseActivity;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.ServiceAreaListResult.ServiceArea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 服务区详情
 * 
 * @author WuXiaoHao
 *
 */
public class ServiceAreaDetailsActivity extends BaseActivity {
	private ServiceArea serviceArea;

	private ImageView imgBig;// 顶部大图
	private TextView tvPhone;// 电话
	private TextView tvAddress;// 地址
	private TextView tvCurrentState;// 当前状态
	private TextView tvKParkingSpace;// 可用停车位
	private TextView tvGParkingSpace;// 停车位总数
	private TextView tvIntroduce;// 简介

	private int height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentXml(R.layout.servicearea_details_activity_layout);
		init();
		initView();
	}

	private void init() {
		serviceArea = (ServiceArea) getIntent().getSerializableExtra("result");
		tvTitle.setText(serviceArea.getService_area_name());
		imgLeft.setImageResource(R.drawable.rank_back_btn_sel);

		imgBig = (ImageView) findViewById(R.id.img_servicearea_details);
		tvPhone = (TextView) findViewById(R.id.tv_servicearea_details_phone);
		tvAddress = (TextView) findViewById(R.id.tv_servicearea_details_address);
		tvCurrentState = (TextView) findViewById(R.id.tv_servicearea_details_current_state);
		tvKParkingSpace = (TextView) findViewById(R.id.layout_servicearea_details_k_parking_space);
		tvGParkingSpace = (TextView) findViewById(R.id.layout_servicearea_details_g_parking_space);
		tvIntroduce = (TextView) findViewById(R.id.tv_servicearea_details_introduce);

	}

	private void initView() {
		imgBig.getLayoutParams().height = (int) (Utils.getWidth(this) / 1.70625);
		CustomApp.IMAGE_CACHE.get(serviceArea.getService_area_img(), imgBig);

		tvPhone.setText(serviceArea.getService_area_phone());
		tvAddress.setText(serviceArea.getService_area_address());
		tvCurrentState.setText(serviceArea.getCurrent_state());
		tvKParkingSpace.setText(serviceArea.getAvailable_parking_spaces() + "个");
		tvGParkingSpace.setText("( 共" + serviceArea.getParking_space() + " )个");
		tvIntroduce.setText(serviceArea.getService_area_introduce());

		height = getViewHeight(findViewById(R.id.layout_servicearea_details_current_state));
		findViewById(R.id.layout_servicearea_details_service_content).getLayoutParams().height = height;

		findViewById(R.id.layout_servicearea_details_phone).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (tvPhone.getText().equals(""))
					return;
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvPhone.getText().toString()));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
	}

	private int getViewHeight(View view) {
		int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(width, height);
		return view.getMeasuredHeight();

	}

	@Override
	protected void onClickLeft(View view) {
		super.onClickLeft(view);
		finish();
	}
}
