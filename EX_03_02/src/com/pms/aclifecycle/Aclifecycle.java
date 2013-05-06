package com.pms.aclifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Aclifecycle extends Activity {
	
	private static final String LIFETAG = "Aclifecycle";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d(LIFETAG, "onCreat Method is executed" );
    }

	@Override
	protected void onStart() {
		
		super.onStart();
		Log.d(LIFETAG, "onStart Method is executed");
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		Log.d(LIFETAG, "onResume Method is executed");
	}

	@Override
	protected void onRestart() {

		super.onRestart();
		Log.d(LIFETAG, "onRestart Method is executed");
	}

	@Override
	protected void onPause() {

		super.onPause();
		Log.d(LIFETAG, "onPause Method is executed");
	}

	@Override
	protected void onStop() {

		super.onStop();
		Log.d(LIFETAG, "onStop Method is executed");
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		Log.d(LIFETAG, "onDestroy Method is executed");
	}
    
}