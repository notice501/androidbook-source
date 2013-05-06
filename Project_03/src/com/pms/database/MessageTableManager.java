package com.pms.database;

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.pms.data.ReceiveMessage;


public class MessageTableManager {
	private String MESSAGE_DB="MESSAGE_DB";
	private String MESSAGE_STATE="STATE";
	private String MESSAGE_TEXT="TEXT";
	private String RECEIVE_TIME="TIME";
	static MessageTableManager mtm=null;
	private  boolean  isNewInstall=true;
	private BaseDB db;
	private Vector<ReceiveMessage> messagelist=new Vector<ReceiveMessage>();
	public MessageTableManager(Context context,String time){
		db=new BaseDB(context);
		time=time.replaceAll("-", "_");//数据库名不用有特殊符号和中文，因此先做替换操作
		MESSAGE_DB=MESSAGE_DB+"_"+time.substring(0, 10);//将日期截取做为表名，如2011_06_10
		isNewInstall=true;
		try{			
			if(db.isTableExits(db.getConnection(), MESSAGE_DB)){
				Cursor  cursor=db.getConnection().query(MESSAGE_DB, new String[]{MESSAGE_STATE,MESSAGE_TEXT,RECEIVE_TIME}, null,null , null, null, null);
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
	public void saveMessageList(Vector<ReceiveMessage> list){
		try{
			for(int i=0;i<list.size();i++){
				ReceiveMessage msg=list.elementAt(i);
				ContentValues cv=new ContentValues();
				cv.put(MESSAGE_TEXT, msg.message);
				cv.put(MESSAGE_STATE, msg.state);
				cv.put(RECEIVE_TIME, msg.time);
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
		" ("+MESSAGE_STATE+" VARCHAR,"+MESSAGE_TEXT+" VARCHAR,"+RECEIVE_TIME+" VARCHAR"+")";
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
	public Vector<ReceiveMessage> getMessageList(){
		messagelist.removeAllElements();
		try{
			String[] cols=new String[]{
					MESSAGE_TEXT,MESSAGE_STATE,RECEIVE_TIME
			};
			if(db.isTableExits(db.getConnection(), MESSAGE_DB)){
				
			}else{
				initMessageTable();
			}
			System.out.println("table  name"+MESSAGE_DB);
			Cursor cursor=db.getConnection().query(MESSAGE_DB, cols, null, null, null, null, null);
			while(cursor.moveToNext()){
				ReceiveMessage msg=new ReceiveMessage();
				msg.message=cursor.getString(cursor.getColumnIndex(MESSAGE_TEXT));
				msg.state=Integer.parseInt(cursor.getString(cursor.getColumnIndex(MESSAGE_STATE)));
				msg.time=cursor.getString(cursor.getColumnIndex(RECEIVE_TIME));
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
	public void updateMessage(ReceiveMessage msg){
		try{
			ContentValues values=new ContentValues();
			values.put(MESSAGE_TEXT, msg.message);
			values.put(MESSAGE_STATE, msg.state);
			values.put(RECEIVE_TIME, msg.time);
			db.update(db.getConnection(), MESSAGE_DB, values, RECEIVE_TIME+"=?", new String[]{msg.time});
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
	public void addOneMessage(ReceiveMessage msg){
		try{
			ContentValues cv=new ContentValues();
			cv.put(MESSAGE_TEXT, msg.message);
			cv.put(MESSAGE_STATE, msg.state);
			cv.put(RECEIVE_TIME, msg.time);
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
	 * @param 
	 */
	public void deleteMessage(String key){
	try{
			db.delete(db.getConnection(), MESSAGE_DB, RECEIVE_TIME+"=?", new String[]{key});
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
