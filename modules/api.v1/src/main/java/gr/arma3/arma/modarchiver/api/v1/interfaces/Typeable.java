package gr.arma3.arma.modarchiver.api.v1.interfaces;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gr.arma3.arma.modarchiver.api.v1.Meta;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Any type of object that can be serialized and deserialized to JSON or YAML.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = Meta.class),
})
public interface Typeable extends Serializable {

	@NotEmpty(message = "type must not be empty.")
	String getType();
}
