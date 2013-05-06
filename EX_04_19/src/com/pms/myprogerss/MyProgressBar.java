package com.pms.myprogerss;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MyProgressBar extends Activity implements OnClickListener{
	
	/*���������ؼ�����*/
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
        //���ô��ڷ�񣬱�����Ϊȷ����������Ҫд��setContentViewǰ
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.main);
        setProgressBarVisibility(true);//���ñ���������ɼ�

        /*�õ�ProgressBar����������������������*/
        pb = (ProgressBar) findViewById(R.id.pb);
        /*���ý�����Ϊxml�ж����ProgressBar�ĵ�ǰ����*/
        setProgress(pb.getProgress() * 100);//���õ�һ��������
		setSecondaryProgress(pb.getSecondaryProgress() * 100);//���õڶ��������
		
		//�õ����е�Button����
        add_one = (Button) findViewById(R.id.bt_add1);
        add_two = (Button) findViewById(R.id.bt_add2);
        min_one = (Button) findViewById(R.id.bt_min1);
        min_two = (Button) findViewById(R.id.bt_min2);
        bt_show = (Button) findViewById(R.id.bt_show);
        
        //�󶨵��������
        add_one.setOnClickListener(this);
        add_two.setOnClickListener(this);
        min_one.setOnClickListener(this);        
        min_two.setOnClickListener(this);
        bt_show.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		/*Ϊÿ��Button��ӵ���¼�*/
		switch(v.getId())
		{
		case R.id.bt_show:
			pb.setVisibility(View.VISIBLE);//���ý������ɼ�
			break;
		case R.id.bt_add1:
			pb.incrementProgressBy(10);//��һ����������������10
			break;
		case R.id.bt_add2:
			pb.incrementSecondaryProgressBy(10);//�ڶ�����������������10
			break;
		case R.id.bt_min1:
			pb.incrementProgressBy(-10);//��һ�����������ȼ���10
			break;
		case R.id.bt_min2:
			pb.incrementSecondaryProgressBy(-10);//�ڶ������������ȼ���10
			break;
			
		}
		
		/*�������Button�͸��±������Ľ�����Ϊ�벼���н�����������ͬ��ʵ��ͬ��Ч��*/
		setProgress(pb.getProgress() * 100);//���õ�һ��������
		setSecondaryProgress(pb.getSecondaryProgress() * 100);//���õڶ��������
		
	}
}