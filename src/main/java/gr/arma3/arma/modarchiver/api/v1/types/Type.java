package gr.arma3.arma.modarchiver.api.v1.types;

import gr.arma3.arma.modarchiver.api.v1.FriendlyName;

/**
 * A Type is a mandatory key in all object definitions
 * and tells the parser how to treat this file.
 */
public interface Type extends FriendlyName {


	@Override
	default String getFriendlyName() {
		throw new RuntimeException("Not implemented: " + this.getClass()
			.getName());
	}


}
