package io.github.ashr123.option;

import java.util.Objects;
import java.util.OptionalLong;
import java.util.function.LongFunction;
import java.util.stream.LongStream;

public enum NoneLong implements OptionLong {
	INSTANCE;

	@Override
	public OptionalLong optionalLong() {
		return OptionalLong.empty();
	}

	@Override
	public LongStream stream() {
		return LongStream.empty();
	}

	@Override
	public OptionLong flatMap(LongFunction<? extends OptionLong> mapper) {
		Objects.requireNonNull(mapper);
		return this;
	}

	@Override
	public String toString() {
		return "NoneLong[]";
	}
}
