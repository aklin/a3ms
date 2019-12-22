package gr.arma3.arma.modarchiver.state;

import gr.arma3.arma.modarchiver.api.v1.Repository;
import gr.arma3.arma.modarchiver.api.v1.interfaces.ApiObject;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Typeable;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import gr.arma3.arma.modarchiver.state.operations.OperationException;
import lombok.Builder;

import javax.annotation.Nullable;
import java.io.IOException;
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
		try {
			Utils.<Repository>parseFile(pathToStateFile);
		} catch (IOException e) {
			throw new OperationException(e);
		}

		return null;
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
		final ApiObject a;

		if (null == name && null == type) {
			return null;
		}

		a = get(name, type, Lookup.SINGLE);


		if (a == null) {
			return null;
		}


		if (type != null) {

		}

		a.getClass().equals(type.getClassRef());

		return null;
	}
}
