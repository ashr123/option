package io.github.ashr123.option;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
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
	public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
		return Option.of(Objects.requireNonNull(mapper).apply(value));
	}

	@Override
	public Option<T> filter(Predicate<? super T> predicate) {
		return Objects.requireNonNull(predicate).test(value) ?
				this :
				new None<>();
	}

	@Override
	public <U> Option<? extends U> flatMap(Function<? super T, ? extends Option<? extends U>> mapper) {
		return Objects.requireNonNull(mapper.apply(value));
	}
}
