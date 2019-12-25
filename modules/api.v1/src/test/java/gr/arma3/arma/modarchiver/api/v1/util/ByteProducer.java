package gr.arma3.arma.modarchiver.api.v1.util;

import lombok.Getter;

import javax.validation.constraints.DecimalMin;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;


@Getter
public class ByteProducer extends InputStream {
	private static Random source = new Random();
	@DecimalMin("-1")
	private final int limit;
	private volatile boolean isOpen;

	public ByteProducer() {
		this(Size.MiB * 4);
	}

	public ByteProducer(final int limit) {
		this.limit = Math.max(0, limit);
		this.isOpen = true;
	}

	public static void setSource(final Random source) {
		ByteProducer.source = Objects.requireNonNull(source,
			"Source cannot be null");
	}

	/**
	 * Number of bytes produced by this instance.
	 */
	private int count = 0;

	@Override
	public int available() {
		return Math.max(0, limit - count);
	}

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
		int next;

		if (!isOpen) {
			return -1;
		}

		do {
			next = (byte) source.nextInt();
		} while (next == -1);
		count++;

		if (count >= limit) {
			this.close();
		}

		return next;
	}

	@Override
	public void close() {
		isOpen = false;
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
