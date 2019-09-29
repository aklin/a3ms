package gr.arma3.arma.modarchiver.api.v1.util;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.Random;


@Getter
@NoArgsConstructor
public class ByteProducer extends InputStream {
	private static final Random source = new Random();

	/**
	 * Number of bytes produced by this instance.
	 */
	private int count = 0;

	/**
	 * Reads the next byte of data from the input stream. The value byte is
	 * returned as an <code>int</code> in the range <code>0</code> to
	 * <code>255</code>. If no byte is available because the end of the
	 * stream
	 * has been reached, the value <code>-1</code> is returned. This method
	 * blocks until input data is available, the end of the stream is
	 * detected,
	 * or an exception is thrown.
	 *
	 * <p> A subclass must provide an implementation of this method.
	 *
	 * @return the next byte of data, or <code>-1</code> if the end of the
	 * stream is reached.
	 */
	@Override
	public int read() {
		count++;
		return (byte) source.nextInt();
	}

	/**
	 * Reads the next byte of data from the input stream. The value byte is
	 * returned as an <code>int</code> in the range <code>0</code> to
	 * <code>255</code>. If no byte is available because the end of the stream
	 * has been reached, the value <code>-1</code> is returned. This method
	 * blocks until input data is available, the end of the stream is detected,
	 * or an exception is thrown.
	 *
	 * <p> A subclass must provide an implementation of this method.
	 *
	 * @return the next byte of data, or <code>-1</code> if the end of the
	 * stream is reached.
	 */
	@Override
	public int read(byte[] b) {
		count += b.length;
		source.nextBytes(b);
		return b.length;
	}

	/**
	 * Resets the total number of bytes read to 0.
	 *
	 * @return this.
	 * @see #getCount()
	 */
	public ByteProducer resetCount() {
		this.count = 0;
		return this;
	}
}
