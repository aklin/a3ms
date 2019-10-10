package gr.arma3.arma.modarchiver.api.v1.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import gr.arma3.arma.modarchiver.api.v1.AbstractV1ApiObject;
import gr.arma3.arma.modarchiver.api.v1.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.ModFile;
import lombok.experimental.UtilityClass;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@UtilityClass
public class Utils {
	static final int DEFAULT_CHUNK_SIZE_KIB = 4;
	private static final Logger logger;
	private static final Validator validator;
	private static final ObjectMapper mapper;
	private static final TypeReference<? extends Map<String, String>> mapRef;

	static {
		logger = Logger.getLogger(ModFile.class.getName());
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		mapper = new ObjectMapper(new YAMLFactory());
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
		mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
		mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
		mapRef = new TypeReference<HashMap<String, String>>() {
		};
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

	public static ApiObject parseFile(final Path path) throws
		IOException {
		final File file = path.toFile();
		final Map<String, String> raw = mapper.readValue(
			new FileInputStream(file), mapRef);
		final String type = raw.computeIfAbsent("type", (s) -> "");

		if (type.isEmpty()) {
			throw new RuntimeException("'type' field missing from: " + path.toString());
		}

		return null;
	}

	public static Map<String, String> parseJson(final String json) {
		try {
			return mapper.readValue(json, mapRef);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Collections.emptyMap();
	}

	public static Map<String, String> parseYaml(final String yaml) {
		try {
			return new ObjectMapper(new YAMLFactory()).readValue(yaml, mapRef);
//			return mapper.readValue(yaml, mapRef);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Collections.emptyMap();
	}

	public static ApiObject mapToObject(final Map<String, String> map) {
		final AbstractV1ApiObject.AbstractV1ApiObjectBuilder builder;
//		final String type = map.get("Type");

		final Reflections reflections = new Reflections(
			"gr.arma3.arma.modarchiver.api.v1",
			new SubTypesScanner(false));

		final Set<Class<? extends ApiObject>> types =
			reflections.getSubTypesOf(
			ApiObject.class);

		types.stream().forEach(type -> {
			System.out.println(type.getName());
		});

/*
		for(Class<? extends ApiObject> type : types){
			type.g
		}

		switch (type) {
			case "Checksum": return gr.arma3.arma.modarchiver.api.v1.Checksum
			.builder();
			case "Mod": return gr.arma3.arma.modarchiver.api.v1.Mod.builder();
		}
*/

		return null;
	}

	private static String getName(final Class<? extends ApiObject> c) {
		return c.getName().substring(c.getName().lastIndexOf('.') + 1);
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
			gr.arma3.arma.modarchiver.api.v1.Checksum.builder()
				.fileHash(0)
				.chunkSizeKiB(chunkSizeKiB)
				.fileSizeBytes(inputSizeBytes);

		final long remainingBytes;
		final int bufferSizeBytes = chunkSizeKiB * Size.KiB;
		final int totalChunks = Utils.getNumberOfChunks(inputSizeBytes,
			chunkSizeKiB);

		final byte[] buffer = new byte[bufferSizeBytes];

		if (inputSizeBytes < 1) {
			return b.checksum(0L).build();
		}

		int totalBytesRead = 0; // This must match the file size at the end.

		for (int i = 0; i < totalChunks; i++) {
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
			b.checksum(chunk.getValue()).fileHash(total.getValue());
		}

		b.fileSizeBytes(totalBytesRead);

		logger.log(Level.INFO, "Read a total of {0} bytes.", totalBytesRead);
		remainingBytes = input.available();
		if (remainingBytes != 0) {
			logger.log(Level.WARNING,
				Config.lang("lang.total-bytes-read-mismatch",
					inputSizeBytes,
					totalBytesRead,
					remainingBytes));
		}

		return b.build();
	}
}
