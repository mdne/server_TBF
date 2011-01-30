package com.mdne.fly.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SocketTest {
	private static byte[] byteArray = new byte[13];
	private static byte[] tmp = new byte[4];
	private static float[] floatTmp = new float[3];
	private static boolean flag;

	public static void main(String[] args) throws InterruptedException {
		while (true) {
			try {
				ServerSocket s = new ServerSocket(64445);
				Socket incomming = s.accept();
				InputStream is = incomming.getInputStream();
				flag = false;

				while (!flag) {
					is.read(byteArray);
					ByteBuffer bb = ByteBuffer.allocate(4);
					for (int i = 0; i < 3; i++) {
						int n = i * 4;
						for (int j = 0; j < 4; j++) {
							tmp[j] = byteArray[n + j];
						}
						bb.put(tmp);
						floatTmp[i] = bb.getFloat(0);
						bb.clear();
					}

					for (float f : floatTmp) {
						System.out.println(f);
					}
					System.out.println("\n");
					
					if (Byte.valueOf("11", 16).equals(byteArray[12])) {
						flag = true;
					}
				}
				is.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
