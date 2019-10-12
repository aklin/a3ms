package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.MetaInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.java.Log;

import javax.validation.constraints.NotEmpty;

@Log
@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class Meta implements MetaInfo {
	private static final long serialVersionUID;

	static {
		serialVersionUID = 1000L;
	}

	@NotEmpty(message = "name must not be empty.")
	private final String name;
	@NotEmpty(message = "type must not be empty.")
	private final String type;

	@JsonCreator
	protected static Meta deserialise(
		@JsonProperty("name") String name,
		@JsonProperty("type") String type
	) {
		return Meta.builder()
			.name(name)
			.type(type)
			.build();
	}

	@Override
	public String getType() {
		return type != null
			? type
			: this.getClass().getName()
				.substring(this.getClass().getName().lastIndexOf('.') + 1);
	}
}
