package com.pms.ucmenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class Ucmenu extends Activity {
	
	GridView menuDialogGrid, menubarGrid;//�ײ��˵��ͶԻ���˵�����GridViewչʾ
	AlertDialog menuDialog;//�˵��Ի���
	View menuView;//menu��ͼ
	private boolean isMore = false;// ���Ʋ˵���ҳ
	/* MENU�˵�ѡ�����*/
	private final int ITEM_SEARCH = 0;// ����
	private final int ITEM_FILE_MANAGER = 1;// �ļ�����
	private final int ITEM_DOWN_MANAGER = 2;// ���ع���
	private final int ITEM_FULLSCREEN = 3;// ȫ��
	private final int ITEM_MORE = 11;// �˵�

	private final int MENUBAR_ITEM_MENU = 4;// �˵�
	
	/*�˵�ͼƬ��Դ */
	int[] menu_image_array = { R.drawable.menu_search,
			R.drawable.menu_filemanager, R.drawable.menu_downmanager,
			R.drawable.menu_fullscreen, R.drawable.menu_inputurl,
			R.drawable.menu_bookmark, R.drawable.menu_bookmark_sync_import,
			R.drawable.menu_sharepage, R.drawable.menu_quit,
			R.drawable.menu_nightmode, R.drawable.menu_refresh,
			R.drawable.menu_more };
	
	/* �˵����� */
	String[] menu_name_array = { "����", "�ļ�����", "���ع���", "ȫ��", "��ַ", "��ǩ",
			"������ǩ", "����ҳ��", "�˳�", "ҹ��ģʽ", "ˢ��", "����" };
	
	/* �˵�ͼƬ2 */
	int[] menu_image_array2 = { R.drawable.menu_auto_landscape,
			R.drawable.menu_penselectmodel, R.drawable.menu_page_attr,
			R.drawable.menu_novel_mode, R.drawable.menu_page_updown,
			R.drawable.menu_checkupdate, R.drawable.menu_checknet,
			R.drawable.menu_refreshtimer, R.drawable.menu_syssettings,
			R.drawable.menu_help, R.drawable.menu_about, R.drawable.menu_return };
	
	/*�˵�����2*/
	String[] menu_name_array2 = { "�Զ�����", "��ѡģʽ", "�Ķ�ģʽ", "���ģʽ", "��ݷ�ҳ",
			"������", "�������", "��ʱˢ��", "����", "����", "����", "����" };

	/*�ײ��˵�ͼƬ*/
	int[] menu_toolbar_image_array = { R.drawable.controlbar_homepage,
			R.drawable.controlbar_backward_enable,
			R.drawable.controlbar_forward_enable, R.drawable.controlbar_window,
			R.drawable.controlbar_showtype_list };
	
	/*�ײ��˵�����*/
	String[] menu_toolbar_name_array = { "��ҳ", "����", "ǰ��", "����", "�˵�" };
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //���벼��
        menuView = View.inflate(this, R.layout.gridview_menu, null);
        //�����Զ���AlertDialog,����Ҫע��ʹ��creat()����������AlertDialog����
         menuDialog = new AlertDialog.Builder(this).create();
         menuDialog.setView(menuView);
         /*���ð��������¼�������menu��*/
         menuDialog.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_MENU){
					menuDialog.dismiss();//����menu�����������menu������رնԻ���
				}
				return false;
			}
		});

        //Ҫ�����Ӧ�Ĳ��ֶ�����ȡ�ÿؼ�����
         menuDialogGrid = (GridView) menuView.findViewById(R.id.gridview);
         //�󶨼�����
         menuDialogGrid.setAdapter(getAdapter(this, menu_name_array, menu_image_array));
         menuDialogGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch(position){
				case ITEM_SEARCH:// ����
					Toast.makeText(Ucmenu.this, "����������", Toast.LENGTH_SHORT).show();
					break;
				case ITEM_FILE_MANAGER:// �ļ�����
					Toast.makeText(Ucmenu.this, "�������ļ�����", Toast.LENGTH_SHORT).show();
					break;
				case ITEM_DOWN_MANAGER:// ���ع���
					Toast.makeText(Ucmenu.this, "���������ع���", Toast.LENGTH_SHORT).show();
					break;
				case ITEM_FULLSCREEN:// ȫ��
					Toast.makeText(Ucmenu.this, "������ȫ��", Toast.LENGTH_SHORT).show();
					break;
				case ITEM_MORE:// ��ҳ
					if (isMore) {
						//���°��µ�����
						menuDialogGrid.setAdapter(getAdapter(Ucmenu.this, menu_name_array, menu_image_array));
						isMore = false;//
					} else {// ��ҳ
						menuDialogGrid.setAdapter(getAdapter(Ucmenu.this, menu_name_array2, menu_image_array2));
						isMore = true;
					}
					menuDialogGrid.invalidate();// ������ͼ
					menuDialogGrid.setSelection(ITEM_MORE);//���õ�ǰѡ����
					break;
				}
				
			}
		});
         /*�ײ��˵�*/
         menubarGrid = (GridView) findViewById(R.id.menuBar_gv);//�õ��ײ��˵�GridView����
         //��������
         menubarGrid.setAdapter(getAdapter(Ucmenu.this,menu_toolbar_name_array,menu_toolbar_image_array));
 		 menubarGrid.setBackgroundResource(R.drawable.channelgallery_bg);// ���ñ���
         //�󶨵���¼�������
         menubarGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*�����������Ӧ��Toast��Ϣ*/
				Toast.makeText(Ucmenu.this,
						menu_toolbar_name_array[position], Toast.LENGTH_SHORT)
						.show();
				//
				if(position == MENUBAR_ITEM_MENU)
					menuDialog.show();
			}
		});
        
    }
    /*����һ��menu�������ܽػ�menu��menu��ʹ�ú����ѧϰ*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");//��������һ��menu
		return super.onCreateOptionsMenu(menu);
	}
	
	//����menu�¼�
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (menuDialog == null) {
			//���menuDialogΪ�գ��򴴽�һ������ʾ
			menuDialog = new AlertDialog.Builder(this).
			setView(menuView).
			show();
		} else {
			menuDialog.show();//menuDialog��Ϊ�գ���ֱ����ʾ
		}
		return false;// ���뷵��false������Ϊtrue������ʾϵͳmenu
	}

	/*����һ������������MyAdapter���󣬷���ʹ��*/
    private MyAdapter getAdapter(Context ct,String[] items,int[] icons){
    	MyAdapter mAdapter = new MyAdapter(ct, items, icons);
    	return  mAdapter;
    }
}