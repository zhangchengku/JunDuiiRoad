package com.mobo.zggkgisandroid.ServiceArea;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.mobo.zggkgisandroid.BaseActivity;
import com.mobo.zggkgisandroid.CustomApp;
import com.mobo.zggkgisandroid.R;
import com.mobo.zggkgisandroid.Utils.Utils;
import com.mobo.zggkgisandroid.WebModel.ServiceAreaListResult.ServiceArea;
import com.mobo.zggkgisandroid.WebModel.ServiceAreaListResult;
import com.mobo.zggkgisandroid.WebModel.ServiceAreaResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 服务区列表
 * 
 * @author WuXiaoHao
 *
 */
public class ServiceAreaListActivity extends BaseActivity {
	private ArrayList<ServiceArea> results;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentXml(R.layout.servicearealist_activity_layout);
		init();
	}

	private void init() {
		results = (ArrayList<ServiceArea>) getIntent().getSerializableExtra("result");

		tvTitle.setText(getIntent().getStringExtra("road_name"));
		imgLeft.setImageResource(R.drawable.rank_back_btn_sel);

		listView = (ListView) findViewById(R.id.list_servicearealist);
		listView.setAdapter(new ListAdapter());
	}

	@Override
	protected void onClickLeft(View view) {
		super.onClickLeft(view);
		finish();
	}

	private class ListAdapter extends BaseAdapter {
		DecimalFormat df = new DecimalFormat("0.00");

		@Override
		public int getCount() {
			return results != null ? results.size() : 0;
		}

		@Override
		public ServiceArea getItem(int arg0) {
			return results.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder viewHolder;
			if (arg1 == null) {
				viewHolder = new ViewHolder();
				arg1 = LayoutInflater.from(ServiceAreaListActivity.this).inflate(R.layout.servicearea_list_item, arg2,
						false);
				viewHolder.imageView = (ImageView) arg1.findViewById(R.id.img_servicearea_list);
				viewHolder.tvName = (TextView) arg1.findViewById(R.id.tv_servicearea_list_item_name);
				viewHolder.tvDistance = (TextView) arg1.findViewById(R.id.tv_servicearea_list_item_code);

				viewHolder.imageView.getLayoutParams().width = Utils.dip2px(ServiceAreaListActivity.this, 55);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			final ServiceArea serviceArea = getItem(arg0);
			if (serviceArea != null) {
				viewHolder.tvName.setText(serviceArea.getRoad_name());
				try {
					double distance = Double.parseDouble(serviceArea.getDistance());
					if (distance >= 1000) {
						viewHolder.tvDistance.setText(df.format(distance / 1000) + "公里");
					} else {
						viewHolder.tvDistance.setText((int) distance + "米");
					}
				} catch (Exception e) {
					viewHolder.tvDistance.setText("");
				}
				CustomApp.IMAGE_CACHE.get(serviceArea.getService_area_img(), viewHolder.imageView);
				arg1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						new ThreadWithProgressDialog().Run(ServiceAreaListActivity.this,
								new ServiceAreaDetailsDialogTask(serviceArea), R.string.com_loading);
					}
				});
			}
			return arg1;
		}

		private class ViewHolder {
			ImageView imageView;
			TextView tvName;// 名字
			TextView tvDistance;// 距离
		}

	}

	private class ServiceAreaDetailsDialogTask implements ThreadWithProgressDialogTask {
		private ServiceArea area;
		private ServiceAreaResult result;

		public ServiceAreaDetailsDialogTask(ServiceArea serviceArea) {
			this.area = serviceArea;
		}

		@Override
		public boolean TaskMain() {
			result = CustomApp.app.connInteface.getServiceArea(area.getRoad_code(), area.getService_area_code());

			result = new ServiceAreaResult();
			result.setStatus("Y");
			ServiceArea serviceArea = new ServiceAreaListResult().new ServiceArea();
			serviceArea.setService_area_code(area.getService_area_code());
			serviceArea.setService_area_name(area.getService_area_name());
			serviceArea.setService_area_introduce(
					"经过测试发现用doPost登陆之后，用doGet检测该用户是否登陆，必须用的是同一个httpclient才可以，否则还是返回没有登陆，所以getHttpClient这个方法应该用单例模式，");
			serviceArea.setService_area_phone("13321132250");
			serviceArea.setService_area_address("京藏公路中段100米");
			serviceArea.setService_area_img(area.getService_area_img());
			serviceArea.setParking_space("8");
			serviceArea.setCurrent_state("正常运营");
			serviceArea.setAvailable_parking_spaces("3");
			result.setResults(serviceArea);
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			int state = Utils.isCorrect(result, true);
			if (state == 0) {
				Intent intent = new Intent(ServiceAreaListActivity.this, ServiceAreaDetailsActivity.class);
				intent.putExtra("result", result.getResults());
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else if (state == 2) {
				CustomApp.app.exit(ServiceAreaListActivity.this);
			}
			return false;
		}

	}
}
