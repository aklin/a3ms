package gr.arma3.arma.modarchiver.api.v1.interfaces;

import gr.arma3.arma.modarchiver.api.v1.util.Utils;

public interface ApiObject<This extends ApiObject>
	extends BaseObject, JsonSerializable {

	MetaInfo getMeta();

	@Override
	default This deserialize() {
		return null;
	}

	@Override
	default String serialize() {
		return Utils.serialize(this);
	}

}
