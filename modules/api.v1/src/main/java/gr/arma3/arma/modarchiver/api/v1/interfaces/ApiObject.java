package gr.arma3.arma.modarchiver.api.v1.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gr.arma3.arma.modarchiver.api.v1.Mod;
import gr.arma3.arma.modarchiver.api.v1.ModFile;
import gr.arma3.arma.modarchiver.api.v1.Modset;
import gr.arma3.arma.modarchiver.api.v1.Repository;

import javax.validation.constraints.NotNull;

/**
 * Object entity. Has a Meta object with name, description, and labels.
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
public interface ApiObject extends Typeable {

	@NotNull
	MetaInfo getMeta();

}
