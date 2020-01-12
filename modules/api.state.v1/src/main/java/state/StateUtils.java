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

	private static final StringBuilder sb;

	static {
		sb = new StringBuilder(240);
		FQN_RGX = Pattern.compile("("
				+ Utils.NAME_RGX.pattern() + "):("
				+ Utils.NAME_RGX.pattern() + ")",
			Pattern.CASE_INSENSITIVE);
	}

	public static String getFQN(final ApiObject obj) {
		return obj.getType() + ":" + obj.getMeta().getName();
	}

	public static Pattern getFQNLookupRegex(String name, String type) {
		sb.setLength(0);

//		System.out.println("\tParam name: "+name);
//		System.out.println("\tParam type: "+type);

		name = name == null ? Utils.NAME_RGX.pattern() : name.trim();
		type = type == null ? Utils.NAME_RGX.pattern() : type.trim();

		sb.append("(").append(type).append(")")
			.append(":")
			.append("(").append(name).append(")");

		return Pattern.compile(sb.toString());
	}
}
