package com.pms.database;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

import android.os.Environment;

public class FileOperation {

	private String  filename=null;
	private String  rootName=null;
	private String  suffix=".txt";
	private final String  PATH="/sdcard/Barcode/";
//	private final String PATH = "/data/data/com.xuanwu.etion/";
	public  int     startPosition;
	public  boolean insertdata=false;
	private String  secondarydir="";
	private File    file;
	/**
	 * 二级目录�?
	 * @param dir
	 */
	public FileOperation(String dir,String suffix){
		this.secondarydir=dir;
		if(suffix!=null)
			this.suffix=suffix;
	}
	/**
	 * 创建打开文件
	 * @param name
	 */
	public boolean initFile(String name){
		if(rootName==null){
			rootName=getRootName();
		}
		rootName="";
		filename=PATH+secondarydir+"/";
		if(rootName!=null){			
			try{
				if(name!=null&&name.length()>0){
						file=new File(filename);
						if(!file.exists())
						{
						    file.mkdirs();
						}
						if(name!=null&&!"".equals(name))
						{
							filename=filename+name+suffix;
							file=new File(filename);
							if(!file.exists()){
								file.createNewFile();//文件不存在则新建
							}
						}
				}else
				{
					file=new File(filename);
					if(!file.exists())
					{
					    file.mkdirs();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("创建文件失败");
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 删除目录
	 * 如果目录下有子目录或文件，也全部删除
	 * 
	 * @param pathName
	 */
	public  boolean deletDirectory(String pathName)
	{
		boolean del = false;
		File file = new File(filename+pathName);
		if(file.exists())
		{
			if(file.isFile())
			{
				file.delete();
			}else if(file.isDirectory())
			{
				String[] f = file.list();
				for(int i=0;i<f.length;i++)
				{
					deletDirectory(pathName+"/"+f[i]);
				}
				del = file.delete();
			}
		}
		return del;
	}
	/**
	 * �?��是否支持存储�?
	 * @return
	 */
	public static String getRootName(){
		try{
			if (Environment.getExternalStorageState().equals(   
			            Environment.MEDIA_MOUNTED)) {  
				
			       return "可用sdcrad";
			       
			 }else {  
			        	return null;
		     }
		 }catch(Exception e){
			return null;
		}
	}
	/**
	 * 获取文件列表
	 * 
	 * @param path
	 * @return
	 */
	public  String[] getFileList()
	{
		Vector<String> fileName = new Vector<String>();
		if(file.exists() && file.isDirectory())
		{
			String[] str = file.list();
			for(String s : str)
			{
				if(new File(filename+s).isFile())
				{
					fileName.addElement(s);
				}
			}
			return fileName.toArray(new String[]{});
		}
		return null;
	}
	/**
	 * 添加�?��记录,�?��记录大小固定�?K
	 * @param data
	 */
	public  boolean addLine(byte[] data,boolean append){	
		if(!file.exists()) {
			return false;
		}
		try {
			FileOutputStream fos = new FileOutputStream(file,append);		//追加方式	
			fos.write(data);
			fos.close();
		} catch (FileNotFoundException e) {
			System.out.println("写存储卡错误");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("写存储卡错误");
			e.printStackTrace();
			return false;
		}
		return true;
    } 
	public  void closeFile(){
		try{
			if(file!=null){
					file=null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 返回当前文件�?
	 * @return
	 */
	public  byte[] getData(){
		
		if(!file.exists()) {
			return null;
		}
		int length = (int) file.length();
		byte[] b = new byte[length];
		try {
			FileInputStream fis = new FileInputStream(file);
			fis.read(b, 0, length);
			fis.close();
		} catch (FileNotFoundException e) {
		
		} catch (IOException e) {
		}
		return length!=0?b:null;
	}
	
	/**
	 * 删除文件
	 */
	public  boolean deleteFile(){
		
		try{
			if(file!=null)
				file.delete();
		}catch(Exception e){
			System.out.println("删除文件IO异常");
			
			return false;
		}
		return true;
	}
	/**
	 * 判断文件是否存在
	 * @param name
	 * @return
	 */
	public  boolean exist(String name)
	{   
		String  mypath=PATH+secondarydir+"/";
	    File file = new File(mypath, name+suffix);
		return file.exists();
	}
	
	public  void deleteAllFile(){
		  delDir("/sdcard/Barcode/");
	}
	/**
	* 删除目录
	* 如果目录下有子目录或文件，也全部删除
	*/
	public  void delDir(String pathName){
		   
		  boolean del = false;
			File file = new File(PATH+pathName);
			if(file.exists())
			{
				if(file.isFile())
				{
					file.delete();
				}else if(file.isDirectory())
				{
					String[] f = file.list();
					for(int i=0;i<f.length;i++)
					{
						deletDirectory(pathName+"/"+f[i]);
					}
					del = file.delete();
				}
			}
	 }
}
