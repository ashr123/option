package io.github.ashr123.option;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "unused"})
public sealed interface Option<T> permits None, Some {
	static <T> Option<T> of(T value) {
		return value == null ?
				new None<>() :
				new Some<>(value);
	}

	static <T> Option<T> of(Optional<T> value) {
		return value.map((Function<T, Option<T>>) Some::new)
				.orElseGet(None::new);
	}

	Optional<T> optional();

	/**
	 * If a value is present, returns a sequential {@link Stream} containing
	 * only that value, otherwise returns an empty {@code Stream}.
	 * <br>
	 * This method can be used to transform a {@code Stream} of optional
	 * elements to a {@code Stream} of present value elements:
	 * <pre>{@code
	 *     Stream<Option<T>> os = ..
	 *     Stream<T> s = os.flatMap(Option::stream)
	 * }</pre>
	 *
	 * @return the optional value as a {@code Stream}
	 * @since 1.0.5
	 */
	Stream<T> stream();

	<U> Option<U> map(Function<? super T, ? extends U> mapper);

	Option<T> filter(Predicate<? super T> predicate);

	<U> Option<? extends U> flatMap(Function<? super T, ? extends Option<? extends U>> mapper);
}
