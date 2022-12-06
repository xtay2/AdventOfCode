package helper.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Conversion {

	public static <T> T[] subarray(T[] array, int start, int end) {
		T[] res = Arrays.copyOf(array, end - start);
		System.arraycopy(array, start, res, 0, res.length);
		return res;
	}

	/** Converts a {@link CharSequence} into a {@link Collection} */
	public static Collection<Character> chars(CharSequence chars) {
		return chars(chars, new ArrayList<>());
	}

	/** Converts a {@link CharSequence} into a specified {@link Collection} */
	public static <C extends Collection<Character>> C chars(CharSequence chars, C collection) {
		return chars.chars().mapToObj(c -> (char) c).collect(Collectors.toCollection(() -> collection));
	}

}
