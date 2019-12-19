package gr.arma3.arma.modarchiver.api.v1;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
public class UserInfoMessage implements ApiObject {
	@NotEmpty(message = "type must not be empty.")
	@Builder.Default
	private final String type = "InternalError";

	@NotNull(message = "Meta object must not be null.")
	@Builder.Default
	private final Meta meta = Meta.builder().build();

	@Min(0)
	@Max(10)
	private final int severity;

	@Nullable
	private final String code;

	@NotNull
	private final String message;

	@Nullable
	private final String details;

	@NotNull
	@Builder.Default
	private final String datetime = Instant.now().toString();
}
