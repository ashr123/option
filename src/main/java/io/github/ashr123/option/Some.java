package io.github.ashr123.option;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public record Some<T>(T value) implements Option<T> {
	public Some {
		if (value == null)
			throw new IllegalStateException("null isn't allowed, use None");
	}

	@Override
	public Optional<T> optional() {
		return Optional.of(value);
	}

	@Override
	public Stream<T> stream() {
		return Stream.of(value);
	}

	@Override
	public <U> Option<? extends U> flatMap(Function<? super T, ? extends Option<? extends U>> mapper) {
		return Objects.requireNonNull(mapper.apply(value));
	}
}
