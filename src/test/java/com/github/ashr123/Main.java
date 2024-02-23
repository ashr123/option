package com.github.ashr123;

import com.github.ashr123.option.None;
import com.github.ashr123.option.Option;
import com.github.ashr123.option.Some;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

public class Main {
	public static void main(String... args) throws NoSuchAlgorithmException {
		final SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
		final Option<Integer> integerOption2 = instanceStrong.nextBoolean() ?
				new Some<>(instanceStrong.nextInt()) :
				new None<>();
		System.out.println(switch (integerOption2) {
			case Some(Integer o) -> o;
			case None() -> "None";
		});

		final Option<Integer> integerOption1 = Option.newOption(instanceStrong.nextBoolean() ?
				instanceStrong.nextInt() :
				null);

		final Option<Integer> integerOption = Option.newOption(Optional.ofNullable(instanceStrong.nextBoolean() ?
				instanceStrong.nextInt() :
				null));
	}
}
