package io.github.ashr123.option;

import java.util.OptionalLong;
import java.util.function.LongFunction;
import java.util.stream.LongStream;

public sealed interface OptionLong permits NoneLong, SomeLong {
	static OptionLong of(Long value) {
		return value == null ?
				NoneLong.INSTANCE :
				new SomeLong(value);
	}

	static OptionLong of(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") OptionalLong value) {
		return value.isPresent() ?
				new SomeLong(value.getAsLong()) :
				NoneLong.INSTANCE;
	}

	OptionalLong optionalLong();

	/**
	 * If a value is present, returns a sequential {@link LongStream} containing
	 * only that value, otherwise returns an empty {@link LongStream}.
	 * <br>
	 * This method can be used to transform a {@code Stream} of optional
	 * elements to a {@link LongStream} of present value elements:
	 * <pre>{@code
	 *     LongStream<OptionLong<T>> os = ..
	 *     LongStream<T> s = os.flatMap(OptionLong::stream)
	 * }</pre>
	 *
	 * @return the optional value as a {@link LongStream}
	 * @since 1.1.0
	 */
	LongStream stream();

	OptionLong flatMap(LongFunction<? extends OptionLong> mapper);
}
