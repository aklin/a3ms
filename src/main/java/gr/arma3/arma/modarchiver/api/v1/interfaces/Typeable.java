package gr.arma3.arma.modarchiver.api.v1.interfaces;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gr.arma3.arma.modarchiver.api.v1.Mod;
import gr.arma3.arma.modarchiver.api.v1.ModFile;
import gr.arma3.arma.modarchiver.api.v1.Modset;
import gr.arma3.arma.modarchiver.api.v1.Repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Any type of object that can be serialized and deserialized to JSON or YAML.
 */
@JsonTypeInfo(
	use = JsonTypeInfo.Id.CLASS,
	property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = Mod.class),
	@JsonSubTypes.Type(value = ModFile.class),
	@JsonSubTypes.Type(value = Modset.class),
	@JsonSubTypes.Type(value = Repository.class),
})
public interface Typeable extends Serializable {

	@NotEmpty(message = "type must not be empty.")
	String getType();

	@NotNull
	@JsonIgnore
	Class<? extends Typeable> getClassRef();
}
