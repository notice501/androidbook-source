package com.notice.myseekbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MySeekBar extends Activity implements OnSeekBarChangeListener {
	
	private SeekBar sb;
	private TextView tv1;
	private TextView tv2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        sb = (SeekBar) findViewById(R.id.sb);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv1.setText("当前值为：" + sb.getProgress());
        tv2.setText("未拖动");
        
        sb.setOnSeekBarChangeListener(this);
    }
    /*监听拖动事件*/
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		tv1.setText("当前值为：" + progress);
		tv2.setText("正在拖动");
		
	}
	/*监听开始拖动*/
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		tv2.setText("开始拖动");
		
	}
	/*监听停止拖动*/
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		tv2.setText("停止拖动");
		
	}
}