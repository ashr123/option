package io.github.ashr123.option;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;

class OptionIntTest {
	@Test
	void factoriesCreateExpectedVariantsAndRejectNullOptionalWrappers() {
		Assertions.assertEquals(new SomeInt(7), OptionInt.of(7));
		Assertions.assertSame(NoneInt.INSTANCE, OptionInt.of((Integer) null));
		Assertions.assertEquals(new SomeInt(7), OptionInt.of(OptionalInt.of(7)));
		Assertions.assertSame(NoneInt.INSTANCE, OptionInt.of(OptionalInt.empty()));
		Assertions.assertThrows(NullPointerException.class, () -> OptionInt.of((OptionalInt) null));
	}

	@Test
	void accessorsAndStreamsExposePresentAndEmptyValues() {
		Assertions.assertEquals(OptionalInt.of(7), new SomeInt(7).optionalInt());
		Assertions.assertArrayEquals(new int[]{7}, new SomeInt(7).stream().toArray());
		Assertions.assertEquals(OptionalInt.empty(), NoneInt.INSTANCE.optionalInt());
		Assertions.assertArrayEquals(new int[0], NoneInt.INSTANCE.stream().toArray());
		Assertions.assertEquals("NoneInt[]", NoneInt.INSTANCE.toString());
	}

	@Test
	void flatMapTransformsSomeAndSkipsNone() {
		final AtomicBoolean called = new AtomicBoolean();

		Assertions.assertEquals(new SomeInt(8), new SomeInt(7).flatMap(value -> new SomeInt(value + 1)));
		Assertions.assertSame(NoneInt.INSTANCE, new SomeInt(7).flatMap(value -> NoneInt.INSTANCE));
		Assertions.assertSame(NoneInt.INSTANCE, NoneInt.INSTANCE.flatMap(value -> {
			called.set(true);
			return new SomeInt(value + 1);
		}));
		Assertions.assertFalse(called.get());
	}

	@Test
	void flatMapRejectsNullMapperAndNullMapperResults() {
		final IntFunction<OptionInt> nullMapper = null;

		Assertions.assertThrows(NullPointerException.class, () -> new SomeInt(7).flatMap(nullMapper));
		Assertions.assertThrows(NullPointerException.class, () -> NoneInt.INSTANCE.flatMap(nullMapper));
		Assertions.assertThrows(NullPointerException.class, () -> new SomeInt(7).flatMap(value -> null));
	}
}
