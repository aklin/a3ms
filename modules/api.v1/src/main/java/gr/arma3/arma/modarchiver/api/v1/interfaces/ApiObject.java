package gr.arma3.arma.modarchiver.api.v1.interfaces;

import javax.validation.constraints.NotNull;

/**
 * Object entity. Has a Meta object with name, description, and labels.
 */
public interface ApiObject extends Typeable {
	@NotNull
	MetaInfo getMeta();
}
