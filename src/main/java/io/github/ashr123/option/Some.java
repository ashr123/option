package io.github.ashr123.option;

import java.util.Optional;

public record Some<T>(T value) implements Option<T> {
	public Some {
		if (value == null)
			throw new IllegalStateException("null isn't allowed, use None");
	}

	@Override
	public Optional<T> toOptional() {
		return Optional.of(value);
	}
}
