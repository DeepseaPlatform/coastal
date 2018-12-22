package za.ac.sun.cs.coastal.strategy.hybrid;

public class BitInputStream {

	private final byte[] bytes;

	// private final int size;

	// private int avail;

	private int availInByte = 8;
	
	private int nextByte = 0;

	public BitInputStream(byte[] bytes) {
		this.bytes = bytes;
		// size = bytes.length;
		// avail = size * 8;
	}

	public long read(int bits) {
		if (bits <= availInByte) {
			long result = bytes[nextByte] & ((1 << bits) - 1);
			if (bits < availInByte) {
				bytes[nextByte] >>>= bits;
				availInByte -= bits;
				// avail -= bits;
			} else {
				availInByte = 8;
				// avail -= bits;
				nextByte++;
			}
			return result;
		} else {
			long result = bytes[nextByte];
			int offset = availInByte;
			nextByte++;
			bits -= availInByte;
			// avail -= availInByte;
			availInByte = 8;
			while (bits >= 8) {
				result |= bytes[nextByte] << offset;
				offset += 8;
				nextByte++;
				bits -= 8;
				// avail -= 8;
			}
			if (bits > 0) {
				result |= bytes[nextByte] & ((1 << bits) - 1);
				bytes[nextByte] >>>= bits;
				availInByte -= bits;
				// avail -= bits;
			}
			return result;
		}
	}

}
