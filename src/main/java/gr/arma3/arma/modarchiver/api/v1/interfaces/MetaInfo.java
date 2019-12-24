package gr.arma3.arma.modarchiver.api.v1.interfaces;

import javax.validation.constraints.NotNull;
import java.util.Map;

public interface MetaInfo extends Nameable, Describable {

	@NotNull
	Map<String, String> getLabels();
}
