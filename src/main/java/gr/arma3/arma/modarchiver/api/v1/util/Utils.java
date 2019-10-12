package gr.arma3.arma.modarchiver.api.v1.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import gr.arma3.arma.modarchiver.api.v1.ApiObject;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@Log
@UtilityClass
public class Utils {
	static final int DEFAULT_CHUNK_SIZE_KIB = 128;
	private static final Validator validator;
	private static final ObjectMapper mapper;

	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		mapper = new ObjectMapper(new YAMLFactory());
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
		mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
		mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
	}

	public static <E extends ApiObject> E fromMap(final Map<String, String> map) {
		try {
			return (E) deserialize(mapper.writer().writeValueAsString(map));
		} catch (JsonProcessingException e) {
			log.severe(e.getMessage());
		}
		return null;
	}

	static int getSizeKiB(final Path path) {
		return (int) Math.ceil(path.toFile().length());
	}

	public static ApiObject parseFile(final Path path) throws IOException {
		return mapper.readValue(new FileInputStream(path.toFile()),
			ApiObject.class);
	}

	/**
	 * Check given objects
	 *
	 * @param object API object to validate.
	 * @return True if no errors occurred.
	 */
	public static boolean validate(final ApiObject object) {
		final Set<ConstraintViolation<ApiObject>> errors =
			validator.validate(object);

		errors.stream()
			.map(ConstraintViolation::getMessage)
			.forEach(log::severe);

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
	 * Create a Checksum object for the given input stream.
	 *
	 * @param chunkSizeKiB Chunk size in KiB.
	 * @param input        Data input stream.
	 * @return Checksum object for given input stream.
	 */
	static gr.arma3.arma.modarchiver.api.v1.Checksum calculateChecksums(
		final int chunkSizeKiB,
		final InputStream input
	) throws IOException {
		final Checksum chunk = new CRC32(); // Single chunk checksum
		final Checksum total = new CRC32(); // Checksum for entire file

		final gr.arma3.arma.modarchiver.api.v1.Checksum.ChecksumBuilder b =
			gr.arma3.arma.modarchiver.api.v1.Checksum.builder()
				.fileHash(0)
				.chunkSizeKiB(chunkSizeKiB);

		final int bufferSizeBytes = chunkSizeKiB * Size.KiB;
		final byte[] buffer = new byte[bufferSizeBytes];

		int totalBytesRead = 0; // This must match the file size at the end.
		int bytesRead;


		while ((bytesRead = input.read(buffer)) > 0) {

			chunk.update(buffer, 0, bytesRead);
			total.update(buffer, 0, bytesRead);

			chunk.getValue();

			totalBytesRead += bytesRead;

			b.checksum(chunk.getValue())
				.fileHash(total.getValue());


			chunk.reset();
			Arrays.fill(buffer, (byte) 0);
		}

		log.log(Level.INFO, "Read a total of {0} bytes.", totalBytesRead);

		return b.fileSizeBytes(totalBytesRead)
			.build();
	}

	public static ApiObject deserialize(final String raw) {
		try {
			return mapper.readValue(raw, ApiObject.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String serialize(ApiObject apiObject) {
		try {
			return mapper
				.writerWithDefaultPrettyPrinter()
				.writeValueAsString(apiObject);
		} catch (JsonProcessingException e) {
			log.severe(e.getMessage());
			return "";
		}
	}
}
