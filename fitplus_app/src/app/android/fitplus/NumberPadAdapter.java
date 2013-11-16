package app.android.fitplus;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NumberPadAdapter extends BaseAdapter {
	LayoutInflater mInflater;
	ArrayList<String> arSrc;
	Context context;
	
	public NumberPadAdapter(Context context, ArrayList<String> arSrc) {
		mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		this.arSrc = arSrc;
		this.context = context;
	}

	public int getCount() {
		return arSrc.size();
	}

	public String getItem(int position) {
		return arSrc.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if( convertView == null ) 
			convertView = mInflater.inflate(R.layout.numberpad_item, parent, false);
		
		// 항목 뷰를 초기화한다.
		TextView txt = (TextView) convertView.findViewById(R.id.numberpad_item);
		txt.setText(arSrc.get(position));
		
		return convertView;
	}

}
