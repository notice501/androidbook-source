package com.pms.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Students {
	
	// 授权常量
	
    public static final String AUTHORITY = "com.pms.provider.students";
    private Students() {}
    // 内部类，继承BaseColums
    public static final class Student implements BaseColumns {
    	// 构造方法
        private Student() {}
        // 定义CONTENT_URI
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/student");
        // 定义内容类型
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.pms.provider.students";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.pms.provider.students";
        
        // 数据库中的表字段常量
        public static final String NAME = "name";					// 姓名
        public static final String GENDER= "gender";				// 性别
        public static final String AGE = "age"; 					// 年龄
    }
}

