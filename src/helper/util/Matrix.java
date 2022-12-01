package helper.util;

import helper.util.point.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class Matrix<T> {

	private final Object[][] content;

	public Matrix(int width, int height) {
		content = new Object[width][height];
	}

	public Matrix(T[][] entries) {
		content = new Object[entries.length][entries[0].length];
		for (int i = 0; i < content.length; i++) {
			content[i] = Arrays.copyOf(entries[i], entries[i].length);
		}
	}

	public Matrix(Iterable<T> entries, int width, int height) {
		this(width, height);
		var iterator = entries.iterator();
		fill(p -> iterator.next());
	}

	public T get(Point p) {
		return isInBounds(p) ? (T) content[p.x][p.y] : null;
	}

	public T insert(T val, Point p) {
		var prev = get(p);
		content[p.x][p.y] = val;
		return (T) prev;
	}

	public List<Tuple<T, Point>> toList() {
		var res = new ArrayList<Tuple<T, Point>>();
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				var point = new Point(i, j);
				res.add(new Tuple<>(get(point), point));
			}
		}
		return res;
	}

	public int width() {
		return content.length;
	}

	public int height() {
		return content[0].length;
	}

	public boolean isInBounds(Point p) {
		return p.x >= 0 && p.x < content.length && p.y >= 0 && p.y < content[0].length;
	}

	public Matrix<T> fill(Function<Point, T> supplier) {
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				var point = new Point(i, j);
				insert(supplier.apply(point), point);
			}
		}
		return this;
	}

	public Stream<Tuple<T, Point>> filter(BiPredicate<T, Point> condition) {
		var res = new ArrayList<Tuple<T, Point>>();
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				var point = new Point(i, j);
				var tuple = new Tuple<>(get(point), point);
				if (condition.test(tuple.x, tuple.y))
					res.add(tuple);
			}
		}
		return res.stream();
	}

	public <R> Matrix<R> map(BiFunction<T, Point, R> transform) {
		var res = new Matrix<R>(width(), height());
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				var point = new Point(i, j);
				res.insert(transform.apply(get(point), point), point);
			}
		}
		return res;
	}

	public List<T> reduceLine(T indentity, BinaryOperator<T> merger) {
		var res = new ArrayList<T>();
		for (int i = 0; i < width(); i++) {
			var line = indentity;
			for (int j = 0; j < height(); j++)
				line = merger.apply(line, get(new Point(i, j)));
			res.add(line);
		}
		return res;
	}

	@Override
	public String toString() {
		return String.join("\n", map((t, p) -> "[" + t + "]").reduceLine("", (a, b) -> a + b));
	}


}
