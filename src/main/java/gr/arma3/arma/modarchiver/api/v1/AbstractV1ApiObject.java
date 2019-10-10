package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.logging.Logger;

@Getter
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@JsonDeserialize(builder = AbstractV1ApiObject.AbstractV1ApiObjectBuilder.class)
public abstract class AbstractV1ApiObject implements ApiObject {
	private static final long serialVersionUID;
	private static final ObjectMapper mapper;
	protected static final Logger logger;

	static {
		serialVersionUID = 1000L;
		mapper = new ObjectMapper(new YAMLFactory());
		logger = Logger.getLogger(AbstractV1ApiObject.class.getName());
	}

	private final Instant lastRevision;
	private final String friendlyName;

	/**
	 * Pretty-print this object.
	 *
	 * @return Json representation of this object.
	 */
	@Override
	public final String toString() {
		try {
			return mapper
				.writerWithDefaultPrettyPrinter()
				.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
