package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.logging.Logger;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@SuperBuilder //(toBuilder = true)
public abstract class AbstractV1ApiObject implements ApiObject {
	private static final long serialVersionUID;
	private static final ObjectMapper mapper;
	private static final Logger logger;

	static {
		serialVersionUID = 1L;
		mapper = new ObjectMapper(new YAMLFactory());
		logger = Logger.getLogger(AbstractV1ApiObject.class.getName());
	}

	private final Instant lastRevision;
	private final String friendlyName;
/*

	public AbstractV1ApiObject(Instant lastRevision, String friendlyName) {
		this.lastRevision = lastRevision;
		this.friendlyName = friendlyName;
	}
*/

	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	//	@Builder
/*	public abstract static class AbstractV1ApiObjectBuilder {

	}*/
}
