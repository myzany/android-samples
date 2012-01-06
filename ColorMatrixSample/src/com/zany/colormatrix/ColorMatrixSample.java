package com.zany.colormatrix;

/**
 * Author : Zany (myzany@mobigen.com / @ohmyzany)
 * Date : 2010-10-15
 * Description : 
 *     - �̹��� ���� ����(Hue/Contrast) �׽�Ʈ
 *     - Pilot Code.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ColorMatrixSample extends Activity {

	private String TAG = this.getClass().getSimpleName();
	
	private ImageView mBaseImage;
	private ColorMatrix mColorMatrix = new ColorMatrix();

	private SeekBarItem mSeekBarHueR;
	private SeekBarItem mSeekBarHueG;
	private SeekBarItem mSeekBarHueB;
	
	private int mValueHueR = 0;
	private int mValueHueG = 0;
	private int mValueHueB = 0;
	
	private class SeekBarItem extends RelativeLayout {
		
		private Context mContext;
		
		private TextView mTextView;
		private String mLabel;
		private int mMax;
		
		public SeekBarItem(Context context, String label, int valueMax, OnSeekBarChangeListener listener) {
			
			super(context);
			mContext = context;
			mLabel = label;
			mMax = valueMax;
			
			RelativeLayout.LayoutParams params;

			params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			RelativeLayout layout = new RelativeLayout(mContext);
			layout.setLayoutParams(params);

			params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			mTextView = new TextView(mContext);
			mTextView.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
			mTextView.setTextSize(13.0f);
			mTextView.setText(mLabel);
			
			params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			params.leftMargin = 100;
			SeekBar seekBar = new SeekBar(mContext);
			seekBar.setLayoutParams(params);
			seekBar.setMax(mMax);
			seekBar.setOnSeekBarChangeListener(listener);
			
			layout.addView(mTextView);
			layout.addView(seekBar);
			
			this.addView(layout);
			
		}
		
		public void setLabel(String mLabel) {
			this.mLabel = mLabel;
			mTextView.setText(mLabel);
		}
		
	}
	
	private class SeekBarChangeListenerHueR implements OnSeekBarChangeListener {
		public void onStopTrackingTouch(SeekBar seekBar) { }
		public void onStartTrackingTouch(SeekBar seekBar) { }
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			mValueHueR = progress;
			mSeekBarHueR.setLabel("Hue-R:" + progress);
			AdjustColorMatrix.setTranslate(mColorMatrix, mValueHueR, mValueHueG, mValueHueB, 0);
			mBaseImage.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
		}
	}

	private class SeekBarChangeListenerHueG implements OnSeekBarChangeListener {
		public void onStopTrackingTouch(SeekBar seekBar) { }
		public void onStartTrackingTouch(SeekBar seekBar) { }
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			mValueHueG = progress;
			mSeekBarHueG.setLabel("Hue-G:" + progress);
			AdjustColorMatrix.setTranslate(mColorMatrix, mValueHueR, mValueHueG, mValueHueB, 0);
			mBaseImage.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
		}
	}

	private class SeekBarChangeListenerHueB implements OnSeekBarChangeListener {
		public void onStopTrackingTouch(SeekBar seekBar) { }
		public void onStartTrackingTouch(SeekBar seekBar) { }
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			mValueHueB = progress;
			mSeekBarHueB.setLabel("Hue-B:" + progress);
			AdjustColorMatrix.setTranslate(mColorMatrix, mValueHueR, mValueHueG, mValueHueB, 0);
			mBaseImage.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);

		LinearLayout.LayoutParams params;

		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setLayoutParams(params);
		mainLayout.setOrientation(LinearLayout.VERTICAL);

		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
		LinearLayout layoutTop = new LinearLayout(this);
		layoutTop.setLayoutParams(params);
		layoutTop.setOrientation(LinearLayout.VERTICAL);

		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 400, 1.0f);
		LinearLayout layoutBottom = new LinearLayout(this);
		layoutBottom.setLayoutParams(params);
		layoutBottom.setOrientation(LinearLayout.VERTICAL);

		// TOP
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a9_01);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
		mBaseImage = new ImageView(this);
		mBaseImage.setLayoutParams(params);
		mBaseImage.setImageBitmap(bitmap);

		layoutTop.addView(mBaseImage);
		
		
		// BOTTOM
		
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
		mSeekBarHueR = new SeekBarItem(this, "Hue-R:0", 255, new SeekBarChangeListenerHueR());
		mSeekBarHueR.setLayoutParams(params);
		layoutBottom.addView(mSeekBarHueR);

		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
		mSeekBarHueG = new SeekBarItem(this, "Hue-G:0", 255, new SeekBarChangeListenerHueG());
		mSeekBarHueG.setLayoutParams(params);
		layoutBottom.addView(mSeekBarHueG);

		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
		mSeekBarHueB = new SeekBarItem(this, "Hue-B:0", 255, new SeekBarChangeListenerHueB());
		mSeekBarHueB.setLayoutParams(params);
		layoutBottom.addView(mSeekBarHueB);

		mainLayout.addView(layoutTop);
		mainLayout.addView(layoutBottom);

		setContentView(mainLayout);
		
	}

}