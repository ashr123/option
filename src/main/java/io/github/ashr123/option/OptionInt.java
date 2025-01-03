package io.github.ashr123.option;

import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public sealed interface OptionInt permits NoneInt, SomeInt {
	static OptionInt of(Integer value) {
		return value == null ?
				NoneInt.INSTANCE :
				new SomeInt(value);
	}

	static OptionInt of(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") OptionalInt value) {
		return value.isPresent() ?
				new SomeInt(value.getAsInt()) :
				NoneInt.INSTANCE;
	}

	OptionalInt optionalInt();

	/**
	 * If a value is present, returns a sequential {@link IntStream} containing
	 * only that value, otherwise returns an empty {@link IntStream}.
	 * <br>
	 * This method can be used to transform a {@link IntStream} of optional
	 * elements to a {@link IntStream} of present value elements:
	 * <pre>{@code
	 *     IntStream<OptionInt<T>> os = ..
	 *     IntStream<T> s = os.flatMap(OptionInt::stream)
	 * }</pre>
	 *
	 * @return the optional value as a {@link IntStream}
	 * @since 1.1.0
	 */
	IntStream stream();

	OptionInt flatMap(IntFunction<? extends OptionInt> mapper);
}
