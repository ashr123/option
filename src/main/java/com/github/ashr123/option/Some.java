package com.github.ashr123.option;

public record Some<T>(T value) implements Option<T> {
	public Some {
		if (value == null)
			throw new IllegalStateException("null isn't allowed, use None");
	}
}
