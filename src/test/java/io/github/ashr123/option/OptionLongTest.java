package io.github.ashr123.option;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.LongFunction;

class OptionLongTest {
	@Test
	void factoriesCreateExpectedVariantsAndRejectNullOptionalWrappers() {
		Assertions.assertEquals(new SomeLong(7L), OptionLong.of(7L));
		Assertions.assertSame(NoneLong.INSTANCE, OptionLong.of((Long) null));
		Assertions.assertEquals(new SomeLong(7L), OptionLong.of(OptionalLong.of(7L)));
		Assertions.assertSame(NoneLong.INSTANCE, OptionLong.of(OptionalLong.empty()));
		Assertions.assertThrows(NullPointerException.class, () -> OptionLong.of((OptionalLong) null));
	}

	@Test
	void accessorsAndStreamsExposePresentAndEmptyValues() {
		Assertions.assertEquals(OptionalLong.of(7L), new SomeLong(7L).optionalLong());
		Assertions.assertArrayEquals(new long[]{7L}, new SomeLong(7L).stream().toArray());
		Assertions.assertEquals(OptionalLong.empty(), NoneLong.INSTANCE.optionalLong());
		Assertions.assertArrayEquals(new long[0], NoneLong.INSTANCE.stream().toArray());
		Assertions.assertEquals("NoneLong[]", NoneLong.INSTANCE.toString());
	}

	@Test
	void flatMapTransformsSomeAndSkipsNone() {
		final AtomicBoolean called = new AtomicBoolean();

		Assertions.assertEquals(new SomeLong(8L), new SomeLong(7L).flatMap(value -> new SomeLong(value + 1)));
		Assertions.assertSame(NoneLong.INSTANCE, new SomeLong(7L).flatMap(value -> NoneLong.INSTANCE));
		Assertions.assertSame(NoneLong.INSTANCE, NoneLong.INSTANCE.flatMap(value -> {
			called.set(true);
			return new SomeLong(value + 1);
		}));
		Assertions.assertFalse(called.get());
	}

	@Test
	void flatMapRejectsNullMapperAndNullMapperResults() {
		final LongFunction<OptionLong> nullMapper = null;

		Assertions.assertThrows(NullPointerException.class, () -> new SomeLong(7L).flatMap(nullMapper));
		Assertions.assertThrows(NullPointerException.class, () -> NoneLong.INSTANCE.flatMap(nullMapper));
		Assertions.assertThrows(NullPointerException.class, () -> new SomeLong(7L).flatMap(value -> null));
	}
}
