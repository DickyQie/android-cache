package com.example.cachedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/****
 * 
 * 
 * 缓存
 * 
 * @author Administrator
 *
 */
public class MainActivity extends Activity {

	private ListView mListView;
	String[] arrayDate={"String Cache","JsonObject Cache","JsonArray Cache","Bitmap Cache","Media Cache","Drawable Cache","Object Cache"};
	Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
		mListView=(ListView) findViewById(R.id.mListView);
		ArrayAdapter<String>  adapter=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,arrayDate);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showIntent(position);
			}
		});
	}
    
    private void showIntent(int i)
    {
    	switch (i) {
		case 0:
			startActivity(new Intent().setClass(this, AStringActivity.class));
			break;
		case 1:
			startActivity(new Intent().setClass(this, BJsonObjectActivity.class));
			break;
		case 2:
			startActivity(new Intent().setClass(this, CJsonArrayActivity.class));
			break;
		case 3:
			startActivity(new Intent().setClass(this, DBitmapActivity.class));
			break;
		case 4:
			startActivity(new Intent().setClass(this, EMediaActivity.class));
			break;
		case 5:
			startActivity(new Intent().setClass(this, FDrawableActivity.class));
			break;
		case 6:
			startActivity(new Intent().setClass(this, HObjectActivity.class));
			break;
		default:
			break;
		}
    }
}
