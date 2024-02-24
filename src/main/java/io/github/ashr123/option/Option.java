package io.github.ashr123.option;

import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "unused"})
public sealed interface Option<T> permits None, Some {
	static <T> Option<T> newOption(T value) {
		return value == null ?
				new None<>() :
				new Some<>(value);
	}

	static <T> Option<T> newOption(Optional<T> value) {
		return value.map((Function<T, Option<T>>) Some::new)
				.orElseGet(None::new);
	}

//	static <T> Optional<T> toOptional(Option<T> option) {
//		return switch (option) {
//			case Some(T value) -> Optional.of(value);
//			case None() -> Optional.empty();
//		};
//	}

	Optional<T> toOptional();
}
