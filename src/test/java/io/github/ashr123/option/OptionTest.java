package io.github.ashr123.option;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

class OptionTest {
	@Test
	void someRejectsNullValues() {
		Assertions.assertEquals(
				"null isn't allowed, use None",
				Assertions.assertThrows(
						IllegalStateException.class,
						() -> new Some<>(null)
				)
						.getMessage()
		);
	}

	@Test
	void factoriesCreateExpectedVariantsFromNullableValuesAndOptional() {
		Assertions.assertEquals(new Some<>("abc"), Option.of("abc"));
		Assertions.assertSame(None.instance(), Option.of((String) null));
		Assertions.assertEquals(new Some<>("abc"), Option.of(Optional.of("abc")));
		Assertions.assertSame(None.instance(), Option.of(Optional.<String>empty()));
		Assertions.assertThrows(NullPointerException.class, () -> Option.of((Optional<String>) null));
	}

	@Test
	void noneIsSingletonWithStableEqualityHashCodeAndToString() {
		final None<?> first = None.instance();
		final None<?> second = None.instance();

		Assertions.assertSame(first, second);
		Assertions.assertEquals(first, second);
		Assertions.assertEquals(0, first.hashCode());
		Assertions.assertEquals("None[]", first.toString());
	}

	@Test
	void optionalAndStreamExposeContainedOrEmptyValues() {
		Assertions.assertEquals(Optional.of("abc"), new Some<>("abc").optional());
		Assertions.assertEquals(List.of("abc"), new Some<>("abc").stream().toList());
		Assertions.assertEquals(Optional.empty(), None.instance().optional());
		Assertions.assertTrue(None.instance().stream().toList().isEmpty());
	}

	@Test
	void mapSupportsWidenedAssignments() {
		Assertions.assertEquals(
				new Some<>(3),
				//widened map
				new Some<>("abc")
						.<Number>map(String::length)
		);
	}

	@Test
	void mapConvertsNullMapperResultsToNone() {
		Assertions.assertSame(
				None.instance(),
				new Some<>("abc")
						.<Integer>map(value -> null)
		);
	}

	@Test
	void mapDoesNotInvokeMapperForNone() {
		final AtomicBoolean called = new AtomicBoolean();
		final Option<Integer> noneMapped = None.<String>instance()
				.map(value -> {
					called.set(true);
					return value.length();
				});

		Assertions.assertSame(None.instance(), noneMapped);
		Assertions.assertFalse(called.get());
	}

	@Test
	void mapRejectsNullMappers() {
		final Function<String, Integer> nullMapper = null;

		Assertions.assertThrows(NullPointerException.class, () -> new Some<>("abc").map(nullMapper));
		Assertions.assertThrows(NullPointerException.class, () -> None.<String>instance().map(nullMapper));
	}

	@Test
	void flatMapSupportsTypedAssignmentsForSome() {

		Assertions.assertEquals(
				new Some<>(4),
				//widened flatMap
				new Some<>("abcd")
						.<Number>flatMap(value -> new Some<>(value.length()))
		);
	}

	@Test
	void flatMapCanReturnNoneForSome() {
		Assertions.assertSame(
				None.instance(),
				new Some<>("abc")
						.flatMap(value -> None.instance())
		);
	}

	@Test
	void flatMapDoesNotInvokeMapperForNone() {
		final AtomicBoolean called = new AtomicBoolean();

		Assertions.assertSame(
				None.instance(),
				None.<String>instance()
						.flatMap(value -> {
							called.set(true);
							return new Some<>(value.length());
						})
		);
		Assertions.assertFalse(called.get());
	}

	@Test
	void flatMapRejectsNullMapperAndNullMapperResults() {
		final Function<String, Option<Integer>> nullMapper = null;

		Assertions.assertThrows(NullPointerException.class, () -> new Some<>("abc").flatMap(nullMapper));
		Assertions.assertThrows(NullPointerException.class, () -> None.<String>instance().flatMap(nullMapper));
		Assertions.assertThrows(NullPointerException.class, () -> new Some<>("abc").flatMap(value -> null));
	}
}
