package gr.arma3.arma.modarchiver.api.v1.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

@Log
@UtilityClass
public class ExitCode {

	@Getter
	public enum App implements ExitCondition {
		OK(0, ""),
		INIT_FAILURE(-1, "Internal failure."),
		;

		private final int exitCode;
		private final String description;

		App(int exitCode, String description) {
			this.exitCode = exitCode;
			this.description = description;
		}
	}

	@Getter
	public enum ResourceOperation implements ExitCondition {
		NOT_FOUND(100, "One or more files could not be found."),
		NOT_READABLE(101,
			"One or more files were located, but could not be read."),
		PARSE_ERROR(110, "Syntax error."),
		;

		private final int exitCode;
		private final String description;

		ResourceOperation(int exitCode, String description) {
			this.exitCode = exitCode;
			this.description = description;
		}
	}
}
