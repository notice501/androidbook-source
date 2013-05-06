package com.pms.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Share extends Activity {
	
	private Button bt;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //取得Button对象
        bt = (Button) findViewById(R.id.bt);
        //为Butotn绑定监听器
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);//创建Intent对象，执行发送动作
				intent.setType("text/plain");//设置数据的MIME类型
				intent.putExtra(Intent.EXTRA_TEXT, "很简单！");//添加要传递的数据
				startActivity(Intent.createChooser(intent, getTitle()));//启动这个intent，并设置选择的标题
			}
		});
    }
}