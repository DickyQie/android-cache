package com.example.cachedemo;

import util.ACache;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * Bitmap缓存
 * 
 */
public class DBitmapActivity extends Activity {

	private ImageView mIv_bitmap_res;

	private ACache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d_bitmap);
		// 初始化控件
		initView();

		mCache = ACache.get(this);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		mIv_bitmap_res = (ImageView) findViewById(R.id.iv_bitmap_res);
	}

	/**
	 * 点击save事件
	 * 
	 * @param v
	 */
	public void save(View v) {
		Resources res = getResources();
		Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.img_test);
		mCache.put("testBitmap", bitmap);
	}

	/**
	 * 点击read事件
	 * 
	 * @param v
	 */
	public void read(View v) {
		Bitmap testBitmap = mCache.getAsBitmap("testBitmap");
		if (testBitmap == null) {
			Toast.makeText(this, "图片缓存缓存为空", Toast.LENGTH_SHORT)
					.show();
			mIv_bitmap_res.setImageBitmap(null);
			return;
		}
		mIv_bitmap_res.setImageBitmap(testBitmap);
	}

	/**
	 * 点击clear事件
	 * 
	 * @param v
	 */
	public void clear(View v) {
		mCache.remove("testBitmap");
	}
}
