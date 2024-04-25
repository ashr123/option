package io.github.ashr123.option;

import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.DoubleFunction;
import java.util.stream.DoubleStream;

public record SomeDouble(double value) implements OptionDouble {
	@Override
	public OptionalDouble optionalDouble() {
		return OptionalDouble.of(value);
	}

	@Override
	public DoubleStream stream() {
		return DoubleStream.of(value);
	}

	@Override
	public OptionDouble flatMap(DoubleFunction<? extends OptionDouble> mapper) {
		return Objects.requireNonNull(mapper.apply(value));
	}
}
