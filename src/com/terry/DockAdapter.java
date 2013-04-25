package com.terry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class DockAdapter extends BaseAdapter {

	ArrayList<View> dock;
	public DockAdapter(ArrayList<View> list){
		dock=list;
		Log.w("count",list.size()+"");
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//ImageView ima=new ImageView();
		return dock.get(position);
	}

	

}
