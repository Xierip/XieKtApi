package pl.xierip.xieapi.utils;

/**
 * Created by xierip on 16.08.17. Web: http://xierip.pl
 */
public class InventoryUtil {

  public static int getSize(final int items) {
    final int size = (items / 9 * 9);
    return (size < items ? size + 9 : size);
  }
}
