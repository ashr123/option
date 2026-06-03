package io.github.ashr123.option;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.DoubleFunction;

class OptionDoubleTest {
	@Test
	void factoriesCreateExpectedVariantsAndRejectNullOptionalWrappers() {
		Assertions.assertEquals(new SomeDouble(2.5), OptionDouble.of(2.5));
		Assertions.assertSame(NoneDouble.INSTANCE, OptionDouble.of((Double) null));
		Assertions.assertEquals(new SomeDouble(2.5), OptionDouble.of(OptionalDouble.of(2.5)));
		Assertions.assertSame(NoneDouble.INSTANCE, OptionDouble.of(OptionalDouble.empty()));
		Assertions.assertThrows(NullPointerException.class, () -> OptionDouble.of((OptionalDouble) null));
	}

	@Test
	void accessorsAndStreamsExposePresentAndEmptyValues() {
		Assertions.assertEquals(OptionalDouble.of(2.5), new SomeDouble(2.5).optionalDouble());
		Assertions.assertArrayEquals(new double[]{2.5}, new SomeDouble(2.5).stream().toArray(), 0.0);
		Assertions.assertEquals(OptionalDouble.empty(), NoneDouble.INSTANCE.optionalDouble());
		Assertions.assertArrayEquals(new double[0], NoneDouble.INSTANCE.stream().toArray(), 0.0);
		Assertions.assertEquals("NoneDouble[]", NoneDouble.INSTANCE.toString());
	}

	@Test
	void preservesNanValues() {
		final OptionDouble option = OptionDouble.of(Double.NaN);

		Assertions.assertEquals(new SomeDouble(Double.NaN), option);
		Assertions.assertTrue(Double.isNaN(option.optionalDouble().getAsDouble()));
	}

	@Test
	void flatMapTransformsSomeAndSkipsNone() {
		final AtomicBoolean called = new AtomicBoolean();

		Assertions.assertEquals(new SomeDouble(3.5), new SomeDouble(2.5).flatMap(value -> new SomeDouble(value + 1)));
		Assertions.assertSame(NoneDouble.INSTANCE, new SomeDouble(2.5).flatMap(value -> NoneDouble.INSTANCE));
		Assertions.assertSame(NoneDouble.INSTANCE, NoneDouble.INSTANCE.flatMap(value -> {
			called.set(true);
			return new SomeDouble(value + 1);
		}));
		Assertions.assertFalse(called.get());
	}

	@Test
	void flatMapRejectsNullMapperAndNullMapperResults() {
		final DoubleFunction<OptionDouble> nullMapper = null;

		Assertions.assertThrows(NullPointerException.class, () -> new SomeDouble(2.5).flatMap(nullMapper));
		Assertions.assertThrows(NullPointerException.class, () -> NoneDouble.INSTANCE.flatMap(nullMapper));
		Assertions.assertThrows(NullPointerException.class, () -> new SomeDouble(2.5).flatMap(value -> null));
	}
}
