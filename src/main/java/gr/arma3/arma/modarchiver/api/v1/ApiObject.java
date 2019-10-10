package gr.arma3.arma.modarchiver.api.v1;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * @since 1.0
 */
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = Mod.class, name = "Mod"),
	@JsonSubTypes.Type(value = ModFile.class, name = "ModFile"),
	@JsonSubTypes.Type(value = Checksum.class, name = "Checksum"),
	@JsonSubTypes.Type(value = Modset.class, name = "Modset"),
	@JsonSubTypes.Type(value = Repository.class, name = "Repository"),
	// \(value = (\w+)\.class\)
	// \(value = $1\.class\, name = "$1")
})
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
