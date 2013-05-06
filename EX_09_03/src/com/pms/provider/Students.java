package com.pms.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Students {
	
	// ��Ȩ����
	
    public static final String AUTHORITY = "com.pms.provider.students";
    private Students() {}
    // �ڲ��࣬�̳�BaseColums
    public static final class Student implements BaseColumns {
    	// ���췽��
        private Student() {}
        // ����CONTENT_URI
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/student");
        // ������������
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.pms.provider.students";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.pms.provider.students";
        
        // ���ݿ��еı��ֶγ���
        public static final String NAME = "name";					// ����
        public static final String GENDER= "gender";				// �Ա�
        public static final String AGE = "age"; 					// ����
    }
}

