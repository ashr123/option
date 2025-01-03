package io.github.ashr123.option;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public final class None<T> implements Option<T> {
	private static final None<?> INSTANCE = new None<>();

	private None() {
		if (INSTANCE != null)
			throw new IllegalStateException("Already instantiated");
	}

	@SuppressWarnings("unchecked")
	public static <T> None<T> instance() {
		return (None<T>) INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
		Objects.requireNonNull(mapper);
		return (Option<U>) this;
	}

	@Override
	public Optional<T> optional() {
		return Optional.empty();
	}

	@Override
	public Stream<T> stream() {
		return Stream.empty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> Option<? extends U> flatMap(Function<? super T, ? extends Option<? extends U>> mapper) {
		Objects.requireNonNull(mapper);
		return (Option<? extends U>) this;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof None<?>;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "None[]";
	}
}
