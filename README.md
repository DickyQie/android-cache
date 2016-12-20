# 登录圆形头像之网络加载与缓存到本地

 <p>Android开发中常常有用户头像显示，似乎大多数都是圆形显示，如果每次加载网络头像，会频繁的请求网络，所以本文主要说的是登录时的头像网络加载和缓存到本地，以便于下次加载时直接从本地获取即可。</p> 
<p>效果图</p> 
<p>&nbsp; &nbsp; &nbsp;&nbsp;<img alt="" src="https://static.oschina.net/uploads/space/2016/1220/091756_k4Qh_2945455.gif"></p> 
<p>自定义控件实现圆形头像显示请看，&nbsp;<a href="https://my.oschina.net/zhangqie/blog/794363" target="_blank" rel="nofollow">Android自定义ImageView实现图片圆形 ，椭圆和矩形圆角显示</a>&nbsp;这篇博客即可。</p> 
<p>代码：</p> 
<pre><code class="language-java">/*****
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
</code></pre> 
<p>不要忘记在AndroidManifest.xml加权限哦！</p> 
<pre><code class="language-html">    &lt;uses-permission android:name="android.permission.INTERNET"&gt;&lt;/uses-permission&gt;
	&lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/&gt;
	&lt;uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/&gt;
	&lt;uses-permission android:name="android.permission.READ_PHONE_STATE"&gt;&lt;/uses-permission&gt;
	&lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"&gt;&lt;/uses-permission&gt;
	&lt;uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"&gt;&lt;/uses-permission&gt;
	&lt;uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"&gt;&lt;/uses-permission&gt;
</code></pre> 
<p>&nbsp;</p> 
<span id="OSC_h2_1"></span>
<h2><span style="color:#B22222">由于代码太多，完整代码未给出，源码直接下载即可</span></h2> 
