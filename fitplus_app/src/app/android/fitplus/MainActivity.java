/*
 * MainActivity.java	0.1 2013/11/14
 * 
 * 첫 화면 액티비티
 */

package app.android.fitplus;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/*
 * 메인 액티비티 클래스
 */
public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_layout);
		
		FragmentManager fm = getFragmentManager();
	    FragmentTransaction tr = fm.beginTransaction();
	    MainFragment mf = new MainFragment();
	    tr.add(R.id.content_fragment, mf);
	    tr.commit();
	    
	    // 각 버튼마다 버튼 리스너 등록
	    findViewById(R.id.exerciseBtn).setOnClickListener(btnListener);
	    findViewById(R.id.historyBtn).setOnClickListener(btnListener);
	    findViewById(R.id.myPageBtn).setOnClickListener(btnListener);
	}
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	*/

	/*
	 * 탭 메뉴 버튼 관련 이벤트 리스너 
	 */
	Button.OnClickListener btnListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentManager fm = getFragmentManager();
		    FragmentTransaction tr = fm.beginTransaction();
			
		    ImageButton exerciseBtn = (ImageButton)findViewById(R.id.exerciseBtn);
		    ImageButton historyBtn = (ImageButton)findViewById(R.id.historyBtn);
		    ImageButton myPageBtn = (ImageButton)findViewById(R.id.myPageBtn);
	
			if(v.getId() == R.id.exerciseBtn){
				Toast.makeText(getApplicationContext(), "exerciseBtn", Toast.LENGTH_SHORT).show();
				exerciseBtn.setSelected(true);
				historyBtn.setSelected(false);
				myPageBtn.setSelected(false);	
				
				MainFragment mf = new MainFragment();	    
			    tr.replace(R.id.content_fragment, mf);
			    tr.commit();
			    
			}	
			else if(v.getId() == R.id.historyBtn){
				Toast.makeText(getApplicationContext(), "historyBtn", Toast.LENGTH_SHORT).show();
				exerciseBtn.setSelected(false);
				historyBtn.setSelected(true);
				myPageBtn.setSelected(false);	
				
				HistoryFragment hf = new HistoryFragment();	    
			    tr.replace(R.id.content_fragment, hf);
			    tr.commit();
			   
			}
			else if(v.getId() == R.id.myPageBtn){
				Toast.makeText(getApplicationContext(), "myPageBtn", Toast.LENGTH_SHORT).show();
				exerciseBtn.setSelected(false);
				historyBtn.setSelected(false);
				myPageBtn.setSelected(true);	
				
				MyPageFragment mpf = new MyPageFragment();	    
			    tr.replace(R.id.content_fragment, mpf);
			    tr.commit();
			    
			}
		}
	};
}
