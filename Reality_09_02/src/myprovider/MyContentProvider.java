package myprovider;

import com.pms.myprovider.Note;
import com.pms.myprovider.Note.NoteColumns;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider{
	
	private static final int NOTES = 1;
	private static final int NOTES_ID = 2;
	
	private static final UriMatcher myUriMatcher;
	
	private final static String DATABASE_NAME = "note_db";  //数据库名
	private final static int DATABASE_VERSION = 1;			//版本号			
	private final static String TABLE_NAME = "notepad";
	
	private static class DbHelper extends SQLiteOpenHelper{
		
		/*构造函数*/
		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		/*创建数据库*/
		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "create table "+TABLE_NAME+" ("
			+NoteColumns._ID+" integer primary key autoincrement, "
			+NoteColumns.TITLE+" text, "
			+NoteColumns.BODY+" text )";
			db.execSQL(sql);
		}

		/*更新数据库*/
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String sql = "drop table if exists "+TABLE_NAME;
			db.execSQL(sql);	
		}
		
	}
	
	private DbHelper dbHelper;

	static {
		myUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		myUriMatcher.addURI(Note.AUTHORITY, "notes", NOTES);
		myUriMatcher.addURI(Note.AUTHORITY, "notes/#", NOTES_ID);

	}

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (myUriMatcher.match(uri)) {
		case NOTES:
			qb.setTables(TABLE_NAME);
			break;

		case NOTES_ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere(NoteColumns._ID + "="
					+ uri.getPathSegments().get(1));
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = Note.NoteColumns.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, orderBy);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (myUriMatcher.match(uri)) {
		case NOTES:
			return NoteColumns.CONTENT_TYPE;

		case NOTES_ID:
			return NoteColumns.CONTENT_ITEM_TYPE;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (myUriMatcher.match(uri) != NOTES) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues cv;
		if (values != null) {
			cv = new ContentValues(values);
		} else {
			cv = new ContentValues();
		}

		if (cv.containsKey(Note.NoteColumns.TITLE) == false) {
			Resources r = Resources.getSystem();
			cv.put(Note.NoteColumns.TITLE, r
					.getString(android.R.string.untitled));
		}

		if (cv.containsKey(Note.NoteColumns.BODY) == false) {
			cv.put(Note.NoteColumns.BODY, "");
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insert(TABLE_NAME, NoteColumns.BODY, cv);
		if (rowId > 0) {
			Uri NoteUri = ContentUris.withAppendedId(
					Note.NoteColumns.CONTENT_URI, rowId);
			return NoteUri;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String rowId = uri.getPathSegments().get(1);
		return db.delete(TABLE_NAME, NoteColumns._ID + "=" + rowId, null);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String rowId = uri.getPathSegments().get(1);
		return db.update(TABLE_NAME, values, NoteColumns._ID + "="
				+ rowId, null);
	}

}
