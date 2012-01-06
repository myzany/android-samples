package com.zany.customtoast;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

public class CustomToast extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

//		LayoutInflater inflater = getLayoutInflater();
//		View layout = inflater.inflate(R.layout.toast_layout,
//		                               (ViewGroup) findViewById(R.id.toast_layout_root));
//
//		ImageView image = (ImageView) layout.findViewById(R.id.image);
//		image.setImageResource(R.drawable.icon);
//		TextView text = (TextView) layout.findViewById(R.id.text);
//		text.setText("Hello! This is a custom toast!");

		ToastLayout toastLayout = new ToastLayout(this, "Custom Toast TEST");
		
		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 50);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(toastLayout.getToastLayout());
		toast.show();
		
	}
	
}