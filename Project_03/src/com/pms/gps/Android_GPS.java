package com.pms.gps;

import com.pms.ui.FlashActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Android_GPS extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(this,FlashActivity.class);
        startActivity(intent);
        finish();
    }
}