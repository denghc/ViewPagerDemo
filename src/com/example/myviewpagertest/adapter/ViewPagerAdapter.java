package com.example.myviewpagertest.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.myviewpagertest.widget.ViewPager;
import com.example.myviewpagertest.widget.ViewPagerItemView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter{
	
	private Context mContext;
	private List<String> mImageList;//Í¼Æ¬ÁÐ±í
	//private LayoutInflater mInflater;
	private HashMap<Integer, ViewPagerItemView> mHashMap;
	
	@SuppressLint("UseSparseArrays")
	public ViewPagerAdapter(Context context){
		this.mContext = context;
		//mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String[] url = {"http://d.hiphotos.baidu.com/album/w%3D2048%3Bq%3D75/sign=6099232c0823dd542173a068e53188af/8601a18b87d6277f35a56d7029381f30e824fcc7.jpg",
				"http://a.hiphotos.baidu.com/album/w%3D2048/sign=9b653aa3d058ccbf1bbcb23a2de0bd3e/fd039245d688d43faf2f02e37c1ed21b0ef43b18.jpg",
				"http://d.hiphotos.baidu.com/pic/w%3D230/sign=9e223dc221a446237ecaa261a8227246/faf2b2119313b07e398017b00dd7912397dd8c4d.jpg",
				"http://h.hiphotos.baidu.com/album/w%3D2048/sign=8a4b25c69358d109c4e3aeb2e560cdbf/b812c8fcc3cec3fdab1ce1cdd788d43f87942763.jpg",
				"http://c.hiphotos.baidu.com/album/w%3D2048/sign=81ad7acef31fbe091c5ec4145f580d33/64380cd7912397dd5b22ddbc5882b2b7d0a2872b.jpg",
				"http://a.hiphotos.baidu.com/album/w%3D2048/sign=9b653aa3d058ccbf1bbcb23a2de0bd3e/fd039245d688d43faf2f02e37c1ed21b0ef43b18.jpg"};
		mImageList = new ArrayList<String>();
		for (int j = 0; j < url.length; j++) {
			mImageList.add(url[j]);
		}
		mHashMap = new HashMap<Integer, ViewPagerItemView>();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImageList.size();
	}

	@Override
	public void startUpdate(View container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		ViewPagerItemView itemView;
		if(mHashMap.containsKey(position)){
			itemView = mHashMap.get(position);
			itemView.reload();
		}else{
			itemView = new ViewPagerItemView(mContext);
			itemView.setData(mImageList.get(position));
			mHashMap.put(position, itemView);
			((ViewPager) container).addView(itemView);
			//((MyViewPager) container).addView(itemView);
		}
		
		return itemView;
    }

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		ViewPagerItemView itemView = (ViewPagerItemView)object;
		itemView.recycle();
	}

	@Override
	public void finishUpdate(View container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == object;
	}

//	@Override
//	public Parcelable saveState() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void restoreState(Parcelable state, ClassLoader loader) {
//		// TODO Auto-generated method stub
//		
//	}

}
