package com.mobo.zggkgisandroid.More;

import java.util.ArrayList;
import java.util.HashMap;

import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.WebModel.Text;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 更多弹出框
 * 
 * @author rujiafentoulvyouyou
 *
 */
public class MorePopupWindow {
	private Activity activity;
	private View mView;
	private PopupWindow popMenu;
	private LinearLayout llCxfw, llZyyy;// 出行服务，专业应用
	private LinearLayout llCxfwGroup;// 出行服务布局
	private ListView listView;// 专业应用列表
	private int height;// 窗口高度

	private View vZjxx, vBgyd, vYydh, vLxdt;// 自驾选线，宾馆预订，语音导航，离线地图

	private ArrayList<MoreEnter> enters = new ArrayList<MorePopupWindow.MoreEnter>();
	private HashMap<Integer, ArrayList<MoreEnter>> hashMap = new HashMap<Integer, ArrayList<MoreEnter>>();
	private int index = -1;// 专业应用的数据下标

	public MorePopupWindow(Activity activity, View mView) {
		this.mView = mView;
		this.activity = activity;
		View popView = LayoutInflater.from(activity).inflate(R.layout.more_popup_layout, null);
		getHeight(popView);
		initView(popView);
		init();
		listView.setAdapter(new ListAdapte(enters));
		popMenu = new PopupWindow(popView, LayoutParams.MATCH_PARENT, height);
		popMenu.setAnimationStyle(R.style.mypopwindow_anim_style);
		popMenu.setFocusable(false);
		popView.setFocusableInTouchMode(false);
		popMenu.setOutsideTouchable(true);
		popMenu.setBackgroundDrawable(new ColorDrawable(0x00000000));
		popMenu.setTouchable(true);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (index == -1) {
					index = arg2;
					listView.setAdapter(new ListAdapte(hashMap.get(index)));
				}
			}
		});

	}

	public void setListView() {
		index = -1;
		listView.setAdapter(new ListAdapte(enters));
	}

	public boolean isDismiss() {
		return listView.getVisibility() == View.VISIBLE && index != -1;
	}

	public void dismiss() {
		popMenu.dismiss();
	}

	public boolean isShowing() {
		return popMenu.isShowing();
	}

	private void getHeight(View popView) {
		popView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		height = popView.getMeasuredHeight();

	}

	private void init() {
		String title[] = { "路况检测", "路况评定", "养护决策", "工程管理" };
		Integer icon[] = { R.drawable.more_lkjc_icon, R.drawable.more_lkpd_icon, R.drawable.more_lmgl_icon,
				R.drawable.more_rcyh_icon };
		for (int i = 0; i < icon.length; i++) {
			MoreEnter enter = new MoreEnter();
			enter.setIcon(icon[i]);
			enter.setMain(true);
			enter.setTitle(title[i]);
			enters.add(enter);
		}
		title = new String[] { "日常巡查", "路况检测" };
		icon = new Integer[] { R.drawable.more_rcxc_icon, R.drawable.more_lkjc_icon };
		ArrayList<MoreEnter> moreEnters = new ArrayList<MorePopupWindow.MoreEnter>();
		hashMap.put(0, moreEnters);
		for (int i = 0; i < icon.length; i++) {
			MoreEnter enter = new MoreEnter();
			enter.setIcon(icon[i]);
			enter.setMain(false);
			enter.setTitle(title[i]);
			moreEnters.add(enter);
		}
		title = new String[] { "路况评定", "桥梁评定", "隧道评定", "安全评价" };
		icon = new Integer[] { R.drawable.more_lkpd_icon, R.drawable.more_qlpd_icon, R.drawable.more_sdpd_icon,
				R.drawable.more_aqpj_icon };
		moreEnters = new ArrayList<MorePopupWindow.MoreEnter>();
		hashMap.put(1, moreEnters);
		for (int i = 0; i < icon.length; i++) {
			MoreEnter enter = new MoreEnter();
			enter.setIcon(icon[i]);
			enter.setMain(false);
			enter.setTitle(title[i]);
			moreEnters.add(enter);
		}
		title = new String[] { "路面管理", "桥梁管理", "隧道管理", "设施管理", "路政管理" };
		icon = new Integer[] { R.drawable.more_lmgl_icon, R.drawable.more_qlgl_icon, R.drawable.more_sdgl_icon,
				R.drawable.more_ssgl_icon, R.drawable.more_lzgl_icon };
		moreEnters = new ArrayList<MorePopupWindow.MoreEnter>();
		hashMap.put(2, moreEnters);
		for (int i = 0; i < icon.length; i++) {
			MoreEnter enter = new MoreEnter();
			enter.setIcon(icon[i]);
			enter.setMain(false);
			enter.setTitle(title[i]);
			moreEnters.add(enter);
		}
		title = new String[] { "养护计划", "养护工程", "日常养护", "应急管理" };
		icon = new Integer[] { R.drawable.more_yhjh_icon, R.drawable.more_yhgc_icon, R.drawable.more_rcyh_icon,
				R.drawable.more_yjgl_icon };
		moreEnters = new ArrayList<MorePopupWindow.MoreEnter>();
		hashMap.put(3, moreEnters);
		for (int i = 0; i < icon.length; i++) {
			MoreEnter enter = new MoreEnter();
			enter.setIcon(icon[i]);
			enter.setMain(false);
			enter.setTitle(title[i]);
			moreEnters.add(enter);
		}
	}

	private void initView(View view) {
		llCxfw = (LinearLayout) view.findViewById(R.id.ll_more_cxfw);
		llZyyy = (LinearLayout) view.findViewById(R.id.ll_more_zyyy);
		llCxfwGroup = (LinearLayout) view.findViewById(R.id.ll_more_buttom_cxfw);
		listView = (ListView) view.findViewById(R.id.list_more);
		vZjxx = view.findViewById(R.id.ll_more_zjxx);
		vBgyd = view.findViewById(R.id.ll_more_bgyd);
		vYydh = view.findViewById(R.id.ll_more_yydh);
		vLxdt = view.findViewById(R.id.ll_more_lxdt);

		llCxfwGroup.setVisibility(View.GONE);
		listView.setVisibility(View.VISIBLE);

		llCxfw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (llCxfw.getTag().equals("1")) {
					llCxfw.setTag("0");
					llZyyy.setTag("1");
					llCxfw.setBackgroundResource(R.drawable.more_popup_selected_sel);
					llZyyy.setBackgroundResource(R.drawable.more_popup_sel);
					llCxfwGroup.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
				}
			}
		});
		llZyyy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (llCxfw.getTag().equals("0")) {
					llCxfw.setTag("1");
					llZyyy.setTag("0");
					llCxfw.setBackgroundResource(R.drawable.more_popup_sel);
					llZyyy.setBackgroundResource(R.drawable.more_popup_selected_sel);
					llCxfwGroup.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);

				} else if (!llCxfw.getTag().equals("0") && index != -1) {
					index = -1;
					listView.setAdapter(new ListAdapte(enters));
				}
			}
		});
		vZjxx.setOnClickListener(new OnClickListener() {// 自驾选线

			@Override
			public void onClick(View v) {

			}
		});
		vBgyd.setOnClickListener(new OnClickListener() {// 宾馆预订

			@Override
			public void onClick(View v) {

			}
		});
		vYydh.setOnClickListener(new OnClickListener() {// 语音导航

			@Override
			public void onClick(View v) {

			}
		});
		vLxdt.setOnClickListener(new OnClickListener() {// 离线地图

			@Override
			public void onClick(View v) {

			}
		});
	}

	/**
	 * 适配器
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class ListAdapte extends BaseAdapter {
		ArrayList<MoreEnter> moreEnters;

		public ListAdapte(ArrayList<MoreEnter> moreEnters) {
			this.moreEnters = moreEnters;
		}

		@Override
		public int getCount() {
			return moreEnters.size();
		}

		@Override
		public MoreEnter getItem(int arg0) {
			return moreEnters.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder;
			if (arg1 == null) {
				holder = new ViewHolder();
				arg1 = LayoutInflater.from(activity).inflate(R.layout.more_list_item, arg2, false);
				holder.imageView = (ImageView) arg1.findViewById(R.id.img_more_zyyy_item);
				holder.textView = (TextView) arg1.findViewById(R.id.tv_more_zyyy_item);
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}
			MoreEnter moreEnter = getItem(arg0);
			holder.imageView.setBackgroundResource(moreEnter.getIcon());
			holder.textView.setText(moreEnter.getTitle());
			return arg1;
		}

		private class ViewHolder {
			TextView textView;
			ImageView imageView;
		}
	}

	/**
	 * list数据
	 * 
	 * @author WuXiaoHao
	 *
	 */
	private class MoreEnter {
		int icon;
		String title;
		boolean isMain;

		public int getIcon() {
			return icon;
		}

		public void setIcon(int icon) {
			this.icon = icon;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public boolean isMain() {
			return isMain;
		}

		public void setMain(boolean isMain) {
			this.isMain = isMain;
		}

	}

	public void show() {
		int[] location = new int[2];
		mView.getLocationOnScreen(location);

		popMenu.showAtLocation(mView, Gravity.NO_GRAVITY, location[0], location[1] - height);
	}
}
