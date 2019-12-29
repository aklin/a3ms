package state;

import gr.arma3.arma.modarchiver.api.v1.OpResult;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MemoryPersistedState implements PersistedState {

	private final Map<String, ApiObject> state = new HashMap<>();

	@Override
	public @NotNull OperationResult create(ApiObject resource) {
		final String fqn = StateUtils.getFQN(resource);

		return new OpResult(
			(!state.containsKey(fqn)
				//save happens here
				&& state.put(fqn, resource) == null)

				? ExitCode.App.OK
				: ExitCode.ResourceOperation.ALREADY_EXISTS,

			Collections.singletonList(resource));
	}

	/**
	 * @param resource Subject.
	 * @return Operation result.
	 */
	@Override
	public @NotNull OperationResult update(ApiObject resource) {
		state.put(StateUtils.getFQN(resource), resource);

		return new OpResult(
			ExitCode.App.OK,
			Collections.singletonList(resource)
		);

	}

	/**
	 * @param resource Subject.
	 * @return Operation result.
	 */
	@Override
	public @NotNull OperationResult delete(ApiObject resource) {
		final String fqn = StateUtils.getFQN(resource);

		return new OpResult(
			state.remove(fqn) != null
				? ExitCode.App.OK
				: ExitCode.ResourceOperation.NOT_FOUND,

			Collections.singletonList(resource));
	}

	/**
	 * Get resource by name and type.
	 *
	 * @param name Resource name. If null, all resources matching the given
	 *             type will be returned.
	 * @param type Resource type. Can be null.
	 * @return Operation result.
	 */
	@Override
	public @NotNull OperationResult get(String name, String type) {
		final Pattern pattern = StateUtils.getFQNLookupRegex(name, type);

		return new OpResult(
			ExitCode.App.OK,
			state.keySet()
				.stream()
				.filter(s -> pattern.matcher(s).matches())
				.map(state::get)
				.collect(Collectors.toList()));
	}

	@Override
	public void close() {
	}
}