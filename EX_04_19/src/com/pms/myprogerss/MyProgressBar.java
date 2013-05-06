package com.pms.myprogerss;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MyProgressBar extends Activity implements OnClickListener{
	
	/*声明各个控件对象*/
	ProgressBar pb ;
	Button      bt_show;
	Button      add_one;
	Button      add_two;
	Button      min_one;
	Button      min_two;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗口风格，标题栏为确定进度条，要写在setContentView前
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.main);
        setProgressBarVisibility(true);//设置标题进度条可见

        /*得到ProgressBar对象，设置两条进度条进度*/
        pb = (ProgressBar) findViewById(R.id.pb);
        /*设置进度条为xml中定义的ProgressBar的当前进度*/
        setProgress(pb.getProgress() * 100);//设置第一个进度条
		setSecondaryProgress(pb.getSecondaryProgress() * 100);//设置第二层进度条
		
		//得到所有的Button对象
        add_one = (Button) findViewById(R.id.bt_add1);
        add_two = (Button) findViewById(R.id.bt_add2);
        min_one = (Button) findViewById(R.id.bt_min1);
        min_two = (Button) findViewById(R.id.bt_min2);
        bt_show = (Button) findViewById(R.id.bt_show);
        
        //绑定点击监听器
        add_one.setOnClickListener(this);
        add_two.setOnClickListener(this);
        min_one.setOnClickListener(this);        
        min_two.setOnClickListener(this);
        bt_show.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		/*为每个Button添加点击事件*/
		switch(v.getId())
		{
		case R.id.bt_show:
			pb.setVisibility(View.VISIBLE);//设置进度条可见
			break;
		case R.id.bt_add1:
			pb.incrementProgressBy(10);//第一个进度条进度增加10
			break;
		case R.id.bt_add2:
			pb.incrementSecondaryProgressBy(10);//第二个进度条进度增加10
			break;
		case R.id.bt_min1:
			pb.incrementProgressBy(-10);//第一个进度条进度减少10
			break;
		case R.id.bt_min2:
			pb.incrementSecondaryProgressBy(-10);//第二个进度条进度减少10
			break;
			
		}
		
		/*点击任意Button就更新标题栏的进度条为与布局中进度条进度相同，实现同步效果*/
		setProgress(pb.getProgress() * 100);//设置第一个进度条
		setSecondaryProgress(pb.getSecondaryProgress() * 100);//设置第二层进度条
		
	}
}