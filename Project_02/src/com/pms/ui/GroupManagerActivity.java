package com.pms.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pms.data.AppContext;
import com.pms.data.Group;
import com.pms.database.GroupTableManager;
import com.pms.scan.R;

public class GroupManagerActivity extends Activity {
	final int MESSAGE_CREATE = 0;
	final int MESSAGE_EDIT = 1;
	final int MESSAGE_CLEAR = 2;
	final int MESSAGE_CLEARALL = 3;
	private View localView;
	Group currGroup = new Group();
	String[] liststr = null;
	private List<String> arraylist = new ArrayList<String>();
	ListView listview = null;
	Vector<Group> grouplist = new Vector<Group>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listview = new ListView(this);

		localView = LayoutInflater.from(this).inflate(
				R.layout.app_frame_layout, null);
		getGroup();
		getListStr();
		initUI();
	}

	private void getGroup() {
		/*
		 * Group group=new Group(); group.groupid="abc";
		 * 
		 * grouplist.add(group); Group group2=new Group();
		 * group2.groupid="abdef";
		 * 
		 * grouplist.add(group2);
		 */
		grouplist = GroupTableManager.getInsance(this).getGroupList();
	}

	private void getListStr() {
		liststr = new String[grouplist.size()];
		arraylist = new ArrayList<String>();
		for (int i = 0; i < grouplist.size(); i++) {
			liststr[i] = ((Group) grouplist.elementAt(i)).groupid;
			arraylist.add(liststr[i]);
		}
	}

	private void refreshUI() {

		getListStr();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, liststr);
		autotext.setAdapter(adapter);
		listview.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arraylist));
	}

	AutoCompleteTextView autotext = null;

	private void initUI() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		View topgroup = (View) localView.findViewById(R.id.my_top);
		((TextView) topgroup.findViewById(R.id.title)).setText("分组管理");
		LinearLayout body = (LinearLayout) localView.findViewById(R.id.my_body);
		body.setOrientation(LinearLayout.VERTICAL);
		TextView text = new TextView(this);
		text.setText("请输入分组号： ");
		autotext = new AutoCompleteTextView(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, liststr);
		autotext.setAdapter(adapter);
		autotext.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					String text = ((AutoCompleteTextView) v).getText()
							.toString();
					check(text);//检测当前输入分组，是否已经存在
				}

			}
		});
		
		body.removeAllViews();
		body.addView(text);
		body.addView(autotext);
		listview.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arraylist));
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int index,
					long arg3) {
				selectNum = index;
				currGroup = grouplist.elementAt(selectNum);
				edit();
			}
		});
		listview.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View v, int index,
					long arg3) {
				selectNum = index;
				currGroup = grouplist.elementAt(selectNum);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		body.addView(listview);
		setContentView(localView);
		ImageButton option = (ImageButton) findViewById(R.id.login_option);
		option.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openOptionsMenu();
			}
		});
		ImageButton break_btn = (ImageButton) findViewById(R.id.break_btn);
		break_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void setListSelected(String name) {
		for (int i = 0; i < grouplist.size(); i++) {
			if (((Group) grouplist.elementAt(i)).groupid.equals(name)) {
				listview.setSelected(true);
				listview.setSelection(i);
				listview.setFocusable(true);
				System.out.println("符合的位置" + i);
				break;
			}
		}
	}

	private void check(String text) {
		boolean equal = false;
		for (int i = 0; i < grouplist.size(); i++) {
			if (((Group) grouplist.elementAt(i)).groupid.equals(text)) {
				equal = true;
				break;
			}
		}
		if (!equal) {
			currGroup = new Group();
			currGroup.groupid = text;
			if (text.length() > 0)
				buildDialog(this, "系统消息", 4).show();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	int msgStyle = 0;
	View alertView = null;
	private int selectNum;

	private Dialog buildDialog(Context context, String title, int msgStyle) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		this.msgStyle = msgStyle;
		builder.setTitle(title);
		if (msgStyle == 0 || msgStyle == 1) {

			alertView = LayoutInflater.from(context).inflate(
					R.layout.group_msg_style, null);
			if (msgStyle == 1) {

				((EditText) alertView.findViewById(R.id.group_text))
						.setText(currGroup.groupid);
				((EditText) alertView.findViewById(R.id.max_num))
						.setText(currGroup.maxNum);
			}
			builder.setView(alertView);
			if (msgStyle == 1 && title.equals("新建分组")) {
				this.msgStyle = 0;// 如果是手动输入的分组号，则将设为新建模式
			}
		} else {
			if (msgStyle == 2) {
				builder.setMessage("确定删除该分组吗");
			} else if (msgStyle == 3) {
				builder.setMessage("确定清空所有分组吗");
			} else {
				builder.setMessage("无此分组号，是否新建" + currGroup.groupid + "分组?");
			}
		}
		builder.setPositiveButton(R.string.button_ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// 保存信息
						okHandle();
					}
				});
		builder.setNegativeButton(R.string.button_cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// 取消当前弹出对话框

					}
				});

		return builder.create();
	}

	private void okHandle() {
		switch (msgStyle) {
		case 1:// 编辑
			if (((EditText) alertView.findViewById(R.id.group_text)).getText()
					.toString().equals(currGroup.groupid)
					&& ((EditText) alertView.findViewById(R.id.max_num))
							.getText().toString().equals(currGroup.maxNum)) {
				// 没有做任何改变，不保存
				// refreshUI();//确保当自动新建时能刷新
			} else if (((EditText) alertView.findViewById(R.id.group_text))
					.getText().toString().equals(currGroup.groupid)
					&& (!((EditText) alertView.findViewById(R.id.max_num))
							.getText().toString().equals(currGroup.maxNum))) {
				// 只修改了最大数量
				((Group) grouplist.elementAt(selectNum)).maxNum = ((EditText) alertView
						.findViewById(R.id.max_num)).getText().toString();
				if (((Group) grouplist.elementAt(selectNum)).maxNum.equals("")) {
					((Group) grouplist.elementAt(selectNum)).maxNum = "500";
				}
				GroupTableManager.getInsance(this).updateGroup(
						((Group) grouplist.elementAt(selectNum)));
				if (currGroup.groupid.equals(AppContext.getInstance()
						.getCurrGroup().groupid)) {
					AppContext.getInstance().setCurrGroup(currGroup);
				}
				// refreshUI();//确保当自动新建时能刷新
			} else {
				//
				add();
			}
			break;
		case 0:// 新建
			add();
			break;
		case 2:// 删除
			grouplist.remove(selectNum);
			GroupTableManager.getInsance(this).deleteGroup(currGroup.groupid);
			refreshUI();
			break;
		case 3:// 删除全部
			grouplist.removeAllElements();
			GroupTableManager.getInsance(this).deleteTable();
			grouplist = GroupTableManager.getInsance(this).getGroupList();
			refreshUI();
			break;

		case 4:
			// grouplist.add(currGroup);//确定创建输入的信息是，将输入信息先添加到队列
			buildDialog(this, "新建分组", 1).show();
			break;
		}
	}

	private void add() {
		Group group = new Group();
		group.groupid = ((EditText) alertView.findViewById(R.id.group_text))
				.getText().toString();
		boolean found = false;
		for (int i = 0; i < grouplist.size(); i++) {
			if (((Group) grouplist.elementAt(i)).groupid.equals(group.groupid)) {
				found = true;
				break;
			}
		}
		if (!found) {

			group.maxNum = ((EditText) alertView.findViewById(R.id.max_num))
					.getText().toString();
			if (group.maxNum.equals("")) {
				group.maxNum = "500";
			}
			grouplist.add(group);
			GroupTableManager.getInsance(this).addOneGroup(group);
			refreshUI();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("系统提示");
			builder.setMessage("分组重复，请重新输入");
			builder.setPositiveButton(R.string.button_ok,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			builder.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.addSubMenu(Menu.NONE, MESSAGE_CREATE, 0, "新建分组");
		menu.addSubMenu(Menu.NONE, MESSAGE_EDIT, 0, "编辑分组");
		menu.addSubMenu(Menu.NONE, MESSAGE_CLEAR, 0, "删除分组");
		menu.addSubMenu(Menu.NONE, MESSAGE_CLEARALL, 0, "删除所有");
		return super.onCreateOptionsMenu(menu);
	}

	private void edit() {
		buildDialog(this, "编辑分组", 1).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case MESSAGE_EDIT:// 编辑 1
			edit();
			break;
		case MESSAGE_CREATE:// 新建 0
			buildDialog(this, "新建分组", 0).show();
			break;
		case MESSAGE_CLEAR:// 删除单条记录 2
			buildDialog(this, "删除分组", 2).show();
			break;
		case MESSAGE_CLEARALL:// 清空所有分组 3
			buildDialog(this, "删除所有分组", 3).show();
			break;

		}
		return super.onOptionsItemSelected(item);
	}
}
