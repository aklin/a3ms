package gr.arma3.arma.modarchiver.api.v1.interfaces;

import gr.arma3.arma.modarchiver.api.v1.StateOperationResult;
import gr.arma3.arma.modarchiver.api.v1.util.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateOpResultTest {

	@Test
	void testDefaultBuilderValues() {
		final StateOpResult defaultValues = StateOperationResult.builder()
			.build();

		assertTrue(Utils.validate(defaultValues));

		assertTrue(defaultValues.isStarted());

		assertFalse(defaultValues.isSuccess());

		assertTrue(defaultValues.isFailure());

		assertNotNull(defaultValues.getMessages());
		assertTrue(defaultValues.getMessages().isEmpty());

		assertNotNull(defaultValues.getResults());
		assertTrue(defaultValues.getResults().isEmpty());
	}
}
