package gr.arma3.arma.modarchiver.api.v1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.TreeSet;

/**
 * Mod object. Corresponds to a directory which complies to Arma 3
 * mod structure.
 *
 * @since 1.0
 */
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = Mod.ModBuilder.class)
public class Mod extends AbstractV1ApiObject {
	private final String folderName;
	private final String friendlyName;
	private final String version;
	private final Instant lastRevision;
	private final TreeSet<ModFile> folderStructure;

}
