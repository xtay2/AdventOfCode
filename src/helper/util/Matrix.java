package helper.util;

import helper.StringHelper;
import helper.util.point.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;
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

	public Matrix(String s) { // TODO: Fix my dimensions!
		var mapping = Arrays.stream(s.split("\n")).map(StringHelper::charArray).toArray(Character[][]::new);
		// Remap
		content = new Character[mapping[0].length][mapping.length];
		for (int i = 0; i < content[0].length; i++) {
			for (int j = 0; j < content.length; j++) {
				content[i][j] = mapping[j][i];
			}
		}
	}

	public Matrix(Iterable<T> entries, int width, int height) {
		this(width, height);
		var iterator = entries.iterator();
		fill(p -> iterator.next());
	}

	public T get(int x, int y) {
		return get(new Point(x, y));
	}

	public T get(Point p) {
		return isInBounds(p) ? (T) content[p.x][p.y] : null;
	}

	public T insert(T val, Point p) {
		var prev = get(p);
		content[p.x][p.y] = val;
		return (T) prev;
	}

	public List<T> toList() {
		var res = new ArrayList<T>();
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				res.add(get(j, i));
			}
		}
		return res;
	}

	public Stream<T> toStream() {
		return toList().stream();
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
				var point = new Point(j, i);
				insert(supplier.apply(point), point);
			}
		}
		return this;
	}

	public Stream<Tuple<T, Point>> filter(BiPredicate<Matrix<T>, Point> condition) {
		var res = new ArrayList<Tuple<T, Point>>();
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				var point = new Point(j, i);
				var tuple = new Tuple<>(get(point), point);
				if (condition.test(this, point))
					res.add(tuple);
			}
		}
		return res.stream();
	}

	public <R> Matrix<R> map(Function<T, R> transform) {
		var res = new Matrix<R>(width(), height());
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				var point = new Point(j, i);
				res.insert(transform.apply(get(point)), point);
			}
		}
		return res;
	}

	public <R> Matrix<R> map(BiFunction<Matrix<T>, Point, R> transform) {
		var res = new Matrix<R>(width(), height());
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				var point = new Point(j, i);
				res.insert(transform.apply(this, point), point);
			}
		}
		return res;
	}

	public Matrix<T> peek(Consumer<T> consumer) {
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				consumer.accept(get(new Point(j, i)));
			}
		}
		return this;
	}

	public Matrix<T> peek(BiConsumer<Matrix<T>, Point> consumer) {
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				consumer.accept(this, new Point(j, i));
			}
		}
		return this;
	}

	public List<T> reduceLine(T indentity, BinaryOperator<T> merger) {
		var res = new ArrayList<T>();
		for (int i = 0; i < width(); i++) {
			var line = indentity;
			for (int j = 0; j < height(); j++)
				line = merger.apply(line, get(new Point(j, i)));
			res.add(line);
		}
		return res;
	}

	public Stream<T> col(int row) {
		return Arrays.stream(content[row]).map(val -> (T) val);
	}

	public Stream<T> col(int row, int start) {
		return col(row, start, content[row].length);
	}

	public Stream<T> col(int row, int start, int end) {
		return col(row).skip(start).limit(end + 1 - start);
	}

	public Stream<T> row(int col) {
		return new Generator<T>(content.length).index(i -> get(i, col)).stream();
	}

	public Stream<T> row(int col, int start) {
		return row(col, start, content.length);
	}

	public Stream<T> row(int col, int start, int end) {
		return row(col).skip(start).limit(end + 1 - start);
	}


	@Override
	public String toString() {
		return String.join("\n", map((t, p) -> "[" + t.get(p) + "]").reduceLine("", (a, b) -> a + b));
	}
}
