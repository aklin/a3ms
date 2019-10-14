package gr.arma3.arma.modarchiver.state;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

@Log
@UtilityClass
public class ServerState {

	public static <E extends ApiObject> E create(final E resource) {
		if (!Utils.validate(resource)) {
			return null;
		}

		return null;
	}

	public static <E extends ApiObject> E update(final E resource) {
		return null;
	}

	public <E extends ApiObject> boolean delete(final E resource) {
		return false;
	}

	public static <E extends ApiObject> E get(
		final String type,
		final String name
	) {
		return null;
	}
}
