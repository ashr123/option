package io.github.ashr123.option;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public record SomeInt(int value) implements OptionInt {
	@Override
	public OptionalInt optionalInt() {
		return OptionalInt.of(value);
	}

	@Override
	public IntStream stream() {
		return IntStream.of(value);
	}

	@Override
	public OptionInt flatMap(IntFunction<? extends OptionInt> mapper) {
		return Objects.requireNonNull(mapper.apply(value));
	}
}
