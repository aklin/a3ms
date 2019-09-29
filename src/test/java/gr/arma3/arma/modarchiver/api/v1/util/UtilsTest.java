package gr.arma3.arma.modarchiver.api.v1.util;

import com.github.snksoft.crc.CRC;
import gr.arma3.arma.modarchiver.api.v1.Checksum;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class UtilsTest {
	private static final Random r;
	private static File test;
	private static File empty;
	private static File[] allFiles;

	static {
		r = new Random();
	}

	@BeforeAll
	static void setup() {
		test = new File("src/test/resources/checksumTest.txt");
		empty = new File("src/test/resources/emptyFile.txt");
		allFiles = new File[]{test, empty};
	}

	private static void testChecksum(final InputStream inputStream) {
		try (inputStream) {
			//we need bytes to run our separate crc32 trial
			final byte[] bytes = inputStream.readAllBytes();
			final long hash = new CRC(CRC.Parameters.CRC32).calculateCRC(
				bytes);
			final Checksum checksum = Utils.calculateChecksums(
				Utils.getSizeKiB(test.toPath()),
				Utils.DEFAULT_CHUNK_SIZE_KIB,
				new ByteArrayInputStream(bytes)
			);

			assertEquals(checksum.getFileHash(), hash);
		} catch (Exception e) {
			fail(e);
		}

	}

	Stream<InputStream> getFileStreams() {
		return Arrays.stream(allFiles).map(file -> {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				fail("Could not open [" + file.getAbsolutePath() + "]", e);
				return null;
			}
		});
	}

	@Test
	void getSizeKiB() {
		assertEquals(test.length(), Utils.getSizeKiB(test.toPath()));
		assertEquals(empty.length(), Utils.getSizeKiB(empty.toPath()));
	}

	@Test
	void calculateChecksums() {
		getFileStreams().map(BufferedInputStream::new)
			.forEach(UtilsTest::testChecksum);
	}

	@Test
	void largeFileTest() throws IOException {
		final int step = Size.KiB.getBytes() * 4;
		final File file = File.createTempFile("test_file_1gb", "_tmp");
		final byte[] buffer = new byte[step];
		final Random r = new Random();
		int written;

		try (final OutputStream os = new FileOutputStream(file)) {
			for (int i = 0; i * step < Size.GiB.getBytes(); i++) {
				r.nextBytes(buffer);
				os.write(buffer);
			}
		}

		try (final InputStream is = createInputStream(Size.GiB.getBytes())) {
			testChecksum(is);
		}
	}

	@Test
	void getNumberOfChunks() {
		Arrays.stream(new ChunkTestData[]{
			new ChunkTestData(1, 1, 0),
			new ChunkTestData(1, 1, 1),
			new ChunkTestData(2, 1, 2047),
			new ChunkTestData(2, 1, 2048),
			new ChunkTestData(3, 1, 2049),
			new ChunkTestData(1, 4, 0),
			new ChunkTestData(1, 4, 1),
			new ChunkTestData(1, 4, 2047),
			new ChunkTestData(1, 4, 2048),
			new ChunkTestData(1, 4, 2049),

			new ChunkTestData(1, 4, 4 * Size.KiB.getBytes()),
			new ChunkTestData(1, 4, (4 * Size.KiB.getBytes()) - 1),
			new ChunkTestData(2, 4, (4 * Size.KiB.getBytes()) + 1),
		}).forEach(test -> assertEquals(test.expectedChunkSize,
			Utils.getNumberOfChunks(test.fileSizeBytes,
				test.chunkSizeKiB), test.toString()));
	}

	private InputStream createInputStream(final int size) throws IOException {
/*
		final byte buffer[] = new byte[Size.KiB.getBytes() * 4];
		final OutputStream os = new ByteArrayOutputStream(buffer.length);



		final File file = File.createTempFile("a3mm", null);


		try (final OutputStream os = new FileOutputStream(file)) {
			for (int i = 0; i * step < Size.GiB.getBytes(); i++) {
				r.nextBytes(buffer);
				os.write(buffer);
			}
		}

		return new BufferedInputStream(new FileInputStream(file));*/
		return null;
	}

	@EqualsAndHashCode
	@RequiredArgsConstructor
	@ToString
	private static class ChunkTestData {
		final int expectedChunkSize;
		final int chunkSizeKiB;
		final int fileSizeBytes;
	}
}
