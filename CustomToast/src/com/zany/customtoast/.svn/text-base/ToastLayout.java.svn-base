package com.zany.customtoast;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ToastLayout {

	private Activity mActivity;
	
	private int mIconResID;
	private String message; 

	public ToastLayout(Activity activity, String message) {
		this.mActivity = activity;
		this.mIconResID = R.drawable.icon;
		this.message = message;
	}

	public void setmIconResID(int mIconResID) {
		this.mIconResID = mIconResID;
	}
	
	public ToastLayout(Activity activity, int iconResID, String message) {
		this.mActivity = activity;
		this.mIconResID = iconResID;
		this.message = message;
	}
	
	public View getToastLayout() {
		
		LayoutInflater inflater = mActivity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) mActivity.findViewById(R.id.custom_toast));
		layout.setBackgroundColor(Color.argb(128, 0, 0, 0));

		ImageView image = (ImageView) layout.findViewById(R.id.image);
		image.setImageResource(this.mIconResID);
		
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(this.message);
		
		return layout;

	}
	
}
