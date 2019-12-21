package gr.arma3.arma.modarchiver.api.v1;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
public class UserInfoMessage implements ApiObject {
	private final String type = "UserInfoMessage";

	@NotNull
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
	private final String datetime = LocalDateTime.now().toString();
}
