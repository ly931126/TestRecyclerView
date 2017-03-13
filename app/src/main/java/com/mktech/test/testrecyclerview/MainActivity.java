package com.mktech.test.testrecyclerview;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.OnListItemClickListener {
	private static final String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			MainFragment mainFragment = new MainFragment();
			getSupportFragmentManager().beginTransaction().add(R.id.content, mainFragment).commit();
		}
	}
	
	@Override
	public void onListItemClick(int position) {
		Fragment fragment = null;
		switch (position) {
			case 0 :
				fragment = new RecyclerListFragment();
				break;
			case 1 :
				fragment = new RecyclerGridFragment();
				break;
		}
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).addToBackStack(null).commit();

	}
}
