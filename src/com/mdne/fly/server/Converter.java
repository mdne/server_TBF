package com.mdne.fly.server;

public class Converter {
	private short x;
	private short y;
	private byte upper;
	private byte lower;
	private byte[] tmp;

	public Converter(){
		this.x = 0;
		this.y = 0;
		this.upper = 0;
		this.lower = 0;
		this.tmp = new byte[8];
	}

	public void setNum(float f1, float f2) {
		this.x = (short) (f1 * 2.8f);
		this.y = (short) (f2 * 2.8f);
	}

	public void convertToByte(short var) {
		this.upper = (byte) (var >> 8); // Get the upper 8 bits
		this.lower = (byte) (var & 0xFF); // Get the lower 8bits
	}

	public void modifier(float f1, float f2) {
		setNum(f1, f2);
		if (x >= 0) {
			// put val into 1
			convertToByte(x);
			tmp[0] = upper;
			tmp[1] = lower;
			tmp[6] = 0;
			tmp[7] = 0;
		} else {
			// put val into 4
			tmp[0] = 0;
			tmp[1] = 0;
			x = (short) (-1 * x);
			convertToByte(x);
			tmp[6] = upper;
			tmp[7] = lower;
		}
		if (y >= 0) {
			// put val into 2
			convertToByte(y);
			tmp[2] = upper;
			tmp[3] = lower;
			tmp[4] = 0;
			tmp[5] = 0;
		} else {
			// put val into 3
			tmp[2] = 0;
			tmp[3] = 0;
			y = (short) (-1 * y);
			convertToByte(y);
			tmp[4] = upper;
			tmp[5] = lower;
		}

	}

	public byte[] getTmp() {
		return tmp;
	}
}