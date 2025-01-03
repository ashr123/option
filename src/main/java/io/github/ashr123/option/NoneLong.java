package io.github.ashr123.option;

import java.util.Objects;
import java.util.OptionalLong;
import java.util.function.LongFunction;
import java.util.stream.LongStream;

public final class NoneLong implements OptionLong {
	public static final NoneLong INSTANCE = new NoneLong();

	private NoneLong() {
		if (INSTANCE != null)
			throw new IllegalStateException("Already instantiated");
	}

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
	public boolean equals(Object obj) {
		return obj instanceof NoneLong;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "NoneLong[]";
	}
}
