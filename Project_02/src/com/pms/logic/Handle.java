package com.pms.logic;

import java.util.Vector;

import com.pms.data.Message;
import com.pms.network.Connect;
import com.pms.network.Consts;

public class Handle {
	/**
	 * 上传一条记录  适合单次扫码模式
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static Object[] sendMessage(Message msg) throws Exception {
		Object[] obj=null;
		String result=null;
		Connect connect=new Connect(Consts.SERVICE_IP,Consts.SERVICE_PORT);
		String message=Consts.IMEI+","+msg.group+","+msg.codetext+"/e";
		System.out.println("message========"+message);
		byte[] output = null;
		output=connect.queryServer(message.getBytes("UTF-8"));
		if(output!=null){
			 result=new String(output,"UTF-8");
			 obj=new Object[]{result};
		}
		connect=null;
		return obj;
	}
	/**
	 * 上传一整个分组  适合连续扫码模式
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static Object[] sendMessageListOfGroup(Vector<Message> message) throws Exception {
		Object[] obj=null;
		Connect connect=new Connect(Consts.SERVICE_IP,Consts.SERVICE_PORT);		
		String result=null;
		StringBuffer sb=new StringBuffer();
		sb.append(Consts.IMEI);
		sb.append(",");
		for(int i=0;i<message.size();i++){
			sb.append(((Message)message.elementAt(i)).group);
			sb.append(",");
			sb.append(((Message)message.elementAt(i)).codetext);
			if(i!=message.size()-1)
			sb.append("\n");
		}
		sb.append("/e");
		byte[] output = null;
		output=connect.queryServer(sb.toString().getBytes("UTF-8"));
		if(output!=null){
			 result=new String(output,"UTF-8");
			 obj=new Object[]{result};
		}
		connect=null;
		return obj;
	}
}
