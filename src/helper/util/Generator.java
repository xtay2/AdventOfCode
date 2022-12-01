package helper.util;

import com.sun.tools.attach.AgentInitializationException;
import helper.math.Conversion;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An eager Generator for the quick creation of lists.
 */
@SuppressWarnings("unchecked")
public class Generator<T> implements Iterable<T> {

	private final List<T> content = new ArrayList<>();
	private final int length;

	// Constructors

	/**
	 * Create n elements with the same value
	 */
	public Generator(int length, T stdVal) {
		this.length = length;
		fill(stdVal);
	}

	/**
	 * Create n elements set to null
	 */
	public Generator(int length) {
		this(length, null);
	}

	/**
	 * Create a generator from an array
	 */
	@SafeVarargs
	public Generator(T... array) {
		length = array.length;
		content.addAll(List.of(array));
	}

	/**
	 * Create a generator from an {@link Iterable}
	 */
	public Generator(Iterable<T> iterable) {
		for (T e : iterable)
			content.add(e);
		length = content.size();
	}

	// Functions

	/**
	 * Override all values in this generator with the same value.
	 */
	public Generator<T> fill(T val) {
		content.clear();
		content.addAll(Collections.nCopies(length(), val));
		return this;
	}

	/**
	 * The passed {@link Function} should supplier n values, given the index for each one.
	 */
	public Generator<T> index(Function<Integer, T> supplier) {
		return calc(info -> supplier.apply(info.idx()));
	}

	/**
	 * The passed {@link Function} transforms every value.
	 */
	public <R> Generator<R> map(Function<T, R> mapper) {
		return calc(info -> mapper.apply(info.val()));
	}

	/**
	 * Like {@link #map(Function)}. But the passed {@link Function} gets
	 * detailed {@link GenInfo} for every value.
	 */
	public <R> Generator<R> calc(Function<GenInfo<T>, R> mapper) {
		var modified = new Object[length()];
		for (int i = 0; i < length(); i++)
			modified[i] = mapper.apply(new GenInfo<>(this, i));
		return new Generator<R>((R[]) modified);
	}

	/**
	 * If this generator contains collections, arrays or other generators,
	 * these will get merged into one.
	 * <p>
	 * Opposite of {@link #groupBy(int)}
	 */
	public Generator<?> flatten() {
		var list = new ArrayList<>();
		for (var elem : content) {
			if (elem instanceof Generator<?> gen)
				list.addAll(gen.list());
			else if (elem instanceof Collection<?> col)
				list.addAll(col);
			else if (elem.getClass().isArray())
				list.addAll(Arrays.asList((Object[]) elem));
			else // Already flattened
				return this;
		}
		return new Generator<>(list);
	}

	/**
	 * Keeps only the elements that match the condition.
	 */
	public Generator<T> filter(Predicate<T> condition) {
		var res = new ArrayList<T>(length());
		for (T o : content) {
			if (condition.test(o))
				res.add(o);
		}
		return new Generator<T>(res);
	}

	/**
	 * Splits content of the generator in groups of the passed groupsize.
	 * <p>
	 * Opposite of {@link #flatten()}
	 */
	public Generator<Generator<T>> groupBy(int groupsize) {
		if (length() % groupsize != 0)
			throw new IllegalArgumentException("Cannot group generator of length " + length() + " by " + groupsize + ".");
		var groups = new ArrayList<Generator<T>>();
		for (int i = 0; i < (length() / groupsize); i++)
			groups.add(new Generator<>(content.subList(i * groupsize, (i + 1) * groupsize)));
		return new Generator<>(groups);
	}

	/**
	 * Executes some code for every element.
	 */
	public Generator<T> foreach(Consumer<T> consumer) {
		content.forEach(consumer);
		return this;
	}

	// Builder

	/**
	 * Reduces all elements into one.
	 *
	 * @param identity is the base value. (Neutral element)
	 */
	public <R> R reduce(R identity, BiFunction<R, T, R> operator) {
		var val = identity;
		for (var e : content)
			val = operator.apply(val, (T) e);
		return val;
	}

	/**
	 * Returns the amount of elements in this generator.
	 */
	public int length() {
		return length;
	}

	/**
	 * Generates a {@link List}.
	 */
	public List<T> list() {
		return new ArrayList<>(content);
	}

	/**
	 * Generates a {@link Stream}.
	 */
	public Stream<T> stream() {
		return content.stream();
	}

	/**
	 * Generates a {@link Matrix}.
	 */
	public Matrix<T> matrix(int width, int height) {
		return new Matrix<>(((Generator<T>) flatten()).list(), width, height);
	}

	@Override
	public String toString() {
		return content.stream()
				.map(v -> v.getClass().isArray()
						? Arrays.deepToString((T[]) v)
						: v.toString()
				)
				.collect(Collectors.joining(", ", "[", "]"));
	}

	@Override
	public Iterator<T> iterator() {
		return content.iterator();
	}

	/**
	 * Info/Functions related to one position in a generator.
	 */
	public record GenInfo<T>(Generator<T> generator, int idx) {

		public T val() {
			return generator.content.get(idx);
		}

		public Generator<T> prev(boolean includeCurrent, int amount) {
			if (amount < 0)
				throw new IllegalArgumentException("Amount cannot be negative");
			var end = idx + (includeCurrent ? 1 : 0);
			var start = end - amount;
			var content = generator.content.subList(Math.max(start, 0), end);
			return new Generator<>(content);

		}

		public Generator<T> prev(boolean includeCurrent) {
			return prev(includeCurrent, idx + (includeCurrent ? 1 : 0));
		}

		public Generator<T> next(boolean includeCurrent, int amount) {
			if (amount < 0)
				throw new IllegalArgumentException("Amount cannot be negative");
			var start = idx + (includeCurrent ? 0 : 1);
			var end = start + amount;
			var content = generator.content.subList(start, Math.min(end, generator.length()));
			return new Generator<>(content);
		}

		public Generator<T> next(boolean includeCurrent) {
			return next(includeCurrent, generator.length() - idx);
		}

	}
}
