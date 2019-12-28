package state;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.util.regex.Pattern;

@Log
@UtilityClass
public class StateUtils {

	/**
	 * (type:name)
	 */
	public static final Pattern FQN_RGX;

	static {
		FQN_RGX = Pattern.compile("("
			+ Utils.NAME_RGX.pattern() + "):("
			+ Utils.NAME_RGX.pattern() + ")");
	}

	public static String getFQN(final ApiObject obj) {
		return obj.getType() + ":" + obj.getMeta().getName();
	}

	public static Pattern getFQNLookupRegex(String name, String type) {
		name = name == null ? Utils.NAME_RGX.pattern() : name;
		type = type == null ? Utils.NAME_RGX.pattern() : type;

		return Pattern.compile(type + ":" + name);
	}
}
