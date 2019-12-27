package gr.arma3.arma.modarchiver.api.v1.util;

import gr.arma3.arma.modarchiver.api.v1.interfaces.ExitCondition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

@Log
@UtilityClass
public class ExitCode {

	@Getter
	public enum App implements ExitCondition {
		OK(0, "", false),
		OK_DRY_RUN(0,
			"Success but nothing changed (used with --dryRun)",
			false),
		INIT_FAILURE(-1, "Internal failure."),
		;

		private final int exitCode;
		private final String description;
		private final boolean isError;


		App(int exitCode, String description) {
			this(exitCode, description, true);
		}

		App(int exitCode, String description, boolean isError) {
			this.exitCode = exitCode;
			this.description = description;
			this.isError = isError;
		}
	}

	@Getter
	@RequiredArgsConstructor
	public enum ResourceOperation implements ExitCondition {
		NOT_FOUND(100, "One or more files could not be found."),
		NOT_READABLE(101,
			"One or more files were located, but could not be read."),
		PARSE_ERROR(110, "Syntax error."),
		PERSISTENCE_ERROR(150,
			"Error saving changes. Changes might be partially saved."),
		;

		private final int exitCode;
		private final String description;
		private final boolean isError = true;
	}
}
