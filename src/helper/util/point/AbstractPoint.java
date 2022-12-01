package helper.util.point;

import helper.util.Tuple;

public abstract class AbstractPoint<T extends Number> extends Tuple<T, T> {

	public AbstractPoint(T x, T y) {
		super(x, y);
	}
}
