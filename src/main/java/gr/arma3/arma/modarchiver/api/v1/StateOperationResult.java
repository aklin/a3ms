package gr.arma3.arma.modarchiver.api.v1;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.StateOpResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class StateOperationResult implements StateOpResult {

	private final String type = "StateOpResult";

	@Builder.Default
	private final LocalDateTime start = LocalDateTime.now();

	@Builder.Default
	private final LocalDateTime end = LocalDateTime.now();

	@Builder.Default
	private final List<UserInfoMessage> messages = Collections.emptyList();

	@Builder.Default
	private final List<ApiObject> results = Collections.emptyList();

	@Builder.Default
	private final boolean success = false;

}
