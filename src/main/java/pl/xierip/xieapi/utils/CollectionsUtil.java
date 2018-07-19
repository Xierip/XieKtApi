package pl.xierip.xieapi.utils;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Xierip on 23.01.2016.
 */
public class CollectionsUtil {
    private static Random random = new Random();

    public static List<String> deserializeList(final String string) {
        if (string.equalsIgnoreCase("")) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(string.split(";")));
    }

    public static Set<Integer> deserializeIntegerSet(final String string) {
        if (string.equalsIgnoreCase("")) return new HashSet<>();
        return Arrays.stream(string.split(";")).map(Integer::parseInt).collect(Collectors.toSet());
    }

    public static int[] integerToInt(final Integer[] integers) {
        final int[] newArray = new int[integers.length];
        for (int i = 0; i < integers.length; i++) {
            newArray[i] = integers[i];
        }
        return newArray;
    }

    public static <T> T randomPick(final List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    public static <T> String serializeList(final List<T> list) {
        return StringUtils.join(list, ";");
    }

    public static <T> String serializeSet(final Set<T> set) {
        return StringUtils.join(set, ";");
    }

    public static <T> List<T> shrinkTo(List<T> list, int newSize) {
        if (list.size() < 2) return new ArrayList<T>(list);
        if (list.size() <= newSize) return list;
        return list.subList(0, newSize);
    }

    public static <T> List<T> shrinkFromTo(List<T> list, int from, int to) {
        if (list.size() < 2) return new ArrayList<T>(list);
        if (list.size() <= to) return list.subList(from, list.size());
        return list.subList(from, to);
    }

    public static <T> List<T> toList(final T... o) {
        final List<T> list = new ArrayList<>();
        list.addAll(Arrays.asList(o));
        return list;
    }

    public static <T, E> Map<T, E> toMap(final T k, final E v) {
        final Map<T, E> map = new HashMap<>();
        map.put(k, v);
        return map;
    }
}
