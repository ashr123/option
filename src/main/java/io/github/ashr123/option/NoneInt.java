package io.github.ashr123.option;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public enum NoneInt implements OptionInt {
	INSTANCE;

	@Override
	public OptionalInt optionalInt() {
		return OptionalInt.empty();
	}

	@Override
	public IntStream stream() {
		return IntStream.empty();
	}

	@Override
	public OptionInt flatMap(IntFunction<? extends OptionInt> mapper) {
		Objects.requireNonNull(mapper);
		return this;
	}

	@Override
	public String toString() {
		return "NoneInt[]";
	}
}
