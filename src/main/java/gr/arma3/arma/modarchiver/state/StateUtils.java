package gr.arma3.arma.modarchiver.state;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

@Log
@UtilityClass
public class StateUtils {

	public static <E extends ApiObject> boolean delete(final E resource) {
		return false;
	}


	public static <E extends ApiObject> E create(E resource) {
		return null;
	}
}
