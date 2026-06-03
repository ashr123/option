package io.github.ashr123.option;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class OptionTest {
	@Test
	void someRejectsNullValues() {
		assertEquals(
				"null isn't allowed, use None",
				assertThrows(
						IllegalStateException.class,
						() -> new Some<>(null)
				)
						.getMessage()
		);
	}

	@Test
	void factoriesCreateExpectedVariantsFromNullableValuesAndOptional() {
		assertEquals(new Some<>("abc"), Option.of("abc"));
		assertSame(None.instance(), Option.of((String) null));
		assertEquals(new Some<>("abc"), Option.of(Optional.of("abc")));
		assertSame(None.instance(), Option.of(Optional.<String>empty()));
		assertThrows(NullPointerException.class, () -> Option.of((Optional<String>) null));
	}

	@Test
	void noneIsSingletonWithStableEqualityHashCodeAndToString() {
		final None<?> first = None.instance();
		final None<?> second = None.instance();

		assertSame(first, second);
		assertEquals(first, second);
		assertEquals(0, first.hashCode());
		assertEquals("None[]", first.toString());
	}

	@Test
	void optionalAndStreamExposeContainedOrEmptyValues() {
		assertEquals(Optional.of("abc"), new Some<>("abc").optional());
		assertEquals(List.of("abc"), new Some<>("abc").stream().toList());
		assertEquals(Optional.empty(), None.<String>instance().optional());
		assertTrue(None.<String>instance().stream().toList().isEmpty());
	}

	@Test
	void mapSupportsWidenedAssignments() {
		assertEquals(
				new Some<>(3),
				//widened map
				new Some<>("abc")
						.<Number>map(String::length)
		);
	}

	@Test
	void mapConvertsNullMapperResultsToNone() {
		assertSame(
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

		assertSame(None.instance(), noneMapped);
		assertFalse(called.get());
	}

	@Test
	void mapRejectsNullMappers() {
		final Function<String, Integer> nullMapper = null;

		assertThrows(NullPointerException.class, () -> new Some<>("abc").map(nullMapper));
		assertThrows(NullPointerException.class, () -> None.<String>instance().map(nullMapper));
	}

	@Test
	void flatMapSupportsTypedAssignmentsForSome() {

		assertEquals(
				new Some<>(4),
				//widened flatMap
				new Some<>("abcd")
						.<Number>flatMap(value -> new Some<>(value.length()))
		);
	}

	@Test
	void flatMapCanReturnNoneForSome() {
		assertSame(
				None.instance(),
				new Some<>("abc")
						.flatMap(value -> None.<Integer>instance())
		);
	}

	@Test
	void flatMapDoesNotInvokeMapperForNone() {
		final AtomicBoolean called = new AtomicBoolean();

		assertSame(
				None.instance(),
				None.<String>instance()
						.flatMap(value -> {
							called.set(true);
							return new Some<>(value.length());
						})
		);
		assertFalse(called.get());
	}

	@Test
	void flatMapRejectsNullMapperAndNullMapperResults() {
		final Function<String, Option<Integer>> nullMapper = null;

		assertThrows(NullPointerException.class, () -> new Some<>("abc").flatMap(nullMapper));
		assertThrows(NullPointerException.class, () -> None.<String>instance().flatMap(nullMapper));
		assertThrows(NullPointerException.class, () -> new Some<>("abc").flatMap(value -> null));
	}
}
