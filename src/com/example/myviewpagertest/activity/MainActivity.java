package com.example.myviewpagertest.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.myviewpagertest.R;
import com.example.myviewpagertest.adapter.ViewPagerAdapter;
import com.example.myviewpagertest.widget.ViewPager;
import com.example.myviewpagertest.widget.ViewPager.OnPageChangeListener;
//import com.example.myviewpagertest.widget.MyViewPager;
//import com.example.myviewpagertest.widget.MyViewPager.OnPageChangeListener;
import com.example.myviewpagertest.widget.PicBar;

import android.os.Bundle;
import android.app.Activity;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private PicBar picBar;//自定义滑动条
	private ViewPager mViewPager;
	//private MyViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;//自定义设配器
	private List<String> mImageList;//图片列表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void init(){
		mViewPager = (ViewPager)findViewById(R.id.myviewpager);
		//mViewPager = (MyViewPager)findViewById(R.id.myviewpager);
		
		String[] url = {"http://d.hiphotos.baidu.com/album/w%3D2048%3Bq%3D75/sign=6099232c0823dd542173a068e53188af/8601a18b87d6277f35a56d7029381f30e824fcc7.jpg",
				"http://a.hiphotos.baidu.com/album/w%3D2048/sign=e3098965aa64034f0fcdc5069bfb7b31/060828381f30e924106bdb894d086e061c95f7bf.jpg",
				"http://d.hiphotos.baidu.com/pic/w%3D230/sign=9e223dc221a446237ecaa261a8227246/faf2b2119313b07e398017b00dd7912397dd8c4d.jpg",
				"http://h.hiphotos.baidu.com/album/w%3D2048/sign=8a4b25c69358d109c4e3aeb2e560cdbf/b812c8fcc3cec3fdab1ce1cdd788d43f87942763.jpg",
				"http://c.hiphotos.baidu.com/album/w%3D2048/sign=81ad7acef31fbe091c5ec4145f580d33/64380cd7912397dd5b22ddbc5882b2b7d0a2872b.jpg",
				"http://a.hiphotos.baidu.com/album/w%3D2048/sign=9b653aa3d058ccbf1bbcb23a2de0bd3e/fd039245d688d43faf2f02e37c1ed21b0ef43b18.jpg"};
		mImageList = new ArrayList<String>();
		for (int j = 0; j < url.length; j++) {
			mImageList.add(url[j]);
		}
		
    	picBar = (PicBar) findViewById(R.id.picbar);
    	picBar.setNumPages(mImageList.size());
        picBar.setPosition(0);
        
    	mViewPagerAdapter = new ViewPagerAdapter(this);
    	mViewPager.setAdapter(mViewPagerAdapter);
    	mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 >= 0 && arg0<=(mImageList.size()-1)) {
                    picBar.setPosition(arg0);
                }
			}
    		 
            
        
         });
    }

}
