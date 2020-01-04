package state;

import gr.arma3.arma.modarchiver.api.v1.OpResult;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.ExitCode;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
			"create",
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
			"update",
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
		System.out.println("************************************");
		System.out.println(fqn);
		if (state.containsKey(fqn)) {
			System.out.println("\n\nKey found. Entry:");
			System.out.println(Utils.serializeAny(state.get(fqn)));
			System.out.println("END.\n\n");

		}

		System.out.println(Utils.serializeAny(state));

		System.out.println("-------------------------------------");

		return new OpResult(
			"delete",
			state.containsKey(fqn) && state.remove(fqn) != null
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
			"get",
			ExitCode.App.OK,
			state.keySet()
				.stream()
				.filter(s -> pattern.matcher(s).matches())
				.map(state::get)
				.collect(Collectors.toList()));
	}

	@Override
	public void close() {
		final String fname = String.format("state_%s.json",
			LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		final File debugOut = new File(fname);


		try {
			//noinspection ResultOfMethodCallIgnored
			debugOut.createNewFile();
			try (final FileWriter writer = new FileWriter(debugOut)) {
				Utils.serializeAny(state, writer);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reset() {
		state.clear();
	}
}
