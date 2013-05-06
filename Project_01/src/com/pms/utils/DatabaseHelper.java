package com.pms.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pms.bean.TrustAppBean;
/**
 * 数据库工具包
 * @author machao
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "db_permission.db";// 数据库名
	private static final String T_TRUSTAPP = "t_trustapp";// 表名
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase db;
	public static final String[] columns = { "_ID", "NAME", "packageName",
			"sourceDir" };

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE t_trustapp ("
				+ "_ID INTEGER PRIMARY KEY autoincrement,"
				+ "NAME TEXT,packageName text,sourceDir text" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * 增加数据
	 * 
	 * @return
	 */
	public boolean insertTrustAppBean(String name, String packageName,
			String sourceDir) {
		String sql = "";
		try {
			db = this.getWritableDatabase();
			sql = "insert into t_trustapp(name,packageName,sourceDir) values('"
					+ name + "','" + packageName + "','" + sourceDir + "')";
			db.execSQL(sql);
			return true;

		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 增加数据
	 * 
	 * @return
	 */
	public boolean insertDifferentTrustAppBean(String name, String packageName,
			String sourceDir) {
		String sql = "";
		if(isExistTrustAppBeanByPackageName(packageName)){
			return false;
		}
		try {
			db = this.getWritableDatabase();
			sql = "insert into t_trustapp(name,packageName,sourceDir) values('"
					+ name + "','" + packageName + "','" + sourceDir + "')";
			db.execSQL(sql);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 查询所有记录
	 */
	public List<TrustAppBean> loadTrustAppBeanAll() {
		List<TrustAppBean> list = new ArrayList<TrustAppBean>();
		TrustAppBean bean = null;
		db = this.getWritableDatabase();
		Cursor cur = db.query("t_trustapp", columns, null, null, null, null,
				null);
		if (cur != null) {
			while (cur.moveToNext()) {
				bean = new TrustAppBean();
				bean.setId(cur.getString(cur.getColumnIndex("_ID")));
				bean.setName(cur.getString(cur.getColumnIndex("NAME")));
				bean.setPackageName(cur.getString(cur
						.getColumnIndex("packageName")));
				bean.setSourceDir(cur
						.getString(cur.getColumnIndex("sourceDir")));
				list.add(bean);
			}
			cur.close();
		}
		return list;
	}

	/**
	 * 通过id删除记录
	 */
	public void deleteTrustAppBeanById(String id) {
		db = this.getWritableDatabase();
		String DELETE_DATA = "DELETE FROM " + T_TRUSTAPP + " WHERE _id=" + id;
		db.execSQL(DELETE_DATA);
	}

	/**
	 * 通过id删除记录
	 */
	public void deleteTrustAppBeanByPackageName(String packageName) {
		db = this.getWritableDatabase();
		String DELETE_DATA = "DELETE FROM " + T_TRUSTAPP
				+ " WHERE packageName='" + packageName + "'";
		db.execSQL(DELETE_DATA);
	}

	/**
	 * 通过id查询记录
	 */
	public TrustAppBean loadTrustAppBeanById(String id) {
		TrustAppBean bean = null;
		db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("select * from t_trustapp where _ID=" + id,
				null);
		if (cur != null && cur.moveToFirst()) {
			bean = new TrustAppBean();
			bean.setId(cur.getString(cur.getColumnIndex("_ID")));
			bean.setName(cur.getString(cur.getColumnIndex("NAME")));
			bean.setPackageName(cur
					.getString(cur.getColumnIndex("packageName")));
			bean.setSourceDir(cur.getString(cur.getColumnIndex("sourceDir")));
			cur.close();
		}
		return bean;
	}

	/**
	 * 通过packageName查询记录
	 */
	public TrustAppBean loadTrustAppBeanByPackageName(String packageName) {
		TrustAppBean bean = null;
		db = this.getWritableDatabase();
		Cursor cur = db.rawQuery(
				"select _ID,NAME,packageName,sourceDir from t_trustapp where packageName='"
						+ packageName + "'", null);
		if (cur != null && cur.moveToFirst()) {
			bean = new TrustAppBean();
			bean.setId(cur.getString(cur.getColumnIndex("_ID")));
			bean.setName(cur.getString(cur.getColumnIndex("NAME")));
			bean.setPackageName(cur
					.getString(cur.getColumnIndex("packageName")));
			bean.setSourceDir(cur.getString(cur.getColumnIndex("sourceDir")));
			cur.close();
		}
		return bean;
	}

	/**
	 * 通过packageName查询程序是否存在信任列表
	 */
	public boolean isExistTrustAppBeanByPackageName(String packageName) {
		TrustAppBean bean = this.loadTrustAppBeanByPackageName(packageName);
		if (bean != null && bean.getId() != null) {
			return true;
		}
		return false;
	}

	public void close() {
		if (db != null)
			db.close();
	}

}
