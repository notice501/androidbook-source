package com.pms.myprovider;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SimpleCursorAdapter;

import com.pms.myprovider.Note.NoteColumns;

public class MainActivity extends ListActivity {
	
	public static final int MENU_ITEM_INSERT = Menu.FIRST;
	
	private static final String[] PROJECTION = new String[] { NoteColumns._ID,
		NoteColumns.TITLE };
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		Intent intent = getIntent();
		if (intent.getData() == null) {
			intent.setData(NoteColumns.CONTENT_URI);
		}
		renderListView();
        
    }
    
	private void renderListView() {
		Cursor cursor = managedQuery(getIntent().getData(), PROJECTION, null,
				null, NoteColumns.DEFAULT_SORT_ORDER);

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.listitem, cursor, new String[] { NoteColumns.TITLE,}, new int[] { R.id.itemtv,});
		setListAdapter(adapter);
	}
}