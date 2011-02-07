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


	public static void main(String[] args) throws Exception {

		while (true) {
			try {
				/**
				 * Talk protocol with arduino the first number in string means
				 * the front led. the second one means a value to transfer 1 and
				 * 4 can have values from 0 to 255 2 and 3 can have values from
				 * 0 to 255
				 * 
				 * 1 120 
				 * 2 70
				 * 3 0
				 * 4 0
				 */				
				
				ServerSocket s = new ServerSocket(64445);
				Socket incomming = s.accept();
				Converter conv = new Converter();
				SerialWriter sw = new SerialWriter();
				sw.connect("/dev/ttyS0");
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
					conv.modifier(floatTmp[2], floatTmp[1]);
					sw.getSerialOutputStream().write(conv.getTmp());

					for (float f : floatTmp) {
						System.out.println(f);
					}
					System.out.println("\n");

					if (Byte.valueOf("11", 16).equals(byteArray[12])) {
						flag = true;
					}
				}				
				sw.closePort().close();
				is.close();
				s.close();
				conv = null;
				sw = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
