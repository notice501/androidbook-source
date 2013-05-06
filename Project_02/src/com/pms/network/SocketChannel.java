package com.pms.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

final class SocketChannel extends TcpChannel {

	private Socket server;
	private SocketAddress socketAddress;
	public SocketChannel(String ip, int port, int timeout) {
		super(ip, port, timeout);
		server = new Socket();
		socketAddress = new InetSocketAddress(ip, port);
	}

	@Override
	public boolean isConnected() {
		return server.isConnected();
	}
	@Override
	public OutputStream getOutputStream() throws IOException {
		return server.getOutputStream();
	}
	@Override
	public InputStream getInputStream() throws IOException {
		return server.getInputStream();
	}

	@Override
	public void connect(int timeout) throws Exception {
		if(server==null)
			throw new Exception("网络连接错误");
		if(!server.isConnected()){
			try {
				server.connect(socketAddress, timeout);				
				server.setSoTimeout(timeout);
			} catch (Exception e) {
				e.printStackTrace();
			}
         }
	}
	@Override
	public int send(OutputStream out, byte[] inData) throws Exception {
		if (out == null || inData == null) {
			throw new Exception("发送的数据为null");
		}
		try {
			out.write(inData);
		} catch (IOException e) {
			throw new Exception("无法发送信息");
		}
		return inData.length;
	}
	@Override
	public byte[] receive(InputStream in) throws Exception {
		if (in == null) {
			throw new Exception("接收到空数据");
		}
		try {
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream stream = new DataOutputStream(bos);
			int count = 0;
			while ( (count = in.read(buffer)) > 0) {
				stream.write(buffer, 0, count);
				count++;
			}
			stream.close();
			return bos.toByteArray();
		} catch (Exception e) {
			throw new Exception("接收不到信息");
		}
	}
	@Override
	public void close() {
		try {
			if(server!=null) {
				server.close();
				server = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
