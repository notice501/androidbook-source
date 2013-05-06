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
        tv1.setText("��ǰֵΪ��" + sb.getProgress());
        tv2.setText("δ�϶�");
        
        sb.setOnSeekBarChangeListener(this);
    }
    /*�����϶��¼�*/
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		tv1.setText("��ǰֵΪ��" + progress);
		tv2.setText("�����϶�");
		
	}
	/*������ʼ�϶�*/
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		tv2.setText("��ʼ�϶�");
		
	}
	/*����ֹͣ�϶�*/
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		tv2.setText("ֹͣ�϶�");
		
	}
}