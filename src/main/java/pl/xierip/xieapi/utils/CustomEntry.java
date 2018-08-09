/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.xierip.xieapi.utils;

import java.util.Map;

/**
 * @author Xierip
 */
public class CustomEntry<K, V> implements Map.Entry<K, V> {

  private K key;
  private V value;

  public CustomEntry(final K key, final V value) {
    this.key = key;
    this.value = value;
  }


  @Override
  public K getKey() {
    return key;
  }

  @Override
  public V getValue() {
    return value;
  }

  @Override
  public V setValue(final V value) {
    final V v = this.value;
    this.value = value;
    return v;
  }

}
