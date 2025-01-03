package io.github.ashr123.option;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public final class NoneInt implements OptionInt {
	public static final NoneInt INSTANCE = new NoneInt();

	private NoneInt() {
		if (INSTANCE != null)
			throw new IllegalStateException("Already instantiated");
	}

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
	public boolean equals(Object obj) {
		return obj instanceof NoneInt;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "NoneInt[]";
	}
}
