package com.example.myviewpagertest.widget;

import com.example.myviewpagertest.R;
import com.example.myviewpagertest.R.dimen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PicBar extends View{

	private Context context;
	
	private float bitSpace = 0;
	private int subHeight = 0;
	
	private Paint mPaint;
	private Paint mHpaint;
	
	private float maxWidth;
	private float maxSubWidth;
	
	private float subWidh = 0;
	private float viewWidth = 0;
	
	private int numPages, currentPage, position;
	
	public PicBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}
	
	public PicBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}
	
	public void init(){
		maxSubWidth = getResources().getDimensionPixelSize(
				R.dimen.picbar_sub_max_width);
		bitSpace = getResources().getDimensionPixelSize(R.dimen.picbar_space);
		subHeight = getResources().getDimensionPixelSize(R.dimen.picbar_height);
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(0x7FFFFFFF);
		mHpaint = new Paint(mPaint);
		mHpaint.setColor(Color.WHITE);
	}
	
	public void setMaxWidth(float width) {
		maxWidth = width;
	}
	
	// 长度不超过最大值
	private boolean widthNotLong() {
		return maxSubWidth * numPages + bitSpace * (numPages - 1) <= maxWidth;
	}
	
	/**
	 * 
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		final int size = numPages;
		if (size <= 1) {
			return;
		}
		float offset = 0;
		
		if (widthNotLong() && maxWidth != 0) {
			subWidh = maxSubWidth;
			viewWidth = subWidh * numPages + bitSpace * (numPages - 1);
			offset = (getWidth() - viewWidth) / 2;
		} else {
			viewWidth = getWidth();
			subWidh = (viewWidth - ((float) numPages - 1) * bitSpace)
					/ (float) numPages;
		}
		for (int i = 0; i < size; i++) {
			Paint paint = null;
			if (position != i) {
				paint = mPaint;
			} else {
				paint = mHpaint;
			}
			float left = offset + i * subWidh + i * bitSpace;
			float right = left + subWidh;
			canvas.drawRect(left, 0, right, subHeight, paint);
		}	
	}
	
	/**
	 * 
	 * @param position
	 *            can be -pageWidth to pageWidth*(numPages+1)
	 */
	public void setPosition(int position) {
		// if (this.position != position) {
		this.position = position;
		invalidate();
		// }
	}
	
	/**
	 * 
	 * @param numPages
	 *            must be positive number
	 */
	public void setNumPages(int numPages) {
		if (numPages <= 0) {
			throw new IllegalArgumentException("num must be positive");
		}
		this.numPages = numPages;
	}
	

}
