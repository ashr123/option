package io.github.ashr123.option;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public record None<T>() implements Option<T> {
	@Override
	public Optional<T> optional() {
		return Optional.empty();
	}

	@Override
	public Stream<T> stream() {
		return Stream.empty();
	}

	@Override
	public <U> Option<? extends U> flatMap(Function<? super T, ? extends Option<? extends U>> mapper) {
		Objects.requireNonNull(mapper);
		//noinspection unchecked
		return (Option<? extends U>) this;
	}

	@Override
	public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
		Objects.requireNonNull(mapper);
		//noinspection unchecked
		return (Option<U>) this;
	}
}
