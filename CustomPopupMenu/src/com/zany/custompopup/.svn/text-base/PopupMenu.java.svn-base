package com.zany.custompopup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class PopupMenu extends RelativeLayout {

	private final String TAG = this.getClass().getSimpleName();
	private Context mContext;

	private Point AREA_SRT = new Point();  // �� VIEW �� ǥ�õǴ� ������ ���� x,y ��ǥ
	private Point AREA_END = new Point();  // �� VIEW �� ǥ�õǴ� ������ ���� x,y ��ǥ
	private Point mLayoutPos = new Point();
	
	private int AREA_WIDTH  = 0;
	private int AREA_HEIGHT = 200;
	private int AREA_MARGIN = 10;
	
	private int mScrWidth;
	private int mScrHeight;

	private boolean mShow;

	public Point getAreaSrt() {
		return this.AREA_SRT;
	}
	
	public Point getAreaEnd() {
		return this.AREA_END;
	}
	
	public boolean isShow() {
		return mShow;
	}
	
	public Point getLayoutCurrentPosition() {
		return mLayoutPos;
	}

	public PopupMenu(Context context, int scrWidth, int scrHeight) {
		
		super(context);
		
		this.mContext = context;
		this.mScrWidth = scrWidth;
		this.mScrHeight = scrHeight;
		
		this.mShow = false;
		this.AREA_WIDTH = mScrWidth - (AREA_MARGIN*2);

		drawLayout();
		this.setVisibility(View.GONE);

	}

	public void show() {
		
		this.mShow = true;

		TranslateAnimation aniTrans = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
				TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
				TranslateAnimation.RELATIVE_TO_SELF, -0.3f,
				TranslateAnimation.RELATIVE_TO_SELF, 0.0f);
		aniTrans.setDuration(200);

		AnimationSet aniSet = new AnimationSet(true);
		aniSet.setInterpolator(new AccelerateInterpolator());
		aniSet.addAnimation(aniTrans);
		aniSet.setFillEnabled(true);
		
		this.startAnimation(aniSet);
		this.setVisibility(View.VISIBLE);

	}
	
	public void hide() {

		TranslateAnimation aniTrans = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
				TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
				TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
				TranslateAnimation.RELATIVE_TO_SELF, -0.3f);
		aniTrans.setDuration(200);

		AnimationSet aniSet = new AnimationSet(true);
		aniSet.setInterpolator(new AccelerateInterpolator());
		aniSet.addAnimation(aniTrans);
		aniSet.setFillEnabled(true);
		
		this.startAnimation(aniSet);
		this.setVisibility(View.GONE);

		// ��ġ ������ GestureListener �� ��ġ ���۰� ��ø�ǹǷ�,
		// ������ ������ ���� ��ǥ�� �ʱ�ȭ�� �ణ ���� ��Ŵ.
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				getAreaSrt().set(0, 0);
				getAreaEnd().set(0, 0);
				mShow = false;
			}
		}, 1000);
		
	}
	
	private void drawLayout() {
		
		Log.e(TAG, "---- drawLayout");

		this.removeAllViewsInLayout();
		this.removeAllViews();

		RelativeLayout.LayoutParams params;
		
		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		RelativeLayout mainLayout = new RelativeLayout(mContext);
		mainLayout.setBackgroundColor(Color.TRANSPARENT);
		mainLayout.setLayoutParams(params);
		
		addView(mainLayout);

		//this.scrollTo(0, AREA_HEIGHT);

	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		if (mShow) {
			
			Point pointSrt = new Point();
			Point pointEnd = new Point();
			Paint paint = new Paint();
			RectF rect = null;
			
			// ��ü ���� ��׶���
			
			paint.reset();
			paint.setColor(Color.BLACK);
			paint.setAlpha((int)(255*0.7));
			paint.setAntiAlias(true);
	
			pointSrt.x = AREA_MARGIN;
			pointSrt.y = AREA_MARGIN;
			
			pointEnd.x = AREA_MARGIN + AREA_WIDTH;
			pointEnd.y = AREA_MARGIN + AREA_HEIGHT;
			
			rect = new RectF(pointSrt.x, pointSrt.y, pointEnd.x, pointEnd.y);
			canvas.drawRoundRect(rect, 10.0f, 10.0f, paint);
	
			AREA_SRT.set(pointSrt.x, pointSrt.y);
			AREA_END.set(pointEnd.x, pointEnd.y);	

			
		}

		super.dispatchDraw(canvas);
		
	}
	
}
