package pl.xierip.xieapi.random;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomCollection<E> {

  private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
  private Random random;
  private double total = 0;

  public RandomCollection() {
    this(new Random());
  }

  public RandomCollection(final Random random) {
    this.random = random;
  }

  public void add(final double weight, final E result) {
    if (weight <= 0) {
      return;
    }
    total += weight;
    map.put(total, result);
  }

  public void clear() {
    map.clear();
    total = 0;
  }

  public NavigableMap<Double, E> getAll() {
    return map;
  }

  public E next() {
    random = new Random();
    if (map.size() == 1) {
      return map.firstEntry().getValue();
    }
    final double value = random.nextDouble() * total;
    return map.ceilingEntry(value).getValue();
  }

  public int size() {
    return map.size();
  }
}