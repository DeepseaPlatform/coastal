package za.ac.sun.cs.coastal.strategy.hybrid;

import java.util.Arrays;

public class BitOutputStream {

	private static final int INITIAL_SIZE = 4;

	private byte[] bytes = new byte[INITIAL_SIZE];

	private int size = INITIAL_SIZE;

	private int free = size * 8;

	private int freeInByte = 8;

	private int firstFreeByte = 0;

	public void write(long value, int bits) {
		while (bits > free) {
			resize();
		}
		if (freeInByte >= bits) {
			long toCopy = (value << (8 - freeInByte)) & 0xffL;
			bytes[firstFreeByte] |= (byte) toCopy;
			free -= bits;
			freeInByte -= bits;
			if (freeInByte == 0) {
				freeInByte = 8;
				firstFreeByte++;
			}
		} else {
			long toCopy = (value << (8 - freeInByte)) & 0xffL;
			bytes[firstFreeByte] |= (byte) toCopy;
			value >>>= freeInByte;
			free -= freeInByte;
			bits -= freeInByte;
			freeInByte = 8;
			firstFreeByte++;
			while (bits >= 8) {
				bytes[firstFreeByte] |= (byte) (value & 0xffL);
				value >>>= 8;
				free -= 8;
				bits -= 8;
				firstFreeByte++;
			}
			if (bits > 0) {
				bytes[firstFreeByte] |= (byte) (value & 0xffL);
				free -= bits;
				freeInByte -= bits;
			}
		}
	}

	private void resize() {
		int newSize = size + (size >> 2);
		byte[] newBytes = Arrays.copyOf(bytes, newSize);
		free += (newSize - size) * 8;
		bytes = newBytes;
		size = newSize;
	}

	public byte[] toByteArray() {
		return bytes;
	}

}
