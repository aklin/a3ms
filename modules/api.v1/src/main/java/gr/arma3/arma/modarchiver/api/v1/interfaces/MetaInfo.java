package gr.arma3.arma.modarchiver.api.v1.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gr.arma3.arma.modarchiver.api.v1.Meta;

import javax.validation.constraints.NotNull;
import java.util.Map;

@JsonDeserialize(as = Meta.class)
public interface MetaInfo extends Nameable, Describable {

	@NotNull
	Map<String, String> getLabels();
}
