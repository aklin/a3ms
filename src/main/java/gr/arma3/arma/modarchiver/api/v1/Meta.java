package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.java.Log;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;

@Log
@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
public class Meta implements Type {

	@NotEmpty(message = "type must not be empty.")
	private final String type;
	@Nullable
	private final String name;
	@Nullable
	private final String description;

	@JsonCreator
	protected static Meta deserialise(
		@JsonProperty("name") String name,
		@JsonProperty("description") String description,
		@JsonProperty("type") String type
	) {
		return Meta.builder()
			.name(name)
			.description(description)
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
