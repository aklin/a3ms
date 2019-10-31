package gr.arma3.arma.modarchiver.api.v1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ModsetTest {
	private static final Modset empty1 = Modset.builder().build();
	private static final Modset empty2 = Modset.builder().build();

	private static final Modset set1;
	private static final Modset set2;

	static {

		set1 = Modset.builder()
			.modList(List.of(
				Mod.builder().build(),
				Mod.builder().build(),
				Mod.builder().build(),
				Mod.builder().build(),
				Mod.builder().build()
			))
			.build();

		set2 = Modset.builder()
			.modList(List.of(
				Mod.builder().build(),
				Mod.builder().build(),
				Mod.builder().build(),
				Mod.builder().build(),
				Mod.builder().build()
			))
			.build();
	}

	@Test
	void combine() {
		assertEquals(empty1.combine(empty2), empty1);
		assertEquals(0, empty1.size() + empty2.size());
	}

	@Test
	void intersect() {
		assertEquals(empty1.intersect(empty2), empty1);
		assertEquals(0, empty1.size() + empty2.size());

		final Modset intersection = set1.intersect(set2);
		assertNotNull(intersection);

	}

	@Test
	void subtract() {
		assertEquals(empty1.subtract(empty2), empty1);
		assertEquals(0, empty1.size() + empty2.size());
	}
}
