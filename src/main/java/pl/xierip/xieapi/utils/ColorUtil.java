package pl.xierip.xieapi.utils;

import java.util.Random;
import org.bukkit.Color;

/**
 * Created by Xierip on 2016-06-25. DarkElite.pl ©
 */
public class ColorUtil {

  private static final Random random = new Random();


  public static Color random() {
    return Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255));
  }

  public static Color next(final Color last, final int delay) {
    int r = last.getRed();
    int g = last.getGreen();
    int b = last.getBlue();
    if (r == 255 && g == 0 && b < 255) {
      b += delay;
      if (b > 255) {
        b = 255;
      }
    }
    if (b == 255 && r <= 255 && g == 0) {
      r -= delay;
      if (r < 0) {
        r = 0;
      }
    }
    if (r == 0 && b == 255 && g < 255) {
      g += delay;
      if (g > 255) {
        g = 255;
      }
    }
    if (g == 255 && r == 0 && b > 0) {
      b -= delay;
      if (b < 0) {
        b = 0;
      }
    }
    if (g == 255 && b == 0 && r < 255) {
      r += delay;
      if (r > 255) {
        r = 255;
      }
    }
    if (r == 255 && b == 0 && g <= 255) {
      g -= delay;
      if (g < 0) {
        g = 0;
      }
    }
    return Color.fromRGB(r, g, b);
  }

  public static CustomEntry<Color, Color> nextGray(final Color last, final int delay) {
    int r = last.getRed();
    int g = last.getGreen();
    if (g == 255) {
      if (r <= 255) {
        r -= delay;
        if (r < 0) {
          r = 0;
        }
        if (r == 0) {
          g = 0;
        }
      }
    } else {
      if (r <= 255) {
        r += delay;
        if (r > 255) {
          r = 255;
        }
      }
      if (r == 255) {
        g = 255;
      }
    }
    return new CustomEntry<>(Color.fromRGB(r, r, r), Color.fromRGB(r, g, r));
  }
}












