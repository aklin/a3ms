package gr.arma3.arma.modarchiver.cli;

import gr.arma3.arma.modarchiver.api.v1.interfaces.OperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Format output.
 */
@AllArgsConstructor
public abstract class OutputFormatter {

	@Setter
	private static OutputFormatter singleton;


	static {
		singleton = Formatter.Json;
	}

	@NonNull
	public static OutputFormatter getDefault() {
		return singleton;
	}

	@NonNull
	public abstract String format(final OperationResult result);

	public static final class Formatter {

		private static final OutputFormatter Json;
		private static final OutputFormatter Null;
		private static final OutputFormatter JsonPath;

		static {
			Json = new OutputFormatter() {
				@Override
				public String format(final OperationResult result) {
					return Utils.serializeAny(result);
				}
			};

			JsonPath = new OutputFormatter() {
				@Override
				public String format(final OperationResult result) {
					return null;
				}
			};

			Null = new OutputFormatter() {
				@Override
				public String format(final OperationResult result) {
					return "";
				}
			};
		}

	}
}
