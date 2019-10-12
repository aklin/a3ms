package gr.arma3.arma.modarchiver.api.v1.interfaces;

import java.io.Serializable;

interface JsonSerializable<This> extends Serializable {

	String serialize();

	This deserialize();

}
