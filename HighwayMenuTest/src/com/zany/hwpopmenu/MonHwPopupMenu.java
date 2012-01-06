package com.zany.hwpopmenu;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MonHwPopupMenu extends RelativeLayout {

	private final String TAG = this.getClass().getSimpleName();
	private final int NOTHING = -1;
	private Context mContext;

	private Point AREA_SRT = new Point();  // 이 VIEW 가 표시되는 영역의 시작 x,y 좌표
	private Point AREA_END = new Point();  // 이 VIEW 가 표시되는 영역의 종료 x,y 좌표
	private Point mLayoutPos = new Point();
	
	private int AREA_WIDTH  = 0;
	private int AREA_HEIGHT = 390;

	private String BLANK_ITEM = "Blank";
	private int LIST_BLANK_COUNT_TOP = 3;

	private AreaMargin marginBackground;
	
	private ListScroll mListScroll;
	private ArrayList<TableRow> mArrTableRow;
	private ArrayList<MenuItem> mArrList;
	
	private int mScrWidth;
	private int mScrHeight;
	private float mItemHeight = 53.0f;

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

	public MonHwPopupMenu(Context context, int scrWidth, int scrHeight) {
		
		super(context);
		
		marginBackground = new AreaMargin(250, 50, 5, 10);
		
		this.mContext = context;
		this.mScrWidth = scrWidth;
		this.mScrHeight = scrHeight;
		
		this.mShow = false;
		this.AREA_WIDTH = mScrWidth - (marginBackground.getLeft() + marginBackground.getRight());

		mArrTableRow = new ArrayList<TableRow>();
		
		mArrList = new ArrayList<MenuItem>();
		mArrList.add(new MenuItem(BLANK_ITEM,false));
		mArrList.add(new MenuItem(BLANK_ITEM,false));
		mArrList.add(new MenuItem(BLANK_ITEM,true));
		mArrList.add(new MenuItem("경부선",true));
		mArrList.add(new MenuItem("남해선",true));
		mArrList.add(new MenuItem("88올림픽선",true));
		mArrList.add(new MenuItem("고창담양선",true));
		mArrList.add(new MenuItem("서해안선",true));
		mArrList.add(new MenuItem("울산선",true));
		mArrList.add(new MenuItem("익산포항선",true));
		mArrList.add(new MenuItem("호남선(논산천안선)",true));
		mArrList.add(new MenuItem("중부선(대전통영선)",true));
		mArrList.add(new MenuItem("제2중부선",true));
		mArrList.add(new MenuItem("평택충주선",true));
		mArrList.add(new MenuItem("중부내륙선",true));
		mArrList.add(new MenuItem("영동선",true));
		mArrList.add(new MenuItem("중앙선",true));
		mArrList.add(new MenuItem("동해선",true));
		mArrList.add(new MenuItem("서울외곽선",true));
		mArrList.add(new MenuItem("마산외곽선",true));
		mArrList.add(new MenuItem("남해제2지선",true));
		mArrList.add(new MenuItem("제2경인선",true));
		mArrList.add(new MenuItem("경인선",true));
		mArrList.add(new MenuItem("인천국제공항선",true));
		mArrList.add(new MenuItem("호남선지선",true));
		mArrList.add(new MenuItem("대전남부순환선",true));
		mArrList.add(new MenuItem("구마선",true));
		mArrList.add(new MenuItem("중앙선지선",true));
		mArrList.add(new MenuItem(BLANK_ITEM,false));
		mArrList.add(new MenuItem(BLANK_ITEM,false));
		mArrList.add(new MenuItem(BLANK_ITEM,false));
		
		drawLayout();
		this.setVisibility(View.GONE);

	}
	
	private class MenuItem {
		
		private String itemTitle;
		private boolean bottomSeparator;
		
		public MenuItem(String itemTitle, boolean bottomSeparator) {
			this.itemTitle = itemTitle;
			this.bottomSeparator = bottomSeparator;
		}
		
		public String getItemTitle() {
			return this.itemTitle;
		}
		
		private boolean getBottomSeparator() {
			return this.bottomSeparator;
		}
		
	}

	public void show() {
		
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

		this.mShow = true;

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

		// 터치 동작이 GestureListener 의 터치 동작과 중첩되므로,
		// 오동작 방지를 위해 좌표값 초기화를 약간 지연 시킴.
		
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
		
		params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, AREA_HEIGHT - 20);
		params.topMargin   = marginBackground.getTop()   + 10;
		params.leftMargin  = marginBackground.getLeft()  + 10;
		params.rightMargin = marginBackground.getRight() + 10;
		
		RelativeLayout listArea = new RelativeLayout(mContext);
		listArea.setBackgroundColor(Color.TRANSPARENT);
		listArea.setLayoutParams(params);
		mainLayout.addView(listArea);
		
		TableLayout tableLayout = new TableLayout(mContext);
		tableLayout.setStretchAllColumns(true);
		
		int currIdx = 0;
		TableRow tableRow;
		
		for (MenuItem menuItem : mArrList) {

			String sTitle = menuItem.getItemTitle();
			sTitle = sTitle.equals(BLANK_ITEM) ? "" : sTitle;
			
			tableRow = new TableRow(mContext);
			tableRow.addView(makeRowTitle(sTitle, NOTHING, Color.TRANSPARENT));
			tableRow.setOnClickListener(new ItemClickListener(tableRow,currIdx));
			tableLayout.addView(tableRow);
			
			if (menuItem.getBottomSeparator()) {
				tableLayout.addView(makeSeparator(NOTHING,1));
			} else {
				tableLayout.addView(makeSeparator(Color.TRANSPARENT,1));
			}
			
			mArrTableRow.add(tableRow);
			currIdx++;

		}

		mListScroll = new ListScroll(mContext);
		mListScroll.addView(tableLayout);
		mListScroll.setHorizontalScrollBarEnabled(false);
		mListScroll.setVerticalScrollBarEnabled(false);
		mListScroll.setFadingEdgeLength(0);
		listArea.addView(mListScroll);

		addView(mainLayout);

	}
	
	private class ListScroll extends ScrollView {
		
		private final String TAG = this.getClass().getSimpleName();
		private GestureDetector mGestureDetector;
		
		public ListScroll(Context context) {
			super(context);
			mGestureDetector = new GestureDetector(new ListGestureListener());
			setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					int action = event.getAction();
					if (mGestureDetector.onTouchEvent(event)) {
						return true;
					} else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
						
						int currScrollPos = mListScroll.getScrollY();
						int itemPos = (int)(currScrollPos / mItemHeight);
						float midPoint = mItemHeight / 2.0f;
						float diffItem = currScrollPos % mItemHeight;
						
						if (diffItem >= midPoint) {
							itemPos++;
						}
						
						smoothScrollTo(0, (int)(itemPos*mItemHeight));
						
						for (TableRow tableRow : mArrTableRow) {
							tableRow.setBackgroundColor(Color.TRANSPARENT);
						}
						
						mArrTableRow.get(itemPos+3).setBackgroundColor(Color.argb(128, 51, 102, 153));
						
						return true;
						
					} else {
						return false;
					}
				}
			});
		}

		private class ListGestureListener extends GestureDetector.SimpleOnGestureListener {

			private final String TAG = this.getClass().getSimpleName();
			
			private ArrayList<Integer> mArrDistX;
			private ArrayList<Integer> mArrDistY;
			
			public ListGestureListener() {
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

				return super.onFling(e1, e2, velocityX, velocityY);
				
			}

		}
		
	}
	
	private class ItemClickListener implements View.OnClickListener {
		
		private TableRow mTableRow;
		private int mCurrIdx;
		
		public ItemClickListener(TableRow tableRow, int currIdx) {
			this.mTableRow = tableRow;
			this.mCurrIdx = currIdx;
		}
		
		@Override
		public void onClick(View v) {
			
			if (!mArrList.get(mCurrIdx).getItemTitle().equals(BLANK_ITEM)) {
				
				for (TableRow tableRow : mArrTableRow) {
					tableRow.setBackgroundColor(Color.TRANSPARENT);
				}
				mTableRow.setBackgroundColor(Color.argb(128, 51, 102, 153));
				mListScroll.smoothScrollTo(0, (int)((mCurrIdx-LIST_BLANK_COUNT_TOP)*mItemHeight));
				
			}
			
		}
		
	}

	private TextView makeRowTitle(String sValue, int iGravity, int bgColor) {
		
		TextView textView = new TextView(mContext);
		textView.setPadding(5, 13, 0, 13);  // L,T,R,B
		textView.setSingleLine();
		textView.setTextSize(13.0f);
		textView.setTypeface(BaseData.FONT_FACE);
		textView.setText(Html.fromHtml(sValue));

		if (bgColor != NOTHING) {
			textView.setBackgroundColor(bgColor);
		} else {
			textView.setBackgroundColor(Color.argb(128, 80, 120, 160));
		}

		if (iGravity != NOTHING) {
			textView.setGravity(iGravity);
		} else {
			textView.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
		}
		
		return textView;
		
	}

	private View makeSeparator(int color, int height) {

		View view = new View(mContext);
		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, height));
		
		if (color != NOTHING) {
			view.setBackgroundColor(color);
		} else {
			view.setBackgroundColor(Color.WHITE);
		}
		
		return view;

	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		Point pointSrt = new Point();
		Point pointEnd = new Point();
		Paint paint = new Paint();
		RectF rect = null;
		
		// 전체 영역 백그라운드
		
		paint.reset();
		paint.setColor(Color.BLACK);
		paint.setAlpha((int)(255*0.7));
		paint.setAntiAlias(true);

		pointSrt.x = marginBackground.getLeft();
		pointSrt.y = marginBackground.getTop();
		
		pointEnd.x = marginBackground.getLeft() + AREA_WIDTH;
		pointEnd.y = marginBackground.getTop() + AREA_HEIGHT;
		
		rect = new RectF(pointSrt.x, pointSrt.y, pointEnd.x, pointEnd.y);
		canvas.drawRoundRect(rect, 10.0f, 10.0f, paint);

		AREA_SRT.set(pointSrt.x, pointSrt.y);
		AREA_END.set(pointEnd.x, pointEnd.y);	

		super.dispatchDraw(canvas);
		
	}

	private class AreaMargin {
		
		int top, bottom;
		int left, right;
		
		public AreaMargin(int left, int top, int right, int bottom) {
			
			this.top = top;
			this.bottom = bottom;
			this.left = left;
			this.right = right;
			
		}
		
		public int getTop()    { return top; }
		public int getBottom() { return bottom; }
		public int getLeft()   { return left; }
		public int getRight()  { return right; }
		
	}
	
}
