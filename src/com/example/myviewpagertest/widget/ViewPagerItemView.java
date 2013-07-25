package com.example.myviewpagertest.widget;

import com.example.myviewpagertest.R;
import com.example.myviewpagertest.util.ImageUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ViewPagerItemView extends FrameLayout{
	
	private Context mContext;
	private ImageView mAlbumImageView;
	private Bitmap mBitmap;
	private String mImageURL;
	public ViewPagerItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		setupViews();
	}
	public ViewPagerItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setupViews();	
	}
	private void setupViews(){
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.img_l, null);
		
		mAlbumImageView = (ImageView)view.findViewById(R.id.imageview);
		addView(view);
	}
	
	public void setData(String image_url){
		this.mImageURL = image_url;
		ImageUtil.getInstance().setImage((Activity) mContext, null, mAlbumImageView, image_url);
	}
	
	public void recycle(){
		mAlbumImageView.setImageBitmap(null);
		if ((this.mBitmap == null) || (this.mBitmap.isRecycled()))
			return;
		this.mBitmap.recycle();
		this.mBitmap = null;
	}
	
	public void reload(){
		ImageUtil.getInstance().setImage((Activity) mContext, null, mAlbumImageView, mImageURL);
	}
}
