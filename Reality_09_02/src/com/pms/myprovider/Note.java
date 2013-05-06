package com.pms.myprovider;

import android.net.Uri;
import android.provider.BaseColumns;


public final class Note {
	//����� AUTHORITY Ҫ����Ψһ�����Һ�Manifest����provider��ǩ��AUTHORITY����һ��
    public static final String AUTHORITY = "com.pms.myprovider";

    
    /**
     * Notes table
     */
    public static final class NoteColumns implements BaseColumns {
        // This class cannot be instantiated
        private NoteColumns() {}

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/notes");


        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.note";


        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.note";


        public static final String DEFAULT_SORT_ORDER = "created DESC";

        public static final String TITLE = "title";

        public static final String BODY = "body";

    }
}
