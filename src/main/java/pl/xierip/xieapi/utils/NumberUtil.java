/*
 * Copyright 2015 Marvin Schäfer (inventivetalent). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package pl.xierip.xieapi.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * © Copyright 2015 inventivetalent
 *
 * @author inventivetalent
 */

public class NumberUtil {

  public static int d(final float f1) {
    final int i = (int) f1;
    return f1 >= i ? i : i - 1;
  }

  public static int floor(final double d1) {
    final int i = (int) d1;
    return d1 >= i ? i : i - 1;
  }

  public static boolean isFloat(final String s) {
    try {
      Float.parseFloat(s);
      return true;
    } catch (final NumberFormatException ex) {
      return false;
    }
  }

  public static boolean isInt(String line) {
    try {
      Integer.parseInt(line);
      return true;
    } catch (final NumberFormatException ex) {
      return false;
    }
  }

  public static Optional<Integer> parseInt(final String s) {
    try {
      return Optional.of(Integer.parseInt(s));
    } catch (final NumberFormatException ex) {
      return Optional.empty();
    }
  }

  public static Optional<Double> parseDouble(final String s) {
    try {
      return Optional.of(Double.parseDouble(s));
    } catch (final NumberFormatException ex) {
      return Optional.empty();
    }
  }

  public static double round(double value, int places) {
    if (places < 0) {
      throw new IllegalArgumentException();
    }

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}
