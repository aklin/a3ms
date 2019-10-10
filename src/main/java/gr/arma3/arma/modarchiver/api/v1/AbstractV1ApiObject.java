package gr.arma3.arma.modarchiver.api.v1;

import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.logging.Logger;

@Getter
@EqualsAndHashCode
@SuperBuilder
@RequiredArgsConstructor
//@AllArgsConstructor
//@NoArgsConstructor
//@JsonDeserialize(builder = AbstractV1ApiObject.AbstractV1ApiObjectBuilder
// .class)
public abstract class AbstractV1ApiObject implements ApiObject {
	private static final long serialVersionUID;
	protected static final Logger logger;

	static {
		serialVersionUID = 1000L;
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
		return Utils.serialize(this);
	}
}
