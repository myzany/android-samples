package com.zany.hwdetail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class HighwayDetailLayerTest extends Activity {

	private final String TAG = this.getClass().getSimpleName();
	private final int TOP_PADDING = 45;

	private Context mContext;
	private MonHwPopupDetail mPopupDetail;
	
	private MyGestureListener mGestureListener;
	private GestureDetector mGestureDetector;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.mContext = this.getApplicationContext();

		BaseData.FONT_FACE = Typeface.createFromAsset(getAssets(), BaseData.FONT_FILE);
		
		//Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		//
		//int displayWidth = display.getWidth();
		//int displayHeight = display.getHeight() - TOP_PADDING;
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int scrWidth  = dm.widthPixels;
		int scrHeight = dm.heightPixels - TOP_PADDING;

		Log.e(TAG, "--------------- screen Width & Height : " + scrWidth + " x " + scrHeight);

		RelativeLayout.LayoutParams params;
		RelativeLayout mainLayout;
		
		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		// Base Layout
		
		mainLayout = new RelativeLayout(mContext);
		mainLayout.setLayoutParams(params);
		mainLayout.setBackgroundColor(Color.TRANSPARENT);
		mainLayout.setBackgroundResource(R.drawable.bg_map);
		
		// BaseMap ImageView
		
		ImageView ivBackground = new ImageView(mContext);
		ivBackground.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		ivBackground.setImageResource(R.drawable.base_map_highway);
		mainLayout.addView(ivBackground);
		
		// Layer Show Button
		
		Button btnShow = new Button(mContext);
		btnShow.setText("Show Layer");
		btnShow.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AlphaAnimation aniAlpha = new AlphaAnimation(0.0f, 1.0f);
				aniAlpha.setDuration(250);
				aniAlpha.setInterpolator(new AccelerateInterpolator());
				aniAlpha.setFillEnabled(true);
				mPopupDetail.setVisibility(View.VISIBLE);
				mPopupDetail.startAnimation(aniAlpha);
			}
		});
		mainLayout.addView(btnShow);
		
		// Popup Layer
		
		mPopupDetail = new MonHwPopupDetail(mContext, scrWidth, scrHeight);
		mPopupDetail.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		mPopupDetail.setVisibility(View.INVISIBLE);
		mainLayout.addView(mPopupDetail);
		
		setContentView(mainLayout);
		
		// GESTURE LISTENER (for Test)
		
		mGestureListener = new MyGestureListener();
		mGestureListener.setPopupDetail(mPopupDetail);
		
		mGestureDetector = new GestureDetector(mainLayout.getContext(), mGestureListener);
		mainLayout.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				return true;
			}
		});
		
	}
	
	private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		
		private MonHwPopupDetail mPopupDetail;
		
		public MyGestureListener() {
		}
		
		public void setPopupDetail(MonHwPopupDetail popupDetail) {
			this.mPopupDetail = popupDetail;
		}

		private boolean isTouchableArea(MotionEvent e) {
			
			boolean bTouchable = true;
			
			if (mPopupDetail.isShow()) {
				if (bTouchable) {
					if (((e.getX() > mPopupDetail.getAreaSrt().x) && (e.getY() > mPopupDetail.getAreaSrt().y)) &&
						((e.getX() < mPopupDetail.getAreaEnd().x) && (e.getY() < mPopupDetail.getAreaEnd().y))) {
						bTouchable = false;
					}
				}
			}

			//Log.i(TAG, "--------- CHECK TOUCH AREA.");
			//Log.i(TAG, "EVENT (x,y) = (" + e.getX() + "," + e.getY() + ") | POPUP MENU isShow : " + mPopupDetail.isShow());
			//Log.i(TAG, "POPUP MENU SRT (x,y) = (" + mPopupDetail.getAreaSrt().x + "," + mPopupDetail.getAreaSrt().y + ")");
			//Log.i(TAG, "POPUP MENU END (x,y) = (" + mPopupDetail.getAreaEnd().x + "," + mPopupDetail.getAreaEnd().y + ")");

			return bTouchable;
			
		}
		@Override
		public boolean onDown(MotionEvent e) {
			if (isTouchableArea(e)) {
				Log.e(TAG,"---------------------- onDown");
			}
			return super.onDown(e);
		}
		
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			if (isTouchableArea(e)) {
				Log.e(TAG,"---------------------- onSingleTapUp");
			}
			return super.onSingleTapUp(e);
		}
		
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (isTouchableArea(e)) {
				Log.e(TAG,"---------------------- onSingleTapConfirmed");
			}
			return super.onSingleTapConfirmed(e);
		}
		
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (isTouchableArea(e)) {
				Log.e(TAG,"---------------------- onDoubleTap");
			}
			return super.onDoubleTap(e);
		}
		
		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			if (isTouchableArea(e)) {
				Log.e(TAG,"---------------------- onDoubleTapEvent");
			}
			return super.onDoubleTapEvent(e);
		}
		
		@Override
		public void onLongPress(MotionEvent e) {
			if (isTouchableArea(e)) {
				Log.e(TAG,"---------------------- onLongPress");
			}
			super.onLongPress(e);
		}
		
		@Override
		public void onShowPress(MotionEvent e) {
			if (isTouchableArea(e)) {
				Log.e(TAG,"---------------------- onShowPress");
			}
			super.onShowPress(e);
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (isTouchableArea(e1)) {
				Log.e(TAG,"---------------------- onScroll");
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (isTouchableArea(e1)) {
				Log.e(TAG,"---------------------- onFling");
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
		
	}
	
}