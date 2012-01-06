package com.zany.customlist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomListView extends Activity {
	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);

		}

		public int getCount() {
			return country.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.listview, null);
				holder = new ViewHolder();
				holder.text1 = (TextView) convertView.findViewById(R.id.TextView01);
				holder.text2 = (TextView) convertView.findViewById(R.id.TextView02);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text1.setText(curr[position]);
			holder.text2.setText(country[position]);

			return convertView;
		}

		static class ViewHolder {
			TextView text1;
			TextView text2;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		int scrWidth  = getWindowManager().getDefaultDisplay().getWidth();
		int scrHeight = getWindowManager().getDefaultDisplay().getHeight();
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, scrHeight/2);
		params.topMargin = 120;
		
		ListView listView = (ListView) findViewById(R.id.MyListView);
		listView.setAdapter(new EfficientAdapter(this));
		listView.setLayoutParams(params);
	}

	private static final String[] country = {
			"Iceland", "India", "Indonesia", "Iran", "Iraq",
			"Ireland", "Israel", "Italy", "Laos", "Latvia",
			"Lebanon", "Lesotho ", "Liberia", "Libya", "Lithuania",
			"Luxembourg" };
	private static final String[] curr = {
			"ISK", "INR", "IDR", "IRR", "IQD",
			"EUR", "ILS", "EUR", "LAK", "LVL",
			"LBP", "LSL", "LRD", "LYD", "LTL ",
			"EUR" };
	
}