package gr.arma3.arma.modarchiver.api.v1.util;

import gr.arma3.arma.modarchiver.api.v1.InternalError;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

@Log
@UtilityClass
public class Errors {

	public static InternalError fromThrowable(final Throwable t) {
		final InternalError e = InternalError.builder()
			.message(t.getMessage())
			.code("EXC")
			.details(t.getCause().getMessage())
			.build();

		log.warning("Generated error object for: " + t.getLocalizedMessage());

		return e;
	}

	public static InternalError getParsingError(
		final String message,
		final String details
	) {
		final InternalError e = InternalError.builder()
			.message(message)
			.details(details)
			.code("PARSERR")
			.build();
		log.fine("Generated PARSERR object for: " + message);
		return e;
	}

	public static InternalError getValidationError(
		final String message,
		final String details
	) {
		final InternalError e = InternalError.builder()
			.message(message)
			.details(details)
			.code("VALERR")
			.build();
		log.fine("Generated VALERR object for: " + message);
		return e;
	}
}
