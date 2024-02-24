[![Maven Central](https://img.shields.io/maven-central/v/io.github.ashr123/option.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.ashr123%22%20AND%20a:%22option%22)
# Option
Small package that brings DOP of Optional to Java

Gives you the possibility to write code as

```java
import io.github.ashr123.option.None;
import io.github.ashr123.option.Option;
import io.github.ashr123.option.Some;

public static Integer intABSOption(Integer integer) {
	return switch (Option.newOption(integer)) {
		case Some(Integer i) when i < 0 -> -i;
		case Some(Integer i) -> i;
		case None<Integer> ignored -> null;
		case None() -> null; // effectively same as the case above
	};
}
```
