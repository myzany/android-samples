package com.zany.custompopup;

import java.util.ArrayList;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class PopupGestureListener extends GestureDetector.SimpleOnGestureListener {

	private final String TAG = this.getClass().getSimpleName();
	
	private final int SCROLL_AMOUNT = -120;
	private ArrayList<Integer> mArrDistX;
	private ArrayList<Integer> mArrDistY;
	
	private PopupMenu mPopupMenu;
	
	public void setPopupMenu(PopupMenu popupMenu) {
		this.mPopupMenu = popupMenu;
	}
	
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
		
		for (int iValue : mArrDistY) {
			scrollAmount += iValue;
		}
		
		if (scrollAmount < SCROLL_AMOUNT) {
			mPopupMenu.show();
		} else if (scrollAmount > SCROLL_AMOUNT*(-1)) {
			mPopupMenu.hide();
		}
		
		return super.onFling(e1, e2, velocityX, velocityY);
		
	}

}
