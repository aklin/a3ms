package gr.arma3.arma.modarchiver.api.v1.types;

import gr.arma3.arma.modarchiver.api.v1.Nameable;

/**
 * A Type is a mandatory key in all object definitions
 * and tells the parser how to treat this file.
 */
public interface Type extends Nameable {


	@Override
	default String getName() {
		throw new RuntimeException("Not implemented: " + this.getClass()
			.getName());
	}


}
