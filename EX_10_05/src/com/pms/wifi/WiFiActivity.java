package com.pms.wifi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class WiFiActivity extends Activity {
	Button bt_open=null;
	Button bt_close=null;
	Button bt_scan=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bt_open=(Button)this.findViewById(R.id.bt_open);
        bt_close=(Button)this.findViewById(R.id.bt_close);
        bt_scan=(Button)this.findViewById(R.id.bt_scan);
    }
}