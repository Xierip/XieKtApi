package pl.xierip.xieapi.random;

import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.Validate;

/**
 * Created by Xierip on 2016-06-25. DarkElite.pl ©
 */
public class RandomUtil {

  private static Random random = new Random();

  public static boolean getChance(final double chance) {
    return !(chance <= 0) && (chance >= 100 || (chance >= getRandomDouble(0, 100)));
  }

  public static int getIntBetween(final int min, int max) {
    max++;
    return random.nextInt(max - min) + min;
  }

  private static double getRandomDouble(final double min, final double max)
      throws IllegalArgumentException {
    Validate.isTrue(max > min, "Max can't be smaller than min!");
    return (random.nextDouble() * (max - min) + min);
  }

  public static Float getRandomFloat(final float min, final float max)
      throws IllegalArgumentException {
    Validate.isTrue(max > min, "Max can't be smaller than min!");
    return random.nextFloat() * (max - min) + min;
  }

  public static String randomString() {
    final int size = 3 + random.nextInt(6);
    return RandomStringUtils.randomAlphabetic(size);
  }
}
