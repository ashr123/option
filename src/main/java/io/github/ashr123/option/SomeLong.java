package io.github.ashr123.option;

import java.util.Objects;
import java.util.OptionalLong;
import java.util.function.LongFunction;
import java.util.stream.LongStream;

public record SomeLong(long value) implements OptionLong {
	@Override
	public OptionalLong optionalLong() {
		return OptionalLong.of(value);
	}

	@Override
	public LongStream stream() {
		return LongStream.of(value);
	}

	@Override
	public OptionLong flatMap(LongFunction<? extends OptionLong> mapper) {
		return Objects.requireNonNull(mapper.apply(value));
	}
}
