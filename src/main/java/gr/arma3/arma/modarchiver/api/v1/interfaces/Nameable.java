package gr.arma3.arma.modarchiver.api.v1.interfaces;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @since 1.0
 */

public interface Nameable {

	/**
	 * Get user-friendly name.
	 *
	 * @return user-friendly name
	 */
	@NotNull
	@Pattern(regexp = "[\\w\\d_-]*")
	default String getName() {
		return "";
	}
}
