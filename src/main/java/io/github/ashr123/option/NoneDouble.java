package io.github.ashr123.option;

import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.DoubleFunction;
import java.util.stream.DoubleStream;

public final class NoneDouble implements OptionDouble {
	public static final NoneDouble INSTANCE = new NoneDouble();

	private NoneDouble() {
		if (INSTANCE != null)
			throw new IllegalStateException("Already instantiated");
	}

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
	public boolean equals(Object obj) {
		return obj instanceof NoneDouble;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "NoneDouble[]";
	}
}
