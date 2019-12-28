package gr.arma3.arma.modarchiver.api.v1.interfaces;


import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Any type of object that can be serialized and deserialized to JSON or YAML.
 */
public interface Typeable extends Serializable {

	@NotEmpty(message = "type must not be empty.")
	String getType();
}
