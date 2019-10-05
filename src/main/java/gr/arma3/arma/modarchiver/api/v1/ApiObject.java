package gr.arma3.arma.modarchiver.api.v1;


import java.io.Serializable;

/**
 * @since 1.0
 */
public interface ApiObject extends FriendlyName, Versionable, Serializable {

	default String getTypeName() {
		return this.getClass().getName().substring(
			this.getClass().getPackageName().length() + 1
		);
	}

	default String getTypeDocumentation() {
		return "";
	}

	@Override
	default String getFriendlyName() {
		return "";
	}

	@Override
	default String getVersion() {
		return "a3md.com/v1";
	}


}
