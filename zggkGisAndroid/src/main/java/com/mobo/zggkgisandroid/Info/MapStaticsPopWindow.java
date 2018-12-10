package com.mobo.zggkgisandroid.Info;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.mobo.zggkgisandroid.R;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MapStaticsPopWindow {

	private PopupWindow pop;
	private ListView listView;
	private Activity activity;
	private List<String> Data;
	private View viewRoot;
	private myClick click;
	viewHolder holder;

	public MapStaticsPopWindow(List<String> Data, View viewRoot,
			Activity activity, myClick click) {
		this.activity = activity;
		this.Data = Data;
		this.viewRoot = viewRoot;
		this.click = click;
		View popView = LayoutInflater.from(activity).inflate(R.layout.listview,
				null);
		pop = new PopupWindow(popView, viewRoot.getWidth(),
				LayoutParams.WRAP_CONTENT);
		pop.setFocusable(true);
		popView.setFocusableInTouchMode(false);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setTouchable(true);
		popView.setBackgroundColor(0xffffffff);
		listView = (ListView) popView.findViewById(R.id.map_statics_lv);
		MyAdater adater = new MyAdater(activity, 0, Data, click);
		listView.setAdapter(adater);

	}

	class MyAdater extends ArrayAdapter<String> {
		private myClick click;
		private Map save = new LinkedHashMap<String, String>();
		List<String> objects;

		public MyAdater(Context context, int resource, List<String> objects,
				myClick click) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			this.objects = objects;
			this.click = click;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub

			if (convertView == null) {
				holder = new viewHolder();
				convertView = LayoutInflater.from(activity).inflate(
						R.layout.map_statics_lv_item, null);
				holder.mapStatics = (RelativeLayout) convertView
						.findViewById(R.id.map_statics);
				holder.mapStaticsDanwei = (TextView) convertView
						.findViewById(R.id.map_statics_danwei);
				holder.mapStaticsDuigou = (TextView) convertView
						.findViewById(R.id.map_statics_duigou);
				convertView.setTag(holder);
			}
			holder = (viewHolder) convertView.getTag();
			holder.mapStaticsDanwei.setText(objects.get(position));
			holder.mapStaticsDuigou.setText("✔");
			holder.mapStatics.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					save.clear();
					save.put("key", objects.get(position));

					click.OnClick(objects.get(position).toString(), position);
					MyAdater.this.notifyDataSetChanged();
					dismiss();
				}

			});
			if (!save.isEmpty()) {
				String object = (String) save.get("key");

				if (position == 0 || position == 1) {
					holder.mapStaticsDuigou.setVisibility(View.INVISIBLE);
				} else {
					if (objects.get(position).equals(object)) {
						holder.mapStaticsDuigou.setVisibility(View.VISIBLE);
					} else {
						holder.mapStaticsDuigou.setVisibility(View.INVISIBLE);
					}
				}

			} else {
				holder.mapStaticsDuigou.setVisibility(View.INVISIBLE);
			}

			return convertView;

		}
	}

	class viewHolder {
		private RelativeLayout mapStatics;
		private TextView mapStaticsDanwei;
		private TextView mapStaticsDuigou;

	}

	public void show() {
		pop.showAsDropDown(viewRoot, 0, 0);

	}

	public void dismiss() {
		if (pop.isShowing()) {
			pop.dismiss();
		}
	}

}

interface myClick {
	void OnClick(String tag, int position);

}
