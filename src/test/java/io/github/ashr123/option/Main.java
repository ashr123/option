package io.github.ashr123.option;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {
	public static void main(String... args) throws NoSuchAlgorithmException {
//		final SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
//		final Option<Integer> integerOption2 = instanceStrong.nextBoolean() ?
//				new Some<>(instanceStrong.nextInt()) :
//				new None<>();
//
//		final Option<Integer> integerOption1 = Option.newOption(instanceStrong.nextBoolean() ?
//				instanceStrong.nextInt() :
//				null);
//
//		final Option<Integer> integerOption = Option.newOption(Optional.ofNullable(instanceStrong.nextBoolean() ?
//				instanceStrong.nextInt() :
//				null));
//
//		System.out.println(Stream.generate(instanceStrong::nextInt)
//				.limit(100)
//				.map(integer -> instanceStrong.nextBoolean() ?
//						integer :
//						null)
//				.map(Option::newOption)
//				.map(Option::toOptional)
//				.map(Option::newOption)
//				.toList());

		System.out.println(intMax(new Pair<>(5, 4)));
	}

	public record Pair<L, R>(L left,
							 R right) {
	}

	public static Integer intMax(Pair<Integer, Integer> pair) {
		return switch (Option.newOption(pair)) {
			case Some(Pair(Integer l, Integer r)) when l != null && r == null -> l;
			case Some(Pair(Integer l, Integer r)) when l == null && r != null -> r;
			case Some(Pair(Integer l, Integer r)) when l != null && r != null && l > r -> l;
			case Some(Pair(Integer l, Integer r)) -> r;
			case None() -> null;
		};
	}
}
