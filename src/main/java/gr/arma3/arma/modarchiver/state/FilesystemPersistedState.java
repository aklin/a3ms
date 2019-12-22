package gr.arma3.arma.modarchiver.state;

import gr.arma3.arma.modarchiver.api.v1.Repository;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Typeable;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import gr.arma3.arma.modarchiver.state.operations.OperationException;
import lombok.Builder;

import javax.annotation.Nullable;
import java.nio.file.Path;

/**
 * @version 1.0
 */
@Builder
public class FilesystemPersistedState implements PersistedState {

	private final Path pathToStateFile;


	@Override
	public <E extends ApiObject> FilesystemPersistedState create(E resource) {
		Utils.validate(resource);

		return null;
	}

	@Override
	public <E extends ApiObject> FilesystemPersistedState update(E resource) {
		Utils.validate(resource);
		return null;
	}

	@Override
	public <E extends ApiObject> FilesystemPersistedState delete(E resource) {
		Utils.validate(resource);
		return null;
	}

	public <E extends ApiObject> E get(
		String name,
		Lookup direction
	) {
		return null;
	}

	@Override
	public ApiObject get(
		String name,
		Typeable type,
		Lookup direction
	) throws OperationException {

		final Repository repository;

		try {
			repository = Utils.parseFile(pathToStateFile);
		} catch (Exception e) {
			throw new OperationException(e);
		}

		return repository;
	}

	/**
	 * Get entity.
	 *
	 * @param name Name.
	 * @param type Type.
	 * @return Object identified by name and/or type, or null.
	 */
	@Override
	public <E extends ApiObject> E get(
		@Nullable final String name,
		@Nullable final Typeable type
	) throws OperationException {
		final ApiObject obj;
		final boolean typeMatch;
		final boolean nameMatch;

		if (null == name && null == type) {
			return null;
		}

		obj = get(name, type, Lookup.SINGLE);

		if (!Utils.validate(obj)) {
			return null;
		}

		typeMatch =
			type == null ^ type.getClassRef().equals(obj.getClassRef());
		nameMatch = name == null ^ name.trim().equals(obj.getMeta().getName());

		return typeMatch || nameMatch
			? (E) obj
			: null;
	}
}
