package com.example.avatarcache;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

/*****
 * 
 * 圆形头像+HTTP网络加载头像并缓存到本地
 * 
 * 
 * @author zq
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private ImageView mImageView, mImageView2;
	String url = " http://avatar.csdn.net/8/6/0/1_dickyqie.jpg";
	private String mFileName;
	private String mSaveMessage;
	Util util = new Util();
	private Bitmap mBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mImageView = (ImageView) findViewById(R.id.personal_image);
		mImageView2 = (ImageView) findViewById(R.id.personal_image2);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			new Thread(connectNet).start();
			break;
		case R.id.button2:
			Bitmap ben = BitmapFactory.decodeFile(Util.ALBUM_PATH + mFileName);
			mImageView2.setImageBitmap(ben);
			break;
		default:
			break;
		}
	}

	private Runnable saveFileRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				util.saveFile(mBitmap, mFileName);
				mSaveMessage = "图片保存成功！";
			} catch (IOException e) {
				mSaveMessage = "图片保存失败！";
				e.printStackTrace();
			}
			messageHandler.sendMessage(messageHandler.obtainMessage());
		}

	};

	private Handler messageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Toast.makeText(MainActivity.this, mSaveMessage, 1).show();

		}
	};

	/*
	 * 连接网络 由于在4.0中不允许在主线程中访问网络，所以需要在子线程中访问
	 */
	private Runnable connectNet = new Runnable() {
		@Override
		public void run() {
			try {
				String filePath = url;
				mFileName = "test.jpg";

				// 以下是取得图片的两种方法
				// 方法1：取得的是byte数组, 从byte数组生成bitmap
				byte[] data = util.getImage(filePath);
				if (data != null) {
					mBitmap = BitmapFactory.decodeByteArray(data, 0,
							data.length);
				} else {
					Toast.makeText(MainActivity.this, "Image error!", 1).show();
				}

				// ******** 方法2：取得的是InputStream，直接从InputStream生成bitmap
				// mBitmap =
				// BitmapFactory.decodeStream(util.getImageStream(filePath));
				// 发送消息，通知handler在主线程中更新UI
				connectHanlder.sendEmptyMessage(0);
			} catch (Exception e) {
				Toast.makeText(MainActivity.this, "无法链接网络！", 1).show();
				e.printStackTrace();
			}

		}

	};

	private Handler connectHanlder = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 更新UI，显示图片
			if (mBitmap != null) {
				mImageView.setImageBitmap(mBitmap);// display image
				new Thread(saveFileRunnable).start();
			}
		}
	};

}
