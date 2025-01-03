[![Maven Central](https://img.shields.io/maven-central/v/io.github.ashr123/option.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.ashr123%22%20AND%20a:%22option%22)

# Option

Small package that brings DOP of Optional to Java.

Gives you the possibility to write code as

```java
import io.github.ashr123.option.None;
import io.github.ashr123.option.Option;
import io.github.ashr123.option.Some;

public record Pair<L, R>(L left, R right) {
}

public static Integer intABSOption(Integer integer) {
	return switch (Option.of(integer)) {
		case Some(Integer i) when i < 0 -> -i;
		case Some(Integer i) -> i;
		case None<Integer> ignored -> null;
	};
}

public static Integer intMax(Pair<Integer, Integer> pair) {
	return switch (Option.of(pair)) {
		case Some(Pair(Integer l, Integer r)) when l != null && r == null -> l;
		case Some(Pair(Integer l, Integer r)) when l == null && r != null -> r;
		case Some(Pair(Integer l, Integer r)) when l != null && r != null && l > r -> l;
		case Some(Pair(Integer l, Integer r)) -> r;
		case None<Pair<Integer, Integer>> ignored -> null;
	};
}
```

## Comparison against existing `Optional<T>`

| `Optional` methods                                                     | `Option<T> opt` equivalent                                                                                             |
|------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| `empty()`                                                              | `None.instance()`                                                                                                      |
| `of(T value)`                                                          | `new Some<>(T value)`                                                                                                  |
| `ofNullable(T value)`                                                  | <ul><li>`of(T value)`</li><li>`of(Optional<T> value)`</li></ul>                                                        |
| `get()`                                                                | <ul><li>switch-case</li><li>`if (opt instanceof Some(T value)) ...`</li></ul>                                          |
| `isPresent()`                                                          | `if (opt instanceof Some<?>) ...`                                                                                      |
| `isEmpty()`                                                            | `if (opt instanceof None<?>) ...`                                                                                      |
| `ifPresent(Consumer<? super T> action)`                                | <ul><li>switch-case</li><li>`if (opt instanceof Some(T value)) value ...`</li></ul>                                    |
| `ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)`    | switch-case                                                                                                            |
| `filter(Predicate<? super T> predicate)`                               | `if (opt instanceof Some(T value) && value ...)`                                                                       |
| `map(Function<? super T, ? extends U> mapper)`                         | <ul><li>`if (opt instanceof Some(T value)) value ...`</li><li>`map(Function<? super T, ? extends U> mapper)`</li></ul> |
| `flatMap(Function<? super T, ? extends Optional<? extends U>> mapper)` | `flatMap(Function<? super T, ? extends Option<? extends U>> mapper)`                                                   |
| `or(Supplier<? extends Optional<? extends T>> supplier)`               | switch-case                                                                                                            |
| `stream()`                                                             | `stream()`                                                                                                             |
| `orElse(T other)`                                                      | switch-case                                                                                                            |
| `orElseGet(Supplier<? extends T> supplier)`                            | switch-case                                                                                                            |
| `orElseThrow()`                                                        | switch-case                                                                                                            |
| `orElseThrow(Supplier<? extends X> exceptionSupplier)`                 | switch-case                                                                                                            |

Requires JRE 21 or above.
