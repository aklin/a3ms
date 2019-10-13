package gr.arma3.arma.modarchiver.api.v1.interfaces;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gr.arma3.arma.modarchiver.api.v1.*;


/**
 * Any type of object that can be serialized and deserialized to JSON or YAML.
 */
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = Mod.class, name = "Mod"),
	@JsonSubTypes.Type(value = Meta.class, name = "Meta"),
	@JsonSubTypes.Type(value = ModFile.class, name = "ModFile"),
	@JsonSubTypes.Type(value = Modset.class, name = "Modset"),
	@JsonSubTypes.Type(value = Repository.class, name = "Repository"),
	// \(value = (\w+)\.class\)
	// \(value = $1\.class\, name = "$1")
})
public interface Typeable {

	String getType();
}
