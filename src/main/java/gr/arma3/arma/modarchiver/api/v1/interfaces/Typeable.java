package gr.arma3.arma.modarchiver.api.v1.interfaces;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gr.arma3.arma.modarchiver.api.v1.*;

import javax.validation.constraints.NotEmpty;


/**
 * Any type of object that can be serialized and deserialized to JSON or YAML.
 */
@JsonTypeInfo(
	use = JsonTypeInfo.Id.CLASS,
	property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = Mod.class),
	@JsonSubTypes.Type(value = Meta.class),
	@JsonSubTypes.Type(value = ModFile.class),
	@JsonSubTypes.Type(value = Modset.class),
	@JsonSubTypes.Type(value = Repository.class),
})
public interface Typeable {

	@NotEmpty(message = "type must not be empty.")
	String getType();

	Class<? extends Typeable> getClassRef();
}
