package com.pms.database;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDB extends SQLiteOpenHelper{
	
	 private static String DB_NAME = "pms.db";  //数据库名�?
	
	 private static int DB_VERSION = 2;  //版本�?
	
	 private  SQLiteDatabase db; //
	
	 public  BaseDB(Context context)
	 {    
		 super(context, DB_NAME, null, DB_VERSION);
		 
		 db=getWritableDatabase();  //读写方式打开数据�?
	 }
	 public void onCreate(SQLiteDatabase db) {
		
	 }
	/**
	* 当检测与前一次创建数据库版本不一样时，先删除表再创建新表
	*/
	 @Override
	 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
	 }
	 /**
	  * 添加操作
	  * @param db          数据�?  提供操作�?    
	  * @param insertSql   对应插入字段   如：insert into person(name,age) values(?,?)
	  * @param obj         对应�?                 如： new Object[]{person.getName(),person.getAge()};
	  * @return
	  */
	 public  boolean  save(SQLiteDatabase db,String insertSql,Object obj[])
	 {
		try{
		  
			 db.execSQL(insertSql,obj);
		
		  }catch(Exception ex)
		  {   
			  ex.printStackTrace();
			  return  false;
		  }finally
		  {
			   closeConnection(db);
		  }
		  return true;
	 }
	 
	 /**
	  * 添加操作
	  * @param db          数据�?  提供操作�?    
	  * @param tableName   表名
	  * @param values      集合对象
	  * @return
	  */
	 public  boolean  save(SQLiteDatabase db,String tableName,ContentValues values )
	 {
		 try{
			 
			 db.insert(tableName, null, values);
		  
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
			  return  false;
		  }finally
		  {
			   closeConnection(db);
		  }
		  return true;
	 }
	 /**
	  * 更新操作
	  * @param db  数据�?  提供操作�?    
	  * @param updateSql  对应跟新字段    如： update person set name=?,age=? where personid=?"
	  * @param obj        对应�?                  如：  new Object[]{person.getName(),person.getAge(),person.getPersonid()};
	  */
	 public boolean update(SQLiteDatabase db,String updateSql,Object obj[]){

	     try{
			  
	        db.execSQL(updateSql, obj);
		  
	      }catch(Exception ex)
		  {   ex.printStackTrace();
			  return  false;
		  }finally
		  {
			   closeConnection(db);
		  }
		  return true;
	 }
	 /**
	  * 更新操作
	  * @param db  数据�?  提供操作�?    
	  * @param table
	  * @param values
	  * @param whereClause
	  * @param whereArgs
	  * @return
	  */
	 public  boolean  update(SQLiteDatabase db,String table,ContentValues values ,String whereClause,String []whereArgs)
	 {
		     try{
		    	  
		    	 db.update(table, values, whereClause, whereArgs);
			  
		      }catch(Exception ex)
			  {  
		    	  ex.printStackTrace();
				  return  false;
			  }finally
			  {
				   closeConnection(db);
			  }
		return true;
	 }
	 /**
	  * 删除
	  * @param    db   数据�?  提供操作�?  
	  * @param    deleteSql   对应跟新字段    如： "where personid=?"
	  * @param    obj[]       对应�?                  如：   new Object[]{person.getPersonid()};
	  * @return                
	  */
	 public  boolean  delete(SQLiteDatabase db ,String table, String deleteSql,String obj[])
	 {
		  try{
			  
			  db.delete(table, deleteSql, obj);
		      
		   }catch(Exception ex)
		   {     
			   ex.printStackTrace();
			   return  false;
		   }finally
		   {
			   closeConnection(db);
		   }
		   return true;
	 }
	/* public Cursor query(String tablename,String cols[]){
		 Cursor cursor=null;
		 try{
			 cursor=db.
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return cursor;
	 }*/
	/**
	 * 查询操作
	 * @param db         
	 * @param findSql  对应查询字段    如： select * from person limit ?,?
	 * @param obj      对应�?                  如：  new String[]{String.valueOf(fristResult),String.valueOf(maxResult)}
	 * @return
	 */
	 public  Cursor  find(SQLiteDatabase db ,String findSql,String obj[]  )
	 {   
		
		 try{
			 Cursor cursor = db.rawQuery(findSql,obj);
			 return  cursor;
          
		   }catch(Exception ex)
		   {    
			    ex.printStackTrace();
				return null;
		   }
	 }
	 /**
	  * 创建�? 
	  * @param db    
	  * @param createTableSql      
	  * @return
	  */
	 public  boolean creatTable(SQLiteDatabase db,String  createTableSql)
	 {   
         try{
			 
    	     db.execSQL(createTableSql); 

           }catch(Exception ex)
		   {
        	   ex.printStackTrace();
				return false;
		   }finally
		   {
			   closeConnection(db);
		   }
		 return true;
	 }
	 /**
	 删除�?
	*/
	public  void  deleteTable(SQLiteDatabase db ,String tableName)
	{   
		try{
			db.execSQL("DROP TABLE IF EXISTS  "+tableName);  	 
		 }catch(Exception ex)
		 {    
			 ex.printStackTrace();
	     }finally{
	    	 closeConnection(db);
	     }
	 }
	 
	 /**
	 * 判断表是否存�?
	 * @param tablename
	 * @return
	 */
	 public boolean isTableExits(SQLiteDatabase db ,String tablename){
		// boolean result=true;//表示不存�?
		 try{
	        
			 String str="select count(*) xcount  from  "+tablename;
	         db.rawQuery(str,null).close();
	       }catch(Exception ex)
		   {
				return false;
		   }finally
		   {
			   closeConnection(db);
		   }
	       return true;
	 }
	 /***
	  * 关闭 获取SQLite数据库连�?
	  * @return  SQLiteDatabase
	  */
	 public SQLiteDatabase getConnection (){
		    if(!db.isOpen())
		    {
		    	db=getWritableDatabase();  //读写方式获取SQLiteDatabase
		    }
	        return db;
	 }
	 /***
	  * 关闭 SQLite数据库连�?
	  * @return
	  */
	 public void closeConnection (SQLiteDatabase db ){
		 try{ 
			 if(db!=null&&db.isOpen())
	          db.close();
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }
	 
}
