package io.github.ashr123.option;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "unused"})
public sealed interface Option<T> permits None, Some {
	Option<?> NONE = new None<>();

	static <T> Option<T> of(T value) {
		//noinspection unchecked
		return value == null ?
				(Option<T>) NONE :
				new Some<>(value);
	}

	static <T> Option<T> of(Optional<T> value) {
		//noinspection unchecked
		return value.map((Function<T, Option<T>>) Some::new)
				.orElse((Option<T>) NONE);
	}

	Optional<T> optional();

	/**
	 * If a value is present, returns a sequential {@link Stream} containing
	 * only that value, otherwise returns an empty {@link Stream}.
	 * <br>
	 * This method can be used to transform a {@link Stream} of optional
	 * elements to a {@link Stream} of present value elements:
	 * <pre>{@code
	 *     Stream<Option<T>> os = ..
	 *     Stream<T> s = os.flatMap(Option::stream)
	 * }</pre>
	 *
	 * @return the optional value as a {@link Stream}
	 * @since 1.0.5
	 */
	Stream<T> stream();

	<U> Option<? extends U> flatMap(Function<? super T, ? extends Option<? extends U>> mapper);
}
