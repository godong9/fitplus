package app.android.fitplus;

import java.util.ArrayList;

import app.android.fitplus.ExerciseResultAdapter;
import app.android.fitplus.ExerciseResultItem;
import app.android.fitplus.NumberPadAdapter;
import app.android.fitplus.R;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ExerciseFragment extends Fragment  implements SensorEventListener {
	public enum COUNTING_MODE {
		touch_numberpad,
		touch_screen,
		automatically
	}
	ViewFlipper pageFlipper;
	ListView exerciseListView;
	ListView exerciseResultListView;
	GridView numberPad;
	
	ArrayList<String> chestExerciseList;
	COUNTING_MODE countingMode;
	String currentExercise;
	String currentExerciseCounter;
	
	ExerciseResultAdapter exerciseResultAdapter;
	ArrayList<ExerciseResultItem> exerciseResultList;
	
	SensorManager sm;
	SensorEventListener oriL;
	Sensor oriSensor;
	private long lastTime;
	int count = 0;
	private float numX, numY, numZ;
	private float lastX, lastY, lastZ;
	private static final int DATA_X = SensorManager.DATA_X;
	private static final int DATA_Y = SensorManager.DATA_Y;
	private static final int DATA_Z = SensorManager.DATA_Z;
	private float x, y, z;
	
	View layout;

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		
		layout = inflater.inflate(R.layout.fragment_exercise, container, false);
		
		getActivity().setTitle(getString(R.string.title_1));

		sm =  (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
		oriSensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
		pageFlipper = (ViewFlipper) layout.findViewById(R.id.page_flipper);
		exerciseListView = (ListView) pageFlipper.findViewById(R.id.exercise_list_page);
		exerciseResultListView = (ListView) pageFlipper.findViewById(R.id.result_list_page);
		
		String[] chestExerciseArray = getResources().getStringArray(R.array.chest_exercise);
		chestExerciseList = new ArrayList<String>();
		for(int i = 0; i < chestExerciseArray.length; i++)
			chestExerciseList.add(chestExerciseArray[i]);
		ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, chestExerciseList);
		exerciseListView.setAdapter(exerciseAdapter);
		exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				currentExercise = ((TextView)v).getText().toString();
				currentExerciseCounter = "0";
				goNext();
			}
		});
		
		exerciseResultList = new ArrayList<ExerciseResultItem>();
		exerciseResultAdapter = new ExerciseResultAdapter(getActivity(), exerciseResultList);
		exerciseResultListView.setAdapter(exerciseResultAdapter);
		
		Button countingModeButton1 = (Button) layout.findViewById(R.id.counting_mode_1);
		countingModeButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectCountingMode(v);
			}
		});
		Button countingModeButton2 = (Button) layout.findViewById(R.id.counting_mode_2);
		countingModeButton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectCountingMode(v);
			}
		});
		Button countingModeButton3 = (Button) layout.findViewById(R.id.counting_mode_3);
		countingModeButton3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectCountingMode(v);
			}
		});
		Button goFirstPageButton = (Button) layout.findViewById(R.id.result_reset_go_first_page);
		goFirstPageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onReset(v);
			}
		});
		Button goSecondPageButton = (Button) layout.findViewById(R.id.result_reset_go_second_page);
		goSecondPageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onReset(v);
			}
		});
		
		return layout;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sm.registerListener(this, oriSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}	

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sm.unregisterListener(this);
	}

	public void onSelectCountingMode(View v) {
		Button confirmButton;
		Button negativeButton;
		switch(v.getId()) {
		case R.id.counting_mode_1:
			countingMode = COUNTING_MODE.touch_numberpad;
			if(pageFlipper.getChildCount() != 4)
				pageFlipper.addView(getActivity().getLayoutInflater().inflate(R.layout.counting_mode_1, null), 2);

			confirmButton = (Button) pageFlipper.findViewById(R.id.counter_confirm);
			confirmButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onConfirmClick(v);
				}
			});
			negativeButton = (Button) pageFlipper.findViewById(R.id.counter_negative);
			negativeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onNegativeClick(v);
				}
			});
			
			currentExerciseCounter = "0";
			((TextView) pageFlipper.findViewById(R.id.counter_text)).setText(currentExerciseCounter);
			numberPad = (GridView) pageFlipper.findViewById(R.id.counter_numberpad);
			String[] numberPadArray = getResources().getStringArray(R.array.numberpad);
			ArrayList<String> numberPadList = new ArrayList<String>();
			for(int i = 0; i < numberPadArray.length; i++)
				numberPadList.add(numberPadArray[i]);
			NumberPadAdapter numberPadAdapter = new NumberPadAdapter(getActivity(), numberPadList);
			numberPad.setAdapter(numberPadAdapter);
			numberPad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					String numberText = ((TextView) v.findViewById(R.id.numberpad_item)).getText().toString();
					if( numberText.isEmpty() == false ) {
						if( currentExerciseCounter.equals("0") ) {
							currentExerciseCounter = numberText;
							((Button) pageFlipper.findViewById(R.id.counter_negative)).setText(getString(R.string.counter_delete));
						}
						else
							currentExerciseCounter += numberText;
						((TextView) pageFlipper.findViewById(R.id.counter_text)).setText(currentExerciseCounter);
					}
				}
			});
			break;
		case R.id.counting_mode_2:
			countingMode = COUNTING_MODE.touch_screen;
			if(pageFlipper.getChildCount() != 4)
				pageFlipper.addView(getActivity().getLayoutInflater().inflate(R.layout.counting_mode_2, null), 2);
			
			confirmButton = (Button) pageFlipper.findViewById(R.id.counter_confirm);
			confirmButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onConfirmClick(v);
				}
			});
			negativeButton = (Button) pageFlipper.findViewById(R.id.counter_negative);
			negativeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onNegativeClick(v);
				}
			});
			View countTouchPad = (View) pageFlipper.findViewById(R.id.counter_touchpad);
			countTouchPad.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onCount(v);
				}
			});
			
			currentExerciseCounter = "0";
			((TextView) pageFlipper.findViewById(R.id.counter_text)).setText(currentExerciseCounter);
			break;
		case R.id.counting_mode_3:
			countingMode = COUNTING_MODE.automatically;
			if(pageFlipper.getChildCount() != 4)
				pageFlipper.addView(getActivity().getLayoutInflater().inflate(R.layout.counting_mode_3, null), 2);
			
			confirmButton = (Button) pageFlipper.findViewById(R.id.counter_confirm);
			confirmButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onConfirmClick(v);
				}
			});
			negativeButton = (Button) pageFlipper.findViewById(R.id.counter_negative);
			negativeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onNegativeClick(v);
				}
			});
			break;
		}
		goNext();
	}
	
	public void onConfirmClick(View v) {
		exerciseResultList.add(new ExerciseResultItem(currentExercise, Integer.parseInt(currentExerciseCounter)));
		exerciseResultAdapter.notifyDataSetChanged();
		goNext();
	}
	
	public void onNegativeClick(View v) {
		if(((Button)v).getText().toString().equals(getString(R.string.counter_cancel))) {
			goPrev();
		}
		else {
			if( currentExerciseCounter.length() == 1) {
				currentExerciseCounter = "0";
				((Button)v).setText(getString(R.string.counter_cancel));
			}
			else {
				currentExerciseCounter = currentExerciseCounter.substring(0, currentExerciseCounter.length()-1);
			}
			((TextView) layout.findViewById(R.id.counter_text)).setText(currentExerciseCounter);
		}
	}
	
	public void onReset(View v) {
		pageFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.viewin_right));
		pageFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.viewout_right));
		switch(v.getId()) {
		case R.id.result_reset_go_first_page:
			pageFlipper.removeViewAt(2);
			pageFlipper.setDisplayedChild(0);
			break;
		case R.id.result_reset_go_second_page:
			currentExerciseCounter = "0";
			((TextView) layout.findViewById(R.id.counter_text)).setText(currentExerciseCounter);
			pageFlipper.setDisplayedChild(1);
			break;
		}
		setPageTitle();
	}
	
	private void goPrev() {
		pageFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.viewin_right));
		pageFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.viewout_right));
		pageFlipper.showPrevious();
		setPageTitle();
	}

	private void goNext() {
		pageFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.viewin_left));
		pageFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.viewout_left));
		pageFlipper.showNext();
		setPageTitle();
	}
	
	private void setPageTitle() {
		switch(pageFlipper.getDisplayedChild()) {
		case 0:
			getActivity().setTitle(getString(R.string.title_1));
			break;
		case 1:
			getActivity().setTitle(getString(R.string.title_2));
			break;
		case 2:
			switch(countingMode) {
			case touch_numberpad:
				getActivity().setTitle(getString(R.string.title_3) + getString(R.string.counting_mode_1));
				break;
			case touch_screen:
				getActivity().setTitle(getString(R.string.title_3) + getString(R.string.counting_mode_2));
				break;
			case automatically:
				getActivity().setTitle(getString(R.string.title_3) + getString(R.string.counting_mode_3));
				break;
			}
			break;
		case 3:
			getActivity().setTitle(getString(R.string.title_4));
			break;
		}
	}
	
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//		if( keyCode == KeyEvent.KEYCODE_BACK) {
//			int pageIndex = pageFlipper.getDisplayedChild();
//			switch(pageIndex) {
//			case 0:
//				break;
//			case 1:
//				if(pageFlipper.getChildCount() == 4)
//					pageFlipper.removeViewAt(2);
//				goPrev();
//				break;
////			case 3:
////				if(pageFlipper.getChildCount() == 4)
////					pageFlipper.removeViewAt(2);
////				goPrev();
////				break;
//			default:
//				goPrev();
//			}	
//        	return true;
//        }
//		else
//			return super.onKeyDown(keyCode, event);
//	}
	
	public void onCount(View v) {
		int counter = Integer.parseInt(currentExerciseCounter);
		counter++;
		currentExerciseCounter = ""+counter;
		((TextView) layout.findViewById(R.id.counter_text)).setText(currentExerciseCounter);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub	
		synchronized (this) {
			if(countingMode == COUNTING_MODE.automatically){
			
				switch (event.sensor.getType()) {

					case Sensor.TYPE_ORIENTATION:
						x = event.values[DATA_X];
						y = event.values[DATA_Y];
						z = event.values[DATA_Z];
						long currentTime = System.currentTimeMillis();
						long gabOfTime = (currentTime - lastTime);
						
						if (gabOfTime > 500) {
							lastTime = currentTime;
							
							x = event.values[SensorManager.DATA_X];
							y = event.values[SensorManager.DATA_Y];
							z = event.values[SensorManager.DATA_Z];
							
							numX = lastX - x;
							numY = lastY - y;
							numZ = lastZ - z;
							String tmpX = String.valueOf(numX);
							String tmpY = String.valueOf(numY);
							String tmpZ = String.valueOf(numZ);
							Log.e("NumX", tmpX);
							Log.e("NumY", tmpY);
							Log.e("NumZ", tmpZ);
							if ( numX > 80 || numY > 80 || numZ > 80 ){
								count++;	
								String tmpCount = String.valueOf(count);
								Log.e("Count: ", tmpCount);
								((TextView) layout.findViewById(R.id.counter_text)).setText(tmpCount);	
							}
				
							lastX = event.values[DATA_X];
							lastY = event.values[DATA_Y];
							lastZ = event.values[DATA_Z];
						}
						
						break;
				}
			}                        
			                          
		}
	}
}
