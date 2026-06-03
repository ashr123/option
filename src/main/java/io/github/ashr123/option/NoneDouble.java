package io.github.ashr123.option;

import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.DoubleFunction;
import java.util.stream.DoubleStream;

public enum NoneDouble implements OptionDouble {
	INSTANCE;

	@Override
	public OptionalDouble optionalDouble() {
		return OptionalDouble.empty();
	}

	@Override
	public DoubleStream stream() {
		return DoubleStream.empty();
	}

	@Override
	public OptionDouble flatMap(DoubleFunction<? extends OptionDouble> mapper) {
		Objects.requireNonNull(mapper);
		return this;
	}

	@Override
	public String toString() {
		return "NoneDouble[]";
	}
}
