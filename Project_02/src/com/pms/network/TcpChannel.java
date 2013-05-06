package com.pms.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class TcpChannel {
	int timeout = 0;//超时
	String ip = "";//请求服务器IP
	int port;//服务器端口	
	public abstract OutputStream getOutputStream() throws IOException;	
	public abstract InputStream getInputStream() throws IOException;	
	public abstract void connect(int timeout) throws Exception;
	public abstract int send(OutputStream connection, byte[] inData) throws Exception;
	public abstract byte[] receive(InputStream in) throws Exception;	
	public abstract boolean isConnected();	
	public abstract void close();
	public TcpChannel(String ip, int port, int timeout) {
		this.ip = ip;
		this.port = port;
		this.timeout = timeout;
	}	
	@Override
	protected void finalize() {
		close();
	}
}
