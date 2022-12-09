package helper.util;

import java.util.Objects;

public class Tuple<A, B> {

	public final A x;
	public final B y;

	public Tuple(A x, B y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public final boolean equals(Object obj) {
		return this == obj
				|| (obj instanceof Tuple<?, ?> t && Objects.equals(x, t.x) && Objects.equals(y, t.y));
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
