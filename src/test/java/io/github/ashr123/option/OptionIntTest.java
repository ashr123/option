package io.github.ashr123.option;

import org.junit.jupiter.api.Test;

import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;

import static org.junit.jupiter.api.Assertions.*;

class OptionIntTest {
	@Test
	void factoriesCreateExpectedVariantsAndRejectNullOptionalWrappers() {
		assertEquals(new SomeInt(7), OptionInt.of(7));
		assertSame(NoneInt.INSTANCE, OptionInt.of((Integer) null));
		assertEquals(new SomeInt(7), OptionInt.of(OptionalInt.of(7)));
		assertSame(NoneInt.INSTANCE, OptionInt.of(OptionalInt.empty()));
		assertThrows(NullPointerException.class, () -> OptionInt.of((OptionalInt) null));
	}

	@Test
	void accessorsAndStreamsExposePresentAndEmptyValues() {
		assertEquals(OptionalInt.of(7), new SomeInt(7).optionalInt());
		assertArrayEquals(new int[]{7}, new SomeInt(7).stream().toArray());
		assertEquals(OptionalInt.empty(), NoneInt.INSTANCE.optionalInt());
		assertArrayEquals(new int[0], NoneInt.INSTANCE.stream().toArray());
		assertEquals("NoneInt[]", NoneInt.INSTANCE.toString());
	}

	@Test
	void flatMapTransformsSomeAndSkipsNone() {
		final AtomicBoolean called = new AtomicBoolean();

		assertEquals(new SomeInt(8), new SomeInt(7).flatMap(value -> new SomeInt(value + 1)));
		assertSame(NoneInt.INSTANCE, new SomeInt(7).flatMap(value -> NoneInt.INSTANCE));
		assertSame(NoneInt.INSTANCE, NoneInt.INSTANCE.flatMap(value -> {
			called.set(true);
			return new SomeInt(value + 1);
		}));
		assertFalse(called.get());
	}

	@Test
	void flatMapRejectsNullMapperAndNullMapperResults() {
		final IntFunction<OptionInt> nullMapper = null;

		assertThrows(NullPointerException.class, () -> new SomeInt(7).flatMap(nullMapper));
		assertThrows(NullPointerException.class, () -> NoneInt.INSTANCE.flatMap(nullMapper));
		assertThrows(NullPointerException.class, () -> new SomeInt(7).flatMap(value -> null));
	}
}
