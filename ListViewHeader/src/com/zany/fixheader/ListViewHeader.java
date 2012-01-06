package com.zany.fixheader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListViewHeader extends Activity {
	
	private final String TAG = this.getClass().getSimpleName();
	
	private final int TEXT_TITLE_HEIGHT = 35;
	private final int LIST_TITLE_HEIGHT = 50;
	
	private Context mContext;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		this.mContext = getApplicationContext();

		BaseData.FONT_FACE = Typeface.createFromAsset(getAssets(), BaseData.FONT_FILE);

		int scrWidth  = getWindowManager().getDefaultDisplay().getWidth();
		int scrHeight = getWindowManager().getDefaultDisplay().getHeight();
		
		RelativeLayout.LayoutParams params;
		
		RelativeLayout mainLayout = new RelativeLayout(mContext);
		
		// TEXT VIEW
		
		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, TEXT_TITLE_HEIGHT);
		
		TextView tvTitle = new TextView(mContext);
		tvTitle.setTextSize(13.0f);
		tvTitle.setTypeface(BaseData.FONT_FACE);
		tvTitle.setBackgroundColor(Color.GRAY);
		tvTitle.setText(Html.fromHtml("<b>List of Country & their denotation</b>"));
		
		// LIST VIEW (TITLE)

		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LIST_TITLE_HEIGHT);
		
		ListView lvTitle = new ListView(mContext);
		lvTitle.setLayoutParams(params);
		lvTitle.setDivider(new ColorDrawable(Color.TRANSPARENT));
		lvTitle.setDividerHeight(0);
		lvTitle.setScrollingCacheEnabled(true);
		lvTitle.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
		lvTitle.setBackgroundColor(Color.argb(255, 35, 44, 97));
		lvTitle.setCacheColorHint(Color.TRANSPARENT);   // 스크롤시 배경이 검은색으로 변하는 현상 제거.
		lvTitle.setFadingEdgeLength(0);                 // Edge 에 붙는 그라데이션 크기 결정.
		lvTitle.setAdapter(new ListAdapterTitle(this));

		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.topMargin = TEXT_TITLE_HEIGHT;
		
		CustomHSV svHzTitle = new CustomHSV(mContext);
		svHzTitle.setLayoutParams(params);
		svHzTitle.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
		svHzTitle.setBackgroundColor(Color.TRANSPARENT);
		svHzTitle.setFadingEdgeLength(0);
		svHzTitle.setSmoothScrollingEnabled(true);
		svHzTitle.setHorizontalScrollBarEnabled(false);
		svHzTitle.setClickable(false);
		svHzTitle.addView(lvTitle);

		// LIST VIEW (CONTENT)
		
		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		ListView lvContent = new ListView(mContext);
		lvContent.setLayoutParams(params);
		lvContent.setDivider(new ColorDrawable(Color.argb(255, 128, 128, 128)));
		lvContent.setDividerHeight(1);
		lvContent.setScrollingCacheEnabled(true);
		lvContent.setScrollBarStyle(ListView.SCROLLBARS_INSIDE_OVERLAY);
		lvContent.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
		lvContent.setBackgroundColor(Color.TRANSPARENT);
		lvContent.setCacheColorHint(Color.TRANSPARENT);   // 스크롤시 배경이 검은색으로 변하는 현상 제거.
		lvContent.setFadingEdgeLength(0);                 // Edge 에 붙는 그라데이션 크기 결정.
		lvContent.setAdapter(new ListAdapterContent(this));

		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.topMargin = TEXT_TITLE_HEIGHT + LIST_TITLE_HEIGHT;
		
		CustomHSV svHzContent = new CustomHSV(mContext);
		svHzContent.setLayoutParams(params);
		svHzContent.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
		svHzContent.setBackgroundColor(Color.TRANSPARENT);
		svHzContent.setFadingEdgeLength(0);
		svHzContent.setSmoothScrollingEnabled(true);
		svHzContent.setHorizontalScrollBarEnabled(true);
		svHzContent.setClickable(true);
		svHzContent.addView(lvContent);

		// ScrollView Scroll Sync

		svHzTitle.setSyncObject(svHzContent);
		svHzContent.setSyncObject(svHzTitle);
		
		// Main Layout
		
		mainLayout.addView(tvTitle);
		mainLayout.addView(svHzTitle);
		mainLayout.addView(svHzContent);
		
		tvTitle.bringToFront();
		lvTitle.bringToFront();
		
		setContentView(mainLayout);

	}
	
	private class CustomHSV extends HorizontalScrollView {
		private CustomHSV mSyncObject = null;
		public CustomHSV(Context context) {
			super(context);
		}
		public void setSyncObject(CustomHSV syncObject) {
			this.mSyncObject = syncObject;
		}
		protected void onScrollChanged(int l, int t, int oldl, int oldt) {
			//Log.e(TAG, "--- l:" + l + ", t:" + t + ", oldl:" + oldl + ", oldt:" + oldt);
			if (mSyncObject != null) {
				mSyncObject.smoothScrollTo(l, mSyncObject.getScrollY());
			}
			super.onScrollChanged(l, t, oldl, oldt);
		}
	}
	
	private static class ListAdapterTitle extends BaseAdapter {
		
		private LayoutInflater mInflater;
		
		public ListAdapterTitle(Context context) {
			mInflater = LayoutInflater.from(context);
		}
		
		public int getCount() {
			return 1;
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
				
				convertView = mInflater.inflate(R.layout.listview_title, null);
				holder = new ViewHolder();
				holder.text01 = (TextView) convertView.findViewById(R.id.TextView01);
				holder.text02 = (TextView) convertView.findViewById(R.id.TextView02);
				holder.text03 = (TextView) convertView.findViewById(R.id.TextView03);
				holder.text04 = (TextView) convertView.findViewById(R.id.TextView04);
				holder.text05 = (TextView) convertView.findViewById(R.id.TextView05);
				holder.text06 = (TextView) convertView.findViewById(R.id.TextView06);
				holder.text07 = (TextView) convertView.findViewById(R.id.TextView07);
				holder.text08 = (TextView) convertView.findViewById(R.id.TextView08);

				convertView.setTag(holder);
				
			} else {
				
				holder = (ViewHolder) convertView.getTag();
				
			}

			ViewHolder.setColumnWidth(holder.text01, 0, BaseData.LISTTYPE_TITLE);
			ViewHolder.setColumnWidth(holder.text02, 1, BaseData.LISTTYPE_TITLE);
			ViewHolder.setColumnWidth(holder.text03, 2, BaseData.LISTTYPE_TITLE);
			ViewHolder.setColumnWidth(holder.text04, 3, BaseData.LISTTYPE_TITLE);
			ViewHolder.setColumnWidth(holder.text05, 4, BaseData.LISTTYPE_TITLE);
			ViewHolder.setColumnWidth(holder.text06, 5, BaseData.LISTTYPE_TITLE);
			ViewHolder.setColumnWidth(holder.text07, 6, BaseData.LISTTYPE_TITLE);
			ViewHolder.setColumnWidth(holder.text08, 7, BaseData.LISTTYPE_TITLE);

			holder.text01.setText(colTitle[0]);
			holder.text02.setText(colTitle[1]);
			holder.text03.setText(colTitle[2]);
			holder.text04.setText(colTitle[3]);
			holder.text05.setText(colTitle[4]);
			holder.text06.setText(colTitle[5]);
			holder.text07.setText(colTitle[6]);
			holder.text08.setText(colTitle[7]);

			return convertView;
			
		}
		
	}
	
	private static class ListAdapterContent extends BaseAdapter {
		
		private LayoutInflater mInflater;

		public ListAdapterContent(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return col01.length;
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
				
				convertView = mInflater.inflate(R.layout.listview_content, null);
				holder = new ViewHolder();
				holder.text01 = (TextView) convertView.findViewById(R.id.TextView01);
				holder.text02 = (TextView) convertView.findViewById(R.id.TextView02);
				holder.text03 = (TextView) convertView.findViewById(R.id.TextView03);
				holder.text04 = (TextView) convertView.findViewById(R.id.TextView04);
				holder.text05 = (TextView) convertView.findViewById(R.id.TextView05);
				holder.text06 = (TextView) convertView.findViewById(R.id.TextView06);
				holder.text07 = (TextView) convertView.findViewById(R.id.TextView07);
				holder.text08 = (TextView) convertView.findViewById(R.id.TextView08);

				convertView.setTag(holder);
				
			} else {
				
				holder = (ViewHolder) convertView.getTag();
				
			}

			ViewHolder.setColumnWidth(holder.text01, 0, BaseData.LISTTYPE_CONTENT);
			ViewHolder.setColumnWidth(holder.text02, 1, BaseData.LISTTYPE_CONTENT);
			ViewHolder.setColumnWidth(holder.text03, 2, BaseData.LISTTYPE_CONTENT);
			ViewHolder.setColumnWidth(holder.text04, 3, BaseData.LISTTYPE_CONTENT);
			ViewHolder.setColumnWidth(holder.text05, 4, BaseData.LISTTYPE_CONTENT);
			ViewHolder.setColumnWidth(holder.text06, 5, BaseData.LISTTYPE_CONTENT);
			ViewHolder.setColumnWidth(holder.text07, 6, BaseData.LISTTYPE_CONTENT);
			ViewHolder.setColumnWidth(holder.text08, 7, BaseData.LISTTYPE_CONTENT);
			
			holder.text01.setText(col01[position]);
			holder.text02.setText(col02[position]);
			holder.text03.setText(col03[position]);
			holder.text04.setText(col04[position]);
			holder.text05.setText(col05[position]);
			holder.text06.setText(col06[position]);
			holder.text07.setText(col07[position]);
			holder.text08.setText(col08[position]);

			return convertView;
			
		}

	}
	
	private static class ViewHolder {
		
		TextView text01;
		TextView text02;
		TextView text03;
		TextView text04;
		TextView text05;
		TextView text06;
		TextView text07;
		TextView text08;

		private static void setColumnWidth(TextView textView, int colNumber, int colType) {
			
			int leftMargin = 0;
			
			if (colNumber != 0) {
				for (int iIdx = 0; iIdx < colNumber; iIdx++) {
					leftMargin += colWidth[iIdx];
				}
			}

			RelativeLayout.LayoutParams params;
			params = new RelativeLayout.LayoutParams(colWidth[colNumber], LayoutParams.FILL_PARENT);

			switch (colType) {
			
				case BaseData.LISTTYPE_TITLE:

					params.topMargin = 10;
					params.leftMargin = leftMargin + 3;
					textView.setLayoutParams(params);
					textView.setGravity(Gravity.CENTER_VERTICAL);
					textView.setBackgroundColor(Color.rgb(35, 44, 97));
					textView.setTypeface(BaseData.FONT_FACE);

					break;
					
				case BaseData.LISTTYPE_CONTENT:
					
					params.height = BaseData.LIST_ROW_HEIGHT;
					params.leftMargin = leftMargin;
					textView.setLayoutParams(params);
					textView.setGravity(Gravity.CENTER_VERTICAL);
					textView.setTypeface(BaseData.FONT_FACE);
					
					break;
					
			}

		}
		
	}

	private static final Integer[] colWidth = {
		70, 150, 150, 150, 100, 100, 100, 100 };
	
	private static final String[] colTitle = {
		"SRT", "Name", "Population", "Area",
		"Col5", "Col6", "Col7", "Col8"
	};
	
	private static final String[] col01 = {
		"ISK", "INR", "IDR", "IRR", "IQD",
		"EUR", "ILS", "EUR", "LAK", "LVL",
		"LBP", "LSL", "LRD", "LYD", "LTL",
		"EUR" };

	private static final String[] col02 = {
		"Iceland", "India", "Indonesia", "Iran", "Iraq",
		"Ireland", "Israel", "Italy", "Laos", "Latvia",
		"Lebanon", "Lesotho", "Liberia", "Libya", "Lithuania",
		"Luxembourg" };

	private static final String[] col03 = {
		"38,398,293", "219,283,203", "384,293,490", "12,394,023", "348,394,232",
		"23,485,394", "12,394,120", "100,298,293", "293,293,102", "98,384,289",
		"39,394,229", "95,298,238", "10,293,293", "201,238,638", "84,283,129",
		"12,384,229" };

	private static final String[] col04 = {
		"32,849㎡", "203,283㎡", "29,392㎡", "82,384㎡", "328,394㎡",
		"290,019㎡", "29,283㎡", "283,290㎡", "109,293㎡", "384,293㎡",
		"201,110㎡", "1,293,923㎡", "283,293㎡", "293,284㎡", "903,293㎡",
		"930,001㎡" };

	private static final String[] col05 = {
		"", "", "", "", "",
		"", "", "", "", "",
		"", "", "", "", "",
		""
	};

	private static final String[] col06 = {
		"", "", "", "", "",
		"", "", "", "", "",
		"", "", "", "", "",
		""
	};

	private static final String[] col07 = {
		"", "", "", "", "",
		"", "", "", "", "",
		"", "", "", "", "",
		""
	};

	private static final String[] col08 = {
		"", "", "", "", "",
		"", "", "", "", "",
		"", "", "", "", "",
		""
	};

}