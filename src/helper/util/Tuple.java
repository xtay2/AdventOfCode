package helper.util;

public class Tuple<A, B> {

	public final A x;
	public final B y;

	public Tuple(A x, B y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
