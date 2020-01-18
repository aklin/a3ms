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
		PARTIAL_FAILURE(-10, "Some operations failed."),
		OK_DRY_RUN(0,
			"Success but nothing changed (used with --dryRun)",
			false),
		READ_PERM(10, "Cannot read file or folder."),
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
	public enum Parser implements ExitCondition {
		NOT_READABLE(101, "File was found but could not be read."),
		EXPECTED_FILE(105, "Expected resource to be a file, not a directory."),
		EXPECTED_DIR(106, "Expected resource to be a directory, not a file."),
		MISSING_PARAM(107, "Missing parameter: -f"),
		PARSE_ERROR(110, "Syntax error."),
		;
		private final int exitCode;
		private final String description;
		private final boolean isError = true;
	}

	@Getter
	@RequiredArgsConstructor
	public enum ResourceOperation implements ExitCondition {
		NOT_FOUND(200, "Resource not found."),
		ALREADY_EXISTS(202, "Resource already exists."),

		SAVE_ERROR(250,
			"Error saving changes. Changes might be partially saved."),
		;

		private final int exitCode;
		private final String description;
		private final boolean isError = true;
	}
}
