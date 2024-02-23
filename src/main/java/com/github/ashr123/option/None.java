package com.github.ashr123.option;

import java.util.Optional;

public record None<T>() implements Option<T> {
	@Override
	public Optional<T> toOptional() {
		return Optional.empty();
	}
}
