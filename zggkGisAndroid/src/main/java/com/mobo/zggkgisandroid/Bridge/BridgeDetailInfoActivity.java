package com.mobo.zggkgisandroid.Bridge;

import java.util.ArrayList;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.BaseActivity;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.BridgeDetailInfoResult;
import com.mobo.zggkgisandroid.WebModel.BridgeDetailInfoResult.BridgeDetailInfo;
import com.mobo.zggkgisandroid.WebModel.BridgeImagesBeanResult;
import com.mobo.zggkgisandroid.WebModel.BridgeImagesBeanResult.BridgeImagesBean.ImageList;
import com.tooklkit.Tooklkit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 桥梁明细信息页面
 * 
 * @author wuhao
 *
 */
public class BridgeDetailInfoActivity extends BaseActivity {

	private String province_code;// 城市编码
	private String bridge_code;// 桥梁编码
	private String bridge_name;// 桥梁名称
	private FragmentAdapter fragmentAdapter;// fragment管理适配器
	private ImageView vpTopImage;// 顶部显示图片
	private ViewPager viewPager;// 底部内容布局
	private BridgeDetailInfo bridgeDetailInfo;// 底部内容数据
	private LinearLayout buttomLayout;// 小圆圈指示器
	private ButtomView[] points;// 小圆圈指示器
	private ImageView imgLast;// 上一张图片
	private ImageView imgNext;// 下一张图片
	private ArrayList<ImageList> image_list;// 顶部图片集合
	private ArrayList<ImageList> imageBaseInfoList;// 顶部基本图片集合
	private int index;// 当前播放的图片张数

	private BridgeBaseInfoFragment baseInfoFragment;
	private BridgeDiseaseInfoFragment bridgeDiseaseInfoFragment;
	private BridgeJudgeInfoFragment bridgeJudgeInfoFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentXml(R.layout.activity_bridge_search_info);

		// 初始化
		init();
		imgLeft.setImageResource(R.drawable.set_back_unclick);
		province_code = getIntent().getStringExtra("province_code");
		bridge_code = getIntent().getStringExtra("bridge_code");
		bridge_name = getIntent().getStringExtra("bridge_name");
		setTitleText(bridge_name.length() > 10 ? bridge_name.substring(0, 10) : bridge_name);
		new ThreadWithProgressDialog().Run(this, new DialogTask(), R.string.com_loading);

		// 设置ViewPager监听
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setImageBackground(arg0);
				if (arg0 == 1
						&& bridgeDiseaseInfoFragment.getImage_list() != null
						&& imageBaseInfoList == null)
					return;
				if (arg0 != 1 && imageBaseInfoList != null
						&& bridgeDiseaseInfoFragment.getImage_list() == null)
					return;
				if (arg0 == 1) {
					if (bridgeDiseaseInfoFragment.getImage_list() != null) {
						index = 0;
						image_list = bridgeDiseaseInfoFragment.getImage_list();
						setImageBitmap();
					} else {
						if (!Utils.isNull(bridgeDiseaseInfoFragment
								.getDamageid()))
							new ThreadWithProgressDialog().Run(
									BridgeDetailInfoActivity.this,
									new GetImageDialogTask("DAMAGE",
											bridgeDiseaseInfoFragment
													.getDamageid(),
											bridgeDiseaseInfoFragment
													.getPosition()),
									R.string.com_loading);
					}
				} else {
					if (imageBaseInfoList != null) {
						index = 0;
						image_list = imageBaseInfoList;
						setImageBitmap();
					} else {
						new ThreadWithProgressDialog().Run(
								BridgeDetailInfoActivity.this,
								new GetImageDialogTask("BASE", "", 0),
								R.string.com_loading);
					}

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		imgLast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				index--;
				setImageBitmap();
			}
		});
		imgNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				index++;
				setImageBitmap();
			}
		});
	}

	/**
	 * 病害页面获取顶部图片
	 * 
	 * @param damage_id
	 * @param position
	 */
	public void getImageBitmap(String damage_id, int position) {
		new ThreadWithProgressDialog().Run(BridgeDetailInfoActivity.this,
				new GetImageDialogTask("DAMAGE", damage_id, position),
				R.string.com_loading);
	}

	@Override
	protected void onClickLeft(View view) {
		super.onClickLeft(view);
		finish();
	}

	private void init() {
		vpTopImage = (ImageView) findViewById(R.id.bridge_search_info_top_img);
		vpTopImage.getLayoutParams().height = (int) (Tooklkit.getWidth(this) / 1.655197132616487);
		imgLast = (ImageView) findViewById(R.id.bridge_search_info_top_last_img);
		imgNext = (ImageView) findViewById(R.id.bridge_search_info_top_next_img);

		viewPager = (ViewPager) findViewById(R.id.bridge_search_info_content_vp);
		// 小圆圈指示器
		buttomLayout = (LinearLayout) findViewById(R.id.bridge_search_info_buttom_ll);
		points = new ButtomView[3];
		for (int i = 0; i < points.length; i++) {
			points[i] = (ButtomView) buttomLayout.getChildAt(i);
		}
		points[0].setSel(true);

	}

	// 改变指示器的状态
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < points.length; i++) {
			if (i == selectItems) {
				points[i].setSel(true);
			} else {
				points[i].setSel(false);
			}
		}
	}

	/**
	 * 获取明细信息
	 * 
	 * @author wuhao
	 * 
	 */
	private class DialogTask implements ThreadWithProgressDialogTask {
		BridgeDetailInfoResult result;
		BridgeImagesBeanResult bridgeImagesBeanResult;

		@Override
		public boolean TaskMain() {
			result = CustomApp.app.connInteface.bridgeDetailInfo(province_code,
					bridge_code);
			bridgeImagesBeanResult = CustomApp.app.connInteface
					.getBridgeImages(province_code, bridge_code, "BASE", "");
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			int state = Utils.isCorrect(result, true);
			if (state == 0 && result.getResults() != null) {
				buttomLayout.setVisibility(View.VISIBLE);

				bridgeDetailInfo = result.getResults().get(0);
				fragmentAdapter = new FragmentAdapter(
						getSupportFragmentManager());
				viewPager.setAdapter(fragmentAdapter);
			} else if (state == 2) {
				CustomApp.app.exit(BridgeDetailInfoActivity.this);
			}
			if (Utils.isCorrect(bridgeImagesBeanResult, state == 0 ? true
					: false) == 0
					&& bridgeImagesBeanResult.getResults() != null
					&& bridgeImagesBeanResult.getResults().get(0).getImage_list() != null
					&& bridgeImagesBeanResult.getResults().get(0).getImage_list()
							.size() > 0) {
				image_list = bridgeImagesBeanResult.getResults().get(0)
						.getImage_list();
				imageBaseInfoList = image_list;
				setImageBitmap();
			}
			return true;
		}

	}

	/**
	 * 获取图片
	 * 
	 * @author wuhao
	 * 
	 */
	private class GetImageDialogTask implements ThreadWithProgressDialogTask {
		private BridgeImagesBeanResult bridgeImagesBeanResult;
		private int position;
		private String image_class;
		private String damage_id;

		public GetImageDialogTask(String image_class, String damage_id,
				int position) {
			this.image_class = image_class;
			this.damage_id = damage_id;
			this.position = position;
		}

		@Override
		public boolean TaskMain() {
			bridgeImagesBeanResult = CustomApp.app.connInteface
					.getBridgeImages(province_code, bridge_code, image_class,
							damage_id);
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			int state = Utils.isCorrect(bridgeImagesBeanResult, true);
			if (state == 0
					&& bridgeImagesBeanResult.getResults() != null
					&& bridgeImagesBeanResult.getResults().get(0).getImage_list() != null
					&& bridgeImagesBeanResult.getResults().get(0).getImage_list()
							.size() > 0) {
				index = 0;
				image_list = bridgeImagesBeanResult.getResults().get(0)
						.getImage_list();
				setImageBitmap();
				if (!Utils.isNull(damage_id)) {
					bridgeDiseaseInfoFragment.setPosition(position);
					bridgeDiseaseInfoFragment.setImage_list(image_list);
				} else {
					imageBaseInfoList = image_list;
				}
			}

			return false;
		}

	}

	/**
	 * viewpager适配器
	 * 
	 * @author wuhao
	 * 
	 */
	private class FragmentAdapter extends FragmentPagerAdapter {

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0) {
				if (baseInfoFragment == null)
					baseInfoFragment = new BridgeBaseInfoFragment(
							bridgeDetailInfo.getBase_info());
				return baseInfoFragment;
			} else if (position == 1) {
				if (bridgeDiseaseInfoFragment == null)
					bridgeDiseaseInfoFragment = new BridgeDiseaseInfoFragment(
							bridgeDetailInfo.getDamage_info());
				return bridgeDiseaseInfoFragment;
			} else {
				if (bridgeJudgeInfoFragment == null)
					bridgeJudgeInfoFragment = new BridgeJudgeInfoFragment(
							bridgeDetailInfo.getEvaluate_info());
				return bridgeJudgeInfoFragment;
			}

		}

		@Override
		public int getCount() {
			return 3;
		}

	}

	/**
	 * 显示图片
	 */
	private void setImageBitmap() {
		try {
			if (image_list.get(index) != null
					&& !Utils.isNull(image_list.get(index).getImage_data()))
				vpTopImage.setImageBitmap(handlerBitmap(image_list.get(index)
						.getImage_data()));
			if (image_list.size() - 1 == index) {
				imgNext.setVisibility(View.GONE);
			} else {
				imgNext.setVisibility(View.VISIBLE);
			}
			if (index == 0) {
				imgLast.setVisibility(View.GONE);
			} else {
				imgLast.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
		}

	}

	/***
	 * 把字符串转化成bitmap对象
	 * 
	 * @param bitmapstring
	 * @return
	 */
	public Bitmap handlerBitmap(String bitmapstring) {
		if (bitmapstring == null) {

			return null;
		}
		byte[] temp = Base64.decode(bitmapstring, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(temp, 0, temp.length);

	}
}
