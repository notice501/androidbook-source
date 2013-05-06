package com.pms.mytoast;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MyToast extends Activity {
	
	/*����Button����*/
	private Button bt_default;
	private Button bt_custom;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*�õ�Button����*/
        bt_default = (Button) findViewById(R.id.bt_default);
        bt_custom = (Button) findViewById(R.id.bt_custom);
        
        /*��������¼�*/
        bt_default.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��ʾToast
				Toast.makeText(MyToast.this, "Ĭ�ϵ�Toast", Toast.LENGTH_LONG).show();
				
			}
		});
        /*��������¼�*/
        bt_custom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//���벼��
				View toastView = getLayoutInflater().inflate(R.layout.mytoast, null);
				//�õ�Toast����
				Toast mToast = new Toast(MyToast.this);
				//����Toast��λ�ã����������ֱ�Ϊλ�ã�x��ƫ�ƣ�y��ƫ��
				mToast.setGravity(Gravity.CENTER, 0, 0);
				//����Toast��չʾʱ�䳤��
				mToast.setDuration(Toast.LENGTH_SHORT);
				//����Toast��Ҫչʾ����ͼ
				mToast.setView(toastView);
				//��ʾToast
				mToast.show();
				
			}
		});
        
    }

}