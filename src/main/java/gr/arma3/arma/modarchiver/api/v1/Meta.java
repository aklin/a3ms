package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.MetaInfo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
//@AllArgsConstructor
public class Meta implements MetaInfo {

	@JsonCreator
	public Meta(
		@JsonProperty("name") String name,
		@JsonProperty("description") String description
	) {
		this.name = name;
		this.description = description;
	}

	@Nullable
	private final String name;
	@Nullable
	private final String description;


	/*protected static Meta deserialise(
		@JsonProperty("name") String name,
		@JsonProperty("description") String description
	) {
		return Meta.builder()
			.name(name)
			.description(description)
			.build();
	}*/
}