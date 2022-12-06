package helper.math;

import java.util.Arrays;

@SuppressWarnings("unused")
public class Conversion {

	public static <T> T[] subarray(T[] array, int start, int end) {
		T[] res = Arrays.copyOf(array, end - start);
		System.arraycopy(array, start, res, 0, res.length);
		return res;
	}
}
