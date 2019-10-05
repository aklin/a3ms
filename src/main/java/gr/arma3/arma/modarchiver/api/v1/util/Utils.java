package gr.arma3.arma.modarchiver.api.v1.util;


import gr.arma3.arma.modarchiver.api.v1.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.ModFile;
import lombok.experimental.UtilityClass;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@UtilityClass
public class Utils {
	static final int DEFAULT_CHUNK_SIZE_KIB = 4;
	private static final Logger logger;
	private static final Validator validator;

	static {
		logger = Logger.getLogger(ModFile.class.getName());
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	public static <E extends ApiObject> E fromYaml(final String yamlstr) {
		return null;
	}

	public static <E extends ApiObject> E fromMap(final Map<String, String> map) {
		return null;
	}

	static int getSizeKiB(final Path path) {
		return (int) Math.ceil(path.toFile().length());
	}

	/**
	 * Check given objects
	 *
	 * @param object
	 * @return
	 */
	static boolean validate(final ApiObject object) {
		final Set<ConstraintViolation<ApiObject>> errors =
			validator.validate(object);

		for (final ConstraintViolation<ApiObject> violation : errors) {
			logger.log(Level.SEVERE,
				object.getTypeName() + ": " + violation.getMessage());
		}

		return errors.isEmpty();
	}

	/**
	 * Get minimum number of chunks needed to accommodate a given size.
	 *
	 * @param inputSizeBytes Size in bytes.
	 * @param chunkSizeKiB   Chunk size in KiB.
	 * @return At least 1.
	 * @see Utils#DEFAULT_CHUNK_SIZE_KIB
	 */
	static int getNumberOfChunks(
		final int inputSizeBytes,
		final int chunkSizeKiB
	) {
		final int inputSize = Math.max(1, inputSizeBytes);
		final int chunkSize = Math.max(1, chunkSizeKiB) * Size.KiB;
		final int divResult = inputSize / chunkSize;
		final int remainder = inputSize % chunkSize > 0 ? 1 : 0;

		return divResult + remainder;
	}

	/**
	 * @param inputSizeBytes Expected input source size in bytes.
	 * @param chunkSizeKiB   Chunk size in KiB.
	 * @param input          Data input stream.
	 * @return
	 */
	static gr.arma3.arma.modarchiver.api.v1.Checksum calculateChecksums(
		final int inputSizeBytes,
		final int chunkSizeKiB,
		final InputStream input
	) throws IOException {
		final Checksum chunk = new CRC32(); // Single chunk checksum
		final Checksum total = new CRC32(); // Checksum for entire file

		final gr.arma3.arma.modarchiver.api.v1.Checksum.ChecksumBuilder b =
			gr.arma3.arma.modarchiver.api.v1.Checksum
				.builder();

		final long remainingBytes;
		final int bufferSizeBytes = chunkSizeKiB * Size.KiB;
		final int totalChunks = Utils.getNumberOfChunks(inputSizeBytes,
			chunkSizeKiB);

		final byte[] buffer = new byte[bufferSizeBytes];
		final long[] chunks = new long[totalChunks];

		if (inputSizeBytes < 1) {
			return gr.arma3.arma.modarchiver.api.v1.Checksum.builder()
				.chunkSizeKiB(chunkSizeKiB)
				.checksums(chunks)
				.fileHash(0)
				.build();
		}

		int totalBytesRead = 0; // This must match the file size at the end.

		for (int i = 0; i < chunks.length; i++) {
			final int bytesRead;
			Arrays.fill(buffer, (byte) 0);
			chunk.reset();

			bytesRead = input.read(buffer);

			if (bytesRead <= 0) {
				break;
			}

			chunk.update(buffer, 0, bytesRead);
			total.update(buffer, 0, bytesRead);

			totalBytesRead += bytesRead;
			chunks[i] = chunk.getValue();
		}

		logger.log(Level.WARNING, "Read a total of {0} bytes.",
			totalBytesRead);
		remainingBytes = input.available();
		if (remainingBytes != 0) {
			logger.log(Level.WARNING,
				MessageFormat.format(
					"Was expecting {0} bytes. Read a total of {1} but there " +
						"is {2} bytes remaining.",
					inputSizeBytes,
					totalBytesRead,
					remainingBytes));
		}

		return gr.arma3.arma.modarchiver.api.v1.Checksum.builder()
			.checksums(chunks)
			.fileHash(total.getValue())
			.chunkSizeKiB(chunkSizeKiB)
			.build();
	}
}
