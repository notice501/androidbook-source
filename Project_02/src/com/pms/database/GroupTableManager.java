package com.pms.database;

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.pms.data.Group;

public class GroupTableManager {
	final static String GROUP_DB="GROUP_DB";
	final static String GROUPID="GroupID";
	final static String GROUPNUM="GroupNum";
	private  boolean  isNewInstall=true;
	private Vector<Group> grouplist=new Vector<Group>();
	static GroupTableManager gtm=null;
	private BaseDB db;
	public GroupTableManager(Context context){
		db=new BaseDB(context);
		isNewInstall=true;
		try{
			
			if(db.isTableExits(db.getConnection(), GROUP_DB)){
				Cursor  cursor=db.getConnection().query(GROUP_DB, new String[]{GROUPID,GROUPNUM}, null,null , null, null, null);
				if(cursor!=null){
					while(cursor.moveToNext()){
						
						isNewInstall=false;
					}
				}
				cursor.close();
				db.closeConnection(db.getConnection());
			}
			if(isNewInstall){
				
				initGroupTable();
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
	public static GroupTableManager getInsance(Context context){
		if(gtm==null){
			gtm=new GroupTableManager(context);
		}
		return gtm;
	}
	/**
	 * 获取所有分组列表
	 * @return
	 */
	public Vector<Group> getGroupList(){
		grouplist.removeAllElements();
		try{
			String[] cols=new String[]{
					GROUPID,GROUPNUM
			};
			if(db.isTableExits(db.getConnection(), GROUP_DB)){
				
			}else{
				initGroupTable();
			}
			Cursor cursor=db.getConnection().query(GROUP_DB, cols, null, null, null, null, null);
			while(cursor.moveToNext()){
				Group group=new Group();
				group.groupid=cursor.getString(cursor.getColumnIndex(GROUPID));
				group.maxNum=cursor.getString(cursor.getColumnIndex(GROUPNUM));
				grouplist.add(group);
			}
			
			cursor.close();
			if(grouplist.size()==0){
				if(db.getConnection().isOpen())
				{
					db.closeConnection(db.getConnection());
				}
				initGroupTable();
				grouplist=getGroupList();
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
		return this.grouplist;
	}
	/**
	 * 添加插入一条分组记录
	 * @param group
	 */
	public  void addOneGroup(Group group){
		
		try{
			ContentValues cv=new ContentValues();
			cv.put(GROUPID, group.groupid);
			cv.put(GROUPNUM, group.maxNum);
			db.save(db.getConnection(), GROUP_DB, cv);
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
	 * 修改，更新一条记录
	 * @param groupid
	 */
	public void updateGroup(Group group){
		try{
			ContentValues values=new ContentValues();
			values.put(GROUPID, group.groupid);
			values.put(GROUPNUM, group.maxNum);
			db.update(db.getConnection(), GROUP_DB, values, GROUPID+"=?", new String[]{group.groupid});
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
	public void deleteGroup(String groupid){
	try{
			db.delete(db.getConnection(), GROUP_DB, GROUPID+"=?", new String[]{groupid});
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
		db.deleteTable(db.getConnection(), GROUP_DB);
	}
	/**\
	 * 初始化表
	 */
	public void initGroupTable(){
		String createSQL="CREATE TABLE IF NOT EXISTS "+GROUP_DB+
		" ("+GROUPID +" VARCHAR primary key,"+
		GROUPNUM+" VARCHAR"+")";
		try{
			
			db.creatTable(db.getConnection(), createSQL);
			ContentValues cv=new ContentValues();
			cv.put(GROUPID, "默认");
			cv.put(GROUPNUM, "500");
			db.save(db.getConnection(), GROUP_DB,cv);
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
	
}