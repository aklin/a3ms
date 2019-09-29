package gr.arma3.arma.modarchiver.api.v1;

import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.TreeSet;

/**
 * Mod object. Corresponds to a directory which complies to Arma 3
 * mod structure.
 *
 * @since 1.0
 */
@Data
@Builder
public class Mod implements ApiObject {
	private final String folderName;
	private final String friendlyName;
	private final String version;
	private final Instant lastRevision;
	private final TreeSet<ModFile> folderStructure;

	public static TreeSet<ModFile> generateStructure(final Path root) throws
		IOException {
		final TreeSet<ModFile> top;

/*
		if(!root.isDirectory() || !root.canRead()){
			throw new IllegalArgumentException("Parameter must be a readable
			directory");
		}
*/

//		Files.walk(root).parallel().map(path -> {Files.});
//		top = new TreeSet<>();

		return null;
	}
}
