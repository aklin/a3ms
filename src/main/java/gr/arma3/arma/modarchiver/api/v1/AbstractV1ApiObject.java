package gr.arma3.arma.modarchiver.api.v1;

import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

@Log
@Getter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
public abstract class AbstractV1ApiObject implements ApiObject {
	private static final long serialVersionUID;

	static {
		serialVersionUID = 1000L;
	}

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
