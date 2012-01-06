package com.zany.hwpopmenu;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class HighwayMenuTest extends Activity {
	
	private Context mContext;
	private MonHwPopupMenu mPopupMenu;

	private final int TOP_PADDING = 90;
	private final int TITLE_HEIGHT = 45;

	private PopupGestureListener mGestureListener;
	private GestureDetector mGestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.mContext = getApplicationContext();

		BaseData.FONT_FACE = Typeface.createFromAsset(getAssets(), BaseData.FONT_FILE);

		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight() - TOP_PADDING;

		RelativeLayout.LayoutParams params;
		RelativeLayout mainLayout;
		RelativeLayout layoutTitle;
		RelativeLayout layoutContent;

		// MAIN LAYOUT
		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		mainLayout = new RelativeLayout(mContext);
		mainLayout.setLayoutParams(params);
		mainLayout.setBackgroundColor(Color.TRANSPARENT);

		// LAYOUT - TITLE
		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, TITLE_HEIGHT);
		params.topMargin = 0;
		layoutTitle = new RelativeLayout(mContext);
		layoutTitle.setLayoutParams(params);
		layoutTitle.setBackgroundColor(Color.rgb(27, 74, 121));
		mainLayout.addView(layoutTitle);
		
		// LAYOUT - CONTENT
		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params.topMargin = TITLE_HEIGHT;
		layoutContent = new RelativeLayout(mContext);
		layoutContent.setLayoutParams(params);
		layoutContent.setBackgroundColor(Color.TRANSPARENT);
		layoutContent.setBackgroundResource(R.drawable.bg_map);
		mainLayout.addView(layoutContent);

		TextView tvTitle = new TextView(mContext);
		tvTitle.setPadding(5, 0, 0, 0);  // L,T,R,B
		tvTitle.setSingleLine();
		tvTitle.setHeight(TITLE_HEIGHT);
		tvTitle.setTypeface(BaseData.FONT_FACE);
		tvTitle.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
		tvTitle.setTextSize(13.0f);
		tvTitle.setTextColor(Color.WHITE);
		tvTitle.setText(Html.fromHtml("<b>메뉴를 보려면 <font color='#EE8888'>우측으로 Swipe</font> 하세요.</b>"));
		layoutTitle.addView(tvTitle);
		
		mPopupMenu = new MonHwPopupMenu(mContext, displayWidth, displayHeight);
		mainLayout.addView(mPopupMenu);

		mGestureListener = new PopupGestureListener();
		mGestureDetector = new GestureDetector(mContext, mGestureListener);

		layoutTitle.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				return true;
			}
		});

		setContentView(mainLayout, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
	}

	private class PopupGestureListener extends GestureDetector.SimpleOnGestureListener {

		private final String TAG = this.getClass().getSimpleName();
		
		private final int SCROLL_AMOUNT = 30;
		private ArrayList<Integer> mArrDistX;
		private ArrayList<Integer> mArrDistY;
		
		public PopupGestureListener() {
			mArrDistX = new ArrayList<Integer>();
			mArrDistY = new ArrayList<Integer>();
		}

		@Override
		public boolean onDown(MotionEvent e) {
			mArrDistX.clear();
			mArrDistY.clear();
			return super.onDown(e);
		}
		
		@Override
		public boolean onScroll(
							MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {

			mArrDistX.add((int)distanceX);
			mArrDistY.add((int)distanceY);
			
			//Log.e(TAG, "onScroll - MotionEvent e1 (x,y) : " + e1.getX() + " x " + e1.getY());
			//Log.e(TAG, "onScroll - MotionEvent e2 (x,y) : " + e2.getX() + " x " + e2.getY());
			//Log.e(TAG, "onScroll - Distance (x,y) : " + distanceX + " x " + distanceY);

			return super.onScroll(e1, e2, distanceX, distanceY);
			
		}
		
		@Override
		public boolean onFling(
							MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {

			//Log.e(TAG, "onFling - MotionEvent e1 (x,y) : " + e1.getX() + " x " + e1.getY());
			//Log.e(TAG, "onFling - MotionEvent e2 (x,y) : " + e2.getX() + " x " + e2.getY());
			//Log.e(TAG, "onFling - Velocity (x,y) : " + velocityX + " x " + velocityY);
			
			int scrollAmount = 0;
			
			for (int iValue : mArrDistX) {
				scrollAmount += iValue;
			}
			
			if (scrollAmount < SCROLL_AMOUNT*(-1)) {
				mPopupMenu.show();
			} else if (scrollAmount > SCROLL_AMOUNT) {
				mPopupMenu.hide();
			}
			
			return super.onFling(e1, e2, velocityX, velocityY);
			
		}

	}
	
}