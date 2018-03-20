package Catchmind;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import Catchmind.Server;

public class Server extends Thread {

	static OutputStream[] os = new OutputStream[0];
	InputStream is = null;;

	InputStreamReader isr = null;;
	static final int port = 3000;
	static ServerSocket serv = null;
	static Socket sock = null;
	BufferedWriter br = null;

	Server(InputStream is) {
		this.is = is;

	}

	public static void main(String[] args) {

		ServerSocket serv = null;

		try {
			serv = new ServerSocket(3000);
			while (true) {
				Socket sock = serv.accept();
				InputStream is = sock.getInputStream();
				OutputStream os = sock.getOutputStream();

				OutputStream[] temp = new OutputStream[Server.os.length + 1];
				System.arraycopy(Server.os, 0, temp, 0, Server.os.length);
				temp[Server.os.length] = os;
				Server.os = temp;

				Server ser = new Server(is);

				System.out.println(is);

				ser.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		InputStreamReader isr = null;
		BufferedReader br = null;
		int su = 0;
		try {
			System.out.println(is);

			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			while (true) {
				String msg = br.readLine();
				System.out.println(msg);
				if (su == -1) {
					break;
				}
				msg += "\n";
				for (int i = 0; i < os.length; i++) {
					os[i].write(msg.getBytes());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
