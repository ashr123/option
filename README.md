[![Maven Central](https://img.shields.io/maven-central/v/io.github.ashr123/option.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.ashr123%22%20AND%20a:%22option%22)
# Option
Small package that brings DOP of Optional to Java

Gives you the possibility to write code as

```java
public static void main(String[] args) {
	final SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
	final Option<Integer> integerOption = instanceStrong.nextBoolean() ?
			new Some<>(instanceStrong.nextInt()) :
			new None<>();
	System.out.println(switch (integerOption2) {
		case Some(Integer o) when o < 0 -> -o;
		case Some(Integer o) -> o;
		case None() -> Integer.MAX_VALUE;
	});
}
```
