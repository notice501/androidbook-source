package com.pms.viewstub;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyViewStub extends Activity {
	
	private ViewStub mViewStub;
	private Button   showButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mViewStub = (ViewStub)findViewById(R.id.viewStub);//实例化ViewStub控件，这里可以看出我们必须为ViewStub设定id
        showButton = (Button)findViewById(R.id.show);
        
        /*为按钮添加点击监听事件，后面的章节会介绍*/         				
        showButton.setOnClickListener(new OnClickListener(){
        	
        	@Override
        	public void onClick(View v) {
        		if (mViewStub != null) {  
                   mViewStub.inflate();  //点击即导入ViewStub标签的内容
                  }  
        		
        	}
        });
    }
}