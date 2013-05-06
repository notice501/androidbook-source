package com.pms.provider;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.pms.provider.Students.Student;

public class MyProvider extends Activity {
	
	private static final String TAG = "MyProvider";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //��ִ�в������
        insert();
        //��ѯ
        query();
        //����
        update();
        //���²�ѯ
        query();
        //ɾ��
        delete();
        //��ѯ
        query();
    }
    
    public void insert(){
    	// ����Uri
    	Uri uri = Student.CONTENT_URI;
    	// ȡ��ContentValues����
    	ContentValues cv = new ContentValues();
    	// ���ѧ����Ϣ
    	cv.put(Student.NAME, "notice");
    	cv.put(Student.GENDER, "��");
    	cv.put(Student.AGE,30);
    	// ����Ϣ����
    	getContentResolver().insert(uri, cv);
    	// �����־
    	Log.i(TAG, uri.toString());
    }
  
    // ��ѯ
    private void query(){
    	// Ҫ��ѯ����
    	   String[] PROJECTION = new String[] { 
    			   Student._ID, 		
    			   Student.NAME, 		
    			   Student.GENDER, 	
    			   Student.AGE 		
    	};
    	// ��ѯѧ����Ϣ�������α����
		Cursor c = managedQuery(Student.CONTENT_URI, PROJECTION, null,
				null, null);
		// �ж��α��Ƿ�Ϊ��
		if (c.moveToFirst()) {
			// �����α�
			for (int i = 0; i < c.getCount(); i++) {
				c.moveToPosition(i);
				String name = c.getString(1);  //ȡ������
				String gender = c.getString(2);//ȡ���Ա�
				int age = c.getInt(3);		   //ȡ������
				// �����־
				Log.i(TAG, name+","+gender+","+age);
			}
		}
    }
    
    // ����
    private void update(){
    	// ָ��IDΪ1�ļ�¼
    	Uri uri = ContentUris.withAppendedId(Student.CONTENT_URI, 1);
    	ContentValues cv = new ContentValues();
    	// ���ѧ����Ϣ
    	cv.put(Student.NAME, "pjd");
    	cv.put(Student.GENDER, "��");
    	cv.put(Student.AGE,25);
    	// ���¸ü�¼
		getContentResolver().update(uri, cv, null, null);
    }
    
    // ɾ��
    private void delete(){
    	// ָ��IDΪ1�ļ�¼
    	Uri uri = ContentUris.withAppendedId(Student.CONTENT_URI, 1);
    	// ɾ���ü�¼
    	getContentResolver().delete(uri, null, null);
    	Log.i(TAG, "��Ϣ��ɾ��");
    }
    
}