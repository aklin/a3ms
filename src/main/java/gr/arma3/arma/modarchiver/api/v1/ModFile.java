package gr.arma3.arma.modarchiver.api.v1;

import lombok.Builder;
import lombok.Data;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * A file that belongs to a mod.
 *
 * @since 1.0
 */

@Data
@Builder
public class ModFile implements ApiObject {
	private static final Logger logger;

	static {
		logger = Logger.getLogger(ModFile.class.getName());
	}

	private final String friendlyName;

	private final Path filePath;
	/**
	 * File size in bytes.
	 */
	private final int fileSizeBytes;

	/**
	 * Checksum for the entire file. For quick and dirty comparisons.
	 */
	@Builder.Default
	private long checksum = 0;

/*

	private ModFile(
			Path filePath,
			int fileSizeKiB,
			int chunkSizeKiB,
			long[] checksums, long checksum, String friendlyName
	) {
		this.friendlyName=friendlyName;
		this.filePath = filePath;
		this.fileSizeKiB = fileSizeKiB;
		this.checksums = checksums;
		this.checksum = checksum;
		this.chunkSizeKiB = chunkSizeKiB;
	}
*/


	/**
	 * Customized builder logic for validation.
	 */
/*	public abstract static class ModFileBuilder {
		private long checksum;
		private boolean checksumSet = false;
		private Path filePath;
		private long[] checksumArray;
		private boolean checksumArraySet = false;
		private int chunkSizeKiB;
		private boolean chunkSizeSet = false;
		private int fileSizeKiB;
		private boolean fileSizeSet = false;
		private Instant lastRevision;
		private boolean lastRevisionSet = false;


		public ModFileBuilder checksum(long checksum) {
			checksumSet = true;
			this.checksum = checksum;
			return this;
		}

		public ModFileBuilder filePath(Path filePath) {
			this.filePath = filePath;
			return this;
		}


		public ModFileBuilder checksums(long[] checksums) {
			this.checksumArraySet = checksums != null;
			this.checksumArray = checksums;
			return this;
		}

		public ModFileBuilder chunkSizeKiB(int chunkSizeKiB) {
			chunkSizeSet = chunkSizeKiB >= 1;
			this.chunkSizeKiB = chunkSizeSet ? chunkSizeKiB : Defaults
			.CHUNK_SIZE_KIB;
			return this;
		}

		public ModFileBuilder fileSizeKiB(int fileSizeKiB) {
			fileSizeSet = fileSizeKiB > 0;
			this.fileSizeKiB = fileSizeSet ? fileSizeKiB : 0;
			return this;
		}

		public ModFileBuilder lastRevision(Instant lastRevision) {
			lastRevisionSet = lastRevision != null;
			this.lastRevision = lastRevision;
			return this;
		}


		public ModFile build() throws IOException {
			final Checksum total;

			if (filePath == null) {
				throw new IllegalStateException("Parameter filePath is
				required.");
			}
			total = new CRC32();

			return new ModFile(
					filePath,
					fileSizeSet ? fileSizeKiB : Utils.getSizeKiB(filePath),
					chunkSizeSet ? chunkSizeKiB : Defaults.CHUNK_SIZE_KIB,
					checksumArraySet
							? checksumArray
							: Utils.calculateChecksums(fileSizeKiB,
							chunkSizeKiB, filePath, total),

					checksumSet ? checksum : total.getValue(),
					lastRevisionSet
							//todo fix this nonesense
							? lastRevision
							: Instant.from(LocalDateTime.now().toInstant
							(ZoneOffset.of(""))), ""
			);
		}
	}*/
}

