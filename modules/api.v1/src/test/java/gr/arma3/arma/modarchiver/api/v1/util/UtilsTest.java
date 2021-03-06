package gr.arma3.arma.modarchiver.api.v1.util;

import com.github.snksoft.crc.CRC;
import gr.arma3.arma.modarchiver.api.v1.*;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
	private static final File test;
	private static final File empty;
	private static final File[] allFiles;

	static {
		test = new File("src/test/resources/checksumTest.txt");
		empty = new File("src/test/resources/emptyFile.txt");
		allFiles = new File[]{test, empty};

		ByteProducer.setSource(new Random());
	}

	@BeforeAll
	static void before() {
		Utils.validate(new Object());
	}


	@Test
	void testValidation() {
		final Mod mod = Mod.builder().build();

		assertTrue(Utils.validate(Mod.builder().build()));
		assertFalse(Utils.validate(ModFile.builder().build()));

		assertFalse(Utils.validate(Modset.builder().build()));
		assertTrue(Utils.validate(Modset.builder()
			.meta(Meta.builder().build())
			.build()));

		assertTrue(Utils.validate(Meta.builder().build()));
		assertTrue(Utils.validate(Repository.builder()
			.meta(Meta.builder().name("Unit Test Repo").build())
			.address("localhost")
			.build()));
	}

	private static ModFile testModFile(final InputStream inputStream) {
		return testModFile(inputStream, empty);
	}

	private static ModFile testModFile(
		final InputStream inputStream,
		final File file
	) {
		try (inputStream) {
			//we need bytes to run our separate crc32 trial
			final byte[] bytes = inputStream.readAllBytes();
			final long hash = new CRC(CRC.Parameters.CRC32).calculateCRC(
				bytes);
			final ModFile modFile = Utils.calculateChecksums(
				Utils.DEFAULT_CHUNK_SIZE_KIB,
				file,
				new ByteArrayInputStream(bytes)
			);

			assertEquals(hash, modFile.getFileHash());
			assertTrue(Utils.validate(modFile));

			return modFile;
		} catch (Exception e) {
			fail(e);
		}
		return null;
	}

	@Test
	void testDeserialize() {
		final Mod mod = Mod.builder()
			.build();

		assertNotNull(mod.getMeta());
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
	void testNullValidation() {
		assertFalse(Utils.validate(null));
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
			.peek(Utils::validate)
			.forEach(modFile -> {
				final String serialized = Utils.serialize(modFile);
				final ApiObject deserialized;

				assertNotNull(serialized);
				assertNotEquals("", serialized);
				assertEquals(serialized, Utils.serialize(modFile));

				deserialized = Utils.deserialize(serialized);

				assertNotNull(deserialized);
				assertEquals("ModFile", deserialized.getType());

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
