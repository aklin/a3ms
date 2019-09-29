package gr.arma3.arma.modarchiver.api.v1.util;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Size {
	KiB(1 << 10),
	MiB(1 << 20),
	GiB(1 << 30);

	private final int bytes;

	private Size(final int sizeBytes) {
		this.bytes = sizeBytes;
	}
}
