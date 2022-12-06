package helper;

import java.util.Objects;

import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.quote;

@SuppressWarnings("unused")
public class StringHelper {

	/** Converts a {@link CharSequence} to a {@link Character[]} */
	public static Character[] charArray(CharSequence str) {
		return str.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
	}

	/** Returns the amount of occurences of a {@link Character} in a {@link CharSequence}. */
	public static int occ(char part, CharSequence whole) {
		return occ(String.valueOf(part), whole);
	}

	/** Returns the amount of occurences of a {@link CharSequence} in another one. */
	public static int occ(CharSequence part, CharSequence whole) {
		int cnt = 0;
		for (int i = 0; i <= whole.length() - part.length(); i++) {
			if (Objects.equals(whole.subSequence(i, i + part.length()), part))
				cnt++;
		}
		return cnt;
	}

	public static void main(String[] args) {
		System.out.println(occ("ana", "banana"));
	}


}
