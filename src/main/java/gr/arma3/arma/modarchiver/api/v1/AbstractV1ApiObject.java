package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

@Log
@Getter
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public abstract class AbstractV1ApiObject implements ApiObject {
	private static final long serialVersionUID;

	static {
		serialVersionUID = 1000L;
	}

	private final String name;
	private final String type;

	/**
	 * Pretty-print this object.
	 *
	 * @return Json representation of this object.
	 */
	@Override
	public final String toString() {
		return Utils.serialize(this);
	}

	@JsonCreator
	protected static AbstractV1ApiObject deserialise(
		@JsonProperty("name") String name,
		@JsonProperty("type") String type
	) {
		return Checksum.builder()
			.name(name)
			.type(type)
			.build();
	}

	@Override
	public String getType() {
		return this.getClass().getName()
			.substring(this.getClass().getName().lastIndexOf('.') + 1);
	}
}
