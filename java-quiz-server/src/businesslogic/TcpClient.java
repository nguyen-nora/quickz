package businesslogic;

import java.io.*;
//import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;
import java.util.concurrent.Future;
import java.util.concurrent.CompletableFuture;

public class TcpClient {
	private AsynchronousSocketChannel client;
	private Future<Void> future = null;

	public TcpClient() {

	}
	public TcpClient(String host, int port) {
		connect(host, port);
	}
	
	public void connect(String host, int port) {
		try {
			client = AsynchronousSocketChannel.open();
			future = client.connect(new InetSocketAddress(host, port));
		} catch (Exception e) {
			e.printStackTrace();
			stop();
		}
	}

	public void sendMessage(String message) {
		CompletableFuture.runAsync(() -> {
			try {
				future.get();
				byte[] byteMsg = new String(message).getBytes();
				ByteBuffer buffer = ByteBuffer.wrap(byteMsg);
				client.write(buffer);
				//System.out.println("Send: "+message);
			} catch (Exception e) {
				e.printStackTrace();
				stop();
			}
		});
	}
	public CompletableFuture<String> readMessage() throws Exception {
		return CompletableFuture.supplyAsync(() -> {
			try {
				future.get();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
		
				client.read(buffer).get();
	
				return new String(buffer.array()).trim();
			} catch (Exception e) {
				e.printStackTrace();
				stop();
			}
			return "";
		});
	}
	public void stop() {
		try {
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
