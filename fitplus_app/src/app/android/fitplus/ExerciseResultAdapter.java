package app.android.fitplus;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExerciseResultAdapter extends BaseAdapter {
	LayoutInflater mInflater;
	ArrayList<ExerciseResultItem> arSrc;
	Context context;
	
	public ExerciseResultAdapter(Context context, ArrayList<ExerciseResultItem> arSrc) {
		mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		this.arSrc = arSrc;
		this.context = context;
	}

	public int getCount() {
		return arSrc.size();
	}

	public ExerciseResultItem getItem(int position) {
		return arSrc.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if( convertView == null ) 
			convertView = mInflater.inflate(R.layout.exercise_result_item, parent, false);
		
		TextView txt = (TextView) convertView.findViewById(R.id.exercise_result_name);
		txt.setText(arSrc.get(position).getExerciseName());
		txt = (TextView) convertView.findViewById(R.id.exercise_result_counter);
		txt.setText(""+arSrc.get(position).getExerciseCounter());
		return convertView;
	}

}
