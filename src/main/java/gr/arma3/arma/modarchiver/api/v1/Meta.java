package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.MetaInfo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Meta implements MetaInfo {

	@Nullable
	private final String name;
	@Nullable
	private final String description;

	@Nullable
	@Singular
	private final Map<String, String> labels;

	@JsonCreator
	public Meta(
		@JsonProperty("name") String name,
		@JsonProperty("description") String description,
		@JsonProperty("labels") Map<String, String> labels
	) {
		this.name = name;
		this.description = description;
		this.labels = labels == null ? Collections.emptyMap() : labels;
	}
}
