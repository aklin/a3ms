package gr.arma3.arma.modarchiver.state.operations;

import com.fasterxml.jackson.annotation.JsonProperty;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Optional;

@Log
@Getter
@Setter
@SuperBuilder(toBuilder = true)
abstract class AbstractOperation implements Operation {

	/**
	 * Operation subject.
	 */
	@NotNull
	private final ApiObject resource;

	/**
	 * Operation initiator.
	 */
	@NotEmpty
	private final String user;

	/**
	 * Parseable time-date string of when the operation was submitted.
	 */
	@NotNull
	@PastOrPresent
	private final String eventTime;

	/**
	 * Pretend to run but make no changes. Ignores {@link #isRequireLock()}.
	 */
	private final boolean dryRun;

	private final boolean requireLock;

	private boolean locked;

	AbstractOperation(
		@JsonProperty("resource") final ApiObject resource,
		@JsonProperty("user") final String user,
		@JsonProperty("eventTime") final String eventTime,
		@JsonProperty("dryRun") final boolean dryRun,
		@JsonProperty("requireLock") final boolean requireLock,
		@JsonProperty("locked") final boolean locked
	) throws OperationException {
		if (resource == null) {
			throw new OperationException("Resource must not be null.");
		}

		if (!Utils.validate(resource)) {
			throw new OperationException("Resource failed validation.");
		}

		this.resource = resource;
		this.locked = locked;
		this.dryRun = dryRun;
		this.requireLock = requireLock;
		this.user = Optional.ofNullable(user).orElse("default");
		this.eventTime = Optional.ofNullable(eventTime)
			.orElse(LocalDateTime.now().toString());

		if (!Utils.validate(this)) {
			throw new OperationException("Operation instance failed " +
				"validation");
		}

		acquireLock();
	}

	@Override
	public void close() {
		releaseLock();
	}

	private void acquireLock() {
		if (!isDryRun() && isRequireLock() && !isLocked()) {
			//TODO acquire lock
			setLocked(true);
		}
	}

	private void releaseLock() {
		if (isLocked()) {
			//TODO unlock
			setLocked(false);
		}
	}
}
