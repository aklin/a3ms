package gr.arma3.arma.modarchiver.api.v1.interfaces;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @since 1.0
 */

public interface Describable {

	/**
	 * Get user-friendly description.
	 *
	 * @return user-friendly description
	 */
	@NotNull
	@Pattern(regexp = "[\\w\\d\\s]*")
	String getDescription();
}
