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
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
