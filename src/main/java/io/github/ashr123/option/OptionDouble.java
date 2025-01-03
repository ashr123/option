package io.github.ashr123.option;

import java.util.OptionalDouble;
import java.util.function.DoubleFunction;
import java.util.stream.DoubleStream;

public sealed interface OptionDouble permits NoneDouble, SomeDouble {
	static OptionDouble of(Double value) {
		return value == null ?
				NoneDouble.INSTANCE :
				new SomeDouble(value);
	}

	static OptionDouble of(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") OptionalDouble value) {
		return value.isPresent() ?
				new SomeDouble(value.getAsDouble()) :
				NoneDouble.INSTANCE;
	}

	OptionalDouble optionalDouble();

	/**
	 * If a value is present, returns a sequential {@link DoubleStream} containing
	 * only that value, otherwise returns an empty {@link DoubleStream}.
	 * <br>
	 * This method can be used to transform a {@link DoubleStream} of optional
	 * elements to a {@link DoubleStream} of present value elements:
	 * <pre>{@code
	 *     DoubleStream<OptionDouble<T>> os = ..
	 *     DoubleStream<T> s = os.flatMap(OptionDouble::stream)
	 * }</pre>
	 *
	 * @return the optional value as a {@link DoubleStream}
	 * @since 1.1.0
	 */
	DoubleStream stream();

	OptionDouble flatMap(DoubleFunction<? extends OptionDouble> mapper);
}
