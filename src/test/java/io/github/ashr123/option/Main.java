package io.github.ashr123.option;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {
	public static void main(String... args) throws NoSuchAlgorithmException {
		final SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
		final Option<Integer> integerOption2 = instanceStrong.nextBoolean() ?
				new Some<>(instanceStrong.nextInt()) :
				new None<>();

		final Option<Integer> integerOption1 = Option.newOption(instanceStrong.nextBoolean() ?
				instanceStrong.nextInt() :
				null);

		final Option<Integer> integerOption = Option.newOption(Optional.ofNullable(instanceStrong.nextBoolean() ?
				instanceStrong.nextInt() :
				null));

		System.out.println(Stream.generate(instanceStrong::nextInt)
				.limit(100)
				.map(integer -> instanceStrong.nextBoolean() ?
						integer :
						null)
				.map(Option::newOption)
				.map(Option::toOptional)
				.map(Option::newOption)
				.toList());
	}
}
