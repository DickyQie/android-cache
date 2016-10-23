# 登录圆形头像之网络加载与缓存到本地

<div id="article_content" class="article_content">

<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px; font-family:Arial; font-size:14px">
<span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><a target="_blank" href="http://lib.csdn.net/base/15" class="replace_word" title="Android知识库" target="_blank" style="color:rgb(223,52,52); text-decoration:none; font-weight:bold">Android</a>开发中常常有用户头像显示，&#20284;乎大多数都是圆形显示，如果每次加载网络头像，会频繁的请求网络，所以本文主要说的是登录时的头像网络加载和缓存到本地，以便于下次加载时直接从本地获取即可。</span></p>
<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px; font-family:Arial; font-size:14px">
<span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><br>
</span></p>
<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px; font-family:Arial; font-size:14px">
<span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px">效果图</span></p>
<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px; font-size:14px">
<span style="font-family:Verdana,Arial,Helvetica,sans-serif">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src="http://img.blog.csdn.net/20161023150715625?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" alt=""></span></p>
<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px; font-size:14px">
<span style="font-family:Verdana,Arial,Helvetica,sans-serif"><br>
</span></p>
<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px">
<span style="font-family:Verdana,Arial,Helvetica,sans-serif"><span style="font-size:14px">自定义控件实现圆形头像显示请看，</span><span style="font-family:&quot;Microsoft YaHei&quot;"><span style="font-size:20px">&nbsp;</span><a target="_blank" href="http://blog.csdn.net/dickyqie/article/details/52438920" style="color:rgb(153,0,0); font-size:20px">Android自定义ImageView实现图片圆形
 ，椭圆和矩形圆角显示</a><span style="color:rgb(153,0,0); font-size:20px">&nbsp;</span><span style="font-size:14px">这篇博客即可。</span></span></span></p>
<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px">
<span style="font-family:Verdana,Arial,Helvetica,sans-serif"><span style="font-family:&quot;Microsoft YaHei&quot;"><span style="font-size:14px"><br>
</span></span></span></p>
<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px">
<span style="font-family:Verdana,Arial,Helvetica,sans-serif"><span style="font-family:&quot;Microsoft YaHei&quot;"><span style="font-size:14px">代码：</span></span></span></p>
<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px">
<span style="font-family:Verdana,Arial,Helvetica,sans-serif"><span style="font-family:&quot;Microsoft YaHei&quot;"><span style="font-size:14px"></span></span></span></p>
<p><strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
class&nbsp;</span></strong>MainActivity <strong><span style="color:#7F0055">extends</span></strong> Activity
<strong><span style="color:#7F0055">implements&nbsp;</span></strong>OnClickListener {</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span></strong> ImageView <span style="color:#0000C0">
mImageView</span>, <span style="color:#0000C0">mImageView2</span>;</p>
<p>&nbsp;&nbsp; <span style="font-family:&quot;Microsoft YaHei&quot;; font-size:14px"><strong><span style="color:rgb(127,0,85)">private</span></strong></span><span style="font-family:&quot;Microsoft YaHei&quot;; font-size:14px">&nbsp;</span>String
<span style="color:#0000C0">url</span> = <span style="color:#2A00FF">&quot;http://avatar.csdn.net/8/6/0/1_dickyqie.jpg&quot;</span>;</p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span></strong> String <span style="color:#0000C0">
mFileName</span>;</p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span></strong> String <span style="color:#0000C0">
mSaveMessage</span>;</p>
<p>&nbsp;&nbsp; Util <span style="color:#0000C0">util</span> = <strong><span style="color:#7F0055">new</span></strong> Util();</p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span></strong> Bitmap <span style="color:#0000C0">
mBitmap</span>;</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; <span style="color:#646464">@<span style="background:silver">Override</span></span></p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">protected</span> <span style="color:#7F0055">
void&nbsp;</span></strong>onCreate(Bundle savedInstanceState) {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">super</span></strong>.onCreate(savedInstanceState);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; setContentView(R.layout.<em><span style="color:#0000C0">activity_main</span></em>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; initView();</p>
<p>&nbsp;&nbsp; }</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span> <span style="color:#7F0055">
void&nbsp;</span></strong>initView() {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><strong><span style="color:#7F9FBF">TODO</span></strong><span style="color:#3F7F5F">Auto-generated method stub</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">mImageView</span> = (ImageView)findViewById(R.id.<em><span style="color:#0000C0">personal_image</span></em>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">mImageView2</span> = (ImageView)findViewById(R.id.<em><span style="color:#0000C0">personal_image2</span></em>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findViewById(R.id.<em><span style="color:#0000C0">button1</span></em>).setOnClickListener(<strong><span style="color:#7F0055">this</span></strong>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findViewById(R.id.<em><span style="color:#0000C0">button2</span></em>).setOnClickListener(<strong><span style="color:#7F0055">this</span></strong>);</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; }</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; <span style="color:#646464">@<span style="background:silver">Override</span></span></p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
void&nbsp;</span></strong>onClick(View v) {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><strong><span style="color:#7F9FBF">TODO</span></strong><span style="color:#3F7F5F">Auto-generated method stub</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">switch</span></strong>(v.getId()) {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">case</span></strong> R.id.<em><span style="color:#0000C0">button1</span></em>:</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">new</span></strong> Thread(<span style="color:#0000C0">connectNet</span>).start();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">break</span></strong>;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">case</span></strong> R.id.<em><span style="color:#0000C0">button2</span></em>:</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Bitmap ben = BitmapFactory.<em>decodeFile</em>(Util.<em><span style="color:#0000C0">ALBUM_PATH</span></em> &#43;
<span style="color:#0000C0">mFileName</span>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">mImageView2</span>.setImageBitmap(ben);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">break</span></strong>;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">default</span></strong>:</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">break</span></strong>;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp; }</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span></strong> Runnable <span style="color:#0000C0">
saveFileRunnable</span> = <strong><span style="color:#7F0055">new&nbsp;</span></strong>Runnable() {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@<span style="background:silver">Override</span></span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
void</span></strong> run() {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">try</span></strong> {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">util</span>.saveFile(<span style="color:#0000C0">mBitmap</span>,
<span style="color:#0000C0">mFileName</span>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">mSaveMessage</span> = <span style="color:#2A00FF">
&quot;</span><span style="color:#2A00FF">图片保存成功！</span><span style="color:#2A00FF">&quot;</span>;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; } <strong><span style="color:#7F0055">catch</span></strong>(IOException e) {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">mSaveMessage</span> = <span style="color:#2A00FF">
&quot;</span><span style="color:#2A00FF">图片保存失败！</span><span style="color:#2A00FF">&quot;</span>;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e.printStackTrace();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">messageHandler</span>.sendMessage(<span style="color:#0000C0">messageHandler</span>.obtainMessage());</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; };</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span></strong> Handler <span style="color:#0000C0">
messageHandler</span> = <strong><span style="color:#7F0055">new</span></strong> <u>
Handler</u>(){</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@<span style="background:silver">Override</span></span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
void</span></strong>handleMessage(Message msg) {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Toast.<em>makeText</em>(MainActivity.<strong><span style="color:#7F0055">this</span></strong>,
<span style="color:#0000C0">mSaveMessage</span>, <u>1</u>).show();</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp; };</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; <span style="color:#3F7F5F">/*</span></p>
<p><span style="color:#3F7F5F">&nbsp;&nbsp; &nbsp;* </span><span style="color:#3F7F5F">连接网络</span><span style="color:#3F7F5F">由于在</span><span style="color:#3F7F5F">4.0</span><span style="color:#3F7F5F">中不允许在主线程中访问网络，所以需要在子线程中访问</span></p>
<p><span style="color:#3F7F5F">&nbsp;&nbsp; &nbsp;*/</span></p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span></strong> Runnable <span style="color:#0000C0">
connectNet</span> = <strong><span style="color:#7F0055">new&nbsp;</span></strong>Runnable() {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@<span style="background:silver">Override</span></span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
void</span></strong> run() {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">try</span></strong> {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String filePath = <span style="color:#0000C0">url</span>;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">mFileName</span> = <span style="color:#2A00FF">
&quot;test.jpg&quot;</span>;</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">以下是取得图片的两种方法</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">方法</span><span style="color:#3F7F5F">1</span><span style="color:#3F7F5F">：取得的是</span><span style="color:#3F7F5F">byte</span><span style="color:#3F7F5F">数组</span><span style="color:#3F7F5F">,
</span><span style="color:#3F7F5F">从</span><span style="color:#3F7F5F">byte</span><span style="color:#3F7F5F">数组生成</span><span style="color:#3F7F5F">bitmap</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">byte</span></strong>[] data = <span style="color:#0000C0">
util</span>.getImage(filePath);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">if</span></strong> (data != <strong>
<span style="color:#7F0055">null</span></strong>) {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">mBitmap</span> =BitmapFactory.<em>decodeByteArray</em>(data, 0,</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; data.<span style="color:#0000C0">length</span>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; } <strong><span style="color:#7F0055">else</span></strong> {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Toast.<em>makeText</em>(MainActivity.<strong><span style="color:#7F0055">this</span></strong>,
<span style="color:#2A00FF">&quot;Imageerror!&quot;</span>, <u>1</u>).show();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// ******** </span><span style="color:#3F7F5F">方法</span><span style="color:#3F7F5F">2</span><span style="color:#3F7F5F">：取得的是</span><span style="color:#3F7F5F">InputStream</span><span style="color:#3F7F5F">，直接从</span><span style="color:#3F7F5F">InputStream</span><span style="color:#3F7F5F">生成</span><span style="color:#3F7F5F">bitmap</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// mBitmap =</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">//BitmapFactory.decodeStream(util.getImageStream(filePath));</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">发送消息，通知</span><span style="color:#3F7F5F">handler</span><span style="color:#3F7F5F">在主线程中更新</span><span style="color:#3F7F5F">UI</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">connectHanlder</span>.sendEmptyMessage(0);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; } <strong><span style="color:#7F0055">catch</span></strong>(Exception e) {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Toast.<em>makeText</em>(MainActivity.<strong><span style="color:#7F0055">this</span></strong>,
<span style="color:#2A00FF">&quot;</span><span style="color:#2A00FF">无法链接网络！</span><span style="color:#2A00FF">&quot;</span>,
<u>1</u>).show();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e.printStackTrace();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; };</p>
<p>&nbsp;</p>
<p>&nbsp;&nbsp; <strong><span style="color:#7F0055">private</span></strong> Handler <span style="color:#0000C0">
connectHanlder</span> = <strong><span style="color:#7F0055">new</span></strong> <u>
Handler</u>(){</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@<span style="background:silver">Override</span></span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
void&nbsp;</span></strong>handleMessage(Message msg) {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">更新</span><span style="color:#3F7F5F">UI</span><span style="color:#3F7F5F">，显示图片</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">if</span></strong> (<span style="color:#0000C0">mBitmap</span> !=
<strong><span style="color:#7F0055">null</span></strong>) {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">mImageView</span>.setImageBitmap(<span style="color:#0000C0">mBitmap</span>);<span style="color:#3F7F5F">//display image</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">new</span></strong> Thread(<span style="color:#0000C0">saveFileRunnable</span>).start();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp; };</p>
<p><br>
</p>
<p>}</p>
<p><span style="color:rgb(51,51,51); font-family:&quot;microsoft yahei&quot;; font-size:24px; font-weight:bold">不要忘记</span><span style="color:rgb(51,51,51); font-family:&quot;microsoft yahei&quot;; font-size:24px"><strong>在</strong></span><span style="font-family:&quot;microsoft yahei&quot;; font-size:24px; color:rgb(51,102,255)"><strong>AndroidManifest.xml</strong></span><span style="color:rgb(51,51,51); font-family:&quot;microsoft yahei&quot;; font-size:24px; font-weight:bold">加权限哦！</span></p>
<p><span style="color:rgb(51,51,51); font-family:&quot;microsoft yahei&quot;; font-size:24px; font-weight:bold"><br>
</span></p>
<p><span style="color:rgb(51,51,51); font-family:&quot;microsoft yahei&quot;; font-size:24px; font-weight:bold"></span></p>
<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px; font-size:14px; font-family:Arial">
<strong><span style="font-size:18px">由于代码太多，完整代码未给出，源码直接下载即可</span></strong></p>
<p style="margin-top:0px; margin-bottom:0px; padding-top:0px; padding-bottom:0px; font-size:14px; font-family:Arial">
</div>
