package io.github.ashr123.option;

import java.security.NoSuchAlgorithmException;

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

		System.out.println(intMax(new Pair<>(null, null)));
	}

	public record Pair<L, R>(L left,
							 R right) {
	}

	public static Integer intABSOption(Integer integer) {
		return switch (Option.of(integer)) {
			case Some(Integer i) when i < 0 -> -i;
			case Some(Integer i) -> i;
			case None() -> null; // effectively same as the case below
			case None<Integer> ignored -> null;
		};
	}

	public static Integer intMax(Pair<Integer, Integer> pair) {
		return switch (Option.of(pair)) {
			case Some(Pair(Integer l, Integer r)) when l != null && r == null -> l;
			case Some(Pair(Integer l, Integer r)) when l == null && r != null -> r;
			case Some(Pair(Integer l, Integer r)) when l != null && r != null && l > r -> l;
			case Some(Pair(Integer l, Integer r)) -> r;
			case None() -> null;
		};
	}
}
