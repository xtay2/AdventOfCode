package helper;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class StreamHelper {

	public static <T> Stream<T> reverse(Stream<T> stream) {
		return (Stream<T>) stream.collect(Collector.of(
				ArrayDeque::new,
				ArrayDeque::addFirst,
				(d1, d2) -> {
					d2.addAll(d1);
					return d2;
				})).stream();
	}

	public static <T> Stream<T> takeWhileIncl(Stream<T> stream, Predicate<T> pred) {
		var res = new ArrayList<T>();
		for (var e : stream.toList()) {
			res.add(e);
			if (pred.test(e))
				continue;
			break;
		}
		System.out.println(res);
		return res.stream();
	}

}
