package io.github.ashr123.option;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {
	public static void main(String... args) throws NoSuchAlgorithmException {
		System.out.println(None.instance().hashCode());
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
		if (Option.of(Stream.of(3, null, 4)
				.findAny()) instanceof Some(Integer value) && value > 3) {
			int newResult = value + 3;
		}

		final long count = Stream.of(3, null, 4)
				.map(Option::of)
				.flatMap(Option::stream)
				.filter(value -> value > 3)
				.count();

		System.out.println(intMax(new Pair<>(null, null)));

		System.out.println(Objects.hash());
		System.out.println(new EmptyRecord().hashCode());
	}

	public static Integer intABSOption(Integer integer) {
		return switch (Option.of(integer)) {
			case Some(Integer i) when i < 0 -> -i;
			case Some(Integer i) -> i;
			case None<Integer> ignored -> null;
		};
	}

	public static Integer intMax(Pair<Integer, Integer> pair) {
		final Option<Pair<Integer, Integer>> pairOption = Option.of(pair);
		return switch (pairOption) {
			case Some(Pair(Integer l, Integer r)) when l != null && r == null -> l;
			case Some(Pair(Integer l, Integer r)) when l == null && r != null -> r;
			case Some(Pair(Integer l, Integer r)) when l != null && r != null && l > r -> l;
			case Some(Pair(Integer l, Integer r)) -> r;
			case None<Pair<Integer, Integer>> ignored -> null;
		};
	}

	public record Pair<L, R>(L left,
							 R right) {
	}

	private record EmptyRecord() {
	}
}
