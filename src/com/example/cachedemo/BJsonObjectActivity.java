package com.example.cachedemo;

import org.json.JSONException;
import org.json.JSONObject;

import util.ACache;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * JsonObject缓存
 * 
 */
public class BJsonObjectActivity extends Activity {

	private TextView mTv_jsonobject_original, mTv_jsonobject_res;
	private JSONObject jsonObject;
	private ACache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b_jsonobject);
		// 初始化控件
		initView();
		mCache = ACache.get(this);
		jsonObject = new JSONObject();
		try {
			jsonObject.put("name", "Yoson");
			jsonObject.put("age", 18);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mTv_jsonobject_original.setText(jsonObject.toString());
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		mTv_jsonobject_original = (TextView) findViewById(R.id.tv_jsonobject_original);
		mTv_jsonobject_res = (TextView) findViewById(R.id.tv_jsonobject_res);
	}

	/**
	 * 点击save事件
	 * 
	 * @param v
	 */
	public void save(View v) {
		mCache.put("testJsonObject", jsonObject);
	}

	/**
	 * 点击read事件
	 * 
	 * @param v
	 */
	public void read(View v) {
		JSONObject testJsonObject = mCache.getAsJSONObject("testJsonObject");
		if (testJsonObject == null) {
			Toast.makeText(this, "缓存数据为空",
					Toast.LENGTH_SHORT).show();
			mTv_jsonobject_res.setText(null);
			return;
		}
		mTv_jsonobject_res.setText(testJsonObject.toString());
	}

	/**
	 * 点击clear事件
	 * 
	 * @param v
	 */
	public void clear(View v) {
		mCache.remove("testJsonObject");
	}
}
