package gr.arma3.arma.modarchiver.api.v1.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import gr.arma3.arma.modarchiver.api.v1.Meta;
import gr.arma3.arma.modarchiver.api.v1.Mod;
import gr.arma3.arma.modarchiver.api.v1.ModFile;
import gr.arma3.arma.modarchiver.api.v1.UserInfoMessage;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ExitCondition;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Typeable;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.annotation.Nullable;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@Log
@UtilityClass
public class Utils {
	static final int DEFAULT_CHUNK_SIZE_KIB = 128;
	private static final Validator validator;
	private static final ObjectMapper mapper;

	public static final Pattern NAME_RGX;
	public static final Pattern DESC_RGX;
	/**
	 * (type:name)
	 */
	public static final Pattern FQN_RGX;

	static {
		validator = Validation.byDefaultProvider()
			.configure()
			.messageInterpolator(new ParameterMessageInterpolator())
			.buildValidatorFactory().usingContext()
			.messageInterpolator(new ParameterMessageInterpolator())
			.getValidator();
		mapper = new ObjectMapper(new YAMLFactory());
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
		mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
		mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED,
			false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
			false);
		mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL,
			true);

		NAME_RGX = Pattern.compile("[\\w\\d_-]*");
		DESC_RGX = Pattern.compile("[\\w\\d\\s]*");
		FQN_RGX = Pattern.compile("("
			+ NAME_RGX.pattern() + "):("
			+ NAME_RGX.pattern() + ")");
	}

	static int getSizeKiB(final Path path) {
		return (int) Math.ceil(path.toFile().length());
	}

	public static <T extends ApiObject> T parseFile(final Path path) throws
		IOException {
		final String raw = Files.lines(path)
			.collect(Collectors.joining("--- !<"));
		return deserialize(raw);
	}

	/**
	 * Check given objects
	 *
	 * @param object API object to validate.
	 * @return True if no errors occurred.
	 */
	public static boolean validate(final @Nullable Object object) {
		return object != null && validator.validate(object)
			.stream()
			.map(ConstraintViolation::getMessage)
			.peek(log::severe)
			.findAny()
			.isEmpty();
	}

	public static Instant parseLastRevision(final Mod mod) {
		try {
			return Instant.parse(mod.getLastRevision());
		} catch (DateTimeParseException e) {
			Errors.fromThrowable(e);
			return Instant.EPOCH;
		}
	}

	public static String getFQN(final ApiObject obj) {
		return obj.getType() + ":" + obj.getMeta().getName();
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
	static ModFile calculateChecksums(
		final int chunkSizeKiB,
		final File file,
		final InputStream input
	) throws IOException {
		final Checksum chunk = new CRC32(); // Single chunk checksum
		final Checksum total = new CRC32(); // Checksum for entire file

		final ModFile.ModFileBuilder b = ModFile.builder()
			.fileHash(0)
			.fileSizeBytes(Files.size(file.toPath()))
			.chunkSizeKiB(chunkSizeKiB)
			.filePath(file.getPath())
			.meta(Meta.builder()
				.name(file.getName())
				.build());


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

		log.log(Level.FINER, "Read a total of {0} bytes.", totalBytesRead);

		return b.fileSizeBytes(totalBytesRead)
			.build();
	}

	public static <E extends Typeable> E deserialize(final String raw) {
		try {
			return mapper.readValue(raw, new TypeReference<>() {
				@Override
				public java.lang.reflect.Type getType() {
					return super.getType();
				}
			});
		} catch (JsonProcessingException e) {
			log.warning(e.getMessage());
			Errors.getParsingError("Cannot create object from this input",
				raw);
			return null;
		}

	}

	public static String serialize(Typeable serializable) {
		try {
			return mapper
				.writerWithDefaultPrettyPrinter()
				.writeValueAsString(serializable)
				.trim();
		} catch (JsonProcessingException e) {
			log.severe(e.getMessage());
			return "";
		}
	}

	public static UserInfoMessage fromExitCondition(final ExitCondition condition) {
		return UserInfoMessage.builder()
			.code(String.valueOf(condition.getExitCode()))
			.severity(condition.isError() ? 10 : 0)
			.message(condition.getDescription())
			.build();
	}
}
