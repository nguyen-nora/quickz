package businesslogic;

import java.io.*;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;
import java.util.concurrent.Future;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class TcpServer {
	private AsynchronousServerSocketChannel server;

	public TcpServer(int port) {
		try {
			server = AsynchronousServerSocketChannel.open();
			server.bind(new InetSocketAddress("127.0.0.1", port));
		} catch (Exception e) {
			e.printStackTrace();
			stop();
		}
	}

	public void run() {
		System.out.println("Waiting for client...");
		while (true) {
			try {
				AsynchronousSocketChannel client = server.accept().get();
				if (client!=null && client.isOpen()) {
					CompletableFuture.runAsync(() -> process(client));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void process(AsynchronousSocketChannel client) {
		String msg = "";
		try {
			while (true) {
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				Future<Integer> bytesRead = client.read(buffer);

				bytesRead.get();

				msg = new String(buffer.array(), "UTF-8").trim();
				System.out.println(msg);
				msg = RequestHandler.process(msg);
				System.out.println(msg);

				buffer.clear();
				buffer = ByteBuffer.wrap(msg.getBytes("UTF-8"));
				Future<Integer> bytesWritten = client.write(buffer);

				bytesWritten.get();
				buffer.clear();

				if (msg == "TERM") {
					closeClient(client);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			closeClient(client);
		}
	}
	public void stop() {
		try {
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void closeClient(AsynchronousSocketChannel client) {
		try {
			System.out.println("Connection closed with client " + ((InetSocketAddress)(client.getRemoteAddress())).getAddress() + ":" + ((InetSocketAddress)(client.getRemoteAddress())).getPort());
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
