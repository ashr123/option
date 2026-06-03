package io.github.ashr123.option;

import org.junit.jupiter.api.Test;

import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.DoubleFunction;

import static org.junit.jupiter.api.Assertions.*;

class OptionDoubleTest {
	@Test
	void factoriesCreateExpectedVariantsAndRejectNullOptionalWrappers() {
		assertEquals(new SomeDouble(2.5), OptionDouble.of(2.5));
		assertSame(NoneDouble.INSTANCE, OptionDouble.of((Double) null));
		assertEquals(new SomeDouble(2.5), OptionDouble.of(OptionalDouble.of(2.5)));
		assertSame(NoneDouble.INSTANCE, OptionDouble.of(OptionalDouble.empty()));
		assertThrows(NullPointerException.class, () -> OptionDouble.of((OptionalDouble) null));
	}

	@Test
	void accessorsAndStreamsExposePresentAndEmptyValues() {
		assertEquals(OptionalDouble.of(2.5), new SomeDouble(2.5).optionalDouble());
		assertArrayEquals(new double[]{2.5}, new SomeDouble(2.5).stream().toArray(), 0.0);
		assertEquals(OptionalDouble.empty(), NoneDouble.INSTANCE.optionalDouble());
		assertArrayEquals(new double[0], NoneDouble.INSTANCE.stream().toArray(), 0.0);
		assertEquals("NoneDouble[]", NoneDouble.INSTANCE.toString());
	}

	@Test
	void preservesNanValues() {
		final OptionDouble option = OptionDouble.of(Double.NaN);

		assertEquals(new SomeDouble(Double.NaN), option);
		assertTrue(Double.isNaN(option.optionalDouble().getAsDouble()));
	}

	@Test
	void flatMapTransformsSomeAndSkipsNone() {
		final AtomicBoolean called = new AtomicBoolean();

		assertEquals(new SomeDouble(3.5), new SomeDouble(2.5).flatMap(value -> new SomeDouble(value + 1)));
		assertSame(NoneDouble.INSTANCE, new SomeDouble(2.5).flatMap(value -> NoneDouble.INSTANCE));
		assertSame(NoneDouble.INSTANCE, NoneDouble.INSTANCE.flatMap(value -> {
			called.set(true);
			return new SomeDouble(value + 1);
		}));
		assertFalse(called.get());
	}

	@Test
	void flatMapRejectsNullMapperAndNullMapperResults() {
		final DoubleFunction<OptionDouble> nullMapper = null;

		assertThrows(NullPointerException.class, () -> new SomeDouble(2.5).flatMap(nullMapper));
		assertThrows(NullPointerException.class, () -> NoneDouble.INSTANCE.flatMap(nullMapper));
		assertThrows(NullPointerException.class, () -> new SomeDouble(2.5).flatMap(value -> null));
	}
}
