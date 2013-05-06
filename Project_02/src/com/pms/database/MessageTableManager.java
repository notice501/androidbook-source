package com.pms.database;

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.pms.data.Message;


public class MessageTableManager {
	private String MESSAGE_DB="MESSAGE_DB";
	private String GROUPID="GROUPID";
	private String STATE="STATE";
	private String CODETEXT="CODETEXT";
	private String REMARK="REMARK";
	private String KEYID="KEY_ID";
	static MessageTableManager mtm=null;
	private  boolean  isNewInstall=true;
	private static String groupid=null;
	private BaseDB db;
	private Vector<Message> messagelist=new Vector<Message>();
	public MessageTableManager(Context context,String groupid){
		db=new BaseDB(context);
		if(groupid.equals("默认")){
			groupid="default";
		}
		MESSAGE_DB=MESSAGE_DB+"_"+groupid;
		this.groupid=groupid;
		isNewInstall=true;
		try{			
			if(db.isTableExits(db.getConnection(), MESSAGE_DB)){
				Cursor  cursor=db.getConnection().query(MESSAGE_DB, new String[]{GROUPID,STATE,CODETEXT,REMARK}, null,null , null, null, null);
				if(cursor!=null){
					while(cursor.moveToNext()){						
						isNewInstall=false;
					}
				}
				cursor.close();
				db.closeConnection(db.getConnection());
			}
			if(isNewInstall){
				
				initMessageTable();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			if(db.getConnection().isOpen())
			{
				db.closeConnection(db.getConnection());
			}
		}
	}
	/**
	 * 批量添加
	 */
	public void saveMessageList(Vector<Message> list){
		try{
			for(int i=0;i<list.size();i++){
				Message msg=list.elementAt(i);
				ContentValues cv=new ContentValues();
				cv.put(KEYID, msg.key);
				cv.put(GROUPID, msg.group);
				cv.put(CODETEXT, msg.codetext);
				cv.put(STATE, msg.state);
				cv.put(REMARK, msg.remark);
				db.save(db.getConnection(), MESSAGE_DB, cv);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			if(db.getConnection().isOpen())
			{
				db.closeConnection(db.getConnection());
			}
		}
	}
	
	/**
	 * 初始新建表
	 */
	public void initMessageTable(){
		String createSQL="CREATE TABLE IF NOT EXISTS "+MESSAGE_DB+
		" ("+KEYID+"  VARCHAR primary key,"+
		GROUPID+" VARCHAR,"+STATE+" VARCHAR,"+CODETEXT+" VARCHAR,"+REMARK+" VARCHAR"+")";
		try{			
			db.creatTable(db.getConnection(), createSQL);			
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			if(db.getConnection().isOpen())
			{
				db.closeConnection(db.getConnection());
			}
		}
	}
	/**
	 * 获取所有分组列表
	 * @return
	 */
	public Vector<Message> getMessageList(){
		messagelist.removeAllElements();
		try{
			String[] cols=new String[]{
					KEYID,GROUPID,CODETEXT,STATE,REMARK
			};
			if(db.isTableExits(db.getConnection(), MESSAGE_DB)){
				
			}else{
				initMessageTable();
			}
			System.out.println("table  name"+MESSAGE_DB);
			Cursor cursor=db.getConnection().query(MESSAGE_DB, cols, null, null, null, null, null);
			while(cursor.moveToNext()){
				Message msg=new Message();
				msg.group=cursor.getString(cursor.getColumnIndex(GROUPID));
				msg.codetext=cursor.getString(cursor.getColumnIndex(CODETEXT));
				msg.key=cursor.getString(cursor.getColumnIndex(KEYID));
				msg.state=Integer.parseInt(cursor.getString(cursor.getColumnIndex(STATE)));
				msg.remark=cursor.getString(cursor.getColumnIndex(REMARK));
				messagelist.add(msg);
			}
			
			cursor.close();		
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			if(db.getConnection().isOpen())
			{
				db.closeConnection(db.getConnection());
			}
		}
		return this.messagelist;
	}
	/**
	 * 修改，更新一条记录
	 * @param groupid
	 */
	public void updateGroup(Message msg){
		try{
			ContentValues values=new ContentValues();
			values.put(GROUPID, msg.group);
			values.put(KEYID, msg.key);
			values.put(CODETEXT, msg.codetext);
			values.put(STATE, msg.state);
			values.put(REMARK, msg.remark);
			db.update(db.getConnection(), MESSAGE_DB, values, KEYID+"=?", new String[]{msg.key});
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			if(db.getConnection().isOpen())
			{
				db.closeConnection(db.getConnection());
			}
		}
	}
	/**
	 * 添加一条消息
	 * @param msg
	 */
	public void addOneMessage(Message msg){
		try{
			ContentValues cv=new ContentValues();
			cv.put(KEYID, msg.key);
			cv.put(GROUPID, msg.group);
			cv.put(CODETEXT, msg.codetext);
			cv.put(STATE, msg.state);
			cv.put(REMARK, msg.remark);
			db.save(db.getConnection(), MESSAGE_DB, cv);
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			if(db.getConnection().isOpen())
			{
				db.closeConnection(db.getConnection());
			}
		}
	}
	/**
	 *删除一条记录
	 * @param groupid
	 */
	public void deleteMessage(String key){
	try{
			db.delete(db.getConnection(), MESSAGE_DB, KEYID+"=?", new String[]{key});
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{
			if(db.getConnection().isOpen())
			{
				db.closeConnection(db.getConnection());
			}
		}
	}
	/**
	 * 删除所有数据
	 */
	public void deleteTable(){
		db.deleteTable(db.getConnection(), MESSAGE_DB);
	}
}
