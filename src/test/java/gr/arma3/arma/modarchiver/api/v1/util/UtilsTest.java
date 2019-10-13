package gr.arma3.arma.modarchiver.api.v1.util;

import com.github.snksoft.crc.CRC;
import gr.arma3.arma.modarchiver.api.v1.Mod;
import gr.arma3.arma.modarchiver.api.v1.ModFile;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
	private static final Random r;
	private static final File test;
	private static final File empty;
	private static final File[] allFiles;

	static {
		final long seed = 4412412124L;//System.nanoTime() + System
		// .currentTimeMillis();
		Logger.getLogger(UtilsTest.class.getName())
			.log(Level.INFO, "RNG seed " + seed);

		r = new Random();
		test = new File("src/test/resources/checksumTest.txt");
		empty = new File("src/test/resources/emptyFile.txt");
		allFiles = new File[]{test, empty};

		ByteProducer.setSource(r);
	}


	@Test
	void testMinFileSize() {
		final Mod mod = Mod.builder().build();

		assertTrue(Utils.validate(Mod.builder().build()));
		assertFalse(Utils.validate(Mod.builder().version(null).build()));
	}

	private static ModFile testModFile(final InputStream inputStream) {
		try (inputStream) {
			//we need bytes to run our separate crc32 trial
			final byte[] bytes = inputStream.readAllBytes();
			final long hash = new CRC(CRC.Parameters.CRC32).calculateCRC(
				bytes);
			final ModFile ModFile = Utils.calculateChecksums(
				Utils.DEFAULT_CHUNK_SIZE_KIB,
				new ByteArrayInputStream(bytes)
			);

			System.out.println(ModFile);

			assertEquals(hash, ModFile.getFileHash());

			return ModFile;
		} catch (Exception e) {
			fail(e);
		}
		return null;
	}

	@Test
	void testDeserialize() {
		final Mod mod = Mod.builder()
			.build();

		assertEquals(Utils.serialize(mod),
			Utils.serialize(Utils.deserialize(Utils.serialize(mod))));
	}

	Stream<InputStream> getFileStreams() {
		return Arrays.stream(allFiles).map(file -> {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				fail("Could not open [" + file.getAbsolutePath() + "]", e);
				return InputStream.nullInputStream();
			}
		});
	}

	@Test
	void getSizeKiB() {
		assertEquals(test.length(), Utils.getSizeKiB(test.toPath()));
		assertEquals(empty.length(), Utils.getSizeKiB(empty.toPath()));
	}

	@Test
	void testLargeSource() {
		UtilsTest.testModFile(new ByteProducer());
	}

	@Test
	void calculateModFilesAndCheckSerialization() {
		getFileStreams()
			.map(BufferedInputStream::new)
			.map(UtilsTest::testModFile)
			.filter(Objects::nonNull)
			.forEach(modFile -> {
				final String serialized = Utils.serialize(modFile);
				final ApiObject deserialized;

				assertNotNull(serialized);
				assertNotEquals("", serialized);

				deserialized = Utils.deserialize(serialized);

				assertNotNull(deserialized);
				assertEquals("ModFile", deserialized.getType());

				System.out.println("toString:\n" + deserialized.toString());
				System.out.println("+++++++++++++++++++++++++");
				System.out.println(Utils.serialize(modFile));
				System.out.println(
					"---------------------------------------------");

				assertEquals(modFile, deserialized);
			});
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

			new ChunkTestData(1, 4, 4 * Size.KiB),
			new ChunkTestData(1, 4, (4 * Size.KiB) - 1),
			new ChunkTestData(2, 4, (4 * Size.KiB) + 1),
		}).forEach(test -> assertEquals(test.expectedChunkSize,
			Utils.getNumberOfChunks(test.fileSizeBytes,
				test.chunkSizeKiB), test.toString()));
	}

	@Test
	void testGetSizeKiB() {
		Arrays.stream(allFiles)
			.forEach(file -> Utils.getSizeKiB(file.toPath()));
	}


	/**
	 * Used to test chunking arithmetic.
	 */
	@EqualsAndHashCode
	@RequiredArgsConstructor
	@ToString
	private static class ChunkTestData {
		final int expectedChunkSize;
		final int chunkSizeKiB;
		final int fileSizeBytes;
	}
}
