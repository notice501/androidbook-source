package com.pms.network;

import java.io.IOException;
import java.util.Vector;



public class Connect {
	public static final int CONNECT_HTTP = 0;
	public static final int CONNECT_SOCKET = 1;	
	private TcpChannel channel;
	private int timeout;
	private int connectType;
	private int port;
	private String ip;	
	private static Vector<TcpChannel> vector = new Vector<TcpChannel>();	
	public static void stopConnectAll() {
		for(TcpChannel con : vector) {
			con.close();
		}
		vector.removeAllElements();
	}
	
	public static void stopConnect(TcpChannel connect) {
		if(connect==null)
			return;
		connect.close();
		vector.remove(connect);
	}

	public Connect(String ip, int port) {
		this(CONNECT_SOCKET, ip, port);
	}

	public Connect(int connect, String ip, int port) {
		connectType = connect;
		this.ip = ip;
		this.port = port;
		timeout = 30000;
	}
	public TcpChannel openChannel() {
		channel = null;
		channel = new SocketChannel(ip, port, timeout);
		vector.addElement(channel);
		return channel;
	}
	public int getTimeout() {
		return timeout;
	}
	/**
	 * 获取网络连接管道
	 * 
	 * @return	连接实例
	 */
	public TcpChannel getTcpChannel() {
		return channel;
	}	
	public final boolean isConnected() {
		return channel.isConnected();
	}
	
	public final byte[] queryServer(byte[] inData) throws Exception
	{	
		channel = openChannel();
		try {
			channel.connect(channel.timeout);
			channel.send(channel.getOutputStream(), inData);
			return   inData;
		} catch (IOException e) {
		}
		close();
		return null;
	}
	public void close() {
		stopConnect(channel);
	}
}
