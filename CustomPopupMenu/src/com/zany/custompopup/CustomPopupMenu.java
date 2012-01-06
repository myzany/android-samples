package com.zany.custompopup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class CustomPopupMenu extends Activity {
	
	private Context mContext;
	private PopupMenu mPopupMenu;

	private final int TOP_PADDING = 50;

	private PopupGestureListener mGestureListener;
	private GestureDetector mGestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		this.mContext = getApplicationContext();

		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight() - TOP_PADDING;

		RelativeLayout.LayoutParams params;
		RelativeLayout mainLayout = new RelativeLayout(mContext);
		mainLayout.setBackgroundColor(Color.WHITE);

		mPopupMenu = new PopupMenu(mContext, displayWidth, displayHeight);
		mainLayout.addView(mPopupMenu);

		mGestureListener = new PopupGestureListener();
		mGestureListener.setPopupMenu(mPopupMenu);
		mGestureDetector = new GestureDetector(mainLayout.getContext(), mGestureListener);

		mainLayout.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				return true;
			}
		});

		setContentView(mainLayout, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
	}
	
}