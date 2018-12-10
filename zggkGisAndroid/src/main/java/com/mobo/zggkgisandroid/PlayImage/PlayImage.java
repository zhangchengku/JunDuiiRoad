package com.mobo.zggkgisandroid.PlayImage;

//import jcifs.dcerpc.msrpc.srvsvc;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebInterface.ConnectionInteface;
import com.mobo.zggkgisandroid.WebModel.MilListResult;
import com.mobo.zggkgisandroid.WebModel.MilListResult.station;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult.PlayImageMess.image_list;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult.PlayImageMess.image_list.bridge;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult.PlayImageMess.image_list.construction;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult.PlayImageMess.image_list.facility;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult.PlayImageMess.image_list.road;
import com.mobo.zggkgisandroid.WebModel.PlayImageResult.PlayImageMess.image_list.tunnel;
import com.mobo.zggkgisandroid.photoView.PhotoView;

/**
 * 
 * 此类为播放图片的界面
 * 
 * 
 * @author xushiwei
 * 
 */

public class PlayImage extends Activity {

	/********************** 控件初始化 ***************************/

	private PhotoView vBackgroudimageIm; // 背景图片
	private ImageButton vBackBtn;// 返回按钮
	private ImageButton vinfomation;// 信息显示按钮
	private LinearLayout vImageDetailLL;// 图片详情容器
	private LinearLayout play_image_backgroud;// 图片背景容器

	private ImageView up;// 前方指示图片
	private ImageView down;// 后方指示图片
	private LinearLayout vImageUpLL;// 前方按钮
	private LinearLayout vImagePlayLL;// 播放按钮
	private LinearLayout vImageDownLL;// 后方按钮
	private LinearLayout vSeekContainer;// 进度条容器
	private LinearLayout vthumbImgContainer;// 小图容器
	private ImageView vSmallImg;// 小图
	private RelativeLayout.LayoutParams params;
	private PowerManager.WakeLock wakeLock;// 控制屏幕常亮的工具
	private TextView vSmallImgLocation;// 小图所在位置
	private TextView vSmallImgStation;// 小图桩号
	private TextView newStation;
	private SeekBar vBar;// 进度条
	private boolean isbar;// Vbar拖动没有true代表有拖动 false代表没有
	private boolean beforeBarIsPlay = false;// 在拖动bar前是不是播放

	private ImageView vPlayOrPause;// 播放或者暂停的图标
	private Intent intent;// 启动该页面的意图
	private Animation anim;// 动画效果播放的时候
	/********************** 初始化变量 ***************************/
	private int downNub;// 控制一次只有一个在下载 down为1时后台下载不显示圈圈下载 down为2时 前端显示圈圈下载 down
	private boolean beforemMove = true;// dialog圈圈时需不需要联网的控制，true为需要联网
	// 为0时没有下载
	private int showdown = 0;// 控制转圈下载的数量
	private boolean prevIsPlay = false;// 上一个状态
	private boolean mSwitchDetail = false;// 信息列表隐藏显示开关
	private boolean flag = false; // 播放控制条件
	private ThreadWithProgressDialog threadManage;// 线程管理者
	private int mindex;// 图片指标类
	private int listindex = -1;// 里程条指标类
	private int mType;// 任务类型判断 0 代表前进 1代表后退 2代表第一次进入系统
	private boolean isFirst = true;// 是不是第一次进入界面

	private View viewType; // 播放时 左侧信息窗口的
	// 关闭seekbar
	private ImageView close_seekbar;
	// 闲事seekbar
	private ImageView visiable_seekbar;
	// 屏幕宽度
	private int srceenwidth;
	// 屏幕高度
	private int srceenheigh;

	/**
	 * 
	 * 主线程处理界面的操作 主要用来处理播放界面的 该类实现的思想是hander发送message时调用isdown方法发送消息
	 */
	private Handler mhandler = new Handler()

	{

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			// 播放
			case 1:
				// 前进时
				if (0 == mType) {
					mindex++;
					listindex++;
					handlerData(mindex);
				} else if (1 == mType) {
					// 后退时
					mindex--;
					listindex--;
					handlerData(mindex);
				}

				break;

			// 转圈下载,
			case 2:
				// 判断要不要转圈
				if (downNub == 0 || downNub == 1) {
					// showdown为转圈的个数 如果有转圈了 就不会在转圈了
					if (0 == showdown) {
						// 判断转圈前是否是播放 如果播放暂停播放，转圈完在恢复播放
						if (flag) {
							flag = false;
							prevIsPlay = true;
						}
						// 如果是前进
						if (mType == 0) {
							// 没有到末尾转圈，到末尾弹框
							if (listindex == (Integer
									.valueOf(mileageListstation
											.getStation_size()) - 1)) {

								if (flag) {
									flag = false;
								}
								// 弹出播放完毕
								CustomApp.app.customToast(Gravity.CENTER,
										CustomApp.SHOW_TOAST_TIMES, "播放完毕");
							} else {
								// 否则启动转圈，并且标识有转圈
								startTheard(0);
								showdown = 1;
							}

							// 如果是后退时，判断有没有到头，到头不缓冲数据，也不会转圈
						} else if (mType == 1) {
							if (listindex == 0) {
								CustomApp.app.customToast(Gravity.CENTER,
										CustomApp.SHOW_TOAST_TIMES, "播放完毕");
							} else {

								startTheard(0);
								showdown = 1;
							}
						}
					}

				}

				break;

			// 后台下载 不会转圈，
			case 3:
				// 判断有没有下载 ，没有后台下载
				if (downNub == 0) {
					// 后台下载为1表示有后台下载
					downNub = 1;
					// 前进时到达播放末尾 不会下载数据 并且不会down置0
					if (mType == 0) {

						if (listindex == (Integer.valueOf(mileageListstation
								.getStation_size()) - 1)) {
							downNub = 0;

						} else {
							// beforeMOve作用是代表我如果后台下载速度慢，播放快时，转圈弹窗，转圈要不要下载数据
							beforemMove = false;
							new Myasyctask().execute();
						}
						// 后退到达播放头时，在后退 没有数据要不要联网
					} else if (mType == 1) {
						if (listindex == 0) {
							downNub = 0;

						} else {
							// beforeMOve作用是代表我如果后台下载速度慢，播放快时，转圈弹窗，转圈要不要下载数据
							beforemMove = false;
							new Myasyctask().execute();
						}
					}

				}
				// 后台下载时数据的填写
				if (0 == mType) {
					if (mindex < arrayimage_list.size() - 1) {
						mindex++;
						listindex++;
						handlerData(mindex);

					}
					if (mindex == arrayimage_list.size() - 1) {

						handlerData(mindex);

					}
				} else if (1 == mType) {
					if (mindex > 0) {
						mindex--;
						listindex--;
						handlerData(mindex);
					}
					if (mindex == 0) {
						handlerData(mindex);
					}

				}
				break;
			// 暂停的时候填写信息
			case 4:
				if (mindex >= 0 && arrayimage_list.size() > 0
						&& mindex < arrayimage_list.size()) {
					handlerData(mindex);
				}
				break;
			case 5:
				// 前进后退按钮的消失动画 在进入该页面只执行一次
				// Animation animation = new AlphaAnimation(1.0f, 0.0f);
				// animation.setDuration(3000);
				// animation.setFillAfter(true);
				// up.setAnimation(animation);
				// down.setAnimation(animation);
				break;
			default:
				break;
			}

		}

	};

	/********************** 请求网络参数列表 -----大图 ***************************/
	private ConnectionInteface connectionInteface;// 网络请求;
	private String province_code;// 省份编码
	private String token = CustomApp.app.token;// 令牌
	private String road_code;// 线路编码
	private String going_direction;// 上下行
	private String forward_or_back;// 前进还是后退
	private String road_station;// 桩号（最小桩号）
	private int page_size = 1;// 每次返回几条数据
	private int page = 1;// 请求第几页
	private String is_station = "0";// 是否已桩号为准
	private String station_index;// 桩号(索引)

	/********************** 网络返回参数列表 -----大图 ***************************/
	private PlayImageResult playimage;// 查询结果
	// private image_list image_list;// 图片列表结果
	private ArrayList<image_list> arrayimage_list = new ArrayList<PlayImageResult.PlayImageMess.image_list>();// 图片结果缓冲

	/********************** 网络返回参数列表 -----里程条 *********************************/
	private MilListResult millList;// 里程条结果返回类

	private station mileageListstation;// 桩号结果集

	// private String back_result_type;// 返回结果类型

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.play_image_layout);
		initview();

	}

	/**
	 * 
	 * 初始化控件
	 * 
	 */
	public void initview()

	{ // 获取启动该页面的意图
		srceenwidth = getResources().getDisplayMetrics().widthPixels;
		srceenheigh = getResources().getDisplayMetrics().heightPixels;
		intent = getIntent();
		// 获取该页面的参数
		Bundle extras = intent.getExtras();
		province_code = extras.getString("province_code");
		road_code = extras.getString("road_code");
		road_station = extras.getString("road_station");
		going_direction = extras.getString("going_direction");
		// bundle.putSerializable("list", millList);
		// millList = (MilListResult) extras.getSerializable("list");

		// if (extras.getBoolean("list")) {
		// millList = CustomApp.app.millList;
		//
		// }
		// ;
		// mileageListstation = millList.getResults();
		// 网络接口初始化
		connectionInteface = new ConnectionInteface(PlayImage.this);
		// 背景图片信息显示容器
		play_image_backgroud = (LinearLayout) findViewById(R.id.play_image_backgroud);
		// 屏幕常亮管理
		wakeLock = ((PowerManager) getSystemService(POWER_SERVICE))
				.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
		// 背景图片管理类
		vBackgroudimageIm = (PhotoView) findViewById(R.id.play_image_image);
		// vBackgroudimageIm.setZoomable(true);
		// vBackgroudimageIm.
		// 返回按钮
		vBackBtn = (ImageButton) findViewById(R.id.play_image_back);
		vBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String current_station = null;
				String new_current_station = null;
				try {
					current_station = arrayimage_list.get(mindex)
							.getRoad_large_img().getCurrent_station();
				} catch (Exception e) {
					// TODO: handle exception
				}

				// if (current_station != null) {
				// if (current_station.contains(".")) {
				// String[] temp = current_station.split("\\.");
				//
				// int temp2 = ((int) (Float.valueOf("0." + temp[1]) * 1000))
				// / ((int) (0.025 * 1000));
				// new_current_station = temp[1] + "." + 0.025 * temp2;
				//
				// }
				//
				// }
				if (current_station != null) {
					Intent intent = new Intent();
					intent.putExtra("station", current_station);
					CustomApp.app.bitmap = handlerBitmap(arrayimage_list
							.get(mindex).getRoad_large_img()
							.getRoad_large_img_data());
					setResult(257, intent);
				}

				PlayImage.this.finish();

			}
		});
		// 信息显示
		vinfomation = (ImageButton) findViewById(R.id.play_image_infomation);
		vinfomation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag) {
				} else {
					if (mSwitchDetail) {
						vImageDetailLL.setAlpha(0.0F);
						play_image_backgroud.setAlpha(0.0F);
						mSwitchDetail = false;
					} else {

						if (vSeekContainer.getVisibility() == View.VISIBLE) {

							closeAnimaltion();

						}
						mSwitchDetail = true;
						vImageDetailLL.setAlpha(0.8F);
						play_image_backgroud.setAlpha(0.8f);
						play_image_backgroud.setAlpha(0.8f);
						vImageDetailLL.setBackground(getResources()
								.getDrawable(R.drawable.road_img_info_alpha_));

						// play_image_backgroud.setAlpha(0.8f);vImageDetailLL.setBackground(getResources().getDrawable(R.drawable.road_img_info_alpha_));

					}
				}

			}
		});
		// 图片详情设置透明图
		vImageDetailLL = (LinearLayout) findViewById(R.id.play_image_detail);

		vImageDetailLL.setAlpha(0.0F);
		play_image_backgroud.setAlpha(0.0f);
		vImageDetailLL.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 播放的时候不显示设置
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (flag) {
					} else {
						if (mSwitchDetail) {
							vImageDetailLL.setAlpha(0.0F);
							play_image_backgroud.setAlpha(0.0F);
							mSwitchDetail = false;
						} else {

							if (vSeekContainer.getVisibility() == View.VISIBLE) {

								closeAnimaltion();

							}
							mSwitchDetail = true;
							vImageDetailLL.setAlpha(0.8F);
							play_image_backgroud.setAlpha(0.8f);
							vImageDetailLL.setBackground(getResources()
									.getDrawable(
											R.drawable.road_img_info_alpha_));

						}
					}

				}

				return true;
			}
		});
		// 前进 功能
		up = (ImageView) findViewById(R.id.play_up);
		down = (ImageView) findViewById(R.id.play_down);
		vImageUpLL = (LinearLayout) findViewById(R.id.play_image_up);
		up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mType = 0;
				isDown();
				System.out.println("mindex" + "==========" + mindex + "bitmaps"
						+ "==============" + arrayimage_list.size());

			}
		});
		// 播放设置监听
		vImagePlayLL = (LinearLayout) findViewById(R.id.play_image_play);
		// vImagePlayLL.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {

		// mType = 0;
		//
		// if (flag) {
		// flag = false;
		// vPlayOrPause.setImageResource(R.drawable.play_button);
		//
		// } else {
		// flag = true;
		// vImageDetailLL.setAlpha(0.0F);
		// play_image_backgroud.setAlpha(0.0f);
		// // 动画资源设置相应的图片
		// vPlayOrPause.setImageResource(R.drawable.pause_button);
		//
		// }
		// vPlayOrPause.setVisibility(View.VISIBLE);
		// vPlayOrPause.startAnimation(anim);
		// vPlayOrPause.setVisibility(View.INVISIBLE);
		//
		// play();
		//
		// }
		// });
		// 下一张设置监听
		vImageDownLL = (LinearLayout) findViewById(R.id.play_image_down);
		down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mType = 1;
				isDown();
				System.out.println("mindex" + "==========" + mindex + "bitmaps"
						+ "==============" + arrayimage_list.size());
			}

		});
		// seekbar 容器初始化
		vSeekContainer = (LinearLayout) findViewById(R.id.seekcontainer);
		// vSeekContainer.setAlpha(0.0f);
		// vSeekContainer.setOnLongClickListener(new OnLongClickListener() {
		//
		// @Override
		// public boolean onLongClick(View v) {
		//
		// if (vSeekContainer.getAlpha() == 0.0f) {
		// vBar.setVisibility(View.VISIBLE);
		// vSeekContainer.setAlpha(1.0F);
		// vthumbImgContainer.setVisibility(View.VISIBLE);
		//
		// } else {
		// vSeekContainer.setAlpha(0.0F);
		// vBar.setVisibility(View.INVISIBLE);
		// vthumbImgContainer.setVisibility(View.INVISIBLE);
		//
		// }
		// return false;
		// }
		// });

		// 进度条 设置
		vBar = (SeekBar) findViewById(R.id.seekbar);
		// vBar.setVisibility(View.INVISIBLE);
		vBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// 设置seekbar的和上方的小图的显示与隐藏
				if (vSeekContainer.getAlpha() == 0.0f) {
					// vthumbImgContainer.setVisibility(View.INVISIBLE);
				} else {
					// vthumbImgContainer.setVisibility(View.VISIBLE);
				}
				// 如果拖动进度条时是播放的状态 记下是播放状态，进度条下载完数据恢复播放
				if (flag) {
					flag = false;
					beforeBarIsPlay = true;
				}

				isbar = true;
				mType = 0;
				// road_station = mileageListstation.get(seekBar.getProgress())
				// .getRoad_station();
				mindex = 0;
				arrayimage_list.removeAll(arrayimage_list);
				station_index = seekBar.getProgress() + "";
				listindex = seekBar.getProgress();

				startTheard(mType);

				// }

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				// if (vSeekContainer.getAlpha() == 0.0f) {
				// vthumbImgContainer.setVisibility(View.INVISIBLE);
				// } else {
				// vthumbImgContainer.setVisibility(View.VISIBLE);
				// }
				// 拖动进度条小图相应的位置的设置 算2次到左边距的距离 从左边算一次 从右边算一次 ，两次结果想加 除以2 这样减小误差
				// params = (android.widget.RelativeLayout.LayoutParams)
				// vthumbImgContainer
				// .getLayoutParams();
				// float val = (((float) seekBar.getProgress() * (float)
				// (seekBar
				// .getWidth() - seekBar.getThumbOffset())) / seekBar
				// .getMax());
				// float offset = seekBar.getThumbOffset();
				// int textWidth = vthumbImgContainer.getWidth();
				// float textCenter = (textWidth / 2.0f);
				// float newX = val + offset - textCenter + seekBar.getLeft();
				// float val1 = (float) (seekBar.getMax() -
				// seekBar.getProgress())
				// * ((float) (seekBar.getWidth() - seekBar
				// .getThumbOffset()) / seekBar.getMax());
				// float newX2 = getResources().getDisplayMetrics().widthPixels
				// - (val1 + seekBar.getLeft() + 2 * seekBar
				// .getThumbOffset()) + seekBar.getThumbOffset()
				// - textCenter;
				// params.setMargins((int) ((newX + newX2) / 2), 0, 0, 0);
				// vthumbImgContainer.setLayoutParams(params);
				// // 如果用户拖动的变化执行设置隐藏，代码修改的设置为显示
				// if (fromUser) {
				// vSmallImg.setVisibility(View.GONE);
				// } else {
				// vSmallImg.setVisibility(View.VISIBLE);
				//
				// }
				// vSmallImgLocation.setText(mileageListstation.get(progress)
				// .getCurrent_location());
				// vSmallImgStation.setText(creatNewStation(mileageListstation
				// .get(progress).getRoad_station()));
				// newStation.setText(creatNewStation(mileageListstation.get(
				// progress).getRoad_station()));

			}
		});
		// 小图容器
		vthumbImgContainer = (LinearLayout) findViewById(R.id.thumb_img_container);
		// vthumbImgContainer.setVisibility(View.INVISIBLE);
		// 小图

		vSmallImg = (ImageView) findViewById(R.id.small_img);
		// 小图位置
		vSmallImgLocation = (TextView) findViewById(R.id.small_img_location);
		// 小图桩号
		vSmallImgStation = (TextView) findViewById(R.id.small_img_station);
		// 播放暂停时开启动画显示的控件
		vPlayOrPause = (ImageView) findViewById(R.id.playOrpause);
		vPlayOrPause.setVisibility(View.INVISIBLE);
		newStation = (TextView) findViewById(R.id.newstation);
		setNewStationWidth();
		// 线程管理类
		threadManage = new ThreadWithProgressDialog();
		anim = new AlphaAnimation(1.0F, 0.0F);
		anim.setDuration(2000);
		// 默认是前进
		mType = 0;
		close_seekbar = (ImageView) findViewById(R.id.close_seekbar);
		close_seekbar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				closeAnimaltion();

			}
		});
		visiable_seekbar = (ImageView) findViewById(R.id.visiable_seekbar);
		visiable_seekbar.setVisibility(View.INVISIBLE);
		visiable_seekbar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (play_image_backgroud.getAlpha() == 1.0) {
					vImageDetailLL.setAlpha(0.0F);
					play_image_backgroud.setAlpha(0.0F);
					mSwitchDetail = false;

				}
				openAnimaltion();

			}
		});
		// 开启线程下载数据
		startTheard(mType);

	}

	/**
	 * 设置文字的宽度
	 */
	private void setNewStationWidth() {
		int width = BitmapFactory.decodeResource(getResources(),
				R.drawable.progressbutton).getHeight() / 2;
		vBar.setPadding(width, 0, width, 0);
		newStation.setPadding(width, 0, 0, 0);

		width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		newStation.measure(width, height);
		width = newStation.getMeasuredWidth();

		newStation.getLayoutParams().width = width;

	}

	/**
	 * 
	 * 
	 * 方法是播放时候调用
	 */
	public void play() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				final Message message = new Message();
				// 暂停初始化
				message.what = 4;
				while (flag) {

					// 播放时的三种情景

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					mType = 0;
					isDown();
				}
				// 播放暂停后填充信息框数据
				mhandler.sendMessage(message);

			}
		}).start();
	}

	public void isDown() {
		// 有缓冲在可以播放
		final Message messagePlay = new Message();

		// 缓冲没有的时候 显示加载数据 转圈操作
		final Message messageShowDown = new Message();

		// 当缓冲小于一个预设值 开线程后台下载 并播放
		final Message messageUnshowDown = new Message();

		// 播放停止后把图片信息框填充数据

		// 播放
		messagePlay.what = 1;
		// 显示下载
		messageShowDown.what = 2;
		// 后台下载
		messageUnshowDown.what = 3;

		if (mindex < arrayimage_list.size() && mindex >= 0) {
			// 当缓冲用完时转圈（在该功能中转圈没有下载任何内容只是提供一个界面下载都是用后台没有显示来下载）
			if (0 == mType) {
				if (arrayimage_list.size() - 1 == mindex) {
					mhandler.sendMessage(messageShowDown);

				} else if (arrayimage_list.size() - mindex < 15
						&& arrayimage_list.size() - mindex > 0) {
					// 后台下载
					mhandler.sendMessage(messageUnshowDown);

				} else {
					// 播放功能
					mhandler.sendMessage(messagePlay);
				}

			} else if (1 == mType) {

				if (0 == mindex) {
					mhandler.sendMessage(messageShowDown);

				} else if (mindex < 15 && arrayimage_list.size() - mindex > 0) {
					mhandler.sendMessage(messageUnshowDown);
				} else {
					// 播放功能
					mhandler.sendMessage(messagePlay);
				}

			}

		}

	}

	/**
	 * 此方法是处理操数据返回结果的
	 * 
	 * @param playimage
	 *            参数大图接口结果的返回对象
	 */
	public void handlerResult(PlayImageResult playimage)

	{
		int correct = Utils.isCorrect(playimage, true);

		if (isFirst) {

			if (Integer.valueOf(station_index) >= 0 && correct == 0) {

				handleUpOrDownData();
				mileageListstation = millList.getResults();
				vBar.setMax(Integer.valueOf(mileageListstation
						.getStation_size()));
				vBar.setProgress(listindex);
				handlerData(mindex);
				isFirst = false;

			} else {
				CustomApp.app.customToast(Gravity.CENTER,
						CustomApp.SHOW_TOAST_TIMES, "初始化失败");
			}

		} else {
			if (beforemMove) {
				if (correct == 0) {
					handleUpOrDownData();

				} else {
					CustomApp.app.customToast(Gravity.CENTER,
							CustomApp.SHOW_TOAST_TIMES, "獲取圖片失敗");

				}

			} else {
				if (downNub == 1) {
					if (correct == 0) {
						handleUpOrDownData();

					}

				}

			}

		}

	}

	/***
	 * 把字符串转化成bitmap对象
	 * 
	 * @param bitmapstring
	 * @return
	 */
	public static Bitmap handlerBitmap(String bitmapstring) {
		if (bitmapstring == null) {

			return null;
		}
		byte[] temp = Base64.decode(bitmapstring, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(temp, 0, temp.length);

	}

	private void handleUpOrDownData() {
		// arrayimage_list = ;// 初始化返回数据集合
		// back_result_type = image_list.getResult_type(); // 返回数据类型

		// 判断当前播放是前进还是后退 当前进时 查看缓冲区大小是否超过预设值 没有超过 则添加到队列中 当超过替换一部分数据
		// 0代表前进 1代表后退

		if (playimage.getResults().get(0) != null
				&& playimage.getResults().get(0).getLarge_image_list() != null) {
			if (mType == 0) {

				if (arrayimage_list.size() >= 45) {

					for (int i = 0; i <= playimage.getResults().get(0)
							.getLarge_image_list().size(); i++) {
						arrayimage_list.remove(0);

					}
					mindex = mindex
							- playimage.getResults().get(0)
									.getLarge_image_list().size();
				}

				arrayimage_list.addAll(playimage.getResults().get(0)
						.getLarge_image_list());
			} else if (mType == 1) {

				if (arrayimage_list.size() >= 45) {
					for (int i = playimage.getResults().get(0)
							.getLarge_image_list().size() - 1; i >= 0; i--) {
						arrayimage_list.remove(arrayimage_list.size() - 1);

					}
				}
				mindex = mindex
						+ playimage.getResults().get(0).getLarge_image_list()
								.size();
				for (int i = 0; i < playimage.getResults().get(0)
						.getLarge_image_list().size(); i++) {
					arrayimage_list.add(0, playimage.getResults().get(0)
							.getLarge_image_list().get(i));

				}

			}
		} else {
			CustomApp.app.customToast(Gravity.CENTER,
					CustomApp.app.SHOW_TOAST_TIMES, "");
		}

	}

	/**
	 * 
	 * 
	 * 
	 * 此方法为处理图片侧边栏信息显示 处理道路数据
	 * 
	 * 
	 */
	public void initDataRoad(road road1) {

		TextView roadCurrentNameTxt;
		TextView roadCurrentPositionTxt;
		TextView roadCurrentStationTxt;
		TextView roadCurrentRoadtypeTxt;
		TextView roadCurrentRoadwidthTxt;
		TextView roadCurrentTrafficnumTxt;
		TextView roadCurrentBuildyearTxt;
		TextView roadCurrentConservationyearTxt;
		TextView roadCurrentSurfaceconditonTxt;
		TextView roadCurrentDatayearTxt;
		TextView roadCurrentConservationadviseTxt;

		roadCurrentNameTxt = (TextView) viewType
				.findViewById(R.id.road_current_name_txt);
		roadCurrentPositionTxt = (TextView) viewType
				.findViewById(R.id.road_current_position_txt);
		roadCurrentStationTxt = (TextView) viewType
				.findViewById(R.id.road_current_station_txt);
		roadCurrentRoadtypeTxt = (TextView) viewType
				.findViewById(R.id.road_current_roadtype_txt);
		roadCurrentRoadwidthTxt = (TextView) viewType
				.findViewById(R.id.road_current_roadwidth_txt);
		roadCurrentTrafficnumTxt = (TextView) viewType
				.findViewById(R.id.road_current_trafficnum_txt);
		roadCurrentBuildyearTxt = (TextView) viewType
				.findViewById(R.id.road_current_buildyear_txt);
		roadCurrentConservationyearTxt = (TextView) viewType
				.findViewById(R.id.road_current_conservationyear_txt);
		roadCurrentSurfaceconditonTxt = (TextView) viewType
				.findViewById(R.id.road_current_surfaceconditon_txt);
		roadCurrentDatayearTxt = (TextView) viewType
				.findViewById(R.id.road_current_datayear_txt);
		roadCurrentConservationadviseTxt = (TextView) viewType
				.findViewById(R.id.road_current_conservationadvise_txt);
		roadCurrentNameTxt.setVisibility(View.GONE);
		roadCurrentPositionTxt.setText(road1.getCurrent_location());
		roadCurrentStationTxt.setText(creatNewStation(road1
				.getCurrent_station()));
		roadCurrentRoadtypeTxt.setText(road1.getRoad_face_type());
		roadCurrentRoadwidthTxt.setText(road1.getRoad_way_width());
		roadCurrentTrafficnumTxt.setText(road1.getDaily_traffic_volume());
		roadCurrentBuildyearTxt.setText(road1.getRoad_build_years());
		roadCurrentConservationyearTxt.setText(road1
				.getRoad_conservation_years());
		if (road1.getRoad_surface_condition().contains(",")) {
			String temp = "";

			String[] split = road1.getRoad_surface_condition().split(",");
			for (int i = 0; i < split.length; i++) {
				if (i == split.length - 1) {
					temp += split[i];
				} else {
					temp += split[i] + "\n";
				}

			}

			roadCurrentSurfaceconditonTxt.setText(temp);
		} else {
			roadCurrentSurfaceconditonTxt.setText(road1
					.getRoad_surface_condition());
		}

		roadCurrentDatayearTxt.setText(road1.getRoad_img_year());

		// TextView playRoadLocationRoute; // 所在路线
		// TextView playRoadCurrentStation;// 当前桩号
		// TextView playRoadCurrentLocation;// 当前位于
		// TextView playRoadCurrentLand;// 当前地貌
		// TextView playRoadSideType;// 边坡类型
		// TextView playRoadTechnicalGrade;// 技术等级
		// TextView playRoadWayWidth;// 路面全宽
		// TextView road_lane_num;// 车道数
		// TextView playRoadManageUnit;// 管养单位
		// playRoadLocationRoute = (TextView) viewType
		// .findViewById(R.id.play_road_location_route);
		// playRoadCurrentStation = (TextView) viewType
		// .findViewById(R.id.play_road_current_station);
		// playRoadCurrentLocation = (TextView) viewType
		// .findViewById(R.id.play_road_current_location);
		// playRoadCurrentLand = (TextView) viewType
		// .findViewById(R.id.play_road_current_land);
		// playRoadSideType = (TextView) viewType
		// .findViewById(R.id.play_road_side_type);
		// playRoadTechnicalGrade = (TextView) viewType
		// .findViewById(R.id.play_road_technical_grade);
		// playRoadWayWidth = (TextView) viewType
		// .findViewById(R.id.play_road_way_width);
		// road_lane_num = (TextView) viewType.findViewById(R.id.road_lane_num);
		// playRoadManageUnit = (TextView) viewType
		// .findViewById(R.id.play_road_manage_unit);
		// playRoadCurrentStation.setText(creatNewStation(road1
		// .getCurrent_station()));
		// playRoadCurrentLocation.setText(road1.getCurrent_location());
		// playRoadCurrentLand.setText(road1.getRoad_current_land());
		// playRoadSideType.setText(road1.getRoad_side_type());
		// playRoadLocationRoute.setText(getLocationRoute(road1
		// .getRoad_location_route()));
		// playRoadTechnicalGrade.setText(road1.getRoad_technical_grade());
		// playRoadWayWidth.setText(road1.getRoad_way_width());
		// road_lane_num.setText(road1.getRoad_lane_num());
		// playRoadManageUnit.setText(road1.getRoad_manage_unit());

	}

	/**
	 * 
	 * 
	 * 线程联网类
	 * 
	 * @author xushiwei
	 * 
	 */
	class DialogTask implements ThreadWithProgressDialogTask {

		@Override
		public boolean TaskMain() {
			// 判断前进后退确定联网参数
			if (mType == 0) {
				forward_or_back = "FOR";
				if (arrayimage_list.size() == 0) {

				} else {
					station_index = listindex + arrayimage_list.size() - mindex
							+ "";
				}
			} else {
				forward_or_back = "BACK";
				station_index = listindex - mindex - 1 + "";
			}
			if (isFirst) {
				millList = connectionInteface.MillList2(CustomApp.app.token,
						province_code, road_code, road_station);
				page_size = 5;
				int correct = Utils.isCorrect(millList, false);
				if (correct == 0) {
					listindex = Integer.valueOf(millList.getResults()
							.getIndex());
					if (listindex >= 0) {
						station_index = listindex + "";
						playimage = connectionInteface.Playimage(province_code,
								token, road_code, going_direction,
								forward_or_back, road_station, page_size + "",
								page + "", is_station, station_index);
					} else {
						station_index = 0 + "";
						listindex=0;
						playimage = connectionInteface.Playimage(province_code,
								token, road_code, going_direction,
								forward_or_back, road_station, page_size + "",
								page + "", is_station, station_index);
					}
				}
				return true;
			}
			while (downNub == 1) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			}
			if (downNub != 1) {
				// 拖动进度条前面记录是不是播放
				if (isbar) {
					if (beforeBarIsPlay) {

						arrayimage_list.removeAll(arrayimage_list);
					}
					// playimage = connectionInteface.Playimage(province_code,
					// CustomApp.app.token, road_code, going_direction,
					// forward_or_back, road_station, page_size + "", page
					// + "");

				}
			}
			if (beforemMove) {
				playimage = connectionInteface.Playimage(province_code, token,
						road_code, going_direction, forward_or_back,
						road_station, page_size + "", page + "", is_station,
						station_index);
			} else {
				Log.v("malog", "第三次");
				if (mType == 0 && (arrayimage_list.size() - 1) == mindex) {
					playimage = connectionInteface.Playimage(province_code,
							token, road_code, going_direction, forward_or_back,
							road_station, page_size + "", page + "",
							is_station, station_index);
				} else if (mType == 1 && mindex == 0) {
					Log.v("malog", "第四次");
					playimage = connectionInteface.Playimage(province_code,
							token, road_code, going_direction, forward_or_back,
							road_station, page_size + "", page + "",
							is_station, station_index);
				}

			}
			//

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			if (isFirst) {
				millList = null;

			}
			playimage = null;
			showdown = 0;
			return false;
		}

		@Override
		public boolean OnTaskDone() {

			handlerResult(playimage);
			playimage = null;
			// 如果是拖动seekbar联网结束后执行的操作
			if (isbar) {

				if (arrayimage_list.size() > 0) {
					mindex = 0;
					vSmallImg.setVisibility(View.VISIBLE);
					handlerData(mindex);
					// 拖动前如果是播放拖动后恢复播放
					if (beforeBarIsPlay) {
						flag = true;
						play();
						beforeBarIsPlay = false;
					}
				} else {
					CustomApp.app.customToast(Gravity.CENTER,
							CustomApp.SHOW_TOAST_TIMES, "获取图片失败");
				}

				isbar = false;
			}
			showdown = 0;
			// 转圈前如果是播放 转圈后继续播放
			if (prevIsPlay) {

				prevIsPlay = false;
				flag = true;
				play();

			}
			beforemMove = true;
			return false;

		}

	}

	/**
	 * 
	 * 根据脚标获取桩号
	 * 
	 * @param index
	 * @return 返回-1代表没有这种类型的否则返回桩号
	 */

	public int getStation(int index) {
		int temp = -1;
		if (arrayimage_list.get(index).getResult_type().equals("TYPE_ROAD")) {

			// for (int i = 0; i < mileageListstation.size(); i++) {
			// if (mileageListstation
			// .get(i)
			// .getRoad_station()
			// .equals(arrayimage_list.get(index).getRoad_large_img()
			// .getCurrent_station())) {
			// temp = i;
			// break;
			//
			// }
			//
			// }

		} else if (arrayimage_list.get(index).getResult_type()
				.equals("TYPE_BRIDG")) {
			// for (int i = 0; i < mileageListstation.size(); i++) {
			// if (mileageListstation
			// .get(i)
			// .getRoad_station()
			// .equals(arrayimage_list.get(index)
			// .getBridge_large_img().getCurrent_station())) {
			// temp = i;
			// break;
			//
			// }
			//
			// }

		} else if (arrayimage_list.get(index).getResult_type()
				.equals("TYPE_TUNNEL")) {
			// for (int i = 0; i < mileageListstation.size(); i++) {
			// if (mileageListstation
			// .get(i)
			// .getRoad_station()
			// .equals(arrayimage_list.get(index)
			// .getTunnel_large_img().getCurrent_station())) {
			// temp = i;
			// break;
			//
			// }
			//
			// }

		} else if (arrayimage_list.get(index).getResult_type()
				.equals("TYPE_FACILITY")) {
			// for (int i = 0; i < mileageListstation.size(); i++) {
			// if (mileageListstation
			// .get(i)
			// .getRoad_station()
			// .equals(arrayimage_list.get(index)
			// .getFacility_large_img().getCurrent_station())) {
			// temp = i;
			// break;
			//
			// }
			//
			// }

		} else if (arrayimage_list.get(index).getResult_type()
				.equals("TYPE_CONSTRUCTION")) {
			// for (int i = 0; i < mileageListstation.size(); i++) {
			// if (mileageListstation
			// .get(i)
			// .getRoad_station()
			// .equals(arrayimage_list.get(index)
			// .getConstruction_large_img()
			// .getCurrent_station())) {
			// temp = i;
			// break;
			//
			// }
			//
			// }

		}
		return temp;

	}

	/**
	 * 
	 * 
	 * 此类为后台下载类不会转圈
	 * 
	 * @author xushiwei
	 * 
	 */
	class Myasyctask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params)

		{
			// 前进后退获取相应的桩号联网每次联网都是获取第一个（后退）或者最后（前进）一个缓冲的桩号
			if (mType == 0) {
				forward_or_back = "FOR";
				if (arrayimage_list.size() == 0) {

				} else {
					station_index = listindex + arrayimage_list.size() - mindex
							+ "";
				}
			} else {
				forward_or_back = "BACK";
				station_index = listindex - mindex - 1 + "";
			}

			playimage = connectionInteface.Playimage(province_code, token,
					road_code, going_direction, forward_or_back, road_station,
					page_size + "", page + "", is_station, station_index);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			// 处理联网后的结果
			handlerResult(playimage);
			// 更改联网后的后台下载数量
			downNub = 0;
			// 清空联网后的结果
			playimage = null;

		}
	}

	/****
	 * 
	 * 根据桩号生成新的桩号 格式如输入1782.1546645878输出 1782+154
	 * 
	 * @param station
	 *            输入桩号
	 * @return 输出桩号
	 */

	public static String creatNewStation(String station) {
		if (station.contains(".")) {

			String[] temp = station.split("\\.");
			if (temp[1].length() >= 3) {
				return temp[0] + "+" + temp[1].substring(0, 3);
			} else {

				return temp[0] + "+" + temp[1];

			}

		}

		return null;

	}

	/**
	 * 
	 * 
	 * 
	 * 信息的填写
	 * 
	 * @param index
	 *            指标序列 arraylist数组的指标
	 * 
	 */
	public void handlerData(int index) {
		vImageDetailLL.removeAllViews();

		if (arrayimage_list.get(index).getResult_type().equals("TYPE_ROAD")) {
			// 获取index位置的道路信息
			road road1 = arrayimage_list.get(index).getRoad_large_img();
			// 设置背景图片
			vBackgroudimageIm.setImageBitmap(handlerBitmap(road1
					.getRoad_large_img_data()));

			// 信息框类型过滤
			// viewType = getLayoutInflater().inflate(
			// R.layout.query_road_detail_layout, null);
			viewType = getLayoutInflater().inflate(
					R.layout.road_current_info_layout, null);
			initDataRoad(road1);
			// 设置进度条的位置 不成功返回-1

			int temp = setSeekbar();
			// 重置temp设置进度为0

			if (temp == -1) {

				Toast.makeText(PlayImage.this, "设置进度失败", Toast.LENGTH_SHORT)
						.show();
			} else {
				// 设置小图图片与桩号
				vSmallImg.setImageBitmap(handlerBitmap(road1
						.getRoad_thumb_img_data()));
				vSmallImgStation.setText(creatNewStation(road1
						.getCurrent_station()));
				newStation.setText(creatNewStation(road1.getCurrent_station()));
				// vSmallImgLocation.setText(mileageListstation.get(temp)
				// .getCurrent_location());
			}
			;

		} else if (arrayimage_list.get(index).getResult_type()
				.equals("TYPE_BRIDG")) {
			// 获取index位置的桥梁信息
			bridge bridge1 = arrayimage_list.get(index).getBridge_large_img();
			// 设置背景图片
			vBackgroudimageIm.setImageBitmap(handlerBitmap(bridge1
					.getBridge_large_img_data()));
			// 信息框类型过滤
			viewType = getLayoutInflater().inflate(R.layout.play_bridge_detail,
					null);
			initDataBridge(bridge1);
			// 设置进度条的位置 不成功返回-1
			int temp = setSeekbar();

			if (temp == -1) {

				Toast.makeText(PlayImage.this, "设置进度失败", Toast.LENGTH_SHORT)
						.show();
			} else {
				// 设置小图图片与桩号
				vSmallImg.setImageBitmap(handlerBitmap(bridge1
						.getBridge_thumb_img_data()));
				vSmallImgStation.setText(creatNewStation(bridge1
						.getCurrent_station()));
				newStation
						.setText(creatNewStation(bridge1.getCurrent_station()));
				// vSmallImgLocation.setText(mileageListstation.get(temp)
				// .getCurrent_location());

			}
			;
			// 逻辑同上
		} else if (arrayimage_list.get(index).getResult_type()
				.equals("TYPE_TUNNEL")) {

			tunnel tunnel1 = arrayimage_list.get(index).getTunnel_large_img();
			vBackgroudimageIm.setImageBitmap(handlerBitmap(tunnel1
					.getTunnel_large_img_data()));
			viewType = getLayoutInflater().inflate(R.layout.play_tunnel_detail,
					null);
			initDataTunnel(tunnel1);
			int temp = setSeekbar();
			// 重置temp设置进度为0

			if (temp == -1) {

				Toast.makeText(PlayImage.this, "设置进度失败", Toast.LENGTH_SHORT)
						.show();
			} else {
				vSmallImg.setImageBitmap(handlerBitmap(tunnel1
						.getTunnel_thumb_img_data()));
				vSmallImgStation.setText(creatNewStation(tunnel1
						.getCurrent_station()));
				newStation
						.setText(creatNewStation(tunnel1.getCurrent_station()));
				// vSmallImgLocation.setText(mileageListstation.get(temp)
				// .getCurrent_location());

			}
			;
			// 逻辑同上
		} else if (arrayimage_list.get(index).getResult_type()
				.equals("TYPE_FACILITY")) {

			facility facility1 = arrayimage_list.get(index)
					.getFacility_large_img();
			vBackgroudimageIm.setImageBitmap(handlerBitmap(facility1
					.getFacility_large_img_data()));
			viewType = getLayoutInflater().inflate(
					R.layout.play_facility_detail, null);
			initDataFacility(facility1);
			int temp = setSeekbar();
			// 重置temp设置进度为0

			if (temp == -1) {

				Toast.makeText(PlayImage.this, "设置进度失败", Toast.LENGTH_SHORT)
						.show();
			} else {

				vSmallImg.setImageBitmap(handlerBitmap(facility1
						.getFacility_thumb_img_data()));
				vSmallImgStation.setText(creatNewStation(facility1
						.getCurrent_station()));
				newStation.setText(creatNewStation(facility1
						.getCurrent_station()));
				// vSmallImgLocation.setText(mileageListstation.get(temp)
				// .getCurrent_location());
			}

			;
			// 逻辑同上
		} else if (arrayimage_list.get(index).getResult_type()
				.equals("TYPE_CONSTRUCTION")) {

			construction construction1 = arrayimage_list.get(index)
					.getConstruction_large_img();
			vBackgroudimageIm.setImageBitmap(handlerBitmap(construction1
					.getConstruction_large_img_data()));
			viewType = getLayoutInflater().inflate(
					R.layout.play_facility_detail, null);
			initDataConstruction(construction1);
			int temp = setSeekbar();
			// 重置temp设置进度为0

			if (temp == -1) {

				Toast.makeText(PlayImage.this, "设置进度失败", Toast.LENGTH_SHORT)
						.show();
			} else {

				vSmallImg.setImageBitmap(handlerBitmap(construction1
						.getConstruction_thumb_img_data()));
				vSmallImgStation.setText(creatNewStation(construction1
						.getCurrent_station()));
				newStation.setText(creatNewStation(construction1
						.getCurrent_station()));
				// vSmallImgLocation.setText(mileageListstation.get(temp)
				// .getCurrent_location());
			}

		}
		vImageDetailLL.addView(viewType);

	}

	/**
	 * 
	 * 处理建筑的信息的方法
	 * 
	 * @param construction1
	 */
	private void initDataConstruction(construction construction1) {

		TextView playConstructionLocationRoute;// 所在路线
		TextView playConstructionCurrentStation;// 当前桩号
		TextView playConstructionCurrentLocation;// 当前位于
		TextView playConstructionName;// 工程名称
		TextView playConstructionInvestmentMoney;// 投资金额
		TextView playConstructionCompProgress;// 完成进度
		TextView playConstructionManageUnit;// 管养单位
		TextView playConstructionConstructUnit;// 施工单位
		playConstructionLocationRoute = (TextView) findViewById(R.id.play_construction_location_route);
		playConstructionCurrentStation = (TextView) findViewById(R.id.play_construction_current_station);
		playConstructionCurrentLocation = (TextView) findViewById(R.id.play_construction_current_location);
		playConstructionName = (TextView) findViewById(R.id.play_construction_name);
		playConstructionInvestmentMoney = (TextView) findViewById(R.id.play_construction_investment_money);
		playConstructionCompProgress = (TextView) findViewById(R.id.play_construction_comp_progress);
		playConstructionManageUnit = (TextView) findViewById(R.id.play_construction_manage_unit);
		playConstructionConstructUnit = (TextView) findViewById(R.id.play_construction_construct_unit);
		playConstructionLocationRoute.setText(getLocationRoute(construction1
				.getConstruction_location_route()));
		playConstructionCurrentStation.setText(creatNewStation(construction1
				.getCurrent_station()));
		playConstructionCurrentLocation.setText(construction1
				.getCurrent_location());
		playConstructionName.setText(construction1.getConstruction_name());
		playConstructionInvestmentMoney.setText(construction1
				.getConstruction_investment_money());
		playConstructionCompProgress.setText(construction1
				.getConstruction_comp_progress());
		playConstructionManageUnit.setText(construction1
				.getConstruction_manage_unit());
		playConstructionConstructUnit.setText(construction1
				.getConstruction_construct_unit());

	}

	/**
	 * 处理设施的方法
	 * 
	 * @param facility1
	 */
	private void initDataFacility(facility facility1) {

		TextView playFacilityLocationRoute;// 所在路线
		TextView playFacilityCurrentStation;// 当前桩号
		TextView playFacilityCurrentLocation;// 当前位于
		TextView playFacilityId;// 设施id
		TextView playFacilityName;// 设施名称
		TextView playFacilityCode;// 设施编码
		TextView playFacilityLongitude;// 经度
		TextView playFacilityLatitude;// 纬度
		TextView playFacilityCenterStation;// 中心桩号

		playFacilityLocationRoute = (TextView) viewType
				.findViewById(R.id.play_facility_location_route);
		playFacilityCurrentStation = (TextView) viewType
				.findViewById(R.id.play_facility__current_station);
		playFacilityCurrentLocation = (TextView) viewType
				.findViewById(R.id.play_facility__current_location);
		playFacilityId = (TextView) viewType
				.findViewById(R.id.play_facility__id);
		playFacilityName = (TextView) viewType
				.findViewById(R.id.play_facility_name);
		playFacilityCode = (TextView) viewType
				.findViewById(R.id.play_facility_code);
		playFacilityLongitude = (TextView) viewType
				.findViewById(R.id.play_facility_longitude);
		playFacilityLatitude = (TextView) viewType
				.findViewById(R.id.play_facility_latitude);
		playFacilityCenterStation = (TextView) viewType
				.findViewById(R.id.play_facility_center_station);
		playFacilityLocationRoute.setText(getLocationRoute(facility1
				.getFacility_location_route()));
		playFacilityCurrentStation.setText(creatNewStation(facility1
				.getCurrent_station()));
		playFacilityCurrentLocation.setText(facility1.getCurrent_location());
		playFacilityId.setText(facility1.getFacility_id());
		playFacilityName.setText(facility1.getFacility_name());
		playFacilityCode.setText(facility1.getFacility_code());
		playFacilityLongitude.setText(facility1.getLongitude());
		playFacilityLatitude.setText(facility1.getLatitude());
		playFacilityCenterStation.setText(facility1
				.getFacility_center_station());

	}

	/**
	 * 
	 * 处理隧道信息方法
	 * 
	 * @param tunnel1
	 */
	private void initDataTunnel(tunnel tunnel1) {

		TextView playTunnelLocationRoute;// 所在路线
		TextView playTunnelCurrentStation;// 当前桩号
		TextView playTunnelCurrentLocation;// 当前位于
		TextView playTunnelName;// 隧道名称
		TextView playTunnelCode;// 隧道编码
		TextView playTunnelLength;// 隧道长度
		TextView playTunnelHeight;// 隧道净高
		TextView playTunnelEvaluateGrade;// 评定等级

		playTunnelLocationRoute = (TextView) viewType
				.findViewById(R.id.play_tunnel_location_route);
		playTunnelCurrentStation = (TextView) viewType
				.findViewById(R.id.play_tunnel_current_station);
		playTunnelCurrentLocation = (TextView) viewType
				.findViewById(R.id.play_tunnel_current_location);
		playTunnelName = (TextView) viewType
				.findViewById(R.id.play_tunnel_name);
		playTunnelCode = (TextView) viewType
				.findViewById(R.id.play_tunnel_code);
		playTunnelLength = (TextView) viewType
				.findViewById(R.id.play_tunnel_length);
		playTunnelHeight = (TextView) viewType
				.findViewById(R.id.play_tunnel_height);
		playTunnelEvaluateGrade = (TextView) viewType
				.findViewById(R.id.play_tunnel_evaluate_grade);
		playTunnelLocationRoute.setText(getLocationRoute(tunnel1
				.getTunnel_location_route()));
		playTunnelCurrentStation.setText(creatNewStation(tunnel1
				.getTunnel_center_station()));
		playTunnelCurrentLocation.setText(tunnel1.getCurrent_station());
		playTunnelName.setText(tunnel1.getTunnel_name());
		playTunnelCode.setText(tunnel1.getTunnel_code());
		playTunnelLength.setText(tunnel1.getTunnel_length());
		playTunnelHeight.setText(tunnel1.getTunnel_height());
		playTunnelEvaluateGrade.setText(tunnel1.getTunnel_evaluate_grade());

	}

	/**
	 * 
	 * 
	 * 给左侧信息栏 桥梁设置信息
	 * 
	 * @param bridge1
	 */
	public void initDataBridge(bridge bridge1) {

		TextView playBridgeLocationRoute;// 所在路线
		TextView playBridgeCurrentStation;// 当前桩号
		TextView playBridgeCurrentLocation;// 当前位于
		TextView playBridgeName;// 桥梁名称
		TextView playBridgeCenterStation;// 桥梁中心桩号
		TextView playBridgeLength;// 桥梁全长
		TextView playBridgeSpanType;// 跨径类型
		TextView playBridgeTechnicalGrade;// 技术状况
		TextView playBridgeDesignUnit;// 设计单位
		TextView playBridgeConstructUnit;// 施工单位
		TextView playBridgeManageUnit;// 监理单位
		TextView playBridgeFixYear;// 修建年度
		ImageButton play_bridge_is_ngtc;// 是否国检

		playBridgeLocationRoute = (TextView) viewType
				.findViewById(R.id.play_bridge_location_route);
		playBridgeCurrentStation = (TextView) viewType
				.findViewById(R.id.play_bridge_current_station);
		playBridgeCurrentLocation = (TextView) viewType
				.findViewById(R.id.play_bridge_current_location);
		playBridgeName = (TextView) viewType
				.findViewById(R.id.play_bridge_name);
		playBridgeCenterStation = (TextView) viewType
				.findViewById(R.id.play_bridge_center_station);
		playBridgeLength = (TextView) viewType
				.findViewById(R.id.play_bridge_length);
		playBridgeSpanType = (TextView) viewType
				.findViewById(R.id.play_bridge_span_type);
		playBridgeTechnicalGrade = (TextView) viewType
				.findViewById(R.id.play_bridge_technical_grade);
		playBridgeDesignUnit = (TextView) viewType
				.findViewById(R.id.play_bridge_design_unit);
		playBridgeConstructUnit = (TextView) viewType
				.findViewById(R.id.play_bridge_construct_unit);
		playBridgeManageUnit = (TextView) viewType
				.findViewById(R.id.play_bridge_manage_unit);
		playBridgeFixYear = (TextView) viewType
				.findViewById(R.id.play_bridge_fix_year);
		play_bridge_is_ngtc = (ImageButton) viewType
				.findViewById(R.id.play_bridge_is_ngtc);
		playBridgeLocationRoute.setText(getLocationRoute(bridge1
				.getBridge_location_route()));
		playBridgeCurrentStation.setText(bridge1.getCurrent_station());
		playBridgeCurrentLocation.setText(bridge1.getCurrent_location());
		playBridgeName.setText(bridge1.getBridge_name());
		playBridgeCenterStation.setText(creatNewStation(bridge1
				.getCurrent_station()));
		playBridgeLength.setText(bridge1.getBridge_length());
		playBridgeSpanType.setText(bridge1.getBridge_span_type());
		playBridgeTechnicalGrade.setText(bridge1.getBridge_technical_grade());
		playBridgeDesignUnit.setText(bridge1.getBridge_design_unit());
		playBridgeConstructUnit.setText(bridge1.getBridge_construct_unit());
		playBridgeManageUnit.setText(bridge1.getBridge_manage_unit());
		playBridgeFixYear.setText(bridge1.getBridge_fix_year());
		if (bridge1.getBridge_is_ngtc().endsWith("Y")) {

			play_bridge_is_ngtc.setVisibility(View.VISIBLE);
			play_bridge_is_ngtc.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent();
					Toast.makeText(PlayImage.this, "国检功能还在研发中。。。",
							Toast.LENGTH_SHORT).show();

				}
			});
		} else {
			play_bridge_is_ngtc.setVisibility(View.INVISIBLE);

		}

	}

	/**
	 * 
	 * 
	 * 
	 * @param station
	 *            传入桩号设置
	 * @return 返回当前seek的进度 如为-1为没有该桩号
	 */

	public int setSeekbar() {
		// for (int i = 0; i < mileageListstation.size(); i++) {
		// if (mileageListstation.get(i).getRoad_station().equals(station))
		// {

		vBar.setProgress(listindex);
		//
		// return i;
		//
		// }
		return listindex;

		// }

		// return -1;

	}

	/***
	 * 
	 * 
	 * 
	 * 根据线路返回相应的道路属性 比如G75 返回国道G75
	 * 
	 * @param location_route
	 *            G75
	 * @return
	 */
	public String getLocationRoute(String location_route) {
		if (location_route.contains("G")) {

			return "国道" + location_route;
		} else if (location_route.contains("S")) {
			return "省道" + location_route;
		} else if (location_route.contains("X")) {
			return "县道" + location_route;
		} else if (location_route.contains("Y")) {
			return "乡道" + location_route;
		} else if (location_route.contains("C")) {
			return "村道" + location_route;
		} else if (location_route.contains("Z")) {
			return "专用公路" + location_route;
		}

		return location_route;
	}

	/**
	 * 
	 * 
	 * 开启线程
	 * 
	 * @param type
	 *            type不同开启不同的线程
	 */

	private void startTheard(int type) {
		if (Utils.detect(this)) {
			threadManage.Run(this, new DialogTask(), R.string.com_loading);
		} else {
			CustomApp.app.customToast(Gravity.CENTER,
					CustomApp.SHOW_TOAST_TIMES, R.string.com_network_wrong);
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
		// 显示的时候屏幕常亮
		wakeLock.acquire();
	}

	@Override
	protected void onPause() {

		super.onPause();
		if (wakeLock != null) {
			// 屏幕常亮关闭
			wakeLock.release();
		}
	}

	public void closeAnimaltion() {

		Animation animation = new TranslateAnimation(0, srceenwidth, 0, 0);
		animation.setDuration(1000);
		animation.setFillAfter(true);

		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				close_seekbar.setClickable(false);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				visiable_seekbar.setVisibility(View.VISIBLE);
				vSeekContainer.setVisibility(View.INVISIBLE);
				// vthumbImgContainer.setVisibility(View.INVISIBLE);

			}
		});
		// vthumbImgContainer.startAnimation(animation);
		vSeekContainer.startAnimation(animation);

	}

	public void openAnimaltion() {

		Animation animation = new TranslateAnimation(srceenwidth, 0, 0, 0);
		animation.setDuration(1000);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

				// TODO Auto-generated method stub
				visiable_seekbar.setVisibility(View.INVISIBLE);
				vSeekContainer.setVisibility(View.VISIBLE);
				// vthumbImgContainer.setVisibility(View.VISIBLE);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				close_seekbar.setClickable(true);
			}
		});
		// vthumbImgContainer.startAnimation(animation);
		vSeekContainer.startAnimation(animation);

	}

}
