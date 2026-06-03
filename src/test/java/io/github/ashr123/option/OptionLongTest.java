package io.github.ashr123.option;

import org.junit.jupiter.api.Test;

import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.LongFunction;

import static org.junit.jupiter.api.Assertions.*;

class OptionLongTest {
	@Test
	void factoriesCreateExpectedVariantsAndRejectNullOptionalWrappers() {
		assertEquals(new SomeLong(7L), OptionLong.of(7L));
		assertSame(NoneLong.INSTANCE, OptionLong.of((Long) null));
		assertEquals(new SomeLong(7L), OptionLong.of(OptionalLong.of(7L)));
		assertSame(NoneLong.INSTANCE, OptionLong.of(OptionalLong.empty()));
		assertThrows(NullPointerException.class, () -> OptionLong.of((OptionalLong) null));
	}

	@Test
	void accessorsAndStreamsExposePresentAndEmptyValues() {
		assertEquals(OptionalLong.of(7L), new SomeLong(7L).optionalLong());
		assertArrayEquals(new long[]{7L}, new SomeLong(7L).stream().toArray());
		assertEquals(OptionalLong.empty(), NoneLong.INSTANCE.optionalLong());
		assertArrayEquals(new long[0], NoneLong.INSTANCE.stream().toArray());
		assertEquals("NoneLong[]", NoneLong.INSTANCE.toString());
	}

	@Test
	void flatMapTransformsSomeAndSkipsNone() {
		final AtomicBoolean called = new AtomicBoolean();

		assertEquals(new SomeLong(8L), new SomeLong(7L).flatMap(value -> new SomeLong(value + 1)));
		assertSame(NoneLong.INSTANCE, new SomeLong(7L).flatMap(value -> NoneLong.INSTANCE));
		assertSame(NoneLong.INSTANCE, NoneLong.INSTANCE.flatMap(value -> {
			called.set(true);
			return new SomeLong(value + 1);
		}));
		assertFalse(called.get());
	}

	@Test
	void flatMapRejectsNullMapperAndNullMapperResults() {
		final LongFunction<OptionLong> nullMapper = null;

		assertThrows(NullPointerException.class, () -> new SomeLong(7L).flatMap(nullMapper));
		assertThrows(NullPointerException.class, () -> NoneLong.INSTANCE.flatMap(nullMapper));
		assertThrows(NullPointerException.class, () -> new SomeLong(7L).flatMap(value -> null));
	}
}
