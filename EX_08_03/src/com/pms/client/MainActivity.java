package com.pms.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * 服务端
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent service = new Intent(this, MyService.class);
        startService(service);
    }
}