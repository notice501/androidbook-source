package com.pms.logic;

import java.util.Vector;

import com.pms.data.Message;
import com.pms.database.FileOperation;



public class FileManager {
	private final static String GROUPDIR="AllGroup";
	/**
	 * 保存一个分组所有信息，适合重新写该分组。一般用在连续扫码模式，或者有替换操作的情况
	 * @param groupid 该分组ID
	 * @param msg  内容
	 * @return
	 */
	   public static boolean saveFileOfGroup(String groupid,Vector<Message> msg){		
		   FileOperation fp=null;
		   try{			   
			   fp=new FileOperation(GROUPDIR,null);
			   fp.initFile(groupid+"");		
			   StringBuffer sb=new StringBuffer();
			   for(int i=0;i<msg.size();i++){
				   Message message=msg.elementAt(i);
				   sb.append(message.group+"  ");
				   sb.append(message.codetext+"  ");
				   String s="";
				   switch(message.state){
				   case 0:
					   s="扫描";
					   break;
				   case 1:
					   s="手录";
					   break;
				   case 2:
					   s="删除";
					   break;
				   }
				   sb.append(s+"  ");
				   sb.append(message.remark);
				   sb.append("\r\n");
			   }
			 
			  fp.addLine(sb.toString().getBytes("UTF-8"),false);
			   sb=null;			
			   fp.closeFile();
			   fp=null;
			   System.gc();
			   return false;
		   }catch(Exception e){
			 e.printStackTrace();
			 if(fp!=null){
				 fp.closeFile();
			 }
			 return false;
		   }			
	   }
	   
	   /**
	    * 指定分组添加一条记录，一般在单次扫描模式，进行添加一条消息使用
	    * @param groupid
	    * @param msg
	    * @return
	    */
	   public static boolean addOneMessageOfGroup(String groupid,Message msg){
		   FileOperation fp=null;
		   try{			   
				   fp=new FileOperation(GROUPDIR,null);
				   fp.initFile(groupid+"");		
				   StringBuffer sb=new StringBuffer();				  
				   sb.append(msg.group+"  ");
				   sb.append(msg.codetext+"  ");
				   String s="";
				   switch(msg.state){
				   case 0:
					   s="扫描";
					   break;
				   case 1:
					   s="手录";
					   break;
				   case 2:
					   s="删除";
					   break;
				   }
				   sb.append(s+"  ");
				   sb.append(msg.remark);
				   sb.append("\r\n");
			 
			   fp.addLine(sb.toString().getBytes("UTF-8"),true);
			   sb=null;			
			   fp.closeFile();
			   fp=null;
			   System.gc();
			   return false;
		   }catch(Exception e){
			 e.printStackTrace();
			 if(fp!=null){
				 fp.closeFile();
			 }
			 return false;
		   }			
	   }
	   /**
	    * 删除该分组文件
	    * @param groupid
	    */
	   public static void delectGroup(String groupid){
		   FileOperation fp=null;
		   try{			   
			   fp=new FileOperation(GROUPDIR,null);
			   fp.initFile(groupid+"");
			   fp.deleteFile();
		   }catch(Exception e){
			   e.printStackTrace();
			   if(fp!=null){
				   fp.closeFile();
			   }
		   }
	  }
}
