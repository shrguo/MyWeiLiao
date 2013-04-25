package com.terry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.terry.R;
import com.terry.R.drawable;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * GridView分页显示安装的应用程序
 * @author Yao.GUET
 * blog: http://blog.csdn.net/Yao_GUET
 * date: 2011-05-05
 */
public class AllAppList extends Activity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		menu.add(0, 1, 1, "壁纸");
		menu.add(0,2,1,"关于");
		return super.onCreateOptionsMenu(menu);
	}

	private static final String TAG = "ScrollLayoutTest";
	private ScrollLayout mScrollLayout;
	private static final float APP_PAGE_SIZE = 16.0f;
	private Context mContext;
    LinearLayout buttonlist;
    PopupWindow pw = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this;
		setContentView(R.layout.main);
		
		mScrollLayout = (ScrollLayout)findViewById(R.id.ScrollLayoutTest);
		buttonlist=(LinearLayout)findViewById(R.id.buttonlist);
		 registerIntentReceivers();
		initViews();
		InitDock();
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId()==1){
	      //  Intent intent=new Intent(AllAppList.this,wallper.class);
	        //startActivity(intent);
			Intent wallpper =new Intent(Intent.ACTION_SET_WALLPAPER);
			Intent xzq=Intent.createChooser(wallpper,"wapper");
			startActivity(xzq);
			}
		if(item.getItemId()==2){
			Intent intent=new Intent(this,about.class);
			startActivity(intent);
		}
		
		return super.onOptionsItemSelected(item);
	}



	/**
	 * 获取系统所有的应用程序，并根据APP_PAGE_SIZE生成相应的GridView页面
	 */
	public void initViews() {
	
		final PackageManager packageManager = getPackageManager();

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // get all apps 
        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        
        // the total pages
        final int PageCount = (int)Math.ceil(apps.size()/APP_PAGE_SIZE);
      
        Log.e(TAG, "size:"+apps.size()+" page:"+PageCount);
        for (int i=0; i<PageCount; i++) {
        	GridView appPage = new GridView(this);
        	// get the "i" page data
        	appPage.setAdapter(new AppAdapter(this, apps, i));
        	
        	appPage.setNumColumns(4);
        	appPage.setOnItemClickListener(listener);
        	appPage.setOnItemLongClickListener(new lis());
            
        	mScrollLayout.addView(appPage);
  
   
        }
       // TableLayout tb=(TableLayout)findViewById(R.id.icons);
     //  TableRow tr=new TableRow(this);
     // tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT,1));
      //tr.setGravity(Gravity.CENTER); 
  
   // tb.addView(tr);
        InitDock();
	}
	public void InitDock(){
	ArrayList<View> dock = new ArrayList<View>(); 	
	GridView docklist=(GridView)findViewById(R.id.iconlist);
   
	ImageView bu=new ImageView(this);
	bu.setPadding(0, 10, 0, 0);
	
	
	
      bu.setImageResource(R.drawable.mainicon_call);    
      bu.setOnClickListener(call);
          
      
      ImageView bu2=new ImageView(this);
        bu2.setImageResource(R.drawable.mainicon_contact);
        bu2.setPadding(0, 10, 0, 0);
        bu2.setOnClickListener(content);
     
        ImageView bu3=new ImageView(this);
        bu3.setImageResource(R.drawable.mainicon_sms);      
        bu3.setOnClickListener(sms);
        bu3.setPadding(0, 10, 0, 0);
     
      ImageView bu4=new ImageView(this);
            bu4.setImageResource(R.drawable.mainicon_browser); 
            bu4.setPadding(0, 10, 0, 0);
            bu4.setOnClickListener(browser);
           dock.add(bu);
           dock.add(bu3);
           dock.add(bu2);
           dock.add(bu4);
           docklist.setAdapter(new DockAdapter(dock));
	}
	
	/**
	 * gridView 的onItemLick响应事件
	 */
	public OnClickListener call=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:"));
			  // intent.setAction("Android.intent.action.CALL");
			   //intent.setData(Uri.parse("tel://"+ "15680019069"));//mobile为你要拨打的电话号码，模拟器中为模拟器编号也可

			   startActivity(intent);
			
		}
	};
	public OnClickListener sms=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent =new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"));
			startActivity(intent);
			
		}
	};
	private OnClickListener browser=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent =new Intent(Intent.ACTION_VIEW,Uri.parse("http://"));
			startActivity(intent);
			
		}
	};
	private OnClickListener content=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 Intent i = new Intent(Intent.ACTION_PICK);
			 i.setType("vnd.android.cursor.dir/phone");
			 startActivityForResult(i, 0); 

		}
	};
	public OnItemClickListener listener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			ResolveInfo appInfo = (ResolveInfo)parent.getItemAtPosition(position);
			Intent mainIntent = mContext.getPackageManager()
				.getLaunchIntentForPackage(appInfo.activityInfo.packageName);
			mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			try {
				// launcher the package
				mContext.startActivity(mainIntent);
			} catch (ActivityNotFoundException noFound) {
				Toast.makeText(mContext, "Package not found!", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	int y = 0;
	public class lis implements OnItemLongClickListener{
		
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
	ResolveInfo appinfo=(ResolveInfo)arg0.getItemAtPosition(arg2);
	ApplicationInfo info=(ApplicationInfo)appinfo.activityInfo.applicationInfo;
	if((info.flags&ApplicationInfo.FLAG_SYSTEM)>0){
		Toast.makeText(mContext, "系统应用不能删除!", Toast.LENGTH_SHORT).show();
	}else{
	Uri uri=Uri.parse("package:"+appinfo.activityInfo.packageName);
Intent remove=new Intent(Intent.ACTION_DELETE,uri);
		startActivity(remove);
		}
			return true;
		}
		
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class AppIntentReceiver extends BroadcastReceiver { 
		@Override 
		public void onReceive(Context context, Intent intent) { 
		initViews();
		} 
		} 
	private void registerIntentReceivers() { 
		IntentFilter	filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED); 
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED); 
		filter.addAction(Intent.ACTION_PACKAGE_CHANGED); 
		filter.addDataScheme("package"); 
		registerReceiver(new AppIntentReceiver(), filter); 
		} 

	
}
